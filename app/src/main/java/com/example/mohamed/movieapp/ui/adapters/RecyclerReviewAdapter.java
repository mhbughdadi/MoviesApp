package com.example.mohamed.movieapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohamed.movieapp.R;
import com.example.mohamed.movieapp.data.ReviewData;
import com.example.mohamed.movieapp.listeners.OnClickItem;

import java.util.List;

/**
 * Created by Mohamed on 11/26/2016.
 */

public class RecyclerReviewAdapter extends RecyclerView.Adapter<RecyclerReviewAdapter.ViewHoder> {
    private Context context;
    private List<ReviewData>data;
    private OnClickItem onClickItem;
    public void setOnClickItem(OnClickItem onClickItem)
    {
        this.onClickItem=onClickItem;
    }

    public RecyclerReviewAdapter(Context context, List<ReviewData> data){
        this.context=context;
        this.data=data;
    }
    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root= LayoutInflater.from(context).inflate(R.layout.review_recycler_item,parent,false);

        return new ViewHoder(root);
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, final int position) {
        holder.tvContent.setText(data.get(position).getContent());
        holder.tvAuthor.setText(data.get(position).getAuther());
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

    public class  ViewHoder extends RecyclerView.ViewHolder{
         TextView tvAuthor;
        TextView tvContent;


        public ViewHoder(View itemView) {
            super(itemView);
            tvAuthor=(TextView)itemView.findViewById(R.id.tvAuthor);
            tvContent=(TextView)itemView.findViewById(R.id.tvContent);
        }
    }
}
