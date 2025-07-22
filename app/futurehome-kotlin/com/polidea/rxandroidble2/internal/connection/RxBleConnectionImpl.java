package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.DeadObjectException;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.ConnectionParameters;
import com.polidea.rxandroidble2.NotificationSetupMode;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleCustomOperation;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import com.polidea.rxandroidble2.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.polidea.rxandroidble2.internal.Priority;
import com.polidea.rxandroidble2.internal.QueueOperation;
import com.polidea.rxandroidble2.internal.operations.OperationsProvider;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueue;
import com.polidea.rxandroidble2.internal.serialization.QueueReleaseInterface;
import com.polidea.rxandroidble2.internal.util.ByteAssociation;
import com.polidea.rxandroidble2.internal.util.QueueReleasingEmitterWrapper;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ConnectionScope
public class RxBleConnectionImpl implements RxBleConnection {
   final BluetoothGatt bluetoothGatt;
   final Scheduler callbackScheduler;
   private final DescriptorWriter descriptorWriter;
   final RxBleGattCallback gattCallback;
   private final IllegalOperationChecker illegalOperationChecker;
   private final Provider<RxBleConnection.LongWriteOperationBuilder> longWriteOperationBuilderProvider;
   private final MtuProvider mtuProvider;
   private final NotificationAndIndicationManager notificationIndicationManager;
   private final ConnectionOperationQueue operationQueue;
   private final OperationsProvider operationsProvider;
   private final ServiceDiscoveryManager serviceDiscoveryManager;

   @Inject
   public RxBleConnectionImpl(
      ConnectionOperationQueue var1,
      RxBleGattCallback var2,
      BluetoothGatt var3,
      ServiceDiscoveryManager var4,
      NotificationAndIndicationManager var5,
      MtuProvider var6,
      DescriptorWriter var7,
      OperationsProvider var8,
      Provider<RxBleConnection.LongWriteOperationBuilder> var9,
      @Named("bluetooth_interaction") Scheduler var10,
      IllegalOperationChecker var11
   ) {
      this.operationQueue = var1;
      this.gattCallback = var2;
      this.bluetoothGatt = var3;
      this.serviceDiscoveryManager = var4;
      this.notificationIndicationManager = var5;
      this.mtuProvider = var6;
      this.descriptorWriter = var7;
      this.operationsProvider = var8;
      this.longWriteOperationBuilderProvider = var9;
      this.callbackScheduler = var10;
      this.illegalOperationChecker = var11;
   }

   @Override
   public RxBleConnection.LongWriteOperationBuilder createNewLongWriteBuilder() {
      return (RxBleConnection.LongWriteOperationBuilder)this.longWriteOperationBuilderProvider.get();
   }

   @Override
   public Single<RxBleDeviceServices> discoverServices() {
      return this.serviceDiscoveryManager.getDiscoverServicesSingle(20L, TimeUnit.SECONDS);
   }

   @Override
   public Single<RxBleDeviceServices> discoverServices(long var1, TimeUnit var3) {
      return this.serviceDiscoveryManager.getDiscoverServicesSingle(var1, var3);
   }

   @Deprecated
   @Override
   public Single<BluetoothGattCharacteristic> getCharacteristic(UUID var1) {
      return this.discoverServices().flatMap(new Function<RxBleDeviceServices, Single<? extends BluetoothGattCharacteristic>>(this, var1) {
         final RxBleConnectionImpl this$0;
         final UUID val$characteristicUuid;

         {
            this.this$0 = var1;
            this.val$characteristicUuid = var2;
         }

         public Single<? extends BluetoothGattCharacteristic> apply(RxBleDeviceServices var1) {
            return var1.getCharacteristic(this.val$characteristicUuid);
         }
      });
   }

   @Override
   public int getMtu() {
      return this.mtuProvider.getMtu();
   }

   @Override
   public Observable<ConnectionParameters> observeConnectionParametersUpdates() {
      return this.gattCallback.getConnectionParametersUpdates();
   }

   @Override
   public <T> Observable<T> queue(RxBleCustomOperation<T> var1) {
      return this.queue(var1, Priority.NORMAL);
   }

