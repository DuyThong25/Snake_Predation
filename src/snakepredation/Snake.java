
package snakepredation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Snake {
    
    private List<Point> snakeBody = new ArrayList<>();
    private Point snakeHead;

    // Constructor
    public Snake(Point initialPosition) {
        this.snakeHead = initialPosition;
        this.snakeBody.add(initialPosition);
    }
    
}
