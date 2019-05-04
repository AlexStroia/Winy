package co.alexdev.winy.feature.ui.detail.uimodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import co.alexdev.winy.core.repository.WinePairingRepository;
import co.alexdev.winy.feature.ui.product.wine.uimodel.ProductMatchesViewModel;

public class DetailActivityViewModel extends ViewModel {

    public LiveData<ProductMatchesViewModel> productMatchesViewModelLiveData;

    public DetailActivityViewModel(WinePairingRepository repository, int wineId) {
        productMatchesViewModelLiveData = Transformations.map(repository.loadWineById(wineId), wine -> new ProductMatchesViewModel(
                wine.id, wine.description, wine.price,
                wine.imageUrl, String.format("%.2f", wine.averageRating), String.valueOf(wine.ratingCount),
                wine.food, wine.title, String.valueOf(wine.score), wine.link));
    }
}
