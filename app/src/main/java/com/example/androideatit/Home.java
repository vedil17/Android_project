package com.example.androideatit;

import android.content.Intent;
import android.os.Bundle;

import com.example.androideatit.Common.Common;
import com.example.androideatit.Interface.ItemOnclickListner;
import com.example.androideatit.Model.Category;
import com.example.androideatit.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    FirebaseDatabase database;
    DatabaseReference category;

    TextView textfullname;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //Init Firebase
        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent=new Intent(Home.this,Cart.class);
                startActivity(cartIntent);
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_menu, R.id.nav_cart, R.id.nav_orders,R.id.nav_home,
                R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);


        //set User name
        View headerview=navigationView.getHeaderView(0);
        textfullname=(TextView)headerview.findViewById(R.id.txtFullName);
        textfullname.setText(Common.currentUser.getName());

        //Load menu
        recycler_menu=(RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        LoadMenu();
    }

    private void LoadMenu() {
        adapter=new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
                menuViewHolder.txtMenuName.setText(category.getName());
                Picasso.with(getBaseContext()).load(category.getImage()).into(menuViewHolder.imageView);
                final Category ClickItem=category;
                menuViewHolder.setClicklistner(new ItemOnclickListner() {
                    @Override
                    public void onclick(View view, int position, boolean IsLongClick) {
                        //get categoryId and send to new activity
                        Intent foodlist=new Intent(Home.this,Foodlist.class);
                        foodlist.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodlist);
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        int id=menuItem.getItemId();
        if(id==R.id.nav_cart)
        {
            Intent cart=new Intent(Home.this,Cart.class);
            startActivity(cart);
        }else  if(id==R.id.nav_orders)
        {
            Intent OrderStatus=new Intent(Home.this,OrderStatus.class);
            startActivity(OrderStatus);
        }else  if(id==R.id.nav_logout)
        {
            Intent Signin=new Intent(Home.this,SignIn.class);
            Signin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(Signin);
        }
        return false;
    }
}
