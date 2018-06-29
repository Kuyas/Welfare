package com.example.android.welfare.UserDetails;

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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.welfare.DatabaseConnection.APIService;
import com.example.android.welfare.DatabaseConnection.APIUtils;
import com.example.android.welfare.DatabaseConnection.ResponseClasses.PersonalPostData;
import com.example.android.welfare.Login.LoginActivity;
import com.example.android.welfare.MainActivity;
import com.example.android.welfare.NetworkStatus;
import com.example.android.welfare.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonalDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private SharedPreferences sharedPreferences;
    private APIService personalUsingAPI;
    private String loginID;
    private String genderSelect;
    private String districtSelect;

    String date_test;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("loggedInID", "").isEmpty()) {
            //TODO: Remove the negation

            Intent loginIntent = new Intent(PersonalDetailsActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            setContentView(R.layout.activity_personal_details);

            final int position, position1;

            //DATE PICKER
            Button btn = findViewById(R.id.activity_personal_button_dob);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                    datePicker.show(getSupportFragmentManager(), "date picker");

                }
            });

            // SPINNER FOR GENDER SELECT
            final Spinner spinner = findViewById(R.id.activity_personal_details_gender_select);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activity_personal_spinner_gender, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    Context context = getApplicationContext();
                    Object text = parent.getItemAtPosition(pos);
                    if (pos > 0) {
                        Toast.makeText(context, (CharSequence) text, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                    Context context = getApplicationContext();
//            Object text =  parent.getItemAtPosition(pos);
                    Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                }
            });

            //SPINNER FOR DISTRICT SELECT
            final Spinner spinner2 = findViewById(R.id.activity_personal_details_district_select);

            List<String> district;
            String[] arrayDistrict = getResources().getStringArray(R.array.activity_personal_spinner_district);
            Arrays.sort(arrayDistrict);
            district = new ArrayList<>(Arrays.asList(arrayDistrict));
            district.add(0, "Choose District");

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, district);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner2.setAdapter(spinnerArrayAdapter);

            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos1, long id) {
                    Context context = getApplicationContext();
                    Object text = parent.getItemAtPosition(pos1);
                    if (pos1 > 0) {
                        Toast.makeText(context, (CharSequence) text, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                }
            });


            personalUsingAPI = APIUtils.getAPIService();
//            loginID = sharedPreferences.getString("loggedInID","");
            loginID = "1";


            final Button buttonNext = findViewById(R.id.button_personal_details_next);
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                        boolean flag = true;
                        TextInputEditText name = findViewById(R.id.edit_text_personal_name);
                        TextInputEditText address = findViewById(R.id.edit_text_personal_address);
                        TextInputEditText place = findViewById(R.id.edit_text_personal_place);
//                TextView date = findViewById(R.id.activity_personal_textview_date);

                        TextValidator validName = new TextValidator(name);
                        TextValidator validAddress = new TextValidator(address);
                        TextValidator validPlace = new TextValidator(place);

                        if (validName.isValid()) {
                            //write to variable
                        } else {
                            flag = false;
                            name.setError("Please Enter a Valid Name");
                        }
                        if (validAddress.isValid()) {
                            //write to variable
                        } else {
                            flag = false;
                            address.setError("Please Enter a valid Address");
                        }
                        if (validPlace.isValid()) {
                            //write to variable
                        } else {
                            place.setError("Please Enter a Valid Place");
                        }
                        if (spinner.getSelectedItem().toString().trim().equals("Choose Gender")) {
                            flag = false;
                            Toast.makeText(PersonalDetailsActivity.this, "Error. Please Select a Valid gender", Toast.LENGTH_SHORT).show();

                        } else {

                            genderSelect = spinner.getSelectedItem().toString().trim();


                        }
                        if (spinner2.getSelectedItem().toString().trim().equals("Choose District")) {
                            flag = false;
                            Toast.makeText(PersonalDetailsActivity.this, "Error. Please Select a Valid District", Toast.LENGTH_SHORT).show();
                        }else {
                            districtSelect = spinner2.getSelectedItem().toString().trim();
                        }
                        if (date_test == null) {
                            flag = false;
                            Toast.makeText(PersonalDetailsActivity.this, "Choose Date", Toast.LENGTH_SHORT).show();
                        }

//                if(date.toString().equals("dd/mm/yyyy")){
//                    flag = false;
//                    Toast.mak eText(PersonalDetailsActivity.this, "Details Not Saved", Toast.LENGTH_LONG).show();
//
//                }else {
//                    //write to variable
//                }

                        if (flag || true) {

                            personalUsingAPI.savePost(loginID, validName.returnText(),"29-03-1998", genderSelect, validAddress.returnText(),
                                    validPlace.returnText(), districtSelect).enqueue(new Callback<PersonalPostData>() {
                                @Override
                                public void onResponse(Call<PersonalPostData> call, Response<PersonalPostData> response) {
                                    long response_code = response.body().getResponseCode();
//                                    Toast.makeText(PersonalDetailsActivity.this, (int) response_code, Toast.LENGTH_LONG).show();

                                    if (true || response_code == 1) {
                                        sharedPreferences.edit().putString("loggedInID", response.body().getId());
//                                        Toast.makeText(PersonalDetailsActivity.this, "logged in ID is" + response.body().getId(), Toast.LENGTH_LONG).show();

                                        Toast.makeText(PersonalDetailsActivity.this, "Details Saved", Toast.LENGTH_LONG).show();

                                        Intent paymentDetailsIntent = new Intent(PersonalDetailsActivity.this,
                                                FamilyDetailsActivity.class);
                                        startActivity(paymentDetailsIntent);
                                    } else {


                                        Toast.makeText(PersonalDetailsActivity.this, "Request gave erroneous response", Toast.LENGTH_LONG).show();
                                    }



                                }

                                @Override
                                public void onFailure(Call<PersonalPostData> call, Throwable t) {
                                    Toast.makeText(PersonalDetailsActivity.this, "Details failed", Toast.LENGTH_LONG).show();

                                }
                            });

                        }
                    } else {
                        LinearLayout linearLayout = findViewById(R.id.layout_activity_personal_details);
                        Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                                getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                        noConnectionSnackbar.show();
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


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
        TextView textView = findViewById(R.id.activity_personal_textview_date);
        textView.setText(currentDateString);
        date_test = currentDateString;

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
                default: {}
            }
        }
    };

}
