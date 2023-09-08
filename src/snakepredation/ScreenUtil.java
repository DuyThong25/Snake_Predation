package snakepredation;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScreenUtil {

    public static void centerScreen(Stage stage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        double x = (primScreenBounds.getWidth() - stage.getWidth()) / 2;
        double y = (primScreenBounds.getHeight() - stage.getHeight()) / 2;
        stage.setX(x);
        stage.setY(y);
    }
}
