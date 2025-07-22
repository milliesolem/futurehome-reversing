package io.flutter.plugins.sharedpreferences

import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectStreamClass

public class StringListObjectInputStream(input: InputStream) : ObjectInputStream(var1) {
   @Throws(java/lang/ClassNotFoundException::class, java/io/IOException::class)
   protected override fun resolveClass(desc: ObjectStreamClass?): Class<*>? {
      val var3: java.util.Set = SetsKt.setOf(
         new java.lang.String[]{"java.util.Arrays$ArrayList", "java.util.ArrayList", "java.lang.String", "[Ljava.lang.String;"}
      );
      val var2: java.lang.String;
      if (var1 != null) {
         var2 = var1.getName();
      } else {
         var2 = null;
      }

      if (var2 != null && !var3.contains(var2)) {
         throw new ClassNotFoundException(var1.getName());
      } else {
         return super.resolveClass(var1);
      }
   }
}
