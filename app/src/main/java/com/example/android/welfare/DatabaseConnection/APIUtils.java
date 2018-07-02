package com.example.android.welfare.databaseconnection;

public class APIUtils {
    private APIUtils() {}

    public static final String BASE_URL = "http://192.168.43.56/Welfare-backend/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);

    }
}
