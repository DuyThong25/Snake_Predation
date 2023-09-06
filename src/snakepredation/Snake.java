package snakepredation;

import java.awt.Point;
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
    private Direction direction = null; // Hướng di chuyển hiện tại
    private Point headPosition; // Tọa độ của đầu con rắn
    private int length; // Chiều dài của con rắn
    private int speed; // Tốc độ di chuyển
    private boolean isAlive; // Trạng thái sống/mất

    public Snake(List<Point> body) {
        this.snakeBody = body;
    }

    public List<Point> getBody() {
        return snakeBody;
    }

    public void setBody(List<Point> body) {
        this.snakeBody = body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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

    public void CreateSnake() {
        for (int i = 0; i < 3; i++) {
            this.snakeBody.add(new Point(5, 5));
        }
        this.headPosition = this.snakeBody.get(0);
    }

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

    public void HandleSnakeMove(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        switch (keyCode) {
            case UP:
            case W:
                System.out.println("Key Code: " + keyCode.toString());
                // Xử lý di chuyển lên ở đây
                break;
            case DOWN:
            case S:
                System.out.println("Key Code: " + keyCode.toString());
                // Xử lý di chuyển xuống ở đây
                break;
            case LEFT:
            case A:
                System.out.println("Key Code: " + keyCode.toString());
                // Xử lý di chuyển qua trái ở đây
                break;
            case RIGHT:
            case D:
                System.out.println("Key Code: " + keyCode.toString());
                // Xử lý di chuyển qua phải ở đây
                break;
            default:
                // Xử lý các trường hợp khác nếu cần
                break;
        }
    }

}
