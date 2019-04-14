package co.alexdev.winy.feature.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import co.alexdev.winy.R;
import co.alexdev.winy.databinding.ActivityProductBinding;

import android.os.Bundle;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_product);

        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }
}
