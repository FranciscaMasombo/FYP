package ie.fran.fyp.Focus.services;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.scottyab.rootbeer.RootBeer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import ie.fran.fyp.Focus.Apps_Database.ApplicationDetails;
import ie.fran.fyp.Focus.Apps_Database.ApplicationLoader;


public class RedirectService extends Service {

    public static final long NOTIFY_INTERVAL = 1000;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String strDate;
    RootBeer rootbeer;
    WifiManager wifiManager;
    Intent intent;
    Intent redirect;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    private List<ApplicationDetails> applicationNameList;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "StudyAID has Started", Toast.LENGTH_LONG).show();
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        redirect = new Intent(this, Redirect.class);
        redirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        rootbeer = new RootBeer(this);
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 5, NOTIFY_INTERVAL);


    }

    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            restartServiceIntent.setPackage(getPackageName());
        }
        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        assert alarmService != null;
        alarmService.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);
        super.onTaskRemoved(rootIntent);
        Log.e("Service Auto Restart", "ON");
    }

    private class TimeDisplayTimerTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    calendar = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    strDate = simpleDateFormat.format(calendar.getTime());
                    Log.e("strDate", strDate);
                    AppsInstalled();
                }
            });
        }
    }

    private void AppsInstalled() {
        applicationNameList = ApplicationLoader.load(this).getApplicationDetails();
        for (ApplicationDetails app : applicationNameList) {
            if (RunningApp().equalsIgnoreCase(app.getPackageName())) {
                //start the redirect
                startActivity(redirect);
            }
        }
    }

    private String RunningApp() {
        String currentApp = "";
        @SuppressLint("WrongConstant") UsageStatsManager usm = (UsageStatsManager) this.getSystemService("usagestats");
        long time = System.currentTimeMillis();
        assert usm != null;
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 2000 * 1000, time);
        if (appList != null && appList.size() > 0) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
            for (UsageStats usageStats : appList) {
                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
            }
            if (mySortedMap != null && !mySortedMap.isEmpty()) {
                currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
            }
        } else {
            ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            assert am != null;
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }

        Log.e("Foreground App", "Current App in foreground is: " + currentApp);
        return currentApp;
    }

    private void fn_update(String str_time) {
        intent.putExtra("time", str_time);
        sendBroadcast(intent);
    }


}