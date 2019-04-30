package ie.fran.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

import ie.fran.fyp.Flashcards.FlashcardsMainActivity;
import ie.fran.fyp.Focus.Focus_On_Activity;
import ie.fran.fyp.Notes.Note_Activity;
import ie.fran.fyp.Planner.Planner_Activity;
import ie.fran.fyp.Scanner.Scanner_Activity;
import ie.fran.fyp.Settings.LoginActivity;
import ie.fran.fyp.Settings.Settings_Activity;
import ie.fran.fyp.ToDo.ToDo_Activity;

public class Dashboard extends AppCompatActivity {
    RelativeLayout focus_on, note, flashcard, planner, todo, scanner;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            Log.i("MainActivity", "fAuth != null");
        } else {
            Intent startIntent = new Intent(Dashboard.this, LoginActivity.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity", "fAuth == null");
        }

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);


        // for the dashbord

        note = findViewById(R.id.note);
        flashcard = findViewById(R.id.flashcard);
        planner = findViewById(R.id.planner);
        todo = findViewById(R.id.todo);
        scanner = findViewById(R.id.scanner);

        focus_on = findViewById(R.id.focus_on);
        focus_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,
                        Focus_On_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Note_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        flashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, FlashcardsMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        planner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Planner_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, ToDo_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Scanner_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(Dashboard.this, Settings_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.focus_on) {
//            Intent intent = new Intent(Dashboard.this, Focus_On_Activity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);
//
//        } else if (id == R.id.note) {
//
//        } else if (id == R.id.flashcard) {
//
//        } else if (id == R.id.planner) {
//
//        } else if (id == R.id.todo) {
//
//        } else if (id == R.id.scanner) {
//
//        } else if (id == R.id.account) {
//
//        } else if (id == R.id.report) {
//
//        }else if (id == R.id.home) {
//            Intent intent = new Intent(Dashboard.this, Dashboard.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);
//        }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

}
