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
import co.alexdev.winy.feature.ui.product.settings.SettingsFragment;
import co.alexdev.winy.feature.ui.search.SearchActivity;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding mBinding;
    private String TAG = "";

    private ProductFragment productFragment = new ProductFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();
    private FavoriteFragment favoriteFragment = new FavoriteFragment();
    private Fragment activeFragment = productFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        mBinding.setLifecycleOwner(this);

        if (savedInstanceState == null) {


            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left).add(R.id.fragment_container, settingsFragment).hide(settingsFragment).commit();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left).add(R.id.fragment_container, favoriteFragment).hide(favoriteFragment).commit();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left).add(R.id.fragment_container, productFragment).commit();

            //  handleFragmentChanging(productFragment);
        }

        mBinding.bottomNavView.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.favorites) {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left).hide(activeFragment).show(favoriteFragment).commit();
                activeFragment = favoriteFragment;
                // handleFragmentChanging(new FavoriteFragment());
            } else if (menuItem.getItemId() == R.id.home) {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left).hide(activeFragment).show(productFragment).commit();
                activeFragment = productFragment;
                //   handleFragmentChanging(new ProductFragment());
            } else if (menuItem.getItemId() == R.id.settings) {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left).hide(activeFragment).show(settingsFragment).commit();
                activeFragment = settingsFragment;
                //     handleFragmentChanging(new SettingsFragment());
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

//    private void handleFragmentChanging(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left);
//
//        Fragment currentFragment = getSupportFragmentManager().getPrimaryNavigationFragment();
//        if (currentFragment != null) {
//            transaction.detach(fragment);
//        }
//
//        Fragment fragmentTemp = getSupportFragmentManager().findFragmentByTag(TAG);
//
//        if (fragmentTemp == null) {
//            fragmentTemp = fragment;
//            if (fragmentTemp instanceof ProductFragment) {
//                TAG = ProductFragment.TAG;
//                transaction.add(R.id.fragment_container, fragmentTemp, TAG);
//            } else if (fragment instanceof FavoriteFragment) {
//                TAG = FavoriteFragment.TAG;
//                transaction.add(R.id.fragment_container, fragment, TAG);
//            } else if (fragment instanceof SettingsFragment) {
//                TAG = SettingsFragment.TAG;
//                transaction.add(R.id.fragment_container, fragment, TAG);
//            }
//        } else {
//            transaction.attach(fragmentTemp);
//        }
//
//        transaction.setPrimaryNavigationFragment(fragmentTemp).commit();
//
////        if (fragment instanceof ProductFragment) {
////            TAG = ProductFragment.TAG;
////            transaction.add(R.id.fragment_container, fragment, TAG).commit();
////        } else if (fragment instanceof FavoriteFragment) {
////            transaction.replace(R.id.fragment_container, fragment, FavoriteFragment.TAG).commit();
////        } else if (fragment instanceof SettingsFragment) {
////            transaction.replace(R.id.fragment_container, fragment, SettingsFragment.TAG).commit();
////        }
//    }
}

