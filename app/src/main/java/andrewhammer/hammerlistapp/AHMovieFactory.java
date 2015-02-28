package andrewhammer.hammerlistapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrewhammer on 2/18/15.
 */
public class AHMovieFactory {
    //returns new AHMovie from well-formed JSON, null otherwise
    public static AHMovie build(JSONObject movieJSON) {
        try {
            if (movieJSON.getString("Response").equals("True")) {
                return new AHMovie(movieJSON.getString("Title"),
                        movieJSON.getString("Year"), movieJSON.getString("Rated"),
                        movieJSON.getString("Runtime"), movieJSON.getString("Genre"),
                        movieJSON.getString("Director"), movieJSON.getString("Actors"),
                        movieJSON.getString("Poster"), movieJSON.getString("Plot"),
                        movieJSON.getString("imdbRating"), movieJSON.getString("tomatoMeter"),
                        movieJSON.toString(), movieJSON.getString("imdbID"));
            } else {
                Log.d("AHMoviefactory", "API call returned false");
                return null;
            }

        } catch (JSONException e) {
            Log.e("AHMoviefactory", e.toString());
        }
        return null;
    }
}