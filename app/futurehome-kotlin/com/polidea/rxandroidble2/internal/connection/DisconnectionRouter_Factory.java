package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.RxBleAdapterStateObservable;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import io.reactivex.Observable;

public final class DisconnectionRouter_Factory implements Factory<DisconnectionRouter> {
   private final Provider<Observable<RxBleAdapterStateObservable.BleAdapterState>> adapterStateObservableProvider;
   private final Provider<RxBleAdapterWrapper> adapterWrapperProvider;
   private final Provider<String> macAddressProvider;

   public DisconnectionRouter_Factory(
      Provider<String> var1, Provider<RxBleAdapterWrapper> var2, Provider<Observable<RxBleAdapterStateObservable.BleAdapterState>> var3
   ) {
      this.macAddressProvider = var1;
      this.adapterWrapperProvider = var2;
      this.adapterStateObservableProvider = var3;
   }

   public static DisconnectionRouter_Factory create(
      Provider<String> var0, Provider<RxBleAdapterWrapper> var1, Provider<Observable<RxBleAdapterStateObservable.BleAdapterState>> var2
   ) {
      return new DisconnectionRouter_Factory(var0, var1, var2);
   }

   public static DisconnectionRouter newInstance(String var0, RxBleAdapterWrapper var1, Observable<RxBleAdapterStateObservable.BleAdapterState> var2) {
      return new DisconnectionRouter(var0, var1, var2);
   }

   public DisconnectionRouter get() {
      return newInstance(
         (String)this.macAddressProvider.get(),
         (RxBleAdapterWrapper)this.adapterWrapperProvider.get(),
         (Observable<RxBleAdapterStateObservable.BleAdapterState>)this.adapterStateObservableProvider.get()
      );
   }
}
