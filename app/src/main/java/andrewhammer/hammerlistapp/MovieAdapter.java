package andrewhammer.hammerlistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by andrewhammer on 2/22/15.
 */
public class MovieAdapter extends ArrayAdapter<AHMovie> {
    private final Context context;
    private final ArrayList movieList;

    //standard viewholder pattern in order to optimize ListView speed
    static class ViewHolder {
        ImageView poster;
    }

    public MovieAdapter(Context context, ArrayList<AHMovie> movieList) {
        super(context, R.layout.list_element_movie, movieList);
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_element_movie, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.poster = (ImageView) rowView.findViewById(R.id.poster_list_element);
            rowView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        String movieURL = ((AHMovie) movieList.get(position)).poster;

        //way simpler than implementing custom bitmap loading w/ weakreferences
        Picasso.with(context)
                .load(movieURL)
                .into(holder.poster);

        return rowView;
    }
}
