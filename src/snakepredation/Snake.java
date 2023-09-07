package snakepredation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.skin.TextInputControlSkin.Direction;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.W;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Snake {

    private List<Point> snakeBody; // Danh sách các phần của con rắn
    private Direction currentDirection;// Hướng di chuyển hiện tại
    private Point headPosition; // Tọa độ của đầu con rắn
    private int length; // Chiều dài của con rắn
    private int speed; // Tốc độ di chuyển
    private boolean isAlive; // Trạng thái sống/mất
    private int scores = 0;
    public Snake(int snakeLength) {
        snakeBody = new ArrayList<>();
        for (int i = 0; i < snakeLength; i++) {
            snakeBody.add(new Point(5, 5));
        }
        this.headPosition = this.snakeBody.get(0);
        this.currentDirection = Direction.RIGHT;
        this.length = snakeBody.size();
        this.isAlive = true; // Còn sống
        this.speed = 130;
    }

    public List<Point> getBody() {
        return snakeBody;
    }

    public void setBody(List<Point> body) {
        this.snakeBody = body;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Point getHeadPosition() {
        return headPosition;
    }

    public void setHeadPosition(Point headPosition) {
        this.headPosition = headPosition;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public List<Point> getSnakeBody() {
        return snakeBody;
    }

    public void setSnakeBody(List<Point> snakeBody) {
        this.snakeBody = snakeBody;
    }

    public void DrawSnake(GameBoard gameboard, GraphicsContext gc) {
        // Vẽ đầu con rắn
        gc.setFill(Color.web("4674E9"));
        gc.fillRoundRect(this.headPosition.getX() * gameboard.getSQUARE_SIZE(), this.headPosition.getY() * gameboard.getSQUARE_SIZE(),
                gameboard.getSQUARE_SIZE() - 5, gameboard.getSQUARE_SIZE() - 5, 35, 35);

        // Vẽ thân con rắn
        for (int i = 1; i < this.snakeBody.size(); i++) {
            int sizeRect = gameboard.getSQUARE_SIZE() - 2;
            double positionSnale_X = this.snakeBody.get(i).getX() * gameboard.getSQUARE_SIZE();
            double positionSnale_Y = this.snakeBody.get(i).getY() * gameboard.getSQUARE_SIZE();
            gc.setFill(Color.BLACK);
            gc.fillRoundRect(positionSnale_X, positionSnale_Y, sizeRect, sizeRect, 20, 20);
        }
    }

    // Handle Event của người dùng và cập nhật biến currentDirection
    public void HandeleDirection(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        switch (keyCode) {
            case UP:
            case W:
                // Xử lý di chuyển lên
                if (this.currentDirection != Direction.DOWN) {
                    this.currentDirection = Direction.UP;
                }
                break;
            case DOWN:
            case S:
                // Xử lý di chuyển xuống
                if (this.currentDirection != Direction.UP) {
                    this.currentDirection = Direction.DOWN;
                }
                break;
            case LEFT:
            case A:
                // Xử lý di chuyển qua trái
                if (this.currentDirection != Direction.RIGHT) {
                    this.currentDirection = Direction.LEFT;
                }
                break;
            case RIGHT:
            case D:
                // Xử lý di chuyển qua phải
                if (this.currentDirection != Direction.LEFT) {
                    this.currentDirection = Direction.RIGHT;
                }
                break;
        }
    }
    // Find and update the snake's previous position

    public void FindPreviousPosition(GraphicsContext gc, GameBoard gameboard) {
        // Tìm vị trí trước đó của con rắn  
        for (int i = this.snakeBody.size() - 1; i >= 1; i--) {
            /*
                    i-1 (vị trí trước đó) -> vị trí trước đó của con rắn
             */

            this.snakeBody.get(i).x = this.snakeBody.get(i - 1).x;
            this.snakeBody.get(i).y = this.snakeBody.get(i - 1).y;
        }
    }

    public void HandleSnakeMove(GameBoard gameboard, Food food, Snake snake) {
        switch (this.currentDirection) {
            case RIGHT:
                // Xử lý khi currentDirection là RIGHT
                this.headPosition.x++;
                break;
            case LEFT:
                // Xử lý khi currentDirection là LEFT
                this.headPosition.x--;
                break;
            case UP:
                // Xử lý khi currentDirection là UP
                this.headPosition.y--;
                break;
            case DOWN:
                // Xử lý khi currentDirection là DOWN
                this.headPosition.y++;
                break;
        }

        // Kiểm tra còn sống không
        if (isSnakeAlive(gameboard) == true) {
            this.isAlive = true;
        } else {
            this.isAlive = false;
        }

        // Kiểm tra ran an moi chưa
        if (isSnakeEat(food)) {
            this.snakeBody.add(new Point(-1, -1));
            food.setExists(false);         
            System.out.println("size" + this.snakeBody.size());
        } else {
            food.setExists(true);
        }
    }

    public boolean isSnakeAlive(GameBoard gameboard) {
        int currentWidthX = this.headPosition.x * gameboard.getSQUARE_SIZE();
        int currentHeightY = this.headPosition.y * gameboard.getSQUARE_SIZE();
//        System.out.println("cur Width:" + currentWidthX);
//        System.out.println("cur Height:" + currentHeightY);
//        System.out.println("Height:" + gameboard.getHeight());
//        System.out.println("Width:" + gameboard.getWidth());
//        System.out.println("Head X:" + this.headPosition.getX());
//        System.out.println("Head y:" + this.headPosition.getY());

        /* //  Kiểm tra rắn dap dau vao tuong
            || Xét rắn đi qua trái headPosition.getX() < 0
            || Dùng current Height để xét rắn đi xuống dưới đập vào tường currentHeightY >= gameboard.getHeight() - Độ cao nên xét trục y
            || Dùng current Width để xét rắn đi qua phải đập vào tường currentWidthX >= gameboard.getWidth() - Độ dài nên xét trục x
            || Xét rắn đi lên trên đập vào tường headPosition.getY < 0 
         */
        if (this.headPosition.getX() < 0 || currentHeightY >= gameboard.getHeight() || this.headPosition.getY() < 0 || currentWidthX >= gameboard.getWidth()) {
            return false;
        }

        // Kill Myselft
        for (int i = 1; i < this.snakeBody.size(); i++) {
            // Xét rắn tự va vào đuôi
            if (this.headPosition.getX() == this.snakeBody.get(i).getX() && this.headPosition.getY() == this.snakeBody.get(i).getY()) {
                return false;
            }
        }
        return true;
    }

    // Snake eat food 
    public boolean isSnakeEat(Food food) {
        // Xet ran da an tra ve true
        if (this.headPosition.getX() == food.getPosition().getX() && this.headPosition.getY() == food.getPosition().getY()) {
            return true;
        } else {
            return false;
        }
    }
}
