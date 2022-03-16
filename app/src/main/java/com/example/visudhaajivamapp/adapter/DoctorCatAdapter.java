package com.example.visudhaajivamapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.visudhaajivamapp.DoctorConsultation.PincodeandCat;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.models.DoctorsCat;

import java.util.List;

public class DoctorCatAdapter extends RecyclerView.Adapter<DoctorCatAdapter.ViewHolder>{

    LayoutInflater inflater;
    public final List<DoctorsCat> categorylist;
    int position = 0;
    final int layout;
    final Context context;

    public DoctorCatAdapter(Context context, List<DoctorsCat> categorylist,int layout){
        this.context = context;
        this.categorylist = categorylist;
        this.layout=layout;
    }
    @NonNull
    @Override
    public DoctorCatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new DoctorCatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.position=position;
        // bind the data
        holder.songTitle.setText(categorylist.get(position).getTitle());
        //holder.songArtists.setText(categorylist.get(position).getArtists());

        Glide.with(context)
                .load(categorylist.get(position).getCoverImage())
                .into((holder.songCoverImage));



    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        TextView songTitle,songArtists;
        ImageView songCoverImage;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            songTitle = itemView.findViewById(R.id.songTitle);
            songCoverImage = itemView.findViewById(R.id.coverImage);

            // handle onClick

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position =this.getAdapterPosition();
            DoctorsCat s = categorylist.get(position);
            String name = s.getTitle();
            String description = s.getArtists();
            //Toast.makeText(v.getContext(), "Do Something With this Click" + name + description, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(v.getContext(), PincodeandCat.class );
            intent.putExtra("name1", name);
            intent.putExtra("description", description);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivities(new Intent[]{intent});

        }


    }


}
