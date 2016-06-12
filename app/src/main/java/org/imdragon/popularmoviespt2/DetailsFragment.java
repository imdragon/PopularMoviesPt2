package org.imdragon.popularmoviespt2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    static final String ARG_PARAM2 = "param2 ";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public String trailerLink;
    public Movie details;
    public ArrayList<String> mReviews = new ArrayList<>();
    ArrayAdapter<String> rAdapter;
    Button fButton;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p/>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(Movie movie) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
//        args.putString(MOVIEDETAILS, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(MOVIEDETAILS);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
setRetainInstance(true);
        // check restore
        if (savedInstanceState != null) {
            Log.e("DetailsFragment", "savedInstance is not null");
            if (getArguments() != null) {
                Log.e("DetailsFragment", "has arguments");

                if (getArguments().containsKey("movieInfo")) {
                    Log.e("DetailsFragment", "has movieInfo");
                    Movie temp = getArguments().getParcelable("movieInfo");

Log.e("DetailsFragment", temp.getTitle());

                }
                //nothing right now
            }
        }
        return inflater.inflate(R.layout.details_view, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    public void updateDetailsFragment(Movie incomingMovie) {
        View view = getView();
//        details = getArguments().getParcelable("movieInfo");
        details = incomingMovie;
        rAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mReviews);
        assert view != null;
        ListView reviewsList = (ListView) view.findViewById(R.id.reviewsListView);
        reviewsList.setOnTouchListener(new View.OnTouchListener() {
            /**
             * Called when a touch event is dispatched to a view. This allows listeners to
             * get a chance to respond before the target view.
             *
             * @param v     The view the touch event has been dispatched to.
             * @param event The MotionEvent object containing full information about
             *              the event.
             * @return True if the listener has consumed the event, false otherwise.
             */
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        reviewsList.setAdapter(rAdapter);
        Toast.makeText(getContext(), details.getMovieId(), Toast.LENGTH_SHORT).show();

        TextView mTitle = (TextView) view.findViewById(R.id.original_title_detail);
        mTitle.setText(details.getTitle());
        mTitle.setShadowLayer(25, 0, 0, Color.BLACK);

        ImageView mBackdrop = (ImageView) view.findViewById(R.id.backdropImageView);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w780/" + details.getBackdrop()).into(mBackdrop);
        Log.e("backdrop url", "http://image.tmdb.org/t/p/w780/" + details.getBackdrop());
        ImageView mPoster = (ImageView) view.findViewById(R.id.mPoster);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w780/" + details.getPoster())
                .placeholder(R.drawable.comingsoon).into(mPoster);
        TextView mOverview = (TextView) view.findViewById(R.id.synopsis);
        mOverview.setText(details.getSynopsis());
        TextView mRelease = (TextView) view.findViewById(R.id.releaseDate);
        mRelease.setText("Released: " + details.getReleaseDate().substring(0, 4));
        TextView mRating = (TextView) view.findViewById(R.id.ratingDetail);

        fButton = (Button) view.findViewById(R.id.favButton);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavorite();
            }
        });

        RatingBar mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        mRatingBar.setRating(Float.valueOf(details.getRating()));
        mRating.setText("Rating: " + details.getRating() + "/10");
        TextView trailerText = (TextView) view.findViewById(R.id.trailerLink);
        trailerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchTrailer(v);
            }
        });

        new getTrailerOrReviews().execute(0, null, null);
        favoriteCheck();
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            Log.e("DetailsFragment", "args check");
            Log.e("DetailsFragment", "onStart fired");
