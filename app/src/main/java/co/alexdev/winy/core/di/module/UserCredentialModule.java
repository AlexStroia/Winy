package co.alexdev.winy.core.di.module;

import javax.inject.Singleton;

import co.alexdev.winy.core.model.user.UserCredential;
import dagger.Module;
import dagger.Provides;

@Module
public class UserCredentialModule {

    @Singleton
    @Provides
    public UserCredential provideUserCredential() {
        return new UserCredential();
    }
}