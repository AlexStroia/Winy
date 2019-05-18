package co.alexdev.winy.feature.ui.product.dish;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.winy.core.model.dish.Dish;
import co.alexdev.winy.core.repository.DishRepository;
import co.alexdev.winy.core.util.Resource;
import co.alexdev.winy.feature.ui.product.dish.uimodel.DishItemViewModel;

public class DishViewModel extends ViewModel {

    public String wine;
    public String description;
    public LiveData<List<String>> dishAutocompleteItems;
    LiveData<List<DishItemViewModel>> dishes = new MutableLiveData<>();
    private DishRepository repository;

    public DishViewModel(DishRepository dishRepository) {
        this.repository = dishRepository;
        dishAutocompleteItems = repository.loadAutocompleteNames();
    }

    public LiveData<Resource<List<Dish>>> onSearchPressed() {
        return repository.loadDish(wine);
    }

    public void setProductsDish() {
        dishes = Transformations.map(repository.loadDishesFromDatabase(wine), dishes -> {
            List<DishItemViewModel> dishItemViewModels = new ArrayList<>();
            if (dishes.size() > 0) {
                description = dishes.get(0).description;
                for (Dish dish : dishes) {
                    DishItemViewModel dishItemViewModel = new DishItemViewModel(dish.id, dish.description, dish.food);
                    dishItemViewModels.add(dishItemViewModel);
                }
            }
            return dishItemViewModels;
        });
    }
}
