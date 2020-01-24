package com.example.submission4.adapter;

import android.content.ContentValues;
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
import com.example.submission4.db.MovieHelper;
import com.example.submission4.model.Movie;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

//import static com.example.submission4.db.DBContract.CONTENT_URI;
import static com.example.submission4.db.DBContract.MovieColumns.CONTENT_URI;

public class MFavoriteAdapter extends RecyclerView.Adapter<MFavoriteAdapter.MFViewHolder> {

    private MFavoriteAdapter.onItemClickCallBack ocb;
    private MovieHelper mHelp;
    private Cursor listMovie;
    private Context ctx;
    public MFavoriteAdapter(MovieHelper mHelp, Context context) {
        this.mHelp = mHelp; this.ctx = context;
    }

    public void setListMovie(Cursor listmovie){
        this.listMovie = listmovie;
    }
    @NonNull
    @Override
    public MFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listlocal, parent, false);
        return new MFViewHolder(v);
    }

    private Movie getItem(int position){
        if(!listMovie.moveToPosition(position)){
            throw new IllegalStateException("position invalid");
        }
        return new Movie(listMovie);
    }
    @Override
    public void onBindViewHolder(@NonNull final MFViewHolder holder, final int position) {
        final Movie m = getItem(position);
        holder.txtJudul.setText(m.getJudul());
        holder.txtSinopsis.setText(m.getDesc());
        holder.delete.setText(R.string.btn_delete);
        String url_image = "https://image.tmdb.org/t/p/w185" + m.getImg_path();
        Glide.with(holder.itemView.getContext())
                .load(url_image)
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(holder.imgPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ocb.onItemClicked(getItem(holder.getAdapterPosition()));
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


                        if (deleteFavourite(m)) {
                            Toast.makeText(v.getContext(), R.string.success_delete, Toast.LENGTH_LONG).show();
                          // listMovie.move(position);
                            listMovie.requery();
                            notifyItemMoved(position, position);
                           notifyItemRangeChanged(position, listMovie.getCount());
                           notifyDataSetChanged();

                           //setListMovie(listMovie);

                        } else {
                            Toast.makeText(v.getContext(),R.string.failed_delete, Toast.LENGTH_LONG).show();
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
        if(listMovie == null){ return  0;}
        return listMovie.getCount();
    }

    public class MFViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJudul, txtSinopsis;
        private ImageView imgPhoto;
        private Button delete;
        public MFViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.item_title);
            txtSinopsis = itemView.findViewById(R.id.item_description);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            delete = itemView.findViewById(R.id.csx);
        }


    }

    public void setOnItemClickCallBack(MFavoriteAdapter.onItemClickCallBack onitemclickcallback){
        this.ocb = onitemclickcallback;
    }
    public interface onItemClickCallBack{
        void onItemClicked(Movie data);
    }

    private boolean deleteFavourite(Movie movieItem) {
        String ID = movieItem.getId();
        Uri uri = Uri.parse(CONTENT_URI+"/"+ID);
        ctx.getContentResolver().delete(uri, null, null);
        return  true;
    }

}
