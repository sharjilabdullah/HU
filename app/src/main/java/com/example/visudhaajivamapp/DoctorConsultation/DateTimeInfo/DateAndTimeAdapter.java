package com.example.visudhaajivamapp.DoctorConsultation.DateTimeInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visudhaajivamapp.R;

import java.util.ArrayList;

public class DateAndTimeAdapter extends RecyclerView.Adapter<DateAndTimeAdapter.DateAndTimeViewHolder> {
    Context context;
    ArrayList<DateModel> dataset;
    String price, name,photo,exp;
    String id;
    DateAndTimeAdapter(Context context, ArrayList<DateModel> dataset, String price, String name, String photo, String exp, String id){
        this.context = context;
        this.dataset = dataset;
        this.price=price;
        this.name=name;
        this.photo=photo;
        this.exp=exp;
        this.id=id;
    }

    @NonNull
    @Override
    public DateAndTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_items,parent,false);
        return (new DateAndTimeViewHolder(itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull DateAndTimeViewHolder holder, int position) {
        holder.dateText.setText(dataset.get(position).date);
        holder.dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.timeRecycler.getVisibility() == View.GONE){
                    holder.timeRecycler.setVisibility(View.VISIBLE);
                    holder.expandImage.setImageResource(R.drawable.expand_less);
                }
                else{
                    holder.timeRecycler.setVisibility(View.GONE);
                    holder.expandImage.setImageResource(R.drawable.expand_more);
                }
            }
        });
        setTime(holder,position);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    class DateAndTimeViewHolder extends RecyclerView.ViewHolder{
        TextView dateText;
        RecyclerView timeRecycler;
        LinearLayout dateLayout;
        ImageView expandImage;
        DateAndTimeViewHolder(View itemView){
            super(itemView);
            dateText = itemView.findViewById(R.id.date_id);
            timeRecycler = itemView.findViewById(R.id.time_recycler_id);
            dateLayout = itemView.findViewById(R.id.date_linear_layout_id);
            expandImage = itemView.findViewById(R.id.expand_id);
        }
    }

    private void setTime(DateAndTimeViewHolder holder,int index){
        holder.timeRecycler.setLayoutManager(new LinearLayoutManager(context));
        if(holder.timeRecycler.getItemDecorationCount() == 0){
            DividerItemDecoration decoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
            decoration.setDrawable(ContextCompat.getDrawable(context,R.drawable.time_divider));
            holder.timeRecycler.addItemDecoration(decoration);
        }

        TimeAdapter timeAdapter = new TimeAdapter(context,dataset.get(index).getTimeList(),dataset.get(index).getDate(),price,name,photo,exp,id);

        holder.timeRecycler.setAdapter(timeAdapter);
    }



}
