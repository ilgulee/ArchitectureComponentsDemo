package ilgulee.com.architecturecomponentsdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ilgulee.com.architecturecomponentsdemo.entity.Doner;

public class DonerFragment extends Fragment {
    private static final String TAG = "DonerFragment";
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDoner = new Doner("dummy name", "dummy@gmail.com", "dummy city", "Type O", 2);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doner, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        name.setText(mDoner.getFullName());
        bloodType.setText(mDoner.getBloodGroup());
        city.setText(mDoner.getCity());
        email.setText(mDoner.getEmail());
        priority.setMinValue(1);
        priority.setMaxValue(3);
        priority.setValue(mDoner.getPriority());
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
