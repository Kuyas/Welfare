package com.example.android.welfare.userdetails.familydetails;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.example.android.welfare.databaseconnection.responseclasses.FamilyData;
import com.example.android.welfare.databaseconnection.responseclasses.PersonalData;
import com.example.android.welfare.login.LoginActivity;
import com.example.android.welfare.MainActivity;
import com.example.android.welfare.NetworkStatus;
import com.example.android.welfare.R;
import com.example.android.welfare.userdetails.TradingDetailsActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyDetailsActivity extends AppCompatActivity implements FamilyMemberDialogFragment.AddButtonDialogListener {
    private final String cacheDataFile = "familydatacache.data";
    private SharedPreferences sharedPreferences;

    private RecyclerView recyclerView;
    private FamilyAdapter familyAdapter;

    private List<FamilyModel> familyModelList;
    private FamilyMemberDialogFragment dialogFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("loggedInID", "").isEmpty()) {
            Intent loginIntent = new Intent(FamilyDetailsActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            setContentView(R.layout.activity_family_details);

            familyModelList = new ArrayList<>();

            final Toolbar toolbar = findViewById(R.id.activity_toolbar);
            toolbar.setTitle(getString(R.string.activity_family_details_heading));
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

            getCacheData();
            recyclerView = findViewById(R.id.family_details_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(FamilyDetailsActivity.this,
                    LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);

            familyAdapter = new FamilyAdapter(this, familyModelList);

            recyclerView.setAdapter(familyAdapter);

            final Button buttonNext = findViewById(R.id.button_family_details_next);
            final Button buttonHome = findViewById(R.id.activity_button_home);
            final Button buttonAddMember = findViewById(R.id.button_add_member);


            buttonNext.setOnClickListener(onClickListener);
            buttonHome.setOnClickListener(onClickListener);
            buttonAddMember.setOnClickListener(onClickListener);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.button_family_details_next): {
                    if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                        Intent tradingDetailsIntent = new Intent(FamilyDetailsActivity.this,
                                TradingDetailsActivity.class);
                        startActivity(tradingDetailsIntent);
                    } else {
                        LinearLayout linearLayout = findViewById(R.id.layout_activity_family_details);
                        Snackbar noConnectionSnackbar = Snackbar.make(linearLayout,
                                getString(R.string.internet_connection_error_message), Snackbar.LENGTH_LONG);
                        noConnectionSnackbar.show();
                    }
                    break;
                }
                case (R.id.activity_button_home): {
                    Intent homeIntent = new Intent(FamilyDetailsActivity.this, MainActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);
                    overridePendingTransition(R.anim.slide_left_to_right, R.anim.slide_right_to_left);
                    break;
                }
                case (R.id.button_add_member): {
                    showEditDialogFragment();
                    break;
                }
                default: {
                    break;
                }
            }
        }
    };

    private void showEditDialogFragment () {
        FragmentManager fragmentManager = getSupportFragmentManager();
        dialogFragment = FamilyMemberDialogFragment.newInstance(
                getString(R.string.dialog_fragment_family_details_heading));
        dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        dialogFragment.show(fragmentManager, "Add Family Member");
    }


    @Override
    public void onFinishEditDialog(FamilyModel model) {
        familyModelList.add(model);
        Toast.makeText(FamilyDetailsActivity.this, getString(
                R.string.activity_family_details_member_added), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getCacheData() {
        APIService storeFamilyData = APIUtils.getAPIService();
        storeFamilyData.getFamilyData(sharedPreferences.getString("mobile_number", ""),
                sharedPreferences.getString("password", "")).enqueue(new Callback<List<FamilyData>>() {
            @Override
            public void onResponse(Call<List<FamilyData>> call, Response<List<FamilyData>> response) {
                FamilyData resp = response.body().get(0);
                ArrayList<FamilyModel> familycache = new ArrayList<>();
                if (resp.getResponseCode()==200) {
                    for (int i = 1; i < response.body().size(); i++) {
                        resp = response.body().get(i);
                        familycache.add(new FamilyModel(resp.getName(), Integer.parseInt(resp.getAge()),
                                resp.getGender(), resp.getOccupation(), resp.getRelationship()));
                    }
                    try {
                        File cache = new File(getCacheDir(), cacheDataFile);
                        ObjectOutputStream cacheWriter = new ObjectOutputStream(new FileOutputStream(cache));
                        cacheWriter.writeObject(familycache);
                        cacheWriter.close();
                        fillWithCache();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FamilyData>> call, Throwable t) {}
        });
    }

    public void fillWithCache() {
        try {
            ObjectInputStream cacheReader = new ObjectInputStream(new FileInputStream(
                    new File(getCacheDir(), cacheDataFile)));
            familyModelList = (ArrayList<FamilyModel>) cacheReader.readObject();
            recyclerView = findViewById(R.id.family_details_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(FamilyDetailsActivity.this,
                    LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);

            familyAdapter = new FamilyAdapter(this, familyModelList);

            recyclerView.setAdapter(familyAdapter);
        } catch (Exception e) {}
    }
}
