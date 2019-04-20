package co.alexdev.winy.feature.ui.signup.uimodel;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import co.alexdev.winy.core.model.UserCredential;
import co.alexdev.winy.core.model.UserInformation;
import co.alexdev.winy.core.util.Constants;
import co.alexdev.winy.core.util.Validator;

public class SignupActivityViewModel extends ViewModel implements LifecycleObserver {

    public UserCredential userCredential;
    public UserInformation userInformation = new UserInformation();
    private String userUID;
    public String userMessage = "";

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private Constants.FIREBASE_DATABASE.SIGNUP_STATE signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.NOT_SET;

    public MutableLiveData<Enum> signupStateEnumLiveData = new MutableLiveData<>();

    public void signupUser() {
        signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.STARTED;
        signupStateEnumLiveData.setValue(signupState);

        if (!Validator.isEmailValid(userCredential.email) || !Validator.isPasswordValid(userCredential.password)
                && !Validator.isFirstNameValid(userInformation.getFirstname()) || !Validator.isLastNameValid(userInformation.getLastname())) {
            signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.FAILURE;
            userMessage = Constants.FIREBASE_DATABASE.MESSAGES.ERROR;
            signupStateEnumLiveData.setValue(signupState);
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(userCredential.email, userCredential.password)
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

