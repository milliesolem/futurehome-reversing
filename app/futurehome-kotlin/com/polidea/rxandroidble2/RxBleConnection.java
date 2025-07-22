package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.polidea.rxandroidble2.exceptions.BleGattException;
import com.polidea.rxandroidble2.internal.Priority;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public interface RxBleConnection {
   int GATT_MTU_MAXIMUM = 517;
   int GATT_MTU_MINIMUM = 23;
   int GATT_READ_MTU_OVERHEAD = 1;
   int GATT_WRITE_MTU_OVERHEAD = 3;

   RxBleConnection.LongWriteOperationBuilder createNewLongWriteBuilder();

   Single<RxBleDeviceServices> discoverServices();

   Single<RxBleDeviceServices> discoverServices(long var1, TimeUnit var3);

   @Deprecated
   Single<BluetoothGattCharacteristic> getCharacteristic(UUID var1);

   int getMtu();

   Observable<ConnectionParameters> observeConnectionParametersUpdates();

   <T> Observable<T> queue(RxBleCustomOperation<T> var1);

   <T> Observable<T> queue(RxBleCustomOperation<T> var1, Priority var2);

   Single<byte[]> readCharacteristic(BluetoothGattCharacteristic var1);

   Single<byte[]> readCharacteristic(UUID var1);

   Single<byte[]> readDescriptor(BluetoothGattDescriptor var1);

   Single<byte[]> readDescriptor(UUID var1, UUID var2, UUID var3);

   Single<Integer> readRssi();

   Completable requestConnectionPriority(int var1, long var2, TimeUnit var4);

   Single<Integer> requestMtu(int var1);

   Observable<Observable<byte[]>> setupIndication(BluetoothGattCharacteristic var1);

   Observable<Observable<byte[]>> setupIndication(BluetoothGattCharacteristic var1, NotificationSetupMode var2);

   Observable<Observable<byte[]>> setupIndication(UUID var1);

   Observable<Observable<byte[]>> setupIndication(UUID var1, NotificationSetupMode var2);

   Observable<Observable<byte[]>> setupNotification(BluetoothGattCharacteristic var1);

   Observable<Observable<byte[]>> setupNotification(BluetoothGattCharacteristic var1, NotificationSetupMode var2);

   Observable<Observable<byte[]>> setupNotification(UUID var1);

   Observable<Observable<byte[]>> setupNotification(UUID var1, NotificationSetupMode var2);

   Single<byte[]> writeCharacteristic(BluetoothGattCharacteristic var1, byte[] var2);

   Single<byte[]> writeCharacteristic(UUID var1, byte[] var2);

   Completable writeDescriptor(BluetoothGattDescriptor var1, byte[] var2);

   Completable writeDescriptor(UUID var1, UUID var2, UUID var3, byte[] var4);

   @Retention(RetentionPolicy.SOURCE)
   public @interface ConnectionPriority {
   }

   @Deprecated
   public interface Connector {
      Single<RxBleConnection> prepareConnection(boolean var1);
   }

   public interface LongWriteOperationBuilder {
      Observable<byte[]> build();

      RxBleConnection.LongWriteOperationBuilder setBytes(byte[] var1);

      RxBleConnection.LongWriteOperationBuilder setCharacteristic(BluetoothGattCharacteristic var1);

      RxBleConnection.LongWriteOperationBuilder setCharacteristicUuid(UUID var1);

      RxBleConnection.LongWriteOperationBuilder setMaxBatchSize(int var1);

      RxBleConnection.LongWriteOperationBuilder setWriteOperationAckStrategy(RxBleConnection.WriteOperationAckStrategy var1);

      RxBleConnection.LongWriteOperationBuilder setWriteOperationRetryStrategy(RxBleConnection.WriteOperationRetryStrategy var1);
   }

   public static enum RxBleConnectionState {
      CONNECTED,
      CONNECTING,
      DISCONNECTED,
      DISCONNECTING;

      private static final RxBleConnection.RxBleConnectionState[] $VALUES;
      private final String description;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         RxBleConnection.RxBleConnectionState var0 = new RxBleConnection.RxBleConnectionState("CONNECTING");
         CONNECTING = var0;
         RxBleConnection.RxBleConnectionState var3 = new RxBleConnection.RxBleConnectionState("CONNECTED");
         CONNECTED = var3;
         RxBleConnection.RxBleConnectionState var1 = new RxBleConnection.RxBleConnectionState("DISCONNECTED");
         DISCONNECTED = var1;
         RxBleConnection.RxBleConnectionState var2 = new RxBleConnection.RxBleConnectionState("DISCONNECTING");
         DISCONNECTING = var2;
         $VALUES = new RxBleConnection.RxBleConnectionState[]{var0, var3, var1, var2};
      }

      private RxBleConnectionState(String var3) {
         this.description = var3;
      }

      @Override
      public String toString() {
         StringBuilder var1 = new StringBuilder("RxBleConnectionState{");
         var1.append(this.description);
         var1.append('}');
         return var1.toString();
      }
   }

   public interface WriteOperationAckStrategy extends ObservableTransformer<Boolean, Boolean> {
   }

   public interface WriteOperationRetryStrategy
      extends ObservableTransformer<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure, RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure> {
      public static class LongWriteFailure {
         final int batchIndex;
         final BleGattException cause;

         public LongWriteFailure(int var1, BleGattException var2) {
            this.batchIndex = var1;
            this.cause = var2;
         }

         public int getBatchIndex() {
            return this.batchIndex;
         }

         public BleGattException getCause() {
            return this.cause;
         }
      }
   }
}
