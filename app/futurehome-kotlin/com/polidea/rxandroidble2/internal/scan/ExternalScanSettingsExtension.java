package com.polidea.rxandroidble2.internal.scan;

public interface ExternalScanSettingsExtension<R extends ExternalScanSettingsExtension<R>> {
   R copyWithCallbackType(int var1);

   boolean shouldCheckLocationProviderState();

   public interface Builder<T extends ExternalScanSettingsExtension.Builder<T>> {
      T setShouldCheckLocationServicesState(boolean var1);
   }
}
