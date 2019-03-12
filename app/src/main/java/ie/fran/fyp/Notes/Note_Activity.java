package ie.fran.fyp.Notes;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
import ie.fran.fyp.Start;

public class Note_Activity extends AppCompatActivity {


    //tODO grid view layourt  rose and co
    //todo progress bar
    FirebaseAuth fAuth;
    RecyclerView mNotesList;
    GridLayoutManager gridLayoutManager;

    DatabaseReference fNotesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_);
        mNotesList = findViewById(R.id.notes_list);

        gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        mNotesList.setHasFixedSize(true);
        mNotesList.setLayoutManager(gridLayoutManager);
        //gridLayoutManager.setReverseLayout(true);
        //gridLayoutManager.setStackFromEnd(true);
        mNotesList.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            fNotesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(fAuth.getCurrentUser().getUid());
        }

        updateUI();

        loadData();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void loadData() {
        Query query = fNotesDatabase.orderByValue();
        FirebaseRecyclerOptions<NoteModel> options = new FirebaseRecyclerOptions.Builder<NoteModel>()
                .setQuery(query, NoteModel.class)
                .build();
        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NoteModel, NoteViewHolder>
                (options) {
            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_note_layout, viewGroup, false);
                return new NoteViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final NoteViewHolder viewHolder, int position, @NonNull NoteModel model) {
                final String noteId = getRef(position).getKey();
                fNotesDatabase.child(noteId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("title")) {
                            String title = dataSnapshot.child("title").getValue().toString();
                            String content = dataSnapshot.child("content").getValue().toString();
                            //String timestamp = dataSnapshot.child("timestamp").getValue().toString();

                            viewHolder.setNoteTitle(title);
                            viewHolder.setNoteContent(content);
                            //viewHolder.setNoteTime(timestamp);

                            // GetTimeAgo getTimeAgo = new GetTimeAgo();
                            // viewHolder.setNoteTime(getTimeAgo.getTimeAgo(Long.parseLong(timestamp), getApplicationContext()));

                            viewHolder.noteCard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Note_Activity.this, NewNote.class);
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
        mNotesList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private void updateUI() {

        if (fAuth.getCurrentUser() != null) {
            Log.i("MainActivity", "fAuth != null");
        } else {
            Intent startIntent = new Intent(Note_Activity.this, Start.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity", "fAuth == null");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.add_note_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.main_new_note_btn:
                Intent newIntent = new Intent(Note_Activity.this, NewNote.class);
                startActivity(newIntent);
                break;
        }

        return true;
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}