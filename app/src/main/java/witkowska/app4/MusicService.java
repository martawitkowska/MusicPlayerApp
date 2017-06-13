package witkowska.app4;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-06-12.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{

    private MediaPlayer player;
    private ArrayList<Song> songList;
    private int song_position;
    private final IBinder musicBind = new MusicBinder(); //representing the inner class


    @Override
    public void onCreate(){
        super.onCreate();
        song_position = 0;
        player = new MediaPlayer();

        initializeMusicPlayer();
    }

    public void initializeMusicPlayer(){
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK); //lets playback continue
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        /* Setting a class as a listener (corresponding to implemented interfaces) */
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public void setSongList(ArrayList<Song> songs) {
        songList = songs;
    }

    public void setSong(int position) {
        song_position = position;
    }


    public void playSong() {
        player.reset(); //resetting the mediaplayer
        player.release();
        player = null;

        Song song = songList.get(song_position);
        int song_ID = song.getID();

        String track_name = "song" + String.valueOf(song_ID);
        int resource_ID = getResources().getIdentifier(track_name, "raw", getPackageName());
        try{
            player = MediaPlayer.create(this, resource_ID);
//            player.prepareAsync(); //then onPrepare() method
        } catch(Exception e){
            Toast.makeText(this, "MP3 not found", Toast.LENGTH_SHORT).show();
        }

        //mp3 will be started after completion of preparing
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer player) {
                player.start();
            }

        });
    }



    /* Interaction between the MainActivity and Service classes */
    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    /* Stop the service when the user exits the app */
    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {}
}
