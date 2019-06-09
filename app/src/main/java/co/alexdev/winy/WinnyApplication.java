package co.alexdev.winy;

import android.app.Application;

import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.di.module.ContextModule;


public class WinnyApplication extends Application {

    private static WinyComponent winyComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        winyComponent = DaggerWinyComponent.builder().contextModule(new ContextModule(this)).build();
    }

    public static WinyComponent getDaggerComponent() {
        return winyComponent;
    }
}
