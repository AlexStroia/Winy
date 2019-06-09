package co.alexdev.winy.feature.util.bindings;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import co.alexdev.winy.core.model.user.CachedUser;

public class SettingsBindings {

    @BindingAdapter({"showOrHide"})
    public static void showOrHideLayout(ConstraintLayout constraintLayout, MutableLiveData<CachedUser> cachedUser) {
        constraintLayout.setVisibility(cachedUser.getValue() == null ? View.GONE : View.VISIBLE);
    }
}
