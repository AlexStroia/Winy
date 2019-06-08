package co.alexdev.winy.feature.ui.account;

import androidx.appcompat.app.AppCompatActivity;

import co.alexdev.winy.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }
}
