CountryPicker
=============
CountryPicker is an Android library created to show a custom fragment which allows to choose a country.

Screenshots
-----------
![Demo Screenshot][1]

Installation
------------

Add CountryPicker dependency to your build.gradle

```groovy

dependencies{
    compile 'com.juanpabloprado:countrypicker:1.0.1'
}

```

Or add CountryPicker as a new dependency inside your pom.xml

```xml

<dependency>
    <groupId>com.juanpabloprado</groupId>
    <artifactId>countrypicker</artifactId>
    <version>1.0.1</version>
    <type>aar</type>
</dependency>

```

## How to use

To embed CountryPicker in your own activity:

```java
getSupportFragmentManager().beginTransaction()
        .replace(R.id.container,
            CountryPicker.getInstance(new CountryPickerListener() {
              @Override public void onSelectCountry(String name, String code) {
                Toast.makeText(MainActivity.this, "Name: " + name, Toast.LENGTH_SHORT).show();
              }
            }))
        .commit();
```

Developed By
------------

* Juan Pablo Prado - <juan@juanpabloprado.com>

<a href="https://twitter.com/juanpablosprado">
  <img alt="Follow me on Twitter" src="http://imageshack.us/a/img812/3923/smallth.png" />
</a>
<a href="https://www.linkedin.com/in/juanpabloprado">
  <img alt="Add me to Linkedin" src="http://imageshack.us/a/img41/7877/smallld.png" />
</a>

License
-------

    Copyright 2015 Juan Pablo Prado

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: ./art/screenshot.png
