package ilgulee.com.architecturecomponentsdemo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ilgulee.com.architecturecomponentsdemo.entity.Doner;

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
