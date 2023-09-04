package snakepredation.FXML_Folder.Play_Screen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class Play_ScreenController implements Initializable {

    @FXML
    private Button ClickMeBtn;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private StackPane stackPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void HandleClick(ActionEvent event) {
        if (event.getSource() == ClickMeBtn) {
            System.out.println("Hello World");
        }
    }

}
