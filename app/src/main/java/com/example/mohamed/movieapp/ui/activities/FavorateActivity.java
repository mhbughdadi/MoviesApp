package com.example.mohamed.movieapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mohamed.movieapp.R;
import com.example.mohamed.movieapp.data.MovieData;
import com.example.mohamed.movieapp.listeners.OnMovieListener;
import com.example.mohamed.movieapp.ui.fragments.DetailsFragment;
import com.example.mohamed.movieapp.ui.fragments.FavorateFragment;
import com.example.mohamed.movieapp.ui.fragments.MoviesFragment;

import java.io.Serializable;

/**
 * Created by Mohamed on 11/25/2016.
 */

public class FavorateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorate_activity);
        FavorateFragment favorateFragment = FavorateFragment.getInstance();
        favorateFragment.setOnMovieListener(new OnMovieListener() {
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
                    Intent intent = new Intent(FavorateActivity.this, DetailsActivity.class);

                    intent.putExtra(DetailsFragment.OBJECT_TAG,  movie);
                    startActivity(intent);
                }
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.recyclerFavorateLayout,favorateFragment).commit();
    }

}
