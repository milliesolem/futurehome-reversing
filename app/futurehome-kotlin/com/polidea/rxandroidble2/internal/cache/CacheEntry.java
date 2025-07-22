package com.polidea.rxandroidble2.internal.cache;

import com.polidea.rxandroidble2.internal.DeviceComponent;
import java.util.Map.Entry;

class CacheEntry implements Entry<String, DeviceComponent> {
   private final DeviceComponentWeakReference deviceComponentWeakReference;
   private final String string;

   CacheEntry(String var1, DeviceComponentWeakReference var2) {
      this.string = var1;
      this.deviceComponentWeakReference = var2;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof CacheEntry)) {
         return false;
      } else {
         var1 = var1;
         if (!this.string.equals(var1.getKey()) || !this.deviceComponentWeakReference.equals(var1.deviceComponentWeakReference)) {
            var2 = false;
         }

         return var2;
      }
   }

   public String getKey() {
      return this.string;
   }

   public DeviceComponent getValue() {
      return this.deviceComponentWeakReference.get();
   }

   @Override
   public int hashCode() {
      return this.string.hashCode() * 31 + this.deviceComponentWeakReference.hashCode();
   }

   public DeviceComponent setValue(DeviceComponent var1) {
      throw new UnsupportedOperationException("Not implemented");
   }
}
