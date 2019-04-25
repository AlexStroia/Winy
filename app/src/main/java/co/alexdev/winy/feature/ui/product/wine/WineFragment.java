package co.alexdev.winy.feature.ui.product.wine;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import co.alexdev.winy.R;
import co.alexdev.winy.databinding.FragmentWineBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WineFragment extends Fragment {

    private FragmentWineBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wine, container, false);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }
}
