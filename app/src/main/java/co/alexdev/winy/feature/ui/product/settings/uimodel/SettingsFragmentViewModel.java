package co.alexdev.winy.feature.ui.product.settings.uimodel;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragmentViewModel extends ViewModel {

    public void signoutUser() {
        FirebaseAuth.getInstance().signOut();
    }
}
