package ilgulee.com.architecturecomponentsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class DonerAddFragment extends Fragment {
    private static final String TAG = "DonerAddFragment";
    public static final String EXTRA_NAME = "ilgulee.com.architecturecomponentsdemo.EXTRA_NAME";
    public static final String EXTRA_BLOOD = "ilgulee.com.architecturecomponentsdemo.EXTRA_BLOOD";
    public static final String EXTRA_CITY = "ilgulee.com.architecturecomponentsdemo.EXTRA_CITY";
    public static final String EXTRA_EMAIL = "ilgulee.com.architecturecomponentsdemo.EXTRA_EMAIL";
    public static final String EXTRA_PRIORITY = "ilgulee.com.architecturecomponentsdemo.EXTRA_PRIORITY";

    private Unbinder mUnbinder;
    @BindView(R.id.doner_name)
    EditText name;
    @BindView(R.id.blood_type)
    EditText bloodType;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.priority_picker)
    NumberPicker priority;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void saveDoner() {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(bloodType.getText())
                || TextUtils.isEmpty(city.getText()) || TextUtils.isEmpty(email.getText())
                || TextUtils.isEmpty(String.valueOf(priority.getValue()))) {
            getActivity().setResult(RESULT_CANCELED, replyIntent);
        } else {
            String donerName = name.getText().toString();
            String blood = bloodType.getText().toString();
            String address = city.getText().toString();
            String emailAddress = email.getText().toString();
            int priorityNumber = priority.getValue();

            replyIntent.putExtra(EXTRA_NAME, donerName);
            replyIntent.putExtra(EXTRA_BLOOD, blood);
            replyIntent.putExtra(EXTRA_CITY, address);
            replyIntent.putExtra(EXTRA_EMAIL, emailAddress);
            replyIntent.putExtra(EXTRA_PRIORITY, priorityNumber);

            getActivity().setResult(RESULT_OK, replyIntent);
        }
        getActivity().finish();

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_doner_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_doner) {
            saveDoner();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Add Doner");

        View view = inflater.inflate(R.layout.fragment_doner, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        priority.setMinValue(1);
        priority.setMaxValue(3);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
