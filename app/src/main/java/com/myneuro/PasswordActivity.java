package com.myneuro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PasswordActivity extends AppCompatActivity {

    EditText username;
    Button reset;
    DBHelper_MyNeuro DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        username = findViewById(R.id.username_reset);
        reset = findViewById(R.id.btnreset);
        DB = new DBHelper_MyNeuro(this);

        //Reset password
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();

                Boolean checkuser = DB.checkusername(user);
                if (checkuser == true) {
                    Intent intent = new Intent(getApplicationContext(), ResetActivity.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                } else {
                    Toast.makeText(com.myneuro.PasswordActivity.this, "User Does Not Exist", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}