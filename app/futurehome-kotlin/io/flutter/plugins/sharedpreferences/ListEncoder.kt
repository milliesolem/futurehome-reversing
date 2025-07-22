package io.flutter.plugins.sharedpreferences

import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.util.ArrayList

public class ListEncoder : SharedPreferencesListEncoder {
   public override fun decode(listString: String): List<String> {
      var var4: java.util.Collection = (java.util.Collection)new StringListObjectInputStream(new ByteArrayInputStream(Base64.decode(var1, 0))).readObject();
      val var2: java.lang.Iterable = var4 as java.util.List;
      var4 = new ArrayList();

      for (var2 : var2) {
         if (var2 is java.lang.String) {
            var4.add(var2);
         }
      }

      return var4 as MutableList<java.lang.String>;
   }

   public override fun encode(list: List<String>): String {
      val var3: ByteArrayOutputStream = new ByteArrayOutputStream();
      val var2: ObjectOutputStream = new ObjectOutputStream(var3);
      var2.writeObject(var1);
      var2.flush();
      val var4: java.lang.String = Base64.encodeToString(var3.toByteArray(), 0);
      return var4;
   }
}
