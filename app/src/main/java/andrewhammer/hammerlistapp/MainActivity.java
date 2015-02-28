package andrewhammer.hammerlistapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
    MovieAdapter adapter;
    private static String MOVIE_JSON = "Movie JSON";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = this.getPreferences(MODE_PRIVATE);
        String moviesJSON = prefs.getString(MOVIE_JSON, "N/A");
        if (moviesJSON.equals("N/A"))
            MovieListSingleton.getInstance().setDefaultFavs();
        else
            MovieListSingleton.getInstance().addMoviesToFavs(moviesJSON);

        ListView movieList = (ListView) findViewById(R.id.fav_movie_list);
        adapter = new MovieAdapter(this, MovieListSingleton.getInstance().getFavMovies());
        movieList.setAdapter(adapter);

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("MainActivity", MovieListSingleton.getInstance().getFavMovies().get(position).title + " click at " + position);
                Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                intent.putExtra(MovieUtils.TARGET_MOVIE_INDEX, position);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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

    @Override
    public void onResume() {
        // Always call the superclass so it can restore the view hierarchy
        super.onResume();

        if(MovieListSingleton.getInstance().hasBeenChanged) {
            adapter.notifyDataSetChanged();
            MovieListSingleton.getInstance().hasBeenChanged = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //back up movie prefs to sharedPrefs
        SharedPreferences prefs = this.getPreferences(MODE_PRIVATE);
        prefs.edit().putString(MOVIE_JSON, MovieListSingleton.getInstance().getJSONArray()).apply();

    }
}