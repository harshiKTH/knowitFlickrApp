package com.flickrapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flickrapp.Models.Items;
import com.flickrapp.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<Items> items;
    private Context mCtx;
    private  onClickListener mListerner;

    //custom interface for passing the index position from recycle adapter
    public interface onClickListener{
        void onItemClick(int position);
    }

    public void setOnclickListner(onClickListener listner){
        mListerner = listner;
    }


    public DataAdapter(Context mCtx,ArrayList<Items> items) {
        this.items = items;
        this.mCtx = mCtx;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        Glide.with(mCtx)
                .load(items.get(position).getMedia().getM())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.flickr_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListerner!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListerner.onItemClick(position);
                        }
                    }

                }
            }) ;

        }
    }
    public void updateView(ArrayList<Items> newItems){
        items = new ArrayList<>();
        items.addAll(newItems);
        notifyDataSetChanged();

    }
}