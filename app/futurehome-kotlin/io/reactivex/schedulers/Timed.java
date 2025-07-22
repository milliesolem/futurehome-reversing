package io.reactivex.schedulers;

import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.TimeUnit;

public final class Timed<T> {
   final long time;
   final TimeUnit unit;
   final T value;

   public Timed(T var1, long var2, TimeUnit var4) {
      this.value = (T)var1;
      this.time = var2;
      this.unit = ObjectHelper.requireNonNull(var4, "unit is null");
   }

   @Override
   public boolean equals(Object var1) {
      boolean var4 = var1 instanceof Timed;
      boolean var3 = false;
      boolean var2 = var3;
      if (var4) {
         var1 = var1;
         var2 = var3;
         if (ObjectHelper.equals(this.value, var1.value)) {
            var2 = var3;
            if (this.time == var1.time) {
               var2 = var3;
               if (ObjectHelper.equals(this.unit, var1.unit)) {
                  var2 = true;
               }
            }
         }
      }

      return var2;
   }

   @Override
   public int hashCode() {
      Object var4 = this.value;
      int var1;
      if (var4 != null) {
         var1 = var4.hashCode();
      } else {
         var1 = 0;
      }

      long var2 = this.time;
      return (var1 * 31 + (int)(var2 ^ var2 >>> 31)) * 31 + this.unit.hashCode();
   }

   public long time() {
      return this.time;
   }

   public long time(TimeUnit var1) {
      return var1.convert(this.time, this.unit);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("Timed[time=");
      var1.append(this.time);
      var1.append(", unit=");
      var1.append(this.unit);
      var1.append(", value=");
      var1.append(this.value);
      var1.append("]");
      return var1.toString();
   }

   public TimeUnit unit() {
      return this.unit;
   }

   public T value() {
      return this.value;
   }
}
