package ie.fran.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import ie.fran.fyp.Notes.Note_Activity;
import ie.fran.fyp.Users.SignUp;
import ie.fran.fyp.Users.login;

public class Start extends AppCompatActivity {

    private Button btnReg, btnLog;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        btnReg = findViewById(R.id.start_reg_btn);
        btnLog = findViewById(R.id.start_log_btn);

        fAuth = FirebaseAuth.getInstance();

        updateUI();

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void register() {
        Intent regIntent = new Intent(Start.this, SignUp.class);
        startActivity(regIntent);
    }

    private void login() {
        Intent logIntent = new Intent(Start.this, login.class);
        startActivity(logIntent);
    }

    private void updateUI() {
        if (fAuth.getCurrentUser() != null) {
            Log.i("Start", "fAuth != null");
            Intent startIntent = new Intent(Start.this, Note_Activity.class);
            startActivity(startIntent);
            finish();
        } else {

            Log.i("Start", "fAuth == null");
        }
    }

}