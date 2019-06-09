package co.alexdev.winy.feature.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.alexdev.winy.core.di.SingletoneScope;

@SingletoneScope
public class KeyboardManager {

    private Context context;

    @Inject
    public KeyboardManager(Context context) {
        this.context = context;
    }

    public void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
