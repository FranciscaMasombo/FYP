package ie.fran.fyp.Focus.Apps;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("unchecked")
public class AppDao_Impl implements AppDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfApplicationDetails;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfApplicationDetails;

  public AppDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfApplicationDetails = new EntityInsertionAdapter<ApplicationDetails>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ApplicationDetails`(`name`,`packageName`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ApplicationDetails value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
        if (value.getPackageName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPackageName());
        }
      }
    };
    this.__deletionAdapterOfApplicationDetails = new EntityDeletionOrUpdateAdapter<ApplicationDetails>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ApplicationDetails` WHERE `name` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ApplicationDetails value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
      }
    };
  }

  @Override
  public void insert(ApplicationDetails applicationName) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfApplicationDetails.insert(applicationName);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(ApplicationDetails applicationName) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfApplicationDetails.handle(applicationName);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public ApplicationDetails[] getAll() {
    final String _sql = "Select * from ApplicationDetails";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfPackageName = _cursor.getColumnIndexOrThrow("packageName");
      final ApplicationDetails[] _result = new ApplicationDetails[_cursor.getCount()];
      int _index = 0;
      while(_cursor.moveToNext()) {
        final ApplicationDetails _item;
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpPackageName;
        _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
        _item = new ApplicationDetails(_tmpName,_tmpPackageName);
        _result[_index] = _item;
        _index ++;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
