package Logic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameAlerts
{
    //Show Lost Alert when losing
    public static void showLostAlert(Stage s)
    {
        BorderPane lostBorderPane;
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GameAlerts.class.getResource("/GUI/lost.fxml"));
            lostBorderPane = loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
            return ;
        }
        s.setTitle("MineSweeper Message");
        s.setScene(new Scene(lostBorderPane));
        s.show();
        s.getScene().getWindow().sizeToScene();
    }
    //======================================================================================================
    //Show Win alert if losing
    public static void showWinAlert(Stage s)
    {
        BorderPane lostBorderPane;
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GameAlerts.class.getResource("/GUI/win.fxml"));
            lostBorderPane = loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
            return ;
        }
        s.setTitle("MineSweeper Message");
        s.setScene(new Scene(lostBorderPane));
        s.show();
        s.getScene().getWindow().sizeToScene();
    }
    //======================================================================================================
    //Create and show the alert for the errors.
    public static void showErrorAlert(String header, String content, Alert.AlertType type)
    {
        Alert alert = new Alert(type);
        alert.setTitle("Mine Sweeper Message");
        alert.setContentText(content);
        alert.setHeaderText(null);
        DialogPane d = alert.getDialogPane();
        d.setStyle("-fx-background-color:rgba(33,235,255,1)");
        alert.setDialogPane(d);
        alert.show();
    }
}
