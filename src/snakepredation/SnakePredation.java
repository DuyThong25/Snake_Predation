package snakepredation;

import snakepredation.Ultil.ScreenUtil;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import snakepredation.jpa_dao.PlayerDAO;

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
        // Set cho nằm giữa màn hình
        ScreenUtil.centerScreen(primaryStage);

        // Set max width/height
        this.primaryStage.setMinWidth(this.primaryStage.getWidth());
        this.primaryStage.setMinHeight(this.primaryStage.getHeight());
    }

    public static void main(String[] args) {    

            launch(args);
    }
}
