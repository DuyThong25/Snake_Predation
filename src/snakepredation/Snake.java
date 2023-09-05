package snakepredation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.skin.TextInputControlSkin.Direction;

public class Snake {

    private List<Point> body; // Danh sách các phần của con rắn
    private Direction direction = null; // Hướng di chuyển hiện tại
    private Point headPosition; // Tọa độ của đầu con rắn
    private int length; // Chiều dài của con rắn
    private int speed; // Tốc độ di chuyển
    private boolean isAlive; // Trạng thái sống/mất
    
//    // Constructor
//    public Snake(Point initialPosition) {
//        this.body.add(initialPosition);
//    }

    public Snake(List<Point> body) {
        this.body = body;
    }

    public List<Point> getBody() {
        return body;
    }

    public void setBody(List<Point> body) {
        this.body = body;
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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
}
