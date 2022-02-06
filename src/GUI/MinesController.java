package GUI;

import Logic.GameAlerts;
import Logic.GameSounds;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.nio.file.Paths;


public class MinesController
{
    //private BorderPane inputPane;
    private Stage primaryStage;
    private Stage alertStage = new Stage();
    private MinesFX minesFX;
    private Tooltip resetTip = new Tooltip();
    private Image alarmImageFile = new Image(Paths.get("src/mines/alarm.jpg").toUri().toString());
    //=================================================================================================
    //minesFXML Nodes.
    @FXML
    private Button btnReset;

    @FXML
    private TextField txtWidth;

    @FXML
    private TextField txtHeight;

    @FXML
    private TextField txtMines;

    @FXML
    private VBox vBox;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnPause;

    @FXML
    private TextField txtFlagCounter;
    @FXML
    private Label lblMsg;

    //=================================================================================================
    //Press The reset Button
    @FXML
    void pressReset()
    {
        GameSounds.stopWinMusic();
        GameSounds.stopLoseMusic();
        if(minesFX.isGameSound())
        {
            if(!GameSounds.isBackgroundMusicPlay())
                GameSounds.playBackgroundMusic();
        }
        //Empty TextBoxes
        if (txtWidth.getText().equals("") || txtHeight.getText().equals(" ") || txtMines.getText().equals(""))
            GameAlerts.showErrorAlert("INPUT ERROR","You Must input the setting for the board", Alert.AlertType.ERROR);
        else
        {
            try
            {
                //Negative Numbers
                if (getHeight() < 0 || getWidth() < 0 || getNumOfMines() < 0)
                    GameAlerts.showErrorAlert("INPUT ERROR","Inputs must be none negative number", Alert.AlertType.ERROR);
                else
                {
                    //All of the Board is mines
                    if(getNumOfMines()==getHeight()*getWidth())
                        GameAlerts.showErrorAlert("INPUT ERROR","You cant win because all of the board full of mines", Alert.AlertType.ERROR);
                    else
                    {
                        //Illegal size of field
                        if(getHeight()>16||getWidth()>30)
                            GameAlerts.showErrorAlert("INPUT ERROR","Height must be <1,16>\nWidth must be<1,30>", Alert.AlertType.ERROR);
                            //Legal Input
                        else
                        {
                            if(getNumOfMines()>getHeight()*getWidth())
                                GameAlerts.showErrorAlert("INPUT ERROR","Number of mines bigger then board size", Alert.AlertType.ERROR);
                            else
                            {
                                minesFX.finishGame();
                                minesFX.setMineField(primaryStage, this, getHeight(), getWidth(), getNumOfMines());
                            }
                        }
                    }
                }
            }
            //Trying to input something that is not number
            catch (NumberFormatException exception )
            {
                GameAlerts.showErrorAlert("INPUT ERROR","One of the Input was not numeric!", Alert.AlertType.ERROR);
            }
        }
    }
    //=================================================================================================
    @FXML
    void pressPlay()
    {
        minesFX.setGameSound(true);
        if(!GameSounds.isBackgroundMusicPlay())
            GameSounds.playBackgroundMusic();
    }
    //=================================================================================================
    @FXML
    void pressPause()
    {
        minesFX.setGameSound(false);
        if(GameSounds.isBackgroundMusicPlay())
            GameSounds.stopBackgroundMusic();
    }
    //=================================================================================================
    //Get The height of the Field
    public int getHeight()
    {
        return Integer.parseInt(txtHeight.getText());
    }
    //=================================================================================================
    //Get The Width of the Field
    public int getWidth()
    {
        return Integer.parseInt(txtWidth.getText());
    }
    //=================================================================================================
    //Get The number of mines
    public int getNumOfMines()
    {
        return Integer.parseInt(txtMines.getText());
    }
    //=================================================================================================
    //Get the VBox that inside the border pane
    public VBox getvBox()
    {
        return vBox;
    }
    //=================================================================================================
    //Set the primary stage
    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }
    //=================================================================================================
    //Return the primary Stage
    public Stage getPrimaryStage()
    {
        return primaryStage;
    }
    //=================================================================================================
    public void setMinesFX(MinesFX minesFX)
    {
        this.minesFX = minesFX;
    }
    //=================================================================================================
    public TextField getTxtFlagCounter()
    {
        return txtFlagCounter;
    }
    //=================================================================================================
    public void setTxtForFlagCounter(String txt)
    {
        this.txtFlagCounter.setText(txt);
    }
    //=================================================================================================
}//End of MinesController.
