package co.alexdev.winy.feature.ui.product.dish;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import co.alexdev.winy.core.model.dish.Dish;
import co.alexdev.winy.core.repository.DishRepository;
import co.alexdev.winy.core.util.Resource;
import co.alexdev.winy.feature.ui.product.dish.uimodel.DishItemViewModel;

public class DishViewModel extends ViewModel {

    public String food;
    LiveData<List<DishItemViewModel>> dishes;
    private DishRepository repository;

    public DishViewModel(DishRepository dishRepository) {
        this.repository = dishRepository;
    }

    public LiveData<Resource<List<Dish>>> onSearchPressed() {
        return repository.loadDish(food);
    }

}
