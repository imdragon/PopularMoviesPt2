package org.imdragon.popularmoviespt2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.imdragon.popularmoviespt2.PosterFragment.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {


    private Movie curMovie;
    private PosterFragment currentPostersState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_posters);


        // check for to see which layout I'm using
        if (findViewById(R.id.only_container) != null) {
            // if restoring don't do anything further so we don't layer something
            if (savedInstanceState != null) {
                return;
            }
//            Log.e("DetailsFrag", "WAS null");
//            onPosterSelected((Movie) savedInstanceState.getParcelable("currentMovie"));
//            DetailsFragment detailsFragment = new DetailsFragment();
//            // add these in case you need to start a new activity
//            detailsFragment.setArguments(getIntent().getExtras());
//
//            // add the fragment to the layout
//            getSupportFragmentManager().beginTransaction().add(R.id.poster_container, detailsFragment).commit();
            PosterFragment posterFragment = new PosterFragment();
            posterFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.only_container, posterFragment, "postersFragment").commit();
        }

    }


    @Override
    public void onPosterSelected(Movie chosenMovie) {
        curMovie = chosenMovie;

        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        if (detailsFragment != null) {
            Log.e("PosterFragment", "IF ran");

            detailsFragment.updateDetailsFragment(chosenMovie);

        } else {
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
            Log.e("PosterFragment", "ELSE ran");
            DetailsFragment dFrag = new DetailsFragment();
            Bundle movDetails = new Bundle();

            Log.e("movieID", chosenMovie.getMovieId());
            movDetails.putParcelable("movieInfo", chosenMovie);
            dFrag.setArguments(movDetails);
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.only_container, dFrag, "details");
            transaction.addToBackStack("tag");
            transaction.commit();

        }


    }

//    /**
//     * Take care of popping the fragment back stack or finishing the activity
//     * as appropriate.
//     */
//    @Override
//    public void onBackPressed() {
//        Log.e("MainActivity", "backpressed Called");
//        PosterFragment originalPosters = (PosterFragment) getSupportFragmentManager().findFragmentByTag("postersFragment");
//       if (originalPosters.isVisible()){
//           return;
//       } else {
//           getSupportFragmentManager().popBackStack();
//       }
//
//    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e("savedFragment", "DONE");
        outState.putParcelable("currentMovie", curMovie);
        super.onSaveInstanceState(outState);
    }
}


