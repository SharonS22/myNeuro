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

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, repassword;
    Button signup;
    TextView signin;
    DBHelper_MyNeuro DB;
    AppCompatCheckBox showpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.btnsignup);
        signin = findViewById(R.id.btnsignin);
        showpass = findViewById(R.id.showpass);
        DB = new DBHelper_MyNeuro(this);

        //sign up button

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals(""))
                    Toast.makeText(com.myneuro.RegisterActivity.this, "Please Enter All The Fields", Toast.LENGTH_SHORT).show();
                else {
                    if (pass.equals(repass)) {
                        Boolean checkuser = DB.checkusername(user);
                        if (checkuser == false) {
                            Boolean insert = DB.insertData(user, pass);
                            if (insert == true) {
                                Toast.makeText(com.myneuro.RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(com.myneuro.RegisterActivity.this, "Registered Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(com.myneuro.RegisterActivity.this, "User Already Exists! Please Sign In", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(com.myneuro.RegisterActivity.this, "Password Not Matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

        //sign in button

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });

        //password visibility

        showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
};
