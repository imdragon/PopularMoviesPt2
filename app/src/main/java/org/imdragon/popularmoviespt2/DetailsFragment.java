package org.imdragon.popularmoviespt2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    static final String MOVIEDETAILS = "param1";
    static final String ARG_PARAM2 = "param2 ";
//    Movie details = getArguments().getParcelable("movieInfo");
//    Movie details = getActivity().getIntent().getParcelableExtra("movieInfo");

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public String trailerLink;
    Movie details;
    public ArrayList<String> reviews = new ArrayList<>();
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
        View view = inflater.inflate(R.layout.details_view, container, false);
        details = getArguments().getParcelable("movieInfo");

        rAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, reviews);
        ListView reviewsList = (ListView) view.findViewById(R.id.reviewsListView);
        reviewsList.setAdapter(rAdapter);
        Toast.makeText(getContext(), details.getMovieId(),  Toast.LENGTH_SHORT).show();

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
//        fButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addFavorite(v);
//            }
//        });

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
        reviewsList.setAdapter(rAdapter);

        // check restore
        if (savedInstanceState != null) {
            //nothing right now
        }

        // Inflate the layout for this fragment

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

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
}

//public class getTrailerOrReviews extends AsyncTask<Integer, Void, Void> {
//    Fragment callingActivity;
//
//    StringBuilder total = new StringBuilder();
//    String firstTrailer;
//
//    public getTrailerOrReviews(Fragment activity) {
//        callingActivity = activity;
//    }
//
//
//    /**
//     * Override this method to perform a computation on a background thread. The
//     * specified parameters are the parameters passed to {@link #execute}
//     * by the caller of this task.
//     * <p/>
//     * This method can call {@link #publishProgress} to publish updates
//     * on the UI thread.
//     *
//     * @param params The parameters of the task.
//     * @return A result, defined by the subclass of this task.
//     * @see #onPreExecute()
//     * @see #onPostExecute
//     * @see #publishProgress
//     */
//    @Override
//    protected Void doInBackground(Integer... params) {
//        String mID = ((DetailsFragment) callingActivity).details.getMovieId();
//        String parameterTorR = "";
//
//        for (int i = 0; i < 2; i++) {
//            if (i == 0) {
//                parameterTorR = "videos";
//            } else if (i == 1) {
//                parameterTorR = "reviews";
//            }
//            try {
//                URL url = new URL("http://api.themoviedb.org/3/movie/" + mID + "/" + parameterTorR + "?api_key=" + callingActivity.getString(R.string.apiKey));
//                // making url request and sending it to be read
//                Log.e("my url is", String.valueOf(url));
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                // preparing a reader to go through the response
//                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                // below allows for controlled reading of potentially large text
//                String line;
//                while ((line = r.readLine()) != null) {
//                    total.append(line);
//                }
//                if (i == 0) {
//                    getTrailer();
//                    total = new StringBuilder();
//                } else {
//                    getReviews();
//                }
//                Log.e("Trail", total.toString());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    private void getTrailer() throws JSONException {
//        JSONObject popArray = new JSONObject(total.toString());
//
//        JSONArray trailers = popArray.getJSONArray("results");
//        for (int i = 0; i < trailers.length(); i++) {
//            JSONObject trailer = trailers.getJSONObject(i);
//            firstTrailer = trailer.getString("key");
//            ((DetailsFragment) callingActivity).trailerLink = firstTrailer;
//        }
//    }
//
//    private void getReviews() throws JSONException {
//        JSONObject revArray = new JSONObject(total.toString());
//        JSONArray reviews = revArray.getJSONArray("results");
//        for (int i = 0; i < reviews.length(); i++) {
//            StringBuilder sb = new StringBuilder();
//            JSONObject review = reviews.getJSONObject(i);
//            sb.append(review.getString("content"));
//            sb.append("\nReview by:\t");
//            sb.append(review.getString("author"));
//            ((DetailsFragment) callingActivity).reviews.add(sb.toString());
//        }
//        ((DetailsFragment) callingActivity).rAdapter.notifyDataSetChanged();
////        ((DetailsFragment)callingActivity).popReviews();
//
//    }
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        // TODO: 4/28/2016  Add a check for fragment being attached to activity.  Perhaps store movie in movie object and then update from there.
//        // Added check to only update link if it exists and also when the fragment is added
//        if (callingActivity.isAdded() && callingActivity.getActivity().findViewById(R.id.trailerLink) != null) {
//            TextView mTrailer = (TextView) callingActivity.getActivity().findViewById(R.id.trailerLink);
//            mTrailer.setVisibility(View.VISIBLE);
//        }
//
//    }
//}
