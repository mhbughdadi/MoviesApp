package com.example.mohamed.movieapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.movieapp.R;
import com.example.mohamed.movieapp.data.MovieData;
import com.example.mohamed.movieapp.listeners.OnClickItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mohamed on 11/11/2016.
 */

public class RecyclerMovieAdapter extends RecyclerView.Adapter<RecyclerMovieAdapter.ViewHolder> {

    private final Context context;
    private final List<MovieData> data;
    private OnClickItem onClickItem;

    public RecyclerMovieAdapter(Context context, List<MovieData> data) {
        this.context = context;
        this.data = data;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.poster.setImageResource(R.drawable.broken);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + data.get(position).getPoster()).into(holder.poster);
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onItemClicked(v, position);

            }
        });
        holder.title.setText(data.get(position).getTitle().toString());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView poster;
        public TextView title;


        public ViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.ivPoster);

            title = (TextView) itemView.findViewById(R.id.tvTitle);

        }
    }
}
