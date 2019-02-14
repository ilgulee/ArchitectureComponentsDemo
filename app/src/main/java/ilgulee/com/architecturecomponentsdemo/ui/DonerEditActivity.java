package ilgulee.com.architecturecomponentsdemo.ui;

import android.support.v4.app.Fragment;

public class DonerEditActivity extends SingleFragmentActivity {
    private static final String TAG = "DonerEditActivity";

    @Override
    protected Fragment createFragment() {
        return new DonerEditFragment();
    }
}