   @Override
   public <T> Observable<T> queue(RxBleCustomOperation<T> var1, Priority var2) {
      return this.operationQueue
         .queue(
            new QueueOperation<T>(this, var1, var2) {
               final RxBleConnectionImpl this$0;
               final RxBleCustomOperation val$operation;
               final Priority val$priority;

               {
                  this.this$0 = var1;
                  this.val$operation = var2x;
                  this.val$priority = var3;
               }

               private Action clearNativeCallbackReferenceAction() {
                  return new Action(this) {
                     final <unrepresentable> this$1;

                     {
                        this.this$1 = var1;
                     }

                     @Override
                     public void run() {
                        this.this$1.this$0.gattCallback.setNativeCallback(null);
                        this.this$1.this$0.gattCallback.setHiddenNativeCallback(null);
                     }
                  };
               }

               @Override
               public Priority definedPriority() {
                  return this.val$priority;
               }

               // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
               // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
               @Override
               protected void protectedRun(ObservableEmitter<T> var1, QueueReleaseInterface var2x) throws Throwable {
                  boolean var5 = false /* VF: Semaphore variable */;

                  Observable var3;
                  try {
                     var5 = true;
                     var3 = this.val$operation.asObservable(this.this$0.bluetoothGatt, this.this$0.gattCallback, this.this$0.callbackScheduler);
                     var5 = false;
                  } finally {
                     if (var5) {
                        var2x.release();
                     }
                  }

                  if (var3 != null) {
                     QueueReleasingEmitterWrapper var7 = new QueueReleasingEmitterWrapper(var1, var2x);
                     var3.doOnTerminate(this.clearNativeCallbackReferenceAction()).subscribe(var7);
                  } else {
                     var2x.release();
                     throw new IllegalArgumentException("The custom operation asObservable method must return a non-null observable");
                  }
               }

               @Override
               protected BleException provideException(DeadObjectException var1) {
                  return new BleDisconnectedException(var1, this.this$0.bluetoothGatt.getDevice().getAddress(), -1);
               }
            }
         );
   }

   @Override
   public Single<byte[]> readCharacteristic(BluetoothGattCharacteristic var1) {
      return this.illegalOperationChecker
         .checkAnyPropertyMatches(var1, 2)
         .andThen(this.operationQueue.queue(this.operationsProvider.provideReadCharacteristic(var1)))
         .firstOrError();
   }

   @Override
   public Single<byte[]> readCharacteristic(UUID var1) {
      return this.getCharacteristic(var1).flatMap(new Function<BluetoothGattCharacteristic, SingleSource<byte[]>>(this) {
         final RxBleConnectionImpl this$0;

         {
            this.this$0 = var1;
         }

         public SingleSource<byte[]> apply(BluetoothGattCharacteristic var1) {
            return this.this$0.readCharacteristic(var1);
         }
      });
   }

   @Override
   public Single<byte[]> readDescriptor(BluetoothGattDescriptor var1) {
      return this.operationQueue
         .queue(this.operationsProvider.provideReadDescriptor(var1))
         .firstOrError()
         .map(new Function<ByteAssociation<BluetoothGattDescriptor>, byte[]>(this) {
            final RxBleConnectionImpl this$0;

            {
               this.this$0 = var1;
            }

            public byte[] apply(ByteAssociation<BluetoothGattDescriptor> var1) {
               return var1.second;
            }
         });
   }

   @Override
   public Single<byte[]> readDescriptor(UUID var1, UUID var2, UUID var3) {
      return this.discoverServices().flatMap(new Function<RxBleDeviceServices, SingleSource<BluetoothGattDescriptor>>(this, var1, var2, var3) {
         final RxBleConnectionImpl this$0;
         final UUID val$characteristicUuid;
         final UUID val$descriptorUuid;
         final UUID val$serviceUuid;

         {
            this.this$0 = var1;
            this.val$serviceUuid = var2x;
            this.val$characteristicUuid = var3x;
            this.val$descriptorUuid = var4;
         }

         public SingleSource<BluetoothGattDescriptor> apply(RxBleDeviceServices var1) {
            return var1.getDescriptor(this.val$serviceUuid, this.val$characteristicUuid, this.val$descriptorUuid);
         }
      }).flatMap(new Function<BluetoothGattDescriptor, SingleSource<byte[]>>(this) {
         final RxBleConnectionImpl this$0;

         {
            this.this$0 = var1;
         }

         public SingleSource<byte[]> apply(BluetoothGattDescriptor var1) {
            return this.this$0.readDescriptor(var1);
         }
      });
   }

   @Override
   public Single<Integer> readRssi() {
      return this.operationQueue.queue(this.operationsProvider.provideRssiReadOperation()).firstOrError();
   }

   @Override
   public Completable requestConnectionPriority(int var1, long var2, TimeUnit var4) {
      if (var1 != 2 && var1 != 0 && var1 != 1) {
         StringBuilder var5 = new StringBuilder("Connection priority must have valid value from BluetoothGatt (received ");
         var5.append(var1);
         var5.append(")");
         return Completable.error(new IllegalArgumentException(var5.toString()));
      } else {
         return var2 <= 0L
            ? Completable.error(new IllegalArgumentException("Delay must be bigger than 0"))
            : this.operationQueue.queue(this.operationsProvider.provideConnectionPriorityChangeOperation(var1, var2, var4)).ignoreElements();
      }
   }

   @Override
   public Single<Integer> requestMtu(int var1) {
      return this.operationQueue.queue(this.operationsProvider.provideMtuChangeOperation(var1)).firstOrError();
   }

   @Override
   public Observable<Observable<byte[]>> setupIndication(BluetoothGattCharacteristic var1) {
      return this.setupIndication(var1, NotificationSetupMode.DEFAULT);
   }

