package snakepredation.FXML_Folder.Home_Screen;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import snakepredation.DataHolder_Singleton.DataHolder;
import snakepredation.FXML_Folder.Play_Screen.Play_ScreenController;
import snakepredation.FXML_Folder.TwoPlayerMode_Screen.TwoPlayerMode_ScreenController;
import snakepredation.EF.Food;
import snakepredation.EF.GameBoard;
import snakepredation.EF.Sound;
import snakepredation.Ultil.ScreenUtil;
import snakepredation.SnakePredation;
import snakepredation.jpa_Model.Gamedetail;
import snakepredation.jpa_Model.GamedetailPK;
import snakepredation.jpa_Model.Gamemode;
import snakepredation.jpa_Model.Player;
import snakepredation.jpa_Model.Scores;
import snakepredation.jpa_Model.ScoresPK;
import snakepredation.jpa_Model.Snake;
import snakepredation.jpa_dao.GamedetailDAO;
import snakepredation.jpa_dao.GamemodeDAO;
import snakepredation.jpa_dao.PlayerDAO;
import snakepredation.jpa_dao.ScoresDAO;
import snakepredation.jpa_dao.SnakeDAO;

public class Home_ScreenController implements Initializable {

    @FXML
    private ImageView background_Home;
    @FXML
    private Button startBtn_Home;
    @FXML
    private Button ratingBtn_Home;
    @FXML
    private Button twoplayerBtn_Home1;
    @FXML
    private FlowPane controlBtnPane;
    @FXML
    private ImageView imageRanking;
    @FXML
    private ScrollPane scrollPaneRanking;

    @FXML
    private ImageView closeRankingBtn;
    @FXML
    private StackPane rankingPane;
    private StackPane rankingPane1;
    @FXML
    private ImageView closeRankingBtn1;
    @FXML
    private TextField inputName1;
    @FXML
    private TextField inputName2;
    @FXML
    private Label labelName1;
    @FXML
    private Label labelName2;
    @FXML
    private Button btnPlay;
    @FXML
    private StackPane InputPane;
    private int checkMode;
    private Sound sound = new Sound();
    private PlayerDAO playerDAO = new PlayerDAO();
    @FXML
    private TableView<Scores> tableRanking;
    @FXML
    private TableColumn<Scores, String> columnStt;

    @FXML
    private TableColumn<Scores, String> columnDate;
    @FXML
    private TableColumn<Scores, String> columnName;
    @FXML
    private TableColumn<Scores, Integer> columnScores;
    @FXML
    private TableColumn<Scores, String> columnMode;

    private final ObservableList<Scores> dataScoresList = FXCollections.observableArrayList();
    private final ScoresDAO scoresDAO = new ScoresDAO();
    @FXML
    private Label lbErrorMessage1;
    @FXML
    private Label lbErrorMessage2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Table View cho
        dataScoresList.addAll(scoresDAO.getAllScores());
        columnScores.setCellValueFactory(new PropertyValueFactory<Scores, Integer>("totalScores"));

        
        columnStt.setCellValueFactory(new Callback<CellDataFeatures<Scores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Scores, String> value) {   
                Integer index = value.getTableView().getItems().indexOf(value.getValue());
                String newIndex = String.valueOf(index + 1);
                return new SimpleStringProperty(newIndex);
            }
        });

        columnName.setCellValueFactory(new Callback<CellDataFeatures<Scores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Scores, String> value) {
                return new SimpleStringProperty(value.getValue().getPlayerID().getPlayerName());
            }
        });

        columnDate.setCellValueFactory(new Callback<CellDataFeatures<Scores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Scores, String> value) {
                Date date = value.getValue().getScoresPK().getScoresDate();
                String formattedDate = formatDate(date);
                return new SimpleStringProperty(formattedDate);
            }
        });
        // Set a comparator for the column to sort as dates
        columnDate.setComparator(Comparator.comparing(o -> {
            try {
                // Parse the date string and return a Comparable representation
                return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(o);
            } catch (ParseException e) {
                e.printStackTrace(); // Handle parsing exception
                return null;
            }
        }));

        columnMode.setCellValueFactory(new Callback<CellDataFeatures<Scores, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Scores, String> value) {
                Player player = playerDAO.getPlayerById(value.getValue().getPlayerID().getPlayerID());
                String nameMode = "";
                for (Gamedetail gamedetail : player.getGamedetailCollection()) {
                    int id = gamedetail.getGameModeID().getGameModeID();
                    if (id == 1) {
                        nameMode = "1 Player";
                    } else {
                        nameMode = "2 Player";
                    }
                }
                return new SimpleStringProperty(nameMode);
            }
        });

        tableRanking.setItems(dataScoresList);
        // gộp 2 row lại
