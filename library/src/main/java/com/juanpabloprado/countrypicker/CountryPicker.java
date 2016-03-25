package com.juanpabloprado.countrypicker;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class CountryPicker extends DialogFragment {
  private CountryAdapter mAdapter;
  private CountryPickerListener mListener;

  static List<Country> countries = getAllCountries();
  private List<String> userCountryCodes;
  private static final String DIALOG_TITLE_KEY = "dialogTitle";

  public static CountryPicker getInstance(String dialogTitle, CountryPickerListener listener) {
    CountryPicker picker = getInstance(listener);
    Bundle bundle = new Bundle();
    bundle.putString(DIALOG_TITLE_KEY, dialogTitle);
    picker.setArguments(bundle);
    return picker;
  }

  public static CountryPicker getInstance(CountryPickerListener listener) {
    CountryPicker picker = new CountryPicker();
    picker.mListener = listener;
    return picker;
  }

  public static CountryPicker getInstance(CountryPickerListener listener,
      List<String> userCountryCodes) {
    CountryPicker picker = getInstance(listener);
    picker.userCountryCodes = userCountryCodes;
    return picker;
  }

  private static List<Country> getAllCountries() {
    List<Country> countries = new ArrayList<Country>();

    for (String countryCode : Locale.getISOCountries()) {
      Country country = new Country();
      country.code = countryCode;
      country.name = new Locale("", countryCode).getDisplayCountry();
      countries.add(country);
    }

    return countries;
  }

  private static List<Country> getAllCountries(List<String> userCountryCodes) {
    List<Country> countries = new ArrayList<Country>();

    for (String countryCode : Locale.getISOCountries()) {
      if (userCountryCodes.contains(countryCode)) {
        Country country = new Country();
        country.code = countryCode;
        country.name = new Locale("", countryCode).getDisplayCountry();
        countries.add(country);
      }
    }

    return countries;
  }

  /**
   * Create view
   */
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.country_picker, container, false);

    // Set dialog title if show as dialog
    Bundle args = getArguments();
    if (args != null) {
      String dialogTitle = args.getString(DIALOG_TITLE_KEY);
      getDialog().setTitle(dialogTitle);

      int width = getResources().getDimensionPixelSize(R.dimen.cp_dialog_width);
      int height = getResources().getDimensionPixelSize(R.dimen.cp_dialog_height);
      getDialog().getWindow().setLayout(width, height);
    }

    EditText searchEditText = (EditText) view.findViewById(R.id.country_picker_search);
    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.country_picker_recycler_view);

    // check if user wants all countries or just specified
    if (userCountryCodes == null) {
      countries = getAllCountries();
    } else {
      countries = getAllCountries(userCountryCodes);
    }

    // Sort the countries based on country name
    Collections.sort(countries);

    // setup recyclerView
    recyclerView.setLayoutManager(
        new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    mAdapter = new CountryAdapter(this, mListener);
    recyclerView.setAdapter(mAdapter);
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
          super.onScrollStateChanged(recyclerView, newState);
          hideKeyboard();
      }
    });

    // Search for which countries matched user query
    searchEditText.addTextChangedListener(new TextWatcher() {

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override public void afterTextChanged(Editable s) {
        mAdapter.getFilter().filter(s);
      }
    });

    return view;
  }
  
  /**
  * Method to hide keyboard if it's open
  */
  public void hideKeyboard() {
    try {
      Activity activity = getActivity();
      if (activity == null) {
        return;
      }
      InputMethodManager input
            = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
      if (input == null) {
        return;
      }
      View currentFocus = activity.getCurrentFocus();
      if (currentFocus == null || currentFocus.getWindowToken() == null) {
        return;
      }

      input.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    } catch (Exception e) {
        // ignoring any exceptions
    }
  }
}
