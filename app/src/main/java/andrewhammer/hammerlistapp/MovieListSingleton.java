package andrewhammer.hammerlistapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by andrewhammer on 2/22/15.
 */
//The movie list only lasts for the lifecycle of the application
public class MovieListSingleton {
    private static MovieListSingleton mInstance;
    private ArrayList<AHMovie> favMovies;
    public boolean hasBeenChanged;

    //Easy way to generate default list of my favorite movies
    private static String darkKnight = "{\"Title\":\"The Dark Knight\",\"Year\":\"2008\",\"Rated\":\"PG-13\",\"Released\":\"18 Jul 2008\",\"Runtime\":\"152 min\",\"Genre\":\"Action, Crime, Drama\",\"Director\":\"Christopher Nolan\",\"Writer\":\"Jonathan Nolan (screenplay), Christopher Nolan (screenplay), Christopher Nolan (story), David S. Goyer (story), Bob Kane (characters)\",\"Actors\":\"Christian Bale, Heath Ledger, Aaron Eckhart, Michael Caine\",\"Plot\":\"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, the caped crusader must come to terms with one of the greatest psychological tests of his ability to fight injustice.\",\"Language\":\"English, Mandarin\",\"Country\":\"USA, UK\",\"Awards\":\"Won 2 Oscars. Another 131 wins & 105 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg\",\"Metascore\":\"82\",\"imdbRating\":\"9.0\",\"imdbVotes\":\"1,353,384\",\"imdbID\":\"tt0468569\",\"Type\":\"movie\",\"tomatoMeter\":\"94\",\"tomatoImage\":\"certified\",\"tomatoRating\":\"8.6\",\"tomatoReviews\":\"316\",\"tomatoFresh\":\"296\",\"tomatoRotten\":\"20\",\"tomatoConsensus\":\"Dark, complex and unforgettable, The Dark Knight succeeds not just as an entertaining comic book film, but as a richly thrilling crime saga.\",\"tomatoUserMeter\":\"94\",\"tomatoUserRating\":\"4.4\",\"tomatoUserReviews\":\"1,814,267\",\"DVD\":\"09 Dec 2008\",\"BoxOffice\":\"$533.3M\",\"Production\":\"Warner Bros. Pictures/Legendary\",\"Website\":\"http://thedarkknight.warnerbros.com/\",\"Response\":\"True\"}";
    private static String memento = "{\"Title\":\"Memento\",\"Year\":\"2000\",\"Rated\":\"R\",\"Released\":\"25 May 2001\",\"Runtime\":\"113 min\",\"Genre\":\"Mystery, Thriller\",\"Director\":\"Christopher Nolan\",\"Writer\":\"Christopher Nolan (screenplay), Jonathan Nolan (short story \\\"Memento Mori\\\")\",\"Actors\":\"Guy Pearce, Carrie-Anne Moss, Joe Pantoliano, Mark Boone Junior\",\"Plot\":\"A man creates a strange system to help him remember things; so he can hunt for the murderer of his wife without his short-term memory loss being an obstacle.\",\"Language\":\"English\",\"Country\":\"USA\",\"Awards\":\"Nominated for 2 Oscars. Another 50 wins & 42 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTc4MjUxNDAwN15BMl5BanBnXkFtZTcwMDMwNDg3OA@@._V1_SX300.jpg\",\"Metascore\":\"80\",\"imdbRating\":\"8.5\",\"imdbVotes\":\"703,094\",\"imdbID\":\"tt0209144\",\"Type\":\"movie\",\"tomatoMeter\":\"92\",\"tomatoImage\":\"certified\",\"tomatoRating\":\"8.2\",\"tomatoReviews\":\"167\",\"tomatoFresh\":\"154\",\"tomatoRotten\":\"13\",\"tomatoConsensus\":\"Christopher Nolan skillfully guides the audience through Memento's fractured narrative, seeping his film in existential dread.\",\"tomatoUserMeter\":\"94\",\"tomatoUserRating\":\"4.1\",\"tomatoUserReviews\":\"374,871\",\"DVD\":\"04 Sep 2001\",\"BoxOffice\":\"$23.8M\",\"Production\":\"Newmarket Films\",\"Website\":\"http://www.otnemem.com\",\"Response\":\"True\"}\n";
    private static String reservoirDogs = "{\"Title\":\"Reservoir Dogs\",\"Year\":\"1992\",\"Rated\":\"R\",\"Released\":\"02 Sep 1992\",\"Runtime\":\"99 min\",\"Genre\":\"Crime, Drama\",\"Director\":\"Quentin Tarantino\",\"Writer\":\"Quentin Tarantino, Roger Avary (background radio dialog), Quentin Tarantino (background radio dialog)\",\"Actors\":\"Harvey Keitel, Tim Roth, Michael Madsen, Chris Penn\",\"Plot\":\"After a simple jewelery heist goes terribly wrong, the surviving criminals begin to suspect that one of them is a police informant.\",\"Language\":\"English\",\"Country\":\"USA\",\"Awards\":\"12 wins & 10 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTQxMTAwMDQ3Nl5BMl5BanBnXkFtZTcwODMwNTgzMQ@@._V1_SX300.jpg\",\"Metascore\":\"78\",\"imdbRating\":\"8.4\",\"imdbVotes\":\"529,687\",\"imdbID\":\"tt0105236\",\"Type\":\"movie\",\"tomatoMeter\":\"92\",\"tomatoImage\":\"certified\",\"tomatoRating\":\"8.8\",\"tomatoReviews\":\"61\",\"tomatoFresh\":\"56\",\"tomatoRotten\":\"5\",\"tomatoConsensus\":\"N/A\",\"tomatoUserMeter\":\"94\",\"tomatoUserRating\":\"4.1\",\"tomatoUserReviews\":\"439,419\",\"DVD\":\"05 Nov 2002\",\"BoxOffice\":\"N/A\",\"Production\":\"Miramax Films\",\"Website\":\"N/A\",\"Response\":\"True\"}";
    private static String beforeSunrise = "{\"Title\":\"Before Sunrise\",\"Year\":\"1995\",\"Rated\":\"R\",\"Released\":\"27 Jan 1995\",\"Runtime\":\"105 min\",\"Genre\":\"Drama, Romance\",\"Director\":\"Richard Linklater\",\"Writer\":\"Richard Linklater, Kim Krizan\",\"Actors\":\"Ethan Hawke, Julie Delpy, Andrea Eckert, Hanno Pöschl\",\"Plot\":\"A young man and woman meet on a train in Europe, and wind up spending one evening together in Vienna. Unfortunately, both know that this will probably be their only night together.\",\"Language\":\"English, German, French\",\"Country\":\"USA, Austria, Switzerland\",\"Awards\":\"1 win & 3 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTQyMTM3MTQxMl5BMl5BanBnXkFtZTcwMDAzNjQ4Mg@@._V1_SX300.jpg\",\"Metascore\":\"77\",\"imdbRating\":\"8.1\",\"imdbVotes\":\"146,014\",\"imdbID\":\"tt0112471\",\"Type\":\"movie\",\"tomatoMeter\":\"100\",\"tomatoImage\":\"certified\",\"tomatoRating\":\"8.3\",\"tomatoReviews\":\"41\",\"tomatoFresh\":\"41\",\"tomatoRotten\":\"0\",\"tomatoConsensus\":\"Thought-provoking and beautifully filmed, Before Sunrise is an intelligent, unabashedly romantic look at modern love, led by marvelously natural performances from Ethan Hawke and Julie Delpy.\",\"tomatoUserMeter\":\"93\",\"tomatoUserRating\":\"4.1\",\"tomatoUserReviews\":\"70,335\",\"DVD\":\"30 Nov 1999\",\"BoxOffice\":\"N/A\",\"Production\":\"Sony Pictures Home Entertainment\",\"Website\":\"N/A\",\"Response\":\"True\"}\n";
    private static String departed = "{\"Title\":\"The Departed\",\"Year\":\"2006\",\"Rated\":\"R\",\"Released\":\"06 Oct 2006\",\"Runtime\":\"151 min\",\"Genre\":\"Crime, Drama, Thriller\",\"Director\":\"Martin Scorsese\",\"Writer\":\"William Monahan (screenplay), Alan Mak, Felix Chong\",\"Actors\":\"Leonardo DiCaprio, Matt Damon, Jack Nicholson, Mark Wahlberg\",\"Plot\":\"An undercover state cop who has infiltrated an Irish gang and a mole in the police force working for the same mob race to track down and identify each other before being exposed to the enemy, after both sides realize their outfit has a rat.\",\"Language\":\"English, Cantonese\",\"Country\":\"USA, Hong Kong\",\"Awards\":\"Won 4 Oscars. Another 87 wins & 77 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTI1MTY2OTIxNV5BMl5BanBnXkFtZTYwNjQ4NjY3._V1_SX300.jpg\",\"Metascore\":\"86\",\"imdbRating\":\"8.5\",\"imdbVotes\":\"697,806\",\"imdbID\":\"tt0407887\",\"Type\":\"movie\",\"tomatoMeter\":\"91\",\"tomatoImage\":\"certified\",\"tomatoRating\":\"8.2\",\"tomatoReviews\":\"262\",\"tomatoFresh\":\"238\",\"tomatoRotten\":\"24\",\"tomatoConsensus\":\"Featuring outstanding work from an excellent cast that includes Jack Nicholson, Leonardo DiCaprio, and Matt Damon, The Departed is a thoroughly engrossing gangster drama with the gritty authenticity and soupy morality that has infused director Martin Scorsese's past triumphs.\",\"tomatoUserMeter\":\"94\",\"tomatoUserRating\":\"4.1\",\"tomatoUserReviews\":\"729,325\",\"DVD\":\"13 Feb 2007\",\"BoxOffice\":\"$132.3M\",\"Production\":\"Warner Bros. Pictures\",\"Website\":\"http://www.thedeparted.com/\",\"Response\":\"True\"}\n";
    private static String nbynw = "{\"Title\":\"North by Northwest\",\"Year\":\"1959\",\"Rated\":\"Approved\",\"Released\":\"26 Sep 1959\",\"Runtime\":\"136 min\",\"Genre\":\"Action, Adventure, Mystery\",\"Director\":\"Alfred Hitchcock\",\"Writer\":\"Ernest Lehman\",\"Actors\":\"Cary Grant, Eva Marie Saint, James Mason, Jessie Royce Landis\",\"Plot\":\"A hapless New York advertising executive is mistaken for a government agent by a group of foreign spies, and is pursued across the country while he looks for a way to survive.\",\"Language\":\"English\",\"Country\":\"USA\",\"Awards\":\"Nominated for 3 Oscars. Another 8 wins & 4 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMjQwMTQ0MzgwNl5BMl5BanBnXkFtZTgwNjc4ODE4MzE@._V1_SX300.jpg\",\"Metascore\":\"N/A\",\"imdbRating\":\"8.4\",\"imdbVotes\":\"186,356\",\"imdbID\":\"tt0053125\",\"Type\":\"movie\",\"tomatoMeter\":\"100\",\"tomatoImage\":\"certified\",\"tomatoRating\":\"9\",\"tomatoReviews\":\"62\",\"tomatoFresh\":\"62\",\"tomatoRotten\":\"0\",\"tomatoConsensus\":\"Gripping, suspenseful, and visually iconic, this late-period Hitchcock classic laid the groundwork for countless action thrillers to follow.\",\"tomatoUserMeter\":\"94\",\"tomatoUserRating\":\"4.2\",\"tomatoUserReviews\":\"76,714\",\"DVD\":\"29 Aug 2000\",\"BoxOffice\":\"N/A\",\"Production\":\"Turner Entertainment\",\"Website\":\"N/A\",\"Response\":\"True\"}";
    private static String obwat = "{\"Title\":\"O Brother, Where Art Thou?\",\"Year\":\"2000\",\"Rated\":\"PG-13\",\"Released\":\"02 Feb 2001\",\"Runtime\":\"106 min\",\"Genre\":\"Adventure, Comedy, Crime\",\"Director\":\"Joel Coen, Ethan Coen\",\"Writer\":\"Homer (epic poem \\\"The Odyssey\\\"), Ethan Coen, Joel Coen\",\"Actors\":\"George Clooney, John Turturro, Tim Blake Nelson, John Goodman\",\"Plot\":\"In the deep south during the 1930s, three escaped convicts search for hidden treasure while a relentless lawman pursues them.\",\"Language\":\"English\",\"Country\":\"UK, France, USA\",\"Awards\":\"Nominated for 2 Oscars. Another 7 wins & 30 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTI3MTEwMTAzMF5BMl5BanBnXkFtZTYwMTMxNTI5._V1_SX300.jpg\",\"Metascore\":\"69\",\"imdbRating\":\"7.8\",\"imdbVotes\":\"194,868\",\"imdbID\":\"tt0190590\",\"Type\":\"movie\",\"tomatoMeter\":\"77\",\"tomatoImage\":\"certified\",\"tomatoRating\":\"7.1\",\"tomatoReviews\":\"149\",\"tomatoFresh\":\"115\",\"tomatoRotten\":\"34\",\"tomatoConsensus\":\"Though not as good as Coen brothers' classics such as Blood Simple, the delightfully loopy O Brother, Where Art Thou? is still a lot of fun.\",\"tomatoUserMeter\":\"89\",\"tomatoUserRating\":\"3.7\",\"tomatoUserReviews\":\"254,094\",\"DVD\":\"12 Jun 2001\",\"BoxOffice\":\"$45.2M\",\"Production\":\"Buena Vista\",\"Website\":\"http://studio.go.com/movies/obrother/index.html\",\"Response\":\"True\"}";
    private static String princessBride = "{\"Title\":\"The Princess Bride\",\"Year\":\"1987\",\"Rated\":\"PG\",\"Released\":\"09 Oct 1987\",\"Runtime\":\"98 min\",\"Genre\":\"Adventure, Comedy, Family\",\"Director\":\"Rob Reiner\",\"Writer\":\"William Goldman (book), William Goldman (screenplay)\",\"Actors\":\"Cary Elwes, Mandy Patinkin, Chris Sarandon, Christopher Guest\",\"Plot\":\"A classic fairy tale, with swordplay, giants, an evil prince, a beautiful princess, and yes, some kissing (as read by a kindly grandfather).\",\"Language\":\"English\",\"Country\":\"USA\",\"Awards\":\"Nominated for 1 Oscar. Another 6 wins & 8 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTkzMDgyNjQwM15BMl5BanBnXkFtZTgwNTg2Mjc1MDE@._V1_SX300.jpg\",\"Metascore\":\"77\",\"imdbRating\":\"8.2\",\"imdbVotes\":\"248,485\",\"imdbID\":\"tt0093779\",\"Type\":\"movie\",\"tomatoMeter\":\"97\",\"tomatoImage\":\"certified\",\"tomatoRating\":\"8.3\",\"tomatoReviews\":\"63\",\"tomatoFresh\":\"61\",\"tomatoRotten\":\"2\",\"tomatoConsensus\":\"A delightfully postmodern fairy tale, The Princess Bride is a deft, intelligent mix of swashbuckling, romance, and comedy that takes an age-old damsel-in-distress story and makes it fresh.\",\"tomatoUserMeter\":\"95\",\"tomatoUserRating\":\"4\",\"tomatoUserReviews\":\"522,388\",\"DVD\":\"26 Jan 1999\",\"BoxOffice\":\"N/A\",\"Production\":\"20th Century Fox\",\"Website\":\"http://www.theprincessbride-themovie.com/\",\"Response\":\"True\"}";
    private static String fullMetalJacket = "{\"Title\":\"Full Metal Jacket\",\"Year\":\"1987\",\"Rated\":\"R\",\"Released\":\"10 Jul 1987\",\"Runtime\":\"116 min\",\"Genre\":\"Drama, War\",\"Director\":\"Stanley Kubrick\",\"Writer\":\"Gustav Hasford (novel), Stanley Kubrick (screenplay), Michael Herr (screenplay), Gustav Hasford (screenplay)\",\"Actors\":\"Matthew Modine, Adam Baldwin, Vincent D'Onofrio, R. Lee Ermey\",\"Plot\":\"A pragmatic U.S. Marine observes the dehumanizing effects the U.S.-Vietnam War has on his fellow recruits from their brutal boot camp training to the bloody street fighting in Hue.\",\"Language\":\"English, Vietnamese\",\"Country\":\"UK, USA\",\"Awards\":\"Nominated for 1 Oscar. Another 9 wins & 7 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMjA4NzY4ODk4Nl5BMl5BanBnXkFtZTgwOTcxNTYxMTE@._V1_SX300.jpg\",\"Metascore\":\"78\",\"imdbRating\":\"8.4\",\"imdbVotes\":\"388,265\",\"imdbID\":\"tt0093058\",\"Type\":\"movie\",\"tomatoMeter\":\"94\",\"tomatoImage\":\"certified\",\"tomatoRating\":\"8.4\",\"tomatoReviews\":\"72\",\"tomatoFresh\":\"68\",\"tomatoRotten\":\"4\",\"tomatoConsensus\":\"Intense, tightly constructed, and darkly comic at times, Stanley Kubrick's Full Metal Jacket may not boast the most original of themes, but it is exceedingly effective at communicating them.\",\"tomatoUserMeter\":\"94\",\"tomatoUserRating\":\"4.1\",\"tomatoUserReviews\":\"320,156\",\"DVD\":\"29 Jun 1999\",\"BoxOffice\":\"N/A\",\"Production\":\"Warner Bros.\",\"Website\":\"N/A\",\"Response\":\"True\"}";

