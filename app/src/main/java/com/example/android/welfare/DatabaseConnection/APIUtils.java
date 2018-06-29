package com.example.android.welfare.DatabaseConnection;

public class APIUtils {
    private APIUtils() {}

    public static final String BASE_URL = "https://welfare-app.000webhostapp.com/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);

    }
}
