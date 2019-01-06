package yoyo.app.android.com.yoyoapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;


public class ReportFragment extends Fragment {

    private static final String TAG = MainActivity.class.getName();
    private Button btnRequest;
    private JsonObjectRequest jsonObjectRequest;
    private ApiService apiService;

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report,container,false);

        init();

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.sendAndRequestResponse(new ApiService.OnRequest() {
                    @Override
                    public void onSend(String s) {

                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return view;
    }

    private void init() {
        btnRequest = (Button) view.findViewById(R.id.buttonRequest);
        apiService = new ApiService(getContext());
    }

}
