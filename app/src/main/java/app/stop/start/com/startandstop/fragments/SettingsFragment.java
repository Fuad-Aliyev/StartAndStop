package app.stop.start.com.startandstop.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import app.stop.start.com.startandstop.R;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private Switch swtSound, swtVibrate, swtNotify;
    private Button btnBack;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        init(rootView);
        control();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        control();
    }

    @Override
    public void onResume() {
        super.onResume();
        control();
    }

    @Override
    public void onPause() {
        super.onPause();
        control();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        control();
    }

    private void init(View view) {
        swtSound = (Switch) view.findViewById(R.id.switchSound);
        swtVibrate = (Switch) view.findViewById(R.id.switchVibrate);
        swtNotify = (Switch) view.findViewById(R.id.switchNotification);
        btnBack = (Button) view.findViewById(R.id.settingBtnBack);
        swtSound.setOnClickListener(this);
        swtVibrate.setOnClickListener(this);
        swtNotify.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        preferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switchSound:
                if (swtSound.isChecked()) {
                    editor.putBoolean("sound", true).commit();
                } else {
                    editor.putBoolean("sound", false).commit();
                }
                break;
            case R.id.switchVibrate:
                if (swtVibrate.isChecked()) {
                    editor.putBoolean("vibrate", true).commit();
                } else {
                    editor.putBoolean("vibrate", false).commit();
                }
                break;
            case R.id.switchNotification:
                if (swtNotify.isChecked()) {
                    editor.putBoolean("notification", true).commit();
                } else {
                    editor.putBoolean("notification", false).commit();
                }
                break;
            case R.id.settingBtnBack:
                goToTimeFragment();
                break;
        }
    }

    private void control() {
        if (preferences.getBoolean("notification", true)) {
            swtNotify.setChecked(true);
        } else {
            swtNotify.setChecked(false);
        }
        if (preferences.getBoolean("vibrate", true)) {
            swtVibrate.setChecked(true);
        } else {
            swtVibrate.setChecked(false);
        }
        if (preferences.getBoolean("sound", true)) {
            swtSound.setChecked(true);
        } else {
            swtSound.setChecked(false);
        }
    }

    private void goToTimeFragment() {
        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().
                beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new HomeFragment());
        fragmentTransaction.commit();
    }

}
