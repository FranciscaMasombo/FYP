package ie.fran.fyp.ToDo;

import android.graphics.Typeface;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import ie.fran.fyp.R;

public class NewTaskAct extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private String i;
    private boolean isExist;
    TextView titlepage, addtitle, adddesc, adddate;
    EditText etitledoes, edescEdoes, edateEdoes;
    Button btnSaveTask, remove;
    DatabaseReference reference;
    Integer doesNum = new Random().nextInt();
    String keydoes = Integer.toString(doesNum);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        try {
            i = getIntent().getStringExtra("i");
            isExist = !i.trim().equals("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titlepage = findViewById(R.id.titlepage);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);

        etitledoes = findViewById(R.id.titledoes);
        edescEdoes = findViewById(R.id.descdoes);
        edateEdoes = findViewById(R.id.datedoes);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference()
                .child("DoesApp")
                .child(fAuth.getCurrentUser().getUid());
//        // insert data to database
//        reference = FirebaseDatabase.getInstance().getReference()
//                .child("DoesApp").
//                child("Does" + doesNum);
        btnSaveTask = findViewById(R.id.btnSaveTask);
        remove = findViewById(R.id.btnDelete);
        // btnCancel = findViewById(R.id.btnCancel);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String title = etitledoes.getText().toString().trim();
                String descdoes = edescEdoes.getText().toString().trim();
                String datedoes = edateEdoes.getText().toString().trim();
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(descdoes) && !TextUtils.isEmpty(datedoes)) {
//
                    createNote(title, descdoes, datedoes);
                } else {
                    Snackbar.make(v, "Fill empty fields", Snackbar.LENGTH_SHORT).show();

                }

            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(i).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(NewTaskAct.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                            i = "no";
                            finish();
                        } else {
                            Log.e("NewNoteActivity", task.getException().toString());
                            Toast.makeText(NewTaskAct.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        // import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        // customize font
        titlepage.setTypeface(MMedium);

        addtitle.setTypeface(MLight);
        etitledoes.setTypeface(MMedium);

        adddesc.setTypeface(MLight);
        edescEdoes.setTypeface(MMedium);

        adddate.setTypeface(MLight);
        edateEdoes.setTypeface(MMedium);

        btnSaveTask.setTypeface(MMedium);
        //  btnCancel.setTypeface(MLight);
        putData();
    }

    private void putData() {
        if (isExist) {
            reference.child(i).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("title")
                            && dataSnapshot.hasChild("descdoes")
                            && dataSnapshot.hasChild("datedoes")) {
                        String title = dataSnapshot.child("title").getValue().toString();
                        String desc = dataSnapshot.child("descdoes").getValue().toString();
                        String date = dataSnapshot.child("datedoes").getValue().toString();

                        etitledoes.setText(title);
                        edescEdoes.setText(desc);
                        edateEdoes.setText(date);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void createNote(String title, String descdoes, String datedoes) {

        if (fAuth.getCurrentUser() != null) {

            if (isExist) {
                // UPDATE A NOTE
                Map updateMap = new HashMap();
                updateMap.put("title", etitledoes.getText().toString().trim());
                updateMap.put("descdoes", edescEdoes.getText().toString().trim());
                updateMap.put("datedoes", edateEdoes.getText().toString().trim());

                //updateMap.put("timestamp", ServerValue.TIMESTAMP);

                reference.child(i).updateChildren(updateMap);

                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
            } else {
                // CREATE A NEW NOTE
                final DatabaseReference newNoteRef = reference.push();

                final Map noteMap = new HashMap();
                noteMap.put("title", title);
                noteMap.put("descdoes", descdoes);
                noteMap.put("datedoes", datedoes);
                //noteMap.put("timestamp", ServerValue.TIMESTAMP);

                Thread mainThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        newNoteRef.setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(NewTaskAct.this, "Note added to database", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(NewTaskAct.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
