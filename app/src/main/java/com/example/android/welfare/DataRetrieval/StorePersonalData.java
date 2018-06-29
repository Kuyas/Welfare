package com.example.android.welfare.DataRetrieval;

import com.example.android.welfare.DatabaseConnection.APIService;
import com.example.android.welfare.DatabaseConnection.APIUtils;
import com.example.android.welfare.DatabaseConnection.ResponseClasses.PersonalData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StorePersonalData {

    public StorePersonalData() {}

    public static void main(String args[]) {
        APIService storePersonalData = APIUtils.getAPIService();
        storePersonalData.getPersonalData("9830955456", "qwertyuiop").enqueue(new Callback<PersonalData>() {
            @Override
            public void onResponse(Call<PersonalData> call, Response<PersonalData> response) {
                System.out.println(response.body().toString());
            }

            @Override
            public void onFailure(Call<PersonalData> call, Throwable t) {
                System.out.println("Request failed");
            }
        });
    }
}
