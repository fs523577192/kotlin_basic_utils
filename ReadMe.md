# Kotlin Basic Utils
This library is the migration of some basic utils from Java to Kotlin. Such basic utils includes `Character.isDigit(Char)`, `Character.codePointAt(CharSequence, Int)`, `StringBuilder.setLength(Int)`, `StringBuilder.insert(Int, CharSequence)`, `Arrays.binarySearch(IntArray, Int)`, etc.

This library also contains some polyfills of Java for a Kotlin multi-platform project, such as `synchronized`, `ConcurrentHashMap` and `Locale`.
With these polyfills we can migrate Java API to Kotlin more easily.

## License
Some codes in this library are migrated from OpenJDK and they are protected by the GNU General Public License version 2 (GPLv2).

Some other codes in this library are migrated from Apache Commons and Spring Framework, and they are protected by the (https://www.apache.org/licenses/LICENSE-2.0.html)[Apache License version 2.0].

Other codes (mainly written by me) are also under the (https://www.apache.org/licenses/LICENSE-2.0.html)[Apache License v2].