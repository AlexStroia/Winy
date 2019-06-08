package co.alexdev.winy.core.di.module;

import javax.inject.Singleton;

import co.alexdev.winy.core.repository.AuthenticationRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class AuthRepoModule {

    @Provides
    @Singleton
    public AuthenticationRepository provideAuthRepository() {
        return new AuthenticationRepository();
    }
}
