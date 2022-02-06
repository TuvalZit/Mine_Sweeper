package GUI;

import Logic.*;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
// TODO: 04/02/2022  : 04/02/2022 Add flag counter
//============================================================================================
//Start of MinesFx Class
public class MinesFX extends Application
{
    //MinesFx private fields.
    private BorderPane pane;
    private  GameCell[][] gameCells;
    private MinesController myMinesController;
    private  Mines mines;
    private Stage stageMessage = new Stage();
    private boolean gameSound = true;
    //===========================================================
    //Start of Main
    public static void main(String[] args)
    {
        launch(args);
    }
    //============================================================================================
    //Run
    @Override
    public void start(Stage primaryStage)
    {
        //Load FXML
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("mineSweeper.fxml"));
            setPane(loader.load());
            setMyController(loader.getController());
            //Play Background music
            GameSounds.playBackgroundMusic();
            //Set The Stage
            primaryStage.setTitle("Mine Sweeper");
            //Set the animated background of the game
            pane.setBackground(new Background(GameImages.BACKGROUND));
            //Set the logo of the game on top
            pane.setTop(GameImages.getLogo());
            //Set the scene and stage
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            myMinesController.setPrimaryStage(primaryStage);
            myMinesController.setMinesFX(this);
            setMineField(primaryStage, myMinesController, 10, 10, 10);
            primaryStage.show();
            primaryStage.getScene().getWindow().sizeToScene();
            setMyController(myMinesController);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
    }
    //============================================================================================
    //Define the Mine field inside the Grid.
    public void setMineField(Stage stage, MinesController myMinesController, int height, int width, int numOfMines)
    {
        //Create New Grade
        GridPane mineGrid = getGridPane();
        mines = new Mines(height,width,numOfMines);
        gameCells = new GameCell[height][width];
        myMinesController.setTxtForFlagCounter(Integer.toString(mines.getFlagCounter()));
        for (int i = 0; i <height ; i++)
        {
            for (int j = 0; j < width; j++)
            {
                gameCells[i][j] = new GameCell(this, i, j);
                gameCells[i][j].setPressGameFieldAction(this, i, j);
                gameCells[i][j].setHoverGameFieldAction();
                gameCells[i][j].setExitGameFieldAction();
                gameCells[i][j].mouseReleasedGameField(this,i,j);
                mineGrid.add(gameCells[i][j], j, i);
            }
        }

        VBox vBox = myMinesController.getvBox();
        //Clear vBox Content if not empty
        if(vBox.getChildren().size()!=0)
            vBox.getChildren().remove(0);
        //Add the new GridPane
        vBox.getChildren().add(mineGrid);
        stage.getScene().getWindow().sizeToScene();
    }
    //============================================================================================
    public void updateGridAfterMove()
    {
        SequentialTransition stepOnMine ;
        for(int i = 0; i< mines.getHeight(); i++)
            for(int j = 0; j< mines.getWidth(); j++)
            {
                //Set the Text according to the location.
                gameCells[i][j].setText(mines.get(i,j));

                //If the button is open and has not near mines set him disabled.
                if(gameCells[i][j].getText().equals(" "))
                {
                    gameCells[i][j].setGraphic(null);
                    gameCells[i][j].setDisable(true);
                    gameCells[i][j].setOpacity(1);
                }

                //Fill the color of the buttons that is not mine and not near mine.
                if(gameCells[i][j].getText().equals(" "))
                    gameCells[i][j].setStyle("-fx-background-color:rgba(33,235,255,1)");


                if(gameCells[i][j].getText().equals("F"))
                {
                    if(mines.isShowAll())
                    {
                        if (!mines.getCell(i, j).getStatus().equals("MINE"))
                        {
                            gameCells[i][j].setStyle("-fx-background-color:rgba(255,87,51,1)");
                        }
                    }
                }
                //If the button is mine
                if(gameCells[i][j].getText().equals("X"))
                {
                    stepOnMine = GameAnimation.getMineEffect(gameCells[i][j]);
                    gameCells[i][j].setGraphic(GameImages.getMineView());//Set the button with mine Image.
                    stepOnMine.play();
                }
            }
        myMinesController.getTxtFlagCounter().setText(Integer.toString(mines.getFlagCounter()));
    }
    //============================================================================================
    //Set All board to be disabled after finish the game(win/lose)
    public void finishGame()
    {
        for(int i = 0; i< mines.getHeight(); i++)
            for(int j = 0; j< mines.getWidth(); j++)
            {
                gameCells[i][j].setDisable(true);
                gameCells[i][j].setOpacity(1);
            }
        myMinesController.getTxtFlagCounter().setText(Integer.toString(mines.getFlagCounter()));
    }
    //============================================================================================
    //Set the controller
    public void setMyController(MinesController myMinesController)
    {
        this.myMinesController = myMinesController;
    }
    //============================================================================================
    //Get the controller
    public MinesController getMyController()
    {
        return myMinesController;
    }
    //============================================================================================
    //set the borderPane for the stage.
    public void setPane(BorderPane pane)
    {
        this.pane = pane;
    }
    //=============================================================================================
    public Mines getMines()
    {
        return this.mines;
    }
    //=============================================================================================
    public Stage getStageMessage()
    {
        return stageMessage;
    }
    //=============================================================================================
    public GridPane getGridPane()
    {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color:rgba(17,17,17,1)");
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }//=============================================================================================
    public void markOptionalCells(List<Mines.CellLocation> optionalCells,boolean mark)
    {
        for(Mines.CellLocation cellLocation:optionalCells)
        {
            int i = cellLocation.getX();
            int j = cellLocation.getY();
            if(mines.getCell(i,j).isHasFlag())
                continue;
            if(mark)
                gameCells[i][j].setStyle("-fx-border-color:rgba(67,255,100,1);-fx-background-color:rgba(67,255,100,1)");
            else
                gameCells[i][j].setStyle("-fx-border-color:rgba(33,235,255,1);-fx-background-color:rgba(17,17,17,1)");

        }
    }
    //=============================================================================================
    public boolean isGameSound()
    {
        return gameSound;
    }
    //=============================================================================================
    public void setGameSound(boolean gameSound)
    {
        this.gameSound = gameSound;
    }
    //=============================================================================================
}//End of MinesJavaFX
