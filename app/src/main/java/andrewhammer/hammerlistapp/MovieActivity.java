package andrewhammer.hammerlistapp;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by andrewhammer on 2/18/15.
 */
public class MovieActivity extends ActionBarActivity {
    private AHMovie movie;
    private String mSrcJSON;

    private static String MOVIE_LOAD_ERROR_MSG = "Could not load or find movie.  Sorry!";
    private static String STATE_SOURCE_JSON = "SRC_JSON";

    private TextView titleView;
    private TextView ratingView;
    private TextView yearView;
    private TextView directorView;
    private TextView actorView;
    private TextView imdbRatingView;
    private TextView rtRatingView;
    private TextView imdbRatingTitleView;
    private TextView rtRatingTitleView;
    private TextView plotView;
    private ImageView posterView;
    private Button webviewButton;
    private Button favoritesButton;

    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Log.d("Movie Activity", "start");

        titleView = (TextView) findViewById(R.id.title);
        ratingView = (TextView) findViewById(R.id.rating);
        yearView = (TextView) findViewById(R.id.year);
        directorView = (TextView) findViewById(R.id.director);
        actorView = (TextView) findViewById(R.id.actors);
        imdbRatingView = (TextView) findViewById(R.id.imdb_rating);
        rtRatingView = (TextView) findViewById(R.id.rotten_tomato_rating);
        plotView = (TextView) findViewById(R.id.synopsis);
        posterView = (ImageView) findViewById(R.id.poster);
        webviewButton = (Button) findViewById(R.id.webviewButton);
        favoritesButton = (Button) findViewById(R.id.fav_Button);
        imdbRatingTitleView = (TextView) findViewById(R.id.imdb);
        rtRatingTitleView = (TextView) findViewById(R.id.rotten_tomatoes);

        handleIntent(getIntent());

        Log.d("Movie Activity", "movie info set");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String requestURL = MovieUtils.movieRequest(intent.getStringExtra(SearchManager.QUERY));
            Log.d("Movie Activity", "Query: " + requestURL);
            new MovieAsyncTask().execute(requestURL);
        } else {
            int i = intent.getIntExtra(MovieUtils.TARGET_MOVIE_INDEX, -1);
            if (i > -1) {
                movie = MovieListSingleton.getInstance().getFavMovies().get(i);
                isFavorite = true;
                setMovieInfo();
            }
        }
    }

    //Sets the movie info stuff
    private void setMovieInfo() {
        if (null != movie) {
            titleView.setText(movie.title);
            ratingView.setText(movie.rating);
            actorView.setText("Starring " + movie.actors);
            directorView.setText("Directed by " + movie.directors);
            yearView.setText(movie.year);
            plotView.setText(movie.synopsis);

            imdbRatingTitleView.setVisibility(View.VISIBLE);
            imdbRatingView.setText(movie.imdbRating);
            setCriticRatingColor(imdbRatingView, true);

            rtRatingTitleView.setVisibility(View.VISIBLE);
            rtRatingView.setText(movie.rtRating);
            setCriticRatingColor(rtRatingView, false);

            Picasso.with(this)
                    .load(movie.poster)
                    .into(posterView);

            mSrcJSON = movie.srcJSON;

            webviewButton.setVisibility(View.VISIBLE);
            webviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(MovieUtils.IMDB_BASE_URL + movie.imdbID));
                    startActivity(intent);

                    Log.d("MovieActivity", "Button pressed to go to url: " + MovieUtils.IMDB_BASE_URL + movie.imdbID);
                }
            });

            favoritesButton.setVisibility(View.VISIBLE);
            setFavoritesButton();
        } else {
            plotView.setText(MOVIE_LOAD_ERROR_MSG);
            titleView.setText("");
            ratingView.setText("");
            actorView.setText("");
            directorView.setText("");
            yearView.setText("");
            webviewButton.setVisibility(View.INVISIBLE);
            favoritesButton.setVisibility(View.INVISIBLE);
            posterView.setImageResource(android.R.color.transparent);

            imdbRatingTitleView.setVisibility(View.INVISIBLE);
            imdbRatingView.setText("");

            rtRatingTitleView.setVisibility(View.INVISIBLE);
            rtRatingView.setText("");

            mSrcJSON = "";
        }
    }

    //Sets the color of a rating textview depending on how good the rating is
    private void setCriticRatingColor(TextView criticRatingView, boolean isIMDB) {
        int color = getResources().getColor(R.color.text_silver);
        if (isIMDB) {
            try {
                double rating = Double.parseDouble(movie.imdbRating);
                if (rating >= 8.0) {
                    color = getResources().getColor(R.color.good_movie_green);
                } else if (rating < 5.0) {
                    color = getResources().getColor(R.color.bad_movie_red);
                }
            } catch (NumberFormatException e) {
                Log.d("MovieActivity Debug", "imdb rating not available");
            }
        } else {
            try {
                double rating = Double.parseDouble(movie.rtRating);
                if (rating >= 80) {
                    color = getResources().getColor(R.color.good_movie_green);
                } else if (rating < 50) {
                    color = getResources().getColor(R.color.bad_movie_red);
                }
            } catch (NumberFormatException e) {
                Log.d("MovieActivity Debug", "rotten tomatoes rating not available");
            }
        }
        criticRatingView.setTextColor(color);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(STATE_SOURCE_JSON, mSrcJSON);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        mSrcJSON = savedInstanceState.getString(STATE_SOURCE_JSON);
        try {
            movie = AHMovieFactory.build(new JSONObject(mSrcJSON));
            setMovieInfo();
        } catch (JSONException e) {
            Log.w("MovieActivity", "JSON download " + e.toString());
            e.printStackTrace();
        }
    }

    private class MovieAsyncTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MovieActivity.this, "", "", true);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                            .url(params[0])
                            .build();

                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                Log.d("Movie Response", responseString);
                return responseString;
            } catch (IOException e) {
                Log.d("Movie Async Task", "Error getting movie info");
                Log.d("Movie Async Task", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject movieJSON = new JSONObject(result);
                movie = AHMovieFactory.build(movieJSON);

                if (null != movie) {
                    if (MovieListSingleton.getInstance().getFavMovies().contains(movie))
                        isFavorite = true;
                    else isFavorite = false;
                }
            } catch (Exception e) {
                movie = null;
                Log.d("Movie Async Task", "Error getting movie info");
                Log.d("Movie Async Task", e.toString());
            } finally {
//                Log.d("MovieActivity", "ProgressDialog status " + progressDialog.getProgress());
                progressDialog.dismiss();
                setMovieInfo();
            }
        }
    }

    private void setFavoritesButton() {
        if (isFavorite) favoritesButton.setText("Remove from favorites");
        else favoritesButton.setText("Add to favorites");

        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) MovieListSingleton.getInstance().removeMovieFromFavs(movie);
                else MovieListSingleton.getInstance().addMovieToFavs(movie);

                isFavorite = !isFavorite;
                setFavoritesButton();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }
}
