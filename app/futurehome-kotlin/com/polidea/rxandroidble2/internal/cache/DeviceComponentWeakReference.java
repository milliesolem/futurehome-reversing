package com.polidea.rxandroidble2.internal.cache;

import com.polidea.rxandroidble2.internal.DeviceComponent;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

class DeviceComponentWeakReference extends WeakReference<DeviceComponent> {
   DeviceComponentWeakReference(DeviceComponent var1) {
      super(var1);
   }

   DeviceComponentWeakReference(DeviceComponent var1, ReferenceQueue<? super DeviceComponent> var2) {
      super(var1, var2);
   }

   boolean contains(Object var1) {
      DeviceComponent var3 = this.get();
      boolean var2;
      if (var1 instanceof DeviceComponent && var3 != null && var3.provideDevice() == ((DeviceComponent)var1).provideDevice()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof WeakReference;
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         WeakReference var4 = (WeakReference)var1;
         var1 = this.get();
         Object var7 = var4.get();
         var2 = var3;
         if (var1 != null) {
            var2 = var3;
            if (var7 instanceof DeviceComponent) {
               var2 = var3;
               if (var1.provideDevice().equals(((DeviceComponent)var7).provideDevice())) {
                  var2 = true;
               }
            }
         }

         return var2;
      }
   }

   @Override
   public int hashCode() {
      int var1;
      if (this.get() != null) {
         var1 = this.get().hashCode();
      } else {
         var1 = 0;
      }

      return var1;
   }

   public boolean isEmpty() {
      boolean var1;
      if (this.get() == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public interface Provider {
      DeviceComponentWeakReference provide(DeviceComponent var1);
   }
}
