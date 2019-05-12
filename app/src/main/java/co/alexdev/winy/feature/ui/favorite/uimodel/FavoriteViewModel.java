package co.alexdev.winy.feature.ui.favorite.uimodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.winy.core.model.wines.ProductMatches;
import co.alexdev.winy.core.repository.BaseRepository;

public class FavoriteViewModel extends ViewModel {

    public LiveData<List<FavoriteItemViewModel>> favorites;

    public FavoriteViewModel(BaseRepository repository) {

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
}
