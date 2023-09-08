package snakepredation.FXML_Folder.Play_Screen;

import java.awt.Point;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    // Hàm xử lý run
    public void run(GameBoard gameBoard, Snake snake, Food food) {
        if (snake.isIsAlive() == false) {
            gameBoard.getGc().setTextAlign(TextAlignment.CENTER);

            // Vẽ văn bản "Game Over!!!" với kích thước font 70 và màu đỏ
            gameBoard.getGc().setFill(Color.RED);
            gameBoard.getGc().setFont(new Font("Digital-7", 80));
            gameBoard.getGc().fillText("Game Over!!!", gameBoard.getWidth() / 2, gameBoard.getHeight() / 2);

            // Vẽ văn bản "Total Scores" với kích thước font 20 và màu trắng
            gameBoard.getGc().setFill(Color.WHITE);
            gameBoard.getGc().setFont(new Font("Digital-7", 30));
            gameBoard.getGc().fillText("Total Scores: " + snake.getScores(), gameBoard.getWidth() / 2, gameBoard.getHeight() / 2 + 50);
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
}
