import java.awt.*;

class Player {
    private int x, y, size;
    private double angle = 0;
    private double rotationSpeed = 0.1;
    private int distanceFromCenter = 54;
    private int centerX, centerY;
    private double[] hexagonX;
    private double[] hexagonY;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        hexagonX = new double[6];
        hexagonY = new double[6];
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);

        calculatePositionOnHexagon();

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

    private void calculatePositionOnHexagon() {
        double angle = getAngle();

        angle = angle % (2 * Math.PI);
        if (angle < 0) {
            angle += 2 * Math.PI;
        }

        int sector = (int) Math.floor(angle / (Math.PI / 3));
        sector = sector % 6;

        double sectorAngle = angle % (Math.PI / 3);
        double t = sectorAngle / (Math.PI / 3);

        int nextSector = (sector + 1) % 6;

        x = (int) (hexagonX[sector] + (hexagonX[nextSector] - hexagonX[sector]) * t);
        y = (int) (hexagonY[sector] + (hexagonY[nextSector] - hexagonY[sector]) * t);
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

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public int getDistanceFromCenter() {
        return distanceFromCenter;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCenter(int centerX, int centerY) {
        this.centerX = centerX;
        this.centerY = centerY;

        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i;
            hexagonX[i] = centerX + distanceFromCenter * Math.cos(angle);
            hexagonY[i] = centerY + distanceFromCenter * Math.sin(angle);
        }
    }

    public int getCurrentSector() {
        double angle = getAngle();
        angle = angle % (2 * Math.PI);
        if (angle < 0) {
            angle += 2 * Math.PI;
        }
        return (int) (angle / (Math.PI / 3));
    }
}
