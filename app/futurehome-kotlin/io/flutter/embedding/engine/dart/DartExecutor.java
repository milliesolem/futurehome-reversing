package io.flutter.embedding.engine.dart;

import android.content.res.AssetManager;
import io.flutter.FlutterInjector;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.embedding.engine.loader.FlutterLoader;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StringCodec;
import io.flutter.util.TraceSection;
import io.flutter.view.FlutterCallbackInformation;
import java.nio.ByteBuffer;
import java.util.List;

public class DartExecutor implements BinaryMessenger {
   private static final String TAG = "DartExecutor";
   private final AssetManager assetManager;
   private final BinaryMessenger binaryMessenger;
   private final DartMessenger dartMessenger;
   private final FlutterJNI flutterJNI;
   private boolean isApplicationRunning = false;
   private final BinaryMessenger.BinaryMessageHandler isolateChannelMessageHandler;
   private String isolateServiceId;
   private DartExecutor.IsolateServiceIdListener isolateServiceIdListener;

   public DartExecutor(FlutterJNI var1, AssetManager var2) {
      BinaryMessenger.BinaryMessageHandler var3 = new BinaryMessenger.BinaryMessageHandler(this) {
         final DartExecutor this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onMessage(ByteBuffer var1, BinaryMessenger.BinaryReply var2x) {
            this.this$0.isolateServiceId = StringCodec.INSTANCE.decodeMessage(var1);
            if (this.this$0.isolateServiceIdListener != null) {
               this.this$0.isolateServiceIdListener.onIsolateServiceIdAvailable(this.this$0.isolateServiceId);
            }
         }
      };
      this.isolateChannelMessageHandler = var3;
      this.flutterJNI = var1;
      this.assetManager = var2;
      DartMessenger var4 = new DartMessenger(var1);
      this.dartMessenger = var4;
      var4.setMessageHandler("flutter/isolate", var3);
      this.binaryMessenger = new DartExecutor.DefaultBinaryMessenger(var4);
      if (var1.isAttached()) {
         this.isApplicationRunning = true;
      }
   }

   @Deprecated
   @Override
   public void disableBufferingIncomingMessages() {
      this.dartMessenger.disableBufferingIncomingMessages();
   }

   @Deprecated
   @Override
   public void enableBufferingIncomingMessages() {
      this.dartMessenger.enableBufferingIncomingMessages();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void executeDartCallback(DartExecutor.DartCallback var1) {
      if (this.isApplicationRunning) {
         Log.w("DartExecutor", "Attempted to run a DartExecutor that is already running.");
      } else {
         TraceSection var2 = TraceSection.scoped("DartExecutor#executeDartCallback");

         try {
            StringBuilder var3 = new StringBuilder("Executing Dart callback: ");
            var3.append(var1);
            Log.v("DartExecutor", var3.toString());
            this.flutterJNI
               .runBundleAndSnapshotFromLibrary(
                  var1.pathToBundle, var1.callbackHandle.callbackName, var1.callbackHandle.callbackLibraryPath, var1.androidAssetManager, null
               );
            this.isApplicationRunning = true;
         } catch (Throwable var9) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
                  throw var9;
               }
            }

            throw var9;
         }

