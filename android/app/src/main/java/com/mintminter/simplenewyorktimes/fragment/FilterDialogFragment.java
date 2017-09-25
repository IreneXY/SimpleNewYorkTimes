package com.mintminter.simplenewyorktimes.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mintminter.simplenewyorktimes.R;
import com.mintminter.simplenewyorktimes.interfaces.SearchParamsCallback;
import com.mintminter.simplenewyorktimes.models.SearchParams;
import com.mintminter.simplenewyorktimes.models.Time;
import com.mintminter.simplenewyorktimes.util.Common;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Irene on 9/24/17.
 */

public class FilterDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    public static final String TAG = "FilterDialogFragment";

    private View mFragmentView;
    private RelativeLayout mBeginDateArea;
    private TextView mBeginDate;
    private RelativeLayout mEndDateArea;
    private TextView mEndDate;
    private Spinner mSort;
    private AppCompatCheckBox mArts;
    private AppCompatCheckBox mFashion;
    private AppCompatCheckBox mSports;
    private TextView mConfirm;
    private TextView mReset;
    private SearchParamsCallback mCallback;
    private boolean mSetBeginDate;
    private SearchParams mSearchParams;

    public FilterDialogFragment() {
    }

    public static FilterDialogFragment newInstance(SearchParams searchParams) {
        FilterDialogFragment frag = new FilterDialogFragment();
        Bundle settings = new Bundle();
        settings.putParcelable(Common.EXTRA_SEARCHPARAM, Parcels.wrap(searchParams));
        frag.setArguments(settings);
        return frag;
    }

    public void setSearchParamsCallback(SearchParamsCallback callback){
        mCallback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_filter, container);
        mBeginDateArea = (RelativeLayout) mFragmentView.findViewById(R.id.filter_begindate_actionarea);
        mBeginDate = (TextView) mFragmentView.findViewById(R.id.filter_begindate_date);
        mEndDateArea = (RelativeLayout) mFragmentView.findViewById(R.id.filter_enddate_actionarea);
        mEndDate = (TextView) mFragmentView.findViewById(R.id.filter_enddate_date);
        mArts = (AppCompatCheckBox) mFragmentView.findViewById(R.id.filter_arts);
        mSort = (Spinner) mFragmentView.findViewById(R.id.filter_sort);
        mFashion = (AppCompatCheckBox) mFragmentView.findViewById(R.id.filter_fashion);
        mSports = (AppCompatCheckBox) mFragmentView.findViewById(R.id.filter_sports);
        mReset = (TextView) mFragmentView.findViewById(R.id.filter_reset);
        mConfirm = (TextView) mFragmentView.findViewById(R.id.filter_confirm);
        return mFragmentView;
    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchParams = Parcels.unwrap(getArguments().getParcelable(Common.EXTRA_SEARCHPARAM));
        getDialog().setTitle(Common.getString(getActivity(), R.string.filter_title));

        mBeginDate.setText(mSearchParams.begin_date);
        mBeginDateArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Time time = Common.time(mSearchParams.begin_date);
                mSetBeginDate = true;
                new DatePickerDialog(getActivity(), FilterDialogFragment.this, time.year, time.month, time.day).show();
            }
        });

        mEndDate.setText(mSearchParams.end_date);
        mEndDateArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Time time = Common.time(Calendar.getInstance().getTimeInMillis());
                mSetBeginDate = false;
                new DatePickerDialog(getActivity(), FilterDialogFragment.this, time.year, time.month, time.day).show();
            }
        });

        if(SearchParams.SORT_NEWEST.toLowerCase().equals(mSearchParams.sort.toLowerCase())){
            mSort.setSelection(0);
        }else{
            mSort.setSelection(1);
        }

        if(mSearchParams.news_desk.size() > 0){
            for(String s : mSearchParams.news_desk){
                if(s.equals(mArts.getText().toString())){
                    mArts.setChecked(true);
                }else if(s.equals(mFashion.getText().toString())){
                    mFashion.setChecked(true);
                }else if(s.equals(mSports.getText().toString())){
                    mSports.setChecked(true);
                }
            }
        }

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBeginDate.setText("");
                mEndDate.setText("");
                mArts.setChecked(false);
                mFashion.setChecked(false);
                mSports.setChecked(false);
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchParams.news_desk.clear();
                if(mArts.isChecked()){
                    mSearchParams.news_desk.add(mArts.getText().toString());
                }
                if(mFashion.isChecked()){
                    mSearchParams.news_desk.add(mFashion.getText().toString());
                }
                if(mSports.isChecked()){
                    mSearchParams.news_desk.add(mSports.getText().toString());
                }
                mSearchParams.begin_date = mBeginDate.getText().toString();
                mSearchParams.end_date = mEndDate.getText().toString();
                mSearchParams.sort = ((String) mSort.getSelectedItem()).toLowerCase();
                mCallback.setSearchParams(mSearchParams);
                dismiss();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        long time = calendar.getTimeInMillis();
        String date = new SimpleDateFormat(Common.SIMPLEDATEFORMAT_REQUEST_DATE).format(new Date(time));
        if(mSetBeginDate){
            mSearchParams.begin_date = date;
            mBeginDate.setText(date);
        }else{
            mSearchParams.end_date = date;
            mEndDate.setText(date);
        }

    }
}
