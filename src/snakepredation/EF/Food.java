package snakepredation.EF;

import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import snakepredation.jpa_Model.Snake;

public class Food {

    private static Color RandomColor_FOOD;
    private Point position; // Tọa độ của mồi
    private boolean exists; // Trạng thái tồn tại của mồi

    public Food(Point position) {
        this.position = position;
        this.RandomColor_FOOD = generateRandomColor(); // Tạo ra màu ngẫu nhiên
        this.exists = true; // Moi xuat hien
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Color getRandomColor_FOOD() {
        return RandomColor_FOOD;
    }

    public Color setRandomColor_FOOD(Color RandomColor_FOOD) {
        return this.RandomColor_FOOD = RandomColor_FOOD;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    //Tạo màu thức ăn ngẫu nhiên
    public static Color generateRandomColor() {
        double red = Math.random(); // Giá trị từ 0.0 đến 1.0 cho thành phần màu đỏ
        double green = Math.random(); // Giá trị từ 0.0 đến 1.0 cho thành phần màu xanh lá cây
        double blue = Math.random(); // Giá trị từ 0.0 đến 1.0 cho thành phần màu lam

        // Tạo màu từ các thành phần màu
        Color randomColor = new Color(red, green, blue, 1.0); // 1.0 ở đây đại diện cho độ trong suốt (alpha)
        return randomColor;
    }

    public void DrawFood(GameBoard gameboard, Snake snake) {

        if (this.isExists() == false) {
            resetFood(gameboard, snake);
        } else {
            gameboard.getGc().setFill(getRandomColor_FOOD());
            gameboard.getGc().fillRect(getPosition().getX() * gameboard.getSQUARE_SIZE(),
                    getPosition().getY() * gameboard.getSQUARE_SIZE(),
                    gameboard.getSQUARE_SIZE() - 5, gameboard.getSQUARE_SIZE() - 5);
        }
    }

    public void DrawFood(GameBoard gameboard, Snake snake1, Snake snake2) {
        if (this.isExists() == false) {
            resetFoodFor2Player(gameboard, snake1, snake2);
        } else {
            gameboard.getGc().setFill(getRandomColor_FOOD());
            gameboard.getGc().fillRect(getPosition().getX() * gameboard.getSQUARE_SIZE(), getPosition().getY() * gameboard.getSQUARE_SIZE(),
                    gameboard.getSQUARE_SIZE() - 5, gameboard.getSQUARE_SIZE() - 5);
        }
    }

    public void resetFood(GameBoard gameBoard, Snake snake) {
        Point newPoint = gameBoard.GenerateRandomFood(snake).getPosition();
        setPosition(newPoint);
    }

    public void resetFoodFor2Player(GameBoard gameBoard, Snake snake1, Snake snake2) {
        Point newPoint = gameBoard.GenerateRandomFoodFor2Player(snake1, snake2).getPosition();
        setPosition(newPoint);
    }
//    public void resetFood(GameBoard gameBoard, Snake snake) {
//        Point newPoint = gameBoard.GenerateRandomFood(snake).getPosition();
//        this.setPosition(newPoint);
//    }
//    public void DrawFood(GameBoard gameboard) {
//        gameboard.getGc().setFill(getRandomColor_FOOD());
//        gameboard.getGc().fillRect(getPosition().getX() * gameboard.getSQUARE_SIZE(), getPosition().getY() * gameboard.getSQUARE_SIZE(),
//                gameboard.getSQUARE_SIZE() - 5, gameboard.getSQUARE_SIZE() - 5);
//
//    }

}
