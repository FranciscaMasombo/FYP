package ie.fran.fyp.Focus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ie.fran.fyp.Focus.Apps.AppsFragment;
import ie.fran.fyp.Focus.Timer.TimerFragment;
import ie.fran.fyp.R;

public class Focus_On_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus__on_);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new AppsFragment()).commit();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.nav_apps:
                        selectedFragment = new AppsFragment();
                        break;
                    case R.id.nav_timer:
                        selectedFragment = new TimerFragment();
                        break;
                    case R.id.nav_profile:
                        new Focus_On_Activity();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        });
    }

}
