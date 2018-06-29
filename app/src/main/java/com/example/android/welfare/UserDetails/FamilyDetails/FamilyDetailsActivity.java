package com.example.android.welfare.UserDetails.FamilyDetails;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.android.welfare.Login.LoginActivity;
import com.example.android.welfare.MainActivity;
import com.example.android.welfare.NetworkStatus;
import com.example.android.welfare.R;
import com.example.android.welfare.UserDetails.TradingDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class FamilyDetailsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private RecyclerView recyclerView;
    private FamilyAdapter familyAdapter;
    private List<FamilyModel> familyModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = this.getSharedPreferences("com.welfare.app", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("loggedInID", "").isEmpty()) {
            //TODO: Remove the negation

            Intent loginIntent = new Intent(FamilyDetailsActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            setContentView(R.layout.activity_family_details);

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

            int maxFamilyMembers = 10;

            recyclerView = findViewById(R.id.family_details_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(FamilyDetailsActivity.this,
                    LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);

            familyModelList = new ArrayList<>();
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
        FamilyMemberDialogFragment dialogFragment = FamilyMemberDialogFragment.newInstance(
                getString(R.string.dialog_fragment_family_details_heading));

        dialogFragment.show(fragmentManager, "Add Family Member");
        //dialogFragment.getDialog().getWindow().setLayout(200, 200);

    }


}
