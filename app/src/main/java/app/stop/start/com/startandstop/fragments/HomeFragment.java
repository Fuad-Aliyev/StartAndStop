package app.stop.start.com.startandstop.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import app.stop.start.com.startandstop.R;
import app.stop.start.com.startandstop.controller.ClickState;
import app.stop.start.com.startandstop.controller.TimeAndDate;
import app.stop.start.com.startandstop.controller.WaitDialog;
import app.stop.start.com.startandstop.database.DatabaseOperations;
import app.stop.start.com.startandstop.main.MainActivity;
import app.stop.start.com.startandstop.model.Data;
import app.stop.start.com.startandstop.model.ModelTime;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private MediaPlayer player;
    private Vibrator vibrator;
    private Notification.Builder n;
    private NotificationManager nm;
    private Data data = new Data();
    private DatabaseOperations db;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final int ID = 7;
    private WaitDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
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
    public void onPause() {
        super.onPause();
        control();
    }

    @Override
    public void onResume() {
        super.onResume();
        control();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        control();
    }

    private void init(View view) {
        for (int i = 0; i < data.getButtons().length; i++) {
            data.getButtons()[i] = (Button) view.findViewById(data.getIds()[i]);
        }
        for (int i = 0; i < data.getButtons().length; i++) {
            data.getButtons()[i].setOnClickListener(this);
        }

        dialog = new WaitDialog(getContext());
        preferences = this.getContext().getSharedPreferences("startstop", Context.MODE_PRIVATE);
        editor = preferences.edit();
        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        player = MediaPlayer.create(getContext(), R.raw.click);
        nm = (NotificationManager) getContext().
                getSystemService(getContext().NOTIFICATION_SERVICE);
    }

    private void customToastStart(View view) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_start, (ViewGroup) view.findViewById(R.id.toastStartLayout));
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    private void customToastStop(View view, String time) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_stop, (ViewGroup) view.findViewById(R.id.toastStopLayout));
        TextView value = (TextView) layout.findViewById(R.id.timeValue);
        value.setText(getContext().getResources().getString(R.string.toastResult) + " " + time
                + " " + getContext().getResources().getString(R.string.toastMinute));
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < data.getButtons().length; i++) {
            if (view.getId() == data.getButtons()[i].getId()) {
                buttonClick(data.getButtons()[i], data.getNames()[i], view);
                break;
            }
        }
    }

    private void buttonClick(final Button button, final String tag, final View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        String value = preferences.getString("value", "0");
        if (value.equals("0")) {
            alert.setMessage(getContext().getResources().getString(R.string.alertStartMessage)).
                    setPositiveButton(getContext().getResources().getString(R.string.alertYes),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    controlSettingParameters(button.getText().toString());
                                    startButton(button, tag, view);
                                }
                            }).setNegativeButton(getContext().getResources().getString(R.string.alertNo),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    }).create().show();
        } else {
            alert.setMessage(getContext().getResources().getString(R.string.alertStopMessage)).
                    setPositiveButton(getContext().getResources().getString(R.string.alertYes),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    addNote(tag, button, view);
                                }
                            }).setNegativeButton(getContext().getResources().getString(R.string.alertNo),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    }).create().show();
        }
    }

    private void control() {
        String tag = preferences.getString("tag", "name");
        String value = preferences.getString("value", "0");
        if (value.equals("1")) {
            for (int i = 0; i < data.getButtons().length; i++) {
                if (tag.equals(data.getNames()[i])) {
                    data.getButtons()[i].setBackgroundColor(Color.parseColor("#cd201f"));
                    disableButton(data.getButtons()[i]);
                    break;
                }
            }
        } else {
            resetColors();
            enableButtons();
        }
    }

    private void resetColors() {
        for (int i = 0; i < data.getButtons().length; i++) {
            data.getButtons()[i].setBackgroundColor(Color.parseColor(data.getColors()[i]));
        }
    }

    private void enableButtons() {
        for (int i = 0; i < data.getButtons().length; i++) {
            data.getButtons()[i].setEnabled(true);
        }
    }

    private void disableButton(Button button) {
        for (int i = 0; i < data.getButtons().length; i++) {
            if (button.getId() == data.getButtons()[i].getId()) {
                continue;
            }
            data.getButtons()[i].setEnabled(false);
        }
    }

    private void startButton(Button button, String tag, View view) {
        customToastStart(view);
        editor.putString("tag", tag).commit();
        editor.putString("value", "1").commit();
        editor.putLong("startTime", TimeAndDate.getTime()).commit();
        editor.putString("startDate", TimeAndDate.getDate()).commit();
        button.setBackgroundColor(Color.parseColor("#cd201f"));
        disableButton(button);
    }

    private void stopButton(String tag, String note, Button button, View view) {
        nm.cancel(ID);
        editor.putString("tag", tag).commit();
        editor.putString("value", "0").commit();
        editor.putLong("stopTime", TimeAndDate.getTime()).commit();
        editor.putString("stopDate", TimeAndDate.getDate()).commit();
        enableButtons();
        resetColors();
        addModel(getModel(note, button, view));
        editor.putLong("startTime", 0).putLong("stopTime", 0).commit();
        editor.putString("startDate", "").putString("stopDate", "").commit();
    }

    private void addModel(ModelTime model) {
        db = new DatabaseOperations(getContext());
        if (db.hasName(model)) {
            db.insertTime(model);
            db.setUpdateTimeAndDate(model.getTime(), model.getName(), model.getStopDate());
            db.getUpdateTimeAndDate();
        } else {
            db.insertTotalTime(model);
            db.insertTime(model);
        }
    }

    private ModelTime getModel(String note, Button button, View view) {
        long startTime = preferences.getLong("startTime", 0);
        long stopTime = preferences.getLong("stopTime", 0);
        String startDate = preferences.getString("startDate", "");
        String stopDate = preferences.getString("stopDate", "");
        String diffTime = TimeAndDate.differenceTime(startTime, stopTime);
        customToastStop(view, diffTime);
        ModelTime model = new ModelTime(button.getText().toString(), diffTime, startDate, stopDate, note);
        return model;
    }

    private void addNote(final String tag, final Button button, final View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText text = new EditText(getContext());
        alert.setView(text);
        alert.setMessage(getContext().getResources().getString(R.string.alertAddNote)).setCancelable(false).
                setPositiveButton(getContext().getResources().getString(R.string.alertSave),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                stopButton(tag, text.getText().toString(), button, view);
                            }
                        }).setNegativeButton(getContext().getResources().getString(R.string.alertCancel)
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        stopButton(tag, "", button, view);
                    }
                }).create().show();
    }


    private void controlSettingParameters(String tag) {
        SharedPreferences pref = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (pref.getBoolean("notification", true)) {
            notification(tag);
        }
        if (pref.getBoolean("vibrate", true)) {
            vibrate();
        }
        if (pref.getBoolean("sound", true)) {
            sound();
        }
    }

    private void notification(String tag) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        n = new Notification.Builder(getContext());
        n.setContentTitle(getContext().getResources().getString(R.string.app_name));
        n.setContentText(tag);
        n.setOngoing(true);
        n.setSmallIcon(R.mipmap.icon);
        n.setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon));
        n.setContentIntent(pendingIntent);
        nm.notify(ID, n.build());
    }

    private void vibrate() {
        vibrator.vibrate(250);
    }

    private void sound() {
        player.start();
    }

}