package com.example.visudhaajivamapp.docterprofile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.visudhaajivamapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {
    Context context;
    ArrayList<AppointmentModel> dataset;
    String typeName;
    public static String STATUS_URL = "https://visudhajivam.in/android/apvdisapt.php";
    StatusAdapter(Context context, ArrayList<AppointmentModel> dataset,String typeName){
        this.context = context;
        this.dataset = dataset;
        this.typeName = typeName;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_items,parent,false);
        return (new StatusViewHolder(itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        if(!typeName.equals("pending")){
            holder.accept.setVisibility(View.GONE);
            holder.decline.setVisibility(View.GONE);
        }
        AppointmentModel data = dataset.get(position);
        holder.nameText.setText(data.getName());
        holder.emailText.setText(data.getEmail());
        holder.phoneText.setText(data.getPhone());
        holder.dateText.setText(data.getDate());
        holder.timeText.setText(data.getTime());
        holder.otherText.setText(data.getOther());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToServer("approve",position,data);
            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToServer("dismiss",position,data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    class StatusViewHolder extends RecyclerView.ViewHolder{
        TextView nameText,emailText,phoneText,dateText,timeText,otherText;
        Button accept,decline;
        StatusViewHolder(View itemView){
            super(itemView);
            nameText = itemView.findViewById(R.id.name_status);
            emailText =itemView.findViewById(R.id.email_status);
            phoneText =itemView.findViewById(R.id.phone_status);
            dateText = itemView.findViewById(R.id.date_status);
            timeText = itemView.findViewById(R.id.time_status);
            otherText = itemView.findViewById(R.id.others_status);
            accept = itemView.findViewById(R.id.accept_status);
            decline = itemView.findViewById(R.id.decline_status);
        }
    }

   private void sendDataToServer(String status,int index,AppointmentModel model){

       StringRequest request= new StringRequest(Request.Method.POST, STATUS_URL, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               Log.d("statusResponse",response);
               if(response.equals("success")){
                   dataset.remove(index);
                   notifyDataSetChanged();
               }

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(context,"error",Toast.LENGTH_LONG).show();
           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> param=new HashMap<String, String>();
               param.put("id",model.getId());
               param.put("status",status);
               Log.d("sendId",model.getId());
               return param;
           }

       };
       RequestQueue queue= Volley.newRequestQueue(context);
       queue.add(request);

   }

}
