package co.alexdev.winy.feature.ui.favorite.uimodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.winy.core.model.wines.ProductMatches;
import co.alexdev.winy.core.repository.WinesRepository;
import co.alexdev.winy.core.util.AnalyticsManager;

public class FavoriteViewModel extends ViewModel {

    public LiveData<List<FavoriteItemViewModel>> favorites;
    private WinesRepository repository;
    private AnalyticsManager analyticsManager;

    public FavoriteViewModel(WinesRepository repository, AnalyticsManager analyticsManager) {
        this.repository = repository;
        this.analyticsManager = analyticsManager;
        init();
    }

    private static List<FavoriteItemViewModel> apply(List<ProductMatches> products) {
        List<FavoriteItemViewModel> favoriteItemViewModelList = new ArrayList<>();
        for (ProductMatches productMatches : products) {
            favoriteItemViewModelList.add(new FavoriteItemViewModel(
                    productMatches.id, productMatches.description, productMatches.price,
                    productMatches.imageUrl, String.valueOf(productMatches.averageRating), String.valueOf(productMatches.ratingCount),
                    productMatches.isAddedToFavorite, productMatches.food, productMatches.title, String.valueOf(productMatches.score),
                    productMatches.link));
        }
        return favoriteItemViewModelList;
    }

    private void init() {
        favorites = Transformations.map(repository.loadFavoriteProducts(), products -> {
            List<FavoriteItemViewModel> favoriteItemViewModelList = new ArrayList<>();
            for (ProductMatches productMatches : products) {
                favoriteItemViewModelList.add(new FavoriteItemViewModel(
                        productMatches.id, productMatches.description, productMatches.price,
                        productMatches.imageUrl, String.valueOf(productMatches.averageRating), String.valueOf(productMatches.ratingCount),
                        productMatches.isAddedToFavorite, productMatches.food, productMatches.title, String.valueOf(productMatches.score),
                        productMatches.link));
            }
            return favoriteItemViewModelList;
        });
    }

    public LiveData<List<FavoriteItemViewModel>> loadFavProducts() {
        return Transformations.map(repository.loadFavoriteProducts(), FavoriteViewModel::apply);
    }
}
