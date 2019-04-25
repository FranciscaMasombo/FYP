package ie.fran.fyp.Flashcards;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ie.fran.fyp.R;

public class AddDeckDialog extends AppCompatDialogFragment {
    private FirebaseAuth fAuth;
    private String i;
    private boolean isExist;
    EditText etitle;
    DatabaseReference reference;
    ExampleDialogListener listener;
    Integer doesNum = new Random().nextInt();
    String keydoes = Integer.toString(doesNum);

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        try {
            isExist = !i.trim().equals("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);
        fAuth = FirebaseAuth.getInstance();
        etitle = view.findViewById(R.id.title);

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Flashcards")
                .child(fAuth.getCurrentUser().getUid());

        builder.setView(view)
                .setTitle("Create New Deck")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String title = etitle.getText().toString().trim();
                if (!TextUtils.isEmpty(title) ) {
                    createDeck(title);
                }

            }
        });
        putData();

        return builder.create();
    }
    @Override
    public void onStart() {
        super.onStart();

    }
    private void createDeck(String title) {
        if (fAuth.getCurrentUser() != null) {
            final DatabaseReference newDeckRef = reference.push();
            final Map noteMap = new HashMap();
            noteMap.put("title", title);
            Thread mainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    newDeckRef.setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                            } else {
                            }
                        }
                    });

                }
            });
            mainThread.start();
        }else {

        }
    }

    private void putData() {
        if (isExist) {
            reference.child(i).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("title")) {
                        String title = dataSnapshot.child("title").getValue().toString();

                        etitle.setText(title);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String titles, String categorys);
    }
}
