package co.alexdev.winy.feature.ui.login.uimodel;

import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.core.model.user.UserInformation;
import co.alexdev.winy.core.util.Constants;
import co.alexdev.winy.core.util.Validator;

public class ActivityLoginViewModel extends ViewModel implements LifecycleObserver {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public MutableLiveData<Enum> authLayoutStateLiveData = new MutableLiveData<>();
    public String loginMessage = "";

    private Constants.FIREBASE_DATABASE.LOGIN_STATE loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.NOT_SET;

    public UserCredential userCredential;
    public UserInformation userInformation = new UserInformation();
    public String userMessage;
    public MutableLiveData<Enum> loginStateEnumLiveData = new MutableLiveData<>();
    public MutableLiveData<Enum> signupStateEnumLiveData = new MutableLiveData<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private Constants.FIREBASE_DATABASE.SIGNUP_STATE signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.NOT_SET;
    private Constants.AUTH_LAYOUT_STATE layoutState = Constants.AUTH_LAYOUT_STATE.LOGIN;
    private String userUID;


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

    public void forgotPassword() {

    }

    public void showLayout() {
        switch (layoutState) {

            case LOGIN:
                layoutState = Constants.AUTH_LAYOUT_STATE.SIGNUP;
                break;

            case SIGNUP:
                layoutState = Constants.AUTH_LAYOUT_STATE.LOGIN;
                break;
        }

        authLayoutStateLiveData.setValue(layoutState);
    }

    public void signupUser() {
        signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.STARTED;
        signupStateEnumLiveData.setValue(signupState);

        if (!Validator.isEmailValid(userCredential.getEmail()) || !Validator.isPasswordValid(userCredential.getPassword())
                && !Validator.isFirstNameValid(userInformation.getFirstname()) || !Validator.isLastNameValid(userInformation.getLastname())) {
            signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.FAILURE;
            userMessage = Constants.FIREBASE_DATABASE.MESSAGES.ERROR;
            signupStateEnumLiveData.setValue(signupState);
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(userCredential.getEmail(), userCredential.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!TextUtils.isEmpty(userUID)) {
                            firebaseDatabase.getReference().child(Constants.FIREBASE_DATABASE.USER_REFERENCE)
                                    .child(userUID)
                                    .setValue(userInformation);
                            userMessage = Constants.FIREBASE_DATABASE.MESSAGES.SUCCES;
                            signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.SUCCES;
                        }
                    } else {
                        signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.FAILURE;
                        userMessage = Objects.requireNonNull(task.getException()).getMessage();
                    }
                    signupStateEnumLiveData.setValue(signupState);
                });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void registerAuthStateListener() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void checkIfUserHasLogged() {
        authStateListener = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                userUID = firebaseAuth.getCurrentUser().getUid();
            }
        };
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void unregisterAuthStateListener() {
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
