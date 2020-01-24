package com.example.submission4.adapter;



import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.submission4.R;

import com.example.submission4.db.TvHelper;

import com.example.submission4.model.Tvshow;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class TvshowAdapter extends RecyclerView.Adapter<TvshowAdapter.TvshowViewHolder> {
    private Context ctx;
    private ArrayList<Tvshow> tvshows= new ArrayList<>();
    private onItemClickCallBack ocb;
    private TvHelper THelp;

    public TvshowAdapter(TvHelper tHelp, Context context) {
        this.THelp = tHelp;
        this.ctx = context;
    }

    public void setData(ArrayList<Tvshow> items) {
        tvshows.clear();
        tvshows.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvshowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new TvshowViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvshowViewHolder holder, final int position) {

        //holder.bind(tvshows.get(position));
        final Tvshow t = tvshows.get(position);
        holder.txtJudul.setText(t.getJudul());
        holder.txtSinopsis.setText(t.getDesc());
        String url_image = "https://image.tmdb.org/t/p/w185" + t.getPoster_path();
        Glide.with(holder.itemView.getContext())
                .load(url_image)
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(holder.imgPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocb.onItemClicked(tvshows.get(holder.getAdapterPosition()));
            }
        });


    }


    @Override
    public int getItemCount() {
        return tvshows.size();
    }

    public class TvshowViewHolder extends RecyclerView.ViewHolder{
        private TextView txtJudul, txtSinopsis;
        private ImageView imgPhoto;

        public TvshowViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.item_title);
            txtSinopsis = itemView.findViewById(R.id.item_description);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);

        }
    }

    public void filterTvShow(ArrayList<Tvshow> newtvshow){
        tvshows = new ArrayList<>();
        tvshows.addAll(newtvshow);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallBack(onItemClickCallBack onitemclickcallback){
        this.ocb = onitemclickcallback;
    }
    public interface onItemClickCallBack{
        void onItemClicked(Tvshow data);
    }



}
