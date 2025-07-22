package kotlin.internal

import kotlin.jvm.internal.Intrinsics

internal final val IMPLEMENTATIONS: PlatformImplementations

internal fun apiVersionIsAtLeast(major: Int, minor: Int, patch: Int): Boolean {
   return KotlinVersion.CURRENT.isAtLeast(var0, var1, var2);
}

@JvmSynthetic
private inline fun <reified T : Any> castToBaseType(instance: Any): T {
   try {
      Intrinsics.reifiedOperationMarker(1, "T");
      val var1: Any = var0;
      return (T)var0;
   } catch (var4: ClassCastException) {
      var0 = var0.getClass().getClassLoader();
      Intrinsics.reifiedOperationMarker(4, "T");
      val var2: Class = Object::class.java;
      val var6: ClassLoader = Object.class.getClassLoader();
      if (!(var0 == var6)) {
         val var3: StringBuilder = new StringBuilder("Instance class was loaded from a different classloader: ");
         var3.append(var0);
         var3.append(", base type classloader: ");
         var3.append(var6);
         throw new ClassNotFoundException(var3.toString(), var4);
      } else {
         throw var4;
      }
   }
}
