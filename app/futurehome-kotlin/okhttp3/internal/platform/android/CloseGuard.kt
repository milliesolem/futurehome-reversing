package okhttp3.internal.platform.android

import java.lang.reflect.Method
import kotlin.jvm.internal.Intrinsics

internal class CloseGuard(getMethod: Method?, openMethod: Method?, warnIfOpenMethod: Method?) {
   private final val getMethod: Method?
   private final val openMethod: Method?
   private final val warnIfOpenMethod: Method?

   init {
      this.getMethod = var1;
      this.openMethod = var2;
      this.warnIfOpenMethod = var3;
   }

   public fun createAndOpen(closer: String): Any? {
      Intrinsics.checkParameterIsNotNull(var1, "closer");
      val var2: Method = this.getMethod;
      if (this.getMethod != null) {
         var var3: Method;
         try {
            var7 = var2.invoke(null, null);
            var3 = this.openMethod;
         } catch (var6: Exception) {
            return null;
         }

         if (var3 == null) {
            try {
               Intrinsics.throwNpe();
            } catch (var5: Exception) {
               return null;
            }
         }

         try {
            var3.invoke(var7, var1);
            return var7;
         } catch (var4: Exception) {
         }
      }

      return null;
   }

   public fun warnIfOpen(closeGuardInstance: Any?): Boolean {
      if (var1 != null) {
         var var3: Method;
         try {
            var3 = this.warnIfOpenMethod;
         } catch (var6: Exception) {
            return false;
         }

         if (var3 == null) {
            try {
               Intrinsics.throwNpe();
            } catch (var5: Exception) {
               return false;
            }
         }

         try {
            var3.invoke(var1, null);
         } catch (var4: Exception) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public companion object {
      public fun get(): CloseGuard {
         var var4: Method;
         var var8: Method;
         var var9: Method;
         try {
            val var7: Class = Class.forName("dalvik.system.CloseGuard");
            var9 = var7.getMethod("get", null);
            var4 = var7.getMethod("open", java.lang.String.class);
            var8 = var7.getMethod("warnIfOpen", null);
         } catch (var5: Exception) {
            var8 = null as Method;
            return new CloseGuard(null, null, null);
         }

         return new CloseGuard(var9, var4, var8);
      }
   }
}
