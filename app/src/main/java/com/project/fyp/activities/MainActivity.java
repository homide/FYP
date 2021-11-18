package com.project.fyp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import com.project.fyp.database.DatabaseHelper;
import com.project.fyp.models.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //UI
    //    static final float END_SCALE = 0.7f;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ViewFlipper viewFlipper;
    TextView welcomeUserTV;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    //Variables
    User currentUser;

    //Database
    DatabaseHelper databaseHelper;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        welcomeUserTV = findViewById(R.id.welcomeUserTextView);

        firebaseDatabase = FirebaseDatabase.getInstance("https://fyp-main-144de-default-rtdb.asia-southeast1.firebasedatabase.app/");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();

        databaseHelper = new DatabaseHelper(this);

        getUserData();

//        findViewById(R.id.wishlist_toolbar_icon).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, WishlistActivity.class));
//            }
//        });
//
        findViewById(R.id.buttonBar).setOnClickListener(v->{
                startActivity(new Intent(MainActivity.this, SelectCategoryClass.class));
        });

        navigationDrawer();
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

//        navigationDrawerAnimation();
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here
        int id = item.getItemId();

        if (id == R.id.logOut){
            databaseHelper.deleteLoginDetails();
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        }
//        else if (id == R.id.wishlist){
//            Intent cinemaIntent = new Intent(this, WishlistActivity.class);
//            startActivity(cinemaIntent);
//        }
//        else if (id == R.id.category){
//            Intent intent = new Intent(this, SelectCategoryClass.class);
//            startActivity(intent);
//        }
//        else if(id == R.id.myAccount || id == R.id.settings || id == R.id.legalAndAbout){
//            Toast.makeText(this, "This doesn't have a function yet", Toast.LENGTH_SHORT).show();
//        }

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