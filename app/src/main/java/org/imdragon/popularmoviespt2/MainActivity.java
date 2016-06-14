package org.imdragon.popularmoviespt2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.imdragon.popularmoviespt2.PosterFragment.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {


    private Movie curMovie;
    PosterFragment posterFragment;

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

            posterFragment = new PosterFragment();
            posterFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.only_container, posterFragment, "postersFragment").commit();
        }
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("currentMovie")) {
                Log.e("MainActivity", "Contains currentMovie");
                onPosterSelected((Movie) savedInstanceState.getParcelable("currentMovie"));
            }
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


