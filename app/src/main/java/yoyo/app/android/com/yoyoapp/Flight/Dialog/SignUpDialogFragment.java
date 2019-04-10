package yoyo.app.android.com.yoyoapp.Flight.Dialog;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.SignUpSignInActivity;
import yoyo.app.android.com.yoyoapp.R;


public class SignUpDialogFragment extends DialogFragment {

    private Button negativeButton, positiveButton;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_dialog, container, false);

        negativeButton = view.findViewById(R.id.button_dialog_no);
        positiveButton = view.findViewById(R.id.button_dialog_yes);


        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
//                FlightDetailsFragment flightDetailsFragment = (FlightDetailsFragment) getFragmentManager().findFragmentByTag("details");
//
//                // Check if the tab fragment is available
//                if (flightDetailsFragment != null) {
//
//                    // Call your method in the TabFragment
//                    flightDetailsFragment.goToBookingActivity();
//                }
            }
        });
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
                startActivity(new Intent(getContext(), SignUpSignInActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_up,  R.anim.no_animation);
            }
        });


        return view;
    }

}
