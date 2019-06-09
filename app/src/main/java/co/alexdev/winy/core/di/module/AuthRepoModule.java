package co.alexdev.winy.core.di.module;

import co.alexdev.winy.core.di.SingletoneScope;
import co.alexdev.winy.core.repository.AuthenticationRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class AuthRepoModule {

    @Provides
    @SingletoneScope
    public AuthenticationRepository provideAuthRepository() {
        return new AuthenticationRepository();
    }
}
