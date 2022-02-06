package Logic;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameAnimation
{
    private GameAnimation(){}
    public static SequentialTransition getMineEffect(Button button)
    {
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setDuration(Duration.seconds(0.5));
        fadeIn.setFromValue(0.5);
        fadeIn.setToValue(1);

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(0.5));

        FadeTransition fadeOut = new FadeTransition();
        fadeOut.setDuration(Duration.seconds(0.5));
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0.5);

        SequentialTransition sequentialTransition = new SequentialTransition(button,fadeIn,pauseTransition,fadeOut);
        sequentialTransition.setCycleCount(Animation.INDEFINITE);
        return sequentialTransition;
    }
    //Set the shadow of the fields.
    public static DropShadow getShadow()
    {
        DropShadow shadow = new DropShadow();
        shadow.setBlurType(BlurType.THREE_PASS_BOX);
        shadow.setColor(Color.rgb(33,235,255,1));
        shadow.setOffsetX(0);
        shadow.setOffsetY(0);
        shadow.setSpread(25);
        return shadow;
    }
}
