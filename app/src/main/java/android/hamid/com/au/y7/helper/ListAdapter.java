package android.hamid.com.au.y7.helper;

import android.app.Activity;
import android.content.Context;
import android.hamid.com.au.y7.ApiApplication;
import android.hamid.com.au.y7.R;
import android.hamid.com.au.y7.mappings.Movie;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hamid on 11/15/15.
 */
public class ListAdapter extends BaseAdapter {
    private Activity activity;
    private List<Movie> movieList;

    public ListAdapter(Activity activity, List<Movie> movieList) {
        this.activity = activity;
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int location) {
        return movieList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_row, null, true);

        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView start_time = (TextView) rowView.findViewById(R.id.start_time);
        TextView end_time = (TextView) rowView.findViewById(R.id.end_time);
        TextView channel = (TextView) rowView.findViewById(R.id.channel);
        TextView rating = (TextView) rowView.findViewById(R.id.rating);

        name.setText(position + 1 + " " +movieList.get(position).getMyName());
        start_time.setText(movieList.get(position).getMyStartTime());
        end_time.setText(movieList.get(position).getMyEndTime());
        channel.setText(movieList.get(position).getMyChannel());
        rating.setText(movieList.get(position).getMyRating());
        if(movieList.get(position).getMyRating().equals("MA")){
            rating.setTextColor(getColor(ApiApplication.getAppContext(), R.color.red));
        }

        return rowView;
    }

    //getColor is depricated on M
    private int  getColor(Context appContext, int color_id) {
        final int version = Build.VERSION.SDK_INT;
        // I do not have M to test
        if (version >= 23) {
            return ContextCompat.getColor(appContext, color_id);
        } else {
            return appContext.getResources().getColor(color_id);
        }
    }

}
