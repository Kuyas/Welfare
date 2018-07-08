package com.example.android.welfare.userdetails;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.example.android.welfare.databaseconnection.DisplayErrorMessage;
import com.example.android.welfare.databaseconnection.responseclasses.PersonalData;
import com.example.android.welfare.databaseconnection.responseclasses.ResponseData;
import com.example.android.welfare.login.LoginActivity;
import com.example.android.welfare.MainActivity;
import com.example.android.welfare.NetworkStatus;
import com.example.android.welfare.R;
import com.example.android.welfare.userdetails.familydetails.FamilyDetailsActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonalDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String cacheDataFile = "personaldatacache.data";

    private SharedPreferences sharedPreferences;
    private APIService personalUsingAPI;
    private String genderSelect;
    private String districtSelect;

    private TextInputEditText name;
    private TextInputEditText address;
    private TextInputEditText place;
    private String[] arrayDistrict;
    private ArrayAdapter<CharSequence> genderAdapter;
    private CheckBox editableCheck;
    private Button buttonDob;

    private Spinner districtSpinner;
    private Spinner genderSpinner;

    AlterView alterView;

    String date_test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("loggedInID", "").isEmpty()) {
            Intent loginIntent = new Intent(PersonalDetailsActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            setContentView(R.layout.activity_personal_details);

            //DATE PICKER
            buttonDob = findViewById(R.id.activity_personal_button_dob);
            buttonDob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                    datePicker.show(getSupportFragmentManager(), "date picker");

                }
            });

            // SPINNER FOR GENDER SELECT
            genderSpinner = findViewById(R.id.activity_personal_details_gender_select);
            genderAdapter = ArrayAdapter.createFromResource(this,
                    R.array.activity_personal_spinner_gender, android.R.layout.simple_spinner_item);
            genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            genderSpinner.setAdapter(genderAdapter);

            genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    Context context = getApplicationContext();
                    Object text = parent.getItemAtPosition(pos);
                    if (pos > 0) {
//                        Toast.makeText(context, (CharSequence) text, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                    Context context = getApplicationContext();
//            Object text =  parent.getItemAtPosition(pos);
//                    Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                }
            });

            //SPINNER FOR DISTRICT SELECT
            districtSpinner = findViewById(R.id.activity_personal_details_district_select);

            alterView = new AlterView();

            List<String> district;
            arrayDistrict = getResources().getStringArray(R.array.activity_personal_spinner_district);
            Arrays.sort(arrayDistrict);
            district = new ArrayList<>(Arrays.asList(arrayDistrict));
            district.add(0, getString(R.string.activity_personal_details_choose_district));

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, district);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            districtSpinner.setAdapter(spinnerArrayAdapter);

            districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos1, long id) {
                    Context context = getApplicationContext();
                    Object text = parent.getItemAtPosition(pos1);
                    if (pos1 > 0) {
//                        Toast.makeText(context, (CharSequence) text, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                }
            });

            personalUsingAPI = APIUtils.getAPIService();
            name = findViewById(R.id.edit_text_personal_name);
            address = findViewById(R.id.edit_text_personal_address);
            place = findViewById(R.id.edit_text_personal_place);
            editableCheck = findViewById(R.id.checkbox_personal_details_editable);

            fillWithCache();
            disableEdit();

            editableCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editableCheck.isChecked()) {
                        allowEdit();
                    } else {
                        disableEdit();
                    }
                }
            });

            final Button buttonNext = findViewById(R.id.button_personal_details_next);
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editableCheck.isChecked()) {
                        if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                            boolean flag = true;
                            TextValidator validName = new TextValidator(name);
                            TextValidator validAddress = new TextValidator(address);
                            TextValidator validPlace = new TextValidator(place);

                            if (validName.isValid()) {
                                //write to variable
                            } else {
                                flag = false;
                                name.setError(getString(R.string.activity_personal_details_invalid_name));
                            }
                            if (validAddress.isValid()) {
                                //write to variable
                            } else {
                                flag = false;
                                address.setError(getString(R.string.activity_personal_details_invalid_address));
                            }
                            if (validPlace.isValid()) {
                                //write to variable
                            } else {
                                place.setError(getString(R.string.activity_personal_details_invalid_place));
                            }
                            if (genderSpinner.getSelectedItem().toString().trim().equals(
                                    getString(R.string.activity_personal_details_choose_gender))) {
                                flag = false;
                                Toast.makeText(PersonalDetailsActivity.this,
                                        getString(R.string.activity_personal_details_invalid_gender),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                genderSelect = genderSpinner.getSelectedItem().toString().trim();
                            }
                            if (districtSpinner.getSelectedItem().toString().trim().equals(
                                    getString(R.string.activity_personal_details_choose_district))) {
                                flag = false;
                                Toast.makeText(PersonalDetailsActivity.this, getString(
                                        R.string.activity_personal_details_invalid_district),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                districtSelect = districtSpinner.getSelectedItem().toString().trim();
                            }
                            if (date_test == null) {
                                flag = false;
                                Toast.makeText(PersonalDetailsActivity.this, getString(
                                        R.string.activity_personal_details_invalid_dob),
                                        Toast.LENGTH_SHORT).show();
                            }

                            if (flag) {
                                personalUsingAPI.savePersonal(sharedPreferences.getString(
                                        "loggedInID", ""), validName.returnText(),
                                        date_test, genderSelect, validAddress.returnText(),
                                        validPlace.returnText(), districtSelect).enqueue(
                                                new Callback<ResponseData>() {
                                    @Override
                                    public void onResponse(Call<ResponseData> call,
                                                           Response<ResponseData> response) {
                                        int response_code = response.body().getResponseCode();
                                        if (response_code == 200) {
                                            Toast.makeText(PersonalDetailsActivity.this,
                                                    getString(R.string.details_saved_confirmation),
                                                    Toast.LENGTH_LONG).show();

                                            nextActivity();
                                        } else {
                                            Toast.makeText(PersonalDetailsActivity.this,
                                                    DisplayErrorMessage.returnErrorMessage(response_code),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseData> call, Throwable t) {
                                        Toast.makeText(PersonalDetailsActivity.this,
                                                getString(R.string.details_saved_failed),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        } else {
                            LinearLayout linearLayout = findViewById(R.id.layout_activity_personal_details);
                            Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                                    getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                            noConnectionSnackbar.show();
                        }
                    } else {
                        nextActivity();
                    }

                }
            });


            final Toolbar toolbar = findViewById(R.id.activity_toolbar);
            toolbar.setTitle(getString(R.string.activity_personal_heading));
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(getDrawable(R.drawable.ic_arrow_back_black_24dp));

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });


            final Button homeButton = findViewById(R.id.activity_button_home);
            homeButton.setOnClickListener(onClickListener);

        }
    }

    public void nextActivity() {
        Intent next = new Intent(PersonalDetailsActivity.this, FamilyDetailsActivity.class);
        startActivity(next);
    }

    public void disableEdit() {
        alterView.disableTextInput(name);
        alterView.disableTextInput(address);
        alterView.disableTextInput(place);

        alterView.disableButton(buttonDob);

        alterView.disableSpinner(genderSpinner);
        alterView.disableSpinner(districtSpinner);
    }

    public void allowEdit() {
        alterView.enableTextInput(name);
        alterView.enableTextInput(place);
        alterView.enableTextInput(address);
        alterView.enableButton(buttonDob);
        alterView.enableSpinner(districtSpinner);
        alterView.enableSpinner(genderSpinner);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = new SimpleDateFormat("dd-MM-yyyy",
                getResources().getConfiguration().locale).format(c.getTime());
        TextView textView = findViewById(R.id.activity_personal_textview_date);
        textView.setText(currentDateString);
        date_test = currentDateString;

    }

    public void getCacheData() {
        APIService storePersonalData = APIUtils.getAPIService();
        storePersonalData.getPersonalData(sharedPreferences.getString("mobile_number", ""),
                sharedPreferences.getString("password", "")).enqueue(new Callback<PersonalData>() {
            @Override
            public void onResponse(Call<PersonalData> call, Response<PersonalData> response) {
                try {
                    int response_code = response.body().getResponseCode();
                    if (response_code == 200) {
                        File cache = new File(getCacheDir(), cacheDataFile);
                        ObjectOutputStream cacheWriter = new ObjectOutputStream(new FileOutputStream(cache));
                        cacheWriter.writeObject(response.body());
                        cacheWriter.close();
                        fillWithCache();
                    }
                } catch (Exception e) {}
            }

            @Override
            public void onFailure(Call<PersonalData> call, Throwable t) {
                System.out.println(getString(R.string.request_failed));
            }
        });
    }

    public void fillWithCache() {
        try {
            ObjectInputStream cacheReader = new ObjectInputStream(new FileInputStream(
                    getCacheDir() + File.separator + cacheDataFile));
            PersonalData cached = (PersonalData) cacheReader.readObject();
            name.setText(cached.getName());
            address.setText(cached.getAddress());
            place.setText(cached.getPlace());
            for (int i = 0; i < arrayDistrict.length; i++) {
                if (arrayDistrict[i].equals(cached.getDistrict())) {
                    districtSpinner.setSelection(i);
                    break;
                }
            }

            for (int i = 0; i < genderAdapter.getCount(); i++) {
                if (genderAdapter.getItem(i).equals(cached.getGender())) {
                    genderSpinner.setSelection(i);
                    break;
                }
            }

            // TODO: fill date picker fragment with previously selected value
        } catch (IOException | ClassNotFoundException e) {
            getCacheData();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.activity_button_home): {
                    Intent homeIntent = new Intent(PersonalDetailsActivity.this, MainActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);
                    overridePendingTransition(R.anim.slide_left_to_right, R.anim.slide_right_to_left);
                    break;
                }
                default:
                    break;
            }
        }
    };

}
