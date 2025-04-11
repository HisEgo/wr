import java.awt.*;
import java.awt.geom.Path2D;

public class Hexagon {
    private int x;
    private int y;
    private int size;
    private int borderWidth;
    private Color color;

    private Path2D hexagon;

    public Hexagon(int x, int y, int size, int borderWidth, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.borderWidth = borderWidth;
        this.color = color;
        this.hexagon = new Path2D.Double();
        calculateHexagonPath();
    }

    private void calculateHexagonPath() {
        hexagon.reset();
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i;
            double pointX = x + size * Math.cos(angle);
            double pointY = y + size * Math.sin(angle);
            if (i == 0) {
                hexagon.moveTo(pointX, pointY);
            } else {
                hexagon.lineTo(pointX, pointY);
            }
        }
        hexagon.closePath();
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.draw(hexagon);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        calculateHexagonPath();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        calculateHexagonPath();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        calculateHexagonPath();
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Path2D getPath() {
        return hexagon;
    }
}
