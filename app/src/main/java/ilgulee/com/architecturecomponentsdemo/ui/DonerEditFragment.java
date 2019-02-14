package ilgulee.com.architecturecomponentsdemo.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ilgulee.com.architecturecomponentsdemo.R;
import ilgulee.com.architecturecomponentsdemo.entity.Doner;
import ilgulee.com.architecturecomponentsdemo.viewmodel.DonerEditViewModel;
import ilgulee.com.architecturecomponentsdemo.viewmodel.DonerViewModel;

public class DonerEditFragment extends Fragment {
    private static final String TAG = "DonerEditFragment";
    private Doner mDoner;
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
    private DonerEditViewModel mDonerEditViewModel;
    private DonerViewModel mDonerViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Edit Doner");
        View view = inflater.inflate(R.layout.fragment_doner, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mDonerEditViewModel = ViewModelProviders.of(this).get(DonerEditViewModel.class);
        mDoner = mDonerEditViewModel.getDoner().getValue();
        Log.d(TAG, "onCreateView: " + mDoner);

        updateUI();
        return view;
    }

    private void updateUI() {
        name.setText(mDoner.getFullName());
        bloodType.setText(mDoner.getBloodGroup());
        city.setText(mDoner.getCity());
        email.setText(mDoner.getEmail());
        priority.setMinValue(1);
        priority.setMaxValue(3);
        priority.setValue(mDoner.getPriority());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_doner_menu, menu);
    }

    private void updateDoner() {
        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(bloodType.getText())
                || TextUtils.isEmpty(city.getText()) || TextUtils.isEmpty(email.getText())
                || TextUtils.isEmpty(String.valueOf(priority.getValue()))) {
            Toast.makeText(getActivity(), "Enter all inputs", Toast.LENGTH_SHORT).show();
        } else {
            mDoner.setFullName(name.getText().toString());
            mDoner.setBloodGroup(bloodType.getText().toString());
            mDoner.setCity(city.getText().toString());
            mDoner.setEmail(email.getText().toString());
            mDoner.setPriority(priority.getValue());
            mDonerViewModel = ViewModelProviders.of(this).get(DonerViewModel.class);
            mDonerViewModel.update(mDoner);
            getActivity().finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_doner) {
            updateDoner();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
