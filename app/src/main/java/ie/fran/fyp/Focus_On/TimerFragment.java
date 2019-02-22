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
import android.widget.TimePicker;
import android.widget.Toast;

import ie.fran.fyp.R;

public class TimerFragment extends Fragment {
    TextView header;
    TextView time;
    NumberPicker sec;
    NumberPicker min;
    NumberPicker hours;
    Button start;
    TimePicker simpleTimePicker;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        //  initiate the view's
        header =  view.findViewById(R.id.header);
        time =  view.findViewById(R.id.time);
        sec = view.findViewById(R.id.sec);
        min = view.findViewById(R.id.min);
        hours =  view.findViewById(R.id.hours);
        start =  view.findViewById(R.id.start);

        simpleTimePicker.setIs24HourView(false); // used to display AM/PM mode
        // perform set on time changed listener event
        simpleTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // display a toast with changed values of time picker

              //  Toast.makeText(hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                time.setText("Time is :: " + hours + " : " + min); // set the current time in text view
            }
        });
        return view;

    }
}
