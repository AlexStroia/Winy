package co.alexdev.winy.core.util.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.alexdev.winy.core.repository.WinePairingRepository;
import co.alexdev.winy.feature.ui.detail.uimodel.DetailActivityViewModel;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private WinePairingRepository repository;
    private int wineId;


    public DetailViewModelFactory(WinePairingRepository repository, int wineId) {
        this.repository = repository;
        this.wineId = wineId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailActivityViewModel.class)) {
            return (T) new DetailActivityViewModel(repository, wineId);
        }
        return super.create(modelClass);

    }
}
