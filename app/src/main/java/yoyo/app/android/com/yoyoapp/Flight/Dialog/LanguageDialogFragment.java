package yoyo.app.android.com.yoyoapp.Flight.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.Utils.LanguageSetup;
import yoyo.app.android.com.yoyoapp.R;

public class LanguageDialogFragment extends DialogFragment {
    private View view;
    private TextView englishTextView, arabicTextView, farsiTextView;
    private LanguageSetup languageSetup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.languages_dialog, container, false);

        init();
        onClick();



        return view;
    }


    private void init() {
        englishTextView = view.findViewById(R.id.tv_language_english);
        arabicTextView = view.findViewById(R.id.tv_language_arabic);
        farsiTextView = view.findViewById(R.id.tv_language_farsi);
        languageSetup = new LanguageSetup(getContext());
    }

    private void onClick() {

        englishTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                languageSetup.setLocale("en");
                dismiss();

            }
        });

        arabicTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                languageSetup.setLocale("ar");
                dismiss();

            }
        });

        farsiTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                languageSetup.setLocale("fa");
                dismiss();
            }
        });
    }
}
