package com.example.submission4.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.submission4.R;

import com.example.submission4.db.TvHelper;
import com.example.submission4.model.Tvshow;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


//import static com.example.submission4.db.DBContract.CONTENT_URI;
public class TFavoriteAdapter extends RecyclerView.Adapter<TFavoriteAdapter.TFavoriteViewHolder>{
    private ArrayList<Tvshow> Tvs = new ArrayList<>();
    private TFavoriteAdapter.onItemClickCallBack ocb;
    private TvHelper THelp;

    public TFavoriteAdapter(TvHelper THelp) {
        this.THelp = THelp;
    }

    public void setData(ArrayList<Tvshow> items){
        Tvs.clear();
        Tvs.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listlocal, parent, false);
        return new TFavoriteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TFavoriteViewHolder holder, final int position) {
        final Tvshow tv = Tvs.get(position);
        holder.txtJudul.setText(tv.getJudul()+tv.getId());
        holder.txtSinopsis.setText(tv.getDesc());
        holder.delete.setText(R.string.btn_delete);
        String url_image = "https://image.tmdb.org/t/p/w185" + tv.getPoster_path();
        Glide.with(holder.itemView.getContext())
                .load(url_image)
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(holder.imgPhoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocb.onItemClicked(Tvs.get(holder.getAdapterPosition()));
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle(R.string.title_confirm);
                alert.setMessage(R.string.msg_confirm);
                alert.setCancelable(false);
                alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hasil = THelp.deleteById(tv.getId());
                        if (hasil > 0) {
                            Toast.makeText(v.getContext(), R.string.success_delete, Toast.LENGTH_LONG).show();
                            Tvs.remove(position);
                            notifyItemMoved(position, position);
                            notifyItemRangeChanged(position, Tvs.size());
                            notifyDataSetChanged();

                        } else {
                            Toast.makeText(v.getContext(), R.string.failed_delete, Toast.LENGTH_LONG).show();
                        }
                    }
                })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog Dialog = alert.create();
                Dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Tvs.size();
    }

    public class TFavoriteViewHolder extends RecyclerView.ViewHolder{
        private TextView txtJudul, txtSinopsis;
        private ImageView imgPhoto;
        private Button delete;
        public TFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.item_title);
            txtSinopsis = itemView.findViewById(R.id.item_description);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            delete = itemView.findViewById(R.id.csx);
        }
    }

    public void setOnItemClickCallBack(TFavoriteAdapter.onItemClickCallBack onitemclickcallback){
        this.ocb = onitemclickcallback;
    }
    public interface onItemClickCallBack{
        void onItemClicked(Tvshow data);
    }
}
