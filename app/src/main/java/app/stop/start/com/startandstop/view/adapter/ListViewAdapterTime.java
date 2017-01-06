package app.stop.start.com.startandstop.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import app.stop.start.com.startandstop.model.ModelTime;

/**
 * Created by Senani Recebov on 12/30/2016.
 */

public class ListViewAdapterTime extends BaseAdapter {

    private List<ModelTime> items;
    private LayoutInflater inflater;
    private DatabaseOperations db;
    private Context context;

    public ListViewAdapterTime() {
    }

    public ListViewAdapterTime(Context context, List<ModelTime> items) {
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
    public View getView(int i, View rootView, ViewGroup viewGroup) {
        final View view;
        final ModelTime model = items.get(i);

        view = inflater.inflate(R.layout.list_in, null);
        TextView name = (TextView) view.findViewById(R.id.txtListInName);
        TextView note = (TextView) view.findViewById(R.id.note);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView startDate = (TextView) view.findViewById(R.id.startDate);
        TextView stopDate = (TextView) view.findViewById(R.id.stopDate);

        name.setText(model.getName());
        time.setText(model.getTime());
        note.setText(model.getNote());
        startDate.setText(model.getStartDate());
        stopDate.setText(model.getStopDate());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                String txtNote = db.getNote(model);
                alert.setTitle(context.getResources().getString(R.string.alertShowNote)).
                        setMessage(txtNote).create().show();
            }
        });

        return view;
    }


}
