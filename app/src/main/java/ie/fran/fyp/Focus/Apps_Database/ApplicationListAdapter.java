package ie.fran.fyp.Focus.Apps_Database;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ie.fran.fyp.R;

public class ApplicationListAdapter extends RecyclerView.Adapter<ApplicationListAdapter.ItemHolder> {

    private Context mContext;
    private List<ApplicationItem> appList;


    public ApplicationListAdapter(Context mContext, List<ApplicationItem> appList) {
        this.mContext = mContext;
        this.appList = appList;
    }



    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {
        final ApplicationItem app = appList.get(position);
        final AppDatabase database = AppDatabase.getAppDatabase(this.mContext);
        holder.appSwitch.setText(app.getName());
        holder.appSwitch.setChecked(app.isSelected());
        holder.appSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchCompat switchCompat = (SwitchCompat) view;
                if(switchCompat.isChecked())
                    database.NameDao().insert(new ApplicationDetails(switchCompat.getText()
                            .toString(), app.getPackageName()));
                else
                    database.NameDao().delete(new ApplicationDetails(switchCompat.getText()
                            .toString()));
                app.setSelected(switchCompat.isChecked());
            }
        });
        holder.appThumbnail.setImageDrawable(app.getIcon());
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public PackageInfo getApp(int position) {
        return appList.get(position);
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        protected SwitchCompat appSwitch;
        protected ImageView appThumbnail;

        public ItemHolder(View view) {
            super(view);
            appSwitch = view.findViewById(R.id.app_switch);
            appThumbnail = view.findViewById(R.id.appThumbnail);
        }
    }
    public void setApps(List<ApplicationItem> appList) {
        this.appList = appList;
        notifyDataSetChanged();
    }
}
