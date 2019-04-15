package ie.fran.fyp.Flashcards;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ie.fran.fyp.R;

public class Deck_Adapter extends RecyclerView.Adapter<Deck_Adapter.MyHolder> {
    Context context;
    ArrayList<Deck_Model> deck_models;

    public Deck_Adapter(Context c, ArrayList<Deck_Model> p) {
        context = c;
        deck_models = p;

    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.deck_card, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
        myHolder.decktitle.setText(deck_models.get(i).getDecktitle());
        final String getTitle = deck_models.get(i).getDecktitle();
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aa = new Intent(context, Cards_Display.class);
                aa.putExtra("title", getTitle);
                context.startActivity(aa);
            }
        });

    }

    @Override
    public int getItemCount() {
        return deck_models.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView decktitle, category;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            decktitle = (TextView) itemView.findViewById(R.id.decktitle);

        }
    }
}
