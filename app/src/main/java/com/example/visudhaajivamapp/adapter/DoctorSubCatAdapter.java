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
import com.example.visudhaajivamapp.DoctorConsultation.Specialist;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.models.DoctorsSubCat;

import java.util.List;

public class DoctorSubCatAdapter extends RecyclerView.Adapter<DoctorSubCatAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<DoctorsSubCat> doctors;
    int position = 0;
    int layout;
    final Context context;
    public DoctorSubCatAdapter(Context context, List<DoctorsSubCat> doctors, int layout){
        this.context = context;
        this.doctors = doctors;
        this.layout=layout;
    }

    @NonNull
    @Override
    public DoctorSubCatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new DoctorSubCatAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DoctorSubCatAdapter.ViewHolder holder, int position) {
        this.position=position;
        // bind the data
        holder.docname.setText(doctors.get(position).getName());
        //holder.doctoraddress.setText(doctors.get(position).getAddress());
        holder.docexp.setText(doctors.get(position).getExperience());
        //holder.doctorspeciality.setText(doctors.get(position).getSpeciality());
        holder.docprice.setText("₹ "+String.valueOf(doctors.get(position).getPrice()));

        Glide.with(context)
                .load(doctors.get(position).getPhoto())
                .into((holder.docphoto));

        //Picasso.get().load(doctors.get(position).getPhoto()).into(holder.docphoto);

    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        TextView docname,doctoraddress,docexp,doctorspeciality,docprice;
        ImageView docphoto;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            docname = itemView.findViewById(R.id.docname);
//            doctoraddress = itemView.findViewById(R.id.doctoraddress);
            docexp = itemView.findViewById(R.id.docexp);
//            doctorspeciality = itemView.findViewById(R.id.doctorspeciality);
            docprice = itemView.findViewById(R.id.docprice);
            docphoto = itemView.findViewById(R.id.docphoto);


            // handle onClick

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position =this.getAdapterPosition();
            DoctorsSubCat s = doctors.get(position);
            String name = s.getName();
            String speciality = s.getSpeciality();
            String address = s.getAddress();
            String experience = s.getExperience();
            String price = s.getPrice();
            String photo = s.getPhoto();
            // Toast.makeText(v.getContext(), "Do Something With this Click" , Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(v.getContext(), Specialist.class );
            intent.putExtra("id",String.valueOf(doctors.get(position).getId()));
            intent.putExtra("name", name);
            intent.putExtra("photo", photo);
            intent.putExtra("address", address);
            intent.putExtra("experience", experience);
            intent.putExtra("price", price);
            intent.putExtra("speciality", speciality);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivities(new Intent[]{intent});



        }
    }
}
