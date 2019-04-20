package co.alexdev.winy.feature.ui.signup.uimodel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import co.alexdev.winy.core.UserCredential;
import co.alexdev.winy.core.UserInformation;
import co.alexdev.winy.utils.Constants;

public class SignupActivityViewModel extends ViewModel {

    public UserCredential userCredential = new UserCredential();
    public UserInformation userInformation = new UserInformation();

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
                    } else {
                        signupState = Constants.SIGNUP_STATE.FAILED;
                    }
                });
        signupStateLiveData.setValue(signupState);
    }
}

