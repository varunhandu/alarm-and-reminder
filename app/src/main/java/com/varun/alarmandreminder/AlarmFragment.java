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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AlarmFragment extends Fragment implements RecylerViewListener{
    View view;
    RecyclerView alarm;
    ArrayList<String> time,message;
    DatabaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_alarm, container, false);
        db = new DatabaseHelper(getActivity());
        time = new ArrayList<>();
        message = new ArrayList<>();
        alarm = view.findViewById(R.id.alarms);
        displayData();
        AlarmAdapter AA = new AlarmAdapter(getActivity(),message,time,this);
        alarm.setAdapter(AA);
        alarm.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    private void displayData() {
        Cursor cursor = db.getAlarm();
        if(cursor.getCount()==0){
            Snackbar.make(view,"No alarms found",Snackbar.LENGTH_SHORT).show();
            return;
        }
        else{
            while(cursor.moveToNext()){
                time.add(cursor.getString(2));
                message.add(cursor.getString(1));
            }
        }
    }

    @Override
    public void OnItemClicked(int index) {
        Snackbar.make(view,"item: "+(index+1),Snackbar.LENGTH_SHORT).show();
        Intent updateal = new Intent(getActivity(),AddNew.class);
        updateal.putExtra("checked",true);
        updateal.putExtra("Time",time.get(index));
        updateal.putExtra("message",message.get(index));
        startActivity(updateal);
    }
}