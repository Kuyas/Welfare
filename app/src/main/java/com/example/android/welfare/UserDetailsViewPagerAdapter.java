package com.example.android.welfare;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class UserDetailsViewPagerAdapter extends FragmentPagerAdapter {

    public UserDetailsViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }



    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new PersonalDetailsFragment();
        }
        else if (position == 1) {
            fragment = new TradingDetailsFragment();
        }
        else if (position == 2) {
            fragment = new OtherDetailsFragment();
        }
        else if (position == 3) {
            fragment = new PaymentDetailsFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Personal Details";
        }
        else if (position == 1) {
            title = "Trading Details";
        }
        else if (position == 2) {
            title = "Other Details";
        }
        else if (position == 3) {
            title = "Payment Details";
        }

        return title;
    }
}
