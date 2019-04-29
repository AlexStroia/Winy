package co.alexdev.winy.core.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.alexdev.winy.core.api.ApiResponse;
import co.alexdev.winy.core.api.WinePairingResponse;
import co.alexdev.winy.core.api.WineResponseService;
import co.alexdev.winy.core.database.WinesDao;
import co.alexdev.winy.core.model.wines.ProductMatches;
import co.alexdev.winy.core.util.DatabaseUtils;
import co.alexdev.winy.core.util.NetworkBoundsResource;
import co.alexdev.winy.core.util.Resource;
import co.alexdev.winy.core.util.WinyExecutor;


@Singleton
public class WinePairingRepository {

    private WinyExecutor executor;
    private WineResponseService service;
    private WinesDao winesDao;
    private DatabaseUtils databaseUtils;

    @Inject
    public WinePairingRepository(WinyExecutor executor, WineResponseService service, WinesDao winesDao, DatabaseUtils databaseUtils) {
        this.executor = executor;
        this.service = service;
        this.winesDao = winesDao;
        this.databaseUtils = databaseUtils;
    }

    public LiveData<Resource<List<ProductMatches>>> getWinesByFoodName(String food) {
        return new NetworkBoundsResource<List<ProductMatches>, WinePairingResponse>(executor) {

            @Override
            protected void saveCallResult(@NonNull WinePairingResponse item) {
                String wines = databaseUtils.extractWinesToString(item.pairedWines);
                List<ProductMatches> productMatches = databaseUtils.appendWinesToProductMatches(wines, food, item.productMatches);
                winesDao.insert(productMatches);
            }

            @Override
            protected boolean shouldFetch(@NonNull List<ProductMatches> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<ProductMatches>> loadFromDatabase() {
                return winesDao.loadWines(food);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<WinePairingResponse>> createCall() {
                return service.getWines(food);
            }
        }.asLiveData();
    }

    public LiveData<List<ProductMatches>> getAllWinesFromDatabase() {
        return winesDao.loadAllWinesFromDatabase();
    }

    public LiveData<List<ProductMatches>> getAllWinesFromDatabaseByFood(String food) {
        return winesDao.loadWines(food);
    }

    public LiveData<String> getProductMatches(String food) {
        return winesDao.getProductMatches(food);
    }
}
