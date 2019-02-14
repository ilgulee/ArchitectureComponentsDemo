package ilgulee.com.architecturecomponentsdemo.ui;

import android.support.v4.app.Fragment;

public class DonerAddActivity extends SingleFragmentActivity {
    private static final String TAG = "DonerAddActivity";

    @Override
    protected Fragment createFragment() {
        return new DonerAddFragment();
    }
}
