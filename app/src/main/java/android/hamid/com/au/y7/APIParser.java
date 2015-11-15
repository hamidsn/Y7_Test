package android.hamid.com.au.y7;

import android.hamid.com.au.y7.mappings.Movie;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hamid on 13/11/2015.
 */
public class APIParser {
    public static List<Movie> parseMovies(JSONObject jsonObject) throws JSONException{
            List<Movie> movies = new ArrayList<Movie>();
            JSONArray jMovies = jsonObject.getJSONArray("results");
            for (int i = 0; i < jMovies.length(); i++) {
                movies.add(new Gson()
                        .fromJson(jMovies.getJSONObject(i).toString(), Movie.class));
            }
            return movies;
    }
}
