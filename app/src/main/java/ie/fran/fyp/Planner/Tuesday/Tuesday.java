package ie.fran.fyp.Planner.Tuesday;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import ie.fran.fyp.Planner.Monday.AddTask;
import ie.fran.fyp.Planner.Monday.MondayViewHolder;
import ie.fran.fyp.Planner.Monday.Monday_Model;
import ie.fran.fyp.R;
import ie.fran.fyp.Start;

public class Tuesday extends AppCompatActivity {
    TextView titlepage,ttittle, ddec, lloc, ttime, ddate;

    FirebaseAuth fAuth;
    DatabaseReference reference;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuesday);

        titlepage = findViewById(R.id.titlepage);

        recyclerView = findViewById(R.id.mondayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // get data from firebase

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            reference = FirebaseDatabase
                    .getInstance().getReference()
                    .child("Tuesday").child(fAuth.getCurrentUser().getUid());
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
        FirebaseRecyclerOptions<Tuesday_Model> options
                = new FirebaseRecyclerOptions.Builder<Tuesday_Model>()
                .setQuery(query, Tuesday_Model.class)
                .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter< Tuesday_Model, TuesdayViewHolder>(options) {


            @NonNull
            @Override
            public TuesdayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_planner_task, viewGroup, false);
                return new TuesdayViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final TuesdayViewHolder viewHolder, int position, @NonNull Tuesday_Model model) {
                final String noteId = getRef(position).getKey();
                reference.child(noteId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("title")) {
                            String titlemon = dataSnapshot.child("title").getValue().toString();
                            String desc = dataSnapshot.child("desc").getValue().toString();
                            String loc = dataSnapshot.child("loc").getValue().toString();
                            String date = dataSnapshot.child("date").getValue().toString();
                            String time = dataSnapshot.child("time").getValue().toString();

                            viewHolder.settitlemon(titlemon);
                            viewHolder.setdesc(desc);
                            viewHolder.setloc(loc);
                            viewHolder.setdate(date);
                            viewHolder.settime(time);

                            viewHolder.weekCard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Tuesday.this, AddTask.class);
                                    intent.putExtra("noteId", noteId);
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
            Intent startIntent = new Intent(Tuesday.this, Start.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity", "fAuth == null");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.add_task_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.add_new_folder:
                Intent newIntent = new Intent(Tuesday.this, AddTaskTuesday.class);
                startActivity(newIntent);
                break;
        }

        return true;
    }
}
