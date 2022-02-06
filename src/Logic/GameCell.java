package Logic;

import GUI.MinesController;
import GUI.MinesFX;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.List;

public class GameCell extends Button
{
    final private double BTN_WIDTH = 45;
    final private double BTN_HEIGHT = 45;
    public GameCell(MinesFX minesFX, int i, int j)
    {
        super();
        setGameFieldProperties(minesFX,i,j);
    }

    private void setGameFieldProperties(MinesFX minesFX, int i,int j)
    {
        setId("btn");
        //Set The Text of the Button according to his location.
        setText(minesFX.getMines().get(i, j));
        setMinHeight(BTN_WIDTH);
        setMinWidth(BTN_HEIGHT);
        setCursor(Cursor.HAND);
        //Graphic on top of text
        setContentDisplay(ContentDisplay.TOP);
        setPadding(new Insets(-1));
        setBackground(new Background(new BackgroundFill(Color.rgb(17, 17, 17, 1), null, null)));
        setStyle("-fx-border-color:rgba(33,235,255,1)");
        setTextFill(Color.rgb(33, 235, 255, 1));
        setFont(Font.font("Ariel", FontWeight.BOLD, 20));
    }
    public void setPressGameFieldAction(MinesFX minesFX, int i,int j)
    {
        setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                boolean isSuccessfulPlay=true;
                //Try to open Button with left mouse button key
                if(event.getButton().equals(MouseButton.PRIMARY))
                {
                    if(minesFX.getMines().getCell(i,j).isOpen())
                    {
                        List<Mines.CellLocation> optionalCells= minesFX.getMines().showClosedNeighborCells(i,j);
                        int countNeighborsWithFlags = minesFX.getMines().getNeighborsWithFlags(optionalCells);
                        int countMinesNearCells = minesFX.getMines().getCell(i,j).getCountMinesNearCell();
                        if(countNeighborsWithFlags==0||countNeighborsWithFlags<countMinesNearCells)
                        {
                            minesFX.markOptionalCells(optionalCells,true);
                        }
                        else
                        {
                            isSuccessfulPlay = minesFX.getMines().openOptionalCells(i,j);
                        }
                    }
                    else
                    {
                        isSuccessfulPlay = minesFX.getMines().open(i, j);
                    }
                    GameSounds.playOpenFieldSound();
                    //Lose scenario
                    if(!isSuccessfulPlay)
                    {
                        minesFX.getMines().setShowAll(true);
                        GameSounds.stopBackgroundMusic();
                        GameSounds.playLoseMusic();
                        GameAlerts.showLostAlert(minesFX.getStageMessage());
                        //When Closing the Lost message
                        minesFX.getStageMessage().setOnCloseRequest(new EventHandler<WindowEvent>()
                        {
                            @Override
                            public void handle(WindowEvent event)
                            {
                                GameSounds.stopLoseMusic();
                                if(minesFX.isGameSound())
                                {
                                    if (!GameSounds.isBackgroundMusicPlay())
                                        GameSounds.playBackgroundMusic();
                                }
                            }
                        });
                        minesFX.finishGame();
                    }
                    minesFX.updateGridAfterMove();
                    //Check if The Player won
                    if(minesFX.getMines().isDone())
                    {
                        GameAlerts.showWinAlert(minesFX.getStageMessage());
                        //backgroundMusic.setVolume(0);
                        GameSounds.stopBackgroundMusic();
                        GameSounds.playWinMusic();
                        //minesFX.updateGridAfterMove();
                        //When Closing the Win Message
                        minesFX.getStageMessage().setOnCloseRequest(new EventHandler<WindowEvent>()
                        {
                            @Override
                            public void handle(WindowEvent event)
                            {
                                GameSounds.stopWinMusic();
                                if(minesFX.isGameSound())
                                {
                                    if (!GameSounds.isBackgroundMusicPlay())
                                        GameSounds.playBackgroundMusic();
                                }
                            }
                        });
                        minesFX.finishGame();
                    }
                }
                //===========================================================================================
                //Try To land Flag
                if(event.getButton().equals((MouseButton.SECONDARY)))
                {
                    minesFX.getMines().toggleFlag(i,j);
                    if(minesFX.getMines().get(i,j)=="F")
                    {
                        setCursor(Cursor.OPEN_HAND);
                        setGraphic(GameImages.getFlagImage());
                        GameSounds.playLandFlagSound();
                    }
                    else
                    {
                        setCursor(Cursor.HAND);
                        setGraphic(null);
                    }
                    minesFX.updateGridAfterMove();
                }
            }
        });
    }
    public void setHoverGameFieldAction()
    {
        setOnMouseEntered(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                final FadeTransition fadeTransition = new FadeTransition();
                fadeTransition.setNode(GameCell.this);
                fadeTransition.setDuration(Duration.seconds(1));
                fadeTransition.setFromValue(0.5);
                fadeTransition.setToValue(1);
                setBackground(new Background(new BackgroundFill(Color.rgb(17,17,17,1),null,null)));
                setTextFill(Color.rgb(33,235,255,1));
                fadeTransition.playFromStart();
                setEffect(GameAnimation.getShadow());
            }
        });
    }
    public void setExitGameFieldAction()
    {
        setOnMouseExited(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                final FadeTransition fadeOut = new FadeTransition();
                fadeOut.setNode(GameCell.this);
                fadeOut.setDuration(Duration.seconds(1));
                fadeOut.setFromValue(1);
                fadeOut.setToValue(0.2);
                setBackground(new Background(new BackgroundFill(Color.rgb(17,17,17,1),null,null)));
                setTextFill(Color.rgb(33,235,255,1));
                fadeOut.playFromStart();
                setEffect(null);
                fadeOut.setOnFinished(new EventHandler<ActionEvent>()
                {
                    @Override
                    public void handle(ActionEvent event)
                    {
                        PauseTransition pauseTransition = new PauseTransition();
                        pauseTransition.setDuration(Duration.seconds(0.2));
                        FadeTransition fadeTransition = new FadeTransition();
                        fadeTransition.setDuration(Duration.seconds(0.5));
                        fadeTransition.setFromValue(0.2);
                        fadeTransition.setToValue(1);
                        SequentialTransition t = new SequentialTransition(GameCell.this,pauseTransition,fadeTransition);
                        t.playFromStart();
                    }
                });
            }
        });
    }
    public void mouseReleasedGameField(MinesFX minesFX, int i,int j)
    {
        setOnMouseReleased(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if(minesFX.getMines().getCell(i,j).isShowOptionalCells())
                {
                    minesFX.getMines().getCell(i,j).setShowOptionalCells(false);
                    List<Mines.CellLocation> optionalCells = minesFX.getMines().showClosedNeighborCells(i, j);
                    if (optionalCells != null)
                        minesFX.markOptionalCells(optionalCells, false);
                }
            }
        });
    }
}
