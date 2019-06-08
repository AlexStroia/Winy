package co.alexdev.winy.feature.ui.login.uimodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.core.model.user.UserInformation;
import co.alexdev.winy.core.repository.AuthenticationRepository;
import co.alexdev.winy.core.util.AnalyticsManager;
import co.alexdev.winy.core.util.Constants;

public class ActivityLoginViewModel extends ViewModel implements LifecycleObserver, AuthenticationRepository.OnUserStateListener {

    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public MutableLiveData<Enum> authLayoutStateLiveData = new MutableLiveData<>();
    public String loginMessage = "";

    public UserCredential userCredential = new UserCredential();
    public UserInformation userInformation = new UserInformation();
    public String userMessage;
    public MutableLiveData<Enum> loginStateEnumLiveData = new MutableLiveData<>();
    public MutableLiveData<Enum> signupStateEnumLiveData = new MutableLiveData<>();
    public MutableLiveData<String> forgotPassLiveData = new MutableLiveData<>();
    private Constants.AUTH_LAYOUT_STATE layoutState = Constants.AUTH_LAYOUT_STATE.LOGIN;

    private AuthenticationRepository authenticationRepository;

    private AnalyticsManager analyticsManager;

    public ActivityLoginViewModel(AnalyticsManager analyticsManager, AuthenticationRepository authenticationRepository) {
        this.userCredential.setEmail("");
        this.userCredential.setPassword("");
        this.analyticsManager = analyticsManager;
        this.authenticationRepository = authenticationRepository;
        authenticationRepository.setListener(this);
    }

    public void loginUser() {
        authenticationRepository.loginUser(userCredential);
    }

    public void forgotPassword(String email) {
        authenticationRepository.forgotPassword(email);
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
        authenticationRepository.signUp(userCredential, userInformation);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void registerAuthStateListener() {
        authenticationRepository.registerAuthStateListener();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void checkIfUserHasLogged() {
        authenticationRepository.checkIfUserHasLogged();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void unregisterAuthStateListener() {
        authenticationRepository.unregisterAuthStateListener();
    }

    @Override
    public void onUserSignup(String userMessage, Constants.FIREBASE_DATABASE.SIGNUP_STATE signup_state) {
        this.userMessage = userMessage;
        analyticsManager.signup(authenticationRepository.userUID);
        signupStateEnumLiveData.setValue(signup_state);
    }

    @Override
    public void onUserLogin(String loginMessage, Constants.FIREBASE_DATABASE.LOGIN_STATE login_state) {
        this.loginMessage = loginMessage;
        analyticsManager.login(authenticationRepository.userUID);
        loginStateEnumLiveData.setValue(login_state);
    }

    @Override
    public void onForgotPassword(String message) {
        forgotPassLiveData.setValue(message);
    }
}
