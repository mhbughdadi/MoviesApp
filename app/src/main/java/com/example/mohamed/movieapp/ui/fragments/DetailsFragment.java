package com.example.mohamed.movieapp.ui.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.movieapp.BuildConfig;
import com.example.mohamed.movieapp.R;
import com.example.mohamed.movieapp.data.MovieData;
import com.example.mohamed.movieapp.data.ReviewData;
import com.example.mohamed.movieapp.data.TrailerData;
import com.example.mohamed.movieapp.data.database.MovieContract;
import com.example.mohamed.movieapp.data.database.MovieHelper;
import com.example.mohamed.movieapp.listeners.OnClickItem;
import com.example.mohamed.movieapp.listeners.OnPostExecution;
import com.example.mohamed.movieapp.network.FetchReviews;
import com.example.mohamed.movieapp.network.FetchTrailer;
import com.example.mohamed.movieapp.ui.adapters.RecyclerReviewAdapter;
import com.example.mohamed.movieapp.ui.adapters.RecyclerTrailerAdapter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Mohamed on 11/24/2016.
 */

public class DetailsFragment extends Fragment implements OnPostExecution {
    private RecyclerView trailerRecyclerView;
    public static final String OBJECT_TAG = "movieObject";
    ImageView thumbnail;
    TextView tvOriginal, tvReleasedate, tvVoteCount, tvVoteAverage, tvPpularity, tvOverview, tvTitle;
    private ImageButton btnFav;
    private MovieData movie;
    MovieHelper helper;
    SQLiteDatabase db;
    private RecyclerView reviewRecylerView;
    private static DetailsFragment instance;

    public static DetailsFragment getInstance(MovieData movie) {
        instance = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsFragment.OBJECT_TAG, movie);
        instance.setArguments(bundle);

        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();

        movie = (MovieData) getArguments().getParcelable(DetailsFragment.OBJECT_TAG);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(DetailsFragment.OBJECT_TAG, movie);
        super.onSaveInstanceState(outState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_fragment, container, false);
//        Bundle bundle=getArguments();
//        MovieData movie= (MovieData) bundle.getSerializable(OBJECT_TAG);
        if (savedInstanceState != null && savedInstanceState.containsKey(DetailsFragment.OBJECT_TAG)) {
            movie = savedInstanceState.getParcelable(DetailsFragment.OBJECT_TAG);
        } else
            movie = getArguments().getParcelable(DetailsFragment.OBJECT_TAG);
        inflateDetailsFragment(view, movie);

//        tvReleasedate=(TextView)findViewById(R.id.tv)

