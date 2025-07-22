package io.reactivex.internal.util;

import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;

public final class BackpressureHelper {
   private BackpressureHelper() {
      throw new IllegalStateException("No instances!");
   }

   public static long add(AtomicLong var0, long var1) {
      long var3;
      do {
         var3 = var0.get();
         if (var3 == Long.MAX_VALUE) {
            return Long.MAX_VALUE;
         }
      } while (!var0.compareAndSet(var3, addCap(var3, var1)));

      return var3;
   }

   public static long addCancel(AtomicLong var0, long var1) {
      long var3;
      do {
         var3 = var0.get();
         if (var3 == Long.MIN_VALUE) {
            return Long.MIN_VALUE;
         }

         if (var3 == Long.MAX_VALUE) {
            return Long.MAX_VALUE;
         }
      } while (!var0.compareAndSet(var3, addCap(var3, var1)));

      return var3;
   }

   public static long addCap(long var0, long var2) {
      var2 = var0 + var2;
      var0 = var2;
      if (var2 < 0L) {
         var0 = Long.MAX_VALUE;
      }

      return var0;
   }

   public static long multiplyCap(long var0, long var2) {
      long var4 = var0 * var2;
      return (var0 | var2) >>> 31 != 0L && var4 / var0 != var2 ? Long.MAX_VALUE : var4;
   }

   public static long produced(AtomicLong var0, long var1) {
      long var3;
      long var7;
      do {
         var7 = var0.get();
         if (var7 == Long.MAX_VALUE) {
            return Long.MAX_VALUE;
         }

         long var5 = var7 - var1;
         var3 = var5;
         if (var5 < 0L) {
            StringBuilder var9 = new StringBuilder("More produced than requested: ");
            var9.append(var5);
            RxJavaPlugins.onError(new IllegalStateException(var9.toString()));
            var3 = 0L;
         }
      } while (!var0.compareAndSet(var7, var3));

      return var3;
   }

   public static long producedCancel(AtomicLong var0, long var1) {
      long var3;
      long var7;
      do {
         var7 = var0.get();
         if (var7 == Long.MIN_VALUE) {
            return Long.MIN_VALUE;
         }

         if (var7 == Long.MAX_VALUE) {
            return Long.MAX_VALUE;
         }

         long var5 = var7 - var1;
         var3 = var5;
         if (var5 < 0L) {
            StringBuilder var9 = new StringBuilder("More produced than requested: ");
            var9.append(var5);
            RxJavaPlugins.onError(new IllegalStateException(var9.toString()));
            var3 = 0L;
         }
      } while (!var0.compareAndSet(var7, var3));

      return var3;
   }
}
