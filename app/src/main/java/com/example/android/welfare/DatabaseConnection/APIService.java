package com.example.android.welfare.DatabaseConnection;

import com.example.android.welfare.DatabaseConnection.ResponseClasses.LoginPostData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("login_user.php")
    @FormUrlEncoded
    Call<LoginPostData> savePost(@Field("mobile_number") String mobile_number,
                                 @Field("password") String password);

}
