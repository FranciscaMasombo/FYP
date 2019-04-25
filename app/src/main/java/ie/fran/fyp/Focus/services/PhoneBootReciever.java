package ie.fran.fyp.Focus.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PhoneBootReciever extends BroadcastReceiver {
    /* Recieves events when phone boot is completed */
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent = new Intent(context, RedirectService.class);
        context.startService(myIntent);
        Log.i("Autostart", "started(-)  (-)");


    }
}