    private static String[] movies = {darkKnight, memento, reservoirDogs, beforeSunrise, departed, nbynw, obwat, princessBride, fullMetalJacket};

    private MovieListSingleton() {
        favMovies = new ArrayList<>();


        hasBeenChanged = false;
    }

    public synchronized static MovieListSingleton getInstance() {
        if (mInstance == null)
            mInstance = new MovieListSingleton();

        return mInstance;
    }

    public ArrayList<AHMovie> getFavMovies() {
        return favMovies;
    }

    public void addMovieToFavs(AHMovie movie) {
        if (movie != null && !favMovies.contains(movie)) { //should take O(n) time
            favMovies.add(movie);
            hasBeenChanged = true;
            Log.d("MovieListSingleton", movie.title + "added");
        }
    }

    public void addMovieToFavs(JSONObject movieJSON) {
        addMovieToFavs(AHMovieFactory.build(movieJSON));
    }

    public void addMoviesToFavs(String moviesArray) {
        try {
            JSONArray movies = new JSONArray(moviesArray);
            for (int i = 0; i < movies.length(); i++) {
                addMovieToFavs((JSONObject) movies.get(i));
            }
        } catch (JSONException e) {
            Log.d("MovieListSingleton", e.toString());
        }
    }

    public void setDefaultFavs() {
        for (String movie : movies) {
            try {
                addMovieToFavs(new JSONObject(movie));
            } catch (JSONException e) {
                Log.d("MovieListSingleton", "Could not add movie to list of favorites: " + movie);
            }
        }
    }

    public void removeMovieFromFavs(AHMovie movie) {
        if (movie != null)
            hasBeenChanged = favMovies.remove(movie) || hasBeenChanged;
    }


    //for use in debug
    public String listMovieTitles() {
        String result = "";
        for (AHMovie movie : favMovies) {
            result = result.concat(movie.title + " ");
        }
        return result;
    }

    public String getJSONArray() {
        String result = "[";
        for (AHMovie movie : favMovies) {
            result = result + movie.srcJSON + ",";
        }
        result = result.substring(0, result.length() - 1) + "]";
        return result;
    }
}
