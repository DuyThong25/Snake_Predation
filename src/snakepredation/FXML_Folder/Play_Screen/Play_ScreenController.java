package snakepredation.FXML_Folder.Play_Screen;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
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
import javafx.util.Duration;
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
    private Snake snake;
    private Food food;
    private GameBoard gameBoard;

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
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
    @FXML
    private Button homeBtn;

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

    // Khởi tạo Timeline và bắt đầu game
    public void startGame(GameBoard gameBoard, Snake snake, Food food) {
        this.setSnake(snake);
        this.setFood(food);
        this.setGameBoard(gameBoard);

        // Khởi tạo timeline và đặt vòng lặp game
        timeline = new Timeline(new KeyFrame(Duration.millis(snake.getSpeed()), e -> run()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // Hàm xử lý run
    public void run() {
        // Kiểm tra game over không -> rắn còn sống? 
        if (checkGameOver(this.gameBoard, this.snake)) {
            return;
        }
        this.gameBoard.DrawBackground();
        setHandleScoresLabel("Scrores: " + this.snake.getScores());

        // Kiểm tra mồi
        if (this.food.isExists() == false) {
            this.food.resetFood(this.gameBoard, this.snake, this.food);
        } else {
            this.food.DrawFood(this.gameBoard);
        }
        this.snake.DrawSnake(this.gameBoard, this.gameBoard.getGc());

        this.snake.FindPreviousPosition(this.gameBoard.getGc(), this.gameBoard);
        this.snake.HandleSnakeMove(this.gameBoard, this.food, this.snake);
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
            // Set label
            setlabelForGameOver(true);
            // Set cho button pause bằng disable
            return true;
        }
        return false;
    }

    @FXML
    private void MouseClick_Home(MouseEvent event) {

    }

    @FXML
    private void MouseClick_Restart(MouseEvent event) {
        // Set label
        setlabelForGameOver(false);


        // Dừng hẳn time line
        this.timeline.stop();
        // Tạo lại rắn và handle move cho rắn
        Snake snakeReset = new Snake(5);
        stackPane_Canvas.getScene().setOnKeyPressed(e -> snakeReset.HandeleDirection(e));
        // Tạo lại thức ăn
        this.food.resetFood(this.gameBoard, snakeReset, this.food);
        // Chạy lại game
        startGame(this.gameBoard, snakeReset, this.food);
    }

    public void setlabelForGameOver(boolean check) {
        // Set label Game Over
        this.gameoverLabel.setVisible(check);
        // Set label Total Scores
        this.totalScoresLabel.setVisible(check);
        this.totalScoresLabel.setText("Total Scores: " + this.snake.getScores());

        // Hiển thị button reset
        this.restartBtn.setVisible(check);
        // Hiển thị button home
        this.homeBtn.setVisible(check);
        // Set cho button pause 
        this.pauseBtn.setDisable(check);

    }
}
