package com.example.androideatit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.androideatit.Interface.ItemOnclickListner;
import com.example.androideatit.Model.Food;
import com.example.androideatit.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Foodlist extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodlist;

    String categoryId="";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);

        //Firebase
        database=FirebaseDatabase.getInstance();
        foodlist=database.getReference("Foods");

        recyclerView=(RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get intent here
        if (getIntent()!=null){
            categoryId=getIntent().getStringExtra("CategoryId");
        }
        if(!categoryId.isEmpty() && categoryId != null){
            loadListFood(categoryId);
        }
    }

    private void loadListFood(String categoryId) {
        adapter=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,R.layout.food_item,FoodViewHolder.class,foodlist.orderByChild("menuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int i) {
                foodViewHolder.food_name.setText(food.getName());
                Log.d("Tag",""+food.getName());
                Picasso.with(getBaseContext()).load(food.getImage()).into(foodViewHolder.food_image);

                final Food local=food;
                foodViewHolder.setItemOnclickListner(new ItemOnclickListner() {
                    @Override
                    public void onclick(View view, int position, boolean IsLongClick) {
                        //Start new activity
                        Intent foodDetail=new Intent(Foodlist.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }

                });
            }

        };
        //set adapter

        recyclerView.setAdapter(adapter);
    }
}
