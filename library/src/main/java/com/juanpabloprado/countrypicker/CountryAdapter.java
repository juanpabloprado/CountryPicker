package com.juanpabloprado.countrypicker;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder>
    implements Filterable {

  /**
   * Inform the client which country has been selected
   */
  public interface CountryPickerListener {
    void onSelectCountry(String name, String code);
  }

  private CountryPicker mCountryPicker;
  private LayoutInflater mInflater;
  private CountryPickerListener mListener;
  private List<Country> mCountries;
  private List<Country> mFilteredCountries;

  public CountryAdapter(CountryPicker countryPicker, List<Country> countries, CountryPickerListener listener) {
    mCountryPicker = countryPicker;
    mInflater = LayoutInflater.from(countryPicker.getActivity());
    mListener = listener;
    mCountries = countries;
    mFilteredCountries = new ArrayList<Country>(mCountries);
  }

  @Override public CountryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new CountryHolder(mInflater.inflate(R.layout.row, parent, false));
  }

  @Override public void onBindViewHolder(CountryHolder holder, int position) {
    final Country country = mFilteredCountries.get(position);

    holder.textView.setText(country.name);

    String drawableName = "flag_" + country.code.toLowerCase(Locale.ENGLISH);
    int drawableId = mCountryPicker.getResources()
        .getIdentifier(drawableName, "drawable", mCountryPicker.getActivity().getPackageName());
    if (drawableId != 0) {
      holder.imageView.setImageDrawable(
          ContextCompat.getDrawable(mCountryPicker.getActivity(), drawableId));
    }
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mListener.onSelectCountry(country.name, country.code);
      }
    });
  }

  @Override public int getItemCount() {
    return mFilteredCountries.size();
  }

  public class CountryHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public ImageView imageView;

    public CountryHolder(View itemView) {
      super(itemView);
      textView = (TextView) itemView.findViewById(R.id.row_title);
      imageView = (ImageView) itemView.findViewById(R.id.row_icon);
    }
  }

  public void refill(List<Country> countries) {
    mFilteredCountries.clear();
    mFilteredCountries.addAll(countries);
    notifyDataSetChanged();
  }

  @Override public Filter getFilter() {
    return new Filter() {
      @Override protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null) {

          List<Country> filteredCountries = new ArrayList<Country>();
          for (Country country : mCountries) {
            if (country.name.toLowerCase(Locale.ENGLISH)
                .contains(((String) constraint).toLowerCase())) {
              filteredCountries.add(country);
            }
          }
          results.values = filteredCountries;
          results.count = filteredCountries.size();
        }
        return results;
      }

      @SuppressWarnings("unchecked") @Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
        if (results != null) {
          refill((List<Country>) results.values);
        }
      }
    };
  }
}