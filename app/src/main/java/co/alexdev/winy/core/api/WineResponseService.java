package co.alexdev.winy.core.api;

import androidx.lifecycle.LiveData;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WineResponseService {

    @GET("pairing?maxPrice=50")
    LiveData<ApiResponse<WinePairingResponse>> getWines(@Query("food") String wineName);


}
