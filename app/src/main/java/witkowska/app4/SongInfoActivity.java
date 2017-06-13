package witkowska.app4;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-06-09.
 */

public class SongInfoActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {

    private ArrayList<Song> songList = new ArrayList<>();
    Song song;
    int position;
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound = false;
    private MusicController controller;
    private boolean paused = false;

    @BindView(R.id.title) TextView song_title;
    @BindView(R.id.artist) TextView song_artist;
    @BindView(R.id.year) TextView song_year;
    @BindView(R.id.picture) ImageView song_picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_info_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle song_data = intent.getBundleExtra("Bundle");
        songList = (ArrayList<Song>) song_data.getSerializable("Songs");
        position = song_data.getInt("Position");
        song = songList.get(position);
        song_title.setText(song.getTitle());
        song_artist.setText(song.getArtist());
        song_year.setText(String.valueOf(song.getYear()));
        song_picture.setImageResource(song.getPictureResource());

        setController();
//        controller.show(0);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(controller != null)
            controller.show(0);
//        Toast.makeText(this, "onWindowFocusChanged()", Toast.LENGTH_SHORT).show();
    }

    public void playClick(View view) {
        musicSrv.setSong(position);
        musicSrv.playSong();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controller.show();
        return false;
    }

    /* Connection (Activity) with the Service
    *  If the connection with the bound is made, we pass the song list */
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            musicSrv = binder.getService(); //get the service
            musicSrv.setSongList(songList); //pass list with songs
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };


    private void setController(){
        controller = new MusicController(SongInfoActivity.this);
        final ConstraintLayout songInfoView = (ConstraintLayout) findViewById(R.id.constraint_view);

//        controller.setPrevNextListeners(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playNext();
//            }
//        }, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playPrev();
//            }
//        });

        controller.setMediaPlayer(SongInfoActivity.this);
        controller.setAnchorView(songInfoView);
        controller.setEnabled(true);

//        controller.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent == null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused = false;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        paused = true;
     }

    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv=null;
        super.onDestroy();
    }


    //play next
    private void playNext(){
        musicSrv.playNext();
        controller.show(0);
    }

    //play previous
    private void playPrev(){
        musicSrv.playPrev();
        controller.show(0);
    }

    @Override
    public void start() {
//        Toast.makeText(this, "start()", Toast.LENGTH_SHORT).show();
        musicSrv.go();
    }

    @Override
    public void pause() {
        musicSrv.pausePlayer();
    }

    @Override
    public int getDuration() {
        if(musicSrv != null && musicBound && musicSrv.isPng())
            return musicSrv.getDur();
        else return 0;
    }

    @Override
    public int getCurrentPosition() {
//        Toast.makeText(this, "getCurrentPosition()", Toast.LENGTH_SHORT).show();
        if(musicSrv != null && musicBound && musicSrv.isPng())
            return musicSrv.getPosn();
        else return 0;
    }

    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);
//        Toast.makeText(this, "seekTo()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isPlaying() {
        if(musicSrv != null && musicBound)
            return musicSrv.isPng();
        else return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() { return true; }

    @Override
    public boolean canSeekBackward() { return true; }

    @Override
    public boolean canSeekForward() { return true; }

    @Override
    public int getAudioSessionId() { return 0; }

}
