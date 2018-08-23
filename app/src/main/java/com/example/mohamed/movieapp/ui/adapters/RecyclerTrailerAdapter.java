package com.example.mohamed.movieapp.ui.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.movieapp.R;
import com.example.mohamed.movieapp.data.TrailerData;
import com.example.mohamed.movieapp.listeners.OnClickItem;

import java.util.List;

/**
 * Created by Mohamed on 11/25/2016.
 */

public class RecyclerTrailerAdapter extends RecyclerView.Adapter<RecyclerTrailerAdapter.ViewHolder> {
    private List<TrailerData> data;
    private Context context;

    public RecyclerTrailerAdapter(Context context) {
        this.context=context;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    private OnClickItem onClickItem;
    public RecyclerTrailerAdapter(Context context,List<TrailerData>data)
    {
        this.context=context;
        this.data=data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.trailer_recycler_item,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.trailerTitle.setText(data.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onItemClicked(v,position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView photo;
        TextView trailerTitle;

        public ViewHolder(View itemView) {

            super(itemView);
            trailerTitle= (TextView) itemView.findViewById(R.id.trailerTitle);
            photo=(ImageView)itemView.findViewById(R.id.ivPhoto);

        }
    }
}
