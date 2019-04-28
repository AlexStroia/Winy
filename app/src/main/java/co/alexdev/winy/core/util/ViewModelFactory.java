package co.alexdev.winy.core.util;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.feature.ui.login.uimodel.ActivityLoginViewModel;
import co.alexdev.winy.feature.ui.signup.uimodel.SignupActivityViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private UserCredential userCredential;

    @Inject
    public ViewModelFactory(UserCredential userCredential) {
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
