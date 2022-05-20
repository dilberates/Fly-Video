package com.example.fly_video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class loginActivity extends AppCompatActivity {

    EditText email,password;
    Button login,sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initCompenent();

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this,SignupActivity.class));
            }
        });
    }

    private void initCompenent() {
        email=findViewById(R.id.emailInput);
        password=findViewById(R.id.passwordInput);
        login=findViewById(R.id.loginBtn);
        sign=findViewById(R.id.createBtn);

    }

}