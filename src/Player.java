import java.awt.*;

class Player {
    private int x, y, size;
    private double angle = 0;
    private double rotationSpeed = 0.05;
    private int distanceFromCenter = 40;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        int playerX = x + (int) ((size + distanceFromCenter) * Math.cos(angle));
        int playerY = y + (int) ((size + distanceFromCenter) * Math.sin(angle));

        int[] xPoints = {
                playerX + (int) (size / 2 * Math.cos(angle)),
                playerX + (int) (size / 2 * Math.cos(angle + 2 * Math.PI / 3)),
                playerX + (int) (size / 2 * Math.cos(angle - 2 * Math.PI / 3))
        };
        int[] yPoints = {
                playerY + (int) (size / 2 * Math.sin(angle)),
                playerY + (int) (size / 2 * Math.sin(angle + 2 * Math.PI / 3)),
                playerY + (int) (size / 2 * Math.sin(angle - 2 * Math.PI / 3))
        };
        g.fillPolygon(xPoints, yPoints, 3);
    }

    public void rotateLeft() {
        angle -= rotationSpeed;
    }

    public void rotateRight() {
        angle += rotationSpeed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
