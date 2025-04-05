import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class Obstacle {
    private int x;
    private int y;
    private int initialSize;
    private double currentSize;
    private int borderWidth;
    private double shrinkRate;
    private double speed;
    private Color color;
    private double rotationAngle;
    private int obstacleType;
    private boolean[] sides;

    public Obstacle(int x, int y, int initialSize, int borderWidth, double shrinkRate, double speed, Color color, double rotationAngle, int obstacleType) {
        this.x = x;
        this.y = y;
        this.initialSize = initialSize;
        this.currentSize = initialSize;
        this.borderWidth = borderWidth;
        this.shrinkRate = shrinkRate;
        this.speed = speed;
        this.color = color;
        this.rotationAngle = rotationAngle;
        this.obstacleType = obstacleType;
        this.sides = new boolean[6];
        generateSides();
    }

    private void generateSides() {
        Random random = new Random();
        switch (obstacleType) {
            case 1:
                for (int i = 0; i < 6; i++) {
                    sides[i] = (i % 2 == 0);
                }
                break;
            case 2:
                int missingSide = random.nextInt(6);
                for (int i = 0; i < 6; i++) {
                    sides[i] = (i != missingSide);
                }
                break;
            case 3:
                int count = 0;
                for (int i = 0; i < 6; i++) {
                    sides[i] = random.nextBoolean();
                    if (sides[i]) {
                        count++;
                    }
                }
                if (count == 0) {
                    sides[random.nextInt(6)] = true;
                }
                break;
        }
    }

    public void draw(Graphics2D g2d) {
        AffineTransform originalTransform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.rotate(rotationAngle);
        g2d.translate(-x, -y);

        int numPoints = 6;
        for (int i = 0; i < numPoints; i++) {
            if (sides[i]) {
                drawTrapezoid(g2d, x, y, (int) currentSize, borderWidth, i);
            }
        }

        g2d.setTransform(originalTransform);
    }

    private void drawTrapezoid(Graphics2D g2d, int centerX, int centerY, int size, int borderWidth, int sideIndex) {
        double angle1 = 2 * Math.PI / 6 * sideIndex;
        double angle2 = 2 * Math.PI / 6 * (sideIndex + 1);

        int x1 = centerX + (int) (size * Math.cos(angle1));
        int y1 = centerY + (int) (size * Math.sin(angle1));
        int x2 = centerX + (int) (size * Math.cos(angle2));
        int y2 = centerY + (int) (size * Math.sin(angle2));


        int offset = borderWidth;
        int x1Offset = centerX + (int) ((size + offset) * Math.cos(angle1));
        int y1Offset = centerY + (int) ((size + offset) * Math.sin(angle1));
        int x2Offset = centerX + (int) ((size + offset) * Math.cos(angle2));
        int y2Offset = centerY + (int) ((size + offset) * Math.sin(angle2));


        int[] xPoints = {x1, x2, x2Offset, x1Offset};
        int[] yPoints = {y1, y2, y2Offset, y1Offset};

        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, 4);
    }

    public void update() {
        currentSize -= shrinkRate;
        if (currentSize < 0) {
            currentSize = 0;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(double currentSize) {
        this.currentSize = currentSize;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public double getShrinkRate() {
        return shrinkRate;
    }

    public void setShrinkRate(double shrinkRate) {
        this.shrinkRate = shrinkRate;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getObstacleType() {
        return obstacleType;
    }

    public void setObstacleType(int obstacleType) {
        this.obstacleType = obstacleType;
    }
}
