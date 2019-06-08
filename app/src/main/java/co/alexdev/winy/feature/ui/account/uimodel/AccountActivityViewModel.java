package co.alexdev.winy.feature.ui.account.uimodel;

import androidx.lifecycle.ViewModel;

import co.alexdev.winy.core.repository.AuthenticationRepository;

public class AccountActivityViewModel extends ViewModel {

    public CachedUserViewModel cachedUserViewModel;

    public AccountActivityViewModel(AuthenticationRepository authenticationRepository) {
        cachedUserViewModel = new CachedUserViewModel(authenticationRepository.cachedUser.getUserCredential(), authenticationRepository.cachedUser.getUserInformation());
    }
}
