package com.polidea.rxandroidble2.internal.scan;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.util.ObservableUtil;
import com.polidea.rxandroidble2.scan.ScanCallbackType;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import java.util.concurrent.TimeUnit;

public class ScanSettingsEmulator {
   final ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult> emulateFirstMatch;
   private final ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult> emulateFirstMatchAndMatchLost;
   final ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult> emulateMatchLost = new ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult>(
      this
   ) {
      final ScanSettingsEmulator this$0;

      {
         this.this$0 = var1;
      }

      public Observable<RxBleInternalScanResult> apply(Observable<RxBleInternalScanResult> var1) {
         return var1.debounce(10L, TimeUnit.SECONDS, this.this$0.scheduler).map(ScanSettingsEmulator.toMatchLost());
      }
   };
   final Scheduler scheduler;

   @Inject
   public ScanSettingsEmulator(@Named("computation") Scheduler var1) {
      this.emulateFirstMatchAndMatchLost = new ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult>(this) {
         final ScanSettingsEmulator this$0;

         {
            this.this$0 = var1;
         }

         public Observable<RxBleInternalScanResult> apply(Observable<RxBleInternalScanResult> var1) {
            return var1.publish(new Function<Observable<RxBleInternalScanResult>, Observable<RxBleInternalScanResult>>(this) {
               final <unrepresentable> this$1;

               {
                  this.this$1 = var1;
               }

               public Observable<RxBleInternalScanResult> apply(Observable<RxBleInternalScanResult> var1) {
                  return Observable.merge(var1.compose(this.this$1.this$0.emulateFirstMatch), var1.compose(this.this$1.this$0.emulateMatchLost));
               }
            });
         }
      };
      this.scheduler = var1;
      this.emulateFirstMatch = new ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult>(this, var1) {
         final Function<RxBleInternalScanResult, Observable<?>> emitAfterTimerFunc;
         final Function<Observable<RxBleInternalScanResult>, Observable<RxBleInternalScanResult>> takeFirstFromEachWindowFunc;
         final ScanSettingsEmulator this$0;
         final Observable<Long> timerObservable;
         final Function<RxBleInternalScanResult, RxBleInternalScanResult> toFirstMatchFunc;
         final Scheduler val$scheduler;

         {
            this.this$0 = var1;
            this.val$scheduler = var2;
            this.toFirstMatchFunc = ScanSettingsEmulator.toFirstMatch();
            this.timerObservable = Observable.timer(10L, TimeUnit.SECONDS, var2);
            this.emitAfterTimerFunc = new Function<RxBleInternalScanResult, Observable<?>>(this) {
               final <unrepresentable> this$1;

               {
                  this.this$1 = var1;
               }

               public Observable<?> apply(RxBleInternalScanResult var1) {
                  return this.this$1.timerObservable;
               }
            };
            this.takeFirstFromEachWindowFunc = new Function<Observable<RxBleInternalScanResult>, Observable<RxBleInternalScanResult>>(this) {
               final <unrepresentable> this$1;

               {
                  this.this$1 = var1;
               }

               public Observable<RxBleInternalScanResult> apply(Observable<RxBleInternalScanResult> var1) {
                  return var1.take(1L);
               }
            };
         }

         public Observable<RxBleInternalScanResult> apply(Observable<RxBleInternalScanResult> var1) {
            return var1.publish(
               new Function<Observable<RxBleInternalScanResult>, ObservableSource<RxBleInternalScanResult>>(this) {
                  final <unrepresentable> this$1;

                  {
                     this.this$1 = var1;
                  }

                  public ObservableSource<RxBleInternalScanResult> apply(Observable<RxBleInternalScanResult> var1) {
                     return var1.window(var1.switchMap(this.this$1.emitAfterTimerFunc))
                        .flatMap(this.this$1.takeFirstFromEachWindowFunc)
                        .map(this.this$1.toFirstMatchFunc);
                  }
               }
            );
         }
      };
   }

