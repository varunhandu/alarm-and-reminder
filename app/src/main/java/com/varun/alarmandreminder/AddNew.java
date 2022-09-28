package com.varun.alarmandreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddNew extends AppCompatActivity {
    TextView time,date;
    EditText messageDate,messageTime;
    Button cancel,set,timeselect,dateselect;
    TimePicker timePicker;
    DatePicker datePicker;
    Calendar calendar;
    ConstraintLayout alarmLayout, reminderLayout;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        alarmLayout = findViewById(R.id.alarm_lay);
        timeselect = findViewById(R.id.timebut);
        time = findViewById(R.id.timetext);
        timePicker = findViewById(R.id.timepicker);
        messageTime = findViewById(R.id.message2);
        reminderLayout = findViewById(R.id.reminder_lay);
        dateselect = findViewById(R.id.datebut);
        date = findViewById(R.id.datetext);
        datePicker = findViewById(R.id.datepicker);
        messageDate = findViewById(R.id.message);
        DatabaseHelper helper = new DatabaseHelper(this);
        check = getIntent().getExtras().getBoolean("checked");
        String message;
        if(check){
            reminderLayout.setVisibility(View.VISIBLE);
            String setDate = getIntent().getExtras().getString("Date");
            if(setDate!=null){
                message = getIntent().getExtras().getString("message");
                date.setText(setDate);
                messageDate.setText(message);
                Toast.makeText(this,"date: "+setDate+" message: "+message,Toast.LENGTH_SHORT).show();
            }
        }
        else
            alarmLayout.setVisibility(View.VISIBLE);
            String setTime = getIntent().getExtras().getString("Time");
            if(setTime!=null){
                message = getIntent().getExtras().getString("message");
                time.setText(setTime);
                messageTime.setText(message);
                Toast.makeText(this,"time: "+setTime+" message: "+message,Toast.LENGTH_SHORT).show();
            }
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.setVisibility(View.GONE);
                messageDate.setVisibility((View.GONE));
                set.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                datePicker.setVisibility(View.VISIBLE);
                dateselect.setVisibility(View.VISIBLE);
            }
        });
        dateselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.setVisibility(View.GONE);
                dateselect.setVisibility((View.GONE));
                int temp;
                String month,day;
                temp = datePicker.getMonth()+1;
                if(temp<10)
                    month = "0"+temp;
                else
                    month = ""+temp;
                temp = datePicker.getDayOfMonth();
                if(temp<10)
                    day = "0"+temp;
                else
                    day = ""+temp;
                date.setText(datePicker.getYear()+"-"+month+"-"+day);
                date.setVisibility(View.VISIBLE);
                messageDate.setVisibility(View.VISIBLE);
                set.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time.setVisibility(View.GONE);
                messageTime.setVisibility((View.GONE));
                set.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                timePicker.setVisibility(View.VISIBLE);
                timeselect.setVisibility(View.VISIBLE);
            }
        });
        timeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.setVisibility(View.GONE);
                timeselect.setVisibility((View.GONE));
                String hour,minute;
                int temp;
                temp = timePicker.getHour();
                if(temp<10)
                    hour = "0"+temp;
                else
                    hour = ""+temp;
                temp = timePicker.getMinute();
                if(temp<10)
                    minute = "0"+temp;
                else
                    minute = ""+temp;

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
                calendar.set(Calendar.MINUTE,timePicker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                time.setText(hour+":"+minute);
                time.setVisibility(View.VISIBLE);
                messageTime.setVisibility(View.VISIBLE);
                set.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
            }
        });

        set = findViewById(R.id.button2);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNew.this,MainActivity.class);
                Boolean verify;
                String what,when;
                if(check){
                    what = messageDate.getText().toString();
                    when = date.getText().toString();
                    if(what.equals("") || when.equals("Choose Date")){
                        Snackbar.make(view ,"Please add a reminder and date",Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        verify = helper.addReminder(new Reminder(what,when));                 // check this line
                        if(verify)
                            intent.putExtra("Status","Reminder added");
                        else
                            intent.putExtra("Status","Reminder not added");
                        startActivity(intent);
                    }
                }
                else{
                    what = messageTime.getText().toString();
                    when = time.getText().toString();
                    if(when.equals("Choose Time")){
                        Snackbar.make(view,"Please specify the time",Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        verify = helper.addAlarm(new Alarm(what, when));                      // ditto
                        if (verify)
                            intent.putExtra("Status", "Alarm added");
                        else
                            intent.putExtra("Status", "Alarm not added");
                        setAlarm();
                        startActivity(intent);
                    }
                }
            }
        });
        cancel = findViewById(R.id.button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNew.this,MainActivity.class));
            }
        });
    }
    private void setAlarm(){
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent gotoAlarm = new Intent(this,AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,gotoAlarm,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000,pendingIntent);
    }
}