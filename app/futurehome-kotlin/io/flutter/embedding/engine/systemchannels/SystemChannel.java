package io.flutter.embedding.engine.systemchannels;

import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.JSONMessageCodec;
import java.util.HashMap;

public class SystemChannel {
   private static final String TAG = "SystemChannel";
   public final BasicMessageChannel<Object> channel;

   public SystemChannel(DartExecutor var1) {
      this.channel = new BasicMessageChannel<>(var1, "flutter/system", JSONMessageCodec.INSTANCE);
   }

   public void sendMemoryPressureWarning() {
      Log.v("SystemChannel", "Sending memory pressure warning to Flutter.");
      HashMap var1 = new HashMap(1);
      var1.put("type", "memoryPressure");
      this.channel.send(var1);
   }
}