         if (var2 != null) {
            var2.close();
         }
      }
   }

   public void executeDartEntrypoint(DartExecutor.DartEntrypoint var1) {
      this.executeDartEntrypoint(var1, null);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void executeDartEntrypoint(DartExecutor.DartEntrypoint var1, List<String> var2) {
      if (this.isApplicationRunning) {
         Log.w("DartExecutor", "Attempted to run a DartExecutor that is already running.");
      } else {
         TraceSection var3 = TraceSection.scoped("DartExecutor#executeDartEntrypoint");

         try {
            StringBuilder var4 = new StringBuilder("Executing Dart entrypoint: ");
            var4.append(var1);
            Log.v("DartExecutor", var4.toString());
            this.flutterJNI
               .runBundleAndSnapshotFromLibrary(var1.pathToBundle, var1.dartEntrypointFunctionName, var1.dartEntrypointLibrary, this.assetManager, var2);
            this.isApplicationRunning = true;
         } catch (Throwable var10) {
            if (var3 != null) {
               try {
                  var3.close();
               } catch (Throwable var9) {
                  var10.addSuppressed(var9);
                  throw var10;
               }
            }

            throw var10;
         }

         if (var3 != null) {
            var3.close();
         }
      }
   }

   public BinaryMessenger getBinaryMessenger() {
      return this.binaryMessenger;
   }

   public String getIsolateServiceId() {
      return this.isolateServiceId;
   }

   public int getPendingChannelResponseCount() {
      return this.dartMessenger.getPendingChannelResponseCount();
   }

   public boolean isExecutingDart() {
      return this.isApplicationRunning;
   }

   @Deprecated
   @Override
   public BinaryMessenger.TaskQueue makeBackgroundTaskQueue(BinaryMessenger.TaskQueueOptions var1) {
      return this.binaryMessenger.makeBackgroundTaskQueue(var1);
   }

   public void notifyLowMemoryWarning() {
      if (this.flutterJNI.isAttached()) {
         this.flutterJNI.notifyLowMemoryWarning();
      }
   }

   public void onAttachedToJNI() {
      Log.v("DartExecutor", "Attached to JNI. Registering the platform message handler for this Dart execution context.");
      this.flutterJNI.setPlatformMessageHandler(this.dartMessenger);
   }

   public void onDetachedFromJNI() {
      Log.v("DartExecutor", "Detached from JNI. De-registering the platform message handler for this Dart execution context.");
      this.flutterJNI.setPlatformMessageHandler(null);
   }

   @Deprecated
   @Override
   public void send(String var1, ByteBuffer var2) {
      this.binaryMessenger.send(var1, var2);
   }

   @Deprecated
   @Override
   public void send(String var1, ByteBuffer var2, BinaryMessenger.BinaryReply var3) {
      this.binaryMessenger.send(var1, var2, var3);
   }

   public void setIsolateServiceIdListener(DartExecutor.IsolateServiceIdListener var1) {
      this.isolateServiceIdListener = var1;
      if (var1 != null) {
         String var2 = this.isolateServiceId;
         if (var2 != null) {
            var1.onIsolateServiceIdAvailable(var2);
         }
      }
   }

   @Deprecated
   @Override
   public void setMessageHandler(String var1, BinaryMessenger.BinaryMessageHandler var2) {
      this.binaryMessenger.setMessageHandler(var1, var2);
   }

   @Deprecated
   @Override
   public void setMessageHandler(String var1, BinaryMessenger.BinaryMessageHandler var2, BinaryMessenger.TaskQueue var3) {
      this.binaryMessenger.setMessageHandler(var1, var2, var3);
   }

   public static class DartCallback {
      public final AssetManager androidAssetManager;
      public final FlutterCallbackInformation callbackHandle;
      public final String pathToBundle;

      public DartCallback(AssetManager var1, String var2, FlutterCallbackInformation var3) {
         this.androidAssetManager = var1;
         this.pathToBundle = var2;
         this.callbackHandle = var3;
      }

      @Override
      public String toString() {
         StringBuilder var1 = new StringBuilder("DartCallback( bundle path: ");
         var1.append(this.pathToBundle);
         var1.append(", library path: ");
         var1.append(this.callbackHandle.callbackLibraryPath);
         var1.append(", function: ");
         var1.append(this.callbackHandle.callbackName);
         var1.append(" )");
         return var1.toString();
      }
   }

   public static class DartEntrypoint {
      public final String dartEntrypointFunctionName;
      public final String dartEntrypointLibrary;
      public final String pathToBundle;

      public DartEntrypoint(String var1, String var2) {
         this.pathToBundle = var1;
         this.dartEntrypointLibrary = null;
         this.dartEntrypointFunctionName = var2;
      }

      public DartEntrypoint(String var1, String var2, String var3) {
         this.pathToBundle = var1;
         this.dartEntrypointLibrary = var2;
         this.dartEntrypointFunctionName = var3;
      }

      public static DartExecutor.DartEntrypoint createDefault() {
         FlutterLoader var0 = FlutterInjector.instance().flutterLoader();
         if (var0.initialized()) {
            return new DartExecutor.DartEntrypoint(var0.findAppBundlePath(), "main");
         } else {
            throw new AssertionError("DartEntrypoints can only be created once a FlutterEngine is created.");
         }
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (var1 != null && this.getClass() == var1.getClass()) {
            var1 = var1;
            return !this.pathToBundle.equals(var1.pathToBundle) ? false : this.dartEntrypointFunctionName.equals(var1.dartEntrypointFunctionName);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return this.pathToBundle.hashCode() * 31 + this.dartEntrypointFunctionName.hashCode();
      }

      @Override
      public String toString() {
         StringBuilder var1 = new StringBuilder("DartEntrypoint( bundle path: ");
         var1.append(this.pathToBundle);
         var1.append(", function: ");
         var1.append(this.dartEntrypointFunctionName);
         var1.append(" )");
         return var1.toString();
      }
   }

   private static class DefaultBinaryMessenger implements BinaryMessenger {
      private final DartMessenger messenger;

      private DefaultBinaryMessenger(DartMessenger var1) {
         this.messenger = var1;
      }

      @Override
      public void disableBufferingIncomingMessages() {
         this.messenger.disableBufferingIncomingMessages();
      }

      @Override
      public void enableBufferingIncomingMessages() {
         this.messenger.enableBufferingIncomingMessages();
      }

      @Override
      public BinaryMessenger.TaskQueue makeBackgroundTaskQueue(BinaryMessenger.TaskQueueOptions var1) {
         return this.messenger.makeBackgroundTaskQueue(var1);
      }

      @Override
      public void send(String var1, ByteBuffer var2) {
         this.messenger.send(var1, var2, null);
      }

      @Override
      public void send(String var1, ByteBuffer var2, BinaryMessenger.BinaryReply var3) {
         this.messenger.send(var1, var2, var3);
      }

      @Override
      public void setMessageHandler(String var1, BinaryMessenger.BinaryMessageHandler var2) {
         this.messenger.setMessageHandler(var1, var2);
      }

      @Override
      public void setMessageHandler(String var1, BinaryMessenger.BinaryMessageHandler var2, BinaryMessenger.TaskQueue var3) {
         this.messenger.setMessageHandler(var1, var2, var3);
      }
   }

   public interface IsolateServiceIdListener {
      void onIsolateServiceIdAvailable(String var1);
   }
}
