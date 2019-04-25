package ie.fran.fyp.Focus.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ie.fran.fyp.Focus.Apps_Database.ApplicationItem;
import ie.fran.fyp.Focus.Apps_Database.ApplicationListAdapter;
import ie.fran.fyp.Focus.Apps_Database.ApplicationLoader;
import ie.fran.fyp.R;

import static android.view.View.GONE;

public class AppsFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static ApplicationListAdapter adapter;
    public RecyclerView recyclerView;
    public String SETTING_NOTIFICATION_LISTENER = "enabled_notification_listeners";
    public String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    public String PREF_ENABLED = "pref_enabled";
    public String PREF_PACKAGES_BLOCKED = "pref_packages_blocked";
    private SharedPreferences Pref;
    ProgressBar androidProgressBar;
    int progressStatusCounter = 0;
    TextView textView;
    Handler progressHandler = new Handler();
    PackageManager pm;
    private List<ApplicationItem> applicationList = new ArrayList<ApplicationItem>();
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apps, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        searchView = view.findViewById(R.id.search);
        if (searchView != null) {
            searchView.setOnQueryTextListener(this);
        }
        androidProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        //Start progressing
        new Thread(new Runnable() {
            public void run() {
                while (progressStatusCounter < 100) {
                    progressStatusCounter += 2;
                    progressHandler.post(new Runnable() {
                        public void run() {
                            androidProgressBar.setProgress(progressStatusCounter);
//
                        }
                    });
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        loadApplications(view.getContext());
        adapter = new ApplicationListAdapter(view.getContext(), applicationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return view;

    }

    @SuppressLint("StaticFieldLeak")
    public void loadApplications(final Context context) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                ApplicationLoader loader = ApplicationLoader.load(context);
                applicationList.addAll(loader.get());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                //make the progress bar go
                androidProgressBar.setVisibility(GONE);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    //search
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            adapter.setApps(applicationList);

        } else {
            String query = newText.toLowerCase();
            ArrayList<ApplicationItem> filtered = new ArrayList<>();
            for (ApplicationItem app : applicationList) {
                String label = app.getName().toLowerCase();
                if (label.contains(query)) {
                    filtered.add(app);
                }
            }
            adapter.setApps(filtered);
        }
        return true;
    }


}

