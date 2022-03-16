package com.example.visudhaajivamapp.docterprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class AppointmentActivity extends AppCompatActivity {
    private final String APPOINTMENT_URL = "https://visudhajivam.in/android/b.php";
    private ArrayList<AppointmentModel> pendingList;
    private ArrayList<AppointmentModel> dismissList;
    private ArrayList<AppointmentModel> approvedList;
    private ArrayList<AppointmentModel> completedList;

    TextView pendingView,approvedView,completedView,cancelledView;
    TextView pendingText,approvedText,completedText,cancelledText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        pendingText = findViewById(R.id.noOfPendingViews);
        approvedText = findViewById(R.id.noOfApprovedViews);
        completedText = findViewById(R.id.noOfCompletedViews);
        cancelledText = findViewById(R.id.noOfCanceledViews);
        pendingView = findViewById(R.id.upcoming_view);
        approvedView = findViewById(R.id.approved_view);
        completedView = findViewById(R.id.completed_view);
        cancelledView = findViewById(R.id.cancelled_view);

        getAppointmentDataFromServer();

        pendingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendingIntent = new Intent(AppointmentActivity.this,StatusActivity.class);
                pendingIntent.putExtra("APP_MODEL_LIST",pendingList);
                pendingIntent.putExtra("ListType","pending");
                startActivity(pendingIntent);
            }
        });

        approvedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent approvedIntent = new Intent(AppointmentActivity.this,StatusActivity.class);
                approvedIntent.putExtra("APP_MODEL_LIST",approvedList);
                approvedIntent.putExtra("ListType","approved");
                startActivity(approvedIntent);
            }
        });

        completedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent completedIntent = new Intent(AppointmentActivity.this,StatusActivity.class);
                completedIntent.putExtra("APP_MODEL_LIST",completedList);
                completedIntent.putExtra("ListType","complete");
                startActivity(completedIntent);
            }
        });

        cancelledView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelledIntent = new Intent(AppointmentActivity.this,StatusActivity.class);
                cancelledIntent.putExtra("APP_MODEL_LIST",dismissList);
                cancelledIntent.putExtra("ListType","cancel");
                startActivity(cancelledIntent);
            }
        });

    }

    private void getAppointmentDataFromServer(){

        StringRequest request= new StringRequest(Request.Method.POST, APPOINTMENT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("upcomingResponse",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonPending = jsonObject.getJSONArray("pending");
                    JSONArray jsonDismiss = jsonObject.getJSONArray("dismiss");
                    JSONArray jsonApproved = jsonObject.getJSONArray("approved");
                    JSONArray jsonCompleted = jsonObject.getJSONArray("Completed");

                    pendingList = new ArrayList<>();
                    for(int i=0;i<jsonPending.length();i++)
                    {
                         AppointmentModel model = new AppointmentModel();
                         JSONObject obj = jsonPending.getJSONObject(i);
                         model.setId(obj.getString("id"));
                         model.setDate(obj.getString("date"));
                         model.setTime(obj.getString("time"));
                         model.setName(obj.getString("name"));
                         model.setPhone(obj.getString("phone"));
                         model.setEmail(obj.getString("email"));
                         model.setOther(obj.getString("other"));
                         pendingList.add(model);
                    }

                    dismissList = new ArrayList<>();
                    for(int i=0;i<jsonDismiss.length();i++)
                    {
                        AppointmentModel model = new AppointmentModel();
                        JSONObject obj = jsonDismiss.getJSONObject(i);
                        model.setId(obj.getString("id"));
                        model.setDate(obj.getString("date"));
                        model.setTime(obj.getString("time"));
                        model.setName(obj.getString("name"));
                        model.setPhone(obj.getString("phone"));
                        model.setEmail(obj.getString("email"));
                        model.setOther(obj.getString("other"));
                        dismissList.add(model);
                    }

                    approvedList = new ArrayList<>();
                    for(int i=0;i<jsonApproved.length();i++)
                    {
                        AppointmentModel model = new AppointmentModel();
                        JSONObject obj = jsonApproved.getJSONObject(i);
                        model.setId(obj.getString("id"));
                        model.setDate(obj.getString("date"));
                        model.setTime(obj.getString("time"));
                        model.setName(obj.getString("name"));
                        model.setPhone(obj.getString("phone"));
                        model.setEmail(obj.getString("email"));
                        model.setOther(obj.getString("other"));
                        approvedList.add(model);
                    }

                    completedList = new ArrayList<>();
                    for(int i=0;i<jsonCompleted.length();i++)
                    {
                        AppointmentModel model = new AppointmentModel();
                        JSONObject obj = jsonCompleted.getJSONObject(i);
                        model.setId(obj.getString("id"));
                        model.setDate(obj.getString("date"));
                        model.setTime(obj.getString("time"));
                        model.setName(obj.getString("name"));
                        model.setPhone(obj.getString("phone"));
                        model.setEmail(obj.getString("email"));
                        model.setOther(obj.getString("other"));
                        completedList.add(model);
                    }

                    //fill fields in layout
                    fillFieldsInAppointmentLayout();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String, String>();
                param.put("id","32");// send the docter id

                return param;
            }

        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void fillFieldsInAppointmentLayout(){
        pendingText.setText(String.valueOf(pendingList.size()));
        approvedText.setText(String.valueOf(approvedList.size()));
        completedText.setText(String.valueOf(completedList.size()));
        cancelledText.setText(String.valueOf(dismissList.size()));
    }


}