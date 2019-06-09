package co.alexdev.winy.core.model.user;

import android.text.TextUtils;

import javax.inject.Singleton;

import co.alexdev.winy.core.di.SingletoneScope;

@SingletoneScope
public class UserCredential {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        if (TextUtils.isEmpty(password)) {
            password = "";
        }
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
