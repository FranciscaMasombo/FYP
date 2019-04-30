package ie.fran.fyp.Scanner;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import ie.fran.fyp.R;

public class Scanner_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Scan_New_doc()).commit();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.nav_add:
                        selectedFragment = new Scan_New_doc();
                        break;
                    case R.id.nav_view:
                        selectedFragment = new ViewAll_docs();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        });
    }
    }
