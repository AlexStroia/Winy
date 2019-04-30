package co.alexdev.winy.core.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.alexdev.winy.core.api.ApiResponse;
import co.alexdev.winy.core.api.WinePairingResponse;
import co.alexdev.winy.core.api.WineResponseService;
import co.alexdev.winy.core.database.PairedWinesDao;
import co.alexdev.winy.core.database.WinesDao;
import co.alexdev.winy.core.model.wines.PairedWines;
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
    private PairedWinesDao pairedWinesDao;
    private DatabaseUtils databaseUtils;

    @Inject
    public WinePairingRepository(WinyExecutor executor, WineResponseService service, WinesDao winesDao,
                                 PairedWinesDao pairedWinesDao, DatabaseUtils databaseUtils) {
        this.executor = executor;
        this.service = service;
        this.winesDao = winesDao;
        this.pairedWinesDao = pairedWinesDao;
        this.databaseUtils = databaseUtils;
    }

    public LiveData<Resource<List<ProductMatches>>> getWinesByFoodName(String food) {
        return new NetworkBoundsResource<List<ProductMatches>, WinePairingResponse>(executor) {

            @Override
            protected void saveCallResult(@NonNull WinePairingResponse item) {
                pairedWinesDao.deleteAll(food);
                List<PairedWines> wines = databaseUtils.createPairedWinesList(item.pairedWines, food);
                List<ProductMatches> productMatches = databaseUtils.appendFoodToProductMatches(food, item.productMatches);
                winesDao.insert(productMatches);
                pairedWinesDao.insert(wines);
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
        return winesDao.loadAllWines();
    }

    public LiveData<List<ProductMatches>> getAllWinesFromDatabaseByFood(String food) {
        return winesDao.loadWines(food);
    }

    public LiveData<List<PairedWines>> loadPairedWinesByFood(String food) {
        return pairedWinesDao.loadPairedWinesByFood(food);
    }

    public LiveData<List<String>> loadAllFoodNames() {
        return winesDao.loadAllFoodNames();
    }
}
