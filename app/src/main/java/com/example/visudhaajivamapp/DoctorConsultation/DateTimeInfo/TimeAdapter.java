package com.example.visudhaajivamapp.DoctorConsultation.DateTimeInfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visudhaajivamapp.R;

import java.util.ArrayList;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    Context context;
    ArrayList<String> datasetTime;
    String date;
    String price,name,photo,exp;
    String id;
    TimeAdapter(Context context, ArrayList<String> datasetTime, String date,String price,String name, String photo,String exp,String id){
        this.context = context;
        this.datasetTime = datasetTime;
        this.date=date;
        this.price=price;
        this.name=name;
        this.photo=photo;
        this.exp=exp;
        this.id=id;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_items,parent,false);
        return (new TimeViewHolder(itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        holder.timeText.setText(datasetTime.get(position));
        holder.timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("DATE",date);
                intent.putExtra("TIME",datasetTime.get(position));
                intent.putExtra("price",price);
                intent.putExtra("name",name);
                intent.putExtra("photo",photo);
                intent.putExtra("exp",exp);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datasetTime.size();
    }

    class TimeViewHolder extends RecyclerView.ViewHolder{
        TextView timeText;
        TimeViewHolder(View itemView){
            super(itemView);
            timeText = itemView.findViewById(R.id.time_id);
        }
    }

}
