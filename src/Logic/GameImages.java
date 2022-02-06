package Logic;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.nio.file.Paths;

public class GameImages
{
    static final private double BTN_WIDTH = 45;
    static final private double BTN_HEIGHT = 45;
    public static final Image MINE_SWEEPER_LOGO = new Image(Paths.get("src/ExternalFiles/Pictures/minesweeper2.png").toUri().toString());
    public static final Image BACKGROUND_IMAGE = new Image(Paths.get("src/ExternalFiles/Pictures/background.gif").toUri().toString());
    public static final Image MINE_IMAGE = new Image(Paths.get("src/ExternalFiles/Pictures/redMine.png").toUri().toString());
    public static final Image FLAG_IMAGE = new Image(Paths.get("src/ExternalFiles/Pictures/flag.png").toUri().toString());
    public static final BackgroundSize BACKGROUND_SIZE = new BackgroundSize(500,525,false,false,false,true);
    public static final BackgroundImage BACKGROUND = new BackgroundImage(BACKGROUND_IMAGE, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BACKGROUND_SIZE);

    //Return The ImageView of the flag.
    public static ImageView getFlagImage()
    {
        ImageView imageView = new ImageView(FLAG_IMAGE);
        imageView.setFitWidth(BTN_WIDTH);
        imageView.setFitHeight(BTN_HEIGHT);
        imageView.setPreserveRatio(true);
        imageView.setCache(true);
        return imageView;
    }
    //============================================================================================
    //Return The ImageView of the Mine.
    public static  ImageView getMineView()
    {
        ImageView imageView = new ImageView(MINE_IMAGE);
        imageView.setFitWidth(BTN_WIDTH);
        imageView.setFitHeight(BTN_HEIGHT);
        imageView.setPreserveRatio(true);
        imageView.setCache(true);
        return imageView;
    }
    //============================================================================================
    public static ImageView getLogo()
    {
        ImageView imageView = new ImageView(MINE_SWEEPER_LOGO);
        imageView.setCursor(Cursor.DISAPPEAR);
        return imageView;
    }
    //============================================================================================
}
