package snakepredation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import snakepredation.FXML_Folder.Play_Screen.Play_ScreenController;

public class SnakePredation extends Application {

//    private static Color RandomColor_FOOD;
//    private GraphicsContext gc;
//    private List<Point> snakeBody = new ArrayList();
//    private Point snakeHead;
//    private int foodX;
//    private int foodY;
    // Xác định hướng di chuyển của rắn dựa trên key code
//    Direction direction = null;
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

        // Khởi tạo GameBoard
        GameBoard gameBoard = new GameBoard(700, 700);
        gameBoard.setGc(bgSnake_Canvas.getGraphicsContext2D());
        
        // Khởi tạo Snake
        List<Point> listSnakeBody = new ArrayList<>();
        Snake snake = new Snake(listSnakeBody);
        for (int i = 0; i < 3; i++) {
            listSnakeBody.add(new Point(5, gameBoard.getROWS() / 2));
            snake.setBody(listSnakeBody);
        }

        run(gameBoard, snake);
    }

    // Hàm xử lý run
    private static void run(GameBoard gameBoard, Snake snakeBody) {
        gameBoard.DrawBackground();
        gameBoard.DrawRandomFood(snakeBody);

    }

    // MAIN
    public static void main(String[] args) {
        launch(args);
    }
    // MAIN
}
