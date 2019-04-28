package co.alexdev.winy.core.api;

import androidx.lifecycle.LiveData;

import java.util.List;

import co.alexdev.winy.core.model.wines.ProductMatches;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WineResponseService {

    @GET("food/wine/pairing?maxPrice=50")
    LiveData<ApiResponse<List<ProductMatches>>> getWines(@Query("food") String wineName);


}
