package com.example.android.welfare.userdetails.familydetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.welfare.R;

import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyViewHolder> {

    private Context context;
    private List<FamilyModel> familyModels;

    public FamilyAdapter (Context context, List<FamilyModel> familyModels) {
        this.context = context;
        this.familyModels = familyModels;
    }

    @NonNull
    @Override
    public FamilyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_family_view, parent, false);

        return new FamilyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyViewHolder holder, int position) {
        FamilyModel familyModel = familyModels.get(position);
        holder.memberName.setText(String.format(context.getString(R.string.activity_family_details_name), familyModel.getMemberName()));
        holder.memberAge.setText(String.format(context.getString(R.string.activity_family_details_age), familyModel.getMemberAge()));
        holder.memberGender.setText(String.format(context.getString(R.string.activity_family_details_gender), familyModel.getMemberGender()));
        holder.memberOccupation.setText(String.format(context.getString(R.string.activity_family_details_occupation), familyModel.getMemberOccupation()));
        holder.memberRelationship.setText(String.format(context.getString(R.string.activity_family_details_relationship), familyModel.getMemberRelationship()));
    }

    @Override
    public int getItemCount() {
        return familyModels.size();
    }
}
