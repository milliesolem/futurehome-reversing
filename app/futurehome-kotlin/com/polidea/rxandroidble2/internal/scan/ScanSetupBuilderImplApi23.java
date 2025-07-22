package com.polidea.rxandroidble2.internal.scan;

import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.operations.ScanOperationApi21;
import com.polidea.rxandroidble2.internal.util.ObservableUtil;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanSettings;
import io.reactivex.ObservableTransformer;

public class ScanSetupBuilderImplApi23 implements ScanSetupBuilder {
   private final AndroidScanObjectsConverter androidScanObjectsConverter;
   private final InternalScanResultCreator internalScanResultCreator;
   private final RxBleAdapterWrapper rxBleAdapterWrapper;
   private final ScanSettingsEmulator scanSettingsEmulator;

   @Inject
   ScanSetupBuilderImplApi23(RxBleAdapterWrapper var1, InternalScanResultCreator var2, ScanSettingsEmulator var3, AndroidScanObjectsConverter var4) {
      this.rxBleAdapterWrapper = var1;
      this.internalScanResultCreator = var2;
      this.scanSettingsEmulator = var3;
      this.androidScanObjectsConverter = var4;
   }

   private static boolean areFiltersSpecified(ScanFilter[] var0) {
      int var3 = var0.length;
      int var2 = 0;

      boolean var1;
      for (var1 = true; var2 < var3; var2++) {
         var1 &= var0[var2].isAllFieldsEmpty();
      }

      return var1 ^ true;
   }

   @Override
   public ScanSetup build(ScanSettings var1, ScanFilter... var2) {
      boolean var4 = areFiltersSpecified(var2);
      boolean var3;
      if (var1.getCallbackType() != 1) {
         var3 = true;
      } else {
         var3 = false;
      }

      ObservableTransformer var7 = ObservableUtil.identityTransformer();
      ObservableTransformer var6 = var7;
      ScanSettings var5 = var1;
      if (var3) {
         var6 = var7;
         var5 = var1;
         if (!var4) {
            RxBleLog.d(
               "ScanSettings.callbackType != CALLBACK_TYPE_ALL_MATCHES but no (or only empty) filters are specified. Falling back to callbackType emulation."
            );
            var6 = this.scanSettingsEmulator.emulateCallbackType(var1.getCallbackType());
            var5 = var1.copyWithCallbackType(1);
         }
      }

      return new ScanSetup(
         new ScanOperationApi21(
            this.rxBleAdapterWrapper, this.internalScanResultCreator, this.androidScanObjectsConverter, var5, new EmulatedScanFilterMatcher(), var2
         ),
         var6
      );
   }
}
