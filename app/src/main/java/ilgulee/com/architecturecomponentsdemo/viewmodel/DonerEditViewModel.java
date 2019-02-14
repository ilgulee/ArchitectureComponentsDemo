package ilgulee.com.architecturecomponentsdemo.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import ilgulee.com.architecturecomponentsdemo.entity.Doner;

public class DonerEditViewModel extends ViewModel {
    private static MutableLiveData<Doner> mDoner = new MutableLiveData<>();

    public MutableLiveData<Doner> getDoner() {
        return mDoner;
    }
}
