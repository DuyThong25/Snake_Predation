package snakepredation.FXML_Folder.Play_Screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.ESCAPE;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import snakepredation.Food;
import snakepredation.GameBoard;
import snakepredation.ScreenUtil;
import snakepredation.Snake;
import snakepredation.SnakePredation;

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
    @FXML
    private Button homeBtn1;
    @FXML
    private Button restartBtn1;
    @FXML
    private FlowPane GameOver_FlowPane;
    @FXML
    private FlowPane Pause_FlowPane;

    private boolean isPause = false;
    private Snake snake1;
    private Snake snake2;

    private Food food;
    private GameBoard gameBoard;

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

    // Khởi tạo Timeline và bắt đầu game chế độ 1 người chơi
    public void startGameFor1Player(GameBoard gameBoard, Snake snake1, Food food) {
        this.setSnake1(snake1);
        this.setFood(food);
        this.setGameBoard(gameBoard);

        // Khởi tạo timeline và đặt vòng lặp game
        timeline = new Timeline(new KeyFrame(Duration.millis(snake1.getSpeed()), e -> runFor1Player()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // Khởi tạo Timeline và bắt đầu game chế độ 2 người chơi
    public void startGameFor2Player(GameBoard gameBoard, Snake snake1, Snake snake2, Food food) {
        this.setSnake1(snake1);
        this.setSnake2(snake2);

        this.setFood(food);
        this.setGameBoard(gameBoard);

        // Khởi tạo timeline và đặt vòng lặp game
        timeline = new Timeline(new KeyFrame(Duration.millis(snake1.getSpeed()), e -> runFor1Player()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // Hàm xử lý run cho chế độ 1 người chơi
    public void runFor1Player() {
        // Kiểm tra game over không -> rắn còn sống? 
        if (checkGameOverFor1Player(this.gameBoard)) {
            return;
        }
        this.gameBoard.DrawBackground();
        setHandleScoresLabel("Scrores: " + this.snake1.getScores());

        // Kiểm tra mồi
        if (this.food.isExists() == false) {
            this.food.resetFood(this.gameBoard, this.snake1, this.food);
        } else {
            this.food.DrawFood(this.gameBoard);
        }
        this.snake1.DrawSnake(this.gameBoard, this.gameBoard.getGc());

        this.snake1.FindPreviousPosition(this.gameBoard.getGc(), this.gameBoard);
        this.snake1.HandleSnakeMove(this.gameBoard, this.food, this.snake1);
    }

    // Hàm xử lý run cho chế độ 2 người chơi
    public void runFor2Player() {
        // Kiểm tra game over không -> rắn còn sống? 
        if (checkGameOverFor2Player(this.gameBoard)) {
            return;
        }
        this.gameBoard.DrawBackground();
        setHandleScoresLabel("Snake 1: " + this.snake1.getScores());
        setHandleScoresLabel("Snake 2: " + this.snake2.getScores());

        // Kiểm tra mồi
        if (this.food.isExists() == false) {
            this.food.resetFood(this.gameBoard, this.snake1, this.food);
        } else {
            this.food.DrawFood(this.gameBoard);
        }
        this.snake1.DrawSnake(this.gameBoard, this.gameBoard.getGc());

        this.snake1.FindPreviousPosition(this.gameBoard.getGc(), this.gameBoard);
        this.snake1.HandleSnakeMove(this.gameBoard, this.food, this.snake1);
    }

    // Nhấn vào button pause
    @FXML
    private void MouseClick_Pause(MouseEvent event) {
        this.isPause = true;
        // Xét label
        Pause_FlowPane.setVisible(true);
        continueBtn.setVisible(true);
        homeBtn1.setVisible(true);
        restartBtn1.setVisible(true);

        // Xét timeline
        this.timeline.pause();
    }

    // Nhấn vào button continue
    @FXML
    private void MouseClick_Continue(MouseEvent event) {
        this.isPause = false;
        // Xét label
        Pause_FlowPane.setVisible(false);
        continueBtn.setVisible(false);
        homeBtn1.setVisible(false);
        restartBtn1.setVisible(false);

        // Xét timeline
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    // Kiểm tra rắn còn sống trong chế độ 1 người chơi
    public boolean checkGameOverFor1Player(GameBoard gameBoard) {
        if (this.snake1.isIsAlive() == false) {
            // Xét flow pane
            this.GameOver_FlowPane.setVisible(true);
            // Set label
            setlabelForGameOver(true);
            return true;
        }
        return false;
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

    // Trở về Home Screen
    @FXML
    private void MouseClick_Home(MouseEvent event) throws IOException {
        //Load file fxml của Play_Screen -> scene builder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/snakepredation/FXML_Folder/Home_Screen/Home_Screen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        // Lấy file CSS
        scene.getStylesheets().add(getClass().getResource("/snakepredation/FXML_Folder/Home_Screen/Home_Screen.css").toExternalForm());

        // Lấy primary stage từ SnakePredation.java
        Stage home_Stage = SnakePredation.getPrimaryStage();
        home_Stage.setScene(scene);
        home_Stage.show();

        // Set cho nằm giữa màn hình
        ScreenUtil.centerScreen(home_Stage);

        // Set max width/height
        home_Stage.setMinWidth(home_Stage.getWidth());
        home_Stage.setMinHeight(home_Stage.getHeight());
    }

    // Restar game
    @FXML
    private void MouseClick_Restart(MouseEvent event) {

        // Xét flow pane
        this.Pause_FlowPane.setVisible(false);
        // Set label
        setlabelForGameOver(false);

        // Dừng hẳn time line
        this.timeline.stop();
        // Tạo lại rắn và handle move cho rắn
        Snake snakeReset = new Snake(2, 5, 5);
        stackPane_Canvas.getScene().setOnKeyPressed(e -> snakeReset.HandeleDirectionFor1Player(e));
        // Tạo lại thức ăn
        this.food.resetFood(this.gameBoard, snakeReset, this.food);
        // Chạy lại game
        startGameFor1Player(this.gameBoard, snakeReset, this.food);
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

    // Nút nhấn tiếp tục game
    @FXML
    private void KeyPress_Continue(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        switch (keyCode) {
            case ESCAPE:
                this.isPause = false;
                // Xét label
                Pause_FlowPane.setVisible(false);
                continueBtn.setVisible(false);
                homeBtn1.setVisible(false);
                restartBtn1.setVisible(false);

                // Xét timeline
                this.timeline.setCycleCount(Animation.INDEFINITE);
                this.timeline.play();
                break;
        }
    }

}
