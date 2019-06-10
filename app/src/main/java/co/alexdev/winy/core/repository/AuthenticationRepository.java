package co.alexdev.winy.core.repository;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import co.alexdev.winy.core.model.user.CachedUser;
import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.core.model.user.UserInformation;
import co.alexdev.winy.core.util.Constants;
import co.alexdev.winy.core.util.Validator;

import static co.alexdev.winy.core.util.Constants.FIREBASE_DATABASE.USER_REFERENCE;

public class AuthenticationRepository {

    public CachedUser cachedUser;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private OnUserStateListener listener;
    private OnProfileReceivedListener profileReceivedListener;

    public String userMessage;
    public String loginMessage = "";

    private Constants.FIREBASE_DATABASE.SIGNUP_STATE signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.NOT_SET;
    private Constants.FIREBASE_DATABASE.LOGIN_STATE loginState = Constants.FIREBASE_DATABASE.LOGIN_STATE.NOT_SET;

    public String userUID;
    private UserInformation userInformation = new UserInformation();
    private UserCredential userCredential = new UserCredential();

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
                        firebaseDatabase.getReference().child(USER_REFERENCE)
                                .child(Objects.requireNonNull(task.getResult()).getUser().getUid())
                                .setValue(userInformation);
                        userMessage = Constants.FIREBASE_DATABASE.MESSAGES.SUCCES;
                        signupState = Constants.FIREBASE_DATABASE.SIGNUP_STATE.SUCCES;
                        cachedUser = new CachedUser(userCredential, userInformation);
                        profileReceivedListener.onProfileReceivedListener(cachedUser);
                        listener.onUserSignup(userMessage, signupState);
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
                    userCredential.setEmail(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail());
                    if (cachedUser == null) {
                        cachedUser = new CachedUser(userCredential, userInformation);
                        profileReceivedListener.onProfileReceivedListener(cachedUser);
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
        authStateListener = firebaseAuth -> {
        };
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void unregisterAuthStateListener() {
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    public void checkIfUserHasLogged() {
        if (firebaseAuth.getCurrentUser() != null) {
            userUID = firebaseAuth.getCurrentUser().getUid();
            fetchUserData();
        }
    }

    public void forgotPassword(String email) {
        if (!TextUtils.isEmpty(email)) {
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

    public void setUserStateListener(OnUserStateListener authStateListener) {
        this.listener = authStateListener;
    }

    public void setCachedUserListener(OnProfileReceivedListener listener) {
        this.profileReceivedListener = listener;
    }

    public interface OnUserStateListener {
        void onUserSignup(String userMessage, Constants.FIREBASE_DATABASE.SIGNUP_STATE signup_state);

        void onUserLogin(String loginMessage, Constants.FIREBASE_DATABASE.LOGIN_STATE login_state);

        void onForgotPassword(String message);
    }

    public interface OnProfileReceivedListener {
        void onProfileReceivedListener(CachedUser cachedUser);
    }
}
