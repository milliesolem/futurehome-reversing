package com.polidea.rxandroidble2.internal.scan;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.util.ScanRecordParser;

public final class InternalScanResultCreator_Factory implements Factory<InternalScanResultCreator> {
   private final Provider<IsConnectableChecker> isConnectableCheckerProvider;
   private final Provider<ScanRecordParser> scanRecordParserProvider;

   public InternalScanResultCreator_Factory(Provider<ScanRecordParser> var1, Provider<IsConnectableChecker> var2) {
      this.scanRecordParserProvider = var1;
      this.isConnectableCheckerProvider = var2;
   }

   public static InternalScanResultCreator_Factory create(Provider<ScanRecordParser> var0, Provider<IsConnectableChecker> var1) {
      return new InternalScanResultCreator_Factory(var0, var1);
   }

   public static InternalScanResultCreator newInstance(ScanRecordParser var0, IsConnectableChecker var1) {
      return new InternalScanResultCreator(var0, var1);
   }

   public InternalScanResultCreator get() {
      return newInstance((ScanRecordParser)this.scanRecordParserProvider.get(), (IsConnectableChecker)this.isConnectableCheckerProvider.get());
   }
}
