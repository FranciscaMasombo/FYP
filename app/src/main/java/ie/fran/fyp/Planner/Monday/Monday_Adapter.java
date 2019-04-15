package ie.fran.fyp.Planner.Monday;

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

public class Monday_Adapter extends RecyclerView.Adapter<Monday_Adapter.MyViewHolder>{

    Context context;
    ArrayList<Monday_Model> monday_models;

    public Monday_Adapter(Context c, ArrayList<Monday_Model> p) {
        context = c;
        monday_models = p;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_planner_task,viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(monday_models.get(i).getTitlemon());
        myViewHolder.desc.setText(monday_models.get(i).getDesc());
        myViewHolder.loc.setText(monday_models.get(i).getLoc());
        myViewHolder.date.setText(monday_models.get(i).getDate());
        myViewHolder.time.setText(monday_models.get(i).getTime());

        final String getTitle = monday_models.get(i).getTitlemon();
        final String getDesc = monday_models.get(i).getDesc();
        final String getloc = monday_models.get(i).getLoc();
        final String getDate = monday_models.get(i).getDate();
        final String getTime = monday_models.get(i).getTime();
        final String getKeyDoes = monday_models.get(i).getKeydoes();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aa = new Intent(context, View_or_Edit.class);
                aa.putExtra("titlemon", getTitle);
                aa.putExtra("desc", getDesc);
                aa.putExtra("loc", getloc);
                aa.putExtra("date", getDate);
                aa.putExtra("time", getTime);
                aa.putExtra("keydoes", getKeyDoes);
                context.startActivity(aa);
            }
        });

    }

    @Override
    public int getItemCount() {
        return monday_models.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, date, loc, time, keydoes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.task_t);
            desc = (TextView) itemView.findViewById(R.id.decrip);
            loc = (TextView) itemView.findViewById(R.id.location);
            date = (TextView) itemView.findViewById(R.id.timeplanner);
            time = (TextView) itemView.findViewById(R.id.dateplanner);

        }
    }
}
