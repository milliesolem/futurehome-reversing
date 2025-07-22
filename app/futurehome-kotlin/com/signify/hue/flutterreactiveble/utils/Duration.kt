package com.signify.hue.flutterreactiveble.utils

import java.util.concurrent.TimeUnit

public data class Duration(value: Long, unit: TimeUnit) {
   public final val value: Long
   public final val unit: TimeUnit

   init {
      this.value = var1;
      this.unit = var3;
   }

   public operator fun component1(): Long {
      return this.value;
   }

   public operator fun component2(): TimeUnit {
      return this.unit;
   }

   public fun copy(value: Long = var0.value, unit: TimeUnit = var0.unit): Duration {
      return new Duration(var1, var3);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is Duration) {
         return false;
      } else {
         var1 = var1;
         if (this.value != var1.value) {
            return false;
         } else {
            return this.unit === var1.unit;
         }
      }
   }

   public override fun hashCode(): Int {
      return Duration$$ExternalSyntheticBackport0.m(this.value) * 31 + this.unit.hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("Duration(value=");
      var1.append(this.value);
      var1.append(", unit=");
      var1.append(this.unit);
      var1.append(')');
      return var1.toString();
   }
}
