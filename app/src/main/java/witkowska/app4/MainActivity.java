package witkowska.app4;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Song> songList;
    private RecyclerView songView;
    private SongAdapter songAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        songView = (RecyclerView)findViewById(R.id.recycler_view);
        songList = new ArrayList<Song>();

//        songAdapter = new SongAdapter(songList);
//        songView.setAdapter(songAdapter);
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        songView.setLayoutManager(layoutManager);

        songAdapter = new SongAdapter(this, songList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        songView.setLayoutManager(mLayoutManager);
        songView.setItemAnimator(new DefaultItemAnimator());
        songView.setAdapter(songAdapter);

        prepareSongs();


        songView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), songView,
                new RecyclerTouchListener.MyClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Song song = songList.get(position);
//                        Toast.makeText(getApplicationContext(), song.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent songIntent = new Intent(getApplicationContext(), SongInfoActivity.class);

                        Bundle song_data = new Bundle();
                        song_data.putInt("Position", position);
                        song_data.putSerializable("Songs", (Serializable)songList);
                        songIntent.putExtra("Bundle", song_data);
                        startActivityForResult(songIntent, 123);
                    }
                    @Override
                    public void onLongClick(View view, int position) {}
                }
        ));
    }


    private void prepareSongs() {
        Song song = new Song(1, "The Coconut Song", "Smokey Mountain", 2010, R.drawable.coconut_song);
        songList.add(song);

        songAdapter.notifyDataSetChanged();
    }


}
