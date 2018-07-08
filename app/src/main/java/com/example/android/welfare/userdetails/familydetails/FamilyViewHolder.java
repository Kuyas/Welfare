package com.example.android.welfare.userdetails.familydetails;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.welfare.R;

public class FamilyViewHolder extends RecyclerView.ViewHolder {

    protected TextView memberName;
    protected TextView memberAge;
    protected TextView memberGender;
    protected TextView memberOccupation;
    protected TextView memberRelationship;


    public FamilyViewHolder(View itemView) {
        super(itemView);

        memberName = itemView.findViewById(R.id.text_view_family_member_name);
        memberAge = itemView.findViewById(R.id.text_view_family_member_age);
        memberGender = itemView.findViewById(R.id.text_view_family_member_gender);
        memberOccupation = itemView.findViewById(R.id.text_view_family_member_occupation);
        memberRelationship = itemView.findViewById(R.id.text_view_family_member_relationship);

    }
}
