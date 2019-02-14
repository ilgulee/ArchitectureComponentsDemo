package ilgulee.com.architecturecomponentsdemo.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ilgulee.com.architecturecomponentsdemo.dao.DonerDao;
import ilgulee.com.architecturecomponentsdemo.entity.Doner;
import ilgulee.com.architecturecomponentsdemo.roomdatabase.DonerRoomDatabase;

/**
 * A Repository is a class that abstracts access to multiple data sources.
 * The Repository is not part of the Architecture Components libraries,
 * but is a suggested best practice for code separation and architecture.
 * A Repository class handles data operations. It provides a clean API to the rest of the app for app data.
 * <p>
 * A Repository manages query threads and allows you to use multiple backends.
 * In the most common example,
 * the Repository implements the logic for deciding whether to fetch data from a network
 * or use results cached in a local database.
 */
public class DonerRepository {
    private DonerDao mDonerDao;
    private LiveData<List<Doner>> mAllDoners;

    public DonerRepository(Application application) {
        DonerRoomDatabase db = DonerRoomDatabase.getDatabase(application);
        mDonerDao = db.donerDao();
        mAllDoners = mDonerDao.getAllDoners();
    }

    /**
     * Add a wrapper for getAllWords().
     * Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     *
     * @returnv LiveData
     */
    public LiveData<List<Doner>> getAllDoners() {
        return mAllDoners;
    }

    public void insert(Doner doner) {
        new InsertAsyncTask(mDonerDao).execute(doner);
    }

    public void delete(Doner doner) {
        new DeleteAsyncTask(mDonerDao).execute(doner);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(mDonerDao).execute();
    }

    public void update(Doner doner) {
        new UpdateAsyncTask(mDonerDao).execute(doner);
    }

    /**
     * Add a wrapper for the insert() method.
     * You must call this on a non-UI thread or your app will crash.
     * Room ensures that you don't do any long-running operations on the main thread, blocking the UI.
     */
    private static class InsertAsyncTask extends android.os.AsyncTask<Doner, Void, Void> {
        private DonerDao mAsyncTaskDonerDao;

        public InsertAsyncTask(DonerDao donerDao) {
            mAsyncTaskDonerDao = donerDao;
        }

        @Override
        protected Void doInBackground(Doner... doners) {
            mAsyncTaskDonerDao.insert(doners[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Doner, Void, Void> {
        private DonerDao mAsyncTaskDonerDao;

        public DeleteAsyncTask(DonerDao donerDao) {
            mAsyncTaskDonerDao = donerDao;
        }

        @Override
        protected Void doInBackground(Doner... doners) {
            mAsyncTaskDonerDao.delete(doners[0]);
            return null;
        }
    }


    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private DonerDao mAsyncTaskDonerDao;

        public DeleteAllAsyncTask(DonerDao donerDao) {
            mAsyncTaskDonerDao = donerDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDonerDao.deleteAll();
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Doner, Void, Void> {
        private DonerDao mAsyncTaskDonerDao;

        public UpdateAsyncTask(DonerDao donerDao) {
            mAsyncTaskDonerDao = donerDao;
        }

        @Override
        protected Void doInBackground(Doner... doners) {
            mAsyncTaskDonerDao.update(doners[0]);
            return null;
        }
    }
}
