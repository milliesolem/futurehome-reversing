package io.sentry.android.replay

import android.util.Log
import android.view.View
import java.lang.reflect.Field
import java.util.ArrayList

internal object WindowManagerSpy {
   private final val mViewsField: Field? by LazyKt.lazy(LazyThreadSafetyMode.NONE, <unrepresentable>.INSTANCE)
      private final get() {
         return mViewsField$delegate.getValue() as Field;
      }


   private final val windowManagerClass: Class<*>? by LazyKt.lazy(LazyThreadSafetyMode.NONE, <unrepresentable>.INSTANCE)
      private final get() {
         return windowManagerClass$delegate.getValue() as Class<?>;
      }


   private final val windowManagerInstance: Any? by LazyKt.lazy(LazyThreadSafetyMode.NONE, <unrepresentable>.INSTANCE)
      private final get() {
         return windowManagerInstance$delegate.getValue();
      }


   public fun swapWindowManagerGlobalMViews(swap: (ArrayList<View>) -> ArrayList<View>) {
      var var4: Any;
      try {
         var4 = this.getWindowManagerInstance();
      } catch (var7: java.lang.Throwable) {
         Log.w("WindowManagerSpy", var7);
         return;
      }

      if (var4 != null) {
         var var2: Field;
         try {
            var2 = INSTANCE.getMViewsField();
         } catch (var6: java.lang.Throwable) {
            Log.w("WindowManagerSpy", var6);
            return;
         }

         if (var2 != null) {
            try {
               val var3: Any = var2.get(var4);
               var2.set(var4, var1.invoke(var3 as ArrayList));
            } catch (var5: java.lang.Throwable) {
               Log.w("WindowManagerSpy", var5);
               return;
            }
         }
      }
   }
}
