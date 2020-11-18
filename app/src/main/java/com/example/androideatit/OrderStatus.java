package com.example.androideatit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.androideatit.Common.Common;
import com.example.androideatit.Model.Request;
import com.example.androideatit.ViewHolder.orderViewHolde;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, orderViewHolde>adapter;
    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Firebase
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        //Init
        recyclerView=(RecyclerView)findViewById(R.id.ListOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        LoadOrders(Common.currentUser.getPhone());
    }

    private void LoadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, orderViewHolde>(Request.class, R.layout.order_layout, orderViewHolde.class, requests.orderByChild("phone").equalTo(phone)) {
            @Override
            protected void populateViewHolder(orderViewHolde orderViewHolde, Request request, int i) {
                orderViewHolde.txtOrderId.setText(adapter.getRef(i).getKey());
                orderViewHolde.txtOrderStatus.setText(convertCodeToStatus(request.getStatus()));
                orderViewHolde.txtOrderAdress.setText(request.getAdress());
                orderViewHolde.txtOrderPhone.setText(request.getPhone());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status)
    {
        if(status.equals("0"))
            return "placed";
        else if(status.equals("1"))
            return "on my way";
        else return "shipped";

    }
}
