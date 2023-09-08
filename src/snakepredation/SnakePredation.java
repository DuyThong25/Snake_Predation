package snakepredation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import snakepredation.FXML_Folder.Play_Screen.Play_ScreenController;

public class SnakePredation extends Application {

    private static Stage primaryStage; // Tham chiếu đến cửa sổ chính

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        SnakePredation.primaryStage = primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Khởi tạo primaryStage
        this.primaryStage = primaryStage;

        //Load file fxml của Play_Screen -> scene builder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/snakepredation/FXML_Folder/Home_Screen/Home_Screen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        // Lấy file CSS
        scene.getStylesheets().add(getClass().getResource("/snakepredation/FXML_Folder/Home_Screen/Home_Screen.css").toExternalForm());

        this.primaryStage.setTitle("Snake Predation");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

        // Set max width/height
        this.primaryStage.setMinWidth(this.primaryStage.getWidth());
        this.primaryStage.setMinHeight(this.primaryStage.getHeight());
    }

    // MAIN
    public static void main(String[] args) {
        launch(args);
    }
    // MAIN
}
