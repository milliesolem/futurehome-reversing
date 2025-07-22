package io.flutter.embedding.engine.deferredcomponents;

import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.embedding.engine.systemchannels.DeferredComponentChannel;

public interface DeferredComponentManager {
   void destroy();

   String getDeferredComponentInstallState(int var1, String var2);

   void installDeferredComponent(int var1, String var2);

   void loadAssets(int var1, String var2);

   void loadDartLibrary(int var1, String var2);

   void setDeferredComponentChannel(DeferredComponentChannel var1);

   void setJNI(FlutterJNI var1);

   boolean uninstallDeferredComponent(int var1, String var2);
}
