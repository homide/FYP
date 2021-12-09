package com.project.fyp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.fyp.R;
import com.project.fyp.activities.login.LoginPage;
import com.project.fyp.activities.wishlist.WishlistActivity;
import com.project.fyp.adapters.MyAdapterWebView;
import com.project.fyp.database.DatabaseHelper;
import com.project.fyp.models.User;
import com.project.fyp.models.Websites;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //UI
    //    static final float END_SCALE = 0.7f;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView welcomeUserTV;
    RecyclerView recyclerView;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    //Variables
    User currentUser;
    ArrayList<Websites> websitesList;

    //Database
    DatabaseHelper databaseHelper;

    //Adapter
    MyAdapterWebView adapterWebView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        welcomeUserTV = findViewById(R.id.welcomeUserTextView);
        recyclerView = findViewById(R.id.websiteRecycler);

        firebaseDatabase = FirebaseDatabase.getInstance("https://fyp-main-144de-default-rtdb.asia-southeast1.firebasedatabase.app/");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();

        databaseHelper = new DatabaseHelper(this);

        getUserData();

        getAllWebsites();
        adapterWebView = new MyAdapterWebView(this, websitesList);
        GridLayoutManager glm = new GridLayoutManager(this,3,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(adapterWebView);

        findViewById(R.id.wishlist_toolbar_icon).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, WishlistActivity.class)));

        findViewById(R.id.buttonBar).setOnClickListener(v->{
                startActivity(new Intent(MainActivity.this, SelectCategoryClass.class));
        });

        navigationDrawer();
    }

    private void getAllWebsites(){
        websitesList = new ArrayList<>();
        Websites website1 = new Websites("https://www.flipkart.com/","https://media.glassdoor.com/sqll/300494/flipkart-com-squarelogo-1433217726546.png");
        websitesList.add(website1);
        Websites website2 = new Websites("https://www.amazon.in/","https://i.pinimg.com/originals/01/ca/da/01cada77a0a7d326d85b7969fe26a728.jpg");
        websitesList.add(website2);
        Websites website3 = new Websites("https://www.snapdeal.com/","https://upload.wikimedia.org/wikipedia/en/thumb/3/35/Snapdeal_Logo_new.png/480px-Snapdeal_Logo_new.png");
        websitesList.add(website3);
        Websites website4 = new Websites("https://www.myntra.com/","https://images.indianexpress.com/2021/01/myntra.png");
        websitesList.add(website4);
        Websites website5 = new Websites("https://www.shopclues.com/","https://images.shopclues.com/images/ui/s_logo.png");
        websitesList.add(website5);
        Websites website6 = new Websites("https://paytmmall.com/","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbTlvGzotj7JDiYug5-2FFKPyC9koBXDPywA&usqp=CAU");
        websitesList.add(website6);
        Websites website7 = new Websites("https://www.ajio.com/","https://play-lh.googleusercontent.com/RWNQyHoMPJ-Z8ApQhQchXsfoBXrj3By1cf49GPRK6-hYiIv0RL6Z1fdZl1OAUpqHCB8");
        websitesList.add(website7);
        Websites website9 = new Websites("https://www.croma.com/","https://m.media-amazon.com/images/S/abs-image-upload-na/8/AmazonStores/A21TJRUUN4KGV/19dae191bb75eda8e3fc9ffc1e335b9f.w400.h400.jpg");
        websitesList.add(website9);
        Websites website10 = new Websites("https://www.reliancedigital.in/","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQu2gpsB_ZhUCb4-8aVrkvG9ONLVKLzDz3Ocg&usqp=CAU");
        websitesList.add(website10);
    }

    private void getUserData(){
        String mail = databaseHelper.getEmail();
        databaseReference = firebaseDatabase.getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if (user.getMail().equals(mail)){
                        currentUser = user;
                    }
                }
                setUserData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setUserData(){
        welcomeUserTV.setText("Welcome " + currentUser.getName() + ".");
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
            finishAffinity();
        }
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

}