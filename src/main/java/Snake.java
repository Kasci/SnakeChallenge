import java.util.ArrayDeque;
import java.util.Deque;

public class Snake {
    public Deque<Point> snake;
    public Direction dir = Direction.UP;

    public Snake() {
        snake = new ArrayDeque<>();
        snake.add(new Point());
    }

    public boolean isOn(Point p) {
        Point s = snake.peekFirst();
        return p.x==s.x && p.y==s.y;
    }
}
