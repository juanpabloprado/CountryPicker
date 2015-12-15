package com.juanpabloprado.countrypicker.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.juanpabloprado.countrypicker.CountryPicker;
import com.juanpabloprado.countrypicker.CountryPickerListener;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getSupportFragmentManager().beginTransaction()
        .replace(R.id.container,
            CountryPicker.getInstance(new CountryPickerListener() {
              @Override public void onSelectCountry(String name, String code) {
                Toast.makeText(MainActivity.this, "Name: " + name, Toast.LENGTH_SHORT).show();
              }
            }))
        .commit();
  }
}
