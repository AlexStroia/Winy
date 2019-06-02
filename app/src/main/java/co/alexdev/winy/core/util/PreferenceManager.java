package co.alexdev.winy.core.util;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

@Singleton
public class PreferenceManager {

    public static final String WINES = "WINES";
    private Context context;
    private SharedPreferences preferenceManager;

    public PreferenceManager(Context context) {
        this.context = context;
        preferenceManager = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveWines(String wines) {
        preferenceManager.edit().putString(WINES, wines).apply();
    }

    public String getWines() {
        return preferenceManager.getString(WINES, "Not found");
    }
}
