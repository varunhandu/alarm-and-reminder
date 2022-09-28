package com.varun.alarmandreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {
    private Context context;
    private ArrayList<String> message, time;
    private RecylerViewListener listener;
    public AlarmAdapter(Context context, ArrayList message, ArrayList time,RecylerViewListener listener) {
        this.context = context;
        this.message = message;
        this.time = time;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlarmAdapter.AlarmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_alrm,parent,false);//cannot inflate at runtime
        return new AlarmAdapter.AlarmHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.AlarmHolder holder, int position) {
        holder.message.setText(String.valueOf(message.get(position)));
        holder.time.setText(String.valueOf(time.get(position)));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return time.size();
    }

    public class AlarmHolder extends RecyclerView.ViewHolder {
        TextView time, message;
        CardView cardView;
        public AlarmHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            message = itemView.findViewById(R.id.label);
            cardView = itemView.findViewById(R.id.cardalarm);
        }
    }
}
