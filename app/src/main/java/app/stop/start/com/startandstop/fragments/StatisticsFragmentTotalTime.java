package app.stop.start.com.startandstop.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import app.stop.start.com.startandstop.R;
import app.stop.start.com.startandstop.database.DatabaseOperations;
import app.stop.start.com.startandstop.model.ModelTime;
import app.stop.start.com.startandstop.view.adapter.ListViewAdapterTime;
import app.stop.start.com.startandstop.view.adapter.ListViewAdapterTotalTime;

public class StatisticsFragmentTotalTime extends Fragment implements View.OnClickListener {

    private Button btnDay, btnMonth;
    public static ListView listView;
    private ArrayList<ModelTime> model;
    private DatabaseOperations db;
    private AdView adView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
        init(rootView);
        loadAd(rootView);
        model = db.getAllTotalTime();
        ListViewAdapterTotalTime adapter = new ListViewAdapterTotalTime(rootView.getContext(), model);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return rootView;
    }

    private void init(View view) {
        listView = (ListView) view.findViewById(R.id.list);
        btnDay = (Button) view.findViewById(R.id.btnDay);
        btnMonth = (Button) view.findViewById(R.id.btnMonth);
        btnDay.setOnClickListener(this);
        btnMonth.setOnClickListener(this);
        db = new DatabaseOperations(view.getRootView().getContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDay:
                changeColor(btnDay);
                model = db.categoryByDay();
                show(model);
                break;
            case R.id.btnMonth:
                changeColor(btnMonth);
                model = db.categoryByMonth();
                show(model);
                break;
        }
    }

    private void show(ArrayList<ModelTime> model) {
        ListViewAdapterTime adapter = new ListViewAdapterTime(getContext(), model);
        listView.setAdapter(adapter);
    }

    private void changeColor(Button button) {
        switch (button.getId()) {
            case R.id.btnDay:
                btnDay.setBackgroundColor(Color.parseColor("#3995c2"));
                btnMonth.setBackgroundColor(Color.parseColor("#395563"));
                break;
            case R.id.btnMonth:
                btnDay.setBackgroundColor(Color.parseColor("#395563"));
                btnMonth.setBackgroundColor(Color.parseColor("#3995c2"));
                break;
        }
    }

    private void loadAd(View view) {
        adView = (AdView) view.findViewById(R.id.ads);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);


    }
}
