package com.example.android.welfare.DatabaseConnection;

import com.example.android.welfare.DatabaseConnection.ResponseClasses.AuthenticationData;
import com.example.android.welfare.DatabaseConnection.ResponseClasses.ResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("login_user.php")
    @FormUrlEncoded
    Call<AuthenticationData> loginUser(@Field("mobile_number") String mobile_number,
                                       @Field("password") String password);

    @POST("register_user.php")
    @FormUrlEncoded
    Call<AuthenticationData> registerUser(@Field("mobile_number") String mobile_number,
                                          @Field("password") String password);

    @POST("check_mobile.php")
    @FormUrlEncoded
    Call<AuthenticationData> checkMobile(@Field("mobile_number") String mobile_number);

    @POST("forgotpassword_user.php")
    @FormUrlEncoded
    Call<AuthenticationData> changePassword(@Field("mobile_number") String user_id,
                                            @Field("password") String password);
    @POST("personal_form.php")
    @FormUrlEncoded
    Call<ResponseData> savePersonal(@Field("user_id") String user_id,
                                    @Field("personal_name") String personal_name,
                                    @Field("personal_dob") String personal_dob,
                                    @Field("personal_gender") String personal_gender,
                                    @Field("personal_address") String personal_address,
                                    @Field("personal_place") String personal_place,
                                    @Field("personal_district") String personal_district);

    @POST("trading_form.php")
    @FormUrlEncoded
    Call<ResponseData> saveTrading(@Field("user_id") String user_id,
                                         @Field("trading_firm_name") String trading_firm_name,
                                         @Field("trading_firm_address") String trading_firm_address,
                                         @Field("trading_mtp_branch") String trading_mtp_branch,
                                         @Field("trading_mtp_godown") String trading_mtp_godown,
                                         @Field("trading_mtp_factory") String trading_mtp_factory,
                                         @Field("trading_mtp_others") String trading_mtp_others,
                                         @Field("trading_ownership_type") String trading_mtp_ownership_type,
                                         @Field("trading_capital_contribution") String trading_capital_contribution,
                                         @Field("trading_gstn_date") String trading_gstn_date,
                                         @Field("trading_mtp_license_num") String trading_license_num,
                                         @Field("trading_license_authority") String trading_license_authority,
                                         @Field("trading_official_name") String trading_official_name);


}
