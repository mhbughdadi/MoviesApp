package com.example.mohamed.movieapp.ui.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mohamed.movieapp.R;
import com.example.mohamed.movieapp.data.MovieData;
import com.example.mohamed.movieapp.listeners.OnMovieListener;
import com.example.mohamed.movieapp.ui.fragments.DetailsFragment;
import com.example.mohamed.movieapp.ui.fragments.MoviesFragment;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MoviesFragment moviesFragment =MoviesFragment.getInstance();
       /* moviesFragment.setFavorateListener(new MoviesFragment.FavorateListener() {
            @Override
            public void openFavorates() {
                Intent intent=new Intent(MainActivity.this,FavorateActivity.class);
                startActivity(intent);
            }
        });*/
        moviesFragment.setOnMovieListener(new OnMovieListener() {
            @Override
            public void onMovie(MovieData movie) {
                if(null!=findViewById(R.id.detaisFragmentLayout))
                {
                    DetailsFragment detailsFragment=DetailsFragment.getInstance(movie);
                    //Bundle bundle=new Bundle();
                    //bundle.putSerializable(DetailsFragment.OBJECT_TAG,movie);
                   // detailsFragment.setArguments(bundle);
//                    detailsFragment.setMovie(movie);
                    getSupportFragmentManager().beginTransaction().replace(R.id.detaisFragmentLayout,detailsFragment,"details").commit();
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra(DetailsFragment.OBJECT_TAG, (Parcelable) movie);
                    startActivity(intent);
                }
            }
        });
        manageFragments(moviesFragment);
    }

    private void manageFragments(MoviesFragment moviesFragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();

        trans.add(R.id.recyclerFragmentLayout, moviesFragment, "recycler");
        trans.commit();

    }

}
