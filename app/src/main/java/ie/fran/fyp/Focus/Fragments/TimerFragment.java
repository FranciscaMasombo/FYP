package ie.fran.fyp.Focus.Fragments;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;

import ie.fran.fyp.Focus.Fragments.AppsFragment;
import ie.fran.fyp.R;


public class TimerFragment extends Fragment  {

    Button startButton;
    Button resumeButton;
    Button pauseButton;
    Button stopButton;
    TextView mntTimerView;
    TextView sndTimerView;
    EditText mntInput;
    EditText sndInput;
    public String SETTING_NOTIFICATION_LISTENER = "enabled_notification_listeners";
    private Boolean isPause = false;
    private Boolean isStop = false;
    public String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    public String PREF_ENABLED = "pref_enabled";
    public String PREF_PACKAGES_BLOCKED = "pref_packages_blocked";
    Switch blocker;
    SharedPreferences Pref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

//        blocker = view.findViewById(R.id.blocker);
//        if (blocker != null) {
//            if (hasAccessGranted()) {
//                blocker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        Pref.edit().putBoolean(PREF_ENABLED, isChecked).apply();
//                    }
//                });
//            }
//        }


        startButton = view.findViewById(R.id.startButton);
        resumeButton = view.findViewById(R.id.resumeButton);
        pauseButton = view.findViewById(R.id.pauseButton);
        stopButton = view.findViewById(R.id.stopButton);
        mntTimerView = view.findViewById(R.id.mntTimerView);
        sndTimerView = view.findViewById(R.id.sndTimerView);
        mntInput = view.findViewById(R.id.mntInput);
        sndInput = view.findViewById(R.id.sndInput);

