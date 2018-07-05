package com.example.android.welfare.userdetails.familydetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.welfare.R;
import com.example.android.welfare.userdetails.TextValidator;

public class FamilyMemberDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextInputEditText memberName;
    private TextInputEditText memberAge;
    private TextInputEditText memberOccupation;
    private TextInputEditText memberRelationship;
    private Spinner memberGender;

    private TextValidator validateName;
    private TextValidator validateAge;
    private TextValidator validateOccupation;
    private TextValidator validateRelationship;

    public FamilyMemberDialogFragment() {
    }

    public interface AddButtonDialogListener {
        void onFinishEditDialog(FamilyModel model);
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

        memberName = view.findViewById(R.id.text_input_family_member_name);
        memberAge = view.findViewById(R.id.text_input_family_member_age);
        memberOccupation = view.findViewById(R.id.text_input_family_member_occupation);
        memberRelationship = view.findViewById(R.id.text_input_family_member_relationship);

        memberGender = view.findViewById(R.id.spinner_family_details_gender_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.dialog_fragment_family_spinner_gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        memberGender.setAdapter(adapter);
        memberGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Context context = getContext();
                Object text = parent.getItemAtPosition(pos);
//                if (pos > 0) {
//                    Toast.makeText(context, (CharSequence) text, Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                //Another interface callback
//                Context context = getApplicationContext();
////            Object text =  parent.getItemAtPosition(pos);
//                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
            }
        });

        final String title = getArguments().getString("title", getString(R.string.dialog_fragment_family_details_heading));
        getDialog().setTitle(title);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        final Button addMember = view.findViewById(R.id.dialog_fragment_family_details_button_add);
        final Button cancel = view.findViewById(R.id.dialog_fragment_family_details_button_cancel);

        addMember.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        getDialog().getWindow().setLayout(getResources().getDimensionPixelSize(R.dimen.family_details_dialog_fragment_width),
                getResources().getDimensionPixelOffset(R.dimen.family_details_dialog_fragment_height));

        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.dialog_fragment_family_details_button_add): {
                boolean flag = true;

                validateName = new TextValidator(memberName);
                validateAge = new TextValidator(memberAge);
                validateOccupation = new TextValidator(memberOccupation);
                validateRelationship = new TextValidator(memberRelationship);

                if (!validateName.isValid()) {
                    flag = false;
                    memberName.setError(getString(R.string.dialog_fragment_family_details_name_error));
                }
                if (!validateAge.isValid()) {
                    flag = false;
                    memberAge.setError(getString(R.string.dialog_fragment_family_details_age_error));
                }
                if (memberGender.getSelectedItem().toString().trim().equals(getString
                        (R.string.dialog_fragment_family_details_choose_gender))) {
                    flag = false;
                    Toast.makeText(getActivity(), getString(R.string.dialog_fragment_family_details_gender_error),
                            Toast.LENGTH_SHORT).show();
                }
                if (!validateOccupation.isValid()) {
                    flag = false;
                    memberOccupation.setError(getString(R.string.dialog_fragment_family_details_occupation_error));
                }
                if (!validateRelationship.isValid()) {
                    flag = false;
                    memberRelationship.setError(getString(R.string.dialog_fragment_family_details_relationship_error));
                }

                if (flag) {

                    AddButtonDialogListener listener = (AddButtonDialogListener) getActivity();

                    String name = memberName.getText().toString();
                    int age = Integer.valueOf(memberAge.getText().toString());
                    String occupation = memberOccupation.getText().toString();
                    String relationship = memberRelationship.getText().toString();

                    String gender = memberGender.getSelectedItem().toString().trim();

                    FamilyModel model = new FamilyModel(name, age, gender, occupation, relationship);
                    listener.onFinishEditDialog(model);

                    dismiss();
                } else {
                    Snackbar errorSnackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                            getString(R.string.dialog_fragment_family_details_invalid_data), Snackbar.LENGTH_SHORT);

                    errorSnackbar.show();
                }
                break;
            }
            case (R.id.dialog_fragment_family_details_button_cancel): {
                dismiss();
                break;
            }
            default: {
                break;
            }
        }
    }
}
