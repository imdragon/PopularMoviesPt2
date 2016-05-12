package org.imdragon.popularmoviespt2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import org.imdragon.popularmoviespt2.PosterFragment.OnFragmentInteractionListener;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_posters);


        // check for to see which layout I'm using
        if (findViewById(R.id.details_fragment) != null) {
            // if restoring don't do anything further so we don't layer something
            if (savedInstanceState != null) {
                return;
            }

//            DetailsFragment detailsFragment = new DetailsFragment();
//            // add these in case you need to start a new activity
//            detailsFragment.setArguments(getIntent().getExtras());
//
//            // add the fragment to the layout
//            getSupportFragmentManager().beginTransaction().add(R.id.details_fragment, detailsFragment).commit();


        }
        PosterFragment posterFragment = new PosterFragment();
        posterFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.poster_container, posterFragment).commit();

    }


    @Override
    public void onPosterSelected(Movie chosenMovie) {

        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        if (detailsFragment != null) {

        }else{
            ///******* SAMPLE CODE! ***********
//            // If the frag is not available, we're in the one-pane layout and must swap frags...
//
//            // Create fragment and give it an argument for the selected article
//            ArticleFragment newFragment = new ArticleFragment();
//            Bundle args = new Bundle();
//            args.putInt(ArticleFragment.ARG_POSITION, position);
//            newFragment.setArguments(args);
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//            // Replace whatever is in the fragment_container view with this fragment,
//            // and add the transaction to the back stack so the user can navigate back
//            transaction.replace(R.id.fragment_container, newFragment);
//            transaction.addToBackStack(null);
//
//            // Commit the transaction
//            transaction.commit();
            ///******* SAMPLE CODE! ***********

            DetailsFragment dFrag = new DetailsFragment();

            Bundle movDetails = new Bundle();

            Movie movieDetails = chosenMovie;
            Log.e("movieID", chosenMovie.getMovieId());
            movDetails.putParcelable("movieInfo", movieDetails);
            dFrag.setArguments(movDetails);
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.poster_container, dFrag);
            transaction.addToBackStack(null);
            transaction.commit();

        }


    }


}


