package com.polidea.rxandroidble2.internal.util;

import android.bluetooth.BluetoothGattDescriptor;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.UUID;

public class ByteAssociationUtil {
   private ByteAssociationUtil() {
   }

   public static Predicate<? super ByteAssociation<UUID>> characteristicUUIDPredicate(UUID var0) {
      return new Predicate<ByteAssociation<UUID>>(var0) {
         final UUID val$characteristicUUID;

         {
            this.val$characteristicUUID = var1;
         }

         public boolean test(ByteAssociation<UUID> var1) {
            return ((UUID)var1.first).equals(this.val$characteristicUUID);
         }
      };
   }

   public static Predicate<? super ByteAssociation<BluetoothGattDescriptor>> descriptorPredicate(BluetoothGattDescriptor var0) {
      return new Predicate<ByteAssociation<BluetoothGattDescriptor>>(var0) {
         final BluetoothGattDescriptor val$bluetoothGattDescriptor;

         {
            this.val$bluetoothGattDescriptor = var1;
         }

         public boolean test(ByteAssociation<BluetoothGattDescriptor> var1) {
            return var1.first.equals(this.val$bluetoothGattDescriptor);
         }
      };
   }

   public static Function<ByteAssociation<?>, byte[]> getBytesFromAssociation() {
      return new Function<ByteAssociation<?>, byte[]>() {
         public byte[] apply(ByteAssociation<?> var1) {
            return var1.second;
         }
      };
   }
}
