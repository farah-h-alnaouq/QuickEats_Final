package com.example.quickeats;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;

public class activity_home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private GridView gridView;
    private ArrayList<Food> foodList;
    private FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //  إعداد Toolbar و Drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        //  برمجة القائمة الجانبية (Navigation Drawer)
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Toast.makeText(activity_home.this, "أنت في الرئيسية", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_cart) {
                    startActivity(new Intent(activity_home.this, CartActivity.class));
                } else if (id == R.id.nav_orders) {
                    startActivity(new Intent(activity_home.this, OrdersActivity.class));
                } else if (id == R.id.nav_logout) {
                    startActivity(new Intent(activity_home.this, activity_login.class));
                    finish();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //  عرض الأكلات في الـ GridView
        gridView = findViewById(R.id.food_grid);
        foodList = new ArrayList<>();
        foodList.add(new Food("Mixed Fried Meat", "25 $", R.drawable.food1));
        foodList.add(new Food("Italian Pizza", "30 $", R.drawable.food2));
        foodList.add(new Food("Vegetarian Salad", "15 $", R.drawable.food3));
        foodList.add(new Food("Beef Burger", "25 $", R.drawable.food4));
        foodList.add(new Food("Chicken Platter", "20 $", R.drawable.food5));
        foodList.add(new Food("Pasta Extra", "22 $", R.drawable.food6));
        foodList.add(new Food("Shawarma Wrap", "15 $", R.drawable.food7));
        foodList.add(new Food("Steak House", "45 $", R.drawable.food8));
        foodList.add(new Food("Calzone", "35 $", R.drawable.food9));

        adapter = new FoodAdapter(this, foodList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food selectedItem = foodList.get(position);
                Intent intent = new Intent(activity_home.this, FoodDetailsActivity.class);
                intent.putExtra("foodName", selectedItem.getName());
                intent.putExtra("foodPrice", selectedItem.getPrice());
                intent.putExtra("foodImage", selectedItem.getImage());
                startActivity(intent);
            }
        });
    }

    // --- برمجة المنيو (الثلاث نقاط) ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(this, "أنت بالفعل في الرئيسية", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.nav_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        } else if (id == R.id.nav_orders) {
            startActivity(new Intent(this, OrdersActivity.class));
            return true;
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(this, activity_login.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}