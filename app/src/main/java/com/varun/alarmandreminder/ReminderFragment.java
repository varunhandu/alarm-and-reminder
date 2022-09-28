package com.varun.alarmandreminder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class ReminderFragment extends Fragment implements RecylerViewListener {
    View view;
    RecyclerView reminder;
    ArrayList<String> date,message;
    DatabaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reminder, container, false);
        db = new DatabaseHelper(getActivity());
        date = new ArrayList<>();
        message = new ArrayList<>();
        displayData();
        reminder = view.findViewById(R.id.reminders);
        ReminderAdapter RA = new ReminderAdapter(getActivity(),message,date,this);
        reminder.setAdapter(RA);
        reminder.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    private void displayData() {
        Cursor cursor = db.getReminder();
        if(cursor.getCount()==0){
            Snackbar.make(view,"No Reminders found",Snackbar.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                date.add(cursor.getString(2));
                message.add(cursor.getString(1));

            }
        }
    }

    @Override
    public void OnItemClicked(int index) {
        Snackbar.make(view,"index: "+(index+1),Snackbar.LENGTH_SHORT).show();
        Intent updaterem = new Intent(getActivity(),AddNew.class);
        updaterem.putExtra("checked",false);
        updaterem.putExtra("Date",date.get(index));
        updaterem.putExtra("message",message.get(index));
        startActivity(updaterem);
    }

}