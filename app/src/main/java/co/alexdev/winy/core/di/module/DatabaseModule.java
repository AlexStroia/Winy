package co.alexdev.winy.core.di.module;

import dagger.Module;

@Module(includes = ContextModule.class)
public class DatabaseModule {
    private static final String DATABASE_NAME = "WINY_DATABASE";

}
