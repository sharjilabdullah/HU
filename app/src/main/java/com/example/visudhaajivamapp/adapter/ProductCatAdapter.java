package com.example.visudhaajivamapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.visudhaajivamapp.DashboardMain;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.addedbysam.ProductCategory;
import com.example.visudhaajivamapp.addedbysam.ProductSubCategory;
import com.example.visudhaajivamapp.models.Category;

import java.util.ArrayList;
import java.util.List;

public class ProductCatAdapter extends RecyclerView.Adapter<ProductCatAdapter.CatViewHolder> {

    public final List<Category> categorylist;
    final int layout;
    //final Activity activity;
    final Context context;
    //final String from;
    //final int catid;
    //private Context mCtx;

    public ProductCatAdapter(Context context,  ArrayList<Category> categorylist, int layout) {
        this.context = context;
        this.categorylist = categorylist;
        this.layout = layout;
        //this.catid=catid;
        //this.activity = activity;
        //this.from = from;
        //this.visibleNumber = visibleNumber;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new CatViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull  final CatViewHolder holder, int position) {




            final Category model = categorylist.get(position);

            //loading the image

            Glide.with(context)
                    .load(model.getImage())
                    .into((holder.imgcategory));



            holder.txttitle.setText(model.getName());




            holder.lytMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Activity activity = (Activity) context;
                    if(activity instanceof ProductCategory){
                        ProductCategory.productCategory = (ProductCategory) context;
                    }

                    //sendcatid(model.getId());
                    Intent intent = new Intent(v.getContext(), ProductSubCategory.class);
                    /*
                    Fragment fragment = new ProductSubCategory();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.ID, model.getId());
                    bundle.putString(Constant.NAME, model.getName());
                    bundle.putString(Constant.FROM, "category");
                    fragment.setArguments(bundle);
                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();

                     */
                    intent.putExtra("category_id",model.getId());
                    v.getContext().startActivity(intent);
                }
            });


    }
/*
    private void sendcatid(int id) {
        String urlSuffix = "?cid=" + id ;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(context,"Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferReader=null;
                try {
                    URL url=new URL(URL+s);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    bufferReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;
                    result=bufferReader.readLine();
                    return  result;

                }catch (Exception e){
                    return null;
                }
            }

        }
        RegisterUser ur=new RegisterUser();
        ur.execute(urlSuffix);
    }

 */

    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {

        public final TextView txttitle;
        final ImageView imgcategory;
        final LinearLayout lytMain;

        public CatViewHolder(View itemView) {
            super(itemView);
            lytMain = itemView.findViewById(R.id.lytMain);
            imgcategory = itemView.findViewById(R.id.imgcategory);
            txttitle = itemView.findViewById(R.id.txttitle);
        }

    }
}


