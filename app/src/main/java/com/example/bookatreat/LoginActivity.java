package com.example.bookatreat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookatreat.Customer.CustomerActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText usernameET, passwordET;
    String usernameST, passwordST;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        //kan du se min comment

        try // Remove ActionBar
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_login); // End of removing ActionBar

        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);

        TextView btnToSignup = findViewById(R.id.signupBTN);
        Button loginBtn = findViewById(R.id.loginbtn_login);

        btnToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(LoginActivity.this, SignupActivity.class);

                startActivity(secondIntent);
            }
        });
// fjfhsefhdhf
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                usernameST = usernameET.getText().toString();
                passwordST = passwordET.getText().toString();
                if(!usernameST.isEmpty() && passwordST.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                } else if (!passwordST.isEmpty() && usernameST.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter your username", Toast.LENGTH_SHORT).show();
                } else if (usernameST.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
                //} else if(/* If username and password doesn't match any id in database*/){
                //    Toast.makeText(getApplicationContext(), "The inserted data is not correct", Toast.LENGTH_SHORT).show();
                else {
                    Intent loginIntent = new Intent(LoginActivity.this, CustomerActivity.class);

                    startActivity(loginIntent);
                }
            }
        });
    }
}

