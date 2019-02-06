package ilgulee.com.architecturecomponentsdemo;

import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ilgulee.com.architecturecomponentsdemo.entity.Doner;
import ilgulee.com.architecturecomponentsdemo.viewmodel.DonerViewModel;

public class DonerAddFragment extends Fragment {
    private static final String TAG = "DonerAddFragment";
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
        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(bloodType.getText())
                || TextUtils.isEmpty(city.getText()) || TextUtils.isEmpty(email.getText())
                || TextUtils.isEmpty(String.valueOf(priority.getValue()))) {
            Toast.makeText(getActivity(), "Enter all inputs", Toast.LENGTH_SHORT).show();
        } else {
            String donerName = name.getText().toString();
            String blood = bloodType.getText().toString();
            String address = city.getText().toString();
            String emailAddress = email.getText().toString();
            int priorityNumber = priority.getValue();
            Doner doner = new Doner(donerName, emailAddress, address, blood, priorityNumber);
            DonerViewModel viewModel = ViewModelProviders.of(this).get(DonerViewModel.class);
            viewModel.insert(doner);
            getActivity().finish();
        }
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
