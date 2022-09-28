package com.varun.alarmandreminder;

import static java.lang.String.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderHolder> {
    private Context context;
    private ArrayList<String> date,message;
    private RecylerViewListener listener;
    public ReminderAdapter(Context context, ArrayList message, ArrayList date, RecylerViewListener listener) {
        this.context = context;
        this.message = message;
        this.date = date;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_remind,parent,false);
        return new ReminderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderHolder holder, int position) {
        holder.message.setText(valueOf(message.get(position)));
        holder.date.setText(valueOf(date.get(position)));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class ReminderHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{
        TextView date,message;
        CardView cardView;

        public ReminderHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            message = itemView.findViewById(R.id.label2);
            cardView = itemView.findViewById(R.id.cardremind);
        }

        /*@Override
        public void onClick(View view) {
            clickRemind.onReminderClick(getBindingAdapterPosition());
        }*/
    }
    /*public interface OnReminderClickListener{
        void onReminderClick(int position);
    }*/
}
