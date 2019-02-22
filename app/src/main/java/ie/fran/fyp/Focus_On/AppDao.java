package ie.fran.fyp.Focus_On;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import ie.fran.fyp.Focus_On.ApplicationDetails;

@Dao
public interface AppDao {

  @Insert
  void insert(ApplicationDetails applicationName);

    @Delete
    void delete(ApplicationDetails applicationName);


    @Query("Select * from ApplicationDetails")
    ApplicationDetails[] getAll();
}
