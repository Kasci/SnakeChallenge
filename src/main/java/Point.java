import java.util.Random;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public Point() {
        Random r = new Random();
        x=r.nextInt(Game.WIDTH);
        y=r.nextInt(Game.HEIGHT);
    }

    public Point clone() {
        return new Point(this.x, this.y);
    }
}
