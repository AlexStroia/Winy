package co.alexdev.winy.core.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.alexdev.winy.core.api.ApiResponse;
import co.alexdev.winy.core.api.DishPairingResponse;
import co.alexdev.winy.core.api.DishResponseService;
import co.alexdev.winy.core.database.DishDao;
import co.alexdev.winy.core.model.dish.Dish;
import co.alexdev.winy.core.util.DatabaseUtils;
import co.alexdev.winy.core.util.Resource;
import co.alexdev.winy.core.util.WinyExecutor;

@Singleton
public class DishRepository {

    private WinyExecutor executor;
    private DishResponseService service;
    private DishDao dishDao;
    private DatabaseUtils databaseUtils;
    private RateLimiter<String> repoListRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);


    @Inject
    public DishRepository(WinyExecutor executor, DishResponseService service, DishDao dishDao, DatabaseUtils databaseUtils) {
        this.executor = executor;
        this.service = service;
        this.dishDao = dishDao;
        this.databaseUtils = databaseUtils;
    }

    public LiveData<Resource<List<Dish>>> loadDish(String searchedQuery) {
        return new NetworkBoundsResource<List<Dish>, DishPairingResponse>(executor) {

            @Override
            protected void saveCallResult(@NonNull DishPairingResponse item) {
                dishDao.deleteAll(searchedQuery);
                dishDao.insert(databaseUtils
                        .appendQueryAndDescriptionToDish(searchedQuery,
                                item.textDescription, item.pairings));
            }

            @Override
            protected boolean shouldFetch(@NonNull List<Dish> data) {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(searchedQuery);
            }

            @NonNull
            @Override
            protected LiveData<List<Dish>> loadFromDatabase() {
                return dishDao.loadDish(searchedQuery);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<DishPairingResponse>> createCall() {
                return service.getDish(searchedQuery);
            }
        }.asLiveData();
    }

    public LiveData<List<Dish>> loadDishesFromDatabase(String wine) {
        return dishDao.loadDish(wine);
    }

    public LiveData<List<String>> loadAutocompleteNames() {
        return dishDao.loadAutocompleteNames();
    }
}
