package com.polidea.rxandroidble2.internal.scan;

import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.operations.ScanOperationApi18;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanSettings;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

public class ScanSetupBuilderImplApi18 implements ScanSetupBuilder {
   private final InternalScanResultCreator internalScanResultCreator;
   private final RxBleAdapterWrapper rxBleAdapterWrapper;
   private final ScanSettingsEmulator scanSettingsEmulator;

   @Inject
   ScanSetupBuilderImplApi18(RxBleAdapterWrapper var1, InternalScanResultCreator var2, ScanSettingsEmulator var3) {
      this.rxBleAdapterWrapper = var1;
      this.internalScanResultCreator = var2;
      this.scanSettingsEmulator = var3;
   }

   @Override
   public ScanSetup build(ScanSettings var1, ScanFilter... var2) {
      ObservableTransformer var3 = this.scanSettingsEmulator.emulateScanMode(var1.getScanMode());
      ObservableTransformer var4 = this.scanSettingsEmulator.emulateCallbackType(var1.getCallbackType());
      return new ScanSetup(
         new ScanOperationApi18(this.rxBleAdapterWrapper, this.internalScanResultCreator, new EmulatedScanFilterMatcher(var2)),
         new ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult>(this, var3, var4) {
            final ScanSetupBuilderImplApi18 this$0;
            final ObservableTransformer val$callbackTypeTransformer;
            final ObservableTransformer val$scanModeTransformer;

            {
               this.this$0 = var1;
               this.val$scanModeTransformer = var2x;
               this.val$callbackTypeTransformer = var3x;
            }

            public Observable<RxBleInternalScanResult> apply(Observable<RxBleInternalScanResult> var1) {
               return var1.compose(this.val$scanModeTransformer).compose(this.val$callbackTypeTransformer);
            }
         }
      );
   }
}
