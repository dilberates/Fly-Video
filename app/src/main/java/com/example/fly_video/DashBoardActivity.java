package com.example.fly_video;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashBoardActivity extends AppCompatActivity  {

    EditText secretCode;
    Button join,share;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initComponents();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                switch(item.getItemId()){
                    case R.id.out:
                        startActivity(new Intent(DashBoardActivity.this,loginActivity.class));

                }

                return false;
            }
        });



        URL serverURL;
        try {
            serverURL =new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions=
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            JitsiMeetConferenceOptions options=new JitsiMeetConferenceOptions.Builder()
                    .setRoom(secretCode.getText().toString())
                    .setWelcomePageEnabled(false)
                    .build();
                JitsiMeetActivity.launch(DashBoardActivity.this,options);

            }
        });
    }

    private void initComponents() {
        secretCode=findViewById(R.id.secretBox);
        join=findViewById(R.id.joinBtn);
        share=findViewById(R.id.shareBtn);
        navigationView=findViewById(R.id.bottomNavigationView);

    }


}