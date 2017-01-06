package app.stop.start.com.startandstop.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import app.stop.start.com.startandstop.R;
import app.stop.start.com.startandstop.database.DatabaseOperations;
import app.stop.start.com.startandstop.model.ModelTime;
import app.stop.start.com.startandstop.view.adapter.ListViewAdapterTime;

public class StatisticsFragmentTime extends Fragment implements View.OnClickListener {

    private static String name;
    public static ListView listView;
    private ArrayList<ModelTime> model;
    private DatabaseOperations db;
    private FragmentTransaction fragmentTransaction;
    private Button btnbBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_statictics_in, container, false);
        init(rootView);
        model = getNameList();
        ListViewAdapterTime adapter = new ListViewAdapterTime(rootView.getContext(), model);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return rootView;
    }

    private void init(View view) {
        listView = (ListView) view.findViewById(R.id.listIn);
        btnbBack = (Button) view.findViewById(R.id.btnBack);
        btnbBack.setOnClickListener(this);
        db = new DatabaseOperations(view.getRootView().getContext());
    }

    public ArrayList<ModelTime> getNameList() {
        return db.getAllTime(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    private void goToTimeFragment() {
        fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().
                beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new StatisticsFragmentTotalTime());
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                goToTimeFragment();
                break;
        }
    }
}
