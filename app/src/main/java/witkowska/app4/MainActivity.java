package witkowska.app4;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.ButterKnife;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Song> songList;
    private ListView songView;
    private SongAdapter songAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        songView = (ListView) findViewById(R.id.song_list);
        songList = new ArrayList<Song>();

        prepareSongs();

        songAdapter = new SongAdapter(this, songList);
        songView.setAdapter(songAdapter);


        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent songIntent = new Intent(getApplicationContext(), SongInfoActivity.class);

                Bundle song_data = new Bundle();
                song_data.putInt("Position", position);
                song_data.putSerializable("Songs", (Serializable)songList);
                songIntent.putExtra("Bundle", song_data);
                startActivityForResult(songIntent, 123);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepareSongs() {
        Song song = new Song(1, "The Coconut Song", "Smokey Mountain", 2010, R.drawable.coconut_song);
        songList.add(song);

        song = new Song(2, "Perfect strangers", "Jonas Blue ft. JP Cooper", 2016, R.drawable.perfect_strangers);
        songList.add(song);

        song = new Song(3, "Shiny", "Jemaine Clement", 2016, R.drawable.shiny);
        songList.add(song);

        song = new Song(4, "Galway Girl", "Ed Sheeran", 2017, R.drawable.galway_girl);
        songList.add(song);

        song = new Song(5, "Immortals", "Fall Out Boys", 2014, R.drawable.immortals);
        songList.add(song);

        song = new Song(6, "Cups (When I'm gone)", "Anna Kendrick", 2013, R.drawable.cups);
        songList.add(song);
    }


}
