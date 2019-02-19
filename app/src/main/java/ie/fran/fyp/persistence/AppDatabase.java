package ie.fran.fyp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ie.fran.fyp.domain.ApplicationDetails ;

@Database(entities = {ApplicationDetails.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;


    public abstract ApplicationNameDao applicationNameDao();


    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "chronex-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}