package com.example.mohamed.movieapp.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.movieapp.R;
import com.example.mohamed.movieapp.data.MovieData;
import com.example.mohamed.movieapp.listeners.OnClickItem;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Mohamed on 1/11/2016.
 */
public class RecyclerFavorateAdapter extends RecyclerView.Adapter<RecyclerFavorateAdapter.ViewHolder> {

    private Context myContext;
    private List<MovieData> albumList;
    private OnClickItem onClickItem;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvTitle);
            thumbnail = (ImageView) view.findViewById(R.id.ivPoster);

        }
    }
    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public RecyclerFavorateAdapter(Context myContext, List<MovieData> albumList) {
        this.myContext = myContext;
        this.albumList = albumList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MovieData favorite = albumList.get(position);
        holder.title.setText(favorite.getTitle());
        holder.thumbnail.setImageBitmap(favorite.getPosterBitmap());

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItem.onItemClicked(view, position);
            }
        });

    }
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
