package com.example.fly_video;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView mcode;
    View mview;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mview=itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());

            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v,getAdapterPosition());
                return false;
            }
        });
        mcode=itemView.findViewById(R.id.codeText);
    }
    private ViewHolder.ClickListener mClickListener;

    public  interface  ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
    public  void setOnClickListener(ViewHolder.ClickListener clickListener){
    mClickListener=clickListener;
    }
}
