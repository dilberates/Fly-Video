package com.example.fly_video;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.adapters.ActionMenuViewBindingAdapter;
import androidx.fragment.app.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashBoardActivity extends AppCompatActivity  {

    FirebaseAuth auth;
    FirebaseFirestore database;
    FirebaseUser user;
    DatabaseReference ref;
    EditText secretCode;
    TextView name,mail;
    Button join,share;
    CardView mainCardView,settingCardView;
    BottomNavigationView navigationView;
    Fragment settingFragment ,mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        initComponents();
        auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();
        navigationView.setSelectedItemId(R.id.mainPage);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment fragment=null;

                switch(item.getItemId()){
                    case R.id.out:
                        new AlertDialog.Builder(DashBoardActivity.this)
                                .setIcon(R.drawable.ic_baseline_warning_24)
                                .setTitle("ÇIKIŞ")
                                .setMessage("Çıkış yapmak istediğinize emin misiniz ?")
                                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startActivity(new Intent(DashBoardActivity.this,loginActivity.class));
                                    }
                                }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                        break;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.body,settingFragment).commit();

                        break;

                    case R.id.mainPage:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.body,mainFragment).commit();

                        break;

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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kod=secretCode.getText().toString();
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); //Share intentini oluşturuyoruz
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Fly Video Katılma Kodu");//share mesaj konusu
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Fly Video Katılma Kodu : "+kod);//share mesaj içeriği
                    startActivity(Intent.createChooser(sharingIntent, "Paylaşmak İçin Seçiniz"));//Share intentini başlığı ile birlikte başlatıyoruz

            }
        });
    }



    private void initComponents() {
        secretCode=findViewById(R.id.secretBox);
        join=findViewById(R.id.joinBtn);
        share=findViewById(R.id.shareBtn);
        navigationView=findViewById(R.id.bottomNavigationView);
        settingFragment=new SettingsFragment();
        mainFragment=new mainFragment();
        mainCardView=findViewById(R.id.cardViewMain);
        settingCardView=findViewById(R.id.cardViewSetting);
        name=findViewById(R.id.fullNameTxt);
        mail=findViewById(R.id.emailTxt);


    }


}