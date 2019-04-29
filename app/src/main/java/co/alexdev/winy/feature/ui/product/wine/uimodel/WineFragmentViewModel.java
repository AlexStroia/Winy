package co.alexdev.winy.feature.ui.product.wine.uimodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.winy.core.model.wines.ProductMatches;
import co.alexdev.winy.core.repository.WinePairingRepository;
import co.alexdev.winy.core.util.Resource;

public class WineFragmentViewModel extends ViewModel {

    public String searchedQuery;
    public LiveData<List<ProductMatchesViewModel>> productMatchesViewModelList;
    public String pairedWines = "";
    private WinePairingRepository winePairingRepository;

    public WineFragmentViewModel(WinePairingRepository repository) {
        this.winePairingRepository = repository;

        productMatchesViewModelList = Transformations.map(winePairingRepository.getAllWinesFromDatabase(), data -> {
            List<ProductMatchesViewModel> productMatchesViewModels = new ArrayList<>();
            for (ProductMatches productMatches : data) {
                productMatchesViewModels.add(new ProductMatchesViewModel(productMatches.id,
                        productMatches.description,
                        productMatches.price,
                        productMatches.imageUrl,
                        productMatches.averageRating,
                        productMatches.ratingCount,
                        productMatches.food,
                        productMatches.pairedWines,
                        productMatches.score,
                        productMatches.link));
            }
            return productMatchesViewModels;
        });
    }

    public LiveData<Resource<List<ProductMatches>>> onSearchPressed(String food) {
        return winePairingRepository.getWinesByFoodName(food);
    }

    public void setProductMatchesIfSearchedSuccess() {
        productMatchesViewModelList = Transformations.map(winePairingRepository.getAllWinesFromDatabaseByFood(searchedQuery), data -> {
            List<ProductMatchesViewModel> productMatchesViewModels = new ArrayList<>();
            for (ProductMatches productMatches : data) {
                productMatchesViewModels.add(new ProductMatchesViewModel(productMatches.id,
                        productMatches.description,
                        productMatches.price,
                        productMatches.imageUrl,
                        productMatches.averageRating,
                        productMatches.ratingCount,
                        productMatches.food,
                        productMatches.pairedWines,
                        productMatches.score,
                        productMatches.link));
            }
            return productMatchesViewModels;
        });
    }
}
