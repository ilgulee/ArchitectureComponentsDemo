package ilgulee.com.architecturecomponentsdemo.roomdatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import ilgulee.com.architecturecomponentsdemo.dao.DonerDao;
import ilgulee.com.architecturecomponentsdemo.entity.Doner;

/**
 * Room is a database layer on top of an SQLite database.
 * Room takes care of mundane tasks that you used to handle with an SQLiteOpenHelper.
 * <p>
 * Room uses the DAO to issue queries to its database.
 * By default, to avoid poor UI performance, Room doesn't allow you to issue database queries on the main thread.
 * LiveData applies this rule by automatically running the query asynchronously on a background thread, when needed.
 * Room provides compile-time checks of SQLite statements.
 * Your Room class must be abstract and extend RoomDatabase.
 * Usually, you only need one instance of the Room database for the whole app.
 * <p>
 * Implement the Room database
 * Create a public abstract class that extends RoomDatabase and call it DonerRoomDatabase.
 * public abstract class DonerRoomDatabase extends RoomDatabase {}
 * Annotate the class to be a Room database,
 * declare the entities that belong in the database and set the version number. Listing the entities will create tables in the database.
 *
 * @Database(entities = {Doner.class}, version = 1)
 * Define the DAOs that work with the database.
 * Provide an abstract "getter" method for each @Dao.
 * public abstract DonerDao donerDao();
 * <p>
 * When you modify the database schema, you'll need to update the version number and define how to handle migrations.
 * <p>
 * For a sample, destroying and re-creating the database is a fine migration strategy.
 * For a real app, you must implement a migration strategy.
 * <p>
 * When you modify the database schema, you'll need to update the version number and define how to handle migrations.
 * <p>
 * For a sample, destroying and re-creating the database is a fine migration strategy.
 * For a real app, you must implement a migration strategy.
 */

/**
 * When you modify the database schema, you'll need to update the version number and define how to handle migrations.
 * <p>
 * For a sample, destroying and re-creating the database is a fine migration strategy.
 * For a real app, you must implement a migration strategy.
 */

/**
 * You can set annotation processor argument (room.schemaLocation) to tell Room to export the schema into a folder.
 * Even though it is not mandatory, it is a good practice to have version history in your codebase
 * and you should commit that file into your version control system (but don't ship it with your app!).
 */
@Database(entities = {Doner.class}, version = 2, exportSchema = false)
public abstract class DonerRoomDatabase extends RoomDatabase {
    public abstract DonerDao donerDao();

    /**
     * Make the DonerRoomDatabase a singleton
     * to prevent having multiple instances of the database opened at the same time.
     */
    private static volatile DonerRoomDatabase INSTANCE;

    public static DonerRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DonerRoomDatabase.class) {
                if (INSTANCE == null) {
                    /**
                     * Add the code to get a database.
                     * This code uses Room's database builder to create a RoomDatabase object
                     * in the application context from the DonerRoomDatabase class and names it "doner_database".
                     */
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DonerRoomDatabase.class,
                            "doner_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Insert the stub data into database after creating database instance
     */
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateStubAsynTask(INSTANCE).execute();
        }
    };

    private static class PopulateStubAsynTask extends AsyncTask<Void, Void, Void> {
        private DonerDao mPopulateStubAsynTaskDao;

        public PopulateStubAsynTask(DonerRoomDatabase db) {
            mPopulateStubAsynTaskDao = db.donerDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mPopulateStubAsynTaskDao.insert(new Doner("Ilgu Lee", "iglee@gmail.com", "Toronto", "Type O", 3));
            mPopulateStubAsynTaskDao.insert(new Doner("Samho Kim", "samho@gmail.com", "Seoul", "Type B", 2));
            mPopulateStubAsynTaskDao.insert(new Doner("Yeonhee Seo", "yeonhee@gmail.com", "Mokpo", "Type AB", 1));

            return null;
        }
    }
}
