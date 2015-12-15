package com.juanpabloprado.countrypicker;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CountryPicker extends DialogFragment implements Comparator<Country> {
  private EditText mSearchEditText;
  private CountryAdapter mAdapter;
  protected List<Country> mCountries;
  protected CountryAdapter.CountryPickerListener mListener;

  private static final String DIALOG_TITLE_KEY = "dialogTitle";

  public static CountryPicker getInstance(String dialogTitle,
      CountryAdapter.CountryPickerListener listener) {
    CountryPicker picker = new CountryPicker();
    Bundle bundle = new Bundle();
    bundle.putString(DIALOG_TITLE_KEY, dialogTitle);
    picker.setArguments(bundle);
    picker.mListener = listener;
    return picker;
  }

  public EditText getSearchEditText() {
    return mSearchEditText;
  }

  private List<Country> getAllCountries() {
    List<Country> countries = new ArrayList<Country>();

    for (String countryCode : Locale.getISOCountries()) {
      Country country = new Country();
      country.code = countryCode;
      country.name = new Locale("", countryCode).getDisplayCountry();
      countries.add(country);
    }

    // Sort the all countries list based on country name
    Collections.sort(countries, this);

    return countries;
  }

  /**
   * Create view
   */
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate view
    View view = inflater.inflate(R.layout.country_picker, container, false);

    // Get countries from the json
    mCountries = getAllCountries();

    // Set dialog title if show as dialog
    Bundle args = getArguments();
    if (args != null) {
      String dialogTitle = args.getString(DIALOG_TITLE_KEY);
      getDialog().setTitle(dialogTitle);

      int width = getResources().getDimensionPixelSize(R.dimen.cp_dialog_width);
      int height = getResources().getDimensionPixelSize(R.dimen.cp_dialog_height);
      getDialog().getWindow().setLayout(width, height);
    }

    mSearchEditText = (EditText) view.findViewById(R.id.country_picker_search);
    RecyclerView countryListView =
        (RecyclerView) view.findViewById(R.id.country_picker_recycler_view);

    mAdapter = new CountryAdapter(this, mCountries);
    countryListView.setAdapter(mAdapter);

    // Search for which countries matched user query
    mSearchEditText.addTextChangedListener(new TextWatcher() {

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override public void afterTextChanged(Editable s) {
        search(s.toString());
      }
    });

    return view;
  }

  private void search(String text) {

    ArrayList<Country> selectedCountries = new ArrayList<Country>();
    for (Country country : mCountries) {
      if (country.name.toLowerCase(Locale.ENGLISH).contains(text.toLowerCase())) {
        selectedCountries.add(country);
      }
    }
    mAdapter.refill(selectedCountries);
  }

  /**
   * Support sorting the countries list
   */
  @Override public int compare(Country lhs, Country rhs) {
    return lhs.name.compareTo(rhs.name);
  }
}
