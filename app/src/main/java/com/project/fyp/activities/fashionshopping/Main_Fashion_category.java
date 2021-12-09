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
import com.project.fyp.activities.generalshopping.Main_General_category;
import com.project.fyp.activities.login.LoginPage;
import com.project.fyp.activities.webview.WebViewActivity;
import com.project.fyp.activities.wishlist.MyAdapterSavedFashion;
import com.project.fyp.activities.wishlist.MyAdapterSavedGeneral;
import com.project.fyp.activities.wishlist.WishlistActivity;
import com.project.fyp.adapters.MyAdapterFashion;
import com.project.fyp.adapters.MyAdapterGeneral;
import com.project.fyp.database.DatabaseHelper;
import com.project.fyp.interfaces.arraySave;
import com.project.fyp.models.Product;
import com.project.fyp.models.SearchList;
import com.project.fyp.threads.CallingSites;

import java.util.ArrayList;

public class Main_Fashion_category extends AppCompatActivity implements arraySave, NavigationView.OnNavigationItemSelectedListener {

    public AutoCompleteTextView searchbar;
    public String searchtext;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView savedFashionItemsTV, lastSeenProductsTV;
    RecyclerView savedRecycler, lastSeenRecycler;

    //Webview
    ImageView flipkartWeb, amazonWeb, snapdealWeb, shopcluesWeb, paytmWeb,bewakoofWeb, myntaWeb, koovsWeb;

    //Database
    DatabaseHelper databaseHelper;

    // Adapter
    MyAdapterSavedFashion adapterSavedFashion;
    MyAdapterFashion adapterFashion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion_category);

        drawerLayout = findViewById(R.id.drawer_layout);
        searchbar = findViewById(R.id.searchText);
        flipkartWeb = findViewById(R.id.flipkartWeb);
        amazonWeb = findViewById(R.id.amazonWeb);
        snapdealWeb = findViewById(R.id.snapdealWeb);
        shopcluesWeb = findViewById(R.id.shopcluesWeb);
        paytmWeb = findViewById(R.id.paytmMallWeb);
        koovsWeb = findViewById(R.id.koovsWeb);
        bewakoofWeb = findViewById(R.id.bewakoofWeb);
        myntaWeb = findViewById(R.id.myntraWeb);
        savedFashionItemsTV = findViewById(R.id.savedFashionItemsTV);
        lastSeenProductsTV = findViewById(R.id.lastSeenProductsTV);
        savedRecycler = findViewById(R.id.savedItemRecyclerView);
        lastSeenRecycler = findViewById(R.id.lastSeenRecyclerView);

        databaseHelper = new DatabaseHelper(this);

        setUpOnClicks();

        if (databaseHelper.checkFashionItemList()){
            setupSavedFashion();
        }else{
            savedFashionItemsTV.setVisibility(View.INVISIBLE);
        }

        if (databaseHelper.checkFashionItemListLastClicked()){
            setupLastClicked();
        }else{
            lastSeenProductsTV.setVisibility(View.INVISIBLE);
        }


        String[] suggestions = getResources().getStringArray(R.array.suggestions);
        searchbar.setAdapter(new ArrayAdapter<>(Main_Fashion_category.this, android.R.layout.simple_list_item_1, suggestions));

        navigationDrawer();
    }

    private void setupLastClicked(){
        ArrayList<Product> arrayList = databaseHelper.getFashionItemsLastClicked();
        lastSeenProductsTV.setVisibility(View.VISIBLE);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        lastSeenRecycler.setLayoutManager(llm);
        adapterFashion = new MyAdapterFashion(this,arrayList);
        lastSeenRecycler.setAdapter(adapterFashion);
        adapterFashion.notifyDataSetChanged();
    }

    private void setupSavedFashion(){
        ArrayList<Product> arrayList = databaseHelper.getFashionItemsWishlist();
        savedFashionItemsTV.setVisibility(View.VISIBLE);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        savedRecycler.setLayoutManager(llm);
        adapterSavedFashion = new MyAdapterSavedFashion(this,arrayList);
        savedRecycler.setAdapter(adapterSavedFashion);
        adapterSavedFashion.notifyDataSetChanged();
    }

    private void setUpOnClicks(){

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

        koovsWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_Fashion_category.this, WebViewActivity.class);
            intent.putExtra("url","https://koovs.com/");
            startActivity(intent);
        });

        bewakoofWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_Fashion_category.this, WebViewActivity.class);
            intent.putExtra("url","https://www.bewakoof.com/");
            startActivity(intent);
        });

        myntaWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_Fashion_category.this, WebViewActivity.class);
            intent.putExtra("url","https://www.myntra.com/");
            startActivity(intent);
        });

        flipkartWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_Fashion_category.this, WebViewActivity.class);
            intent.putExtra("url","https://www.flipkart.com/");
            startActivity(intent);
        });

        amazonWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_Fashion_category.this, WebViewActivity.class);
            intent.putExtra("url","https://www.amazon.in/");
            startActivity(intent);
        });

        snapdealWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_Fashion_category.this, WebViewActivity.class);
            intent.putExtra("url","https://www.snapdeal.com/");
            startActivity(intent);
        });

        shopcluesWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_Fashion_category.this, WebViewActivity.class);
            intent.putExtra("url","https://www.shopclues.com/");
            startActivity(intent);
        });

        paytmWeb.setOnClickListener(v->{
            Intent intent = new Intent(Main_Fashion_category.this, WebViewActivity.class);
            intent.putExtra("url","https://paytmmall.com/");
            startActivity(intent);
        });

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

        if (id == R.id.logOut){
            databaseHelper.deleteLoginDetails();
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
            finishAffinity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupLastClicked();
        setupSavedFashion();
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