package com.example.fly_video;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.fly_video.databinding.FragmentSettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {


    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private FragmentSettingsBinding binding;
    private FirebaseAuth auth;
    Button maccounti,minfo,mhelp;
    TextView name,mail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentSettingsBinding.inflate(inflater,container,false);
        View root=binding.getRoot();

        maccounti=binding.account;
        minfo=binding.info;
        mhelp=binding.help;
        name=binding.fullNameTxt;
        mail=binding.emailTxt;

        auth= FirebaseAuth.getInstance();
        String uid =auth.getCurrentUser().getUid();
        database=FirebaseDatabase.getInstance("https://flyvideo-a5ed2-default-rtdb.firebaseio.com/");
        userRef=database.getReference("Users").child(uid);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String dname = ds.child("name").getValue(String.class);
                    String dmail = ds.child("password").getValue(String.class);
                    name.setText(dname);
                    mail.setText(dmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }
}