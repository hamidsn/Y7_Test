package android.hamid.com.au.y7;

import android.hamid.com.au.y7.helper.ListAdapter;
import android.hamid.com.au.y7.mappings.Movie;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hamid on 13/11/2015.
 */

public class MainActivity extends AppCompatActivity {

    public static int pageNumber = 0;
    private ListView listView;
    private ListAdapter adapter;
    private List<Movie> movieList;
    private ProgressBar progressBar;
    private boolean loadingMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.toolbar_progress_bar);
        listView = (ListView) findViewById(R.id.listView);

        movieList = new ArrayList<>();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                //Load more data if user scrolls to the bottom
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount) && !(loadingMore)) {
                    showFetchingProgress(true);
                    loadMovies();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMovies() {
        // When false means we reached the maximum number of pages(count)
        if (pageNumber != Integer.MAX_VALUE) {
            final Response.ErrorListener errorHandler = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //todo: Handle error better than showing a single message
                    showErrorReason(volleyError.toString());
                }
            };
            showFetchingProgress(true);
            ApiUtils.getMovieData(new Response.Listener<List<Movie>>() {
                @Override
                public void onResponse(List<Movie> response) {
                    for (int i = 0; i < response.size(); i++) {
                        movieList.add(response.get(i));
                    }
                    updateUI(movieList);
                }
            }, errorHandler, pageNumber);
        } else {
            showErrorReason(getResources().getString(R.string.last_page));
        }
    }

    private void showErrorReason(String message) {
        Snackbar.make(listView, message, Snackbar.LENGTH_LONG)
                .show();
        showFetchingProgress(false);
    }

    private void updateUI(List<Movie> movies) {
        showFetchingProgress(false);
        int lastViewedPosition = listView.getFirstVisiblePosition();
        //get offset of the first visible view
        View v = listView.getChildAt(0);
        int topOffset = (v == null) ? 0 : v.getTop();
        adapter = new ListAdapter(this, movies);
        listView.setAdapter(adapter);
        listView.setSelectionFromTop(lastViewedPosition, topOffset);
    }

    private void showFetchingProgress(boolean isFetching) {
        progressBar.setVisibility(isFetching ? View.VISIBLE : View.GONE);
        loadingMore = isFetching;
    }
}
