package co.alexdev.winy.feature.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import co.alexdev.winy.R;
import co.alexdev.winy.databinding.ActivityProductBinding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_product);

        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mBinding.viewPager));
        mBinding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayout));
    }


    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ProductActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
