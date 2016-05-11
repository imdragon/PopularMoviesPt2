package org.imdragon.popularmoviespt2;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends FragmentActivity implements PosterFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_posters);

        // check for to see which layout I'm using
        if (findViewById(R.id.poster_container) != null){
            // if restoring don't do anything further so we don't layer something
            if(savedInstanceState != null){
                return;
            }

            PosterFragment posterFragment = new PosterFragment();
            // add these in case you need to start a new activity
            posterFragment.setArguments(getIntent().getExtras());

            // add the fragment to the layout
            getSupportFragmentManager().beginTransaction().add(R.id.poster_container, posterFragment).commit();
        }

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
