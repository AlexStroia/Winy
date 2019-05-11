package co.alexdev.winy;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

import co.alexdev.winy.feature.ui.product.Activity;

public class WinnyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Check if is already a logged user
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Activity.startActivity(this);
        }
    }
}
