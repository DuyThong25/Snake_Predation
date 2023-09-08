/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package snakepredation.FXML_Folder.Home_Screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import snakepredation.FXML_Folder.Play_Screen.Play_ScreenController;

/**
 * FXML Controller class
 *
 * @author duyth
 */
public class Home_ScreenController implements Initializable {

    @FXML
    private ImageView background_Home;
    @FXML
    private Button startBtn_Home;
    @FXML
    private Button ratingBtn_Home;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void MoveTo_PlayScreen(MouseEvent event) throws Exception {

        // Load file fxml của Play_Screen -> scene builder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/snakepredation/FXML_Folder/Play_Screen/Play_Screen.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        // Lấy file CSS
        scene.getStylesheets().add(getClass().getResource("/snakepredation/FXML_Folder/Play_Screen/Play_Screen.css").toExternalForm());
        
        Stage playStage = new Stage();
        playStage.setTitle("Snake Predation");
        playStage.setScene(scene);
        playStage.show();

        // Set max width/height
        playStage.setMinWidth(playStage.getWidth());
        playStage.setMinHeight(playStage.getHeight());
        System.out.println("3");
                System.out.println(root.getScene());


    }

    @FXML
    private void MoveTo_RatingScreen(MouseEvent event) {
        System.out.println("ok");

    }

}
