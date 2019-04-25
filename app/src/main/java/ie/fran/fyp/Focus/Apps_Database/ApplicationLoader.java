package ie.fran.fyp.Focus.Apps_Database;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ApplicationLoader {

    List<ApplicationItem> applicationItemList;
    Context context;
    public static ApplicationLoader loader;

    private ApplicationLoader(Context context) {
        applicationItemList = new ArrayList<>();
        this.context = context;
    }

    public static ApplicationLoader load(Context context){
        if(loader == null){
            loader = new ApplicationLoader(context);
        }
        return loader;
    }

    public List<ApplicationItem> get() {
        AppDatabase database = AppDatabase.getAppDatabase(context);
        List<ApplicationDetails> list = Arrays.asList(database.NameDao().getAll());
        if(!applicationItemList.isEmpty())
            return applicationItemList;
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for(ApplicationInfo info : packages){
            if(packageManager.getLaunchIntentForPackage(info.packageName) != null){
                String appName = packageManager.getApplicationLabel(info).toString();
                String appPackage = info.packageName;
                Drawable icon = packageManager.getApplicationIcon(info);
                applicationItemList.add(new ApplicationItem(appName,
                        appPackage, list.contains(new ApplicationDetails(appName)), icon));
            }
        }

        return applicationItemList;
    }

    public List<ApplicationDetails> getApplicationDetails() {
        AppDatabase database = AppDatabase.getAppDatabase(context);
        List<ApplicationDetails> list = Arrays.asList(database.NameDao().getAll());
        return list;
    }
}
