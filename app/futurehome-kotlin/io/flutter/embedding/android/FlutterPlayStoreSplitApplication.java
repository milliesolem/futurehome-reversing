package io.flutter.embedding.android;

import com.google.android.play.core.splitcompat.SplitCompatApplication;
import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.deferredcomponents.PlayStoreDeferredComponentManager;

public class FlutterPlayStoreSplitApplication extends SplitCompatApplication {
   public void onCreate() {
      super.onCreate();
      PlayStoreDeferredComponentManager var1 = new PlayStoreDeferredComponentManager(this, null);
      FlutterInjector.setInstance(new FlutterInjector.Builder().setDeferredComponentManager(var1).build());
   }
}
