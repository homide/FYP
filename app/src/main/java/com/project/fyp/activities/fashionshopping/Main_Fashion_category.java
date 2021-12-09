package com.project.fyp.activities.fashionshopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.project.fyp.R;
import com.project.fyp.activities.Main2Activity;
import com.project.fyp.activities.SelectCategoryClass;
import com.project.fyp.activities.wishlist.WishlistActivity;
import com.project.fyp.interfaces.arraySave;
import com.project.fyp.threads.CallingSites;

public class Main_Fashion_category extends AppCompatActivity implements arraySave, NavigationView.OnNavigationItemSelectedListener {

    public AutoCompleteTextView searchbar;
    public String searchtext;
    ViewFlipper viewFlipper;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion_category);

        drawerLayout = findViewById(R.id.drawer_layout);
        searchbar = findViewById(R.id.searchText);

        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchtext = searchbar.getText().toString();
                if (searchtext.length() <= 0) {
                    Toast.makeText(Main_Fashion_category.this, "Please add something to search.", Toast.LENGTH_SHORT).show();
                } else {
                    arraySave.products.clear();
                    final ProgressDialog pd = new ProgressDialog(Main_Fashion_category.this);
                    pd.setMessage("Searching websites...");
                    pd.show();
                    CallingSites callingSites = new CallingSites();
                    callingSites.callingfashion(searchtext);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            int cat = 1;
                            Intent intent = new Intent(Main_Fashion_category.this, Main2Activity.class);
                            intent.putExtra("cat", cat);
                            startActivity(intent);
                        }
                    }, 4000);
                }
            }
        });

        String[] suggestions = getResources().getStringArray(R.array.suggestions);
        searchbar.setAdapter(new ArrayAdapter<>(Main_Fashion_category.this, android.R.layout.simple_list_item_1, suggestions));

        findViewById(R.id.wishlist_toolbar_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Fashion_category.this, WishlistActivity.class));
            }
        });

        findViewById(R.id.category_toolbar_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Fashion_category.this, SelectCategoryClass.class));
            }
        });

        navigationDrawer();
    }

    private void navigationDrawer() {
        //hamburger
        navigationView = findViewById(R.id.navView);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        findViewById(R.id.navigation_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}