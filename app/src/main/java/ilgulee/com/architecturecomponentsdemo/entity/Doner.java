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
    @NonNull
    private String email;
    @NonNull
    private String city;
    @NonNull
    private Integer priority;
    @NonNull
    private String bloodGroup;

    public Doner(@NonNull String fullName, @NonNull String email, @NonNull String city, @NonNull String bloodGroup, @NonNull Integer priority) {
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

    @NonNull
    public Integer getPriority() {
        return priority;
    }

    @NonNull
    public String getFullName() {
        return fullName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    @NonNull
    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setFullName(@NonNull String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    public void setPriority(@NonNull Integer priority) {
        this.priority = priority;
    }

    public void setBloodGroup(@NonNull String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
