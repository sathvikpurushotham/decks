package com.example.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList card_id, card_title, card_answer, card_count;

    CustomAdapter(Activity activity,Context context, ArrayList card_id,ArrayList card_title,ArrayList card_answer, ArrayList card_count)
    {
        this.activity=activity;
          this.context= context;
          this.card_id= card_id;
          this.card_title= card_title;
          this.card_answer= card_answer;
          this.card_count= card_count;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.my_row,parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.card_id_txt.setText(String.valueOf(card_id.get(position)));
        holder.card_title_txt.setText(String.valueOf(card_title.get(position)));
//        holder.card_answer_txt.setText(String.valueOf(card_answer.get(position)));
        holder.card_count_txt.setText(String.valueOf(card_count.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(card_answer.get(position)).equals("SwitchToImg"))
                {
                    Intent intent = new Intent(context,ImageCardView.class);
                    intent.putExtra("id",String.valueOf(card_id.get(position)));
                    intent.putExtra("title",String.valueOf(card_title.get(position)));
//                    intent.putExtra("answer",String.valueOf(card_answer.get(position)));
                    intent.putExtra("count",String.valueOf(card_count.get(position)));
                    activity.startActivityForResult(intent,1);
                }
                else
                {
                    Intent intent = new Intent(context,ViewActivity.class);
                    intent.putExtra("id",String.valueOf(card_id.get(position)));
                    intent.putExtra("title",String.valueOf(card_title.get(position)));
                    intent.putExtra("answer",String.valueOf(card_answer.get(position)));
                    intent.putExtra("count",String.valueOf(card_count.get(position)));
                    activity.startActivityForResult(intent,1);
                }

//
            }

        });
    }

    @Override
    public int getItemCount() {
        return card_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView card_id_txt, card_title_txt,card_answer_txt,card_count_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_id_txt= itemView.findViewById(R.id.card_id_txt);
            card_title_txt= itemView.findViewById(R.id.card_title_txt_view);
//            card_answer_txt= itemView.findViewById(R.id.card_answer_txt_view);
            card_count_txt= itemView.findViewById(R.id.card_count_txt_view);
            mainLayout= itemView.findViewById(R.id.mainLayout);
        }
    }


}