//        tableRanking.setRowFactory(tv -> new TableRow<Scores>() {
//            @Override
//            protected void updateItem(Scores item, boolean empty) {
//                super.updateItem(item, empty);
//
//                if (!empty) {
//                    String nameMode = columnMode.getCellData(getIndex());
//                    if ("2 Player".equals(nameMode)) {
//                        // Apply merging logic here
//                        setStyle("-fx-background-color: lightgray;"); // Set the background color for merged rows
//                    } else {
//                        setStyle(""); // Reset style for other rows
//                    }
//                } else {
//                    setStyle(""); // Reset style for empty rows
//                }
//            }
//        });
    }

    // Chuyển ngày thành chuỗi
    private String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return sdf.format(date);
        } else {
            return null;
        }
    }

    // Bấm vào nút play
    @FXML
    private void MoveTo_PlayScreen(MouseEvent event) throws Exception {
        this.sound.ClickSound("/asset/music/click.mp3");
        this.checkMode = 1;
        // Xử lý visible
        controlBtnPane.setVisible(false);
        imageRanking.setVisible(true);
        InputPane.setVisible(true);
        labelName2.setDisable(true);
        inputName2.setDisable(true);
        labelName2.setVisible(false);
        inputName2.setVisible(false);
        inputName2.setText("OFF");
    }

    @FXML
    private void MoveTo2Player_PlayScreen(MouseEvent event) {
        this.sound.ClickSound("/asset/music/click.mp3");
        this.checkMode = 2;
        // Xử lý visible
        controlBtnPane.setVisible(false);
        imageRanking.setVisible(true);
        InputPane.setVisible(true);
        labelName2.setDisable(false);
        inputName2.setDisable(false);
        labelName2.setVisible(true);
        inputName2.setVisible(true);
        inputName2.setText("");

    }

    @FXML
    private void MoveTo_RatingScreen(MouseEvent event) {
        this.sound.ClickSound("/asset/music/click.mp3");
        // Xử lý visible
        controlBtnPane.setVisible(false);
        rankingPane.setVisible(true);
    }

    @FXML
    private void closeRanking(MouseEvent event) {
        this.sound.ClickSound("/asset/music/click.mp3");
        imageRanking.setVisible(false);
        rankingPane.setVisible(false);
        controlBtnPane.setVisible(true);
        InputPane.setVisible(false);
        lbErrorMessage1.setText("");
        lbErrorMessage2.setText("");
    }

    @FXML
    private void PlayGame_Screen(MouseEvent event) throws IOException, Exception {
        this.sound.ClickSound("/asset/music/click.mp3");
        if (inputName1.getText().isBlank() || inputName2.getText().isBlank()) {
            if (this.checkMode == 1) {
                lbErrorMessage1.setText("Vui lòng không để trống");
                lbErrorMessage2.setText("");

            } else {
                if (inputName1.getText().isBlank() && inputName2.getText().isBlank()) {
                    lbErrorMessage1.setText("Vui lòng không để trống");
                    lbErrorMessage2.setText("Vui lòng không để trống");

                } else if (inputName2.getText().isBlank()) {
                    lbErrorMessage1.setText("");
                    lbErrorMessage2.setText("Vui lòng không để trống");
                } else {
                    lbErrorMessage1.setText("Vui lòng không để trống");
                    lbErrorMessage2.setText("");
                }
            }
        } else {
            Player newPlayer1 = new Player();
            Gamedetail gameDetail = new Gamedetail();
            SnakeDAO snakeDAO = new SnakeDAO();
            GamedetailDAO gamedetailDAO = new GamedetailDAO();
            GamemodeDAO gamemodeDAO = new GamemodeDAO();
            lbErrorMessage1.setText("");
            lbErrorMessage2.setText("");
            // Lấy giờ hiện tại
            LocalDateTime myDateObj = LocalDateTime.now();
            Date today = Date.from(myDateObj.atZone(ZoneId.systemDefault()).toInstant()); // Chuyển từ LocalDateTime sang Date
            if (this.checkMode == 1) {

                // set dữ liệu của player mới vào database
                newPlayer1.setPlayerID(playerDAO.getNextPlayerID());
                newPlayer1.setPlayerName(inputName1.getText());
                newPlayer1.setSnakeID(snakeDAO.getSnakeById(1));

                playerDAO.addPlayer(newPlayer1);

                // Thêm game detail o day
                gameDetail.setGameName("1 người chơi");
                gameDetail.setGameModeID(gamemodeDAO.getGamemodeById(this.checkMode));

                GamedetailPK gamedetailPK = new GamedetailPK();

                gamedetailPK.setPlayerID(newPlayer1.getPlayerID());
                gamedetailPK.setGameID(gamedetailDAO.getNextGamedetailID());
                gamedetailPK.setGameDate(today);
                gameDetail.setGamedetailPK(gamedetailPK);

                gamedetailDAO.addGamedetail(gameDetail);

                // Truyền dữ liệu vào data holder
                DataHolder.getInstance().setPlayerID(newPlayer1.getPlayerID());
                DataHolder.getInstance().setGameID(gamedetailPK.getGameID()); // lưu game id

                // Chạy chế độ 1 người chơi
                onePlayerMode();
            } else {
                Player newPlayer2 = new Player();
                Gamedetail gameDetail2 = new Gamedetail();

                // set dữ liệu của player 1 vào database
                newPlayer1.setPlayerID(playerDAO.getNextPlayerID());
                newPlayer1.setPlayerName(inputName1.getText());
                newPlayer1.setSnakeID(snakeDAO.getSnakeById(2));

                // Lấy id khi người chơi khi bấm play truyền vào class Dataholder để tương tác dữ liệu với controller khác
                DataHolder.getInstance().setPlayerID(newPlayer1.getPlayerID());
                // Thêm player 1 mới vào database
                playerDAO.addPlayer(newPlayer1);

                // set dữ liệu của player 2 vào database
                newPlayer2.setPlayerID(playerDAO.getNextPlayerID());
                newPlayer2.setPlayerName(inputName2.getText());
                newPlayer2.setSnakeID(snakeDAO.getSnakeById(2));
                // Lấy id khi người chơi khi bấm play truyền vào class Dataholder để tương tác dữ liệu với controller khác
                DataHolder.getInstance2().setPlayerID2(newPlayer2.getPlayerID());
                // Thêm  player 2 mới vào database
                playerDAO.addPlayer(newPlayer2);

                // Thêm game detail 1 o day
                gameDetail.setGameName("2 người chơi");
                gameDetail.setGameModeID(gamemodeDAO.getGamemodeById(this.checkMode));
                GamedetailPK gamedetailPK = new GamedetailPK();

                gamedetailPK.setPlayerID(newPlayer1.getPlayerID());
                gamedetailPK.setGameID(gamedetailDAO.getNextGamedetailID());
                gamedetailPK.setGameDate(today);

                gameDetail.setGamedetailPK(gamedetailPK);

                gamedetailDAO.addGamedetail(gameDetail);

                // Thêm game detail 2 o day
                gameDetail2.setGameName(gameDetail.getGameName());
                gameDetail2.setGameModeID(gameDetail.getGameModeID());
                GamedetailPK gamedetailPK2 = new GamedetailPK();

                gamedetailPK2.setPlayerID(newPlayer2.getPlayerID());
                gamedetailPK2.setGameID(gamedetailPK.getGameID());
                gamedetailPK2.setGameDate(today);
                gameDetail2.setGamedetailPK(gamedetailPK2);

                gamedetailDAO.addGamedetail(gameDetail2);

                // Lưu vào dataholder
                DataHolder.getInstance().setGameID(gamedetailPK.getGameID()); // lưu game id

                // chạy
                twoPlayerMode();
            }
        }
    }

    public void onePlayerMode() throws IOException {
        // Load file fxml của Play_Screen -> scene builder
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/snakepredation/FXML_Folder/Play_Screen/Play_Screen.fxml"));
        Parent root = loader.load();

        // Lấy file controller của Play_Screen
        Play_ScreenController play_Controller = loader.getController();

        Scene scene = new Scene(root);
        // Lấy file CSS của Play_Screen
        scene.getStylesheets().add(getClass()
                .getResource("/snakepredation/FXML_Folder/Play_Screen/Play_Screen.css").toExternalForm());

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
        //Set màu cho rắn
        snake.setColorBody("#056622");
        snake.setColorEyes("#000000");
        snake.setColorHead("#017A26");

        // Khởi tạo vị trí thức ăn và màu thức ăn ngẫu nhiên
        Food food = gameBoard.GenerateRandomFood(snake);

        // Handle Event for Snakeee move
        // Xử lý 1 người chơi
        bgSnake_Canvas.getScene().setOnKeyPressed(e -> snake.HandeleDirectionFor1Player(e));

        play_Controller.startGameFor1Player(gameBoard, snake, food);
    }

    public void twoPlayerMode() throws IOException {
// Load file fxml của Play_Screen -> scene builder
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/snakepredation/FXML_Folder/TwoPlayerMode_Screen/TwoPlayerMode_Screen.fxml"));
        Parent root = loader.load();

        // Lấy file controller của Play_Screen
        TwoPlayerMode_ScreenController twoPlayerMode_Controller = loader.getController();

        Scene scene = new Scene(root);
        // Lấy file CSS của Play_Screen
        scene.getStylesheets().add(getClass()
                .getResource("/snakepredation/FXML_Folder/TwoPlayerMode_Screen/twoplayermode_screen.css").toExternalForm());

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
