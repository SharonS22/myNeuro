package com.myneuro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetActivity extends AppCompatActivity {

    TextView username, heading;
    EditText pass, repass;
    Button confirm;
    DBHelper_MyNeuro DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        heading = findViewById(R.id.heading);
        username = findViewById(R.id.username_reset_text);
        pass = findViewById(R.id.password_reset);
        repass = findViewById(R.id.repassword_reset);
        confirm = findViewById(R.id.btnconfirm);
        DB = new DBHelper_MyNeuro(this);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));

        //Password reset - password match
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String password = pass.getText().toString();
                String repassword = repass.getText().toString();
                if (password.equals(repassword)) {

                    Boolean checkpasswordupdate = DB.updatepassword(user, password);
                    if (checkpasswordupdate == true) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(com.myneuro.ResetActivity.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(com.myneuro.ResetActivity.this, "Password Not Updated", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(com.myneuro.ResetActivity.this, "Passwords Not Matching", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}




