package com.example.android.welfare;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class UserDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        final Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        toolbar.setTitle(getString(R.string.activity_main_heading));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(getDrawable(R.drawable.ic_arrow_back_black_24dp));

        final TabLayout tabLayout = findViewById(R.id.activity_user_details_tabs);

        final ViewPager viewPager = findViewById(R.id.view_pager_user_details);
        final UserDetailsViewPagerAdapter userDetailsViewPagerAdapter = new UserDetailsViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(userDetailsViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

//        FamilyDetailsFragment familyDetailsFragment = new FamilyDetailsFragment();
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.user_details_coordinator_layout, familyDetailsFragment, familyDetailsFragment.toString()).commit();


    }

//    @Override
//    public void replaceFragment(Fragment fragment) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().
//                replace(R.id.user_details_coordinator_layout, fragment, fragment.toString());
//        fragmentTransaction.addToBackStack(fragment.toString());
//        fragmentTransaction.commit();
//    }
}
