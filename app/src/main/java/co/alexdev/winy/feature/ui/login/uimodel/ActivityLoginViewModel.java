package co.alexdev.winy.feature.ui.login.uimodel;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.core.util.Constants;
import co.alexdev.winy.core.util.Validator;

public class ActivityLoginViewModel extends ViewModel implements LifecycleObserver {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private Constants.FIREBASE_DATABASE.LOGIN_STATE loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.NOT_SET;
    public String loginMessage = "";

    public UserCredential userCredential;

    public MutableLiveData<Enum> loginStateEnumLiveData = new MutableLiveData<>();

    public ActivityLoginViewModel(UserCredential userCredential) {
        this.userCredential = userCredential;
        this.userCredential.setEmail("");
        this.userCredential.setPassword("");
    }

    public void loginUser() {
        loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.LOADING;
        loginStateEnumLiveData.setValue(loginState);

        if (!Validator.isEmailValid(userCredential.getEmail())) {
            loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.FAILURE;
            loginMessage = Constants.FIREBASE_DATABASE.MESSAGES.EMAIL;
            loginStateEnumLiveData.setValue(loginState);
            return;
        }
        if (!Validator.isPasswordValid(userCredential.getPassword())) {
            loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.FAILURE;
            loginMessage = Constants.FIREBASE_DATABASE.MESSAGES.ERROR;
            loginStateEnumLiveData.setValue(loginState);
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(userCredential.getEmail(), userCredential.getPassword()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.SUCCESS;
            } else {
                loginMessage = Objects.requireNonNull(task.getException()).getMessage();
                loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.FAILURE;
            }
            loginStateEnumLiveData.setValue(loginState);
        });
    }
}
