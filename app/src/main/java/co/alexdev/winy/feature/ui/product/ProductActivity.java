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

        mBinding.bottomNavView.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.favorites) {
                changeFragment(new FavoriteFragment());
            }
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

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }
}
