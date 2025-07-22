package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import com.polidea.rxandroidble2.internal.operations.OperationsProvider;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueue;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.UUID;

public final class LongWriteOperationBuilderImpl implements RxBleConnection.LongWriteOperationBuilder {
   byte[] bytes;
   PayloadSizeLimitProvider maxBatchSizeProvider;
   final ConnectionOperationQueue operationQueue;
   final OperationsProvider operationsProvider;
   private final RxBleConnection rxBleConnection;
   RxBleConnection.WriteOperationAckStrategy writeOperationAckStrategy = new ImmediateSerializedBatchAckStrategy();
   RxBleConnection.WriteOperationRetryStrategy writeOperationRetryStrategy = new NoRetryStrategy();
   private Single<BluetoothGattCharacteristic> writtenCharacteristicObservable;

   @Inject
   LongWriteOperationBuilderImpl(ConnectionOperationQueue var1, MtuBasedPayloadSizeLimit var2, RxBleConnection var3, OperationsProvider var4) {
      this.operationQueue = var1;
      this.maxBatchSizeProvider = var2;
      this.rxBleConnection = var3;
      this.operationsProvider = var4;
   }

   @Override
   public Observable<byte[]> build() {
      Single var1 = this.writtenCharacteristicObservable;
      if (var1 != null) {
         if (this.bytes != null) {
            return var1.flatMapObservable(
               new Function<BluetoothGattCharacteristic, Observable<byte[]>>(this) {
                  final LongWriteOperationBuilderImpl this$0;

                  {
                     this.this$0 = var1;
                  }

                  public Observable<byte[]> apply(BluetoothGattCharacteristic var1) {
                     return this.this$0
                        .operationQueue
                        .queue(
                           this.this$0
                              .operationsProvider
                              .provideLongWriteOperation(
                                 var1,
                                 this.this$0.writeOperationAckStrategy,
                                 this.this$0.writeOperationRetryStrategy,
                                 this.this$0.maxBatchSizeProvider,
                                 this.this$0.bytes
                              )
                        );
                  }
               }
            );
         } else {
            throw new IllegalArgumentException("setBytes() needs to be called before build()");
         }
      } else {
         throw new IllegalArgumentException("setCharacteristicUuid() or setCharacteristic() needs to be called before build()");
      }
   }

   @Override
   public RxBleConnection.LongWriteOperationBuilder setBytes(byte[] var1) {
      this.bytes = var1;
      return this;
   }

   @Override
   public RxBleConnection.LongWriteOperationBuilder setCharacteristic(BluetoothGattCharacteristic var1) {
      this.writtenCharacteristicObservable = Single.just(var1);
      return this;
   }

   @Override
   public RxBleConnection.LongWriteOperationBuilder setCharacteristicUuid(UUID var1) {
      this.writtenCharacteristicObservable = this.rxBleConnection
         .discoverServices()
         .flatMap(new Function<RxBleDeviceServices, SingleSource<? extends BluetoothGattCharacteristic>>(this, var1) {
            final LongWriteOperationBuilderImpl this$0;
            final UUID val$uuid;

            {
               this.this$0 = var1;
               this.val$uuid = var2;
            }

            public SingleSource<? extends BluetoothGattCharacteristic> apply(RxBleDeviceServices var1) throws Exception {
               return var1.getCharacteristic(this.val$uuid);
            }
         });
      return this;
   }

   @Override
   public RxBleConnection.LongWriteOperationBuilder setMaxBatchSize(int var1) {
      this.maxBatchSizeProvider = new ConstantPayloadSizeLimit(var1);
      return this;
   }

   @Override
   public RxBleConnection.LongWriteOperationBuilder setWriteOperationAckStrategy(RxBleConnection.WriteOperationAckStrategy var1) {
      this.writeOperationAckStrategy = var1;
      return this;
   }

   @Override
   public RxBleConnection.LongWriteOperationBuilder setWriteOperationRetryStrategy(RxBleConnection.WriteOperationRetryStrategy var1) {
      this.writeOperationRetryStrategy = var1;
      return this;
   }
}
