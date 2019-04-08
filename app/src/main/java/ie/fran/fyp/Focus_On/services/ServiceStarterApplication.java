package ie.fran.fyp.Focus_On.services;

import android.app.Application;
import android.content.Intent;
public class ServiceStarterApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, AppBlockerService.class));
    }
}
