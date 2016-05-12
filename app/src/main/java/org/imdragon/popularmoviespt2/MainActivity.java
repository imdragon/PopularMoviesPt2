package org.imdragon.popularmoviespt2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements PosterFragment.OnFragmentInteractionListener{
GridView gridView;
    public ArrayList moviePosterAddress;
    public ArrayList<Movie> movieObjectArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_posters);

        // check for to see which layout I'm using
        if (findViewById(R.id.poster_container) != null){
            gridView = (GridView) findViewById(R.id.gridview);
            new RequestPopularMovies(this).execute(null,null,null);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e("SAVEDiN", "SAVEDiN");
        outState.putStringArrayList("posters", moviePosterAddress);
        outState.putParcelableArrayList("movies", movieObjectArray);
        super.onSaveInstanceState(outState);
    }

    public void setupGrid() {
        gridView.setAdapter(new ImageAdapter(MainActivity.this, moviePosterAddress));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Movie movieDetails = movieObjectArray.get(position);
                Log.e("movieID", movieObjectArray.get(position).getMovieId());
                Intent i = new Intent(MainActivity.this, DetailsFragment.class);
                i.putExtra("movieInfo", movieDetails);
                startActivity(i);
            }
        });
    }


}
