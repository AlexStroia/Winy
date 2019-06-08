package co.alexdev.winy.core.repository;


import co.alexdev.winy.core.model.user.AuthUser;
import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.core.model.user.UserInformation;

public class AuthenticationRepository {

    AuthUser authUser;

    public void setAuthUser(UserCredential credential, UserInformation information) {
        if (authUser == null) {
            authUser = new AuthUser(credential, information);
        }
    }
}
