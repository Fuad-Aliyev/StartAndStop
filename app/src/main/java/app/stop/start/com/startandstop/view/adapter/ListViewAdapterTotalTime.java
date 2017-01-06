package app.stop.start.com.startandstop.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import app.stop.start.com.startandstop.R;
import app.stop.start.com.startandstop.database.DatabaseOperations;
import app.stop.start.com.startandstop.fragments.StatisticsFragmentTime;
import app.stop.start.com.startandstop.fragments.StatisticsFragmentTotalTime;
import app.stop.start.com.startandstop.model.ModelTime;

public class ListViewAdapterTotalTime extends BaseAdapter {

    private List<ModelTime> items;
    private LayoutInflater inflater;
    private ListViewAdapterTotalTime adapter;
    private DatabaseOperations db;
    private FragmentTransaction fragmentTransaction;
    private Context context;

    public ListViewAdapterTotalTime(Context context, List<ModelTime> items) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
        db = new DatabaseOperations(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, final View rootView, ViewGroup viewGroup) {
        final View view;
        final ModelTime model = items.get(i);

        view = inflater.inflate(R.layout.list, null);
        final TextView name = (TextView) view.findViewById(R.id.name);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView startDate = (TextView) view.findViewById(R.id.startDate);
        TextView stopDate = (TextView) view.findViewById(R.id.stopDate);
        final ImageButton btnDelete = (ImageButton) view.findViewById(R.id.btnRemove);

        time.setText(model.getTime());
        name.setText(model.getName());
        startDate.setText(model.getStartDate());
        stopDate.setText(model.getStopDate());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTimeFragment(view);
                String txtName = name.getText().toString();
                new StatisticsFragmentTime().setName(txtName);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getRootView().getContext());
                alert.setMessage(context.getResources().getString(R.string.alertDeleteMessage)).
                        setTitle(context.getResources().getString(R.string.alertDelete)).
                        setPositiveButton(context.getResources().getString(R.string.alertYes),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        db.deleteTotalTime(model);
                                        items.remove(model);
                                        adapter = new ListViewAdapterTotalTime(view.getRootView().getContext(), items);
                                        StatisticsFragmentTotalTime.listView.setAdapter(adapter);
                                    }
                                }).setNegativeButton(context.getResources().getString(R.string.alertNo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        }).create().show();
            }
        });

        return view;
    }

    private void goToTimeFragment(View view) {
        fragmentTransaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().
                beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new StatisticsFragmentTime());
        fragmentTransaction.commit();
    }

}
