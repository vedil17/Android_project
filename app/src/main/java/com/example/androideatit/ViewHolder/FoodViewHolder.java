package com.example.androideatit.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androideatit.Interface.ItemOnclickListner;
import com.example.androideatit.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_name;
    public ImageView food_image;

    private ItemOnclickListner itemOnclickListner;

    public void setItemOnclickListner(ItemOnclickListner itemOnclickListner) {
        this.itemOnclickListner = itemOnclickListner;
    }

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        food_name=(TextView)itemView.findViewById(R.id.food_name);
        food_image=(ImageView)itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemOnclickListner.onclick(v,getAdapterPosition(),false);
    }
}
