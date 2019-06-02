package co.alexdev.winy.feature.ui.product.wine.uimodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.winy.core.model.wines.PairedWines;
import co.alexdev.winy.core.model.wines.PairingText;
import co.alexdev.winy.core.model.wines.ProductMatches;
import co.alexdev.winy.core.repository.WinesRepository;
import co.alexdev.winy.core.util.AnalyticsManager;
import co.alexdev.winy.core.util.PreferenceManager;
import co.alexdev.winy.core.util.Resource;
import co.alexdev.winy.core.util.WinyWidgetService;

public class WineFragmentViewModel extends ViewModel {

    public String food;
    public LiveData<List<ProductMatchesViewModel>> productMatchesViewModelLiveData;
    public LiveData<List<PairedWinesViewModel>> pairedWinesViewModelLiveData;
    public LiveData<List<String>> foodNamesLiveData;
    private WinesRepository winesRepository;
    private PreferenceManager preferenceManager;

    private AnalyticsManager analyticsManager;

    public WineFragmentViewModel(WinesRepository repository, AnalyticsManager analyticsManager, PreferenceManager preferenceManager) {
        this.winesRepository = repository;
        this.analyticsManager = analyticsManager;
        this.preferenceManager = preferenceManager;
        foodNamesLiveData = repository.loadAllFoodNames();
    }

    public LiveData<Resource<List<ProductMatches>>> onSearchPressed() {
        analyticsManager.searchFood(food);
        return winesRepository.getWinesByFoodName(food);
    }

    /*
     When a search occurs, the list is updated with the data returned for that specific search */
    public void setProductMatchesListForSearch() {
        productMatchesViewModelLiveData = Transformations.map(winesRepository.loadAllWinesFromDatabaseByFood(food), data -> {
            List<ProductMatchesViewModel> productMatchesViewModels = new ArrayList<>();
            for (ProductMatches productMatches : data) {
                productMatchesViewModels.add(new ProductMatchesViewModel(
                        productMatches.id, productMatches.description, productMatches.price,
                        productMatches.imageUrl, String.valueOf(productMatches.averageRating), String.valueOf(productMatches.ratingCount),
                        productMatches.isAddedToFavorite, productMatches.food, productMatches.title, String.valueOf(productMatches.score),
                        productMatches.link));
            }
            return productMatchesViewModels;
        });
    }

    public LiveData<PairingText> pairingTextDescription() {
        return winesRepository.loadPairingTextByFood(food);
    }

    public void setPairedWinesViewModelList(Context context) {
        StringBuilder winesBuilder = new StringBuilder();
        pairedWinesViewModelLiveData =
                Transformations.map(winesRepository.loadPairedWinesByFood(food), data -> {
                    List<PairedWinesViewModel> pairedWinesViewModelList = new ArrayList<>();
                    for (PairedWines pairedWines : data) {
                        String pairedWinesDescription = pairedWines.description.substring(0, 1).toUpperCase() + pairedWines.description.substring(1).toLowerCase();
                        winesBuilder.append("" + pairedWinesDescription).append("\n");
                        pairedWinesViewModelList.add(new PairedWinesViewModel(pairedWinesDescription));
                    }
                    preferenceManager.saveWines(winesBuilder.toString());
                    WinyWidgetService.startIntentAction(context);
                    return pairedWinesViewModelList;
                });
    }

    public void setProductMatchesViewModelList() {
        productMatchesViewModelLiveData = Transformations.map(winesRepository.loadAllWinesFromDatabaseByFood(food), data -> {
            List<ProductMatchesViewModel> productMatchesViewModels = new ArrayList<>();
            for (ProductMatches productMatches : data) {
                productMatchesViewModels.add(new ProductMatchesViewModel(
                        productMatches.id, productMatches.description, productMatches.price,
                        productMatches.imageUrl, String.valueOf(productMatches.averageRating), String.valueOf(productMatches.ratingCount),
                        productMatches.isAddedToFavorite, productMatches.food, productMatches.title, String.valueOf(productMatches.score),
                        productMatches.link));
            }
            return productMatchesViewModels;
        });
    }
}