   private ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult> repeatedWindowTransformer(int var1) {
      return new ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult>(this, var1, Math.max(TimeUnit.SECONDS.toMillis(5L) - var1, 0L)) {
         final ScanSettingsEmulator this$0;
         final long val$delayToNextWindow;
         final int val$windowInMillis;

         {
            this.this$0 = var1;
            this.val$windowInMillis = var2;
            this.val$delayToNextWindow = var3;
         }

         public Observable<RxBleInternalScanResult> apply(Observable<RxBleInternalScanResult> var1) {
            return var1.take(this.val$windowInMillis, TimeUnit.MILLISECONDS, this.this$0.scheduler)
               .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>(this) {
                  final <unrepresentable> this$1;

                  {
                     this.this$1 = var1;
                  }

                  public ObservableSource<?> apply(Observable<Object> var1) {
                     return var1.delay(this.this$1.val$delayToNextWindow, TimeUnit.MILLISECONDS, this.this$1.this$0.scheduler);
                  }
               });
         }
      };
   }

   private ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult> scanModeBalancedTransformer() {
      return this.repeatedWindowTransformer(2500);
   }

   private ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult> scanModeLowPowerTransformer() {
      return this.repeatedWindowTransformer(500);
   }

   private static ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult> splitByAddressAndForEach(
      ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult> var0
   ) {
      return new ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult>(var0) {
         final ObservableTransformer val$compose;

         {
            this.val$compose = var1;
         }

         public Observable<RxBleInternalScanResult> apply(Observable<RxBleInternalScanResult> var1) {
            return var1.groupBy(new Function<RxBleInternalScanResult, String>(this) {
               final <unrepresentable> this$0;

               {
                  this.this$0 = var1;
               }

               public String apply(RxBleInternalScanResult var1) {
                  return var1.getBluetoothDevice().getAddress();
               }
            }).flatMap(new Function<GroupedObservable<String, RxBleInternalScanResult>, Observable<RxBleInternalScanResult>>(this) {
               final <unrepresentable> this$0;

               {
                  this.this$0 = var1;
               }

               public Observable<RxBleInternalScanResult> apply(GroupedObservable<String, RxBleInternalScanResult> var1) {
                  return var1.compose(this.this$0.val$compose);
               }
            });
         }
      };
   }

   static Function<RxBleInternalScanResult, RxBleInternalScanResult> toFirstMatch() {
      return new Function<RxBleInternalScanResult, RxBleInternalScanResult>() {
         public RxBleInternalScanResult apply(RxBleInternalScanResult var1) {
            return new RxBleInternalScanResult(
               var1.getBluetoothDevice(),
               var1.getRssi(),
               var1.getTimestampNanos(),
               var1.getScanRecord(),
               ScanCallbackType.CALLBACK_TYPE_FIRST_MATCH,
               var1.isConnectable()
            );
         }
      };
   }

   static Function<RxBleInternalScanResult, RxBleInternalScanResult> toMatchLost() {
      return new Function<RxBleInternalScanResult, RxBleInternalScanResult>() {
         public RxBleInternalScanResult apply(RxBleInternalScanResult var1) {
            return new RxBleInternalScanResult(
               var1.getBluetoothDevice(),
               var1.getRssi(),
               var1.getTimestampNanos(),
               var1.getScanRecord(),
               ScanCallbackType.CALLBACK_TYPE_MATCH_LOST,
               var1.isConnectable()
            );
         }
      };
   }

   ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult> emulateCallbackType(int var1) {
      if (var1 != 2) {
         if (var1 != 4) {
            return var1 != 6 ? ObservableUtil.identityTransformer() : splitByAddressAndForEach(this.emulateFirstMatchAndMatchLost);
         } else {
            return splitByAddressAndForEach(this.emulateMatchLost);
         }
      } else {
         return splitByAddressAndForEach(this.emulateFirstMatch);
      }
   }

   ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult> emulateScanMode(int var1) {
      if (var1 != -1) {
         if (var1 != 0) {
            if (var1 != 1) {
               return ObservableUtil.identityTransformer();
            }

            return this.scanModeBalancedTransformer();
         }
      } else {
         RxBleLog.w("Cannot emulate opportunistic scan mode since it is OS dependent - fallthrough to low power");
      }

      return this.scanModeLowPowerTransformer();
   }
}
