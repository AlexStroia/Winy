package co.alexdev.winy.core.api;

import androidx.lifecycle.LiveData;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DishResponseService {

    @GET
    LiveData<ApiResponse<DishPairingResponse>> getDish(@Query("wine") String wine);
}
