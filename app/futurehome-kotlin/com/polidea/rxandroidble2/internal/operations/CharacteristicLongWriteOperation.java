package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.DeadObjectException;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.polidea.rxandroidble2.exceptions.BleGattCallbackTimeoutException;
import com.polidea.rxandroidble2.exceptions.BleGattCannotStartException;
import com.polidea.rxandroidble2.exceptions.BleGattCharacteristicException;
import com.polidea.rxandroidble2.exceptions.BleGattException;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.QueueOperation;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.connection.PayloadSizeLimitProvider;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.serialization.QueueReleaseInterface;
import com.polidea.rxandroidble2.internal.util.ByteAssociation;
import com.polidea.rxandroidble2.internal.util.DisposableUtil;
import com.polidea.rxandroidble2.internal.util.QueueReleasingEmitterWrapper;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.UUID;

public class CharacteristicLongWriteOperation extends QueueOperation<byte[]> {
   private final PayloadSizeLimitProvider batchSizeProvider;
   private final BluetoothGatt bluetoothGatt;
   private final BluetoothGattCharacteristic bluetoothGattCharacteristic;
   private final Scheduler bluetoothInteractionScheduler;
   final byte[] bytesToWrite;
   private final RxBleGattCallback rxBleGattCallback;
   private byte[] tempBatchArray;
   private final TimeoutConfiguration timeoutConfiguration;
   private final RxBleConnection.WriteOperationAckStrategy writeOperationAckStrategy;
   private final RxBleConnection.WriteOperationRetryStrategy writeOperationRetryStrategy;

   CharacteristicLongWriteOperation(
      BluetoothGatt var1,
      RxBleGattCallback var2,
      @Named("bluetooth_interaction") Scheduler var3,
      @Named("operation-timeout") TimeoutConfiguration var4,
      BluetoothGattCharacteristic var5,
      PayloadSizeLimitProvider var6,
      RxBleConnection.WriteOperationAckStrategy var7,
      RxBleConnection.WriteOperationRetryStrategy var8,
      byte[] var9
   ) {
      this.bluetoothGatt = var1;
      this.rxBleGattCallback = var2;
      this.bluetoothInteractionScheduler = var3;
      this.timeoutConfiguration = var4;
      this.bluetoothGattCharacteristic = var5;
      this.batchSizeProvider = var6;
      this.writeOperationAckStrategy = var7;
      this.writeOperationRetryStrategy = var8;
      this.bytesToWrite = var9;
   }

   static Function<Observable<?>, ObservableSource<?>> bufferIsNotEmptyAndOperationHasBeenAcknowledgedAndNotUnsubscribed(
      RxBleConnection.WriteOperationAckStrategy var0, ByteBuffer var1, QueueReleasingEmitterWrapper<byte[]> var2
   ) {
      return new Function<Observable<?>, ObservableSource<?>>(var2, var1, var0) {
         final ByteBuffer val$byteBuffer;
         final QueueReleasingEmitterWrapper val$emitterWrapper;
         final RxBleConnection.WriteOperationAckStrategy val$writeOperationAckStrategy;

         {
            this.val$emitterWrapper = var1;
            this.val$byteBuffer = var2x;
            this.val$writeOperationAckStrategy = var3;
         }

         private Function<Object, Boolean> bufferIsNotEmpty(ByteBuffer var1) {
            return new Function<Object, Boolean>(this, var1) {
               final <unrepresentable> this$0;
               final ByteBuffer val$byteBuffer;

               {
                  this.this$0 = var1;
                  this.val$byteBuffer = var2x;
               }

               public Boolean apply(Object var1) {
                  return this.val$byteBuffer.hasRemaining();
               }
            };
         }

         private Predicate<Object> notUnsubscribed(QueueReleasingEmitterWrapper<byte[]> var1) {
            return new Predicate<Object>(this, var1) {
               final <unrepresentable> this$0;
               final QueueReleasingEmitterWrapper val$emitterWrapper;

               {
                  this.this$0 = var1;
                  this.val$emitterWrapper = var2x;
               }

               @Override
               public boolean test(Object var1) {
                  return this.val$emitterWrapper.isWrappedEmitterUnsubscribed() ^ true;
               }
            };
         }

         public ObservableSource<?> apply(Observable<?> var1) {
            return var1.takeWhile(this.notUnsubscribed(this.val$emitterWrapper))
               .map(this.bufferIsNotEmpty(this.val$byteBuffer))
               .compose(this.val$writeOperationAckStrategy)
               .takeWhile(new Predicate<Boolean>(this) {
                  final <unrepresentable> this$0;

                  {
                     this.this$0 = var1;
                  }

                  public boolean test(Boolean var1) {
                     return var1;
                  }
               });
         }
      };
   }

