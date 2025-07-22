package io.flutter.embedding.engine.plugins.broadcastreceiver;

import android.content.BroadcastReceiver;
import androidx.lifecycle.Lifecycle;

public interface BroadcastReceiverControlSurface {
   void attachToBroadcastReceiver(BroadcastReceiver var1, Lifecycle var2);

   void detachFromBroadcastReceiver();
}
