package com.example.task.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.task.R;

import com.example.task.viewmodel.TimerModelView;

public class TimerFragment extends Fragment

{
    TextView timer_textview;
    private TimerModelView mLiveDataTimerViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_timer, container, false);
        timer_textview =root.findViewById(R.id.timer_textview);

        mLiveDataTimerViewModel = new ViewModelProvider(this).get(TimerModelView.class);

        subscribe();

        return root;
    }
    private void subscribe() {
        final Observer<Long> elapsedTimeObserver = new Observer<Long>() {

            @Override
            public void onChanged(@Nullable final Long aLong) {
                String newText =  String.valueOf(aLong);

                //timer_textview.setText(String.valueOf(aLong));
                //timer_textview.setText(newText);
                Log.d("insideFunc","insideFunc");

                //Log.d("countValue",String.valueOf(count));
                timer_textview.setText(String.valueOf(aLong));

            }
        };
        mLiveDataTimerViewModel.getElapsedTime().observe(requireActivity(), elapsedTimeObserver);
    }

}
