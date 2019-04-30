package co.alexdev.winy.feature.util.bindings;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.databinding.BindingAdapter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class AutocompleteBindings {

    @BindingAdapter({"autocompleteItems"})
    public static void setAutocompleteItems(AutoCompleteTextView autoCompleteTextView,
                                            List<String> items) {
        if (items != null && items.size() > 0) {
            LinkedHashSet<String> noDuplicatesHashSet = new LinkedHashSet<>(items);
            ArrayList<String> noDuplicatesList = new ArrayList<>(noDuplicatesHashSet);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(autoCompleteTextView.getContext(),
                    android.R.layout.select_dialog_item, noDuplicatesList);
            autoCompleteTextView.setAdapter(arrayAdapter);
            autoCompleteTextView.setThreshold(2);
        }
    }
}
