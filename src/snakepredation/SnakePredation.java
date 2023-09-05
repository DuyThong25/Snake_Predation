package snakepredation;

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
        
        // Khởi tạo gameboard
        GameBoard gameBoard = new GameBoard(950, 950);
        gameBoard.setGc(bgSnake_Canvas.getGraphicsContext2D());
        
        run(gameBoard);
    }

    // Hàm xử lý run
    private static void run(GameBoard gameBoard) {
        gameBoard.DrawBackground(gameBoard.getGc());
    }

    // MAIN
    public static void main(String[] args) {
        launch(args);
    }
    // MAIN
}
