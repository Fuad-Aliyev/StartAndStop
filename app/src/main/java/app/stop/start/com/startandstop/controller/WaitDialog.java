package app.stop.start.com.startandstop.controller;

import android.app.ProgressDialog;
import android.content.Context;

import app.stop.start.com.startandstop.R;

public class WaitDialog {

    private ProgressDialog dialog;
    private Context context;

    public WaitDialog(Context context){
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    public void startDialog(){
        dialog.setMessage(context.getResources().getString(R.string.dialogWait));
        dialog.show();
    }

    public void stopDialog(){
        dialog.dismiss();
    }

}
