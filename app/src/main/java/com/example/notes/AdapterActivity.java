package com.example.notes;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterActivity extends RecyclerView.Adapter<AdapterActivity.RecyclerViewHolder>{

    public ArrayList<NOTES>xList;
    public Context context;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView xHeadView;
        public TextView xDateView;
        public RelativeLayout cardBody;
        public RecyclerViewHolder(View itemView){
            super(itemView);
            xHeadView = itemView.findViewById(R.id.textView);
            xDateView = itemView.findViewById(R.id.textView2);
            cardBody = itemView.findViewById(R.id.card_body);
        }
    }
    public AdapterActivity(ArrayList<NOTES> list, Context context){
        xList = list;
        this.context = context;
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

        final Drawable redBackground = ContextCompat.getDrawable(context, R.drawable.round_rect_red);
        final Drawable yellowBackground = ContextCompat.getDrawable(context, R.drawable.round_rect_yellow);
        final Drawable blueBackground = ContextCompat.getDrawable(context, R.drawable.round_rect_blue);
        final Drawable purpleBackground = ContextCompat.getDrawable(context, R.drawable.round_rect_purple);
        final Drawable greenBackground = ContextCompat.getDrawable(context, R.drawable.round_rect_green);


        NOTES currentItem = xList.get(position);

        holder.xHeadView.setText(currentItem.getHead());
        holder.xDateView.setText(currentItem.getDate());

        if((position+1) == 1)
        holder.cardBody.setBackground(redBackground);
        else
        if((position+1)%5 == 0)
        holder.cardBody.setBackground(greenBackground);
        else
        if((position+1)%4 == 0)
            holder.cardBody.setBackground(purpleBackground);
        else
        if((position+1)%3 == 0)
            holder.cardBody.setBackground(blueBackground);
        else
        if((position+1)%2 == 0)
            holder.cardBody.setBackground(yellowBackground);
        else
            holder.cardBody.setBackground(redBackground);
    }

    @Override
    public int getItemCount() {
        return xList.size();
    }


}
