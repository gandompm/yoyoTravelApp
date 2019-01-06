package yoyo.app.android.com.yoyoapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import yoyo.app.android.com.yoyoapp.DataModels.Client;
import yoyo.app.android.com.yoyoapp.DirectSelect.AdvancedExampleCountryAdapter;
import yoyo.app.android.com.yoyoapp.DirectSelect.AdvancedExampleCountryPOJO;
import com.ramotion.directselect.DSListView;

import java.util.List;

public class CompleteInfoActivity extends AppCompatActivity {

    private Button submitInfoButton;
    private EditText firstnameEditText, emailEditText,lastnameEditText,phoneNumberEditText;
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
        emailEditText.setText(client.getEmail());
    }

    private void init() {
        submitInfoButton = findViewById(R.id.button_compinfo_submit);
        firstnameEditText = findViewById(R.id.et_compinfo_firstname);
        lastnameEditText = findViewById(R.id.et_compinfo_lastname);
        //phoneNumberEditText = findViewById(R.id.et_compinfo_phonenumber);
        emailEditText = findViewById(R.id.et_compinfo_email);
        emailEditText.setKeyListener(null);
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
                    firstnameEditText.setError("first name cannot be empty");
                }
                if (lastnameEditText.getText().toString().trim().isEmpty())
                {
                    lastnameEditText.setError("last name cannot be empty");
                }
//                if (phoneNumberEditText.getText().toString().trim().isEmpty())
//                {
//                    phoneNumberEditText.setError("phone number cannot be empty");
//                }
                if (emailEditText.getText().toString().trim().isEmpty())
                {
                    emailEditText.setError("email cannot be empty");
                }
                startActivity(new Intent(CompleteInfoActivity.this,MainActivity.class));
            }
        });
    }
}
