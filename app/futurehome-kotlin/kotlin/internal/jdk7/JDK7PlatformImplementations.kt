package kotlin.internal.jdk7

import kotlin.internal.PlatformImplementations

internal open class JDK7PlatformImplementations : PlatformImplementations {
   private fun sdkIsNullOrAtLeast(version: Int): Boolean {
      val var2: Boolean;
      if (JDK7PlatformImplementations.ReflectSdkVersion.sdkVersion != null && JDK7PlatformImplementations.ReflectSdkVersion.sdkVersion < var1) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public override fun addSuppressed(cause: Throwable, exception: Throwable) {
      if (this.sdkIsNullOrAtLeast(19)) {
         var1.addSuppressed(var2);
      } else {
         super.addSuppressed(var1, var2);
      }
   }

   public override fun getSuppressed(exception: Throwable): List<Throwable> {
      val var3: java.util.List;
      if (this.sdkIsNullOrAtLeast(19)) {
         val var2: Array<java.lang.Throwable> = var1.getSuppressed();
         var3 = ArraysKt.asList(var2);
      } else {
         var3 = super.getSuppressed(var1);
      }

      return var3;
   }

   private object ReflectSdkVersion {
      public final val sdkVersion: Int?
   }
}
