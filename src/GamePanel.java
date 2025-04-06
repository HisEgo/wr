import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private Hexagon hexagon;
    private int hexagonSize;
    private int largerHexagonSize;
    private Player player;
    private List<Obstacle> obstacles;
    private Random random;
    private int centerX;
    private int centerY;
    private int obstacleSpawnDistance;
    private Timer timer;
    private boolean obstacleCreated = false;
    private int score = 0;
    private int obstacleSpacing = 150;
    private double[] hexagonX;
    private double[] hexagonY;
    private Color randomColor;
    private int colorChangeInterval = 30;
    private int frameCounter = 0;
    private boolean gameOver = false;


    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

        hexagonSize = 35;
        largerHexagonSize = 55;
        hexagon = new Hexagon(getWidth() / 2, getHeight() / 2, hexagonSize, 2, Color.WHITE);
        player = new Player(getWidth() / 2, getHeight() / 2, 20);
        obstacles = new ArrayList<>();
        random = new Random();

        obstacleSpawnDistance = hexagonSize * 3;
        timer = new Timer(20, this);
        timer.start();

        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke("LEFT"), "rotateLeft");
        am.put("rotateLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.rotateLeft();
                repaint();
            }
        });

        im.put(KeyStroke.getKeyStroke("RIGHT"), "rotateRight");
        am.put("rotateRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.rotateRight();
                repaint();
            }
        });

        hexagonX = new double[6];
        hexagonY = new double[6];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        hexagon.setX(centerX);
        hexagon.setY(centerY);
        player.setCenter(centerX, centerY);

        calculateHexagonVertices();
        calculatePlayerPosition();

        drawSectors(g2d, centerX, centerY);

        hexagon.draw(g2d);
        player.draw(g2d);
        drawObstacles(g2d);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + score, 10, 30);

        if (gameOver) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics fm = g2d.getFontMetrics();
            String gameOverText = "Game Over!";
            int textWidth = fm.stringWidth(gameOverText);
            int textX = centerX - textWidth / 2;
            int textY = centerY;
            g2d.drawString(gameOverText, textX, textY);
        }
    }

    private void calculateHexagonVertices() {
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i;
            hexagonX[i] = centerX + largerHexagonSize * Math.cos(angle);
            hexagonY[i] = centerY + largerHexagonSize * Math.sin(angle);
        }
    }

    private void calculatePlayerPosition() {
        double angle = player.getAngle();
        int sector = (int) Math.floor(angle / (Math.PI / 3));
        sector = (sector % 6 + 6) % 6;

        double sectorAngle = angle % (Math.PI / 3);
        double t = sectorAngle / (Math.PI / 3);

        int nextSector = (sector + 1) % 6;

        double playerX = hexagonX[sector] + (hexagonX[nextSector] - hexagonX[sector]) * t;
        double playerY = hexagonY[sector] + (hexagonY[nextSector] - hexagonY[sector]) * t;

        player.setX((int) playerX);
        player.setY((int) playerY);
    }


    private void drawObstacles(Graphics2D g2d) {
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g2d);
        }
    }

    private void drawSectors(Graphics2D g2d, int centerX, int centerY) {
        g2d.setColor(Color.DARK_GRAY);
        int hexagonSize = hexagon.getSize();

        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i;
            int x = centerX + (int) (2000 * Math.cos(angle));
            int y = centerY + (int) (2000 * Math.sin(angle));
            g2d.drawLine(centerX, centerY, x, y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            return;
        }

        if (e.getSource() == timer) {
            addNewObstacle();

            Iterator<Obstacle> iterator = obstacles.iterator();
            while (iterator.hasNext()) {
                Obstacle obstacle = iterator.next();
                obstacle.update();

                if (obstacle.getCurrentSize() <= hexagonSize) {
                    iterator.remove();
                    obstacleCreated = false;
                }

                if (checkCollision(obstacle)) {
                    gameOver = true;
                    timer.stop();
                    repaint();
                    break;
                }
            }

            frameCounter++;

            if (frameCounter >= colorChangeInterval) {
                generateRandomColor();
                frameCounter = 0;
                setBackground(randomColor);
            }

            repaint();
        }
    }

    private boolean checkCollision(Obstacle obstacle) {
        double playerDistanceFromCenter = hexagonSize;

        double playerAngle = player.getAngle();
        int sector = (int) Math.floor(playerAngle / (Math.PI / 3));
        sector = (sector % 6 + 6) % 6;

        double obstacleSize = obstacle.getCurrentSize();
        double angle1 = 2 * Math.PI / 6 * sector;
        double angle2 = 2 * Math.PI / 6 * (sector + 1);

        double x1 = centerX + (obstacleSize) * Math.cos(angle1);
        double y1 = centerY + (obstacleSize) * Math.sin(angle1);
        double x2 = centerX + (obstacleSize) * Math.cos(angle2);
        double y2 = centerY + (obstacleSize) * Math.sin(angle2);

        double x1Offset = centerX + (obstacleSize + obstacle.getBorderWidth()) * Math.cos(angle1);
        double y1Offset = centerY + (obstacleSize + obstacle.getBorderWidth()) * Math.sin(angle1);
        double x2Offset = centerX + (obstacleSize + obstacle.getBorderWidth()) * Math.cos(angle2);
        double y2Offset = centerY + (obstacleSize + obstacle.getBorderWidth()) * Math.sin(angle2);

        double playerX = player.getX();
        double playerY = player.getY();

        boolean inside = isInside(playerX, playerY, new double[]{x1, x2, x2Offset, x1Offset}, new double[]{y1, y2, y2Offset, y1Offset});

        return inside;
    }

    private boolean isInside(double x, double y, double[] polyX, double[] polyY) {
        int n = polyX.length;
        boolean inside = false;
        for (int i = 0, j = n - 1; i < n; j = i++) {
            if (((polyY[i] > y) != (polyY[j] > y)) &&
                    (x < (polyX[j] - polyX[i]) * (y - polyY[i]) / (polyY[j] - polyY[i]) + polyX[i])) {
                inside = !inside;
            }
        }
        return inside;
    }

    private void addNewObstacle() {
        if (obstacles.isEmpty() || obstacles.get(obstacles.size() - 1).getInitialSize() - obstacles.get(obstacles.size() - 1).getCurrentSize() > obstacleSpacing) {
            centerX = getWidth() / 2;
            centerY = getHeight() / 2;

            int initialSize = 400;
            int borderWidth = 20;
            double shrinkRate = 2;
            double speed = 1;
            Color color = Color.WHITE;

            int sector = random.nextInt(6);
            double angle = 2 * Math.PI / 6 * sector;

            int obstacleType = random.nextInt(3) + 1;

            int x = centerX;
            int y = centerY;

            Obstacle obstacle = new Obstacle(x, y, initialSize, borderWidth, shrinkRate, speed, color, angle, obstacleType);
            obstacles.add(obstacle);
            obstacleCreated = true;
        }
    }

    private void generateRandomColor() {
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        randomColor = new Color(red, green, blue);
    }
}
