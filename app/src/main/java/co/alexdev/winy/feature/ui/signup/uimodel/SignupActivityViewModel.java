package co.alexdev.winy.feature.ui.signup.uimodel;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import co.alexdev.winy.core.UserCredential;
import co.alexdev.winy.core.UserInformation;
import co.alexdev.winy.utils.Constants;

public class SignupActivityViewModel extends ViewModel {

    public UserCredential userCredential;
    public UserInformation userInformation;
    private String userUID;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private int signupState = Constants.SIGNUP_STATE.NOT_SET;
    public MutableLiveData<Integer> signupStateLiveData = new MutableLiveData();

    public void signupUser() {
        signupState = Constants.SIGNUP_STATE.STARTED;
        firebaseAuth.createUserWithEmailAndPassword(userCredential.email, userCredential.password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        signupState = Constants.SIGNUP_STATE.SUCCES;
                        if (firebaseAuth.getCurrentUser() != null) {
                            userUID = firebaseAuth.getCurrentUser().getUid();
                            firebaseDatabase.getReference().child(Constants.FIREBASE_DATABASE.USER_REFERENCE)
                                    .child(userUID)
                                    .setValue(userInformation);
                        }
                    } else {
                        Log.d("SignupActivityViewModel", "Message: " + task.getException().getMessage());
                        signupState = Constants.SIGNUP_STATE.FAILED;
                    }
                });
        signupStateLiveData.setValue(signupState);
    }
}

