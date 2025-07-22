package com.polidea.rxandroidble2.internal.util;

import java.util.Arrays;
import java.util.UUID;

public class CharacteristicChangedEvent extends CharacteristicNotificationId {
   public final byte[] data;

   public CharacteristicChangedEvent(UUID var1, Integer var2, byte[] var3) {
      super(var1, var2);
      this.data = var3;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 instanceof CharacteristicChangedEvent) {
         if (!super.equals(var1)) {
            return false;
         } else {
            var1 = var1;
            return Arrays.equals(this.data, var1.data);
         }
      } else {
         if (!(var1 instanceof CharacteristicNotificationId) || !super.equals(var1)) {
            var2 = false;
         }

         return var2;
      }
   }

   public int hashCode() {
      return super.hashCode() * 31 + Arrays.hashCode(this.data);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CharacteristicChangedEvent{UUID=");
      var1.append(((UUID)this.first).toString());
      var1.append(", instanceId=");
      var1.append(((Integer)this.second).toString());
      var1.append(", data=");
      var1.append(Arrays.toString(this.data));
      var1.append('}');
      return var1.toString();
   }
}
