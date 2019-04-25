package ie.fran.fyp.Flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ie.fran.fyp.R;
import ie.fran.fyp.Settings.LoginActivity;

public class Flashcard_Activity extends AppCompatActivity implements AddDeckDialog.ExampleDialogListener {
    Button adddeck;
    private String i;
    private boolean isExist;
    DatabaseReference reference;
    RecyclerView recyclerView;
    FirebaseAuth fAuth;

    public static final String KEY_KEY = "KEY_KEY";
    public static final String KEY_DECK = "KEY_DECK";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_);

        try {
            i = getIntent().getStringExtra("i");
            isExist = !i.trim().equals("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        adddeck = (Button) findViewById(R.id.adddeck);
        adddeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        recyclerView = findViewById(R.id.decks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            reference = FirebaseDatabase
                    .getInstance().getReference()
                    .child("Flashcards")
                    .child(fAuth.getCurrentUser().getUid());
        }
        updateUI();
        loadData();
    }
    @Override
    public void onStart() {
        super.onStart();

    }
    private void loadData() {
        Query query = reference.orderByValue();
        FirebaseRecyclerOptions<Deck_Model> options
                = new FirebaseRecyclerOptions.Builder<Deck_Model>()
                .setQuery(query, Deck_Model.class)
                .build();
        FirebaseRecyclerAdapter firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Deck_Model,Deck_Adapter>(options){

            @NonNull
            @Override
            public Deck_Adapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.deck_card, viewGroup, false);
                return new Deck_Adapter(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final Deck_Adapter holder, int position, @NonNull Deck_Model model) {
                final String i = getRef(position).getKey();
                reference.child(i).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("title")){
                            String title = dataSnapshot.child("title").getValue().toString();

                            holder.setDeckTitle(title);

                            holder.fulldeck.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Flashcard_Activity.this, Cards_Display.class);
                                   // intent.putExtra("i", i);
                                    startActivity(intent);
                                }
                            });

                            holder.edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Flashcard_Activity.this, CreateFlashcards.class);
                                    // intent.putExtra("i", i);
                                    startActivity(intent);
                                }
                            });



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }
    private void updateUI() {

        if (fAuth.getCurrentUser() != null) {
            Log.i("MainActivity", "fAuth != null");
        } else {
            Intent startIntent = new Intent(Flashcard_Activity.this, LoginActivity.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity", "fAuth == null");
        }
    }
    private void openDialog() {
        AddDeckDialog addDeckDialog = new AddDeckDialog();
        addDeckDialog.show(getSupportFragmentManager(), "example dialog");
    }
    public void applyTexts(String username, String password) {

    }
}

