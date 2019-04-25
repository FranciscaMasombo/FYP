package ie.fran.fyp.Flashcards;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ie.fran.fyp.R;

public class Deck_Adapter  extends RecyclerView.ViewHolder{
TextView decktitle;
LinearLayout fulldeck;
Button edit, delete;

    public Deck_Adapter(@NonNull View itemView) {
        super(itemView);
        decktitle = (TextView) itemView.findViewById(R.id.title);
        fulldeck = (LinearLayout) itemView.findViewById(R.id.fulldeck);
        edit= (Button)  itemView.findViewById(R.id.btnEditCard);
        delete= (Button)  itemView.findViewById(R.id.btnDeleteCard);

    }

    public void setDeckTitle(String title) {
        decktitle.setText(title);
    }
}
