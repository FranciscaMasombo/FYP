package ie.fran.fyp.Flashcards.CreateCards;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import ie.fran.fyp.Flashcards.Flashcard_Activity;
import ie.fran.fyp.R;

public class AddCardDialog extends AppCompatDialogFragment {
    AddCardDialogListener listener;
    private FirebaseAuth fAuth;
    private String i;
    private boolean isExist;
    private Bundle bd;
    DatabaseReference reference;
    EditText etCardFront,etCardBack;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        try {
            isExist = !i.trim().equals("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_flashcard, null);
        fAuth = FirebaseAuth.getInstance();
        etCardFront = (EditText) view.findViewById(R.id.etCardFront);
        etCardBack = (EditText) view.findViewById(R.id.etCardBack);

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Flashcards")
                .child("deck")
                .child(fAuth.getCurrentUser().getUid())

        ;
        builder.setView(view)
                .setTitle("Add Card")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("Add Card", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String front = etCardFront.getText().toString().trim();
                String back = etCardBack.getText().toString().trim();

                if (!TextUtils.isEmpty(front) && !TextUtils.isEmpty(back) ) {
                    createCard(front,back);
                } else {
                    etCardFront.setError("Required");
                    etCardBack.setError("Required");

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
    private void putData() {
        if (isExist) {
            reference.child(i).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("front")
                            && dataSnapshot.hasChild("back")) {
                        String front = dataSnapshot.child("front").getValue().toString();
                        String back = dataSnapshot.child("back").getValue().toString();

                        etCardFront.setText(front);
                        etCardBack.setText(back);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void createCard(String front, String back) {
        String key;
        String message;

        if(bd != null){
            key = (String) bd.get(Flashcard_Activity.KEY_KEY);
            message = "edit";
        }
        else {
            key = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Flashcards")
                    .child("deck")
                    .child(fAuth.getCurrentUser().getUid())
                    .push().getKey();
            message = "null";
        }
        final Map noteMap = new HashMap();

        noteMap.put("front", front);
        noteMap.put("back", back);
        FirebaseDatabase.getInstance()
                .getReference()
                .child("Flashcards")
                .child("deck")
                .child(fAuth.getCurrentUser().getUid())
                .child(key)
                .setValue(noteMap);

//        if (fAuth.getCurrentUser() != null) {
//
//            final DatabaseReference newDeckRef = reference.push();
//           // final Map noteMap = new HashMap();
//
//            noteMap.put("front", front);
//            noteMap.put("back", back);
//
//            Thread mainThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    newDeckRef.setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                            } else {
//                            }
//                        }
//                    });
//
//                }
//            });
//            mainThread.start();
//        }else {
//
//        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddCardDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface AddCardDialogListener {
    }
}
