package co.alexdev.winy.feature.ui.product.settings.uimodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import co.alexdev.winy.core.model.user.CachedUser;
import co.alexdev.winy.core.repository.AuthenticationRepository;

public class SettingsFragmentViewModel extends ViewModel implements AuthenticationRepository.OnProfileReceivedListener {

    public MutableLiveData<CachedUser> cachedUser = new MutableLiveData<>();
    private AuthenticationRepository repository;

    public SettingsFragmentViewModel(AuthenticationRepository repository) {
        this.repository = repository;
        this.repository.setCachedUserListener(this);
    }

    public void signoutUser() {
        repository.cachedUser = null;
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onProfileReceivedListener(CachedUser cachedUser) {
        this.cachedUser.postValue(cachedUser);
    }
}