//            details = args.getParcelable("movieInfo");
            updateDetailsFragment((Movie) args.getParcelable("movieInfo"));
        } else {
            Log.e("DetailsFragment", "No movie was passed!");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        if(args != null){
            Log.e("DetailsFragment", "onResume fired");
            updateDetailsFragment((Movie) getArguments().getParcelable("movieInfo"));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractio
// nListener");
//        }

    }

    public void watchTrailer(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailerLink)));
    }

    // Favorites Section
    public void addFavorite() {

        ContentValues fav = new ContentValues();

        fav.put(MovDBContract.MovieEntry.COLUMN_MOVIEID, details.getMovieId());
        fav.put(MovDBContract.MovieEntry.COLUMN_TITLE, details.getTitle());
        fav.put(MovDBContract.MovieEntry.COLUMN_DESCRIPTION, details.getSynopsis());
        fav.put(MovDBContract.MovieEntry.COLUMN_POSTER, details.getPoster());
        fav.put(MovDBContract.MovieEntry.COLUMN_BACKDROP, details.getBackdrop());
        fav.put(MovDBContract.MovieEntry.COLUMN_RATING, details.getRating());
        fav.put(MovDBContract.MovieEntry.COLUMN_RELEASE, details.getReleaseDate());
        fav.put(MovDBContract.MovieEntry.COLUMN_FAVORITE, "favorite");

        Uri rUri = getActivity().getContentResolver().insert(MovDBContract.MovieEntry.CONTENT_URI, fav);
        if (rUri == null) {
            removeFavorite();
        } else {
            Toast.makeText(getActivity(), rUri.toString(), Toast.LENGTH_SHORT).show();
            fButton.setBackgroundColor(Color.YELLOW);
            fButton.setText("Favorite!");
        }
    }

    private Boolean favoriteCheck() {
        Boolean flag = false;
        Cursor cs = getActivity().getContentResolver().query(MovDBContract.MovieEntry.CONTENT_URI, new String[]{MovDBContract.MovieEntry.COLUMN_MOVIEID}, null, null, null);
        if (cs == null) {
            return flag;
        } else {
            while (cs.moveToNext()) {
                if (cs.getString(0).equals(details.getMovieId())) {
                    fButton.setBackgroundColor(Color.YELLOW);
                    fButton.setText("Favorite!");
                    flag = true;
                } else {
                    flag = false;
                }
            }
        }
        cs.close();
        return flag;
    }

    private void removeFavorite() {
        getActivity().getContentResolver().delete(MovDBContract.MovieEntry.CONTENT_URI, MovDBContract.MovieEntry.COLUMN_MOVIEID + "=?", new String[]{details.getMovieId()});
        fButton.setText("Mark as\nfavorite");
        fButton.setBackgroundColor(Color.CYAN);
    }


//


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
        void onFragmentInteraction(Movie movie);
    }


    public class getTrailerOrReviews extends AsyncTask<Integer, Void, Void> {

        StringBuilder total = new StringBuilder();

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Integer... params) {
            String mID = details.getMovieId();
            String parameterTorR = "";

            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    parameterTorR = "videos";
                } else if (i == 1) {
                    parameterTorR = "reviews";
                }
                try {
                    URL url = new URL("http://api.themoviedb.org/3/movie/" + mID + "/" + parameterTorR + "?api_key=" + getString(R.string.apiKey));
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
                    if (i == 0) {
                        getTrailer();
                        total = new StringBuilder();
                    } else {
                        getReviews();
                    }
                    Log.e("Trail", total.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        private void getTrailer() throws JSONException {
            JSONObject popArray = new JSONObject(total.toString());

            JSONArray trailers = popArray.getJSONArray("results");
            for (int i = 0; i < trailers.length(); i++) {
                JSONObject trailer = trailers.getJSONObject(i);
                trailerLink = trailer.getString("key");
            }
        }

        private void getReviews() throws JSONException {
            JSONObject revArray = new JSONObject(total.toString());
            JSONArray reviews = revArray.getJSONArray("results");
            for (int i = 0; i < reviews.length(); i++) {
                StringBuilder sb = new StringBuilder();
                JSONObject review = reviews.getJSONObject(i);
                sb.append(review.getString("content"));
                sb.append("\nReview by:\t");
                sb.append(review.getString("author"));
                mReviews.add(sb.toString());
                Log.e("Reviews: ", mReviews.toString());
            }
//            rAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // TODO: 4/28/2016  Add a check for fragment being attached to activity.  Perhaps store movie in movie object and then update from there.
            // Added check to only update link if it exists and also when the fragment is added
            if (isAdded() && getView().findViewById(R.id.trailerLink) != null) {
                TextView mTrailer = (TextView) getView().findViewById(R.id.trailerLink);
                mTrailer.setVisibility(View.VISIBLE);
            }
            rAdapter.notifyDataSetChanged();

        }
    }
}
