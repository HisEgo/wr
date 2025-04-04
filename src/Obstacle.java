import java.awt.*;

class Obstacle {
    private int x, y;
    private int width, height;
    private double speed;

    public Obstacle(int x, int y, int width, int height, double speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    public void move() {
        //
    }
}