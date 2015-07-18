package net.voxelplanet.lorforandroid.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiNotifications {
    @GET("/yandex-tableau")
    void getCount(
            @Query("csrf") String csrf,
            Callback<Integer> response
    );
}
