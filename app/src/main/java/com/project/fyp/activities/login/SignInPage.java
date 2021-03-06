package com.project.fyp.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.fyp.R;
import com.project.fyp.activities.MainActivity;
import com.project.fyp.database.DatabaseHelper;

import java.util.HashMap;

public class SignInPage extends AppCompatActivity {

    //UI
    EditText email, username, password, passwordConfirm;
    Button signUpButton;

    //Variables
    String emailText, usernameText, passwordText, passConfirmText;
    boolean checkMailExist = false;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    //Database
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        email = findViewById(R.id.emailEditText);
        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        passwordConfirm = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);

        firebaseDatabase = FirebaseDatabase.getInstance("https://fyp-main-144de-default-rtdb.asia-southeast1.firebasedatabase.app");
        firebaseAuth = FirebaseAuth.getInstance();

        databaseHelper = new DatabaseHelper(this);

        signUpButton.setOnClickListener(v->{
            emailText = email.getText().toString().trim();
            usernameText = username.getText().toString().trim();
            passwordText = password.getText().toString().trim();
            passConfirmText = passwordConfirm.getText().toString().trim();
            if (checkForDetails(emailText,passwordText,passConfirmText,usernameText)){
                checkEmailExistsOrNot(emailText);
            }
        });

    }

    private boolean checkEmailExistsOrNot(String mail){
        firebaseAuth.fetchSignInMethodsForEmail(mail).addOnCompleteListener(task -> {
            if (task.getResult().getSignInMethods().size() == 0){
                sendInfo(emailText, usernameText);
                firebaseAuth.createUserWithEmailAndPassword(emailText,passwordText);
            }else {
                Toast.makeText(SignInPage.this, "User with this email already exists!", Toast.LENGTH_LONG).show();
            }
        });

        return checkMailExist;
    }

    private void sendInfo(String mail, String name){
        databaseReference = firebaseDatabase.getReference();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mail", mail);
        hashMap.put("name", name);

        databaseReference.child("Users").push().setValue(hashMap);

        Toast.makeText(SignInPage.this, "Profile Created! Please login again.", Toast.LENGTH_LONG).show();
        addToDatabase(emailText, passwordText, "true");

        onBackPressed();
    }

    private void addToDatabase(String email, String password, String loggedIn){
        databaseHelper.deleteLoginDetails();
        databaseHelper.insertLoginDetails(email, password,loggedIn);
    }


    private boolean checkForDetails(String emailText, String passwordText, String passConfirmText, String usernameText){
        if (password.length() < 7){
            Toast.makeText(this,"Password should be greater than 7 characters", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!emailText.contains("@")){
            Toast.makeText(this,"Incorrect Email ID", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!passwordText.equals(passConfirmText)){
            Toast.makeText(this,"Password don't match!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (usernameText.length() < 2){
            Toast.makeText(this,"Username should have atleast 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}