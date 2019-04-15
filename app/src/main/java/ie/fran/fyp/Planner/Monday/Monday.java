package ie.fran.fyp.Planner.Monday;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ie.fran.fyp.R;

public class Monday extends AppCompatActivity {
    TextView titlepage,ttittle, ddec, lloc, ttime, ddate;

    DatabaseReference reference;
    RecyclerView ourdoes;
    ArrayList<Monday_Model> list;
    Monday_Adapter monday_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday);

        titlepage = findViewById(R.id.titlepage);

        ourdoes = findViewById(R.id.mondayList);
        ourdoes.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Monday_Model>();

        // get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Week");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // set code to retrive data and replace layout
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Monday_Model p = dataSnapshot1.getValue(Monday_Model.class);
                    list.add(p);
                }
                monday_adapter = new Monday_Adapter(Monday.this, list);

                ourdoes.setAdapter(monday_adapter);
                monday_adapter.notifyDataSetChanged();
                Log.e("myTag", "data");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // set code to show an error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();

            }
        });

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
            case R.id.main_new_task_btn:
                Intent newIntent = new Intent(Monday.this, AddTask.class);
                startActivity(newIntent);
                break;
        }

        return true;
    }
}
