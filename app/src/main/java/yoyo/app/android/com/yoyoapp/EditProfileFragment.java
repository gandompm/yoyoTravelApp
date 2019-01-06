package yoyo.app.android.com.yoyoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.strictmode.CleartextNetworkViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import yoyo.app.android.com.yoyoapp.DataModels.Client;
import yoyo.app.android.com.yoyoapp.DirectSelect.AdvancedExampleCountryAdapter;
import yoyo.app.android.com.yoyoapp.DirectSelect.AdvancedExampleCountryPOJO;
import com.ramotion.directselect.DSListView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class EditProfileFragment extends Fragment {

    private Button submitInfoButton;
    private EditText firstnameEditText,phoneNumberEditText,lastnameEditText,emailEditText;
    private ImageView userImageview;
    private Uri imageUri = null;
    private Client client;
    private View view;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);


        setupCountrySelector();
        init();
        setupSharedPref();
        selectImage();
//        setupSubmitInfo();

        return view;
    }

    private void init() {
        firstnameEditText = view.findViewById(R.id.et_edit_profile_firstname);
        lastnameEditText = view.findViewById(R.id.et_edit_profile_lastname);
       // phoneNumberEditText = view.findViewById(R.id.et_edit_profile_lastname);
        emailEditText = view.findViewById(R.id.et_edit_profile_email);
        userImageview = view.findViewById(R.id.iv_edit_profile_img);
    }

    private void setupSharedPref() {
        UserSharedManager userSharedManager = new UserSharedManager(getContext());
        client = userSharedManager.getClient();

        if (client!= null && client.getFirstName()!= "")
        {
            firstnameEditText.setText(client.getFirstName());
            lastnameEditText.setText(client.getLastName());
            emailEditText.setText(client.getEmail());
            Picasso.with(getContext()).load(client.getPicture()).into(userImageview);
        }
    }
    private void setupCountrySelector() {
        // Prepare dataset
        List<AdvancedExampleCountryPOJO> exampleDataSet = AdvancedExampleCountryPOJO.getExampleDataset();

        // Create adapter with our dataset
        ArrayAdapter<AdvancedExampleCountryPOJO> adapter = new AdvancedExampleCountryAdapter(
                getContext(), R.layout.advanced_example_country_list_item, exampleDataSet);

        // Set adapter to our DSListView
        DSListView<AdvancedExampleCountryPOJO> pickerView = view.findViewById(R.id.ds_county_list);
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
                if (phoneNumberEditText.getText().toString().trim().isEmpty())
                {
                    phoneNumberEditText.setError("phone number cannot be empty");
                }
                if (emailEditText.getText().toString().trim().isEmpty())
                {
                    emailEditText.setError("email cannot be empty");
                }
                startActivity(new Intent(getContext(),MainActivity.class));
            }
        });
    }
    private void selectImage() {
        userImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        cropingFunc();
                    }
                } else {
                    cropingFunc();
                }
            }
        });
    }

    private void cropingFunc() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setMinCropWindowSize(512, 512)
                .start(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                imageUri = result.getUri();
                userImageview.setImageURI(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }
}

