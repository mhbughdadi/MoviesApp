package com.example.mohamed.movieapp.listeners;

import com.example.mohamed.movieapp.data.MovieData;
import com.example.mohamed.movieapp.data.ReviewData;
import com.example.mohamed.movieapp.data.TrailerData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mohamed on 11/25/2016.
 */

public interface OnPostExecution {
    void onPostMovieExec(List<MovieData> movieDatas);
    void onPostTrailerData(List<TrailerData>trailerDatas);
    void onPostReviewData(List<ReviewData>reviewDatas);
}
