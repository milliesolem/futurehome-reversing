package kotlin

internal class LazyKt__LazyJVMKt {
   open fun LazyKt__LazyJVMKt() {
   }

   @JvmStatic
   public fun <T> lazy(lock: Any?, initializer: () -> T): Lazy<T> {
      return new SynchronizedLazyImpl(var1, var0);
   }

   @JvmStatic
   public fun <T> lazy(mode: LazyThreadSafetyMode, initializer: () -> T): Lazy<T> {
      val var2: Int = LazyKt__LazyJVMKt.WhenMappings.$EnumSwitchMapping$0[var0.ordinal()];
      val var3: Lazy;
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               throw new NoWhenBranchMatchedException();
            }

            var3 = new UnsafeLazyImpl(var1);
         } else {
            var3 = new SafePublicationLazyImpl(var1);
         }
      } else {
         var3 = new SynchronizedLazyImpl(var1, null, 2, null);
      }

      return var3;
   }

   @JvmStatic
   public fun <T> lazy(initializer: () -> T): Lazy<T> {
      return new SynchronizedLazyImpl(var0, null, 2, null);
   }
}
