package ilgulee.com.architecturecomponentsdemo;

import android.support.v4.app.Fragment;

public class DonerListActivity extends SingleFragmentActivity {
    private static final String TAG = "DonerListActivity";


    @Override
    protected Fragment createFragment() {
        return new DonerListFragment();
    }
}
