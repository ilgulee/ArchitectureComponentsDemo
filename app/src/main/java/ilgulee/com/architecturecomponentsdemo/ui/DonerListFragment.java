package ilgulee.com.architecturecomponentsdemo.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ilgulee.com.architecturecomponentsdemo.R;
import ilgulee.com.architecturecomponentsdemo.entity.Doner;
import ilgulee.com.architecturecomponentsdemo.viewmodel.DonerEditViewModel;
import ilgulee.com.architecturecomponentsdemo.viewmodel.DonerViewModel;

public class DonerListFragment extends Fragment {
    private static final String TAG = "DonerListFragment";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton mFab;
    private DonerAdapter mDonerAdapter;
    private Unbinder mUnbinder;
    private DonerViewModel mDonerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DonerAddActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mDonerAdapter = new DonerAdapter();
        updateUI();
        return view;
    }

    private void updateUI() {
        mDonerViewModel = ViewModelProviders.of(this).get(DonerViewModel.class);
        mDonerViewModel.getAllDoners().observe(this, new Observer<List<Doner>>() {
            @Override
            public void onChanged(@Nullable List<Doner> doners) {
                mDonerAdapter.dataUpdate(doners);
            }
        });
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mDonerAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        Doner selectedDoner = mDonerAdapter.getDonerItemAt(viewHolder.getAdapterPosition());
                        mDonerViewModel.delete(selectedDoner);
                    }
                });
        helper.attachToRecyclerView(mRecyclerView);
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
            mDoners.clear();
            mDoners = doners;
            notifyDataSetChanged();
        }

        public Doner getDonerItemAt(int position) {
            return mDoners.get(position);
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
            DonerEditViewModel donerEditViewModel = ViewModelProviders.of(getActivity()).get(DonerEditViewModel.class);
            donerEditViewModel.getDoner().setValue(mDoner);

//            Toast.makeText(getActivity(), donerEditViewModel.getDoner().getValue().getFullName(),Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getActivity(), DonerEditActivity.class);
            startActivity(intent);
        }
    }
}