   @Override
   public Observable<Observable<byte[]>> setupIndication(BluetoothGattCharacteristic var1, NotificationSetupMode var2) {
      return this.illegalOperationChecker
         .checkAnyPropertyMatches(var1, 32)
         .andThen(this.notificationIndicationManager.setupServerInitiatedCharacteristicRead(var1, var2, true));
   }

   @Override
   public Observable<Observable<byte[]>> setupIndication(UUID var1) {
      return this.setupIndication(var1, NotificationSetupMode.DEFAULT);
   }

   @Override
   public Observable<Observable<byte[]>> setupIndication(UUID var1, NotificationSetupMode var2) {
      return this.getCharacteristic(var1)
         .flatMapObservable(new Function<BluetoothGattCharacteristic, ObservableSource<? extends Observable<byte[]>>>(this, var2) {
            final RxBleConnectionImpl this$0;
            final NotificationSetupMode val$setupMode;

            {
               this.this$0 = var1;
               this.val$setupMode = var2x;
            }

            public Observable<? extends Observable<byte[]>> apply(BluetoothGattCharacteristic var1) {
               return this.this$0.setupIndication(var1, this.val$setupMode);
            }
         });
   }

   @Override
   public Observable<Observable<byte[]>> setupNotification(BluetoothGattCharacteristic var1) {
      return this.setupNotification(var1, NotificationSetupMode.DEFAULT);
   }

   @Override
   public Observable<Observable<byte[]>> setupNotification(BluetoothGattCharacteristic var1, NotificationSetupMode var2) {
      return this.illegalOperationChecker
         .checkAnyPropertyMatches(var1, 16)
         .andThen(this.notificationIndicationManager.setupServerInitiatedCharacteristicRead(var1, var2, false));
   }

   @Override
   public Observable<Observable<byte[]>> setupNotification(UUID var1) {
      return this.setupNotification(var1, NotificationSetupMode.DEFAULT);
   }

   @Override
   public Observable<Observable<byte[]>> setupNotification(UUID var1, NotificationSetupMode var2) {
      return this.getCharacteristic(var1)
         .flatMapObservable(new Function<BluetoothGattCharacteristic, ObservableSource<? extends Observable<byte[]>>>(this, var2) {
            final RxBleConnectionImpl this$0;
            final NotificationSetupMode val$setupMode;

            {
               this.this$0 = var1;
               this.val$setupMode = var2x;
            }

            public Observable<? extends Observable<byte[]>> apply(BluetoothGattCharacteristic var1) {
               return this.this$0.setupNotification(var1, this.val$setupMode);
            }
         });
   }

   @Override
   public Single<byte[]> writeCharacteristic(BluetoothGattCharacteristic var1, byte[] var2) {
      return this.illegalOperationChecker
         .checkAnyPropertyMatches(var1, 76)
         .andThen(this.operationQueue.queue(this.operationsProvider.provideWriteCharacteristic(var1, var2)))
         .firstOrError();
   }

   @Override
   public Single<byte[]> writeCharacteristic(UUID var1, byte[] var2) {
      return this.getCharacteristic(var1).flatMap(new Function<BluetoothGattCharacteristic, SingleSource<byte[]>>(this, var2) {
         final RxBleConnectionImpl this$0;
         final byte[] val$data;

         {
            this.this$0 = var1;
            this.val$data = var2x;
         }

         public SingleSource<byte[]> apply(BluetoothGattCharacteristic var1) {
            return this.this$0.writeCharacteristic(var1, this.val$data);
         }
      });
   }

   @Override
   public Completable writeDescriptor(BluetoothGattDescriptor var1, byte[] var2) {
      return this.descriptorWriter.writeDescriptor(var1, var2);
   }

   @Override
   public Completable writeDescriptor(UUID var1, UUID var2, UUID var3, byte[] var4) {
      return this.discoverServices().flatMap(new Function<RxBleDeviceServices, SingleSource<BluetoothGattDescriptor>>(this, var1, var2, var3) {
         final RxBleConnectionImpl this$0;
         final UUID val$characteristicUuid;
         final UUID val$descriptorUuid;
         final UUID val$serviceUuid;

         {
            this.this$0 = var1;
            this.val$serviceUuid = var2x;
            this.val$characteristicUuid = var3x;
            this.val$descriptorUuid = var4x;
         }

         public SingleSource<BluetoothGattDescriptor> apply(RxBleDeviceServices var1) {
            return var1.getDescriptor(this.val$serviceUuid, this.val$characteristicUuid, this.val$descriptorUuid);
         }
      }).flatMapCompletable(new Function<BluetoothGattDescriptor, CompletableSource>(this, var4) {
         final RxBleConnectionImpl this$0;
         final byte[] val$data;

         {
            this.this$0 = var1;
            this.val$data = var2x;
         }

         public CompletableSource apply(BluetoothGattDescriptor var1) {
            return this.this$0.writeDescriptor(var1, this.val$data);
         }
      });
   }
}
