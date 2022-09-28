package com.varun.alarmandreminder;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    ImageView add;
    MenuItem alarm,remind;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                
            }
        });
        AlarmFragment alfrag = new AlarmFragment();
        ReminderFragment remfrag = new ReminderFragment();
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.frame, AlarmFragment.class, null)
                    .commit();
        }*/
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction()
            .setReorderingAllowed(true).add(R.id.fragContainer,new ReminderFragment()).commit();
        }
        add = findViewById(R.id.imageView);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addnew = new Intent(MainActivity.this,AddNew.class);
                /*ReminderFragment remfrag = (ReminderFragment)getSupportFragmentManager().findFragmentByTag("Reminder");
                AlarmFragment alfrag = (AlarmFragment) getSupportFragmentManager().findFragmentByTag("Alarm");
                if(remfrag!=null && remfrag.isVisible())

                if(alfrag!=null && alfrag.isVisible())
                    */
                if(remfrag.isVisible() || savedInstanceState==null)
                    addnew.putExtra("checked",true);
                if(alfrag.isVisible())
                    addnew.putExtra("checked",false);
                startActivity(addnew);
            }
        });
        Bundle verify = getIntent().getExtras();
        if(verify!=null) {
            String status = verify.getString("Status");
            Snackbar.make(bottomNavigationView, status, Snackbar.LENGTH_LONG).show();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.alarm:
                    changeFragment(alfrag);
                    break;
                case R.id.remind:
                    changeFragment(remfrag);
                    break;
            }
            return true;
        });

    }
    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer,fragment).setReorderingAllowed(true);
        fragmentTransaction.commit();
    }
}