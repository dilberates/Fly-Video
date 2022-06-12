package com.example.fly_video;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity  {

    FirebaseAuth auth;
    FirebaseFirestore database;
    FirebaseUser user;
    DatabaseReference ref;
    CardView mainCardView,settingCardView;
    BottomNavigationView navigationView;
    MainFragment mainFragment;
    HistoryFragment historyFragment;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    List<Model> modelList=new ArrayList<>();


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
                                        auth.signOut();
                                        startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
                                    }
                                }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                        break;


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
        historyFragment=new HistoryFragment();
        mainFragment=new MainFragment();
        mainCardView=findViewById(R.id.cardViewMain);

    }
   private void searchData(String s) {
        db.collection("Codes").whereEqualTo("search",s.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelList.clear();
                        for(DocumentSnapshot doc:task.getResult()){
                            Model model=new Model(doc.getString("id"),
                                    doc.getString("code"));
                            modelList.add(model);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    public boolean onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

       getMenuInflater().inflate(R.menu.menu_search,menu);
       MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchData(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


}