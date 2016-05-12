package org.imdragon.popularmoviespt2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements PosterFragment.OnFragmentInteractionListener {
    public ArrayList moviePosterAddress = new ArrayList();
    public ArrayList<Movie> movieObjectArray;

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
    public void onFragmentInteraction(int position) {
        Toast.makeText(MainActivity.this, position, Toast.LENGTH_SHORT).show();
    }


}


