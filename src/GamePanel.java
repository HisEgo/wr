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
        timer = new Timer(20, this); // کاهش سرعت تایمر برای حرکت نرم‌تر
        timer.start();

        // اضافه کردن key binding برای چرخش به چپ
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke("LEFT"), "rotateLeft");
        am.put("rotateLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.rotateLeft();
                repaint();
            }
        });

        // اضافه کردن key binding برای چرخش به راست
        im.put(KeyStroke.getKeyStroke("RIGHT"), "rotateRight");
        am.put("rotateRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.rotateRight();
                repaint();
            }
        });
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

            // استفاده از Iterator برای حذف موانع
            Iterator<Obstacle> iterator = obstacles.iterator();
            while (iterator.hasNext()) {
                Obstacle obstacle = iterator.next();
                obstacle.update();

                // بررسی برخورد با 6 ضلعی مرکزی
                if (obstacle.getCurrentSize() <= hexagonSize) {
                    iterator.remove(); // حذف مانع اگر به اندازه کافی کوچک شده باشد
                    obstacleCreated = false;
                    score++; // افزایش امتیاز
                }
            }

            repaint();
        }
    }

    private void addNewObstacle() {
        if (!obstacleCreated) {
            centerX = getWidth() / 2;
            centerY = getHeight() / 2;

            int initialSize = 200;
            int borderWidth = 5;
            double shrinkRate = 0.4; // سرعت کوچک شدن
            double speed = 1;
            Color color = Color.WHITE;

            int sector = random.nextInt(6);
            double angle = 2 * Math.PI / 6 * sector;

            int x = centerX;
            int y = centerY;

            Obstacle obstacle = new Obstacle(x, y, initialSize, borderWidth, shrinkRate, speed, color, angle);
            obstacles.add(obstacle);
            obstacleCreated = true;
        }
    }
}
