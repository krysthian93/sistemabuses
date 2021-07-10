package tracking.com.trackingandroid.apps.record_tour.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import tracking.com.trackingandroid.R;

public class DialogLoading {

    private Activity activity;
    private AlertDialog dialog;

    public DialogLoading(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_loading, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void dismissLoadingDialog() {
        dialog.dismiss();
    }
}
