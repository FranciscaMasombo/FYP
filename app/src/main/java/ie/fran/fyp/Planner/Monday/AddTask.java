package ie.fran.fyp.Planner.Monday;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Calendar;
import java.util.Random;

import ie.fran.fyp.R;


public class AddTask extends AppCompatActivity {
    TimePickerDialog timePickerDialog;

    int currentHour;
    int currentMinute;
    String amPm;
    TextView titlepage, addtitle, desct, loct, datet, timet,  date,time ;
    EditText titlemon, desc, loc;
    Button select_color, save;
    DatabaseReference reference;
    Integer doesNum = new Random().nextInt();
    String keydoes = Integer.toString(doesNum);
    Calendar c = Calendar.getInstance();
    DatePickerDialog dpd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        titlepage = findViewById(R.id.titlepage);
        addtitle = findViewById(R.id.addtitle);
        desct = findViewById(R.id.desct);
        loct = findViewById(R.id.loct);
        datet = findViewById(R.id.datet);
        timet = findViewById(R.id.timet);

        // inputs
        titlemon = findViewById(R.id.titlemon);
        desc = findViewById(R.id.desc);
        loc = findViewById(R.id.loc);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);

        select_color = findViewById(R.id.select_color);
        save = findViewById(R.id.save);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference().child("Week").
                        child("monday" + doesNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titlemon").setValue(titlemon.getText().toString());
                        dataSnapshot.getRef().child("desc").setValue(desc.getText().toString());
                        dataSnapshot.getRef().child("loc").setValue(loc.getText().toString());
                        dataSnapshot.getRef().child("date").setValue(date.getText().toString());
                        dataSnapshot.getRef().child("time").setValue(time.getText().toString());
                        dataSnapshot.getRef().child("keydoes").setValue(keydoes);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentHour = c.get(Calendar.HOUR_OF_DAY);
                currentMinute = c.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(AddTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        date.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentHour = c.get(Calendar.HOUR_OF_DAY);
                currentMinute = c.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(AddTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        time.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });


    }
}
