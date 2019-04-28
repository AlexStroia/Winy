package co.alexdev.winy.core.repository;


import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.alexdev.winy.core.api.WineResponseService;
import co.alexdev.winy.core.model.wines.ProductMatches;
import co.alexdev.winy.core.util.Resource;
import co.alexdev.winy.core.util.WinyExecutor;


@Singleton
public class WinePairingRepository {

    private WinyExecutor executor;
    private WineResponseService service;

    @Inject
    public WinePairingRepository(WinyExecutor executor, WineResponseService service) {
        this.executor = executor;
        this.service = service;
    }

    public LiveData<Resource<List<ProductMatches>>> getWines(String wine) {
//        return new NetworkBoundsResource<WinePairingResponse, List<ProductMatches>>(executor) {
//
//            @Override
//            protected void saveCallResult(@NonNull List<ProductMatches> item) {
//
//            }
//
//            @Override
//            protected boolean shouldFetch(@NonNull WinePairingResponse data) {
//                return true;
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<WinePairingResponse> loadFromDatabase() {
//                return null;
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<ApiResponse<List<ProductMatches>>> createCall() {
//                return service.getWines(wine);
//            }
//        };
        return null;
    }
}
