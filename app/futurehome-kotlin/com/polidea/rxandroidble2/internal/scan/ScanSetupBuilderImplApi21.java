package com.polidea.rxandroidble2.internal.scan;

import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.operations.ScanOperationApi21;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanSettings;
import io.reactivex.ObservableTransformer;

public class ScanSetupBuilderImplApi21 implements ScanSetupBuilder {
   private final AndroidScanObjectsConverter androidScanObjectsConverter;
   private final InternalScanResultCreator internalScanResultCreator;
   private final RxBleAdapterWrapper rxBleAdapterWrapper;
   private final ScanSettingsEmulator scanSettingsEmulator;

   @Inject
   ScanSetupBuilderImplApi21(RxBleAdapterWrapper var1, InternalScanResultCreator var2, ScanSettingsEmulator var3, AndroidScanObjectsConverter var4) {
      this.rxBleAdapterWrapper = var1;
      this.internalScanResultCreator = var2;
      this.scanSettingsEmulator = var3;
      this.androidScanObjectsConverter = var4;
   }

   @Override
   public ScanSetup build(ScanSettings var1, ScanFilter... var2) {
      ObservableTransformer var3 = this.scanSettingsEmulator.emulateCallbackType(var1.getCallbackType());
      return new ScanSetup(
         new ScanOperationApi21(
            this.rxBleAdapterWrapper, this.internalScanResultCreator, this.androidScanObjectsConverter, var1, new EmulatedScanFilterMatcher(var2), null
         ),
         var3
      );
   }
}
