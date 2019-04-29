package co.alexdev.winy.core.util.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.alexdev.winy.core.repository.WinePairingRepository;
import co.alexdev.winy.feature.ui.product.wine.uimodel.WineFragmentViewModel;

public class WineViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private WinePairingRepository repository;

    public WineViewModelFactory(WinePairingRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WineFragmentViewModel.class)) {
            return (T) new WineFragmentViewModel(repository);
        }
        return super.create(modelClass);
    }
}
