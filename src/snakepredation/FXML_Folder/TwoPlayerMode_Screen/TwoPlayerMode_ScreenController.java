package snakepredation.FXML_Folder.TwoPlayerMode_Screen;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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

public class TwoPlayerMode_ScreenController implements Initializable {

    @FXML
    private StackPane stackPane;
    private Label handleScores;
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
    private ScoresDAO scoresDAO = new ScoresDAO();
    private PlayerDAO playerDAO = new PlayerDAO();
    private Sound sound = new Sound();

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
        timeline = new Timeline(new KeyFrame(Duration.millis(snake1.getSnakeSpeed()), e -> runFor2Player()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // Hàm xử lý run cho chế độ 2 người chơi
    public void runFor2Player() {
        // Kiểm tra game over không -> rắn còn sống? 
        if (checkGameOverFor2Player(this.gameBoard)) {
            this.timeline.stop();
            InsertDBScoresFor2Player();
            return;
        }

        this.gameBoard.DrawBackground();
        setHandleScoresLabel1("PLAYER 1: " + this.snake1.getScores());
        setHandleScoresLabel2("PLAYER 2: " + this.snake2.getScores());

        // Kiểm tra mồi
//        if (this.food.isExists() == false) {
//            this.food.resetFoodFor2Player(this.gameBoard, this.food, this.snake1, this.snake2);
//        } else {
//            this.food.DrawFood(this.gameBoard);
//        }
        this.food.DrawFood(this.gameBoard, this.snake1, this.snake2);

        this.snake1.DrawSnake(this.gameBoard, this.gameBoard.getGc(), "#1AD9CE", "#000000", "#17B3AA");
        this.snake2.DrawSnake(this.gameBoard, this.gameBoard.getGc(), "#1F42DB", "#000000", "#003DAD");

        this.snake1.FindPreviousPosition(this.gameBoard.getGc(), this.gameBoard);
        this.snake2.FindPreviousPosition(this.gameBoard.getGc(), this.gameBoard);

        //HandleSnakeMoveFor2Player(this.gameBoard, this.food, this.snake1, this.snake2);
        this.snake1.HandleSnakeMove(this.gameBoard, this.food);
        this.snake2.HandleSnakeMove(this.gameBoard, this.food);

        // Kiểm tra snake 1 còn sống không
        if (snake1.isSnakeAlive(gameBoard) == true) {
            snake1.setIsAlive((short) 1);
        } else {
            snake1.setIsAlive((short) 0);
        }
        // Kiểm tra snake 2 còn sống không
        if (snake2.isSnakeAlive(gameBoard) == true) {
            snake2.setIsAlive((short) 1);
        } else {
            snake2.setIsAlive((short) 0);
        }

        // Kiểm tra ran an moi chưa
        if (snake1.isSnakeEat(food)) {
            snake1.setScores(snake1.getScores() + 5);
            snake1.getSnakeBody().add(new Point(-1, -1));
            this.sound.EatSound("/asset/music/eat.mp3");
            food.setExists(false);
        } else if (snake2.isSnakeEat(food)) {// Kiểm tra ran 2 an moi chưa
            snake2.setScores(snake2.getScores() + 5);
            snake2.getSnakeBody().add(new Point(-1, -1));
            this.sound.EatSound("/asset/music/eat.mp3");
            food.setExists(false);
        } else {
            food.setExists(true);
        }
    }

    // Kiểm tra rắn còn sống trong chế độ 2 người chơi
    public boolean checkGameOverFor2Player(GameBoard gameBoard) {
        if (this.snake1.getIsAlive() == 0 || this.snake2.getIsAlive() == 0) {
            this.sound.GameoverSound("/asset/music/gameover.mp3");
            // Xét flow pane
            this.GameOver_FlowPane.setVisible(true);
            // Set label
            setlabelForGameOver(true);
            // Kiểm tra rắn nào đập vào tường trước
            if (this.snake1.getIsAlive() == 0) {
                this.setPlayerWinLabel("SNAKE 2 WIN !!");
            } else if (this.snake2.getIsAlive() == 0) {
                this.setPlayerWinLabel("SNAKE 1 WIN !!");
            }
            return true;
        }
        // Kiểm tra 2 con rắn va nhau
        // Kiểm tra snake 1 bị rắn 2 va vào người không
        for (int i = 1; i < this.snake1.getSnakeBody().size(); i++) {
            if (this.snake2.getHeadPosition().x == this.snake1.getSnakeBody().get(i).getX() && this.snake2.getHeadPosition().y == this.snake1.getSnakeBody().get(i).getY()) {
                this.GameOver_FlowPane.setVisible(true);
                setlabelForGameOver(true);
                this.setPlayerWinLabel("SNAKE 1 WIN !!");
                return true;
            }
        }
        // Kiểm tra snake 2 bị snake 1 va vào người không
        for (int i = 1; i < this.snake2.getSnakeBody().size(); i++) {
            if (this.snake1.getHeadPosition().x == this.snake2.getSnakeBody().get(i).getX() && this.snake1.getHeadPosition().y == this.snake2.getSnakeBody().get(i).getY()) {
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

    @FXML
    private void MouseClick_Restart(MouseEvent event) throws Exception {
        this.sound.ClickSound("/asset/music/click.mp3");
        Gamedetail gameDetail = new Gamedetail();
        GamedetailPK gameDetailPK = new GamedetailPK();

        Gamedetail gameDetail2 = new Gamedetail();
        GamedetailPK gameDetailPK2 = new GamedetailPK();

        GamemodeDAO gameModeDAO = new GamemodeDAO();
        GamedetailDAO gameDetailDAO = new GamedetailDAO();

        // Lấy giờ hiện tại
        LocalDateTime myDateObj = LocalDateTime.now();
        Date today = Date.from(myDateObj.atZone(ZoneId.systemDefault()).toInstant()); // Chuyển từ LocalDateTime sang Date

        int playerID = DataHolder.getInstance().getPlayerID();
        int playerID2 = DataHolder.getInstance2().getPlayerID2();

        // Thêm game detail 1 o day
        gameDetailPK.setGameID(DataHolder.getInstance().getGameID());
        gameDetailPK.setGameDate(today);
        gameDetailPK.setPlayerID(playerID);

        gameDetail.setGameName("2 Người chơi");
        gameDetail.setGameModeID(gameModeDAO.getGamemodeById(2));
        gameDetail.setGamedetailPK(gameDetailPK);

        gameDetailDAO.addGamedetail(gameDetail);

        // Thêm game detail 2 o day
        gameDetailPK2.setPlayerID(playerID2);
        gameDetailPK2.setGameID(gameDetailPK.getGameID());
        gameDetailPK2.setGameDate(today);

        gameDetail2.setGameName(gameDetail.getGameName());
        gameDetail2.setGameModeID(gameDetail.getGameModeID());
        gameDetail2.setGamedetailPK(gameDetailPK2);

        gameDetailDAO.addGamedetail(gameDetail2);

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
        this.food.resetFoodFor2Player(this.gameBoard, snakeReset1, snakeReset2);
        // Chạy lại game
        startGameFor2Player(this.gameBoard, snakeReset1, snakeReset2, food);
    }

    @FXML
    private void KeyPress_Continue(KeyEvent event) {

    }

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
    // Insert database of Scores when gameover

    public void InsertDBScoresFor2Player() {
        // Xử lý cập nhật vào database
        LocalDateTime myDateObj = LocalDateTime.now();
        Date today = Date.from(myDateObj.atZone(ZoneId.systemDefault()).toInstant()); // Chuyển từ LocalDateTime sang Date

        // Lấy id người chơi được lưu trong class dataholder
        int playerID = DataHolder.getInstance().getPlayerID();
        int playerID2 = DataHolder.getInstance2().getPlayerID2();

        /* Xet diem cho player 1 */
        Scores newScores = new Scores();
        ScoresPK newScoresPK = new ScoresPK();

        newScoresPK.setScoresID(playerID);
        // Lấy ngày hiện tại chuyển từ Calendar sang Date
        newScoresPK.setScoresDate(today);
        newScores.setScoresPK(newScoresPK);
        newScores.setTotalScores(this.snake1.getScores());
        // Tìm player mới được thêm vào
        Player existPlayer = playerDAO.getPlayerById(playerID);
        newScores.setPlayerID(existPlayer);
        scoresDAO.addScores(newScores);

        /* Xet diem cho player 2 */
        Scores newScores2 = new Scores();
        ScoresPK newScoresPK2 = new ScoresPK();

        newScoresPK2.setScoresID(playerID2);
        // Lấy ngày hiện tại chuyển từ Calendar sang Date
        newScoresPK2.setScoresDate(today);
        newScores2.setScoresPK(newScoresPK2);
        newScores2.setTotalScores(this.snake2.getScores());
        // Tìm player mới được thêm vào
        Player existPlayer2 = playerDAO.getPlayerById(playerID2);
        newScores2.setPlayerID(existPlayer2);
        scoresDAO.addScores(newScores2);
    }

}
