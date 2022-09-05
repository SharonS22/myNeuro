package com.myneuro;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnlogin;
    TextView forgot, signup;
    AppCompatCheckBox showpasslog;
    DBHelper_MyNeuro DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        btnlogin = findViewById(R.id.btnsignin1);
        signup = findViewById(R.id.btnsignup);
        forgot = findViewById(R.id.btnforgot);
        showpasslog = findViewById(R.id.showpasslog);
        DB = new DBHelper_MyNeuro(this);

        //Login Button
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals(""))
                    Toast.makeText(com.myneuro.LoginActivity.this, "Please Enter All The Fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if (checkuserpass == true) {
                        Toast.makeText(com.myneuro.LoginActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(com.myneuro.LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        //Forgot password button
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                startActivity(intent);
            }
        });

        //Sign up button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Show password button
        showpasslog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean value) {
                if (value) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }
}