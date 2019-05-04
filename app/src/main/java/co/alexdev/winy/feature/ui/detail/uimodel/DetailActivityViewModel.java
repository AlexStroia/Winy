package co.alexdev.winy.feature.ui.detail.uimodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import co.alexdev.winy.core.repository.WinePairingRepository;
import co.alexdev.winy.feature.ui.product.wine.uimodel.ProductMatchesViewModel;

public class DetailActivityViewModel extends ViewModel {

    public LiveData<ProductMatchesViewModel> productMatchesViewModelLiveData;
    private WinePairingRepository repository;

    public DetailActivityViewModel(WinePairingRepository repository, int wineId) {
        this.repository = repository;
        productMatchesViewModelLiveData = Transformations.map(repository.loadWineById(wineId), wine -> new ProductMatchesViewModel(
                wine.id, wine.description, wine.price,
                wine.imageUrl, wine.averageRating.substring(0, 3), String.valueOf(wine.ratingCount), wine.isAddedToFavorite,
                wine.food, wine.title, String.valueOf(wine.score), wine.link));
    }

    public void insertToDatabase(ProductMatchesViewModel productMatchesViewModel) {
        repository.update(productMatchesViewModel.id, !productMatchesViewModel.isAddedToFavorite);
    }
}
