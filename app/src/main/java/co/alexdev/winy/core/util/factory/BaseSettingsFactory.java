package co.alexdev.winy.core.util.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.alexdev.winy.core.repository.AuthenticationRepository;
import co.alexdev.winy.feature.ui.account.uimodel.AccountActivityViewModel;
import co.alexdev.winy.feature.ui.product.settings.uimodel.SettingsFragmentViewModel;

public class BaseSettingsFactory extends ViewModelProvider.NewInstanceFactory {

    private AuthenticationRepository repository;

    public BaseSettingsFactory(AuthenticationRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AccountActivityViewModel.class)) {
            return (T) new AccountActivityViewModel(repository);
        } else if(modelClass.isAssignableFrom(SettingsFragmentViewModel.class)) {
            return (T) new SettingsFragmentViewModel(repository);
        }
        return super.create(modelClass);
    }
}
