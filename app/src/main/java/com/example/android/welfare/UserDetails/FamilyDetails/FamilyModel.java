package com.example.android.welfare.UserDetails.FamilyDetails;

public class FamilyModel {

//    private int serialNo;
    private int memberAge;
    private String memberName;
    private String memberGender;
    private String memberOccupation;
    private String memberRelationship;

    public FamilyModel() {
    }

    public FamilyModel(String memberName, int memberAge, String memberGender,
                       String memberOccupation, String memberRelationship) {
        this.memberAge = memberAge;
        this.memberName = memberName;
        this.memberGender = memberGender;
        this.memberOccupation = memberOccupation;
        this.memberRelationship = memberRelationship;
    }

//    public int getSerialNo() {
//        return serialNo;
//    }
//
//    public void setSerialNo(int serialNo) {
//        this.serialNo = serialNo;
//    }

    public int getMemberAge() {
        return memberAge;
    }

    public void setMemberAge(int memberAge) {
        this.memberAge = memberAge;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberGender() {
        return memberGender;
    }

    public void setMemberGender(String memberGender) {
        this.memberGender = memberGender;
    }

    public String getMemberOccupation() {
        return memberOccupation;
    }

    public void setMemberOccupation(String memberOccupation) {
        this.memberOccupation = memberOccupation;
    }

    public String getMemberRelationship() {
        return memberRelationship;
    }

    public void setMemberRelationship(String memberRelationship) {
        this.memberRelationship = memberRelationship;
    }
}
