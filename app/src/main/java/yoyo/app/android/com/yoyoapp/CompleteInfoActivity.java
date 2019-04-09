package yoyo.app.android.com.yoyoapp;

import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.squareup.picasso.Picasso;
import yoyo.app.android.com.yoyoapp.DataModels.Client;
import yoyo.app.android.com.yoyoapp.DirectSelect.AdvancedExampleCountryAdapter;
import yoyo.app.android.com.yoyoapp.DirectSelect.AdvancedExampleCountryPOJO;
import com.ramotion.directselect.DSListView;

import java.util.List;

public class CompleteInfoActivity extends AppCompatActivity {

    private Button submitInfoButton;
    private EditText firstnameEditText,lastnameEditText,phoneNumberEditText;
    private ImageView userImageview;
    private TextView emailTextview;
    private Client client;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar)
            supportActionBar.hide();

        setContentView(R.layout.fragment_complete_info);

        setupCountrySelector();
        init();


        UserSharedManager userSharedManager = new UserSharedManager(CompleteInfoActivity.this);
        client = userSharedManager.getClient();

        updateViewsSharedPref();

        setupSubmitInfo();
    }

    private void updateViewsSharedPref() {
        firstnameEditText.setText(client.getFirstName());
        lastnameEditText.setText(client.getLastName());
        emailTextview.setText(client.getEmail());
        Picasso.with(CompleteInfoActivity.this).load(client.getPicture()).into(userImageview);
    }

    private void init() {
        submitInfoButton = findViewById(R.id.button_compinfo_continue);
        firstnameEditText = findViewById(R.id.et_compinfo_firstname);
        lastnameEditText = findViewById(R.id.et_compinfo_lastname);
        phoneNumberEditText = findViewById(R.id.et_compinfo_phonenumber);
        emailTextview = findViewById(R.id.tv_compinfo_email_address);
        userImageview = findViewById(R.id.iv_compinfo_img);
        emailTextview.setKeyListener(null);
    }

    private void setupCountrySelector() {
        // Prepare dataset
        List<AdvancedExampleCountryPOJO> exampleDataSet = AdvancedExampleCountryPOJO.getExampleDataset();

        // Create adapter with our dataset
        ArrayAdapter<AdvancedExampleCountryPOJO> adapter = new AdvancedExampleCountryAdapter(
                this, R.layout.advanced_example_country_list_item, exampleDataSet);

        // Set adapter to our DSListView
        DSListView<AdvancedExampleCountryPOJO> pickerView = findViewById(R.id.ds_county_list);
        pickerView.setAdapter(adapter);
    }

    private void setupSubmitInfo() {

        submitInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstnameEditText.getText().toString().trim().isEmpty())
                {
                    firstnameEditText.setError(getString(R.string.first_name_cannot_be_empty));
                }
                if (lastnameEditText.getText().toString().trim().isEmpty())
                {
                    lastnameEditText.setError(getString(R.string.last_name_cannot_be_empty));
                }
                if (phoneNumberEditText.getText().toString().trim().isEmpty())
                {
                    phoneNumberEditText.setError(getString(R.string.phone_number_cannot_be_empty));
                }
                if (emailTextview.getText().toString().trim().isEmpty())
                {
                    emailTextview.setError(getString(R.string.email_cannot_be_empty));
                }
                startActivity(new Intent(CompleteInfoActivity.this,MainActivity.class));
            }
        });
    }
}
