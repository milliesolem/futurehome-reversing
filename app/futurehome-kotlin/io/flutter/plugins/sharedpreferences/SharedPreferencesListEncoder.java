package io.flutter.plugins.sharedpreferences;

import java.util.List;

public interface SharedPreferencesListEncoder {
   List<String> decode(String var1);

   String encode(List<String> var1);
}
