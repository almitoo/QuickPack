package com.example.quickpack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quickpack.Constants.MyConstants;
import com.example.quickpack.Utils.FirebaseUtils;
import com.example.quickpack.Models.UserCalendar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EventCalender extends AppCompatActivity {
    private CalendarView calendarView;
    private String header;
    private EditText editText;
    private int selectedDay = -1, selectedMonth, selectedYear;
    private UserCalendar userCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calender);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        header=intent.getStringExtra(MyConstants.HEADER_SMALL);
        getSupportActionBar().setTitle(header);


        calendarView = findViewById(R.id.calendarView);
        editText = findViewById(R.id.editText);


        if(!FirebaseUtils.checkIfSignedIn(this)) {
            return;
        }

        //load calender user data from firebase
        loadUserCalendarDate();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                calendarClicked(dayOfMonth, month, year);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtils.checkIfSignedIn(this);
    }

    private void loadUserCalendarDate() {
        FirebaseDatabase.getInstance().getReference()
                .child(FirebaseUtils.USER_CALENDAR)
                .child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userCalendar = snapshot.getValue(UserCalendar.class);

                        if(selectedDay == -1) {
                            editText.setText("");
                        }

                        String event = userCalendar.getEvent(selectedDay, selectedMonth, selectedYear);
                        if(event == null) {
                            editText.setText("");
                        } else {
                            editText.setText(event);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void calendarClicked(int day, int month, int year){
        String event = userCalendar.getEvent(day, month+1, year);

        if(event == null) {
            editText.setText("");
        } else {
            editText.setText(event);
        }

        selectedDay = day;
        selectedMonth = month;
        selectedYear = year;
    }

    public void buttonSaveEvent(View view) {
        if(!FirebaseUtils.checkIfSignedIn(this)) {
            return;
        }

        String event = editText.getText().toString().trim();
        String originalEvent = userCalendar.getEvent(selectedDay, selectedMonth, selectedYear);


        userCalendar.setUserDateEvent(selectedDay, selectedMonth+1, selectedYear, event);

        FirebaseDatabase.getInstance()
                .getReference().child(FirebaseUtils.USER_CALENDAR)
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(userCalendar)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(EventCalender.this,
                                    "Could not update event, please try again",
                                    Toast.LENGTH_SHORT).show();

                            if(originalEvent != null) { // event existed before updated
                                userCalendar.setUserDateEvent(selectedDay, selectedMonth, selectedYear,
                                        originalEvent);
                            } else { // no event before update
                                userCalendar.removeUserDateEvent(selectedDay, selectedMonth, selectedYear);
                            }
                        }


                    }
                });

        Toast.makeText(EventCalender.this,
                "Event are saved",
                Toast.LENGTH_SHORT).show();

    }

    //Open the calender Google App
    public void addEventToGoogleCalender(View view){
        Intent intent=new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI).putExtra(CalendarContract.Events.TITLE,editText.getText().toString().trim());
        try {
            startActivity(intent);
        } catch ( ActivityNotFoundException exception) {//If there are no apps on the device that can receive an implicit intent
            Toast.makeText(EventCalender.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}