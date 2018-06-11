package com.example.android.welfare;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PersonalDetailsFragment extends Fragment implements View.OnClickListener {

//    private FragmentChangeListener fragmentChangeListener;

    public PersonalDetailsFragment() {
    }

//    public interface FragmentChangeListener {
//        void replaceFragment(Fragment fragment);
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_personal_details, container, false);
        initView(view);

        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        initView(view);
//    }


    private void initView(View view) {
        final Button nextButton = view.findViewById(R.id.personal_details_next);
        nextButton.setOnClickListener(this);
    }


//    public void replaceFragment(Fragment fragment) {
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.user_details_coordinator_layout, fragment, fragment.toString());
//        fragmentTransaction.addToBackStack(fragment.toString());
//        fragmentTransaction.commit();
//  }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.personal_details_next): {
//                FamilyDetailsFragment familyDetailsFragment = new FamilyDetailsFragment();
//                fragmentChangeListener.replaceFragment(familyDetailsFragment);
                break;
            }
            default: {
                break;
            }
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        Activity activity = (Activity) context;
//        fragmentChangeListener = (FragmentChangeListener) activity;
//    }
}
