package ie.fran.fyp.Flashcards;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ie.fran.fyp.R;

public class Flashcard_Activity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {
    Button adddeck;
    TextView Myname;
    EditText title;
    EditText category;
    AlertDialog.Builder ThisDialog;
    DatabaseReference reference;
    RecyclerView decks;
    ArrayList<Deck_Model> list;
    Deck_Adapter deck_adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_);

        adddeck = (Button) findViewById(R.id.adddeck);
        adddeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        decks = findViewById(R.id.decks);
        decks.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Deck_Model>();

        // get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Flashcards");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Deck_Model p = new Deck_Model();
                    p.setDecktitle(dataSnapshot1.getValue(String.class));
                    list.add(p);
                }
                deck_adapter = new Deck_Adapter(Flashcard_Activity.this, list);
                decks.setAdapter(deck_adapter);
                deck_adapter.notifyDataSetChanged();
                Log.e("myTag", "data");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
                throw databaseError.toException();
            }
        });

    }

    private void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void applyTexts(String username, String password) {

    }
}

