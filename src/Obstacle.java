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
        int[] xPoints = new int[numPoints];
        int[] yPoints = new int[numPoints];
        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI / numPoints * i;
            xPoints[i] = x + (int) (currentSize * Math.cos(angle));
            yPoints[i] = y + (int) (currentSize * Math.sin(angle));
        }

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(borderWidth));
        for (int i = 0; i < numPoints; i++) {
            if (sides[i]) {
                g2d.drawLine(xPoints[i], yPoints[i], xPoints[(i + 1) % numPoints], yPoints[(i + 1) % numPoints]);
            }
        }

        g2d.setTransform(originalTransform);
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

    public boolean[] getSides() {
        return sides;
    }

    public void setSides(boolean[] sides) {
        this.sides = sides;
    }
}
