package com.example.androideatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.androideatit.Database.Database;
import com.example.androideatit.Model.Food;
import com.example.androideatit.Model.Order;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,food_Description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;

    Food currentfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //Firebase
        database=FirebaseDatabase.getInstance();
        foods=database.getReference("Foods");

        //Init View
        numberButton=(ElegantNumberButton)findViewById(R.id.number_button);
        btnCart=(FloatingActionButton)findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCarts(new Order(foodId,currentfood.getName(),numberButton.getNumber(),currentfood.getPrice(),currentfood.getDiscount()));
                Toast.makeText(FoodDetail.this,"Added to cart",Toast.LENGTH_SHORT).show();
            }
        });

        food_Description=(TextView)findViewById(R.id.food_description);
        food_name=(TextView)findViewById(R.id.food_name);
        food_price=(TextView)findViewById(R.id.food_price);
        food_image=(ImageView)findViewById(R.id.img_food);

        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        //get food id from intent
        if (getIntent()!=null){
            foodId=getIntent().getStringExtra("FoodId");
        }
        if(!foodId.isEmpty()){
            getDetailFood(foodId);
        }
    }

    private void getDetailFood(final String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentfood=dataSnapshot.getValue(Food.class);

                //set image
                Picasso.with(getBaseContext()).load(currentfood.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(currentfood.getName());
                food_price.setText(currentfood.getPrice());
                food_name.setText(currentfood.getName());
                food_Description.setText(currentfood.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
