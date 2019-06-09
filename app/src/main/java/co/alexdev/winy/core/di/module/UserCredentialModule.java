package co.alexdev.winy.core.di.module;


import co.alexdev.winy.core.di.SingletoneScope;
import co.alexdev.winy.core.model.user.UserCredential;
import dagger.Module;
import dagger.Provides;

@Module
public class UserCredentialModule {

    @Provides
    @SingletoneScope
    public UserCredential provideUserCredential() {
        return new UserCredential();
    }
}
