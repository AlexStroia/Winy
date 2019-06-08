package co.alexdev.winy.core.util.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import co.alexdev.winy.core.repository.AuthenticationRepository;
import co.alexdev.winy.feature.ui.account.uimodel.AccountActivityViewModel;

public class SettingsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AuthenticationRepository repository;

    public SettingsViewModelFactory(AuthenticationRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AccountActivityViewModel.class)) {
            return (T) new AccountActivityViewModel(repository);
        }
        return super.create(modelClass);
    }
}
