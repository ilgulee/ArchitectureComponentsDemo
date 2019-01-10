package ilgulee.com.architecturecomponentsdemo;

import android.support.v4.app.Fragment;

public class DonerActivity extends SingleFragmentActivity {
    private static final String TAG = "DonerActivity";

    @Override
    protected Fragment createFragment() {
        return new DonerFragment();
    }
}
