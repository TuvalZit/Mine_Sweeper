package Logic;

import javafx.scene.media.AudioClip;

import java.nio.file.Paths;

//Game Sound Class
public class GameSounds
{
    //Game Sound Constants
    static final AudioClip BACKGROUND_MUSIC = new AudioClip(Paths.get("src/ExternalFiles/Sounds/backgroundMusic.mp3").toUri().toString());
    static final AudioClip LAND_FLAG_SOUND = new AudioClip(Paths.get("src/ExternalFiles/Sounds/flag.wav").toUri().toString());
    static final AudioClip LOSE_MUSIC = new AudioClip( Paths.get("src/ExternalFiles/Sounds/gameover.mp3").toUri().toString());
    static final AudioClip OPEN_FIELD_SOUND = new AudioClip(Paths.get("src/ExternalFiles/Sounds/open.wav").toUri().toString());
    static final AudioClip WIN_MUSIC = new AudioClip(Paths.get("src/ExternalFiles/Sounds/win.mp3").toUri().toString());
    //=============================================================================
    //Play Background Music
    public static void playBackgroundMusic()
    {
        BACKGROUND_MUSIC.play(0.1);
        BACKGROUND_MUSIC.setCycleCount(AudioClip.INDEFINITE);
    }
    //=============================================================================
    //Play Land Flag Sound
    public static void playLandFlagSound()
    {
        LAND_FLAG_SOUND.play(20);
    }
    //=============================================================================
    //Play Lose Music
    public static void playLoseMusic()
    {
        LOSE_MUSIC.play(0.1);
        LOSE_MUSIC.setCycleCount(AudioClip.INDEFINITE);
    }
    //=============================================================================
    //Play Open Field Sound
    public static void playOpenFieldSound()
    {
        OPEN_FIELD_SOUND.play(0.1);
    }
    //=============================================================================
    //Play Win Music
    public static void playWinMusic()
    {
        WIN_MUSIC.play(0.1);
        WIN_MUSIC.setCycleCount(AudioClip.INDEFINITE);
    }
    //=============================================================================
    //Stop Background Music
    public static void stopBackgroundMusic()
    {
        BACKGROUND_MUSIC.stop();
    }
    //=============================================================================
    //Stop Land Flag Sound
    public static void stopLandFlagSound()
    {
        LAND_FLAG_SOUND.stop();
    }
    //=============================================================================
    //Stop Lose Music
    public static void stopLoseMusic()
    {
        LOSE_MUSIC.stop();
    }
    //=============================================================================
    //Stop Open Field Sound
    public static void stopOpenFieldSound()
    {
        OPEN_FIELD_SOUND.stop();
    }
    //=============================================================================
    //Stop Win Music
    public static void stopWinMusic()
    {
        WIN_MUSIC.stop();
    }
    //=============================================================================
    public static boolean isBackgroundMusicPlay()
    {
        return BACKGROUND_MUSIC.isPlaying();
    }
}//End Game Sound Class
