package co.alexdev.winy.feature.ui.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.MobileAds;

import co.alexdev.winy.R;
import co.alexdev.winy.databinding.ActivityProductBinding;
import co.alexdev.winy.feature.ui.favorite.FavoriteFragment;
import co.alexdev.winy.feature.ui.product.settings.SettingsFragment;
import co.alexdev.winy.feature.ui.search.SearchActivity;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding mBinding;

    private ProductFragment productFragment = new ProductFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();
    private FavoriteFragment favoriteFragment = new FavoriteFragment();
    private Fragment activeFragment = productFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        mBinding.setLifecycleOwner(this);

        MobileAds.initialize(this, getString(R.string.CA_APP_ID_WINE));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left).add(R.id.fragment_container, settingsFragment).hide(settingsFragment).commit();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left).add(R.id.fragment_container, favoriteFragment).hide(favoriteFragment).commit();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left).add(R.id.fragment_container, productFragment).commit();
        }

        mBinding.bottomNavView.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.favorites) {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left).hide(activeFragment).show(favoriteFragment).commit();
                activeFragment = favoriteFragment;
            } else if (menuItem.getItemId() == R.id.home) {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left).hide(activeFragment).show(productFragment).commit();
                activeFragment = productFragment;
            } else if (menuItem.getItemId() == R.id.settings) {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left).hide(activeFragment).show(settingsFragment).commit();
                activeFragment = settingsFragment;
            }
            menuItem.setChecked(true);
            return false;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ProductActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            SearchActivity.startActivity(this);
        }
        return super.onOptionsItemSelected(item);
    }
}

