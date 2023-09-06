package snakepredation;

import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Food {

    private static Color RandomColor_FOOD;
    private Point position; // Tọa độ của mồi
    private boolean exists; // Trạng thái tồn tại của mồi

    public Food(Point position) {
        this.position = position;
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
   
}
