package co.alexdev.winy.core.util.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.alexdev.winy.core.repository.DishRepository;
import co.alexdev.winy.core.repository.WinesRepository;
import co.alexdev.winy.feature.ui.favorite.uimodel.FavoriteViewModel;
import co.alexdev.winy.feature.ui.product.dish.DishViewModel;
import co.alexdev.winy.feature.ui.product.wine.uimodel.WineFragmentViewModel;
import co.alexdev.winy.feature.ui.search.uimodel.SearchActivityViewModel;

public class BaseViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private WinesRepository repository;
    private DishRepository dishRepository;

    public BaseViewModelFactory(WinesRepository repository) {
        this.repository = repository;
    }

    public BaseViewModelFactory(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WineFragmentViewModel.class)) {
            return (T) new WineFragmentViewModel(repository);
        } else if (modelClass.isAssignableFrom(SearchActivityViewModel.class)) {
            return (T) new SearchActivityViewModel(repository);
        } else if (modelClass.isAssignableFrom(FavoriteViewModel.class)) {
            return (T) new FavoriteViewModel(repository);
        } else if (modelClass.isAssignableFrom(DishViewModel.class)) {
            return (T) new DishViewModel(dishRepository);
        }
        return super.create(modelClass);
    }
}
