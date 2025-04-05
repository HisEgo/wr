import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private Hexagon hexagon;
    private int hexagonSize;
    private Player player;
    private List<Obstacle> obstacles;
    private Random random;
    private int centerX;
    private int centerY;
    private int obstacleSpawnDistance;
    private Timer timer;
    private boolean obstacleCreated = false;
    private int score = 0;


    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

        hexagonSize = 35;
        hexagon = new Hexagon(getWidth() / 2, getHeight() / 2, hexagonSize, 2, Color.WHITE);
        player = new Player(getWidth() / 2, getHeight() / 2, 10);
        obstacles = new ArrayList<>();
        random = new Random();

        obstacleSpawnDistance = hexagonSize * 3;
        timer = new Timer(1000, this);
        timer.start();
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

        // تنظیم موقعیت مانع
        if (!obstacles.isEmpty()) {
            Obstacle obstacle = obstacles.get(0);
            obstacle.setX(centerX);
            obstacle.setY(centerY);
        }

        drawSectors(g2d, centerX, centerY);

        hexagon.draw(g2d);
        player.draw(g2d);
        drawObstacles(g2d);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + score, 10, 30);
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
        if (e.getSource() == timer) {
            addNewObstacle();
            repaint();
        }
    }

    private void addNewObstacle() {
        if (!obstacleCreated) {
            centerX = getWidth() / 2;
            centerY = getHeight() / 2;

            int outerSize = 300;
            int borderWidth = 20;
            Color color = Color.WHITE;

            int sector = random.nextInt(6);
            double angle = 2 * Math.PI / 6 * sector;

            int x = centerX;
            int y = centerY;

            Obstacle obstacle = new Obstacle(x, y, outerSize, borderWidth, 0, 0, color, angle);
            obstacles.add(obstacle);
            obstacleCreated = true;
        }
    }
}
