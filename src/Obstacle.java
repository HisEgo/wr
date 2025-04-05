import java.awt.*;
import java.awt.geom.AffineTransform;

public class Obstacle {
    private int x;
    private int y;
    private int size;
    private int borderWidth;
    private double speedX;
    private double speedY;
    private Color color;
    private double rotationAngle;

    public Obstacle(int x, int y, int size, int borderWidth, double speedX, double speedY, Color color, double rotationAngle) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.borderWidth = borderWidth;
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = color;
        this.rotationAngle = rotationAngle;
    }

    public void draw(Graphics2D g2d) {
        AffineTransform originalTransform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.rotate(rotationAngle);
        g2d.translate(-x, -y);


        Hexagon hexagon = new Hexagon(x, y, size, borderWidth, color);
        hexagon.draw(g2d);

        g2d.setTransform(originalTransform);
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
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
}
