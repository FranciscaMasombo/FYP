package ie.fran.fyp.Focus.services;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.scottyab.rootbeer.RootBeer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import ie.fran.fyp.Focus.Apps.ApplicationDetails;
import ie.fran.fyp.Focus.Apps.ApplicationLoader;
import ie.fran.fyp.R;


public class AppBlockerService extends Service {

    public static final long NOTIFY_INTERVAL = 1000;
    public static String str_receiver = "com.nephi.getoffyourphone.receiver";
    public String str_testing;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String strDate;
    Date date_current, date_diff;
    //Root Detector
    RootBeer rootbeer;
    //Con Manager
    WifiManager wifiManager;
    //DH Helper
    Intent intent;
    Intent lockIntent;
    Intent Shame;
    //Shame Int Counter
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
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
          //redirect
        lockIntent = new Intent(this, Redirect.class);
        lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //Root Detector
        rootbeer = new RootBeer(this);
        //Wifi Manager
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 5, NOTIFY_INTERVAL);
        intent = new Intent(str_receiver);

        //Root Detector
        rootbeer = new RootBeer(this);
        //Wifi Manager
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

        //DB
    }

    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            restartServiceIntent.setPackage(getPackageName());
        }

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        assert alarmService != null;
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);
        Log.e("Service_Auto_Restart", "ON");
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
                    //twoDatesBetweenTime();
                    LockApps();
                    /*if (db.get_once(1) == 1) {
                        db.set_once(0);
                        try {
                            lock_State();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }*/
                }

            });
        }
    }

    private void LockApps() {
        applicationNameList = ApplicationLoader.load(this).getApplicationDetails();
        for (ApplicationDetails app : applicationNameList) {
            if (printForegroundTask().equalsIgnoreCase(app.getPackageName())) {
                startActivity(lockIntent);
            }
        }
    }

    private String printForegroundTask() {
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

    public void notification_update() {
//        Intent intent = new Intent(this, Main.class);
//        // use System.currentTimeMillis() to have a unique ID for the pending intent
//        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = new Random().nextInt(); // just use a counter in some util class...

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("notification_1", "Timer_Notification", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 500,});
            notificationChannel.enableVibration(true);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notification_1");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT) //HIGH, MAX, FULL_SCREEN and setDefaults(Notification.DEFAULT_ALL) will make it a Heads Up Display Style
                //.setDefaults(Notification.) // also requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher) // Required!
                .setContentTitle("TITLE")
                .setContentText("THIS IS MESSSAGEEE")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("THIS IS MESSAGEEE"))
                .setVibrate(new long[]{0, 500});
        //.setAutoCancel(true);
        //.setOngoing(true)
        //.addAction(R.drawable.ic_clear_black_48dp, "Dismiss", dismissIntent);
        //.addAction(R.drawable.ic_action_boom, "Action!", someOtherPendingIntent);

        // Builds the notification and issues it.
        assert notificationManager != null;
        notificationManager.notify(313, builder.build());
    }

}