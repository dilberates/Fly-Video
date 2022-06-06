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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    Button updateButton;
    EditText mCode;
    String pId,pCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mCode=findViewById(R.id.codeEdt);
        updateButton=findViewById(R.id.update);
        Bundle bundle=getIntent().getExtras();
        pId=bundle.getString("pId");
        pCode=bundle.getString("pCode");
        mCode.setText(pCode);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=getIntent().getExtras();
                if(bundle != null){
                    String id=pId;
                    String code=mCode.getText().toString().trim();
                    updateData(id,code);
                } else{

                }
            }
        });

    }

    private void updateData(String id, String code) {
        db.collection("Codes").document(id)
                .update("code",code)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity2.this, "Güncellendi.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity2.this,DashBoardActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}