package snakepredation.FXML_Folder.Play_Screen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class Play_ScreenController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Canvas bg__Snake;
    @FXML
    private StackPane stackPane_Canvas;

    /**
     * Initializes the Getter/ Setter
     */
    public Canvas getBg__Snake() {
        return bg__Snake;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      
    }

    @FXML
    public void HandleSnakeMove(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        switch (keyCode) {
            case UP:
            case W:
                System.out.println("Key Code: " + keyCode.toString());
                // Xử lý di chuyển lên ở đây
                break;
            case DOWN:
            case S:
                System.out.println("Key Code: " + keyCode.toString());
                // Xử lý di chuyển xuống ở đây
                break;
            case LEFT:
            case A:
                System.out.println("Key Code: " + keyCode.toString());
                // Xử lý di chuyển qua trái ở đây
                break;
            case RIGHT:
            case D:
                System.out.println("Key Code: " + keyCode.toString());
                // Xử lý di chuyển qua phải ở đây
                break;
            default:
                // Xử lý các trường hợp khác nếu cần
                break;
        }
    }

}
