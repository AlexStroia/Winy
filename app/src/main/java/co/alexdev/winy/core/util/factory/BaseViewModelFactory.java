package co.alexdev.winy.core.util.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.alexdev.winy.core.repository.BaseRepository;
import co.alexdev.winy.feature.ui.favorite.uimodel.FavoriteViewModel;
import co.alexdev.winy.feature.ui.product.wine.uimodel.WineFragmentViewModel;
import co.alexdev.winy.feature.ui.search.uimodel.SearchActivityViewModel;

public class BaseViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private BaseRepository repository;

    public BaseViewModelFactory(BaseRepository repository) {
        this.repository = repository;
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
        }
        return super.create(modelClass);
    }
}
