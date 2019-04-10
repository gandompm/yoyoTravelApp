package yoyo.app.android.com.yoyoapp.Flight.Dialog;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import es.dmoral.toasty.Toasty;
import yoyo.app.android.com.yoyoapp.R;

public class UpdateDialogFragment extends DialogFragment {

    private String message;
    private TextView messageTextview;
    private Button positiveButton, negativButton;
    private View view;
    private DownloadManager downloadManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.update_dialog, container, false);

        init();
        onClick();

        messageTextview.setText(message);

        return view;
    }

    private void onClick() {
        // download the latest app version
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("http://yoyoapp.ir/fa/download/android");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                long refrences = downloadManager.enqueue(request);

                Toasty.info(getContext(),getString(R.string.downloading)).show();

                dismiss();
            }
        });
        negativButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void init() {
        messageTextview = view.findViewById(R.id.tv_update_dialog_message);
        positiveButton = view.findViewById(R.id.button_update_dialog_update);
        negativButton = view.findViewById(R.id.button_update_dialog_no);
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
