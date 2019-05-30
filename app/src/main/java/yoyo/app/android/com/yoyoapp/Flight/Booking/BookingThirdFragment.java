package yoyo.app.android.com.yoyoapp.Flight.Booking;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import yoyo.app.android.com.yoyoapp.Flight.Utils.Tools;
import yoyo.app.android.com.yoyoapp.R;

public class BookingThirdFragment extends Fragment {

    private int result;
    private TextView firstMessageTextview, secondMessageTextview, voucherTextview, voucherNumberTextview;
    private ImageView resultImageview ,voucherImageview;
    private ImageButton copyVoucherButton;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_booking_third, container, false);

        init();

        Bundle bundle = getArguments();
        result = Integer.parseInt(bundle.getString("status"));

        // if the booking was successful, setup views
        if (result >= 100)
        {
            voucherNumberTextview.setText(BookingActivity.voucherNumber);
            copyVoucherButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Tools.copyToClipboard(getContext(), BookingActivity.voucherNumber);
                }
            });
        }
        else
        {
            voucherImageview.setVisibility(View.GONE);
            voucherNumberTextview.setVisibility(View.GONE);
            voucherTextview.setVisibility(View.GONE);
            copyVoucherButton.setVisibility(View.GONE);
            resultImageview.setImageDrawable(getResources().getDrawable(R.drawable.fail_cross));
            firstMessageTextview.setText(getString(R.string.failed));
            secondMessageTextview.setText(getString(R.string.please_try_again));
        }

        return view;
    }

    private void init() {
        firstMessageTextview = view.findViewById(R.id.iv_booking_third_message_1);
        secondMessageTextview = view.findViewById(R.id.iv_booking_third_message_2);
        resultImageview = view.findViewById(R.id.iv_booking_third);
        voucherNumberTextview = view.findViewById(R.id.tv_booking_third_voucher_number);
        voucherTextview = view.findViewById(R.id.tv_booking_third_voucher);
        copyVoucherButton = view.findViewById(R.id.bt_booking_third_copy_code);
        voucherImageview = view.findViewById(R.id.iv_booking_third_voucher_number);
    }

}
