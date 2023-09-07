package snakepredation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

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

        // Lấy canvas ra từ controller
        Canvas bgSnake_Canvas = controller.getBg__Snake();

        // Khởi tạo GameBoard
        GameBoard gameBoard = new GameBoard(700, 700);
        gameBoard.setGc(bgSnake_Canvas.getGraphicsContext2D());

        // Khởi tạo vị trí Snake
        Snake snake = new Snake(5);
        
        // Khởi tạo thức ăn và màu thức ăn
        Food food = gameBoard.GenerateRandomFood(snake);

        // Handle Event for Snakeee
        scene.setOnKeyPressed(e -> snake.HandeleDirection(e));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(snake.getSpeed()), e -> run(gameBoard, snake, food)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // Hàm xử lý run
    private void run(GameBoard gameBoard, Snake snake, Food food) {
        if (snake.isIsAlive() == false) {
            gameBoard.getGc().setFill(Color.RED);
            gameBoard.getGc().setFont(new Font("Digital-7", 70));
            gameBoard.getGc().fillText("Game Over!!!", gameBoard.getWidth() / 3.5, gameBoard.getHeight() / 2);
            return;
        }

        gameBoard.DrawBackground();
        gameBoard.DrawFood(food);
        snake.DrawSnake(gameBoard, gameBoard.getGc());

        snake.FindPreviousPosition(gameBoard.getGc(), gameBoard);
        snake.HandleSnakeMove(gameBoard);
    }

    // MAIN
    public static void main(String[] args) {
        launch(args);
    }
    // MAIN
}
