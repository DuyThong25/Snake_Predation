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
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import snakepredation.FXML_Folder.Play_Screen.Play_ScreenController;

public class SnakePredation extends Application {

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

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(snake.getSpeed()), e -> run(gameBoard, snake, food, controller)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // Hàm xử lý run
    private void run(GameBoard gameBoard, Snake snake, Food food, Play_ScreenController controller) {
        if (snake.isIsAlive() == false) {
            gameBoard.getGc().setTextAlign(TextAlignment.CENTER);

            // Vẽ văn bản "Game Over!!!" với kích thước font 70 và màu đỏ
            gameBoard.getGc().setFill(Color.RED);
            gameBoard.getGc().setFont(new Font("Digital-7", 80));
            gameBoard.getGc().fillText("Game Over!!!", gameBoard.getWidth() / 2, gameBoard.getHeight() / 2);

            // Vẽ văn bản "Total Scores" với kích thước font 20 và màu trắng
            gameBoard.getGc().setFill(Color.WHITE);
            gameBoard.getGc().setFont(new Font("Digital-7", 30));
            gameBoard.getGc().fillText("Total Scores: " + snake.getScores(), gameBoard.getWidth() / 2, gameBoard.getHeight() / 2 + 50);
            return;
        }
        controller.setHandleScoresLabel("Scrores: " + snake.getScores());
        gameBoard.DrawBackground();

        // Kiểm tra mồi
        if (food.isExists() == false) {
            Point newPoint = gameBoard.GenerateRandomFood(snake).getPosition();
            food.setPosition(newPoint);
        } else {
            food.DrawFood(gameBoard);
        }
        snake.DrawSnake(gameBoard, gameBoard.getGc());

        snake.FindPreviousPosition(gameBoard.getGc(), gameBoard);
        snake.HandleSnakeMove(gameBoard, food, snake);

    }

    // MAIN
    public static void main(String[] args) {
        launch(args);
    }
    // MAIN
}
