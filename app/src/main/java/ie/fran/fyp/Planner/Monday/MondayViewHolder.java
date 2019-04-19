package ie.fran.fyp.Planner.Monday;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ie.fran.fyp.R;

public class MondayViewHolder extends RecyclerView.ViewHolder {

    public CardView weekCard;
    TextView text_titlemon;
    TextView text_desc;
    TextView text_loc;
    TextView text_date;
    TextView text_time;
    public MondayViewHolder(View itemView) {
        super(itemView);

        text_titlemon = (TextView) itemView.findViewById(R.id.task_t);
        text_desc = (TextView) itemView.findViewById(R.id.decrip);
        text_loc = (TextView) itemView.findViewById(R.id.location);
        text_date = (TextView) itemView.findViewById(R.id.timeplanner);
        text_time = (TextView) itemView.findViewById(R.id.dateplanner);
        weekCard = (CardView) itemView.findViewById(R.id.task_cardview);

    }

    public void settitlemon(String titlemon) {
        text_titlemon.setText(titlemon);
    }

    public void setdesc(String desc) {
        text_desc.setText(desc);

    }

    public void setloc(String loc) {
        text_loc.setText(loc);
    }

    public void setdate(String date) {
        text_date.setText(date);
    }

    public void settime(String time) {
        text_time.setText(time);
    }
}
