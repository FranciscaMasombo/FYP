package ie.fran.fyp.Flashcards;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ie.fran.fyp.Flashcards.CreateCards.AddCardDialog;
import ie.fran.fyp.R;

public class CreateFlashcards extends AppCompatActivity implements AddCardDialog.AddCardDialogListener{
    Button add;
    private String i;
    private boolean isExist;
    DatabaseReference reference;
    RecyclerView recyclerView;
    FirebaseAuth fAuth;
    EditText etTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcards);
        try {
            i = getIntent().getStringExtra("i");
            isExist = !i.trim().equals("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        etTitle = (EditText) findViewById(R.id.etTitle);

       // etTitle.setText(etitle.getTitle());

        add = (Button) findViewById(R.id.btnAddCard);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddCardDialog();
            }
        });

        recyclerView = findViewById(R.id.recyclerAddedCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            reference = FirebaseDatabase
                    .getInstance().getReference()
                    .child("Flashcards")
                    .child(fAuth.getCurrentUser().getUid())
                    .child("cards");
        }
        putData();
        updateUI();
        loadData();
    }

    private void putData() {
        if (isExist) {
            DatabaseReference  referenceX = FirebaseDatabase.getInstance().getReference()
                    .child("Flashcards")
                    .child(fAuth.getCurrentUser().getUid());
            referenceX.child(i).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("title")) {
                        String title = dataSnapshot.child("title").getValue().toString();

                        etTitle.setText(title);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void updateUI() {
    }

    private void loadData() {
        Query query = reference.orderByValue();
        FirebaseRecyclerOptions<Card_Model> options
                = new FirebaseRecyclerOptions.Builder<Card_Model>()
                .setQuery(query, Card_Model.class)
                .build();
        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Card_Model,Card_Adapter>
                (options){

            @NonNull
            @Override
            public Card_Adapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.card_card, viewGroup, false);
                return new Card_Adapter(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final Card_Adapter holder, int position, @NonNull Card_Model model) {
                final String i = getRef(position).getKey();
                reference.child(i).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("front")&&
                                dataSnapshot.hasChild("back")){
                            String front = dataSnapshot.child("front").getValue().toString();
                            String back = dataSnapshot.child("back").getValue().toString();
                            holder.setFront(front);
                            holder.setBack(back);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();

    }
    private void openAddCardDialog() {
        AddCardDialog addCardDialog = new AddCardDialog();
        addCardDialog.show(getSupportFragmentManager(), "example dialog");
    }

}
