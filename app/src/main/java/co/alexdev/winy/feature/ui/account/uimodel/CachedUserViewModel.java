package co.alexdev.winy.feature.ui.account.uimodel;

import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.core.model.user.UserInformation;

public class CachedUserViewModel {

    private UserCredential userCredential;
    private UserInformation userInformation;

    public CachedUserViewModel(UserCredential userCredential, UserInformation userInformation) {
        this.userCredential = userCredential;
        this.userInformation = userInformation;
    }

    public UserCredential getUserCredential() {
        return userCredential;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }
}
