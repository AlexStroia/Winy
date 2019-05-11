package co.alexdev.winy.feature.ui.search.uimodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.winy.core.model.wines.ProductMatches;
import co.alexdev.winy.core.repository.WinePairingRepository;

public class SearchActivityViewModel extends ViewModel {

    private final WinePairingRepository repository;
    public LiveData<List<SearchProductViewModel>> products = new MutableLiveData<>();

    public LiveData<List<String>> productsTitle;

    public SearchActivityViewModel(WinePairingRepository repository) {
        this.repository = repository;
        productsTitle = Transformations.map(repository.loadAllWinesFromDatabase(), wines -> {
            List<String> productTitles = new ArrayList<>();
            for (ProductMatches productMatches : wines) {
                productTitles.add(productMatches.title);
            }
            return productTitles;
        });
    }

    public LiveData<List<SearchProductViewModel>> searchProductByName(String name) {
        products = Transformations.map(repository.loadWinesByCharacters(name), products -> {
            List<SearchProductViewModel> searchProductViewModelList = new ArrayList<>();
            for (ProductMatches wine : products) {
                searchProductViewModelList.add(new SearchProductViewModel(
                        wine.id, wine.description, wine.price,
                        wine.imageUrl, wine.averageRating.substring(0, 3), String.valueOf(wine.ratingCount), wine.isAddedToFavorite,
                        wine.food, wine.title, String.valueOf(wine.score), wine.link));
            }
            return searchProductViewModelList;
        });
        return products;
    }
}
