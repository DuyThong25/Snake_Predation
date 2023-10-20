package snakepredation;

import java.io.File;
import snakepredation.Ultil.ScreenUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

        //Chạy nhạc   
        String path = getClass().getResource("/asset/music/eat.mp3").getPath();
        System.out.println("Path: " +  path);
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
