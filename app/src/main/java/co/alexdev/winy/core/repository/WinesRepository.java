package co.alexdev.winy.core.repository;


import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.alexdev.winy.core.api.ApiResponse;
import co.alexdev.winy.core.api.WinePairingResponse;
import co.alexdev.winy.core.api.WineResponseService;
import co.alexdev.winy.core.database.PairedWinesDao;
import co.alexdev.winy.core.database.PairingTextDao;
import co.alexdev.winy.core.database.WinesDao;
import co.alexdev.winy.core.model.wines.PairedWines;
import co.alexdev.winy.core.model.wines.PairingText;
import co.alexdev.winy.core.model.wines.ProductMatches;
import co.alexdev.winy.core.util.DatabaseUtils;
import co.alexdev.winy.core.util.Resource;
import co.alexdev.winy.core.util.WinyExecutor;


@Singleton
public class WinesRepository {

    private WinyExecutor executor;
    private WineResponseService service;
    private WinesDao winesDao;
    private PairedWinesDao pairedWinesDao;
    private PairingTextDao pairingTextDao;
    private DatabaseUtils databaseUtils;
    private RateLimiter<String> repoListRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    @Inject
    public WinesRepository(WinyExecutor executor, WineResponseService service, WinesDao winesDao,
                           PairedWinesDao pairedWinesDao, PairingTextDao pairingTextDao, DatabaseUtils databaseUtils) {
        this.executor = executor;
        this.service = service;
        this.winesDao = winesDao;
        this.pairedWinesDao = pairedWinesDao;
        this.pairingTextDao = pairingTextDao;
        this.databaseUtils = databaseUtils;
    }

    public LiveData<Resource<List<ProductMatches>>> getWinesByFoodName(String food) {
        return new NetworkBoundsResource<List<ProductMatches>, WinePairingResponse>(executor) {

            @Override
            protected void saveCallResult(@NonNull WinePairingResponse item) {
                pairedWinesDao.deleteAll(food);
                if (!TextUtils.isEmpty(item.pairingText)) {
                    pairingTextDao.deleteAll(food);
                    pairingTextDao.insert(new PairingText(food, item.pairingText));
                }
                List<PairedWines> wines = databaseUtils.createPairedWinesList(item.pairedWines, food);
                List<ProductMatches> productMatches = databaseUtils.appendFoodToProductMatches(food, item.productMatches);
                winesDao.insert(productMatches);
                pairedWinesDao.insert(wines);
            }

            @Override
            protected boolean shouldFetch(@NonNull List<ProductMatches> data) {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(food);
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

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(food);
            }
        }.asLiveData();
    }

    public LiveData<List<ProductMatches>> loadAllWinesFromDatabase() {
        return winesDao.loadAllWines();
    }

    public void update(int id, boolean isAddedToFavorite) {
        executor.getDiskIO().execute(() -> winesDao.update(id, isAddedToFavorite));
    }

    public LiveData<ProductMatches> loadWineById(int id) {
        return winesDao.loadWineById(id);
    }

    public LiveData<List<ProductMatches>> loadAllWinesFromDatabaseByFood(String food) {
        return winesDao.loadWines(food);
    }

    public LiveData<List<ProductMatches>> loadOtherProductMatches(String food, int id) {
        return winesDao.loadOtherProductMatches(food, id);
    }

    public LiveData<List<PairedWines>> loadPairedWinesByFood(String food) {
        return pairedWinesDao.loadPairedWinesByFood(food);
    }

    public LiveData<PairingText> loadPairingTextByFood(String food) {
        return pairingTextDao.loadPairedTextByFood(food);
    }

    public LiveData<List<ProductMatches>> loadWinesByCharacters(String characters) {
        return winesDao.loadWinesByCharacters(characters);
    }

    public LiveData<List<ProductMatches>> loadFavoriteProducts() {
        return winesDao.loadFavoriteProducts();
    }

    public LiveData<List<String>> loadAllFoodNames() {
        return winesDao.loadAllFoodNames();
    }
}
