package witkowska.app4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-06-09.
 */

public class SongInfoActivity extends AppCompatActivity {

    private ArrayList<Song> songList = new ArrayList<>();
    Song song;
    int position;

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

        Song movie = songList.get(position);

        song_title.setText(movie.getTitle());
        song_artist.setText(movie.getArtist());
        song_year.setText(String.valueOf(movie.getYear()));
//        int res = movie.getPictureResource();
        song_picture.setImageResource(movie.getPictureResource());

    }

//    int resID=getResources().getIdentifier(fname, "raw", getPackageName());
//
//    MediaPlayer mediaPlayer=MediaPlayer.create(this,resID);
//                    mediaPlayer.start();



}
