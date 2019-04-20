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
import co.alexdev.winy.core.UserCredential;
import co.alexdev.winy.core.UserInformation;
import co.alexdev.winy.utils.Constants;

public class SignupActivityViewModel extends ViewModel implements LifecycleObserver {

    public UserCredential userCredential;
    public UserInformation userInformation = new UserInformation();
    private String userUID;
    private String userMessage;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;

    private int signupState = Constants.SIGNUP_STATE.NOT_SET;
    public MutableLiveData<String> signupLiveDataMessage = new MutableLiveData();

    public void signupUser() {
        signupState = Constants.SIGNUP_STATE.STARTED;
        firebaseAuth.createUserWithEmailAndPassword(userCredential.email, userCredential.password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        signupState = Constants.SIGNUP_STATE.SUCCES;
                        if (!TextUtils.isEmpty(userUID)) {
                            firebaseDatabase.getReference().child(Constants.FIREBASE_DATABASE.USER_REFERENCE)
                                    .child(userUID)
                                    .setValue(userInformation);
                            userMessage = Constants.FIREBASE_DATABASE.MESSAGES.SUCCES;
                        }
                    } else {
                        userMessage = Objects.requireNonNull(task.getException()).getMessage();
                    }
                });
        signupLiveDataMessage.setValue(userMessage);
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

