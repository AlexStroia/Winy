package co.alexdev.winy.core.util.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.feature.ui.login.uimodel.ActivityLoginViewModel;
import co.alexdev.winy.feature.ui.signup.uimodel.SignupActivityViewModel;

public class SignupLoginViewModelFActory extends ViewModelProvider.NewInstanceFactory {

    private UserCredential userCredential;

    @Inject
    public SignupLoginViewModelFActory(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignupActivityViewModel.class)) {
            return (T) new SignupActivityViewModel(userCredential);
        } else if (modelClass.isAssignableFrom(ActivityLoginViewModel.class))
            return (T) new ActivityLoginViewModel(userCredential);
        return super.create(modelClass);
    }
}
