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
        player.setX(centerX);
        player.setY(centerY);
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

        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i;
            int x = centerX + (int) (2000 * Math.cos(angle));
            int y = centerY + (int) (2000 * Math.sin(angle));
            g2d.drawLine(centerX, centerY, x, y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
            }

            repaint();
        }
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
}
