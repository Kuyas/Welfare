package com.example.android.welfare;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("login_user.php")
    @FormUrlEncoded
    Call<LoginPostData> loginUser(@Field("mobile_number") String mobile_number,
                        @Field("password") String password);

    @POST("register_user.php")
    @FormUrlEncoded
    Call<LoginPostData> registerUser(@Field("mobile_number") String mobile_number,
                                 @Field("password") String password);

    @POST("getuserid.php")
    @FormUrlEncoded
    Call<LoginPostData> getUserId(@Field("mobile_number") String mobile_number);

    @POST("forgotpassword_user.php")
    @FormUrlEncoded
    Call<LoginPostData> changePassword(@Field("user_id") String user_id,
                                       @Field("password") String password);
}
