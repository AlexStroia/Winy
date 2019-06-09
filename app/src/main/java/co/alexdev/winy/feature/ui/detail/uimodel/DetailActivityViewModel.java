package co.alexdev.winy.feature.ui.detail.uimodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.winy.core.model.wines.ProductMatches;
import co.alexdev.winy.core.repository.WinesRepository;

public class DetailActivityViewModel extends ViewModel {

    public LiveData<DetailActivityProductViewModel> productMatchesViewModelLiveData;
    public LiveData<List<DetailActivityProductViewModel>> similarDetailProductActivityViewModelLiveData;
    public String url = "";
    private WinesRepository repository;
    public boolean shouldUseSharedElementTransition;

    public DetailActivityViewModel(WinesRepository repository, int wineId, String foodName) {
        this.repository = repository;
        productMatchesViewModelLiveData = getUiData(repository, wineId);


        similarDetailProductActivityViewModelLiveData = getListLiveData(foodName, wineId, repository);
    }

    private LiveData<DetailActivityProductViewModel> getUiData(WinesRepository repository, int wineId) {
        return Transformations.map(repository.loadWineById(wineId), wine -> new DetailActivityProductViewModel(
                wine.id, wine.description, wine.price,
                wine.imageUrl, wine.averageRating.substring(0, 3), String.valueOf(wine.ratingCount), wine.isAddedToFavorite,
                wine.food, wine.title, String.valueOf(wine.score), wine.link));
    }

    public void insertToDatabase(DetailActivityProductViewModel detailActivityProductViewModel) {
        shouldUseSharedElementTransition = !detailActivityProductViewModel.isAddedToFavorite;
        repository.update(detailActivityProductViewModel.id, !detailActivityProductViewModel.isAddedToFavorite);
    }

    public LiveData<DetailActivityProductViewModel> updateUI(int clickedWineId) {
        return Transformations.map(repository.loadWineById(clickedWineId), wine -> new DetailActivityProductViewModel(
                wine.id, wine.description, wine.price,
                wine.imageUrl, wine.averageRating.substring(0, 3), String.valueOf(wine.ratingCount), wine.isAddedToFavorite,
                wine.food, wine.title, String.valueOf(wine.score), wine.link));
    }

    public LiveData<List<DetailActivityProductViewModel>> updateRecycler(String foodName, int wineId) {
        return getListLiveData(foodName, wineId, repository);
    }

    private LiveData<List<DetailActivityProductViewModel>> getListLiveData(String foodName, int wineId, WinesRepository repository) {
        return Transformations.map(repository.loadOtherProductMatches(foodName, wineId), wines -> {
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
}