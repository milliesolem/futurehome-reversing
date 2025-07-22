package io.sentry.android.replay

import android.view.View
import android.view.Window
import java.lang.reflect.Field

internal object WindowSpy {
   private final val decorViewClass: Class<*>? by LazyKt.lazy(LazyThreadSafetyMode.NONE, <unrepresentable>.INSTANCE)
      private final get() {
         return decorViewClass$delegate.getValue() as Class<?>;
      }


   private final val windowField: Field? by LazyKt.lazy(LazyThreadSafetyMode.NONE, <unrepresentable>.INSTANCE)
      private final get() {
         return windowField$delegate.getValue() as Field;
      }


   public fun pullWindow(maybeDecorView: View): Window? {
      val var4: Class = this.getDecorViewClass();
      var var2: Window = null;
      if (var4 != null) {
         var2 = null;
         if (var4.isInstance(var1)) {
            val var6: Field = INSTANCE.getWindowField();
            var2 = null;
            if (var6 != null) {
               val var5: Any = var6.get(var1);
               var2 = var5 as Window;
            }
         }
      }

      return var2;
   }
}
