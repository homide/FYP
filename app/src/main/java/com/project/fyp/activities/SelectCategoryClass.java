package com.project.fyp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.project.fyp.R;
import com.project.fyp.activities.generalshopping.Main_General_category;

public class SelectCategoryClass extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView2;
    ImageView generalCat, fashionCat;
    DrawerLayout drawerLayout;
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        generalCat = findViewById(R.id.generalCat);
        fashionCat = findViewById(R.id.fashionCat);
        back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCategoryClass.super.onBackPressed();
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout2);
        navigationView2 = findViewById(R.id.navView2);
        navigationView2.bringToFront();
        navigationView2.setNavigationItemSelectedListener(this);

        generalCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cinemaIntent = new Intent(SelectCategoryClass.this, Main_General_category.class);
                startActivity(cinemaIntent);
            }
        });

//        groceriesCat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent cinemaIntent = new Intent(SelectCategoryClass.this, Main_Groceries_category.class);
//                startActivity(cinemaIntent);
//            }
//        });
//
//        fashionCat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent cinemaIntent = new Intent(SelectCategoryClass.this, Main_Fashion_category.class);
//                startActivity(cinemaIntent);
//            }
//        });
//
//        medCat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent cinemaIntent = new Intent(SelectCategoryClass.this, Main_Medicine_category.class);
//                startActivity(cinemaIntent);
//            }
//        });
//
//        electroCat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent cinemaIntent = new Intent(SelectCategoryClass.this, Main_Electronics_category.class);
//                startActivity(cinemaIntent);
//            }
//        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //test

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Toast.makeText(this, "This doesn't have a funtion yet", Toast.LENGTH_SHORT).show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}