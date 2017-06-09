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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {

    private List<Song> songList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, artist;
        public ImageView picture;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            artist = (TextView) view.findViewById(R.id.artist);
            year = (TextView) view.findViewById(R.id.year);
//            picture = (ImageView) view.findViewById(R.id.picture);
        }
    }

    public SongAdapter(ArrayList<Song> songList) {
        this.songList = songList;
    }

    @Override
    public int getItemViewType(int position) { return 0; }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.title.setText(song.getTitle());
        holder.artist.setText(song.getArtist());
        holder.year.setText(String.valueOf(song.getYear()));
//        holder.picture.setImageResource(song.getPictureResource());
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }
}