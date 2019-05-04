package co.alexdev.winy.feature.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import co.alexdev.winy.R;

public class DetailActivity extends AppCompatActivity {

    private static final String WINE_ID = "WINE_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    public static void startActivity(Context context, int id) {
        context.startActivity(new Intent(context, DetailActivity.class).putExtra(WINE_ID, id));
    }
}
