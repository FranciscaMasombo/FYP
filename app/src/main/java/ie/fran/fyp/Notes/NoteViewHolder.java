package ie.fran.fyp.Notes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ie.fran.fyp.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    View mView;
    TextView textTitle, textContent;
    CardView noteCard;

    public NoteViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        textContent = mView.findViewById(R.id.content);
        textTitle = mView.findViewById(R.id.note_title);
        noteCard = mView.findViewById(R.id.note_card);

    }

    public void setNoteTitle(String title) {
        textTitle.setText(title);
    }

    public void setNoteContent(String content) {
        textContent.setText(content);
    }

}
