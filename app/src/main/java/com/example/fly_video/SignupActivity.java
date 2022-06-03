package com.example.fly_video;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseFirestore database;
    EditText email,password,name;
    Button login,create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initCompenent();

        auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memail,mpass,mname;
                memail=email.getText().toString();
                mpass=password.getText().toString();
                mname=name.getText().toString();

                Users users=new Users();
                users.setEmail(memail);
                users.setName(mname);
                users.setPassword(mpass);


                auth.createUserWithEmailAndPassword(memail,mpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("Users")
                                    .document().set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                }
                            });
                            Toast.makeText(SignupActivity.this,"Yeni hesap oluşturuldu.",Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
    }
    private void initCompenent() {
        email=findViewById(R.id.emailInput);
        password=findViewById(R.id.passwordInput);
        login=findViewById(R.id.loginBtn);
        create=findViewById(R.id.createBtn);
        name=findViewById(R.id.nameTxt);

    }
}