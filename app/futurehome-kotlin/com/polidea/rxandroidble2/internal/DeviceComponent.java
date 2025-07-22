package com.polidea.rxandroidble2.internal;

import bleshadow.dagger.BindsInstance;
import bleshadow.dagger.Subcomponent;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.RxBleDevice;

@Subcomponent(
   modules = {DeviceModule.class}
)
@DeviceScope
public interface DeviceComponent {
   @DeviceScope
   RxBleDevice provideDevice();

   @bleshadow.dagger.Subcomponent.Builder
   public interface Builder {
      DeviceComponent build();

      @BindsInstance
      DeviceComponent.Builder macAddress(@Named("mac-address") String var1);
   }
}
