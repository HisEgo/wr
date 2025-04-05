import java.awt.*;
import java.awt.geom.AffineTransform;

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

    public Obstacle(int x, int y, int initialSize, int borderWidth, double shrinkRate, double speed, Color color, double rotationAngle) {
        this.x = x;
        this.y = y;
        this.initialSize = initialSize;
        this.currentSize = initialSize;
        this.borderWidth = borderWidth;
        this.shrinkRate = shrinkRate;
        this.speed = speed;
        this.color = color;
        this.rotationAngle = rotationAngle;
    }

    public void draw(Graphics2D g2d) {
        AffineTransform originalTransform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.rotate(rotationAngle);
        g2d.translate(-x, -y);

        Hexagon hexagon = new Hexagon(x, y, (int) currentSize, borderWidth, color);
        hexagon.draw(g2d);

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
}
