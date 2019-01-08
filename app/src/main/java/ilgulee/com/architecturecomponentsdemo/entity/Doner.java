package ilgulee.com.architecturecomponentsdemo.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Entity: When working with Architecture Components, this is an annotated class that describes a database table.
 */
@Entity(tableName = "doner_table")
public class Doner {

    @PrimaryKey(autoGenerate = true)
    private Integer Id;

    @ColumnInfo(name = "name")
    @NonNull
    private String fullName;

    private String email;
    private String city;
    private int priority;

    @NonNull
    private String bloodGroup;

    public Doner(@NonNull String fullName, String email, String city, @NonNull String bloodGroup, int priority) {
        this.fullName = fullName;
        this.email = email;
        this.city = city;
        this.bloodGroup = bloodGroup;
        this.priority = priority;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getId() {
        return Id;
    }

    public int getPriority() {
        return priority;
    }


    @NonNull
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    @NonNull
    public String getBloodGroup() {
        return bloodGroup;
    }
}
