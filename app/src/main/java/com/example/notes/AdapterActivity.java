package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterActivity extends RecyclerView.Adapter<AdapterActivity.RecyclerViewHolder>{

    public ArrayList<NOTES>xList;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView xHeadView;
        public TextView xDateView;
        public RecyclerViewHolder(View itemView){
            super(itemView);
            xHeadView = itemView.findViewById(R.id.textView);
            xDateView = itemView.findViewById(R.id.textView2);
        }
    }

    public AdapterActivity(ArrayList<NOTES> list){ xList = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card, parent, false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {


        NOTES currentItem = xList.get(position);

        holder.xHeadView.setText(currentItem.getHead());
        holder.xDateView.setText(currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return xList.size();
    }


}