        return view;
    }

    RecyclerTrailerAdapter adapter;

    private void inflateDetailsFragment(View view, final MovieData movie) {
        btnFav = (ImageButton) view.findViewById(R.id.btnAddToFav);
        helper = new MovieHelper(DetailsFragment.this.getActivity());
        db = helper.getWritableDatabase();
        Toast.makeText(this.getActivity(), movie.isFavorate() ? "movie is favorate" : "movie is not favorate", Toast.LENGTH_SHORT).show();
        thumbnail = (ImageView) view.findViewById(R.id.ivBackPster);
        if (movie.getPosterBitmap() != null) {
            thumbnail.setImageBitmap(movie.getPosterBitmap());
        } else {
            Picasso.with(this.getActivity()).load("http://image.tmdb.org/t/p/w185/" + movie.getPoster()).into(thumbnail);
        }
        if (helper.isFavorateMovie(db, movie.getId())) {
            btnFav.setImageResource(R.drawable.favorate);
            movie.setFavorate(true);
        } else {
            btnFav.setImageResource(R.drawable.not_favorate);
            movie.setFavorate(false);
        }
        tvOriginal = (TextView) view.findViewById(R.id.tvOriginalTitle);
        tvOriginal.setText(movie.getOriginalTitle());
        tvOverview = (TextView) view.findViewById(R.id.tvOverview);
        tvOverview.setText(movie.getOverview());
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(movie.getTitle());
        tvReleasedate = (TextView) view.findViewById(R.id.tvReleaseDate);
        Toast.makeText(this.getActivity(), movie.getReleaseDate(), Toast.LENGTH_SHORT).show();
        tvReleasedate.setText(movie.getReleaseDate());
        tvVoteCount = (TextView) view.findViewById(R.id.tvVoteCount);
        tvVoteCount.setText(String.valueOf(movie.getVoteCount()));
        tvVoteAverage = (TextView) view.findViewById(R.id.tvVoteAverage);
        tvVoteAverage.setText(getRate(String.valueOf(movie.getVoteAverage())));
        trailerRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerTrailerView);
        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(DetailsFragment.this.getActivity()));
        FetchTrailer fetchTrailer = new FetchTrailer(this.getActivity());
        fetchTrailer.execute("http://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?");
        fetchTrailer.setOnPostExecution(this);
        reviewRecylerView = (RecyclerView) view.findViewById(R.id.recyclerReviewView);
        reviewRecylerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        FetchReviews fetchReviews = new FetchReviews(this.getContext());
        fetchReviews.execute("http://api.themoviedb.org/3/movie/" + movie.getId() + "/reviews?");
        fetchReviews.setOnPostExecution(this);


        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movie.isFavorate()) {
                    addMovieToFavorate(movie);
                    movie.setFavorate(true);

                } else if (movie.isFavorate()) {
                    if (removeMovieFromFavorate(movie) > 0) {
                        movie.setFavorate(false);
                        Toast.makeText(DetailsFragment.this.getContext(), "movie deleted ...", Toast.LENGTH_SHORT).show();


                    }
                }
                btnFav.setImageResource(movie.isFavorate() ? R.drawable.favorate : R.drawable.not_favorate);
            }
        });
    }

    private String getReadableDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY,MMM,DD");

        return simpleDateFormat.format(date);
    }

    private String getRate(String voteAverage) {
        String rate = String.format("%1.2f/%d", Double.parseDouble(voteAverage), 10);
        return rate;
    }

    private int removeMovieFromFavorate(MovieData movie) {
        return db.delete(MovieContract.TABLE_NAME, MovieContract.TITLE + "=?", new String[]{movie.getTitle()});

    }

    private void addMovieToFavorate(MovieData movie) {
        Bitmap bitmap = ((BitmapDrawable) thumbnail.getDrawable()).getBitmap();
        ContentValues values = new ContentValues();
        values.put(MovieContract._ID, movie.getId());
        values.put(MovieContract.TITLE, movie.getTitle());
        values.put(MovieContract.ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(MovieContract.OVERVIEW, movie.getOverview());
        values.put(MovieContract.VOTE_COUNT, movie.getVoteCount());
        values.put(MovieContract.VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieContract.RELEASE_DATE, movie.getReleaseDate());
        values.put(MovieContract.POPULARITY, movie.getPopularity());
        values.put(MovieContract.IMAGE, getByteArrayFromBitMap(bitmap));
        values.put(MovieContract.POSTER_PATH, movie.getPoster());
        values.put(MovieContract.FAVORATE, movie.isFavorate() ? 1 : 0);
        long result = db.insert(MovieContract.TABLE_NAME, null, values);
        if (result > 0) {
            btnFav.setImageResource(R.drawable.favorate);
            Toast.makeText(this.getActivity(), "MovieInserted to the favorate ", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] getByteArrayFromBitMap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    @Override
    public void onPostMovieExec(List<MovieData> movieDatas) {

    }

    @Override
    public void onPostTrailerData(final List<TrailerData> trailerDatas) {
        adapter = new RecyclerTrailerAdapter(DetailsFragment.this.getActivity(), trailerDatas);
        adapter.setOnClickItem(new OnClickItem() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailerDatas.get(position).getKey()));
                startActivity(new Intent().createChooser(in, "Open with "));
            }
        });
        trailerRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onPostReviewData(final List<ReviewData> reviewDatas) {

        RecyclerReviewAdapter adapter = new RecyclerReviewAdapter(DetailsFragment.this.getActivity(), reviewDatas);
        adapter.setOnClickItem(new OnClickItem() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewDatas.get(position).getLink()).buildUpon()
                        .appendQueryParameter("api_key", BuildConfig.THE_MOVIEDB_API_KEY).build());

                startActivity(new Intent().createChooser(in, "Open with"));
            }
        });
        reviewRecylerView.setAdapter(adapter);

    }
}
