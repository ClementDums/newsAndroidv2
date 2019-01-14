package com.clt.dumas.clem.news.networks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApikeyService {
    @GET("top-headlines")
    Call<QueryResult> listRepos(@Query("country") String country, @Query("apikey") String apikey);
}
