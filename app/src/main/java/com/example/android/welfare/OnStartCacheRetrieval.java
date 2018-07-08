package com.example.android.welfare;

import com.example.android.welfare.databaseconnection.APIService;
import com.example.android.welfare.databaseconnection.APIUtils;
import com.example.android.welfare.databaseconnection.responseclasses.BankingData;
import com.example.android.welfare.databaseconnection.responseclasses.FamilyData;
import com.example.android.welfare.databaseconnection.responseclasses.OtherData;
import com.example.android.welfare.databaseconnection.responseclasses.PersonalData;
import com.example.android.welfare.databaseconnection.responseclasses.TradingData;
import com.example.android.welfare.userdetails.familydetails.FamilyModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnStartCacheRetrieval {
    public static final String personalcachefile = "personaldatacache.data";
    public static final String familycachefile = "familydatacache.data";
    public static final String tradingcachefile = "tradingdatacache.data";
    public static final String othercachefile = "otherdatacache.data";
    public static final String bankingcachefile = "bankingdatacache.data";

    public static void cachePersonalData(String mobile, String password,final String cachedir) {
        APIService storePersonalData = APIUtils.getAPIService();
        storePersonalData.getPersonalData(mobile, password).enqueue(new Callback<PersonalData>() {
            @Override
            public void onResponse(Call<PersonalData> call, Response<PersonalData> response) {
                try {
                    int response_code = response.body().getResponseCode();
                    if (response_code == 200) {
                        File cache = new File(cachedir, personalcachefile);
                        ObjectOutputStream cacheWriter = new ObjectOutputStream(new FileOutputStream(cache));
                        cacheWriter.writeObject(response.body());
                        cacheWriter.close();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<PersonalData> call, Throwable t) {
            }
        });
    }

    public static void cacheFamilyData(String mobile, String password,final String cachedir) {
        APIService storeFamilyData = APIUtils.getAPIService();
        storeFamilyData.getFamilyData(mobile, password).enqueue(new Callback<List<FamilyData>>() {
            @Override
            public void onResponse(Call<List<FamilyData>> call, Response<List<FamilyData>> response) {
                FamilyData resp = response.body().get(0);
                ArrayList<FamilyModel> familycache = new ArrayList<>();
                if (resp.getResponseCode() == 200) {
                    for (int i = 1; i < response.body().size(); i++) {
                        resp = response.body().get(i);
                        familycache.add(new FamilyModel(resp.getName(), Integer.parseInt(resp.getAge()),
                                resp.getGender(), resp.getOccupation(), resp.getRelationship()));
                    }
                    try {
                        File cache = new File(cachedir, familycachefile);
                        ObjectOutputStream cacheWriter = new ObjectOutputStream(new FileOutputStream(cache));
                        cacheWriter.writeObject(familycache);
                        cacheWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FamilyData>> call, Throwable t) {
            }
        });
    }

    public static void cacheTradingData(String mobile, String password, final String cachedir) {
        APIService storeTradingData = APIUtils.getAPIService();
        storeTradingData.getTradingData(mobile, password).
                enqueue(new Callback<TradingData>() {
                    @Override
                    public void onResponse(Call<TradingData> call, Response<TradingData> response) {
                        try {
                            int response_code = response.body().getResponseCode();
                            if (response_code == 200) {
                                File cache = new File(cachedir, tradingcachefile);
                                ObjectOutputStream cacheWriter = new ObjectOutputStream(new FileOutputStream(cache));
                                cacheWriter.writeObject(response.body());
                                cacheWriter.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<TradingData> call, Throwable t) {
                    }
                });
    }

    public static void cacheOtherData(String mobile, String password, final String cachedir) {
        APIService storeOtherData = APIUtils.getAPIService();
        storeOtherData.getOtherData(mobile, password).
                enqueue(new Callback<OtherData>() {
                    @Override
                    public void onResponse(Call<OtherData> call, Response<OtherData> response) {
                        try {
                            int response_code = response.body().getResponseCode();
                            if (response_code == 200) {
                                File cache = new File(cachedir, othercachefile);
                                ObjectOutputStream cacheWriter = new ObjectOutputStream(new
                                        FileOutputStream(cache));
                                cacheWriter.writeObject(response.body());
                                cacheWriter.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtherData> call, Throwable t) {}
                });
    }

    public static void cacheBankingData(String mobile, String password, final String cachedir) {
        APIService storeBankingData = APIUtils.getAPIService();
        storeBankingData.getBankingData(mobile, password).enqueue(new Callback<BankingData>() {
            @Override
            public void onResponse(Call<BankingData> call, Response<BankingData> response) {
                try {
                    int response_code = response.body().getResponseCode();
                    if (response_code == 200) {
                        File cache = new File(cachedir, bankingcachefile);
                        ObjectOutputStream cacheWriter = new ObjectOutputStream(new FileOutputStream(cache));
                        cacheWriter.writeObject(response.body());
                        cacheWriter.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BankingData> call, Throwable t) {
            }
        });
    }
}
