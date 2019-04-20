package co.alexdev.winy.feature.ui.loginsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import co.alexdev.winy.R;
import co.alexdev.winy.databinding.ActivityLoginSignupBinding;
import co.alexdev.winy.feature.ui.login.ActivityLogin;
import co.alexdev.winy.feature.ui.signup.SignupActivity;

import android.os.Bundle;

public class LoginSignupActivity extends AppCompatActivity {

    private ActivityLoginSignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_signup);
        binding.btnSignup.setOnClickListener((view) -> SignupActivity.startActivity(this));
        binding.btnLogin.setOnClickListener((view) -> ActivityLogin.startActivity(this));
    }
}
