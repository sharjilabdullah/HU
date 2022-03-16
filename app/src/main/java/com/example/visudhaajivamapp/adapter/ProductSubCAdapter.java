package com.example.visudhaajivamapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.visudhaajivamapp.HelperClasses.Session;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.addedbysam.AllSubCategory;
import com.example.visudhaajivamapp.addedbysam.ProductDetail;
import com.example.visudhaajivamapp.addedbysam.ProductSubCategory;
import com.example.visudhaajivamapp.models.SubCategory;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductSubCAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mCtx;
    private List<SubCategory> productList;
    //final String from;
    public final int resource;
    final Activity activity;
    final Session session;
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    public boolean isLoading;boolean isFavorite;
    //final int subcid;


    public ProductSubCAdapter(Context mCtx, List<SubCategory> productList, int resource) {
        this.mCtx = mCtx;
        this.productList = productList;
        //this.subcid=subcid;
        this.activity = (Activity) mCtx;
        this.resource = resource;
        //this.from = from;
        this.session = new Session(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        try {
            View view = inflater.inflate(R.layout.sub_category_list, null);
            return new ProductSubCAdapter.ProductViewHolder(view);
        }catch (Exception e){
            Log.e(TAG, "Errorrrrrrrrrrrrr", e);
            throw e;
        }

         */

        if (viewType == VIEW_TYPE_ITEM) {
            //LayoutInflater inflater = LayoutInflater.from(mCtx);
            //View view = inflater.inflate(R.layout.sub_category_list, null);
            View view = LayoutInflater.from(mCtx).inflate(resource, parent, false);
            return new ProductViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mCtx).inflate(R.layout.item_progressbar, parent, false);
            return new ViewHolderLoading(view);
        }

        return null;


    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ProductViewHolder) {
            SubCategory product = productList.get(position);
            //if (subcid == product.getCategory_id()){

                    //loading the image
                    Glide.with(mCtx)
                            .load(product.getImage())
                            .into(((ProductViewHolder) holder).imageView);

                ((ProductViewHolder) holder).textViewTitle.setText(product.getName());
                ((ProductViewHolder) holder).textViewRating.setText(String.valueOf("₹ "+product.getDiscounted_price()));
            ((ProductViewHolder) holder).textViewPrice.setPaintFlags(((ProductViewHolder) holder).textViewPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                ((ProductViewHolder) holder).textViewPrice.setText("₹ "+String.valueOf(product.getPrice()));


                //holder.txtqty.setText(priceVariations.get(0).getCart_count());
                    /*
                    if (product.isIs_favorite()) {
                        ((ProductViewHolder) holder).wishlist.setImageResource(R.drawable.ic_is_favorite);
                    } else {
                        ((ProductViewHolder) holder).wishlist.setImageResource(R.drawable.ic_is_not_favorite);
                    }


                    final Session session = new Session(activity);

                    ((ProductViewHolder) holder).wishlist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //isFavorite = product.isIs_favorite();
                            if (isFavorite) {
                                isFavorite = false;
                                ((ProductViewHolder) holder).wishlist.setImageResource(R.drawable.ic_is_not_favorite);
                                ((ProductViewHolder) holder).lottieAnimationView.setVisibility(View.GONE);
                            } else {
                                isFavorite = true;
                                ((ProductViewHolder) holder).lottieAnimationView.setVisibility(View.VISIBLE);
                                ((ProductViewHolder) holder).lottieAnimationView.playAnimation();
                            }
                            //product.setIs_favorite(isFavorite);
                            //AddOrRemoveFavorite(activity, session, product.getId(), isFavorite);
                        }
                    });

                     */

                ((ProductViewHolder) holder).lytmain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Activity activity = (Activity) mCtx;
                        if(activity instanceof AllSubCategory){
                            AllSubCategory.allSubCategory = (AllSubCategory)mCtx;
                        }
                        else{
                            ProductSubCategory.productSubCategory = (ProductSubCategory)mCtx;
                        }
                        //Create intent getting the context of your View and the class where you want to go
                        Intent intent = new Intent(v.getContext(), ProductDetail.class);
//                        intent.putExtra("image", product.getImage());
//                        intent.putExtra("name", product.getName());
//                        intent.putExtra("discounted_price", product.getDiscounted_price());
//                        intent.putExtra("price", product.getPrice());
//                        intent.putExtra("manufact",product.getManufacture());
//                        intent.putExtra("madein",product.getMande_in());
//                        intent.putExtra("stock",product.getStock());

                        intent.putExtra("PRODUCT_DETAILS",product);
                        //intent.putExtra("context",mCtx)

                        //start the activity from the view/context
                        v.getContext().startActivity(intent);

                    }
                });
            //}
        } else if (holder instanceof ViewHolderLoading) {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }




    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolderLoading extends RecyclerView.ViewHolder {
        public final ProgressBar progressBar;

        public ViewHolderLoading(View view) {
            super(view);
            progressBar = view.findViewById(R.id.itemProgressbar);
        }
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView,wishlist;
        CardView lytmain;
        //final LottieAnimationView lottieAnimationView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewRating = itemView.findViewById(R.id.discountedprice);
            textViewPrice = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.imageView);
            wishlist =itemView.findViewById(R.id.ic_wishlist);
            lytmain= itemView.findViewById(R.id.lytMain);
            //lottieAnimationView = itemView.findViewById(R.id.lottieAnimationView);
        }
    }
}