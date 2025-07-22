package com.polidea.rxandroidble2.internal;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.ClientScope;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.internal.cache.DeviceComponentCache;
import java.util.Map;

@ClientScope
public class RxBleDeviceProvider {
   private final Map<String, DeviceComponent> cachedDeviceComponents;
   private final Provider<DeviceComponent.Builder> deviceComponentBuilder;

   @Inject
   public RxBleDeviceProvider(DeviceComponentCache var1, Provider<DeviceComponent.Builder> var2) {
      this.cachedDeviceComponents = var1;
      this.deviceComponentBuilder = var2;
   }

   public RxBleDevice getBleDevice(String param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield com/polidea/rxandroidble2/internal/RxBleDeviceProvider.cachedDeviceComponents Ljava/util/Map;
      // 04: aload 1
      // 05: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0a: checkcast com/polidea/rxandroidble2/internal/DeviceComponent
      // 0d: astore 2
      // 0e: aload 2
      // 0f: ifnull 19
      // 12: aload 2
      // 13: invokeinterface com/polidea/rxandroidble2/internal/DeviceComponent.provideDevice ()Lcom/polidea/rxandroidble2/RxBleDevice; 1
      // 18: areturn
      // 19: aload 0
      // 1a: getfield com/polidea/rxandroidble2/internal/RxBleDeviceProvider.cachedDeviceComponents Ljava/util/Map;
      // 1d: astore 2
      // 1e: aload 2
      // 1f: monitorenter
      // 20: aload 0
      // 21: getfield com/polidea/rxandroidble2/internal/RxBleDeviceProvider.cachedDeviceComponents Ljava/util/Map;
      // 24: aload 1
      // 25: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 2a: checkcast com/polidea/rxandroidble2/internal/DeviceComponent
      // 2d: astore 3
      // 2e: aload 3
      // 2f: ifnull 3d
      // 32: aload 3
      // 33: invokeinterface com/polidea/rxandroidble2/internal/DeviceComponent.provideDevice ()Lcom/polidea/rxandroidble2/RxBleDevice; 1
      // 38: astore 1
      // 39: aload 2
      // 3a: monitorexit
      // 3b: aload 1
      // 3c: areturn
      // 3d: aload 0
      // 3e: getfield com/polidea/rxandroidble2/internal/RxBleDeviceProvider.deviceComponentBuilder Lbleshadow/javax/inject/Provider;
      // 41: invokeinterface bleshadow/javax/inject/Provider.get ()Ljava/lang/Object; 1
      // 46: checkcast com/polidea/rxandroidble2/internal/DeviceComponent$Builder
      // 49: aload 1
      // 4a: invokeinterface com/polidea/rxandroidble2/internal/DeviceComponent$Builder.macAddress (Ljava/lang/String;)Lcom/polidea/rxandroidble2/internal/DeviceComponent$Builder; 2
      // 4f: invokeinterface com/polidea/rxandroidble2/internal/DeviceComponent$Builder.build ()Lcom/polidea/rxandroidble2/internal/DeviceComponent; 1
      // 54: astore 4
      // 56: aload 4
      // 58: invokeinterface com/polidea/rxandroidble2/internal/DeviceComponent.provideDevice ()Lcom/polidea/rxandroidble2/RxBleDevice; 1
      // 5d: astore 3
      // 5e: aload 0
      // 5f: getfield com/polidea/rxandroidble2/internal/RxBleDeviceProvider.cachedDeviceComponents Ljava/util/Map;
      // 62: aload 1
      // 63: aload 4
      // 65: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 6a: pop
      // 6b: aload 2
      // 6c: monitorexit
      // 6d: aload 3
      // 6e: areturn
      // 6f: astore 1
      // 70: aload 2
      // 71: monitorexit
      // 72: aload 1
      // 73: athrow
   }
}
