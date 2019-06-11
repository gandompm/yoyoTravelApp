package yoyo.app.android.com.yoyoapp.Trip.dialog;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.booking.BookingActivity;


public class ExitDialogFragment extends DialogFragment {

    private Button negativeButton, positiveButton;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exit_dialog, container, false);

        negativeButton = view.findViewById(R.id.button_exitdialog_no);
        positiveButton = view.findViewById(R.id.button_exitdialog_yes);


        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
                ((BookingActivity)getContext()).finish();
                getActivity().overridePendingTransition(0,  0);
            }
        });


        return view;
    }

}
