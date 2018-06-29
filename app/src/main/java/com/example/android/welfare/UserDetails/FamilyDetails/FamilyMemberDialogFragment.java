package com.example.android.welfare.UserDetails.FamilyDetails;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.android.welfare.R;

public class FamilyMemberDialogFragment extends DialogFragment {

    public FamilyMemberDialogFragment() {
    }

    public static FamilyMemberDialogFragment newInstance (String title) {
        FamilyMemberDialogFragment fragment = new FamilyMemberDialogFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_family_member, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextInputEditText memberName = view.findViewById(R.id.text_input_family_member_name);
        final TextInputEditText memberAge = view.findViewById(R.id.text_input_family_member_age);
        final TextInputEditText memberOccupation = view.findViewById(R.id.text_input_family_member_occupation);
        final TextInputEditText memberRelationship = view.findViewById(R.id.text_input_family_member_relationship);

        final Spinner memberGender = view.findViewById(R.id.spinner_family_details_gender_select);

        final String title = getArguments().getString("title", "");
        getDialog().setTitle(title);
    }
}
