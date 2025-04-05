import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private Hexagon hexagon;
    private Player player;
    private List<Obstacle> obstacles;
    private Timer timer;
    private int score = 0;
    private Random random = new Random();
    private double gameSpeed = 1;

    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        hexagon = new Hexagon(0, 0, 50);
        player = new Player(0, 0, 30);

        obstacles = new ArrayList<>();

        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        repaint();
    }

    private void updateGame() {
        updateObstacles();
        checkCollisions();
        increaseDifficulty();
    }

    private void increaseDifficulty() {
        if (score % 100 == 0) {
            gameSpeed *= 1.05;
        }
    }

    private void checkCollisions() {
        //
    }

    private void updateObstacles() {
        //
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        hexagon.setX(centerX);
        hexagon.setY(centerY);
        player.setX(centerX);
        player.setY(centerY);

        hexagon.draw(g2d);
        player.draw(g2d);
        drawObstacles(g2d);

        drawSectors(g2d, centerX, centerY);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + score, 10, 30);
    }

    private void drawObstacles(Graphics2D g2d) {
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g2d);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.rotateLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.rotateRight();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //
    }

    private void drawSectors(Graphics2D g2d, int centerX, int centerY) {
        g2d.setColor(Color.DARK_GRAY);
        int hexagonSize = hexagon.getSize();

        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i;
            int x = centerX + (int) (1000 * Math.cos(angle));
            int y = centerY + (int) (1000 * Math.sin(angle));
            g2d.drawLine(centerX, centerY, x, y);
        }
    }

    class Hexagon {
        private int x, y, size;

        public Hexagon(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }

        public void draw(Graphics2D g) {
            g.setColor(Color.WHITE);
            int[] xPoints = new int[6];
            int[] yPoints = new int[6];
            for (int i = 0; i < 6; i++) {
                double angle = 2 * Math.PI / 6 * i;
                xPoints[i] = x + (int) (size * Math.cos(angle));
                yPoints[i] = y + (int) (size * Math.sin(angle));
            }
            g.drawPolygon(xPoints, yPoints, 6);
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y){
            this.y = y;
        }

        public int getSize() {
            return size;
        }
    }
}
