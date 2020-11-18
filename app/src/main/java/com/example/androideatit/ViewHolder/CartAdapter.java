package com.example.androideatit.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.androideatit.Interface.ItemOnclickListner;
import com.example.androideatit.Model.Order;
import com.example.androideatit.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_cart_name,txt_price;
    public ImageView img_cart_count;

    private ItemOnclickListner itemOnclickListner;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_cart_name=(TextView)itemView.findViewById(R.id.cart_item_name);
        txt_price=(TextView)itemView.findViewById(R.id.cart_item_Price);
        img_cart_count=(ImageView)itemView.findViewById(R.id.cart_item_count);
    }

    @Override
    public void onClick(View v) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listdata=new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemview=inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        TextDrawable drawable=TextDrawable.builder().buildRound(""+listdata.get(position).getQuantity(), Color.RED);
        holder.img_cart_count.setImageDrawable(drawable);

        Locale locale=new Locale("en","US");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);

        int Price=(Integer.parseInt(listdata.get(position).getPrice()))*(Integer.parseInt(listdata.get(position).getQuantity()));
        holder.txt_price.setText(fmt.format(Price));

        holder.txt_cart_name.setText(listdata.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}
