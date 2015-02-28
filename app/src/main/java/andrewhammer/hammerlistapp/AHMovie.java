package andrewhammer.hammerlistapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrewhammer on 2/18/15.
 */
public class AHMovie {
    public String title;
    public String year;
    public String rating;
    public String runtime;
    public String genre;
    public String directors;
    public String actors;
    public String poster;
    public String synopsis;
    public String imdbRating;
    public String rtRating;
    public String srcJSON;
    public String imdbID;

    public AHMovie(String title, String year, String rating, String runtime, String genre,
                   String directors, String actors, String poster,
                   String synopsis, String imdbRating, String rtRating, String srcJSON, String imdbID) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.runtime = runtime;
        this.genre = genre;
        this.directors = directors;
        this.actors = actors;
        this.poster = poster;
        this.synopsis = synopsis;
        this.imdbRating = imdbRating;
        this.rtRating = rtRating;
        this.srcJSON = srcJSON;
        this.imdbID = imdbID;
    }

    //Overrides generic object 'equals' method
    //imdbID is guaranteed to be unique
    @Override
    public boolean equals (Object o) {
        return o instanceof AHMovie && imdbID.equals(((AHMovie) o).imdbID);
    }


}
