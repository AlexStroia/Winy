package co.alexdev.winy.core.util.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.alexdev.winy.core.repository.WinesRepository;
import co.alexdev.winy.feature.ui.detail.uimodel.DetailActivityViewModel;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private WinesRepository repository;
    private int wineId;
    private String foodName;


    public DetailViewModelFactory(WinesRepository repository, int wineId, String foodName) {
        this.repository = repository;
        this.wineId = wineId;
        this.foodName = foodName;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailActivityViewModel.class)) {
            return (T) new DetailActivityViewModel(repository, wineId, foodName);
        }
        return super.create(modelClass);

    }
}
