package yoyo.app.android.com.yoyoapp.Flight.Profile;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentManager;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.R;


public class AboutFragment extends BaseFragment {

    private ImageView backImageView;
    TextView phonenumTextview, phonenumTextview2;
    private View view;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);


        init();
        onClick();

        return view;
    }

    private void init() {

        fragmentManager = getFragmentManager();
        backImageView = view.findViewById(R.id.iv_about_back);
        phonenumTextview = view.findViewById(R.id.tv_about_phone1);
        phonenumTextview2 = view.findViewById(R.id.tv_about_phone2);
    }

    private void onClick() {

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        phonenumTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+982128427330"));
                startActivity(callIntent);

            }
        });

        phonenumTextview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:+989109040384"));
                startActivity(callIntent);

            }
        });

    }

}
