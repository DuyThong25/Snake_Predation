package snakepredation.EF;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
    private String path;
    private Media media;
    private MediaPlayer mediaPlayer;

    public Sound(Media media, MediaPlayer mediaPlayer) {
        this.media = media;
        this.mediaPlayer = mediaPlayer;
    }
    
    public Sound(){
        
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
    
    public void EatSound(String filePath) {
        path = getClass().getResource(filePath).getPath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    
    public void GameoverSound(String filePath) {
        path = getClass().getResource(filePath).getPath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    
    public void ClickSound(String filePath) {
        path = getClass().getResource(filePath).getPath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
