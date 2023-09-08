package snakepredation.FXML_Folder.Play_Screen;

import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import snakepredation.Food;
import snakepredation.GameBoard;
import snakepredation.Snake;

public class Play_ScreenController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Canvas bg__Snake;
    @FXML
    private StackPane stackPane_Canvas;
    @FXML
    private AnchorPane HandleScoresLabel;
    @FXML
    private Label handleScores;
    private boolean isPause;
    private Timeline timeline;

        
    public boolean isIsPause() {
        return isPause;
    }

    public void setIsPause(boolean isPause) {
        this.isPause = isPause;
    }
    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Canvas getBg__Snake() {
        return bg__Snake;
    }

    public Label getHandleScoresLabel() {
        return handleScores;
    }

    // Phương thức để gán giá trị vào Label
    public void setHandleScoresLabel(String value) {
        handleScores.setText(value);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    // Hàm xử lý run
    public void run(GameBoard gameBoard, Snake snake, Food food) {
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
        setHandleScoresLabel("Scrores: " + snake.getScores());
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
        if (isIsPause()) {
            timeline.pause();
        } else {
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }

    @FXML
    private void MouseClick_Pause(MouseEvent event) {
        this.isPause = true;
    }
}