        resumeButton.setEnabled(false);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        startButton.setEnabled(true);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setEnabled(false);
                pauseButton.setEnabled(true);
                stopButton.setEnabled(true);
                mntInput.setEnabled(false);
                sndInput.setEnabled(false);
                isStop = false;
                if (mntInput.length() == 0 || sndInput.length() == 0) {
                    if (mntInput.length() == 0 || sndInput.length() != 0) {
                        mntInput.setText("00");
                    } else if (mntInput.length() != 0 || sndInput.length() == 0) {
                        sndInput.setText("00");
                    } else {

                        mntInput.setText("00");
                        sndInput.setText("00");
                    }
                }
                String mntInputString = mntInput.getText().toString();
                String sndInputString = sndInput.getText().toString();
                int mntGet = Integer.parseInt(mntInputString);
                int sndGet = Integer.parseInt(sndInputString);
                int mntFinal = mntGet * 60;
                final int totalTime = mntFinal + sndGet;
                if (mntGet > 60) {
                    if (sndGet > 60) {
                        mntGet = sndGet / 60 + mntGet;
                    }
                    Toast.makeText(getActivity(), "Please enter Minutes less than 60", Toast.LENGTH_SHORT).show();
                    mntInput.setEnabled(true);
                    sndInput.setEnabled(true);
                    startButton.setEnabled(true);
                    pauseButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    resumeButton.setEnabled(false);
                    mntInput.setText("00");
                    sndInput.setText("00");
                    mntTimerView.setText("00");
                    sndTimerView.setText("00");
                } else {
                    if (totalTime == 0) {
                        Toast.makeText(getActivity(), "Please enter some value", Toast.LENGTH_SHORT).show();
                        mntInput.setEnabled(true);
                        sndInput.setEnabled(true);
                        startButton.setEnabled(true);
                        pauseButton.setEnabled(false);
                        stopButton.setEnabled(false);
                        resumeButton.setEnabled(false);
                    } else {
                        CountDownTimer timer;
                        timer = new CountDownTimer(totalTime * 1000 + 100, 1000) {

                            @Override
                            public void onTick(long p1) {
                                if (isPause || isStop) {
                                    cancel();
                                } else {
                                    int minutes = (int) p1 / 1000 / 60;
                                    int seconds = (int) p1 / 1000 - minutes * 60;
                                    String zeroDigit1 = "0";
                                    String zeroDigit2 = "0";
                                    if (minutes < 10) {
                                        zeroDigit1 = "0";
                                    } else {
                                        zeroDigit1 = "";
                                    }
                                    if (seconds < 10) {
                                        zeroDigit2 = "0";
                                    } else {
                                        zeroDigit2 = "";
                                    }
                                    mntTimerView.setText(zeroDigit1 + String.valueOf(minutes));
                                    sndTimerView.setText(zeroDigit2 + String.valueOf(seconds));
                                }
                            }

                            @Override
                            public void onFinish() {
                                Toast.makeText(getActivity(), "Finished", Toast.LENGTH_SHORT).show();
                                mntInput.setEnabled(true);
                                sndInput.setEnabled(true);
                                startButton.setEnabled(true);
                                pauseButton.setEnabled(false);
                                stopButton.setEnabled(false);
                                resumeButton.setEnabled(false);
                                mntInput.setText("00");
                                sndInput.setText("00");
                                mntTimerView.setText("00");
                                sndTimerView.setText("00");
                            }
                        }.start();
                    }
                }
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                resumeButton.setEnabled(true);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(true);
                isPause = true;
            }
        });
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                resumeButton.setEnabled(false);
                pauseButton.setEnabled(true);
                stopButton.setEnabled(true);
                startButton.setEnabled(false);
                isPause = false;
                int mntRsm = Integer.parseInt(mntTimerView.getText().toString());
                int sndRsm = Integer.parseInt(sndTimerView.getText().toString());
                final int totalTime = (mntRsm * 60) + sndRsm;
                CountDownTimer timer;
                timer = new CountDownTimer(totalTime * 1000 + 100, 1000) {
                    @Override
                    public void onTick(long p2) {
                        if (isPause || isStop) {
                            cancel();
                        } else {
                            int minutes = (int) p2 / 1000 / 60;
                            int seconds = (int) p2 / 1000 - minutes * 60;
                            String zeroDigit1 = "0";
                            String zeroDigit2 = "0";
                            if (minutes < 10) {
                                zeroDigit1 = "0";
                            } else {
                                zeroDigit1 = "";
                            }
                            if (seconds < 10) {
                                zeroDigit2 = "0";
                            } else {
                                zeroDigit2 = "";
                            }
                            mntTimerView.setText(zeroDigit1 + String.valueOf(minutes));
                            sndTimerView.setText(zeroDigit2 + String.valueOf(seconds));
                        }
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(getActivity(), "Finished", Toast.LENGTH_SHORT).show();
                        mntInput.setEnabled(true);
                        sndInput.setEnabled(true);
                        startButton.setEnabled(true);
                        pauseButton.setEnabled(false);
                        stopButton.setEnabled(false);
                        resumeButton.setEnabled(false);
                        mntInput.setText("00");
                        sndInput.setText("00");
                        mntTimerView.setText("00");
                        sndTimerView.setText("00");
                    }
                }.start();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                Toast.makeText(getActivity(), "Stoped", Toast.LENGTH_SHORT).show();
                resumeButton.setEnabled(false);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(false);
                startButton.setEnabled(true);
                mntInput.setEnabled(true);
                sndInput.setEnabled(true);
                mntInput.setText("00");
                sndInput.setText("00");
                mntTimerView.setText("00");
                sndTimerView.setText("00");
                isStop = true;
            }
        });
        return view;
    }

    //@Override
    public void onCheckboxAppChecked(int position, boolean isChecked) {
        String pkg = AppsFragment.adapter.getApp(position).packageName;
        if (Pref.contains(PREF_PACKAGES_BLOCKED)) {
            HashSet<String> pkgs = new HashSet<>(Arrays.asList(Pref.getString(PREF_PACKAGES_BLOCKED, "").split(";")));
            if (isChecked) {
                pkgs.add(pkg);
            } else {
                pkgs.remove(pkg);
            }
            Pref.edit().putString(PREF_PACKAGES_BLOCKED, TextUtils.join(";", pkgs)).apply();

        } else {
            Pref.edit().putString(PREF_PACKAGES_BLOCKED, pkg).apply();
        }
    }

    private boolean hasAccessGranted() {
        ContentResolver contentResolver = this.getActivity().getContentResolver();
        String enabledNotificationListeners = Settings.Secure.getString(contentResolver, SETTING_NOTIFICATION_LISTENER);
        String packageName = this.getActivity().getPackageName();
        // check to see if the enabledNotificationListeners String contains our package name
        return !(enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName));
    }

}
