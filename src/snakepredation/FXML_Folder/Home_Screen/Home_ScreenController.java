/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package snakepredation.FXML_Folder.Home_Screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import snakepredation.FXML_Folder.Play_Screen.Play_ScreenController;
import snakepredation.Food;
import snakepredation.GameBoard;
import snakepredation.Snake;
import snakepredation.SnakePredation;

/**
 * FXML Controller class
 *
 * @author duyth
 */
public class Home_ScreenController implements Initializable {

    @FXML
    private ImageView background_Home;
    @FXML
    private Button startBtn_Home;
    @FXML
    private Button ratingBtn_Home;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void MoveTo_PlayScreen(MouseEvent event) throws Exception {

        // Load file fxml của Play_Screen -> scene builder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/snakepredation/FXML_Folder/Play_Screen/Play_Screen.fxml"));
        Parent root = loader.load();

        // Lấy file controller của Play_Screen
        Play_ScreenController play_Controller = loader.getController();

        Scene scene = new Scene(root);
        // Lấy file CSS của Play_Screen
        scene.getStylesheets().add(getClass().getResource("/snakepredation/FXML_Folder/Play_Screen/Play_Screen.css").toExternalForm());

        // Lấy primary stage từ SnakePredation.java
        Stage play_Stage = SnakePredation.getPrimaryStage();
        play_Stage.setScene(scene);
        play_Stage.show();

        // Set max width/height
        play_Stage.setMinWidth(play_Stage.getWidth());
        play_Stage.setMinHeight(play_Stage.getHeight());

        // Lấy canvas background ra từ Play_Screen controller
        Canvas bgSnake_Canvas = play_Controller.getBg__Snake();

        // Khởi tạo GameBoard
        GameBoard gameBoard = new GameBoard(700, 700);
        gameBoard.setGc(bgSnake_Canvas.getGraphicsContext2D()); // Set cho Canvas = GraphicsContext2D

        // Khởi tạo vị trí Snake
        Snake snake = new Snake(5);

        // Khởi tạo thức ăn và màu thức ăn
        Food food = gameBoard.GenerateRandomFood(snake);

        // Handle Event for Snakeee move
        root.getScene().setOnKeyPressed(e -> snake.HandeleDirection(e));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(snake.getSpeed()), e -> play_Controller.run(gameBoard, snake, food)));
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
        play_Controller.setTimeline(timeline);
        play_Controller.run(gameBoard, snake, food);
    }

    @FXML
    private void MoveTo_RatingScreen(MouseEvent event) {
        System.out.println("ok");

    }

}
