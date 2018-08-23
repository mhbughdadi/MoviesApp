package com.example.mohamed.movieapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mohamed.movieapp.R;
import com.example.mohamed.movieapp.ui.fragments.DetailsFragment;
import com.example.mohamed.movieapp.data.MovieData;

/**
 * Created by Mohamed on 11/21/2016.
 */

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        Intent in = getIntent();
        MovieData movie = (MovieData) in.getParcelableExtra(DetailsFragment.OBJECT_TAG);
        DetailsFragment detailsFragment=DetailsFragment.getInstance(movie);
//        detailsFragment.setMovie(movie);
        getSupportFragmentManager().beginTransaction().add(R.id.detaisFragmentLayout,detailsFragment).commit();

//        Toast.makeText(this, movie.getId()+ movie.getTitle() + movie.overview, Toast.LENGTH_SHORT).show();

    }
}
