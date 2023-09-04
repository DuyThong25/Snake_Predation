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
import javafx.scene.canvas.Canvas;
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
//    private List<Point> snakeBody = new ArrayList();
//    private Point snakeHead;
//    private int foodX;
//    private int foodY;
    // Xác định hướng di chuyển của rắn dựa trên key code
    Direction direction = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Load file fxml -> scene builder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/snakepredation/FXML_Folder/Play_Screen/Play_Screen.fxml"));
        Parent root = loader.load();
        // Lấy file controller Play_Screen
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
        
        // Lấy canvas ra từ controller
        Canvas bgSnake_Canvas = controller.getBg__Snake();
        
        gc = bgSnake_Canvas.getGraphicsContext2D();
        
        run(gc);
    }

    // Hàm xử lý run
    private static void run(GraphicsContext gc) {
        DrawBackground(gc);
    }

    // Draw background
    private static void DrawBackground(GraphicsContext gc) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("AAD751"));
                } else {
                    gc.setFill(Color.web("A2D149"));
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
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

    // MAIN
    public static void main(String[] args) {
        launch(args);
    }
    // MAIN
}
