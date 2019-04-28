package co.alexdev.winy.core.di;

import javax.inject.Singleton;

import co.alexdev.winy.core.di.module.UserCredentialModule;
import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.feature.ui.login.ActivityLogin;
import co.alexdev.winy.feature.ui.signup.SignupActivity;
import dagger.Component;

@Singleton
@Component(modules = {UserCredentialModule.class})
public interface WinyComponent {

    UserCredential userCredential();

    void inject(SignupActivity signupActivity);

    void inject(ActivityLogin activityLogin);
}
