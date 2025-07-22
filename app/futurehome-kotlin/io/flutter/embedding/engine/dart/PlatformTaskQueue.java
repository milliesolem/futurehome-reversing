package io.flutter.embedding.engine.dart;

import android.os.Handler;
import android.os.Looper;
import io.flutter.util.HandlerCompat;

public class PlatformTaskQueue implements DartMessenger.DartMessengerTaskQueue {
   private final Handler handler = HandlerCompat.createAsyncHandler(Looper.getMainLooper());

   @Override
   public void dispatch(Runnable var1) {
      this.handler.post(var1);
   }
}
