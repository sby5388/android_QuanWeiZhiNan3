package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }


    private class PoliceCrimeHolder extends CrimeHolder {
        private Button mButtonCallPolice;

        PoliceCrimeHolder(LayoutInflater inflater, ViewGroup parent, @LayoutRes int layoutID) {
            super(inflater, parent, layoutID);
            mButtonCallPolice = itemView.findViewById(R.id.button_call_police);
            mButtonCallPolice.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == mButtonCallPolice.getId()) {
                Toast.makeText(getContext(), "Call Police", Toast.LENGTH_SHORT).show();
                return;
            }
            super.onClick(view);
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Crime mCrime;

        private TextView mTitleTextView;
        private TextView mDateTextView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent, @LayoutRes int layoutID) {
            super(inflater.inflate(layoutID, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(),
                    mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private static final int VIEW_TYPE_NORMAL = 0;
        private static final int VIEW_TYPE_REQUIRES_POLICE = 1;

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }


        @Override
        public int getItemViewType(int position) {
            final Crime crime = mCrimes.get(position);
            return crime.isRequiresPolice() ? VIEW_TYPE_REQUIRES_POLICE : VIEW_TYPE_NORMAL;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            if (viewType == VIEW_TYPE_NORMAL) {
                return new CrimeHolder(layoutInflater, parent, R.layout.list_item_crime);
            }
            if (viewType == VIEW_TYPE_REQUIRES_POLICE) {
                return new PoliceCrimeHolder(layoutInflater, parent, R.layout.list_item_crime_police);
            }
            // 2019/11/28 default
            return new CrimeHolder(layoutInflater, parent, R.layout.list_item_crime);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
