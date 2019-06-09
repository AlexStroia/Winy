package co.alexdev.winy.core.repository;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.alexdev.winy.core.model.user.CachedUser;
import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.core.model.user.UserInformation;
import co.alexdev.winy.core.util.Constants;
import co.alexdev.winy.core.util.Validator;

import static co.alexdev.winy.core.util.Constants.FIREBASE_DATABASE.USER_REFERENCE;

@Singleton
public class AuthenticationRepository {

    public CachedUser cachedUser;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private OnUserStateListener listener;

    public String userMessage;
    public String loginMessage = "";

    private Constants.FIREBASE_DATABASE.SIGNUP_STATE signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.NOT_SET;
    private Constants.FIREBASE_DATABASE.LOGIN_STATE loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.NOT_SET;

    public String userUID;
    private UserInformation userInformation = new UserInformation();
    private UserCredential userCredential = new UserCredential();

    @Inject
    public AuthenticationRepository() {
    }

    public void signUp(UserCredential userCredential, UserInformation userInformation) {
        signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.STARTED;
        listener.onUserSignup("", signupState);

        if (!Validator.isEmailValid(userCredential.getEmail()) || !Validator.isPasswordValid(userCredential.getPassword())
                && !Validator.isFirstNameValid(userInformation.getFirstname()) || !Validator.isLastNameValid(userInformation.getLastname())) {
            signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.FAILURE;
            userMessage = Constants.FIREBASE_DATABASE.MESSAGES.ERROR;
            listener.onUserSignup(userMessage, signupState);
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(userCredential.getEmail(), userCredential.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!TextUtils.isEmpty(userUID)) {
                            firebaseDatabase.getReference().child(USER_REFERENCE)
                                    .child(userUID)
                                    .setValue(userInformation);
                            userMessage = Constants.FIREBASE_DATABASE.MESSAGES.SUCCES;
                            signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.SUCCES;
                            cachedUser = new CachedUser(userCredential, userInformation);
                            listener.onUserSignup(userMessage, signupState);
                        }
                    } else {
                        signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.FAILURE;
                        userMessage = Objects.requireNonNull(task.getException()).getMessage();
                        listener.onUserSignup(userMessage, signupState);
                    }
                });
    }

    public void loginUser(UserCredential userCredential) {
        loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.LOADING;
        listener.onUserLogin("", loginState);

        if (!Validator.isEmailValid(userCredential.getEmail())) {
            loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.FAILURE;
            loginMessage = Constants.FIREBASE_DATABASE.MESSAGES.EMAIL;
            listener.onUserLogin(loginMessage, loginState);
            return;
        }
        if (!Validator.isPasswordValid(userCredential.getPassword())) {
            loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.FAILURE;
            loginMessage = Constants.FIREBASE_DATABASE.MESSAGES.ERROR;
            listener.onUserLogin(loginMessage, loginState);
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(userCredential.getEmail(), userCredential.getPassword()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.SUCCESS;
                fetchUserData();
            } else {
                loginMessage = Objects.requireNonNull(task.getException()).getMessage();
                loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.FAILURE;
            }
            listener.onUserLogin(loginMessage, loginState);
        });
    }

    private void fetchUserData() {
        FirebaseDatabase.getInstance().getReference().child(USER_REFERENCE).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userInformation = dataSnapshot.getValue(UserInformation.class);
                    userCredential.setEmail(firebaseAuth.getCurrentUser().getEmail());
                    if (cachedUser == null) {
                        cachedUser = new CachedUser(userCredential, userInformation);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loginMessage = databaseError.getMessage();
                loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.FAILURE;
            }
        });
    }

    public void registerAuthStateListener() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void unregisterAuthStateListener() {
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    public void checkIfUserHasLogged() {
        authStateListener = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                userUID = firebaseAuth.getCurrentUser().getUid();
                userCredential.setEmail(firebaseAuth.getCurrentUser().getEmail());
                fetchUserData();
                if (cachedUser == null) {
                    userCredential.setEmail(firebaseAuth.getCurrentUser().getEmail());
                    cachedUser = new CachedUser(userCredential, userInformation);
                }
            }
        };
    }

    public void forgotPassword(String email) {
        if(!TextUtils.isEmpty(email)) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            listener.onForgotPassword(Constants.FIREBASE_DATABASE.MESSAGES.FORGOT_EMAIL_SENT);
                        } else {
                            listener.onForgotPassword(Constants.FIREBASE_DATABASE.MESSAGES.FORGOT_EMAIL_INCORECT);
                        }
                    });
        } else {
            listener.onForgotPassword(Constants.FIREBASE_DATABASE.MESSAGES.EMAIL);
        }
    }

    public void setListener(OnUserStateListener authStateListener) {
        this.listener = authStateListener;
    }

    public interface OnUserStateListener {
        void onUserSignup(String userMessage, Constants.FIREBASE_DATABASE.SIGNUP_STATE signup_state);

        void onUserLogin(String loginMessage, Constants.FIREBASE_DATABASE.LOGIN_STATE login_state);

        void onForgotPassword(String message);
    }
}
