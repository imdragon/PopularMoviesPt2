package org.imdragon.popularmoviespt2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.imdragon.popularmoviespt2.PosterFragment.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_posters);


        // check for to see which layout I'm using
        if (findViewById(R.id.details_fragment) != null) {
            // if restoring don't do anything further so we don't layer something
            if (savedInstanceState != null) {

            }

//            DetailsFragment detailsFragment = new DetailsFragment();
//            // add these in case you need to start a new activity
//            detailsFragment.setArguments(getIntent().getExtras());
//
//            // add the fragment to the layout
//            getSupportFragmentManager().beginTransaction().add(R.id.details_fragment, detailsFragment).commit();


        }
//        PosterFragment posterFragment = new PosterFragment();
//        posterFragment.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction().add(R.id.poster_container, posterFragment).commit();
//        setHasOptionsMenu(true);

    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.sortChoice) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setTitle(R.string.sort_option).setItems(R.array.sortOptionArray, new DialogInterface.OnClickListener() {
//                //// TODO: 3/8/2016 See about styling the AlertDialog without a new layout
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    if (which == 0) {
//                        setTitle("Most Popular");
//                        new PosterFragment.RequestPopularMovies().execute("popularity.desc", null, null);
//                        new PosterFragment.RequestPopularMovies().execute("popularity.desc", null, null);
//                        new PosterFragment.RequestPopularMovies();
//                        // popularity.desc
//                    }
//                    if (which == 1) {
//                        setTitle("Highest Rated");
//                        // below request shows by highest rating for US movies
//                        new PosterFragment.RequestPopularMovies(PostersFragment.this).execute("certification_country=US&sort_by=vote_average.desc&vote_count.gte=1000", null, null);
//                        // rating.desc
//                    }
//                    if (which == 2) {
//                        getActivity().setTitle("My Favorites");
//                        favoriteLayout();
//                    }
//                }
//            });
//            AlertDialog pop = builder.create();
//            pop.show();
//        }
//        if (item.getItemId() == R.id.deleteAllFavorites) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage("Delete all favorites?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                /**
//                 * This method will be invoked when a button in the dialog is clicked.
//                 *
//                 * @param dialog The dialog that received the click.
//                 * @param which  The button that was clicked (e.g.
//                 *               {@link DialogInterface#BUTTON1}) or the position
//                 */
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    getActivity().getContentResolver().delete(MovDBContract.MovieEntry.CONTENT_URI, null, null);
//                }
//            }).setNegativeButton("NO!", new DialogInterface.OnClickListener() {
//                /**
//                 * This method will be invoked when a button in the dialog is clicked.
//                 *
//                 * @param dialog The dialog that received the click.
//                 * @param which  The button that was clicked (e.g.
//                 *               {@link DialogInterface#BUTTON1}) or the position
//                 */
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//            AlertDialog pop = builder.create();
//            pop.show();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        MenuInflater inflate = getActivity().getMenuInflater();
//        inflate.inflate(R.menu.popup_menu_layout, menu);
//    }


    @Override
    public void onPosterSelected(Movie chosenMovie) {

        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        if (detailsFragment != null) {
            DetailsFragment dFrag = new DetailsFragment();

            Bundle movDetails = new Bundle();

//            Movie movieDetails = chosenMovie;
            Log.e("movieID", chosenMovie.getMovieId());
            movDetails.putParcelable("movieInfo", chosenMovie);
            dFrag.setArguments(movDetails);
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.details_fragment, dFrag);
            transaction.addToBackStack(null);
            transaction.commit();

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

            DetailsFragment dFrag = new DetailsFragment();

            Bundle movDetails = new Bundle();

//            Movie movieDetails = chosenMovie;
            Log.e("movieID", chosenMovie.getMovieId());
            movDetails.putParcelable("movieInfo", chosenMovie);
            dFrag.setArguments(movDetails);
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.details_fragment, dFrag);
            transaction.addToBackStack(null);
            transaction.commit();

        }


    }


}


