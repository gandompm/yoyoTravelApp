package yoyo.app.android.com.yoyoapp.Flight;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import yoyo.app.android.com.yoyoapp.R;


public class AboutFragment extends Fragment {

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
                fragmentManager.popBackStack();
            }
        });
    }

}
