package snakepredation.FXML_Folder.Home_Screen;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import snakepredation.FXML_Folder.Play_Screen.Play_ScreenController;
import snakepredation.FXML_Folder.TwoPlayerMode_Screen.TwoPlayerMode_ScreenController;
import snakepredation.Food;
import snakepredation.GameBoard;
import snakepredation.ScreenUtil;
import snakepredation.Snake;
import snakepredation.SnakePredation;

public class Home_ScreenController implements Initializable {

    @FXML
    private ImageView background_Home;
    @FXML
    private Button startBtn_Home;
    @FXML
    private Button ratingBtn_Home;
    @FXML
    private Button twoplayerBtn_Home1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // Bấm vào nút play
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

        // Set cho nằm giữa màn hình
        ScreenUtil.centerScreen(play_Stage);

        // Set max width/height
        play_Stage.setMinWidth(play_Stage.getWidth());
        play_Stage.setMinHeight(play_Stage.getHeight());

        // Lấy canvas background ra từ Play_Screen controller
        Canvas bgSnake_Canvas = play_Controller.getBg__Snake();

        // Khởi tạo GameBoard
        GameBoard gameBoard = new GameBoard(700, 700);
        gameBoard.setGc(bgSnake_Canvas.getGraphicsContext2D()); // Set cho Canvas = GraphicsContext2D

        // Khởi tạo chiều dài và vị trí Snake
        Snake snake = new Snake(2, 5, 5);

        // Khởi tạo thức ăn và màu thức ăn
        Food food = gameBoard.GenerateRandomFood(snake);

        // Handle Event for Snakeee move
        // Xử lý 1 người chơi
        root.getScene().setOnKeyPressed(e -> snake.HandeleDirectionFor1Player(e));
        play_Controller.startGameFor1Player(gameBoard, snake, food);

    }

    @FXML
    private void MoveTo_RatingScreen(MouseEvent event) {
        System.out.println("ok");
    }

    @FXML
    private void TwoPlayer_PlayScreen(MouseEvent event) throws IOException {
        // Load file fxml của Play_Screen -> scene builder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/snakepredation/FXML_Folder/TwoPlayerMode_Screen/TwoPlayerMode_Screen.fxml"));
        Parent root = loader.load();

        // Lấy file controller của Play_Screen
        TwoPlayerMode_ScreenController twoPlayerMode_Controller = loader.getController();

        Scene scene = new Scene(root);
        // Lấy file CSS của Play_Screen
        scene.getStylesheets().add(getClass().getResource("/snakepredation/FXML_Folder/TwoPlayerMode_Screen/twoplayermode_screen.css").toExternalForm());

        // Lấy primary stage từ SnakePredation.java
        Stage play_Stage = SnakePredation.getPrimaryStage();
        play_Stage.setScene(scene);
        play_Stage.show();

        // Set cho nằm giữa màn hình
        ScreenUtil.centerScreen(play_Stage);

        // Set max width/height
        play_Stage.setMinWidth(play_Stage.getWidth());
        play_Stage.setMinHeight(play_Stage.getHeight());

//        // Lấy canvas background ra từ Play_Screen controller
        Canvas bgSnake_Canvas = twoPlayerMode_Controller.getBg__Snake();

        // Khởi tạo GameBoard
        GameBoard gameBoard = new GameBoard(700, 700);
        gameBoard.setGc(bgSnake_Canvas.getGraphicsContext2D()); // Set cho Canvas = GraphicsContext2D

        // Khởi tạo chiều dài và vị trí Snake 1
        Snake snake1 = new Snake(2, 5, 8);
        // Khởi tạo chiều dài và vị trí Snake 1
        Snake snake2 = new Snake(2, 5, 10);

        // Khởi tạo thức ăn và màu thức ăn
        Food food = gameBoard.GenerateRandomFoodFor2Player(snake1, snake2);

        // Handle Event for Snakeee move
        Set<KeyCode> keysPressed = new HashSet<>(); // Lưu trữ danh sách phím được nhấn

        root.getScene().setOnKeyPressed(e -> {
            keysPressed.add(e.getCode()); // Thêm phím được nhấn vào danh sách
            snake1.HandeleDirectionFor2PlayerOfSnake1(e);
            snake2.HandeleDirectionFor2PlayerOfSnake2(e);
        });

        root.getScene().setOnKeyReleased(e -> {
            keysPressed.remove(e.getCode()); // Xóa phím đã nhả ra khỏi danh sách
        });
        twoPlayerMode_Controller.startGameFor2Player(gameBoard, snake1, snake2, food);
    }

}
