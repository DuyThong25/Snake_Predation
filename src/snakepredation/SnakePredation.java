package snakepredation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.skin.TextInputControlSkin.Direction;
import snakepredation.FXML_Folder.Play_Screen.Play_ScreenController;

public class SnakePredation extends Application {

    private static final int WIDTH = 700;
    private static final int HEIGHT = WIDTH;
    private static final int ROWS = 20;
    private static final int COLUMNS = ROWS;
    private static final int SQUARE_SIZE = WIDTH / ROWS;

    private static Color RandomColor_FOOD;
    private GraphicsContext gc;
    private List<Point> snakeBody = new ArrayList();
    private Point snakeHead;
    private int foodX;
    private int foodY;
    // Xác định hướng di chuyển của rắn dựa trên key code
    Direction direction = null;

    
    
    
    
    // MAIN
    public static void main(String[] args) {
        launch(args);
    }
    // MAIN

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Load file fxml -> scene builder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/snakepredation/FXML_Folder/Play_Screen/Play_Screen.fxml"));
        Parent root = loader.load();
        // Lấy file CONTROLLER
        Play_ScreenController controller = loader.getController();

        Scene scene = new Scene(root);
        // Lấy file CSS
        scene.getStylesheets().add(getClass().getResource("/snakepredation/FXML_Folder/Play_Screen/Play_Screen.css").toExternalForm());

        primaryStage.setTitle("Snake Predation");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Set max width/height
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());

        // Kết nối sự kiện keyPressed với phương thức HandleSnakeMove trong controller
        scene.setOnKeyPressed(controller::HandleSnakeMove);

    }
    
    //Tạo màu ngẫu nhiên
    public static Color generateRandomColor() {
        double red = Math.random(); // Giá trị từ 0.0 đến 1.0 cho thành phần màu đỏ
        double green = Math.random(); // Giá trị từ 0.0 đến 1.0 cho thành phần màu xanh lá cây
        double blue = Math.random(); // Giá trị từ 0.0 đến 1.0 cho thành phần màu lam

        // Tạo màu từ các thành phần màu
        Color randomColor = new Color(red, green, blue, 1.0); // 1.0 ở đây đại diện cho độ trong suốt (alpha)
        return randomColor;
    }

}
