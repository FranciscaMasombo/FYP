package ie.fran.fyp.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import ie.fran.fyp.domain.ApplicationDetails ;

@Dao
public interface ApplicationNameDao {

    @Insert
    void insert(ApplicationDetails... applicationName);

    @Delete
    void delete(ApplicationDetails... applicationName);


    @Query("Select * from ApplicationDetails")
    ApplicationDetails[] getAll();
}
