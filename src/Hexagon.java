import java.awt.*;
import java.awt.geom.Path2D;

public class Hexagon {
    private int x;
    private int y;
    private int size;
    private int borderWidth;
    private Color color;

    public Hexagon(int x, int y, int size, int borderWidth, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.borderWidth = borderWidth;
        this.color = color;
    }

    public void draw(Graphics2D g2d) {
        Path2D hexagon = new Path2D.Double();
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

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.draw(hexagon);
    }

    public void draw2(Graphics2D g2d) {
        Path2D hexagon = new Path2D.Double();
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i;
            double pointX = x + 80 * Math.cos(angle);
            double pointY = y + 80 * Math.sin(angle);
            if (i == 0) {
                hexagon.moveTo(pointX, pointY);
            } else {
                hexagon.lineTo(pointX, pointY);
            }
        }
        hexagon.closePath();

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.draw(hexagon);
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
