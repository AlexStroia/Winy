package co.alexdev.winy.core.model.user;

public class AuthUser {
    private UserCredential userCredential;
    private UserInformation userInformation;

    public AuthUser(UserCredential userCredential, UserInformation userInformation) {
        this.userCredential = userCredential;
        this.userInformation = userInformation;
    }
}
