package ie.fran.fyp.ToDo;

import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.TextView;

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

public class ToDo_Activity extends AppCompatActivity {

    TextView titlepage, subtitlepage;
    Button btnAddNew;
    DatabaseReference reference;
    RecyclerView recyclerView;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_);

        // working with data
        recyclerView = findViewById(R.id.ourdoes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            reference = FirebaseDatabase
                    .getInstance().getReference()
                    .child("ToDoApp")
                    .child(fAuth.getCurrentUser().getUid());
        }

        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);
        btnAddNew = findViewById(R.id.btnAddNew);

        // import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        // customize font
        titlepage.setTypeface(MMedium);
        subtitlepage.setTypeface(MLight);


        btnAddNew.setTypeface(MLight);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ToDo_Activity.this,NewTaskAct.class);
                startActivity(a);
            }
        });
        updateUI();

        loadData();

    }
    @Override
    public void onStart() {
        super.onStart();

    }
    private void updateUI() {

        if (fAuth.getCurrentUser() != null) {
            Log.i("MainActivity", "fAuth != null");
        } else {
            Intent startIntent = new Intent(ToDo_Activity.this, LoginActivity.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity", "fAuth == null");
        }
    }

    private void loadData() {
        Query query = reference.orderByValue();
        FirebaseRecyclerOptions<ToDoModel> options
                = new FirebaseRecyclerOptions.Builder<ToDoModel>()
                .setQuery(query, ToDoModel.class)
                .build();
        FirebaseRecyclerAdapter firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<ToDoModel, ToDoViewHolder>
                (options) {

            @NonNull
            @Override
            public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_does, viewGroup, false);
                return new ToDoViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ToDoViewHolder holder, int position, @NonNull ToDoModel model) {
                final String i = getRef(position).getKey();
                reference.child(i).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("title")){
                            String title = dataSnapshot.child("title").getValue().toString();
                            String dec = dataSnapshot.child("descdoes").getValue().toString();
                            String date = dataSnapshot.child("datedoes").getValue().toString();

                            holder.setNoteTitle(title);
                            holder.setNoteDec(dec);
                            holder.setNoteDate(date);
                            holder.item.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(ToDo_Activity.this, NewTaskAct.class);
                                    intent.putExtra("i", i);
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
}
















