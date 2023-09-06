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
    private Direction currentDirection = null; // Hướng di chuyển hiện tại
    private Point headPosition; // Tọa độ của đầu con rắn
    private int length; // Chiều dài của con rắn
    private int speed; // Tốc độ di chuyển
    private boolean isAlive; // Trạng thái sống/mất

    public Snake(int snakeLengt) {
        snakeBody = new ArrayList<>();
        for (int i = 0; i < snakeLengt; i++) {
            snakeBody.add(new Point(5, 5));
        }
        this.headPosition = this.snakeBody.get(0);
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

//    public void InitSnake() {
//        // Khởi tạo con rắn có độ dài là 3 tại một vị trí cố định
//        for (int i = 0; i < 3; i++) {
//            this.snakeBody.add(new Point(5, 5));
//        }
//        this.headPosition = this.snakeBody.get(0);
//    }

    public void DrawSnake(GameBoard gameboard, GraphicsContext gc) {
        // Vẽ đầu con rắn
        gc.setFill(Color.web("4674E9"));
        gc.fillRoundRect(this.headPosition.getX() * gameboard.getSQUARE_SIZE(), this.headPosition.getY() * gameboard.getSQUARE_SIZE(),
                gameboard.getSQUARE_SIZE() - 5, gameboard.getSQUARE_SIZE() - 5, 35, 35);

        // Vẽ thân con rắn
        for (int i = 1; i < this.snakeBody.size(); i++) {
            int sizeRect = gameboard.getSQUARE_SIZE() - 5;
            double positionSnale_X = this.snakeBody.get(i).getX() * gameboard.getSQUARE_SIZE();
            double positionSnale_Y = this.snakeBody.get(i).getY() * gameboard.getSQUARE_SIZE();

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
                if (getCurrentDirection() != currentDirection.DOWN) {
                    this.currentDirection = this.currentDirection.UP;
                }
                break;
            case DOWN:
            case S:
                // Xử lý di chuyển xuống
                if (getCurrentDirection() != currentDirection.UP) {
                    this.currentDirection = this.currentDirection.DOWN;
                }
                break;
            case LEFT:
            case A:
                // Xử lý di chuyển qua trái
                if (getCurrentDirection() != currentDirection.RIGHT) {
                    this.currentDirection = this.currentDirection.LEFT;
                }
                break;
            case RIGHT:
            case D:
                // Xử lý di chuyển qua phải
                if (getCurrentDirection() != currentDirection.LEFT) {
                    this.currentDirection = this.currentDirection.RIGHT;
                }
                break;
        }
    }
  // Find and update the snake's previous position
    public void FindPreviousPosition() {
        // Tìm vị trí trước đó của con rắn  
        for (int i = this.snakeBody.size() - 1; i >= 1; i--) {
            /*
                    i-1 (vị trí trước đó) -> vị trí trước đó của con rắn
             */
            this.snakeBody.get(i).x = this.snakeBody.get(i - 1).x;
            this.snakeBody.get(i).y = this.snakeBody.get(i - 1).y;
        }
    }
    public void HandleSnakeMove() {
        
    }

}
