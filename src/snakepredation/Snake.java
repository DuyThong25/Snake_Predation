
package snakepredation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Snake {
    
    private List<Point> snakeBody = new ArrayList<>();
    private Point snakeHead = snakeBody.get(0);

    // Constructor
    public Snake(Point initialPosition) {
        this.snakeBody.add(initialPosition);
    }
    

    public List<Point> getSnakeBody() {
        return snakeBody;
    }

    public void setSnakeBody(List<Point> snakeBody) {
        this.snakeBody = snakeBody;
    }

    public Point getSnakeHead() {
        return snakeHead;
    }

    public void setSnakeHead(Point snakeHead) {
        this.snakeHead = snakeHead;
    }
    
}
