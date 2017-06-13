package witkowska.app4;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

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

    @BindView(R.id.title) TextView song_title;
    @BindView(R.id.artist) TextView song_artist;
    @BindView(R.id.year) TextView song_year;
    @BindView(R.id.picture) ImageView song_picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_info_activity);
        ButterKnife.bind(this);

//        Toast.makeText(getApplicationContext(), "Hello from here!", Toast.LENGTH_SHORT).show();
        //mAdapter = new MoviesAdapter(movieList);

        Intent intent = getIntent();
        Bundle song_data = intent.getBundleExtra("Bundle");
        songList = (ArrayList<Song>) song_data.getSerializable("Songs");
        position = song_data.getInt("Position");

        song = songList.get(position);

        song_title.setText(song.getTitle());
        song_artist.setText(song.getArtist());
        song_year.setText(String.valueOf(song.getYear()));
        song_picture.setImageResource(song.getPictureResource());
    }

    public void playClick(View view) {
//        String tag = view.getTag().toString();
        musicSrv.setSong(Integer.parseInt(String.valueOf(position)));
        musicSrv.playSong();
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
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv=null;
        super.onDestroy();
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return position;
    }

    @Override
    public void seekTo(int pos) { }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() { return 0; }


}