   private static Function<Observable<Throwable>, ObservableSource<?>> errorIsRetryableAndAccordingTo(
      RxBleConnection.WriteOperationRetryStrategy var0, ByteBuffer var1, int var2, CharacteristicLongWriteOperation.IntSupplier var3
   ) {
      return new Function<Observable<Throwable>, ObservableSource<?>>(var0, var3, var2, var1) {
         final int val$batchSize;
         final ByteBuffer val$byteBuffer;
         final CharacteristicLongWriteOperation.IntSupplier val$previousBatchIndexSupplier;
         final RxBleConnection.WriteOperationRetryStrategy val$writeOperationRetryStrategy;

         {
            this.val$writeOperationRetryStrategy = var1;
            this.val$previousBatchIndexSupplier = var2x;
            this.val$batchSize = var3x;
            this.val$byteBuffer = var4;
         }

         private Consumer<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure> repositionByteBufferForRetry() {
            return new Consumer<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure>(this) {
               final <unrepresentable> this$0;

               {
                  this.this$0 = var1;
               }

               public void accept(RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure var1) {
                  int var3x = var1.getBatchIndex();
                  int var2x = this.this$0.val$batchSize;
                  ((Buffer)this.this$0.val$byteBuffer).position(var3x * var2x);
               }
            };
         }

         private Function<Throwable, Observable<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure>> toLongWriteFailureOrError() {
            return new Function<Throwable, Observable<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure>>(this) {
               final <unrepresentable> this$0;

               {
                  this.this$0 = var1;
               }

               public Observable<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure> apply(Throwable var1) {
                  return !(var1 instanceof BleGattCharacteristicException) && !(var1 instanceof BleGattCannotStartException)
                     ? Observable.error(var1)
                     : Observable.just(
                        new RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure(
                           this.this$0.val$previousBatchIndexSupplier.get(), (BleGattException)var1
                        )
                     );
               }
            };
         }

         public ObservableSource<?> apply(Observable<Throwable> var1) {
            return var1.flatMap(this.toLongWriteFailureOrError()).doOnNext(this.repositionByteBufferForRetry()).compose(this.val$writeOperationRetryStrategy);
         }
      };
   }

   private Observable<ByteAssociation<UUID>> writeBatchAndObserve(int var1, ByteBuffer var2, CharacteristicLongWriteOperation.IntSupplier var3) {
      return Observable.create(
         new ObservableOnSubscribe<ByteAssociation<UUID>>(this, this.rxBleGattCallback.getOnCharacteristicWrite(), var2, var1, var3) {
            final CharacteristicLongWriteOperation this$0;
            final int val$batchSize;
            final ByteBuffer val$byteBuffer;
            final Observable val$onCharacteristicWrite;
            final CharacteristicLongWriteOperation.IntSupplier val$previousBatchIndexSupplier;

            {
               this.this$0 = var1;
               this.val$onCharacteristicWrite = var2x;
               this.val$byteBuffer = var3x;
               this.val$batchSize = var4;
               this.val$previousBatchIndexSupplier = var5;
            }

            // $VF: Could not inline inconsistent finally blocks
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            @Override
            public void subscribe(ObservableEmitter<ByteAssociation<UUID>> var1) {
               var1.setDisposable((DisposableObserver)this.val$onCharacteristicWrite.subscribeWith(DisposableUtil.disposableObserverFromEmitter(var1)));

               try {
                  byte[] var2x = this.this$0.getNextBatch(this.val$byteBuffer, this.val$batchSize);
                  this.this$0.writeData(var2x, this.val$previousBatchIndexSupplier);
               } catch (Throwable var4) {
                  var1.onError(var4);
                  return;
               }
            }
         }
      );
   }

   private static Predicate<ByteAssociation<UUID>> writeResponseForMatchingCharacteristic(BluetoothGattCharacteristic var0) {
      return new Predicate<ByteAssociation<UUID>>(var0) {
         final BluetoothGattCharacteristic val$bluetoothGattCharacteristic;

         {
            this.val$bluetoothGattCharacteristic = var1;
         }

         public boolean test(ByteAssociation<UUID> var1) {
            return ((UUID)var1.first).equals(this.val$bluetoothGattCharacteristic.getUuid());
         }
      };
   }

