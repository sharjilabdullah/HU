package com.example.visudhaajivamapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visudhaajivamapp.DoctorConsultation.DoctorCategory;
import com.example.visudhaajivamapp.HelperClasses.CategoryHelperClass;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.addedbysam.ProductCategory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter{




    ArrayList<CategoryHelperClass> categorylocations;

    public CategoryAdapter(ArrayList<CategoryHelperClass> categorylocations) {
        this.categorylocations = categorylocations;
    }

    @Override
    public int getItemViewType(int position) {
        if(categorylocations.get(position).getTitle().contains("Medicines"))
        {
            return  0;
        }else{
            return 1;
        }
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType==0)
        {
            view = layoutInflater.inflate(R.layout.categories_card,parent,false);
            return new ViewHolderMedicines(view);
        }
        else{
            view = layoutInflater.inflate(R.layout.categories_card,parent,false);
            return new ViewHolderDoctors(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if(categorylocations.get(position).getTitle().contains("Medicines")){
            ViewHolderMedicines viewHolderMedicines = (ViewHolderMedicines) holder;
            viewHolderMedicines.title.setText(categorylocations.get(position).getTitle());
            viewHolderMedicines.image.setImageResource(categorylocations.get(position).getImage());
        }else{

            //CategoryHelperClass categoryHelperClass = categorylocations.get(position);
            ViewHolderDoctors viewHolderDoctors = (ViewHolderDoctors) holder;
            viewHolderDoctors.title.setText(categorylocations.get(position).getTitle());
            viewHolderDoctors.image.setImageResource(categorylocations.get(position).getImage());

        }

    }

    @Override
    public int getItemCount() {
        return categorylocations.size();
    }

    static class ViewHolderMedicines extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView title;
        public ViewHolderMedicines(@NonNull @NotNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.category_button);
            title = itemView.findViewById(R.id.category_title);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent (v.getContext(), ProductCategory.class);
            v.getContext().startActivity(intent);
        }
    }



    static class ViewHolderDoctors extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView title;


        public ViewHolderDoctors(@NonNull @NotNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.category_button);
            title = itemView.findViewById(R.id.category_title);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent (v.getContext(), DoctorCategory.class);
            v.getContext().startActivity(intent);
        }
    }



    /*
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_card,parent,false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder((view));

        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryHelperClass categoryHelperClass = categorylocations.get(position);
        holder.image.setImageResource(categoryHelperClass.getImage());
        holder.title.setText(categoryHelperClass.getTitle());


    }

    @Override
    public int getItemCount() {
        return categorylocations.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView title;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.category_button);
            title = itemView.findViewById(R.id.category_title);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent (v.getContext(), Departments.class);
            v.getContext().startActivity(intent);
        }
    }

     */
}

