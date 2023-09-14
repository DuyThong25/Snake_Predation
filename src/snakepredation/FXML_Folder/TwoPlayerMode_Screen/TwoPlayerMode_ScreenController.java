package snakepredation.FXML_Folder.TwoPlayerMode_Screen;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import snakepredation.Food;
import snakepredation.GameBoard;
import snakepredation.Ultil.ScreenUtil;
import snakepredation.Snake;
import snakepredation.SnakePredation;

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
    @FXML
    private Label handleScores1;
    @FXML
    private Label handleScores2;
    @FXML
    private Label totalScoresLabel_1;
    @FXML
    private Label totalScoresLabel_2;
    @FXML
    private Label playerWinLabel;

    private boolean isPause = false;
    private Snake snake1;
    private Snake snake2;
    private Food food;
    private GameBoard gameBoard;

    public Label getTotalScoresLabel_1() {
        return totalScoresLabel_1;
    }

    public void setTotalScoresLabel_1(Label totalScoresLabel_1) {
        this.totalScoresLabel_1 = totalScoresLabel_1;
    }

    public Label getTotalScoresLabel_2() {
        return totalScoresLabel_2;
    }

    public void setTotalScoresLabel_2(String text) {
        this.totalScoresLabel_2.setText(text);
    }

    public Label getPlayerWinLabel() {
        return playerWinLabel;
    }

    public void setPlayerWinLabel(String text) {
        this.playerWinLabel.setText(text);
    }

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

    public Label getHandleScoresLabel1() {
        return handleScores1;
    }

    // Phương thức để gán giá trị vào Label
    public void setHandleScoresLabel1(String value) {
        handleScores1.setText(value);
    }

    public Label getHandleScoresLabel2() {
        return handleScores2;
    }

    // Phương thức để gán giá trị vào Label
    public void setHandleScoresLabel2(String value) {
        handleScores2.setText(value);
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
        setHandleScoresLabel1("PLAYER 1: " + this.snake1.getScores());
        setHandleScoresLabel2("PLAYER 2: " + this.snake2.getScores());

        // Kiểm tra mồi
        if (this.food.isExists() == false) {
            this.food.resetFoodFor2Player(this.gameBoard, this.food, this.snake1, this.snake2);
        } else {
            this.food.DrawFood(this.gameBoard);
        }
        this.snake1.DrawSnake(this.gameBoard, this.gameBoard.getGc(), "#1AD9CE", "#000000","#17B3AA");
        this.snake2.DrawSnake(this.gameBoard, this.gameBoard.getGc(), "#1F42DB", "#000000","#003DAD");

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
            // Kiểm tra rắn nào đập vào tường trước
            if (this.snake1.isIsAlive() == false) {
                this.setPlayerWinLabel("SNAKE 2 WIN !!");
            } else if (this.snake2.isIsAlive() == false) {
                this.setPlayerWinLabel("SNAKE 1 WIN !!");
            }
            return true;
        }
        // Kiểm tra 2 con rắn va nhau
        // Kiểm tra snake 1 bị rắn 2 va vào người không
        for (int i = 1; i < this.snake1.getBody().size(); i++) {
            if (this.snake2.getHeadPosition().x == this.snake1.getBody().get(i).getX() && this.snake2.getHeadPosition().y == this.snake1.getBody().get(i).getY()) {
                this.GameOver_FlowPane.setVisible(true);
                setlabelForGameOver(true);
                this.setPlayerWinLabel("SNAKE 1 WIN !!");
                return true;
            }
        }
        // Kiểm tra snake 2 bị snake 1 va vào người không
        for (int i = 1; i < this.snake2.getBody().size(); i++) {
            if (this.snake1.getHeadPosition().x == this.snake2.getBody().get(i).getX() && this.snake1.getHeadPosition().y == this.snake2.getBody().get(i).getY()) {
                this.GameOver_FlowPane.setVisible(true);
                setlabelForGameOver(true);
                this.setPlayerWinLabel("SNAKE 2 WIN !!");
                return true;
            }
        }
        // Nếu đầu của 2 con va vào nhau thì xét điểm
        if (this.snake1.getHeadPosition().getX() == this.snake2.getHeadPosition().getX() && this.snake1.getHeadPosition().getY() == this.snake2.getHeadPosition().getY()) {
            this.GameOver_FlowPane.setVisible(true);
            setlabelForGameOver(true);
            if (this.snake1.getScores() > this.snake2.getScores()) {
                this.setPlayerWinLabel("SNAKE 1 WIN !!");
            } else if (this.snake1.getScores() < this.snake2.getScores()) {
                this.setPlayerWinLabel("SNAKE 2 WIN !!");
            } else {
                this.setPlayerWinLabel("DRAW !!");
            }
            return true;
        }
        return false;
    }
    // Xét label cho game over

    public void setlabelForGameOver(boolean check) {
        // Set label Game Over
        this.gameoverLabel.setVisible(check);
        // Set label Total Scores
        this.totalScoresLabel_1.setVisible(check);
        this.totalScoresLabel_1.setText("PLAYER 1: " + this.snake1.getScores());
        this.totalScoresLabel_2.setVisible(check);
        this.totalScoresLabel_2.setText("PLAYER 2: " + this.snake2.getScores());
        this.playerWinLabel.setVisible(check);

        // Hiển thị button reset
        this.restartBtn.setVisible(check);
        // Hiển thị button home
        this.homeBtn.setVisible(check);
        // Set cho button pause 
        this.pauseBtn.setDisable(check);

    }

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

    @FXML
    private void MouseClick_Restart(MouseEvent event) {
        // Xét flow pane
        this.Pause_FlowPane.setVisible(false);
        // Set label
        setlabelForGameOver(false);

        // Dừng hẳn time line
        this.timeline.stop();
        // Tạo lại rắn và handle move cho rắn
        Snake snakeReset1 = new Snake(2, 5, 5);
        Snake snakeReset2 = new Snake(2, 2, 2);

        // Handle Event for Snakeee move
        Set<KeyCode> keysPressed = new HashSet<>(); // Lưu trữ danh sách phím được nhấn

        stackPane_Canvas.getScene().setOnKeyPressed(e -> {
            keysPressed.add(e.getCode()); // Thêm phím được nhấn vào danh sách
            snakeReset1.HandeleDirectionFor2PlayerOfSnake1(e);
            snakeReset2.HandeleDirectionFor2PlayerOfSnake2(e);
        });

        stackPane_Canvas.getScene().setOnKeyReleased(e -> {
            keysPressed.remove(e.getCode()); // Xóa phím đã nhả ra khỏi danh sách
        });
        // Tạo lại thức ăn
        this.food.resetFoodFor2Player(this.gameBoard, this.food, snakeReset1, snakeReset2);
        // Chạy lại game
        startGameFor2Player(this.gameBoard, snakeReset1, snakeReset2, food);
    }

    @FXML
    private void KeyPress_Continue(KeyEvent event) {
    }

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

}
