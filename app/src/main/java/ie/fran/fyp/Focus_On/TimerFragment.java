package ie.fran.fyp.Focus_On;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import ie.fran.fyp.R;

public class TimerFragment extends Fragment {
    TextView header;
    TextView time;
    NumberPicker sec;
    NumberPicker min;
    NumberPicker hours;
    Button start, restart;

    private boolean mTimerRunning;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        //  initiate the view's
        header = view.findViewById(R.id.header);
        time = view.findViewById(R.id.time);
        sec = view.findViewById(R.id.sec);
        min = view.findViewById(R.id.min);
        hours = view.findViewById(R.id.hours);
        start = view.findViewById(R.id.start);
        restart = view.findViewById(R.id.restart);
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetTimer();
            }
        });
        updateCountDownText();
        return view;
    }

    private void updateCountDownText() {
    }

    private void pauseTimer() {
    }

    private void ResetTimer() {
    }

    private void startTimer() {

    }

}