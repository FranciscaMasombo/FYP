package ie.fran.fyp.ToDo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ie.fran.fyp.R;

public class ToDoViewHolder extends RecyclerView.ViewHolder{
    TextView titledoes, descdoes, datedoes;
    View mView;
    LinearLayout item;

    public ToDoViewHolder(@NonNull View itemView) {
        super(itemView);
        titledoes = (TextView) itemView.findViewById(R.id.titledoes);
        descdoes = (TextView) itemView.findViewById(R.id.descdoes);
        datedoes = (TextView) itemView.findViewById(R.id.datedoes);
        item = (LinearLayout) itemView.findViewById(R.id.item);
    }


    public void setNoteTitle(String title) {
        titledoes.setText(title);
    }

    public void setNoteDec(String dec) {
        descdoes.setText(dec);
    }

    public void setNoteDate(String date) {
        datedoes.setText(date);
    }
}
