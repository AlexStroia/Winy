package co.alexdev.winy.feature.ui.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import co.alexdev.winy.R;
import co.alexdev.winy.databinding.ActivityProductBinding;
import co.alexdev.winy.feature.ui.favorite.FavoriteFragment;
import co.alexdev.winy.feature.ui.product.settings.SettingsFragment;
import co.alexdev.winy.feature.ui.search.SearchActivity;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding mBinding;
    private Fragment productFragment = new ProductFragment();
    private Fragment favoritesFragment = new FavoriteFragment();
    private Fragment settingsFragment = new SettingsFragment();
    private Fragment activeFragment = productFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        mBinding.setLifecycleOwner(this);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, favoritesFragment).hide(favoritesFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, settingsFragment).hide(settingsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, productFragment).commit();

        if (savedInstanceState == null) {
            handleFragmentChanging(productFragment);
        }

        mBinding.bottomNavView.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.favorites) {
                handleFragmentChanging(favoritesFragment);
            } else if (menuItem.getItemId() == R.id.home) {
                handleFragmentChanging(productFragment);
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

    private void handleFragmentChanging(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().hide(activeFragment)
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left);
        if (fragment instanceof ProductFragment) {
            transaction.show(productFragment).commit();
        } else if (fragment instanceof FavoriteFragment) {
            transaction.show(favoritesFragment).commit();
        } else if (fragment instanceof SettingsFragment) {
            transaction.show(settingsFragment).commit();
        }
        activeFragment = fragment;
    }
}
