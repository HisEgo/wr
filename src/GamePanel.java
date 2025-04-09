
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.awt.geom.AffineTransform;


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
    private double globalRotationAngle = 0;
    private double rotationSpeed = 0.01;

    private int playerSector;
    private boolean paused = false;

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
                if (!paused) {
                    player.rotateLeft();
                    updatePlayerSector();
                    repaint();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("RIGHT"), "rotateRight");
        am.put("rotateRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!paused) {
                    player.rotateRight();
                    updatePlayerSector();
                    repaint();
                }
            }
        });

        // Key listener for pause/resume
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    paused = !paused;
                    System.out.println("Game Paused: " + paused);
                    if (paused) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                    repaint();
                }
            }
        });

        hexagonX = new double[6];
        hexagonY = new double[6];

        updatePlayerSector();
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

        g2d.rotate(globalRotationAngle, centerX, centerY);
        drawSectors(g2d, centerX, centerY);
        hexagon.draw(g2d);

        g2d.rotate(-globalRotationAngle, centerX, centerY);

        g2d.rotate(globalRotationAngle, centerX, centerY);
        player.draw(g2d);
        g2d.rotate(-globalRotationAngle, centerX, centerY);

        drawObstacles(g2d);
        printObstacleSectors();
        printPlayerSector();

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

        if (paused) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            FontMetrics fm = g2d.getFontMetrics();
            String pausedText = "Paused";
            int stringWidth = fm.stringWidth(pausedText);
            int textX = centerX - stringWidth / 2;
            int textY = centerY;
            g2d.drawString(pausedText, textX, textY);
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
            AffineTransform originalTransform = g2d.getTransform();
            g2d.translate(centerX, centerY);
            g2d.rotate(globalRotationAngle);
            g2d.translate(-centerX, -centerY);
            obstacle.draw(g2d);
            g2d.setTransform(originalTransform);
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
        if (gameOver || paused) {
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

                if (checkCollision(obstacles)) {
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

            globalRotationAngle = (globalRotationAngle + rotationSpeed) % (2 * Math.PI);
            updatePlayerSector();
            repaint();
        }
    }


    public boolean checkCollision(List<Obstacle> obstacles) {

        if (obstacles.get(0).getCurrentSize() == player.getDistanceFromCenter()) {
            int playerSector = player.getCurrentSector();
            for (int i = 0; i < obstacles.get(0).getOccupiedSectors().size(); i++) {
                if (playerSector == obstacles.get(0).getOccupiedSectors().get(i)) {
                    return true;
                }
            }
        }
        return false;
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
            // Correct the angle based on the sector
            angle = Math.round(angle / (Math.PI / 3)) * (Math.PI / 3);

            int obstacleType = random.nextInt(3) + 1;

            int x = centerX;
            int y = centerY;

            Obstacle obstacle = new Obstacle(x, y, initialSize, borderWidth, shrinkRate, speed, color, angle, obstacleType);
            obstacles.add(obstacle);
            obstacleCreated = true;
        }
    }

    private void printObstacleSectors() {
        System.out.println("Obstacle Sectors:");
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            List<Integer> sectors = obstacle.getOccupiedSectors();
            System.out.println("Obstacle " + i + " Sectors: " + sectors);
        }
    }

    private void printPlayerSector() {
        int sector = playerSector;
        System.out.println("Player Sector: " + sector);
    }


    private void generateRandomColor() {
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        randomColor = new Color(red, green, blue);
    }

    public void updatePlayerSector() {
        double angle = player.getAngle();
        playerSector = (int) Math.floor(angle / (Math.PI / 3));
        playerSector = (playerSector % 6 + 6) % 6;
    }

}