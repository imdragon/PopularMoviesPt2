package org.imdragon.popularmoviespt2;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RequestPopularMovies extends AsyncTask<String, Void, Void> {
    StringBuilder total = new StringBuilder();
    public Activity callingActivity;
    private JSONObject popArray;
    private JSONArray movies;

    public RequestPopularMovies(Activity activity) {
        callingActivity = activity;
    }

    final String MOVIE_BLOCKS = "results";
    final String MOVIE_TITLE = "original_title";
    final String MOVIE_POSTER = "poster_path";
    final String MOVIE_BACKDROP = "backdrop_path";
    final String MOVIE_OVERVIEW = "overview";
    final String MOVIE_RATING = "vote_average";
    final String MOVIE_RELEASE_DATE = "release_date";
    final String MOVIE_ID = "id";
    final String MOVIE_RUNTIME = "runtime";

    @Override
    protected Void doInBackground(String... params) {

        // using the apikey in a separate string file to protect the apikey
        try {
            URL url = new URL("https://api.themoviedb.org/3/discover/movie?sort_by=" + params[0] + "&api_key=" + callingActivity.getString(R.string.apiKey));
            // making url request and sending it to be read
            Log.e("my url is", String.valueOf(url));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // preparing a reader to go through the response
            BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            // below allows for controlled reading of potentially large text
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            //JSON section
            popArray = new JSONObject(total.toString());
            movies = popArray.getJSONArray(MOVIE_BLOCKS);
            ((MainActivity) callingActivity).moviePosterAddress.clear();
            ((MainActivity) callingActivity).movieObjectArray = new ArrayList<>();
            // get movie poster filenames and put in an array
            for (int i = 0; i < movies.length(); i++) {
                Movie tempMovie = new Movie();
                JSONObject aTitle = movies.getJSONObject(i);
                ((MainActivity) callingActivity).moviePosterAddress.add(aTitle.getString(MOVIE_POSTER));
                // check to see if working
                Log.e("poster path" + i, aTitle.getString(MOVIE_POSTER));
                // Build movie object
                tempMovie.setTitle(aTitle.getString(MOVIE_TITLE));
                tempMovie.setPoster(aTitle.getString(MOVIE_POSTER));
                tempMovie.setSynopsis(aTitle.getString(MOVIE_OVERVIEW));
                tempMovie.setRating(aTitle.getString(MOVIE_RATING));
                tempMovie.setReleaseDate(aTitle.getString(MOVIE_RELEASE_DATE));
                tempMovie.setBackdrop(aTitle.getString(MOVIE_BACKDROP));
                tempMovie.setMovieId(aTitle.getString(MOVIE_ID));
                ((MainActivity) callingActivity).movieObjectArray.add(tempMovie);

            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {((MainActivity)callingActivity).setupGrid();
    }
}