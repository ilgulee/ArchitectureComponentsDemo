package ilgulee.com.architecturecomponentsdemo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ilgulee.com.architecturecomponentsdemo.entity.Doner;

import static android.app.Activity.RESULT_OK;

public class DonerListFragment extends Fragment {
    private static final String TAG = "DonerListFragment";
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton mFab;
    private DonerAdapter mDonerAdapter;
    private Unbinder mUnbinder;
    private DonerViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DonerAddActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mDonerAdapter = new DonerAdapter();
        updateUI();
        return view;
    }

    private void updateUI() {
        mViewModel = ViewModelProviders.of(this).get(DonerViewModel.class);
        mViewModel.getAllDoners().observe(this, new Observer<List<Doner>>() {
            @Override
            public void onChanged(@Nullable List<Doner> doners) {
                mDonerAdapter.dataUpdate(doners);
            }
        });
        mRecyclerView.setAdapter(mDonerAdapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(DonerAddFragment.EXTRA_NAME);
            String bloodType = data.getStringExtra(DonerAddFragment.EXTRA_BLOOD);
            String city = data.getStringExtra(DonerAddFragment.EXTRA_CITY);
            String email = data.getStringExtra(DonerAddFragment.EXTRA_EMAIL);
            int priority = data.getIntExtra(DonerAddFragment.EXTRA_PRIORITY, 1);
            mViewModel.insert(new Doner(name, email, city, bloodType, priority));
        } else {
            Toast.makeText(
                    getActivity(),
                    "Not saved",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private class DonerAdapter extends RecyclerView.Adapter<DonerListFragment.DonerViewHolder> {
        List<Doner> mDoners;

        public DonerAdapter() {
            mDoners = new ArrayList<>();
        }

        @NonNull
        @Override
        public DonerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.priority_blood_list, viewGroup, false);
            return new DonerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DonerViewHolder donerViewHolder, int i) {
            Doner doner = mDoners.get(i);
            donerViewHolder.bind(doner);
        }

        @Override
        public int getItemCount() {
            return mDoners.size();

        }

        public void dataUpdate(List<Doner> doners) {
            mDoners = doners;
            notifyDataSetChanged();
        }
    }

    public class DonerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.priority)
        TextView mPriority;
        @BindView(R.id.bloodType)
        TextView mBloodType;
        private Doner mDoner;

        public DonerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Doner doner) {
            mDoner = doner;
            mPriority.setText(String.valueOf(mDoner.getPriority()));
            mBloodType.setText(mDoner.getBloodGroup());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), mDoner.getFullName() + " clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}
