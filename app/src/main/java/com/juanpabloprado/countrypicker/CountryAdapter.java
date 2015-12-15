package com.juanpabloprado.countrypicker;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.Locale;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder> {

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

  public CountryAdapter(CountryPicker countryPicker, List<Country> countries) {
    mCountryPicker = countryPicker;
    mInflater = LayoutInflater.from(countryPicker.getActivity());
    mListener = countryPicker.mListener;
    mCountries = countries;
  }

  @Override public CountryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new CountryHolder(mInflater.inflate(R.layout.row, parent, false));
  }

  @Override public void onBindViewHolder(CountryHolder holder, int position) {
    final Country country = mCountries.get(position);

    holder.textView.setText(country.name);

    String drawableName = "flag_" + country.code.toLowerCase(Locale.ENGLISH);
    int drawableId = mCountryPicker.getResources()
        .getIdentifier(drawableName, "drawable", mCountryPicker.getActivity().getPackageName());
    holder.imageView.setImageDrawable(
        ContextCompat.getDrawable(mCountryPicker.getActivity(), drawableId));

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mListener.onSelectCountry(country.name, country.code);
      }
    });
  }

  @Override public long getItemId(int arg0) {
    return 0;
  }

  @Override public int getItemCount() {
    return mCountries.size();
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
    mCountries.clear();
    mCountries.addAll(countries);
    notifyDataSetChanged();
  }
}