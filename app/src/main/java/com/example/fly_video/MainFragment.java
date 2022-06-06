package com.example.fly_video;

import android.content.Intent;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fly_video.databinding.FragmentMainBinding;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    Button join,share;
    EditText secretCode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentMainBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        secretCode=binding.secretBox;
        join=binding.joinBtn;
        share=binding.shareBtn;
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
                JitsiMeetActivity.launch(getActivity(),options);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Bundle bundle=new Bundle();
                bundle.putString("key",secretCode.getText().toString());
                HistoryFragment historyFragment=new HistoryFragment();
                historyFragment.setArguments(bundle);
                transaction.replace(R.id.body,historyFragment).commit();
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

        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding=null;
    }
}