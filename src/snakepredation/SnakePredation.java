package snakepredation;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

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
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/snakepredation", "root", "ddThtV@2520");
            System.out.println("Success");

            try {
                String sql = "SELECT * FROM gamemode";
                PreparedStatement statement = con.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    // Đọc thông tin từ cơ sở dữ liệu
                    int id = resultSet.getInt("GameModeID");
                    String name = resultSet.getString("GameModeName");

                    // Xử lý dữ liệu hoặc hiển thị nó
                    System.out.println("ID: " + id + ", Name: " + name);
                }

                // Đóng các tài nguyên
                resultSet.close();
                statement.close();
                try {
                    if (con != null) {
                        con.close();
                        System.out.println("Connection closed");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            launch(args);

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SnakePredation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            System.out.println("Fail 1");

        } catch (SQLException ex) {
            Logger.getLogger(SnakePredation.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fail 2");
        }
    }
}
