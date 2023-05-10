import java.util.ArrayDeque;
import java.util.Deque;

public class Snake {
    private Deque<Point> snake;
    private Direction dir = Direction.UP;

    public Snake() {
        snake = new ArrayDeque<>();
        snake.add(new Point());
    }

    public boolean isOn(Point p) {
        Point s = getSnake().peekFirst();
        return p.x==s.x && p.y==s.y;
    }

    public Deque<Point> getSnake() {
        return snake;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public Point peekHead() {
        return this.snake.peekFirst();
    }

    public void addHead(Point point) {
        this.snake.addFirst(point);
    }

    public void removeTail() {
        this.snake.removeLast();
    }

    public boolean doesColidesWithItself() {
        return snake.stream().skip(1).anyMatch(it -> isOn(it));
    }
}
