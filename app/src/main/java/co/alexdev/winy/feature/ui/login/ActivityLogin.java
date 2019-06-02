package co.alexdev.winy.feature.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

import co.alexdev.winy.R;
import co.alexdev.winy.core.di.DaggerWinyComponent;
import co.alexdev.winy.core.di.WinyComponent;
import co.alexdev.winy.core.di.module.ContextModule;
import co.alexdev.winy.core.util.Constants;
import co.alexdev.winy.core.util.factory.LoginViewModelFactory;
import co.alexdev.winy.databinding.ActivityLoginBinding;
import co.alexdev.winy.feature.ui.login.uimodel.ActivityLoginViewModel;
import co.alexdev.winy.feature.ui.product.ProductActivity;

public class ActivityLogin extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ActivityLoginViewModel activityLoginViewModel;

    private static final int GOOGLE_SIGN = 101;

    @Inject
    LoginViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        WinyComponent component = DaggerWinyComponent.builder().contextModule(new ContextModule(this)).build();
        component.inject(this);

        binding.setLifecycleOwner(this);
        activityLoginViewModel = ViewModelProviders.of(this, factory).get(ActivityLoginViewModel.class);
        binding.setViewModel(activityLoginViewModel);
        getLifecycle().addObserver(activityLoginViewModel);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            ProductActivity.startActivity(this);
            finish();
        }

        Spanned alreadyHave = Html.fromHtml(getString(R.string.already_have_account));
        Spanned dontHave = Html.fromHtml(getString(R.string.no_account));
        binding.tvNoAccount.setText(dontHave);

        observeLoginState();

        observeSignupState();

        observeAuthLayoutState(alreadyHave, dontHave);
    }

    private void observeAuthLayoutState(Spanned alreadyHave, Spanned dontHave) {
        activityLoginViewModel.authLayoutStateLiveData.observe(this, enumValue -> {

            if (Constants.AUTH_LAYOUT_STATE.SIGNUP.equals(enumValue)) {
                showRightContent(true, alreadyHave);
            } else if (Constants.AUTH_LAYOUT_STATE.LOGIN.equals(enumValue)) {
                showRightContent(false, dontHave);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initGoogleSignInSignUp();
    }

    private void observeSignupState() {
        activityLoginViewModel.signupStateEnumLiveData.observe(this, signupStateEnum -> {

            if (Constants.FIREBASE_DATABASE.SIGNUP_STATE.SUCCES.equals(signupStateEnum)) {
                ProductActivity.startActivity(this);
                finish();
            } else if (Constants.FIREBASE_DATABASE.SIGNUP_STATE.FAILURE.equals(signupStateEnum)) {
                binding.progressBar.setVisibility(View.GONE);
                Snackbar.make(binding.coordinator, activityLoginViewModel.userMessage, Snackbar.LENGTH_LONG).show();
            } else if (Constants.FIREBASE_DATABASE.SIGNUP_STATE.STARTED.equals(signupStateEnum)) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void observeLoginState() {
        activityLoginViewModel.loginStateEnumLiveData.observe(this, loggedState -> {
            if (Constants.FIREBASE_DATABASE.LOGIN_STATE.SUCCESS.equals(loggedState)) {
                ProductActivity.startActivity(this);
            } else if (Constants.FIREBASE_DATABASE.LOGIN_STATE.LOADING.equals(loggedState)) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
                Snackbar.make(binding.coordinator, activityLoginViewModel.loginMessage, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void initGoogleSignInSignUp() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(this, gso);

        binding.ibGooglePlus.setOnClickListener(view -> {
            Intent intent = client.getSignInIntent();
            startActivityForResult(intent, GOOGLE_SIGN);
        });
    }

    private void showRightContent(boolean showSignup, Spanned message) {
        if (showSignup) {
            binding.switcher.showNext();
        } else {
            binding.switcher.showPrevious();
        }
        binding.tvNoAccount.setText(message);
    }

    private void handleSignInResult(GoogleSignInAccount signInAccount) {

        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        activityLoginViewModel.firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = activityLoginViewModel.firebaseAuth.getCurrentUser();
                        //Cache the user into the repo
                        ProductActivity.startActivity(this);
                    } else {
                        Snackbar.make(binding.coordinator, Constants.FIREBASE_DATABASE.MESSAGES.AUTHENTICATION_FAILED, Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ActivityLogin.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account;
            try {
                account = task.getResult(ApiException.class);
                handleSignInResult(account);
            } catch (ApiException e) {
                Log.d("ActivityLogin", "Google sign in failed", e);
                e.printStackTrace();
            }
        }
    }
}
