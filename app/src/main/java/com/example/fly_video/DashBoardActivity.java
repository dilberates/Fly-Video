package com.example.fly_video;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
public class DashBoardActivity extends AppCompatActivity  {

    FirebaseAuth auth;
    FirebaseFirestore database;
    FirebaseUser user;
    DatabaseReference ref;
    TextView name,mail;
    CardView mainCardView,settingCardView;
    BottomNavigationView navigationView;
    MainFragment mainFragment;
    SettingsFragment settingsFragment;
    HistoryFragment historyFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        initComponents();
        auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();
        navigationView.setSelectedItemId(R.id.mainPage);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.body,mainFragment).commit();

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
                                        startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
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
                                .replace(R.id.body,settingsFragment).commit();
                        return true;

                    case R.id.mainPage:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.body,mainFragment).commit();
                        return true;
                    case R.id.history:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.body,historyFragment).commit();
                        return true;
                }

                return false;
            }



        });

    }
    private void initComponents() {
        navigationView=findViewById(R.id.bottomNavigationView);
        settingsFragment=new SettingsFragment();
        historyFragment=new HistoryFragment();
        mainFragment=new MainFragment();
        mainCardView=findViewById(R.id.cardViewMain);
        settingCardView=findViewById(R.id.cardViewSetting);
        name=findViewById(R.id.fullNameTxt);
        mail=findViewById(R.id.emailTxt);


    }


}