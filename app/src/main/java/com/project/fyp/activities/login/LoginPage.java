package com.project.fyp.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.project.fyp.R;
import com.project.fyp.activities.MainActivity;
import com.project.fyp.database.DatabaseHelper;

public class LoginPage extends AppCompatActivity {

    //UI
    TextView signInRedirectText, errorText;
    EditText emailEdit, passwordEdit;
    Button loginButton;

    //Variables
    String email, password;
    boolean log = false;

    //Firebase
    FirebaseAuth auth;

    //Database
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        signInRedirectText = findViewById(R.id.signInText);
        emailEdit = findViewById(R.id.emailEditText);
        passwordEdit = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        errorText = findViewById(R.id.errorText);

        auth = FirebaseAuth.getInstance();

        databaseHelper = new DatabaseHelper(LoginPage.this);

        if (databaseHelper.checkLoginDetails()){
            Intent intent = new Intent(LoginPage.this, MainActivity.class);
            startActivity(intent);
        }

        loginButton.setOnClickListener(v->{
            email = emailEdit.getText().toString().trim();
            password = passwordEdit.getText().toString().trim();
            checkForDetails(email,password);
            checkIDPass(email, password);
        });

        signInRedirectText.setOnClickListener(v->{
            Intent intent = new Intent(this, SignInPage.class);
            startActivity(intent);
        });
    }

    private void checkIDPass(String email, String password){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task ->{
            if (task.isSuccessful()){
                addToDatabase(email,password,"true");
                Toast.makeText(LoginPage.this, "Welcome back!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                startActivity(intent);
            }else{
                errorText.setVisibility(View.VISIBLE);
                errorText.setText("Incorrect email or password!");
            }
        });
    }

    private void addToDatabase(String email, String password, String loggedIn){
        databaseHelper.deleteLoginDetails();
        databaseHelper.insertLoginDetails(email, password,loggedIn);
    }

    private boolean checkForDetails(String email, String password){
        if (password.length() < 7){
            Toast.makeText(this,"Password should be greater than 7 characters", Toast.LENGTH_LONG).show();
            return false;
        }else if (!email.contains("@")){
            Toast.makeText(this,"Incorrect Email ID", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }
}