package ilgulee.com.architecturecomponentsdemo.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ilgulee.com.architecturecomponentsdemo.entity.Doner;

/**
 * DAO: Data access object.
 * A mapping of SQL queries to functions.
 * You used to have to define these painstakingly in your SQLiteOpenHelper class.
 * When you use a DAO, you call the methods, and Room takes care of the rest.
 * The DAO must be an interface or abstract class.
 * By default, all queries must be executed on a separate thread.
 * Room uses the DAO to create a clean API for your code.
 * Generally one DAO for one table
 */

@Dao
public interface DonerDao {

    @Insert
    void insert(Doner doner);

    @Delete
    void delete(Doner doner);

    @Query("DELETE FROM doner_table")
    void deleteAll();

    @Update
    void update(Doner doner);

    /**
     * LiveData is a data holder class that can be observed within a given lifecycle.
     * Always holds/caches latest version of data. Notifies its active observers when the
     * data has changed. Since we are getting all the contents of the database,
     * we are notified whenever any of the database contents have changed.
     * <p>
     * you create an Observer of the data in the onCreate() method of MainActivity
     * and override the observer's onChanged() method.
     * When the LiveData changes, the observer is notified and onChanged() is executed.
     * You will then update the cached data in the adapter, and the adapter will update what the user sees.
     */
    @Query("SELECT * FROM doner_table ORDER BY priority ASC")
    LiveData<List<Doner>> getAllDoners();

    /**
     * When data changes you usually want to take some action, such as displaying the updated data in the UI.
     * This means you have to observe the data so that when it changes, you can react.
     * Depending on how the data is stored, this can be tricky.
     * Observing changes to data across multiple components of your app
     * can create explicit, rigid dependency paths between the components.
     * This makes testing and debugging difficult, among other things.
     *
     * LiveData, a lifecycle library class for data observation, solves this problem.
     * Use a return value of type LiveData in your method description,
     * and Room generates all necessary code to update the LiveData when the database is updated.
     * If you use LiveData independently from Room, you have to manage updating the data. LiveData has no publicly available methods to update the stored data.
     *
     * If you, the developer, want to update the stored data,
     * you must use MutableLiveData instead of LiveData.
     * The MutableLiveData class has two public methods that allow you to set the value of a LiveData object,
     * setValue(T) and postValue(T).
     * Usually, MutableLiveData is used in the ViewModel,
     * and then the ViewModel only exposes immutable LiveData objects to the observers.
     */


}
