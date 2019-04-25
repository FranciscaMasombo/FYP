package ie.fran.fyp.Flashcards;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ie.fran.fyp.R;

class Card_Adapter extends RecyclerView.ViewHolder {
    TextView vfront,vback;

    public Card_Adapter(@NonNull View itemView) {
        super(itemView);
        vfront = (TextView) itemView.findViewById(R.id.tvCardFront);
        vback = (TextView) itemView.findViewById(R.id.tvCardBack);
    }

    public void setBack(String back) {

    }

    public void setFront(String front) {
    }
}
