package co.alexdev.winy.core.util;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import javax.inject.Inject;

import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.di.module.ContextModule;
import co.alexdev.winy.feature.widget.WinyWidget;

public class WinyWidgetService extends IntentService {

    public static final String WINY_WIDGET_SERVICE = "WINY_WIDGET_SERVICE";

    @Inject
    public PreferenceManager preferenceManager;

    public WinyWidgetService(String name) {
        super(name);
    }

    public WinyWidgetService() {
        super("WinyWidgetService");
    }

    public static void startIntentAction(Context context) {
        Intent intent = new Intent(context, WinyWidgetService.class);
        intent.setAction(WINY_WIDGET_SERVICE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetsIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, WinyWidget.class));
        WinyWidget.updateWidget(this, appWidgetManager, preferenceManager.getWines(), appWidgetsIds);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        WinyComponent winyComponent = DaggerWinyComponent.builder().contextModule(new ContextModule(this))
                .build();
        winyComponent.inject(this);
    }
}
