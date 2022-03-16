package com.example.visudhaajivamapp.DoctorConsultation.DateTimeInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visudhaajivamapp.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class ConfirmOrderDocterActivity extends AppCompatActivity implements PaymentResultListener {
    private TextView userNameText,userEmailText,selectedDateText,selectedTimeText,mobileText,userProblemText,docterNameText,docterDescriptionText,docterFeeText;
    private RadioButton razorRadio;
    private Button paymentButton;
    DocterModel docterModel;
    ImageView backbutt;
    private String TAG =" testPayment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order_docter);
        userNameText = findViewById(R.id.username_id);
        userEmailText = findViewById(R.id.user_email_id);
        selectedDateText = findViewById(R.id.user_selected_date_id);
        selectedTimeText = findViewById(R.id.user_selected_time_id);
        mobileText = findViewById(R.id.user_mobile_id);
        userProblemText = findViewById(R.id.user_problem_id);
        docterNameText = findViewById(R.id.docnName_id);
        docterDescriptionText = findViewById(R.id.doctor_description_id);
        docterFeeText = findViewById(R.id.docter_fees_id);
        razorRadio = findViewById(R.id.razor_id);
        paymentButton = findViewById(R.id.payment_id);
        backbutt = findViewById(R.id.back_icon);

        Intent intent = getIntent();
        if(intent.hasExtra("DOCTER_MODEL")){
            docterModel = (DocterModel) intent.getSerializableExtra("DOCTER_MODEL");
            fillAllFields();
        }
        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPaymentMethod();
                startPayment();
                /*
                Intent intent1=new Intent(ConfirmOrderDocterActivity.this,Extra.class);
                intent1.putExtra("FEE",docterFeeText.getText().toString());
                startActivity(intent1);
                 */
                //move to RazorPayActivity
            }
        });

    }

    private void fillAllFields(){
        userNameText.setText(docterModel.getUserName());
        userEmailText.setText(docterModel.getUserEmail());
        selectedDateText.setText(docterModel.getSelectedDate());
        selectedTimeText.setText(docterModel.getSelectedTime());
        mobileText.setText(docterModel.getUserMobile());
        userProblemText.setText(docterModel.getUserProblem());
        docterNameText.setText(docterModel.getDocName());
        docterDescriptionText.setText(docterModel.getDocDesc());
        docterFeeText.setText("₹ "+docterModel.getDocFees());
    }

    private void setPaymentMethod(){
        if(razorRadio.isChecked()){
            docterModel.setPaymentMethod("Razor Pay");
        }
    }

    public void startPayment(){
        final Activity activity = this;
        final Checkout co = new Checkout();
        try{
            JSONObject options = new JSONObject();
            options.put("name", "Visudh Ajivam");
            options.put("description", "App Payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", R.drawable.logo_pay);
            options.put("currency", "INR");
            //String payment = txtTotal.getText().toString();
            //payment =payment.substring(1);
            double total = Double.parseDouble(docterModel.getDocFees());
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "samsri2908@gmail.com");
            preFill.put("contact", "7985713017");
            options.put("prefill", preFill);
            co.open(activity, options);

        }catch (Exception e){
            Log.e("error"," error "+e.toString());
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.e(TAG, " payment successfull "+ s.toString());
        Toast.makeText(this, "Payment successfully done! " +s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {

        Log.e(TAG,  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }
}