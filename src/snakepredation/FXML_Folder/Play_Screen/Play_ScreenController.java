package snakepredation.FXML_Folder.Play_Screen;

import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    private boolean isPause = false;
    private Timeline timeline;
    @FXML
    private Button continueBtn;
    @FXML
    private ImageView pauseBtn;
    @FXML
    private Button restartBtn;
    @FXML
    private Label gameoverLabel;
    @FXML
    private Label totalScoresLabel;

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
        // Kiểm tra có game over không -> rắn còn sống? 
        if (checkGameOver(gameBoard, snake)) {
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
    }

    // Nhấn vào button pause
    @FXML
    private void MouseClick_Pause(MouseEvent event) {
        this.isPause = true;
        continueBtn.setVisible(true);
        this.timeline.pause();
    }

    // Nhấn vào button continue
    @FXML
    private void MouseClick_Continue(MouseEvent event) {
        this.isPause = false;
        this.continueBtn.setVisible(false);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    // Kiểm tra rắn còn sống không
    public boolean checkGameOver(GameBoard gameBoard, Snake snake) {
        if (snake.isIsAlive() == false) {
            // Set label Game Over
            this.gameoverLabel.setVisible(true);
            // Set label Total Scores
            this.totalScoresLabel.setVisible(true);
            this.totalScoresLabel.setText("Total Scores: " + snake.getScores());
            // Hiển thị button reset
            this.restartBtn.setVisible(true);
            
            // Set cho button pause bằng disable
            this.pauseBtn.setDisable(true);
            return true;
        }
        return false;
    }

}
