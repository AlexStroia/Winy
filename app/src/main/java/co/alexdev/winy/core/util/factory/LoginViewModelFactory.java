package co.alexdev.winy.core.util.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.core.repository.AuthenticationRepository;
import co.alexdev.winy.core.util.AnalyticsManager;
import co.alexdev.winy.feature.ui.login.uimodel.ActivityLoginViewModel;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AnalyticsManager analyticsManager;
    private AuthenticationRepository authenticationRepository;

    @Inject
    public LoginViewModelFactory(AnalyticsManager analyticsManager, AuthenticationRepository authenticationRepository) {
        this.analyticsManager = analyticsManager;
        this.authenticationRepository = authenticationRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ActivityLoginViewModel(analyticsManager, authenticationRepository);
    }
}
