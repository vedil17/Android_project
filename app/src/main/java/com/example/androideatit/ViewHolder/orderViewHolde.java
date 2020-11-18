package com.example.androideatit.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import  com.example.androideatit.Interface.ItemOnclickListner;
import com.example.androideatit.R;

public class orderViewHolde extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId,txtOrderStatus,txtOrderPhone,txtOrderAdress;
    public ItemOnclickListner itemClickListener;


    public orderViewHolde(@NonNull View itemView) {
        super(itemView);
        txtOrderId=(TextView) itemView.findViewById(R.id.order_id);
        txtOrderStatus=(TextView) itemView.findViewById(R.id.order_status);
        txtOrderPhone=(TextView) itemView.findViewById(R.id.order_phone);
        txtOrderAdress=(TextView) itemView.findViewById(R.id.order_Adress);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemOnclickListner itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onclick(view,getAdapterPosition(),false);
    }

}
