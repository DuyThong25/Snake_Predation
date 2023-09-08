package snakepredation.FXML_Folder.Play_Screen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import snakepredation.FXML_Folder.Home_Screen.Home_ScreenController;
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

    /**
     * Initializes the Getter/ Setter
     */
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
    
    public Play_ScreenController() {
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
               
          // Khởi tạo GameBoard
        GameBoard gameBoard = new GameBoard(700, 700);
        gameBoard.setGc(getBg__Snake().getGraphicsContext2D()); // Set cho Canvas = GraphicsContext2D

        // Khởi tạo vị trí Snake
        Snake snake = new Snake(5);

        // Khởi tạo thức ăn và màu thức ăn
        Food food = gameBoard.GenerateRandomFood(snake);

        // Handle Event for Snakeee
//        scene.setOnKeyPressed(e -> snake.HandeleDirection(e));

    }
}
