package com.project.fyp.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.project.fyp.R;
import com.project.fyp.activities.login.LoginPage;
import com.project.fyp.adapters.MyAdapterGeneral;
import com.project.fyp.adapters.MyAdapterFashion;
import com.project.fyp.database.DatabaseHelper;
import com.project.fyp.interfaces.arraySave;
import com.project.fyp.threads.CallingSites;

public class Main2Activity extends AppCompatActivity implements arraySave, NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    MyAdapterGeneral adapter;
    int initialSize;
    AutoCompleteTextView searchBar;
    Button search;

    DatabaseHelper databaseHelper;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        int catSelector = getIntent().getIntExtra("cat", 0);

        databaseHelper = new DatabaseHelper(this);

        drawerLayout = findViewById(R.id.drawer_layout3);
        navigationView = findViewById(R.id.navView3);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        searchBar = findViewById(R.id.searchText);
        search = findViewById(R.id.btnSearch);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        search.setOnClickListener(v->{
            String searchtext = searchBar.getText().toString();
            if (searchtext.length() <= 0) {
                Toast.makeText(Main2Activity.this, "Please add something to search.", Toast.LENGTH_SHORT).show();
            } else {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                arraySave.products.clear();
                final ProgressDialog pd = new ProgressDialog(Main2Activity.this);
                pd.setMessage("Searching websites...");
                pd.show();
                pd.setCanceledOnTouchOutside(false);
                if (catSelector == 0){
                    CallingSites callingSites = new CallingSites();
                    callingSites.callingmain(searchtext);
                }else{
                    CallingSites callingSites = new CallingSites();
                    callingSites.callingfashion(searchtext);
                }
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    pd.dismiss();
                    switch(catSelector){
                        case 0:
                            adapter = new MyAdapterGeneral(Main2Activity.this, arraySave.products);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
                            recyclerView.setAdapter(adapter);
                            keepRefreshing();
                            break;
                        case 1:
                            MyAdapterFashion adapterfashion = new MyAdapterFashion(Main2Activity.this, arraySave.products);
                            recyclerView.setLayoutManager(new GridLayoutManager(Main2Activity.this, 2));
                            recyclerView.setAdapter(adapterfashion);
                            break;
//                        case 2:
//                            MyAdaptorGroceries adaptorgroceries = new MyAdaptorGroceries(Main2Activity.this, arraySave.products);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
//                            recyclerView.setAdapter(adaptorgroceries);
//                            break;
                    }
                }, 4000);
                searchBar.setText("");
            }
        });

        findViewById(R.id.navigation_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        switch(catSelector){
            case 0:
                adapter = new MyAdapterGeneral(Main2Activity.this, arraySave.products);
                recyclerView.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
                recyclerView.setAdapter(adapter);
                keepRefreshing();
                break;
            case 1:
                MyAdapterFashion adaptorfashion = new MyAdapterFashion(Main2Activity.this, arraySave.products);
                recyclerView.setLayoutManager(new GridLayoutManager(Main2Activity.this, 2));
                recyclerView.setAdapter(adaptorfashion);
                break;
//            case 2:
//                MyAdaptorGroceries adaptorgroceries = new MyAdaptorGroceries(Main2Activity.this, arraySave.products);
//                recyclerView.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
//                recyclerView.setAdapter(adaptorgroceries);
//                break;
        }
    }

    public void keepRefreshing(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean condition = false;
                if(!condition) {
                    if (initialSize < arraySave.products.size()) {
                        adapter.notifyDataSetChanged();
                        initialSize = arraySave.products.size();
                        handler.postDelayed(this, 1500);
                    }else{
                        condition = false;
                    }
                }
                else {
                    condition = true;
                }
            }
        }, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            arraySave.products.clear();
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logOut){
            databaseHelper.deleteLoginDetails();
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
            finishAffinity();
        }

        DrawerLayout drawer =findViewById(R.id.drawer_layout3);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}