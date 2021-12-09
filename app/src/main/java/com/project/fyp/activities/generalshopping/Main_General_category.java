package com.project.fyp.activities.generalshopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.project.fyp.R;
import com.project.fyp.activities.Main2Activity;
import com.project.fyp.activities.SelectCategoryClass;
import com.project.fyp.activities.login.LoginPage;
import com.project.fyp.activities.webview.WebViewActivity;
import com.project.fyp.activities.wishlist.MyAdapterSavedGeneral;
import com.project.fyp.activities.wishlist.WishlistActivity;
import com.project.fyp.adapters.MyAdapterGeneral;
import com.project.fyp.database.DatabaseHelper;
import com.project.fyp.interfaces.arraySave;
import com.project.fyp.models.Product;
import com.project.fyp.models.SearchList;
import com.project.fyp.threads.CallingSites;

import java.util.ArrayList;

public class Main_General_category extends AppCompatActivity implements arraySave, NavigationView.OnNavigationItemSelectedListener {

    //UI
    public AutoCompleteTextView searchbar;
    public String searchtext;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView savedGeneralItemsTV, lastSeenProductsTV;
    RecyclerView savedRecycler, lastSeenRecycler;

    //Webview
    ImageView flipkartWeb, amazonWeb, snapdealWeb, shopcluesWeb, paytmWeb;

    //Database
    DatabaseHelper databaseHelper;

    //Variables
    ArrayList<SearchList> searchLists;

    // Adapter
    MyAdapterSavedGeneral adapterSavedGeneral;
    MyAdapterGeneral adapterGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_category);

        drawerLayout = findViewById(R.id.drawer_layout);
        searchbar = findViewById(R.id.searchText);
        flipkartWeb = findViewById(R.id.flipkartWeb);
        amazonWeb = findViewById(R.id.amazonWeb);
        snapdealWeb = findViewById(R.id.snapdealWeb);
        shopcluesWeb = findViewById(R.id.shopcluesWeb);
        paytmWeb = findViewById(R.id.paytmMallWeb);
        savedGeneralItemsTV = findViewById(R.id.savedGeneralItemsTV);
        lastSeenProductsTV = findViewById(R.id.lastSeenProductsTV);
        savedRecycler = findViewById(R.id.savedItemRecyclerView);
        lastSeenRecycler = findViewById(R.id.lastSeenRecyclerView);

        databaseHelper = new DatabaseHelper(this);
        searchLists = new ArrayList<>();

        searchLists = databaseHelper.getSearchedItems();

        setUpOnClicks();

        if (databaseHelper.checkGeneralItemList()){
            setupSavedGeneral();
        }else{
            savedGeneralItemsTV.setVisibility(View.INVISIBLE);
        }

        if (databaseHelper.checkGeneralItemListLastClicked()){
            setupLastClicked();
        }else{
            lastSeenProductsTV.setVisibility(View.INVISIBLE);
        }

        String[] suggestions = getResources().getStringArray(R.array.suggestions);
        searchbar.setAdapter(new ArrayAdapter<>(Main_General_category.this, android.R.layout.simple_list_item_1, suggestions));


        navigationDrawer();
    }

    private void setupLastClicked(){
        ArrayList<Product> arrayList = databaseHelper.getGeneralItemsLastClicked();
        lastSeenProductsTV.setVisibility(View.VISIBLE);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        lastSeenRecycler.setLayoutManager(llm);
        adapterGeneral = new MyAdapterGeneral(this,arrayList);
        lastSeenRecycler.setAdapter(adapterGeneral);
        adapterGeneral.notifyDataSetChanged();
    }

    private void setupSavedGeneral(){
        ArrayList<Product> arrayList = databaseHelper.getGeneralItemsWishlist();
        savedGeneralItemsTV.setVisibility(View.VISIBLE);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        savedRecycler.setLayoutManager(llm);
        adapterSavedGeneral = new MyAdapterSavedGeneral(this,arrayList);
        savedRecycler.setAdapter(adapterSavedGeneral);
        adapterSavedGeneral.notifyDataSetChanged();
    }

    private void setUpOnClicks(){

        findViewById(R.id.wishlist_toolbar_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_General_category.this, WishlistActivity.class));
            }
        });

        findViewById(R.id.category_toolbar_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_General_category.this, SelectCategoryClass.class));
            }
        });

        flipkartWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_General_category.this, WebViewActivity.class);
            intent.putExtra("url","https://www.flipkart.com/");
            startActivity(intent);
        });

        amazonWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_General_category.this, WebViewActivity.class);
            intent.putExtra("url","https://www.amazon.in/");
            startActivity(intent);
        });

        snapdealWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_General_category.this, WebViewActivity.class);
            intent.putExtra("url","https://www.snapdeal.com/");
            startActivity(intent);
        });

        shopcluesWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_General_category.this, WebViewActivity.class);
            intent.putExtra("url","https://www.shopclues.com/");
            startActivity(intent);
        });

        paytmWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_General_category.this, WebViewActivity.class);
            intent.putExtra("url","https://paytmmall.com/");
            startActivity(intent);
        });

        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchtext = searchbar.getText().toString();
                if (searchtext.length() <= 0) {
                    Toast.makeText(Main_General_category.this, "Please add something to search.", Toast.LENGTH_SHORT).show();
                } else {
                    if (searchLists.size() > 5){
                        databaseHelper.deleteSearchItem(searchLists.get(0).getId());
                    }
                    databaseHelper.insertSearchItem(searchtext);
                    arraySave.products.clear();
                    final ProgressDialog pd = new ProgressDialog(Main_General_category.this);
                    pd.setMessage("Searching websites...");
                    pd.show();
                    CallingSites callingSites = new CallingSites();
                    callingSites.callingmain(searchtext);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            int cat = 0;
                            Intent intent = new Intent(Main_General_category.this, Main2Activity.class);
                            intent.putExtra("cat", cat);
                            startActivity(intent);
                        }
                    }, 4000);
                }
            }
        });
    }

    private void navigationDrawer() {
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

        if (id == R.id.logOut){
            databaseHelper.deleteLoginDetails();
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
            finishAffinity();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

    @Override
    protected void onResume() {
        super.onResume();
        setupSavedGeneral();
        setupLastClicked();
    }
}