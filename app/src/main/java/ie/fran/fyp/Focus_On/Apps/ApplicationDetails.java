package ie.fran.fyp.Focus_On.Apps;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ApplicationDetails")
public class ApplicationDetails {

    @PrimaryKey
    @NonNull
    private String name;
    private String packageName;


    @Ignore
    public ApplicationDetails() {
    }

    @Ignore
    public ApplicationDetails(String name) {
        this.name = name;
    }

    public ApplicationDetails(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationDetails that = (ApplicationDetails) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
