package org.imdragon.popularmoviespt2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PosterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PosterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PosterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    GridView gridView;
    int selectedMovie = 0;


    protected ArrayList<String> moviePosterAddress = new ArrayList<>();
    public ArrayList<Movie> movieObjectArray;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PosterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PosterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PosterFragment newInstance(String param1, String param2) {
        PosterFragment fragment = new PosterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sortChoice) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.sort_option).setItems(R.array.sortOptionArray, new DialogInterface.OnClickListener() {
                //// TODO: 3/8/2016 See about styling the AlertDialog without a new layout
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        getActivity().setTitle("Most Popular");
                        new RequestPopularMovies().execute("popularity.desc", null, null);
                        // popularity.desc
                    }
                    if (which == 1) {
                        getActivity().setTitle("Highest Rated");
                        // below request shows by highest rating for US movies
                        new RequestPopularMovies().execute("certification_country=US&sort_by=vote_average.desc&vote_count.gte=1000", null, null);
                        // rating.desc
                    }
                    if (which == 2) {
                        getActivity().setTitle("My Favorites");
//                        enable when doing favorites
//                        favoriteLayout();
                    }
                }
            });
            AlertDialog pop = builder.create();
            pop.show();
        }
        if (item.getItemId() == R.id.deleteAllFavorites) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Delete all favorites?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                /**
                 * This method will be invoked when a button in the dialog is clicked.
                 *
                 * @param dialog The dialog that received the click.
                 * @param which  The button that was clicked (e.g.
                 *               {@link DialogInterface#BUTTON1}) or the position
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().getContentResolver().delete(MovDBContract.MovieEntry.CONTENT_URI, null, null);
                }
            }).setNegativeButton("NO!", new DialogInterface.OnClickListener() {
                /**
                 * This method will be invoked when a button in the dialog is clicked.
                 *
                 * @param dialog The dialog that received the click.
                 * @param which  The button that was clicked (e.g.
                 *               {@link DialogInterface#BUTTON1}) or the position
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog pop = builder.create();
            pop.show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater inflate = getActivity().getMenuInflater();
        inflate.inflate(R.menu.popup_menu_layout, menu);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview);
        if (savedInstanceState == null) {
            new RequestPopularMovies().execute("popularity.desc", null, null);
            Log.e("WAS NULL", "WAS NULL");
        } else {
            moviePosterAddress = savedInstanceState.getStringArrayList("posters");
            movieObjectArray = savedInstanceState.getParcelableArrayList("movies");
            selectedMovie = savedInstanceState.getInt("lastChoice", 0);
            setupGrid();
            Log.e("NOT NULL", "NOT NULL");
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onPosterSelected(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPosterSelected(Movie chosenMovie);
    }

    public void setupGrid() {

        gridView.setAdapter(new ImageAdapter(getActivity(), moviePosterAddress));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                sendMovie(movieObjectArray.get(position));
//                Movie movieDetails = movieObjectArray.get(position);
//                Log.e("movieID", movieObjectArray.get(position).getMovieId());
//                Intent i = new Intent(getContext(), DetailsFragment.class);
//                i.putExtra("movieInfo", movieDetails);
//                startActivity(i);
            }
        });
    }

    public void sendMovie(Movie pickedMovie){
        mListener.onPosterSelected(pickedMovie);
    }

        @Override
        public void onSaveInstanceState(Bundle outState) {
        Log.e("SAVEDiN", "SAVEDiN");
        outState.putStringArrayList("posters", moviePosterAddress);
        outState.putParcelableArrayList("movies", movieObjectArray);
        super.onSaveInstanceState(outState);
    }

    public class RequestPopularMovies extends AsyncTask<String, Void, Void> {
        StringBuilder total = new StringBuilder();
        private JSONObject popArray;
        private JSONArray movies;


        final String MOVIE_BLOCKS = "results";
        final String MOVIE_TITLE = "original_title";
        final String MOVIE_POSTER = "poster_path";
        final String MOVIE_BACKDROP = "backdrop_path";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RATING = "vote_average";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_ID = "id";
        final String MOVIE_RUNTIME = "runtime";

        public RequestPopularMovies() {

        }

        @Override
        protected Void doInBackground(String... params) {

            // using the apikey in a separate string file to protect the apikey
            try {
                URL url = new URL("https://api.themoviedb.org/3/discover/movie?sort_by=" + params[0] + "&api_key=" + getString(R.string.apiKey));
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
                moviePosterAddress.clear();
                movieObjectArray = new ArrayList<>();
                // get movie poster filenames and put in an array
                for (int i = 0; i < movies.length(); i++) {
                    Movie tempMovie = new Movie();
                    JSONObject aTitle = movies.getJSONObject(i);
                    moviePosterAddress.add(aTitle.getString(MOVIE_POSTER));
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
                    movieObjectArray.add(tempMovie);

                }
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setupGrid();
//            if(((PosterFragment) callingActivity).isDualPane){
//                ((PosterFragment) callingActivity).updateDetailsFragment();
//            }
        }
    }
}
