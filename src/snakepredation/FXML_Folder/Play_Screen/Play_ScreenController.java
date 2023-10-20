package snakepredation.FXML_Folder.Play_Screen;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
import snakepredation.DataHolder_Singleton.DataHolder;
import snakepredation.EF.Food;
import snakepredation.EF.GameBoard;
import snakepredation.EF.Sound;
import snakepredation.Ultil.ScreenUtil;
import snakepredation.SnakePredation;
import snakepredation.jpa_Model.Gamedetail;
import snakepredation.jpa_Model.GamedetailPK;
import snakepredation.jpa_Model.Player;
import snakepredation.jpa_Model.Scores;
import snakepredation.jpa_Model.ScoresPK;
import snakepredation.jpa_Model.Snake;
import snakepredation.jpa_dao.GamedetailDAO;
import snakepredation.jpa_dao.GamemodeDAO;
import snakepredation.jpa_dao.PlayerDAO;
import snakepredation.jpa_dao.ScoresDAO;
import snakepredation.jpa_dao.SnakeDAO;

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
    private Food food;
    private GameBoard gameBoard;
    private Sound sound = new Sound();

    public void setSnake1(Snake snake1) {
        this.snake1 = snake1;
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

    private ScoresDAO scoresDAO = new ScoresDAO();
    private PlayerDAO playerDAO = new PlayerDAO();

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
        timeline = new Timeline(new KeyFrame(Duration.millis(snake1.getSnakeSpeed()), e -> {
            runFor1Player();
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    // Hàm xử lý run cho chế độ 1 người chơi
    public void runFor1Player() {
        if (checkGameOverFor1Player(this.gameBoard)) {
            this.timeline.stop();
            InsertDBScoresFor1Player();
            return;
        }

        this.gameBoard.DrawBackground();
        setHandleScoresLabel("Scrores: " + this.snake1.getScores());

        this.food.DrawFood(gameBoard, snake1);
        this.snake1.DrawSnake(this.gameBoard, this.gameBoard.getGc(), "#017A26", "#000000", "#056622");

        this.snake1.FindPreviousPosition(this.gameBoard.getGc(), this.gameBoard);

        // Kiểm tra game over không -> rắn còn sống? 
        this.snake1.HandleSnakeMove(this.gameBoard, this.food);

        // Kiểm tra snake 1 còn sống không
        if (this.snake1.isSnakeAlive(this.gameBoard) == true) {
            this.snake1.setIsAlive((short) 1);
        } else {
            this.snake1.setIsAlive((short) 0);
        }

        // Kiểm tra ran an moi chua
        if (this.snake1.isSnakeEat(this.food)) {// Kiểm tra ran an moi chưa
            this.snake1.setScores(this.snake1.getScores() + 5);
            this.snake1.getSnakeBody().add(new Point(-1, -1));
            this.sound.EatSound("/asset/music/eat.mp3");
            this.food.setExists(false);
        } else {
            this.food.setExists(true);
        }
    }

    // Nhấn vào button pause
    @FXML
    private void MouseClick_Pause(MouseEvent event) {
        this.sound.ClickSound("/asset/music/click.mp3");
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
        this.sound.ClickSound("/asset/music/click.mp3");
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
        if (this.snake1.getIsAlive() == 0) {
            this.sound.GameoverSound("/asset/music/gameover.mp3");
            // Xét flow pane
            this.GameOver_FlowPane.setVisible(true);
            // Set label
            setlabelForGameOver(true);

            return true;
        }
        return false;
    }

    // Trở về Home Screen
    @FXML
    private void MouseClick_Home(MouseEvent event) throws IOException {
        this.sound.ClickSound("/asset/music/click.mp3");
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
    private void MouseClick_Restart(MouseEvent event) throws Exception {
        this.sound.ClickSound("/asset/music/click.mp3");
        Gamedetail gameDetail = new Gamedetail();
        GamemodeDAO gamemodeDAO = new GamemodeDAO();
        GamedetailDAO gameDetailDAO = new GamedetailDAO();
        int playerID = DataHolder.getInstance().getPlayerID();

        // Lấy giờ hiện tại
        LocalDateTime myDateObj = LocalDateTime.now();
        Date today = Date.from(myDateObj.atZone(ZoneId.systemDefault()).toInstant()); // Chuyển từ LocalDateTime sang Date

        GamedetailPK gamedetailPK = new GamedetailPK();
        gamedetailPK.setGameID(DataHolder.getInstance().getGameID());
        gamedetailPK.setGameDate(today);
        gamedetailPK.setPlayerID(playerID);

        gameDetail.setGamedetailPK(gamedetailPK);
        gameDetail.setGameName("1 Người chơi");
        gameDetail.setGameModeID(gamemodeDAO.getGamemodeById(1));

        gameDetailDAO.addGamedetail(gameDetail);
        // Xét flow pane
        this.Pause_FlowPane.setVisible(false);
        // Set label
        setlabelForGameOver(false);
        // Dừng hẳn time line
        this.timeline.stop();
        // Tạo lại rắn và handle move cho rắn
        Snake snakeReset = new Snake(2, 5, 5);
        this.bg__Snake.getScene().setOnKeyReleased(e -> snakeReset.HandeleDirectionFor1Player(e));
        // Tạo lại thức ăn
        this.food.resetFood(gameBoard, snakeReset);
        // Chạy lại game
        startGameFor1Player(this.gameBoard, snakeReset, this.food);

    }

    // Insert database of Scores when gameover
    public void InsertDBScoresFor1Player() {
        // Xử lý cập nhật vào database
        LocalDateTime myDateObj = LocalDateTime.now();
        Date today = Date.from(myDateObj.atZone(ZoneId.systemDefault()).toInstant()); // Chuyển từ LocalDateTime sang Date

        Scores newScores = new Scores();
        ScoresPK newScoresPK = new ScoresPK();
        // Lấy id người chơi được lưu trong class dataholder
        int playerID = DataHolder.getInstance().getPlayerID();

        newScoresPK.setScoresID(playerID);
        // Lấy ngày hiện tại chuyển từ Calendar sang Date
        newScoresPK.setScoresDate(today);
        newScores.setScoresPK(newScoresPK);
        newScores.setTotalScores(this.snake1.getScores());
        // Tìm player mới được thêm vào
        Player existPlayer = playerDAO.getPlayerById(playerID);
        newScores.setPlayerID(existPlayer);
        scoresDAO.addScores(newScores);
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
