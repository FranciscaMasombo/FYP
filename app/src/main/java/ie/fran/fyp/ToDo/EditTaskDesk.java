package ie.fran.fyp.ToDo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ie.fran.fyp.R;


public class EditTaskDesk extends AppCompatActivity {
    EditText  titledoes, descdoes, datedoes;
    Button saveUpdate, delete;
    DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_to_do);

        titledoes = findViewById(R.id.titledoes);
        descdoes = findViewById(R.id.descdoes);
        datedoes = findViewById(R.id.datedoes);

        saveUpdate = findViewById(R.id.btnSaveUpdate);
        delete = findViewById(R.id.btnDelete);

        titledoes.setText(getIntent().getStringExtra("titledoes"));
        descdoes.setText(getIntent().getStringExtra("descdoes"));
        datedoes.setText(getIntent().getStringExtra("datedoes"));

        final String keykey = getIntent().getStringExtra("keydoes");
        dbref = FirebaseDatabase.getInstance().getReference().child("DoesApp")
                .child("Does"+keykey);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent a = new Intent(EditTaskDesk.this,ToDo_Activity.class);
                            startActivity(a);
                        }else{
                            Toast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        saveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titledoes").setValue(titledoes.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(descdoes.getText().toString());
                        dataSnapshot.getRef().child("datedoes").setValue(datedoes.getText().toString());
                        dataSnapshot.getRef().child("keydoes").setValue(keykey);

                        Intent a = new Intent(EditTaskDesk.this, ToDo_Activity.class);
                        startActivity(a);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}