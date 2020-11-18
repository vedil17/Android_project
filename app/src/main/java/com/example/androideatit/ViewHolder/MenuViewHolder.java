package com.example.androideatit.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androideatit.Interface.ItemOnclickListner;
import com.example.androideatit.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView;

    private ItemOnclickListner itemOnclickListner;

    public MenuViewHolder(View itemView) {
        super(itemView);
        txtMenuName=(TextView)itemView.findViewById(R.id.menu_name);
        imageView=(ImageView)itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);
    }

    public void setClicklistner(ItemOnclickListner clicklistner) {
        this.itemOnclickListner= clicklistner;
    }


    @Override
    public void onClick(View v) {
        itemOnclickListner.onclick(v,getAdapterPosition(),false);
    }
}
