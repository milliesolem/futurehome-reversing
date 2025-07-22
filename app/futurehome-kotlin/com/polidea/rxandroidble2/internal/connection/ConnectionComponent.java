package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.BindsInstance;
import bleshadow.dagger.Subcomponent;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.Timeout;
import com.polidea.rxandroidble2.internal.operations.ConnectOperation;
import java.util.Set;

@Subcomponent(
   modules = {ConnectionModule.class}
)
@ConnectionScope
public interface ConnectionComponent {
   @ConnectionScope
   ConnectOperation connectOperation();

   @ConnectionScope
   Set<ConnectionSubscriptionWatcher> connectionSubscriptionWatchers();

   @ConnectionScope
   RxBleGattCallback gattCallback();

   @ConnectionScope
   RxBleConnection rxBleConnection();

   @bleshadow.dagger.Subcomponent.Builder
   public interface Builder {
      @BindsInstance
      ConnectionComponent.Builder autoConnect(@Named("autoConnect") boolean var1);

      ConnectionComponent build();

      @BindsInstance
      ConnectionComponent.Builder operationTimeout(Timeout var1);

      @BindsInstance
      ConnectionComponent.Builder suppressOperationChecks(@Named("suppressOperationChecks") boolean var1);
   }

   public static class NamedBooleans {
      public static final String AUTO_CONNECT = "autoConnect";
      public static final String SUPPRESS_OPERATION_CHECKS = "suppressOperationChecks";

      private NamedBooleans() {
      }
   }

   public static class NamedInts {
      static final String GATT_MTU_MINIMUM = "GATT_MTU_MINIMUM";
      static final String GATT_WRITE_MTU_OVERHEAD = "GATT_WRITE_MTU_OVERHEAD";

      private NamedInts() {
      }
   }
}
