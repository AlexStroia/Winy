package co.alexdev.winy.feature.ui.detail.uimodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.winy.core.model.wines.ProductMatches;
import co.alexdev.winy.core.repository.WinePairingRepository;

public class DetailActivityViewModel extends ViewModel {

    public LiveData<DetailActivityProductViewModel> productMatchesViewModelLiveData;
    public LiveData<List<DetailActivityProductViewModel>> similarDetailProductActivityViewModelLiveData;
    private WinePairingRepository repository;

    public DetailActivityViewModel(WinePairingRepository repository, int wineId, String foodName) {
        this.repository = repository;
        productMatchesViewModelLiveData = Transformations.map(repository.loadWineById(wineId), wine -> new DetailActivityProductViewModel(
                wine.id, wine.description, wine.price,
                wine.imageUrl, wine.averageRating.substring(0, 3), String.valueOf(wine.ratingCount), wine.isAddedToFavorite,
                wine.food, wine.title, String.valueOf(wine.score), wine.link));

        similarDetailProductActivityViewModelLiveData = Transformations.map(repository.loadOtherProductMatches(foodName, wineId), wines -> {
            List<DetailActivityProductViewModel> detailActivityProductViewModelList = new ArrayList<>();
            for (ProductMatches wine : wines) {
                detailActivityProductViewModelList.add(new DetailActivityProductViewModel(
                        wine.id, wine.description, wine.price,
                        wine.imageUrl, wine.averageRating.substring(0, 3), String.valueOf(wine.ratingCount), wine.isAddedToFavorite,
                        wine.food, wine.title, String.valueOf(wine.score), wine.link));
            }
            return detailActivityProductViewModelList;
        });
    }

    public void insertToDatabase(DetailActivityProductViewModel detailActivityProductViewModel) {
        repository.update(detailActivityProductViewModel.id, !detailActivityProductViewModel.isAddedToFavorite);
    }
}
