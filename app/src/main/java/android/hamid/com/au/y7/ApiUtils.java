package android.hamid.com.au.y7;

import android.hamid.com.au.y7.mappings.Movie;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hamid on 13/11/2015.
 * Goal: All API calls in this class
 */
public class ApiUtils {
    public static final String UTF8 = "UTF-8";
    private final static RequestQueue myMainRequestQueue = Volley
            .newRequestQueue(ApiApplication.getAppContext());

    public static void getMovieData(final Response.Listener<List<Movie>> listener, Response.ErrorListener errorListener, int myPageNumber) {
        String url = ApiApplication.getAppContext().getString(R.string.base_URL)
                + String.format(ApiApplication.getAppContext().getString(R.string.method_guid), Integer.toString(myPageNumber));

        Request<List<Movie>> request = new Request<List<Movie>>(
                Request.Method.GET, url, errorListener) {

            @Override
            protected Response<List<Movie>> parseNetworkResponse(NetworkResponse networkResponse) {

                try {
                    String jsonString = new String(networkResponse.data, UTF8);
                    JSONObject jsonObject = new JSONObject(jsonString);
                    int count = jsonObject.getInt("count");
                    if (count - 1 > MainActivity.pageNumber) {
                        MainActivity.pageNumber++;
                    } else {
                        MainActivity.pageNumber = Integer.MAX_VALUE;
                    }
                    //Parse response
                    List<Movie> movies = APIParser.parseMovies(jsonObject);
                    //Send data back to UI thread
                    return Response.success(movies,
                            HttpHeaderParser.parseCacheHeaders(networkResponse));
                } catch (JSONException e) {
                    Log.e(ApiUtils.class
                            .getSimpleName(), "Failed communication with API.  [" + e + "]");
                } catch (UnsupportedEncodingException e) {
                    Log.e(ApiUtils.class.getSimpleName(), "A valid json string" +
                            " was not received from the server [" + e + "]");

                }
                //Error occured
                listener.onResponse(null);
                return null;
            }

            @Override
            protected void deliverResponse(List<Movie> movies) {
                listener.onResponse(movies);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        myMainRequestQueue.add(request);

    }
}
