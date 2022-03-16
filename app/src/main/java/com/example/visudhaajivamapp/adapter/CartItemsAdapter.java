package com.example.visudhaajivamapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bumptech.glide.Glide;
import com.example.visudhaajivamapp.DashboardMain;
import com.example.visudhaajivamapp.LoginActivity;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.addedbysam.AllSubCategory;
import com.example.visudhaajivamapp.addedbysam.CartListActivity;
import com.example.visudhaajivamapp.models.CartItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.CartViewHolder> {

    public final List<CartItems> categorylist = CartListActivity.productList;

    //categorylist = CartListActivity.
    final int layout;
    //final Activity activity;
    public static int Cartitems;
    final Context context;
    //final String product_varid,userid,identity;
    //final int catid;
    //private Context mCtx;
    LinearLayout layoutCartItems,layoutCartNoItems, layoutCartPayments;
    String Cartremove_URL= "https://visudhajivam.in/android/cart_delete.php";
    public CartItemsAdapter(Context context,  int layout,LinearLayout layoutCartItems,LinearLayout layoutCartNoItems, LinearLayout layoutCartPayments) {
        this.context = context;
        //this.categorylist = categorylist;
        this.layout = layout;
        this.layoutCartItems = layoutCartItems;
        this.layoutCartNoItems = layoutCartNoItems;
        this.layoutCartPayments=layoutCartPayments;
        //this.visibleNumber = visibleNumber;
    }

    @NonNull

    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new CartViewHolder(view);
    }


    @NonNull

    public void onBindViewHolder(@NonNull  final CartViewHolder holder, int position) {




        Log.d("CattegorryListt", String.valueOf(categorylist));

        Log.d("Position", String.valueOf(position));
        final CartItems model = categorylist.get(position);

        //loading the image
        Cartitems=getItemCount();

        Glide.with(context)
                .load(model.getImage())
                .into((holder.imgcategory));

        holder.disprice.setText("₹ "+String.valueOf(model.getDiscounted_price()));
        holder.orprice.setPaintFlags( holder.orprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.orprice.setText("₹ "+String.valueOf(model.getPrice()));
        holder.txttitle.setText(model.getName());
        holder.quantity.setText("Qty : "+model.getQuantity());




        holder.layout_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StringRequest stringRequest = new StringRequest(Request.Method.POST, Cartremove_URL,
                        new Response.Listener<String>() {

                            @SuppressLint("LongLogTag")
                            @Override
                            public void onResponse(String response) {
                                Log.d("showwwwwwwwwwwcartremove", String.valueOf(response));
                                if(response.equals("success")){
                                    Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
                                    /*
//                                    Intent refresh = new Intent(context, CartListActivity.class);
//                                    context.startActivity(refresh);
//                                    ((Activity)context).finish();
//                                    DashboardMain.notificationCountCart--;


                                    categorylist.remove(position);
                                    notifyDataSetChanged();

                                     */

                                    int newPosition = holder.getAdapterPosition();
                                    categorylist.remove(newPosition);
                                    notifyItemRemoved(newPosition);
                                    notifyItemRangeChanged(newPosition, categorylist.size());

                                    Intent refresh = new Intent(context, CartListActivity.class);
                                    refresh.putExtra("FromAdapter","FromAdapter");
                                    context.startActivity(refresh);
                                    ((Activity)context).finish();

                                    /*
                                    if(categorylist.size()==0){
                                        layoutCartNoItems.setVisibility(View.VISIBLE);
                                        layoutCartItems.setVisibility(View.GONE);
                                        layoutCartPayments.setVisibility(View.GONE);
                                        //Intent refresh = new Intent(context, CartListActivity.class);
                                        //context.startActivity(refresh);
                                    }

                                     */
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> prams = new HashMap<>();
                        prams.put("pvid", String.valueOf(model.getId()));
                        prams.put("id",String.valueOf(model.getUrid()));
                        prams.put("identity",model.getIdentity());


                        return prams;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                requestQueue.add(stringRequest);
            }
        });

    }

    /*
    public void toggleSelection(int position) {
        CartItems selectedProduct = categorylist.get(position);
        if(selectedProduct.isSelected()) { // no need to check " == true"
            selectedProduct.setSelected( false);
        }
        else {
            selectedProduct.setSelected(true);
        }
        //notifyDataSetInvalidated();
    }

 */

    public int getItemCount() {
        return categorylist.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        public final TextView txttitle,orprice, disprice,quantity;
        final ImageView imgcategory;
        LinearLayout layout_remove,cartdetail;


        public CartViewHolder(View itemView) {
            super(itemView);
            quantity=itemView.findViewById(R.id.quantity);
            orprice = itemView.findViewById(R.id.price_ori);
            disprice=itemView.findViewById(R.id.dis_price);
            imgcategory = itemView.findViewById(R.id.image_cartlist);
            txttitle = itemView.findViewById(R.id.name_cart);
            layout_remove=itemView.findViewById(R.id.layout_remove);
            cartdetail = itemView.findViewById(R.id.detail_ofcart);
        }

    }

}
