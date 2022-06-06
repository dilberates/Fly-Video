package com.example.fly_video;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    HistoryFragment listActivity;
    List<Model> modelList;

    public CustomAdapter(HistoryFragment listActivity, List<Model> modelList) {
        this.listActivity = listActivity;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View itemView= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(listActivity.getActivity());
                String []options={"Güncelle","Sil"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                     if(which==0){
                         String id=modelList.get(position).getId();
                         String code=modelList.get(position).getCode();
                         Intent intent=new Intent(listActivity.getActivity(),MainActivity2.class);

                          intent.putExtra("pId",id);
                          intent.putExtra("pCode",code);

                          listActivity.startActivity(intent);
                     }
                     if(which==1){
                         listActivity.delete(position);
                     }
                    }
                }).create().show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
     viewHolder.mcode.setText(modelList.get(i).getCode());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
