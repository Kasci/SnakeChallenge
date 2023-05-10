import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.time.Instant;

public class Game {
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;

    private Screen screen;
    private boolean running;

    private Snake snake;
    public Point food;
    public Game(Screen screen) {
        this.screen = screen;
        snake = new Snake();
        food = new Point();
    }

    public void start() throws IOException {
        this.running = true;
        loop();
        TextGraphics textGraphics = this.screen.newTextGraphics();
        textGraphics.putString(10,10,"GAME OVER");
        this.screen.refresh();
    }

    private void loop() throws IOException {
        Instant begin = Instant.now();
        Direction dir = Direction.NONE;
        while (running) {
            render();
            Direction d = input();
            System.out.println(d);
            if (d != Direction.NONE) dir = d;
            Instant now = Instant.now();
            if (now.toEpochMilli() - begin.toEpochMilli() > 300) {
                begin = now;
                update(dir);
            }
        }
    }

    private void update(Direction dir) {
        Point p = snake.snake.peekFirst().clone();
        if (dir != Direction.NONE) {
            snake.dir = dir;
        }
        switch (snake.dir) {
            case UP: p.y--; break;
            case DOWN: p.y++; break;
            case LEFT: p.x--; break;
            case RIGHT: p.x++; break;
        }
        p.x =(p.x+WIDTH)%WIDTH;
        p.y =(p.y+HEIGHT)%HEIGHT;
        snake.snake.addFirst(p);
        if (snake.isOn(food)) {
            food = new Point();
        } else {
            snake.snake.removeLast();
        }
        if (snake.snake.stream().skip(1).anyMatch(it -> snake.isOn(it))) {
            this.running = false;
        }
    }

    private Direction input() throws IOException {
        KeyStroke keyStroke = this.screen.pollInput();
        if (keyStroke != null && keyStroke.getKeyType() == KeyType.Character) {
            switch (keyStroke.getCharacter()) {
                case 'w': return Direction.UP;
                case 's': return Direction.DOWN;
                case 'a': return Direction.LEFT;
                case 'd': return Direction.RIGHT;
                default: return Direction.NONE;
            }
        }
        return Direction.NONE;
    }

    private void render() throws IOException {
        this.screen.clear();
        TextGraphics g = this.screen.newTextGraphics();
        g.setCharacter(food.x, food.y, 'o');
        for (Point p: snake.snake) {
            g.setCharacter(p.x, p.y, 'X');
        }
        this.screen.refresh(Screen.RefreshType.DELTA);
    }
}
