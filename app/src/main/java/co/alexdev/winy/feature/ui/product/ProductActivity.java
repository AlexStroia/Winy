package co.alexdev.winy.feature.ui.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import co.alexdev.winy.R;
import co.alexdev.winy.databinding.ActivityProductBinding;
import co.alexdev.winy.feature.ui.favorite.FavoriteFragment;
import co.alexdev.winy.feature.ui.search.SearchActivity;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        mBinding.setLifecycleOwner(this);

        if (savedInstanceState == null) {
            handleReplace(new ProductFragment());
        }

        mBinding.bottomNavView.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.favorites) {
                handleReplace(new FavoriteFragment());
            } else if (menuItem.getItemId() == R.id.home) {
                handleReplace(new ProductFragment());
            }
            menuItem.setChecked(true);
            return false;
        });
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

    private void handleReplace(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
