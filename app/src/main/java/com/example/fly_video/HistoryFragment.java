package com.example.fly_video;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fly_video.databinding.FragmentHistoryBinding;
import com.example.fly_video.databinding.FragmentMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    List<Model> modelList=new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    CustomAdapter adapter;
    Button lisbtn;
    FirebaseFirestore db2=FirebaseFirestore.getInstance();
    String data;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHistoryBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        lisbtn=binding.listele;
        mRecyclerView=binding.recyclerView;

        Bundle bundle=getArguments();
        if(bundle!=null) {
            data = bundle.getString("key");
            uploadData();
        }else{
            Toast.makeText(HistoryFragment.this.getActivity(), "Kod alanı boş", Toast.LENGTH_SHORT).show();
        }
        mRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        lisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showData();
            }
        });
        return root;
    }


    private void showData() {
        db2.collection("Codes")
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
                      adapter=new CustomAdapter(HistoryFragment.this,modelList);
                      mRecyclerView.setAdapter(adapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HistoryFragment.this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
    public void delete(int index){
          db.collection("Codes").document(modelList.get(index).getId())
                  .delete()
                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {

                          Toast.makeText(HistoryFragment.this.getActivity(), "Silindi ....", Toast.LENGTH_SHORT).show();
                          showData();
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Toast.makeText(HistoryFragment.this.getActivity(), " Başarısız.", Toast.LENGTH_SHORT).show();
                      }
                  });
    }

    private void uploadData() {
        String id= UUID.randomUUID().toString();
        Map<String,Object> doc=new HashMap<>();
        doc.put("id",id);
        doc.put("code",data);
        doc.put("search",data.toLowerCase());
        db.collection("Codes").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(HistoryFragment.this.getActivity(), "Yeni kod eklendi.", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HistoryFragment.this.getActivity(), "Yeni kod ekleme başarısız.", Toast.LENGTH_SHORT).show();

                    }
                });

    }
    /*private void searchData(String s) {
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        MenuInflater inflater1= getActivity().getMenuInflater();
        inflater1.inflate(R.menu.menu_search,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)item.getActionView();
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
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return false;
    }*/

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding=null;
    }
}