   byte[] getNextBatch(ByteBuffer var1, int var2) {
      var2 = Math.min(var1.remaining(), var2);
      byte[] var3 = this.tempBatchArray;
      if (var3 == null || var3.length != var2) {
         this.tempBatchArray = new byte[var2];
      }

      var1.get(this.tempBatchArray);
      return this.tempBatchArray;
   }

   @Override
   protected void protectedRun(ObservableEmitter<byte[]> var1, QueueReleaseInterface var2) {
      int var3 = this.batchSizeProvider.getPayloadSizeLimit();
      if (var3 > 0) {
         Observable var5 = Observable.error(new BleGattCallbackTimeoutException(this.bluetoothGatt, BleGattOperationType.CHARACTERISTIC_LONG_WRITE));
         ByteBuffer var4 = ByteBuffer.wrap(this.bytesToWrite);
         QueueReleasingEmitterWrapper var7 = new QueueReleasingEmitterWrapper(var1, var2);
         CharacteristicLongWriteOperation.IntSupplier var8 = new CharacteristicLongWriteOperation.IntSupplier(this, var4, var3) {
            final CharacteristicLongWriteOperation this$0;
            final int val$batchSize;
            final ByteBuffer val$byteBuffer;

            {
               this.this$0 = var1;
               this.val$byteBuffer = var2x;
               this.val$batchSize = var3x;
            }

            @Override
            public int get() {
               return (int)Math.ceil((float)this.val$byteBuffer.position() / this.val$batchSize) - 1;
            }
         };
         this.writeBatchAndObserve(var3, var4, var8)
            .subscribeOn(this.bluetoothInteractionScheduler)
            .filter(writeResponseForMatchingCharacteristic(this.bluetoothGattCharacteristic))
            .take(1L)
            .timeout(this.timeoutConfiguration.timeout, this.timeoutConfiguration.timeoutTimeUnit, this.timeoutConfiguration.timeoutScheduler, var5)
            .repeatWhen(bufferIsNotEmptyAndOperationHasBeenAcknowledgedAndNotUnsubscribed(this.writeOperationAckStrategy, var4, var7))
            .retryWhen(errorIsRetryableAndAccordingTo(this.writeOperationRetryStrategy, var4, var3, var8))
            .subscribe(new Observer<ByteAssociation<UUID>>(this, var7) {
               final CharacteristicLongWriteOperation this$0;
               final QueueReleasingEmitterWrapper val$emitterWrapper;

               {
                  this.this$0 = var1;
                  this.val$emitterWrapper = var2x;
               }

               @Override
               public void onComplete() {
                  this.val$emitterWrapper.onNext(this.this$0.bytesToWrite);
                  this.val$emitterWrapper.onComplete();
               }

               @Override
               public void onError(Throwable var1) {
                  this.val$emitterWrapper.onError(var1);
               }

               public void onNext(ByteAssociation<UUID> var1) {
               }

               @Override
               public void onSubscribe(Disposable var1) {
               }
            });
      } else {
         StringBuilder var6 = new StringBuilder("batchSizeProvider value must be greater than zero (now: ");
         var6.append(var3);
         var6.append(")");
         throw new IllegalArgumentException(var6.toString());
      }
   }

   @Override
   protected BleException provideException(DeadObjectException var1) {
      return new BleDisconnectedException(var1, this.bluetoothGatt.getDevice().getAddress(), -1);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CharacteristicLongWriteOperation{");
      var1.append(LoggerUtil.commonMacMessage(this.bluetoothGatt));
      var1.append(", characteristic=");
      var1.append(LoggerUtil.wrap(this.bluetoothGattCharacteristic, false));
      var1.append(", maxBatchSize=");
      var1.append(this.batchSizeProvider.getPayloadSizeLimit());
      var1.append('}');
      return var1.toString();
   }

   void writeData(byte[] var1, CharacteristicLongWriteOperation.IntSupplier var2) {
      if (RxBleLog.isAtLeast(3)) {
         RxBleLog.d("Writing batch #%04d: %s", var2.get(), LoggerUtil.bytesToHex(var1));
      }

      this.bluetoothGattCharacteristic.setValue(var1);
      if (!this.bluetoothGatt.writeCharacteristic(this.bluetoothGattCharacteristic)) {
         throw new BleGattCannotStartException(this.bluetoothGatt, BleGattOperationType.CHARACTERISTIC_LONG_WRITE);
      }
   }

   interface IntSupplier {
      int get();
   }
}
