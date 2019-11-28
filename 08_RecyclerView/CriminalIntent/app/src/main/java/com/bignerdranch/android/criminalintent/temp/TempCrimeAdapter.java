package com.bignerdranch.android.criminalintent.temp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.criminalintent.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator  on 2019/10/22.
 */
public class TempCrimeAdapter extends RecyclerView.Adapter<TempCrimeAdapter.TempCrimeViewHolder> {

    private List<TempCrime> mTempCrimes;

    public TempCrimeAdapter() {
        mTempCrimes = new ArrayList<>();
    }

    @NonNull
    @Override
    public TempCrimeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        return new TempCrimeViewHolder(inflater,viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull TempCrimeViewHolder tempCrimeViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mTempCrimes.size();
    }

    public static class TempCrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TempCrime mCrime;
        private final TextView mTextViewTitle;
        private final TextView mTextViewDate;

        TempCrimeViewHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.list_item_crime, viewGroup, false));

            itemView.setOnClickListener(this);
            mTextViewDate = itemView.findViewById(R.id.crime_date);
            mTextViewTitle = itemView.findViewById(R.id.crime_title);

        }


        private void bind(TempCrime crime) {
            this.mCrime = crime;
            this.mTextViewTitle.setText(crime.getTitle());
            this.mTextViewDate.setText(crime.getDate());

        }

        @Override
        public void onClick(View v) {

        }
    }
}
