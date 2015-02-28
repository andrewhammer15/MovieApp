package andrewhammer.hammerlistapp;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by andrewhammer on 2/18/15.
 */
public class MovieUtils {
    public static String API_BASE_URL = "http://www.omdbapi.com/?y=&type=movie&plot=short&r=json&tomatoes=true&t=";
    public static String IMDB_BASE_URL = "http://www.imdb.com/title/";
    public static String TARGET_MOVIE_INDEX = "INDEX";

    //generate a request to omdbapi
    public static String movieRequest(String query) {
        query = query.replace(' ', '+');
        return API_BASE_URL.concat(query);
    }

}
