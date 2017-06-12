package witkowska.app4;

/**
 * Created by Administrator on 2017-04-11.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SongAdapter extends BaseAdapter {

    private ArrayList<Song> songs;
    private LayoutInflater songInf;


    public SongAdapter(Context c, ArrayList<Song> songList){
        songs = songList;
        songInf = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout songLay = (RelativeLayout) songInf.inflate (R.layout.song_list_row, parent, false);

        TextView songView = (TextView)songLay.findViewById(R.id.title);
        TextView artistView = (TextView)songLay.findViewById(R.id.artist);
        TextView yearView = (TextView)songLay.findViewById(R.id.year);

        Song currSong = songs.get(position);
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getArtist());
        yearView.setText(String.valueOf(currSong.getYear()));

        songLay.setTag(position);
        return songLay;
    }
}