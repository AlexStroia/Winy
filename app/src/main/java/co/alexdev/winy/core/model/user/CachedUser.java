package co.alexdev.winy.core.model.user;

public class CachedUser {
    private UserCredential userCredential;
    private UserInformation userInformation;

    public CachedUser(UserCredential userCredential, UserInformation userInformation) {
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
