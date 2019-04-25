package ie.fran.fyp.Planner.Monday;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

import ie.fran.fyp.R;

public class View_or_Edit extends AppCompatActivity {
    TimePickerDialog timePickerDialog;

    int currentHour;
    int currentMinute;
    String amPm;
    TextView titlepage, addtitle, desct, loct, datet, timet,  date,time ;
    EditText titlemon, desc, loc;
    Button update, delete;
    DatabaseReference reference;
    Integer doesNum = new Random().nextInt();
    String keydoes = Integer.toString(doesNum);
    Calendar c = Calendar.getInstance();
    DatePickerDialog dpd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_or__edit);
        titlepage = findViewById(R.id.titlepage);

        titlepage = findViewById(R.id.titlepage);
        addtitle = findViewById(R.id.addtitle);
        desct = findViewById(R.id.desct);
        loct = findViewById(R.id.loct);
        datet = findViewById(R.id.datet);
        timet = findViewById(R.id.timet);

        
        titlemon = findViewById(R.id.titlemon);
        desc = findViewById(R.id.desc);
        loc = findViewById(R.id.loc);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);

        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);


        titlemon.setText(getIntent().getStringExtra("titlemon"));
        desc.setText(getIntent().getStringExtra("desc"));
        loc.setText(getIntent().getStringExtra("loc"));
        date.setText(getIntent().getStringExtra("date"));
        time.setText(getIntent().getStringExtra("time"));

        final String keykey = getIntent().getStringExtra("keydoes");
        reference = FirebaseDatabase.getInstance().getReference().child("Week")
                .child("monday"+keykey);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent a = new Intent(View_or_Edit.this, Monday.class);
                            startActivity(a);
                        }else{
                            Toast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titlemon").setValue(titlemon.getText().toString());
                        dataSnapshot.getRef().child("desc").setValue(desc.getText().toString());
                        dataSnapshot.getRef().child("loc").setValue(loc.getText().toString());
                        dataSnapshot.getRef().child("date").setValue(date.getText().toString());
                        dataSnapshot.getRef().child("time").setValue(time.getText().toString());
                        dataSnapshot.getRef().child("keydoes").setValue(keykey);

                        Intent a = new Intent(View_or_Edit.this, Monday.class);
                        startActivity(a);

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

                timePickerDialog = new TimePickerDialog(View_or_Edit.this, new TimePickerDialog.OnTimeSetListener() {
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

                timePickerDialog = new TimePickerDialog(View_or_Edit.this, new TimePickerDialog.OnTimeSetListener() {
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
