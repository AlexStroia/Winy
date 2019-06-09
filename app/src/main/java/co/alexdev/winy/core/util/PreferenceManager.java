package co.alexdev.winy.core.util;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import co.alexdev.winy.core.di.SingletoneScope;

@SingletoneScope
public class PreferenceManager {

    public static final String WINES = "WINES";
    private SharedPreferences preferenceManager;

    public PreferenceManager(Context context) {
        preferenceManager = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveWines(String wines) {
        preferenceManager.edit().putString(WINES, wines).apply();
    }

    public String getWines() {
        return preferenceManager.getString(WINES, "Not found");
    }
}
