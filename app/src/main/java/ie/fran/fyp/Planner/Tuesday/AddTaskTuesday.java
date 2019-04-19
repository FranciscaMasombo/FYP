package ie.fran.fyp.Planner.Tuesday;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ie.fran.fyp.R;

public class AddTaskTuesday extends AppCompatActivity {
    TimePickerDialog timePickerDialog;
    int currentHour;
    int currentMinute;
    String amPm;
    TextView titlepage, addtitle, desct, loct, datet, timet, date, time;
    EditText titlemon, desc, loc;
    Button delete, save;
    DatabaseReference reference;
    Integer doesNum = new Random().nextInt();
    String keydoes = Integer.toString(doesNum);
    Calendar c = Calendar.getInstance();
    DatePickerDialog dpd;

    private String noteID;
    private FirebaseAuth fAuth;

    private boolean isExist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_tuesday);
        try {
            noteID = getIntent().getStringExtra("noteId");
            //Toast.makeText(this, noteID, Toast.LENGTH_SHORT).show();
            isExist = !noteID.trim().equals("");

        } catch (Exception e) {
            e.printStackTrace();
        }
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference()
                .child("Tuesday")
                .child(fAuth.getCurrentUser().getUid());

        titlepage = findViewById(R.id.titlepage);
        addtitle = findViewById(R.id.addtitle);
        desct = findViewById(R.id.desct);
        loct = findViewById(R.id.loct);
        datet = findViewById(R.id.datet);
        timet = findViewById(R.id.timet);

        // inputs
        titlemon = findViewById(R.id.ttitlemon);
        desc = findViewById(R.id.tdesc);
        loc = findViewById(R.id.tloc);
        date = findViewById(R.id.tdate);
        time = findViewById(R.id.ttime);

        delete = findViewById(R.id.delete);
        save = findViewById(R.id.save);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titlemon.getText().toString().trim();
                String descip = desc.getText().toString().trim();
                String loca = loc.getText().toString().trim();
                String datee = date.getText().toString().trim();
                String timee = time.getText().toString().trim();

                if (!TextUtils.isEmpty(title)
                        && !TextUtils.isEmpty(descip)
                        && !TextUtils.isEmpty(loca)
                        && !TextUtils.isEmpty(datee)
                        && !TextUtils.isEmpty(timee)) {
                    createTask(title, descip, loca, datee, timee);
                }else {
                    Snackbar.make(view, "Fill empty fields", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isExist) {
                    reference.child(noteID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddTaskTuesday.this, " Deleted", Toast.LENGTH_SHORT).show();
                                noteID = "no";
                                finish();
                            } else {
                                Log.e("NewNoteActivity", task.getException().toString());
                                Toast.makeText(AddTaskTuesday.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else {
                    Toast.makeText(AddTaskTuesday.this, " Nothing to delete", Toast.LENGTH_SHORT).show();

                }
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentHour = c.get(Calendar.HOUR_OF_DAY);
                currentMinute = c.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(AddTaskTuesday.this, new TimePickerDialog.OnTimeSetListener() {
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

                timePickerDialog = new TimePickerDialog(AddTaskTuesday.this, new TimePickerDialog.OnTimeSetListener() {
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

        putData();

    }

    private void putData() {

        if (isExist) {
            reference.child(noteID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("title")
                            && dataSnapshot.hasChild("desc")
                            && dataSnapshot.hasChild("loc")
                            && dataSnapshot.hasChild("date")
                            && dataSnapshot.hasChild("time")
                    ) {
                        String titleS = dataSnapshot.child("title").getValue().toString();
                        String descS = dataSnapshot.child("desc").getValue().toString();
                        String locS = dataSnapshot.child("loc").getValue().toString();
                        String dateS = dataSnapshot.child("date").getValue().toString();
                        String timeS = dataSnapshot.child("time").getValue().toString();

                        titlemon.setText(titleS);
                        desc.setText(descS);
                        loc.setText(locS);
                        date.setText(dateS);
                        time.setText(timeS);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


    private void createTask(String title, String descip, String loca, String datee, String timee) {
        if (fAuth.getCurrentUser() != null) {
            if (isExist) {
                // UPDATE
                Map updateMap = new HashMap();
                updateMap.put("title", titlemon.getText().toString().trim());
                updateMap.put("desc", desc.getText().toString().trim());
                updateMap.put("loc", loc.getText().toString().trim());
                updateMap.put("date", date.getText().toString().trim());
                updateMap.put("time", time.getText().toString().trim());

                reference.child(noteID).updateChildren(updateMap);
            } else {
                // CREATE A NEW
                final DatabaseReference newTaskref = reference.push();
                final Map noteMap = new HashMap();
                noteMap.put("title", title);
                noteMap.put("desc", descip);
                noteMap.put("loc", loca);
                noteMap.put("date", datee);
                noteMap.put("time", timee);
                Thread mainThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        newTaskref.setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddTaskTuesday.this, "added to database", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AddTaskTuesday.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });
                mainThread.start();
            }

        } else {
            Toast.makeText(this, "USERS IS NOT SIGNED IN", Toast.LENGTH_SHORT).show();
        }
    }
}