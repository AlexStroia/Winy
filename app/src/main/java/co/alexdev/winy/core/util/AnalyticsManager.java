package co.alexdev.winy.core.util;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class AnalyticsManager {

    private static final String USER_SIGN_UP = "USER_SIGN_UP";
    private static final String USER_LOG_IN = "USER_LOG_IN";
    private static final String USER_SEARCH_FOOD = "USER_SEARCH_FOOD";
    private static final String USER_SEARCH_WINE = "USER_SEARCH_WINE";

    @Inject
    public Context context;
    private FirebaseAnalytics analytics;


    public AnalyticsManager(Context context) {
        this.context = context;
        this.analytics = FirebaseAnalytics.getInstance(context);
    }

    public void signup(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(USER_SIGN_UP, String.valueOf(id));
        analytics.logEvent(USER_SIGN_UP, bundle);
    }

    public void login(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(USER_LOG_IN, id);
        analytics.logEvent(USER_LOG_IN, bundle);
    }

    public void searchFood(String food) {
        Bundle bundle = new Bundle();
        bundle.putString(USER_SEARCH_FOOD, food);
        analytics.logEvent(USER_SEARCH_FOOD, bundle);
    }

    public void searchWine(String wine) {
        Bundle bundle = new Bundle();
        bundle.putString(USER_SEARCH_WINE, wine);
        analytics.logEvent(USER_SEARCH_WINE, bundle);
    }
}
