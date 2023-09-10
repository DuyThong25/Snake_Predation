package snakepredation.FXML_Folder.TwoPlayerMode_Screen;

import java.awt.Point;
import java.net.URL;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import snakepredation.Food;
import snakepredation.GameBoard;
import snakepredation.Snake;

public class TwoPlayerMode_ScreenController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private StackPane stackPane;
    private Label handleScores;
    @FXML
    private AnchorPane HandleScoresLabel;
    @FXML
    private StackPane stackPane_Canvas;
    @FXML
    private Canvas bg__Snake;
    @FXML
    private FlowPane GameOver_FlowPane;
    @FXML
    private Label gameoverLabel;
    @FXML
    private Label totalScoresLabel;
    @FXML
    private Button homeBtn;
    @FXML
    private Button restartBtn;
    @FXML
    private FlowPane Pause_FlowPane;
    @FXML
    private Button homeBtn1;
    @FXML
    private Button restartBtn1;
    @FXML
    private Button continueBtn;
    @FXML
    private ImageView pauseBtn;

    private boolean isPause = false;
    private Snake snake1;
    private Snake snake2;
    private Food food;
    private GameBoard gameBoard;
    @FXML
    private Label handleScores1;
    @FXML
    private Label handleScores2;

    public void setSnake1(Snake snake1) {
        this.snake1 = snake1;
    }

    public void setSnake2(Snake snake2) {
        this.snake2 = snake2;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
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
        // TODO
    }

    // Khởi tạo Timeline và bắt đầu game chế độ 2 người chơi
    public void startGameFor2Player(GameBoard gameBoard, Snake snake1, Snake snake2, Food food) {
        this.setSnake1(snake1);
        this.setSnake2(snake2);
        this.setFood(food);
        this.setGameBoard(gameBoard);

        // Khởi tạo timeline và đặt vòng lặp game
        timeline = new Timeline(new KeyFrame(Duration.millis(snake1.getSpeed()), e -> runFor2Player()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // Hàm xử lý run cho chế độ 2 người chơi
    public void runFor2Player() {
        // Kiểm tra game over không -> rắn còn sống? 
        if (checkGameOverFor2Player(this.gameBoard)) {
            this.timeline.stop();
            return;
        }
        this.gameBoard.DrawBackground();
//        setHandleScoresLabel("Snake 1: " + this.snake1.getScores());
        setHandleScoresLabel("Snake 2: " + this.snake2.getScores());

        // Kiểm tra mồi
        if (this.food.isExists() == false) {
            this.food.resetFoodFor2Player(this.gameBoard, this.food, this.snake1, this.snake2);
        } else {
            this.food.DrawFood(this.gameBoard);
        }
        this.snake1.DrawSnake(this.gameBoard, this.gameBoard.getGc());
        this.snake2.DrawSnake(this.gameBoard, this.gameBoard.getGc());

        this.snake1.FindPreviousPosition(this.gameBoard.getGc(), this.gameBoard);
        this.snake2.FindPreviousPosition(this.gameBoard.getGc(), this.gameBoard);

//        HandleSnakeMoveFor2Player(this.gameBoard, this.food, this.snake1, this.snake2);
        this.snake1.HandleSnakeMove(this.gameBoard, this.food);
        this.snake2.HandleSnakeMove(this.gameBoard, this.food);

        // Kiểm tra snake 1 còn sống không
        if (snake1.isSnakeAlive(gameBoard) == true) {
            snake1.setIsAlive(true);
        } else {
            snake1.setIsAlive(false);
        }
        // Kiểm tra snake 2 còn sống không
        if (snake2.isSnakeAlive(gameBoard) == true) {
            snake2.setIsAlive(true);
        } else {
            snake2.setIsAlive(false);
        }

        // Kiểm tra ran an moi chưa
        if (snake1.isSnakeEat(food)) {
            snake1.setScores(snake1.getScores() + 5);
            snake1.getSnakeBody().add(new Point(-1, -1));
            food.setExists(false);
        } else if (snake2.isSnakeEat(food)) {// Kiểm tra ran 2 an moi chưa
            snake2.setScores(snake2.getScores() + 5);
            snake2.getSnakeBody().add(new Point(-1, -1));
            food.setExists(false);
        } else {
            food.setExists(true);
        }
    }

    // Kiểm tra rắn còn sống trong chế độ 2 người chơi
    public boolean checkGameOverFor2Player(GameBoard gameBoard) {
        if (this.snake1.isIsAlive() == false || this.snake2.isIsAlive() == false) {
            // Xét flow pane
            this.GameOver_FlowPane.setVisible(true);
            // Set label
            setlabelForGameOver(true);
            return true;
        }
        // Kiểm tra 2 con rắn va nhau
        // Kiểm tra snake 1 bị rắn 2 va vào người không
        for (int i = 1; i < this.snake1.getBody().size(); i++) {
            if (this.snake2.getHeadPosition().x == this.snake1.getBody().get(i).getX() && this.snake2.getHeadPosition().y == this.snake1.getBody().get(i).getY()) {
                return true;
            }
        }
        // Kiểm tra snake 2 bị snake 1 va vào người không
        for (int i = 1; i < this.snake2.getBody().size(); i++) {
            if (this.snake1.getHeadPosition().x == this.snake2.getBody().get(i).getX() && this.snake1.getHeadPosition().y == this.snake2.getBody().get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    // Xét label cho game over
    public void setlabelForGameOver(boolean check) {
        // Set label Game Over
        this.gameoverLabel.setVisible(check);
        // Set label Total Scores
        this.totalScoresLabel.setVisible(check);
        this.totalScoresLabel.setText("Total Scores: " + this.snake1.getScores());

        // Hiển thị button reset
        this.restartBtn.setVisible(check);
        // Hiển thị button home
        this.homeBtn.setVisible(check);
        // Set cho button pause 
        this.pauseBtn.setDisable(check);

    }

    @FXML
    private void MouseClick_Home(MouseEvent event) {
    }

    @FXML
    private void MouseClick_Restart(MouseEvent event) {
    }

    @FXML
    private void KeyPress_Continue(KeyEvent event) {
    }

    @FXML
    private void MouseClick_Continue(MouseEvent event) {
    }

    @FXML
    private void MouseClick_Pause(MouseEvent event) {
    }

}
