package com.polidea.rxandroidble2.internal.util;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import java.util.Arrays;
import java.util.UUID;

public class ByteAssociation<T> {
   public final T first;
   public final byte[] second;

   public ByteAssociation(T var1, byte[] var2) {
      this.first = (T)var1;
      this.second = var2;
   }

   public static <T> ByteAssociation<T> create(T var0, byte[] var1) {
      return new ByteAssociation<>((T)var0, var1);
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof ByteAssociation;
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         var1 = var1;
         var2 = var3;
         if (Arrays.equals(var1.second, this.second)) {
            var2 = var3;
            if (var1.first.equals(this.first)) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   @Override
   public int hashCode() {
      return this.first.hashCode() ^ Arrays.hashCode(this.second);
   }

   @Override
   public String toString() {
      StringBuilder var1 = this.first;
      if (var1 instanceof BluetoothGattCharacteristic) {
         var1 = new StringBuilder("BluetoothGattCharacteristic(");
         var1.append(((BluetoothGattCharacteristic)this.first).getUuid().toString());
         var1.append(")");
         var1 = var1.toString();
      } else if (var1 instanceof BluetoothGattDescriptor) {
         var1 = new StringBuilder("BluetoothGattDescriptor(");
         var1.append(((BluetoothGattDescriptor)this.first).getUuid().toString());
         var1.append(")");
         var1 = var1.toString();
      } else if (var1 instanceof UUID) {
         var1 = new StringBuilder("UUID(");
         var1.append(this.first.toString());
         var1.append(")");
         var1 = var1.toString();
      } else {
         var1 = var1.getClass().getSimpleName();
      }

      StringBuilder var2 = new StringBuilder();
      var2.append(this.getClass().getSimpleName());
      var2.append("[first=");
      var2.append((String)var1);
      var2.append(", second=");
      var2.append(Arrays.toString(this.second));
      var2.append("]");
      return var2.toString();
   }
}
