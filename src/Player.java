import java.awt.*;

class Player {
    private int x, y, size;
    private double angle = 0;
    private double rotationSpeed = 0.1;
    private int distanceFromCenter = 40;
    private int centerX, centerY;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        int playerX = x;
        int playerY = y;

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

    public double getAngle() {
        return angle;
    }

    public int getDistanceFromCenter() {
        return distanceFromCenter;
    }

    public void setCenter(int centerX, int centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }
}
