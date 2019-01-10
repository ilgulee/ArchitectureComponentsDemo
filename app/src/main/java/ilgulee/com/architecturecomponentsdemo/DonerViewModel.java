package ilgulee.com.architecturecomponentsdemo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ilgulee.com.architecturecomponentsdemo.entity.Doner;

/**
 * The ViewModel's role is to provide data to the UI and survive configuration changes.
 * A ViewModel acts as a communication center between the Repository and the UI.
 * You can also use a ViewModel to share data between fragments.
 * The ViewModel is part of the lifecycle library.
 * <p>
 * A ViewModel holds your app's UI data in a lifecycle-conscious way that survives configuration changes.
 * Separating your app's UI data from your Activity and Fragment classes lets you better follow the single responsibility principle: Your activities and fragments are responsible for drawing data to the screen, while your ViewModel can take care of holding and processing all the data needed for the UI.
 * <p>
 * In the ViewModel, use LiveData for changeable data that the UI will use or display.
 * <p>
 * Using LiveData has several benefits
 * You can put an observer on the data (instead of polling for changes) and only update the
 * the UI when the data actually changes.
 * The Repository and the UI are completely separated by the ViewModel.
 * There are no database calls from the ViewModel, making the code more testable.
 * <p>
 * <p>
 * Warning: Never pass context into ViewModel instances.
 * Do not store Activity, Fragment, or View instances or their Context in the ViewModel.
 * For example, an Activity can be destroyed and created many times during the lifecycle of a ViewModel
 * as the device is rotated.
 * If you store a reference to the Activity in the ViewModel,
 * you end up with references that point to the destroyed Activity. This is a memory leak.
 * <p>
 * If you need the application context, use AndroidViewModel, as shown in this example
 * <p>
 * Important: ViewModel is not a replacement for the onSaveInstanceState() method,
 * because the ViewModel does not survive a process shutdown. Learn more here.
 */

public class DonerViewModel extends AndroidViewModel {
    private DonerRepository mRepository;
    private LiveData<List<Doner>> mAllDoners;

    public DonerViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DonerRepository(application);
        mAllDoners = mRepository.getAllDoners();
    }

    public LiveData<List<Doner>> getAllDoners() {
        return mAllDoners;
    }

    public void insert(Doner doner) {
        mRepository.insert(doner);
    }

    public void delete(Doner doner) {
        mRepository.delete(doner);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void update(Doner doner) {
        mRepository.update(doner);
    }
}