package com.example.mohamed.movieapp.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohamed.movieapp.R;
import com.example.mohamed.movieapp.data.ReviewData;
import com.example.mohamed.movieapp.data.TrailerData;
import com.example.mohamed.movieapp.listeners.OnClickItem;
import com.example.mohamed.movieapp.listeners.OnMovieListener;
import com.example.mohamed.movieapp.listeners.OnPostExecution;
import com.example.mohamed.movieapp.ui.activities.FavorateActivity;
import com.example.mohamed.movieapp.ui.activities.MainActivity;
import com.example.mohamed.movieapp.ui.adapters.RecyclerMovieAdapter;
import com.example.mohamed.movieapp.data.MovieData;
import com.example.mohamed.movieapp.network.FetchMovies;
import com.example.mohamed.movieapp.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mohamed on 11/11/2016.
 */

public class MoviesFragment extends Fragment {
    public static final String OBJ = "object";
    private RecyclerView movieList;
    private OnMovieListener onMovieListener;
    private Button btnAddToFav;
//    List<MovieData> dataList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static  MoviesFragment instance;
    private RecyclerMovieAdapter adapter;
    public static  final String ARRAYOBJECTS="array_of_objects";
    private List<MovieData> dataList;

    public static  MoviesFragment getInstance()
    {
        if(instance==null)
        {
            instance=new MoviesFragment();
//            Bundle bundle=new Bundle();
//            bundle.putSerializable(DetailsFragment.OBJECT_TAG,movie);


        }

        return instance;
    }

    public void initializeListView(final List<MovieData>data)
    {
        dataList=new ArrayList<>();
        dataList.clear();
//        dataList=data;
        for (int i=0;i<data.size();i++)
        {
            dataList.add(data.get(i));

        }
        adapter=new RecyclerMovieAdapter(this.getActivity(),data);

        movieList.removeAllViews();
        adapter.setOnClickItem(new OnClickItem() {
            @Override
            public void onItemClicked(View view, int position) {
                onMovieListener.onMovie(data.get(position));
            }
        });


//      /  Toast.makeText(MoviesFragment.this.getContext(), ""+dataList.size(), Toast.LENGTH_SHORT).show();
        movieList.setNestedScrollingEnabled(true);
        movieList.setAdapter(adapter);
        this.setFavorateListener(new MoviesFragment.FavorateListener() {
            @Override
            public void openFavorates() {
                Intent intent=new Intent(MoviesFragment.this.getActivity(),FavorateActivity.class);
                MoviesFragment.this.getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
//        fillList("http://api.themoviedb.org/3/movie/"+sharedPreferences.getString(getResources().getString(R.string.option),getResources().getString(R.string.popular))+"?");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MoviesFragment.ARRAYOBJECTS, (ArrayList<? extends Parcelable>) dataList);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        editor=sharedPreferences.edit();
        Toast.makeText(this.getActivity(), sharedPreferences.getString(getResources().getString(R.string.option),getResources().getString(R.string.popular_default)), Toast.LENGTH_SHORT).show();
        movieList = (RecyclerView) view.findViewById(R.id.recyclerView);

        setHasOptionsMenu(true);
        movieList.setLayoutManager(new GridLayoutManager(this.getActivity().getApplicationContext(), 2));
//        dataList = new ArrayList<MovieData>();
        if(savedInstanceState!=null&&savedInstanceState.containsKey(MoviesFragment.ARRAYOBJECTS))
        {
            dataList=savedInstanceState.getParcelableArrayList(MoviesFragment.ARRAYOBJECTS);
            initializeListView(dataList);
        }
        else{
            fillList("http://api.themoviedb.org/3/movie/"+sharedPreferences.getString(getResources().getString(R.string.option),getResources().getString(R.string.popular_default))+"?");

        }



//                Toast.makeText(MoviesFragment.this.getContext(), ""+dataList.size(), Toast.LENGTH_SHORT).show();


        return view;
    }

    public void setOnMovieListener(com.example.mohamed.movieapp.listeners.OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    public void fillList(String url) {

        if (Utils.isConnectionOpen(this.getActivity())) {
            FetchMovies conn = new FetchMovies(this.getContext());

            conn.execute(url);
            conn.setOnPostExecution(new OnPostExecution() {
                @Override
                public void onPostMovieExec(final List<MovieData> movieDatas) {
//                    Toast.makeText(MoviesFragment.this.getContext(), "" + movieDatas.size(), Toast.LENGTH_SHORT).show();

//                    for (int i = 0; i <movieDatas.size() ; i++) {
//                        dataList.add(movieDatas.get(i));
//                    }
                    initializeListView(movieDatas);

                }

                @Override
                public void onPostTrailerData(List<TrailerData> trailerDatas) {

                }

                @Override
                public void onPostReviewData(List<ReviewData> reviewDatas) {

                }
            });
        } else {
            Toast.makeText(this.getActivity().getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_movies_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.actionPopular) {
            editor.putString(getResources().getString(R.string.option),getResources().getString(R.string.popular_default));
            editor.commit();
            fillList("http://api.themoviedb.org/3/movie/popular?");


            return true;

        }
        if (item.getItemId() == R.id.actionTopRated) {
            editor.putString(getResources().getString(R.string.option),getResources().getString(R.string.top_rated_default));
            editor.commit();
            fillList("http://api.themoviedb.org/3/movie/top_rated?");
            return true;


        }
        if (item.getItemId() == R.id.actionFavorate) {
            favorateListener.openFavorates();


            return true;
        }
        return false;
    }
    private FavorateListener favorateListener;
    public void setFavorateListener(FavorateListener favorateListener)
    {
        this.favorateListener=favorateListener;
    }
    public interface FavorateListener{
        public void openFavorates();
    }
}
