package ie.fran.fyp.Focus.Apps_Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

//https://stackoverflow.com/questions/51342864/android-check-if-the-room-database-is-populated-on-startup-without-livedata
//https://developer.android.com/training/data-storage/room/
@Database(entities = {ApplicationDetails.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase db;
    public abstract AppDao NameDao();

    public static AppDatabase getAppDatabase(final Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,"appdb")
                    .allowMainThreadQueries()
                    .build();
        }
        return db;
    }
}