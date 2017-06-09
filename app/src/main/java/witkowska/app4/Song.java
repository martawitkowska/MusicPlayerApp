package witkowska.app4;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-06-07.
 */

public class Song implements Serializable {
    private int id, year, picture;
    private String title, artist;

    public Song (int id, String title, String artist, int year, int picture_resource) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.picture = picture_resource;
    }

    public int getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public int getYear(){return year;}
    public int getPictureResource(){return picture;}
}
