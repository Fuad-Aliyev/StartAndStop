package app.stop.start.com.startandstop.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import app.stop.start.com.startandstop.R;

public class AboutFragment extends Fragment implements View.OnClickListener {

    private TextView txtFacebook, txtLinkedin, txtEmail;
    private Button btnShare;

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        txtFacebook = (TextView) view.findViewById(R.id.txtFacebook);
        txtLinkedin = (TextView) view.findViewById(R.id.txtLinkedin);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        btnShare = (Button) view.findViewById(R.id.aboutBtnShare);
        txtFacebook.setOnClickListener(this);
        txtLinkedin.setOnClickListener(this);
        txtEmail.setOnClickListener(this);
        btnShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtFacebook:
                startActivity(openInFacebook(getContext()));
                break;
            case R.id.txtLinkedin:
                startActivity(openInLinkedin(getContext()));
                break;
            case R.id.txtEmail:
                openInEmail();
                break;
            case R.id.aboutBtnShare:
                goToTimeFragment();
                break;
        }
    }

    private Intent openInFacebook(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100002665617709"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/senan.recebov"));
        }
    }

    private Intent openInLinkedin(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.linkedin.android", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/senanirecebov"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/senanirecebov"));
        }
    }

    private void openInEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "senanrecebov3@gmail.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Start and Stop");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, ""));
    }

    private void goToTimeFragment() {
        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().
                beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new HomeFragment());
        fragmentTransaction.commit();
    }

}
