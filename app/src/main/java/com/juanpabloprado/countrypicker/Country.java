package com.juanpabloprado.countrypicker;

import android.os.Parcel;
import android.os.Parcelable;

public class Country implements Parcelable {
  public String code;
  public String name;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(code);
    dest.writeString(name);
  }

  public static final Creator<Country> CREATOR = new Creator<Country>() {
    public Country createFromParcel(Parcel source) {
      Country country = new Country();
      country.code = source.readString();
      country.name = source.readString();
      return country;
    }

    public Country[] newArray(int size) {
      return new Country[size];
    }
  };
}