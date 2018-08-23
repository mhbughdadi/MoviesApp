package com.example.mohamed.movieapp.ui.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohamed.movieapp.R;
import com.example.mohamed.movieapp.data.database.MovieContract;
import com.example.mohamed.movieapp.data.database.MovieHelper;
import com.example.mohamed.movieapp.listeners.OnClickItem;
import com.example.mohamed.movieapp.listeners.OnMovieListener;
import com.example.mohamed.movieapp.ui.adapters.RecyclerFavorateAdapter;
import com.example.mohamed.movieapp.ui.adapters.RecyclerMovieAdapter;
import com.example.mohamed.movieapp.data.MovieData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 11/11/2016.
 */

public class FavorateFragment extends Fragment {
    public static final String OBJ = "object";
    private RecyclerView movieList;
    private OnMovieListener onMovieListener;
    private Button btnAddToFav;
    List<MovieData> dataList;
    private SQLiteDatabase db;
    private RecyclerFavorateAdapter adapter;
    private static FavorateFragment instance;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MoviesFragment.ARRAYOBJECTS, (ArrayList<? extends Parcelable>) dataList);
        super.onSaveInstanceState(outState);
    }

    public static FavorateFragment getInstance()
    {
        if(instance==null)
        {
            instance=new FavorateFragment();
        }
        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();
//        dataList = getFavoratefromDB();
//        initializeListView(dataList);
    }
    public void initializeListView(final List<MovieData>data)
    {
        adapter=new RecyclerFavorateAdapter(this.getActivity(),data);


        adapter.setOnClickItem(new OnClickItem() {
            @Override
            public void onItemClicked(View view, int position) {
                onMovieListener.onMovie(data.get(position));
            }
        });


        Toast.makeText(FavorateFragment.this.getContext(), ""+dataList.size(), Toast.LENGTH_SHORT).show();
        movieList.setNestedScrollingEnabled(false);
        movieList.setAdapter(adapter);
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container,false);
        movieList = (RecyclerView) view.findViewById(R.id.recyclerView);
        movieList.setNestedScrollingEnabled(false);
        db=new MovieHelper(this.getActivity()).getWritableDatabase();


        movieList.setLayoutManager(new GridLayoutManager(this.getActivity(),2));
        if(savedInstanceState!=null&&savedInstanceState.containsKey(MoviesFragment.ARRAYOBJECTS))
        {
            dataList=savedInstanceState.getParcelableArrayList(MoviesFragment.ARRAYOBJECTS);

        }
        else
             dataList = getFavoratefromDB();
        initializeListView(dataList);
        return view;
    }

    public void setOnMovieListener(com.example.mohamed.movieapp.listeners.OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }



    public List<MovieData> getFavoratefromDB()
    {
        Cursor cursor=db.query(MovieContract.TABLE_NAME,null,null,null,null,null,null);
        List<MovieData> data=new ArrayList<>();
        if (cursor!=null&&cursor.getCount()>0)
        {
            cursor.moveToFirst();
            while (cursor.moveToNext())
            {
                int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(MovieContract._ID)));
                String title=cursor.getString(cursor.getColumnIndex(MovieContract.TITLE));
                String originalTitle=cursor.getString(cursor.getColumnIndex(MovieContract.ORIGINAL_TITLE));
                String poster=cursor.getString(cursor.getColumnIndex(MovieContract.POSTER_PATH));
                String release=cursor.getString(cursor.getColumnIndex(MovieContract.RELEASE_DATE));
                String overview=cursor.getString(cursor.getColumnIndex(MovieContract.OVERVIEW));
                int voteCount=Integer.parseInt(cursor.getString(cursor.getColumnIndex(MovieContract.VOTE_COUNT)));
                double voteAverage=Double.parseDouble(cursor.getString(cursor.getColumnIndex(MovieContract.VOTE_AVERAGE)));
                double popularity=Double.parseDouble(cursor.getString(cursor.getColumnIndex(MovieContract.POPULARITY)));
                boolean favorate=(cursor.getInt(cursor.getColumnIndex(MovieContract.FAVORATE))==1)?true:false;
                byte[] blob=cursor.getBlob(cursor.getColumnIndex(MovieContract.IMAGE));
                Bitmap bitmap=decodeBitmap(blob);
                MovieData movie=new MovieData(id,title,originalTitle,overview,poster,"",release,voteCount,popularity,voteAverage);
                movie.setPosterBitmap(bitmap);
                movie.setFavorate(favorate);
                data.add(movie);
            }
        }
        return data;
    }
    public Bitmap decodeBitmap(byte[] bytes)
    {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }



}
