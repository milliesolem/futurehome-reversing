package io.flutter.view;

import android.hardware.display.DisplayManager;
import android.view.Choreographer;
import io.flutter.embedding.engine.FlutterJNI;

public class VsyncWaiter {
   private static VsyncWaiter instance;
   private static VsyncWaiter.DisplayListener listener;
   private final FlutterJNI.AsyncWaitForVsyncDelegate asyncWaitForVsyncDelegate;
   private FlutterJNI flutterJNI;
   private VsyncWaiter.FrameCallback frameCallback;
   private long refreshPeriodNanos = -1L;

   private VsyncWaiter(FlutterJNI var1) {
      this.frameCallback = new VsyncWaiter.FrameCallback(this, 0L);
      this.asyncWaitForVsyncDelegate = new FlutterJNI.AsyncWaitForVsyncDelegate(this) {
         final VsyncWaiter this$0;

         {
            this.this$0 = var1;
         }

         private android.view.Choreographer.FrameCallback obtainFrameCallback(long var1) {
            if (this.this$0.frameCallback != null) {
               this.this$0.frameCallback.cookie = var1;
               VsyncWaiter.FrameCallback var3 = this.this$0.frameCallback;
               this.this$0.frameCallback = null;
               return var3;
            } else {
               return this.this$0.new FrameCallback(this.this$0, var1);
            }
         }

         @Override
         public void asyncWaitForVsync(long var1) {
            Choreographer.getInstance().postFrameCallback(this.obtainFrameCallback(var1));
         }
      };
      this.flutterJNI = var1;
   }

   public static VsyncWaiter getInstance(float var0, FlutterJNI var1) {
      if (instance == null) {
         instance = new VsyncWaiter(var1);
      }

      var1.setRefreshRateFPS(var0);
      VsyncWaiter var2 = instance;
      var2.refreshPeriodNanos = (long)(1.0E9 / var0);
      return var2;
   }

   public static VsyncWaiter getInstance(DisplayManager var0, FlutterJNI var1) {
      if (instance == null) {
         instance = new VsyncWaiter(var1);
      }

      if (listener == null) {
         VsyncWaiter var3 = instance;
         VsyncWaiter.DisplayListener var4 = var3.new DisplayListener(var3, var0);
         listener = var4;
         var4.register();
      }

      if (instance.refreshPeriodNanos == -1L) {
         float var2 = var0.getDisplay(0).getRefreshRate();
         instance.refreshPeriodNanos = (long)(1.0E9 / var2);
         var1.setRefreshRateFPS(var2);
      }

      return instance;
   }

   public static void reset() {
      instance = null;
      listener = null;
   }

   public void init() {
      this.flutterJNI.setAsyncWaitForVsyncDelegate(this.asyncWaitForVsyncDelegate);
   }

   class DisplayListener implements android.hardware.display.DisplayManager.DisplayListener {
      private DisplayManager displayManager;
      final VsyncWaiter this$0;

      DisplayListener(VsyncWaiter var1, DisplayManager var2) {
         this.this$0 = var1;
         this.displayManager = var2;
      }

      public void onDisplayAdded(int var1) {
      }

      public void onDisplayChanged(int var1) {
         if (var1 == 0) {
            float var2 = this.displayManager.getDisplay(0).getRefreshRate();
            this.this$0.refreshPeriodNanos = (long)(1.0E9 / var2);
            this.this$0.flutterJNI.setRefreshRateFPS(var2);
         }
      }

      public void onDisplayRemoved(int var1) {
      }

      void register() {
         this.displayManager.registerDisplayListener(this, null);
      }
   }

   private class FrameCallback implements android.view.Choreographer.FrameCallback {
      private long cookie;
      final VsyncWaiter this$0;

      FrameCallback(VsyncWaiter var1, long var2) {
         this.this$0 = var1;
         this.cookie = var2;
      }

      public void doFrame(long var1) {
         var1 = System.nanoTime() - var1;
         if (var1 < 0L) {
            var1 = 0L;
         }

         this.this$0.flutterJNI.onVsync(var1, this.this$0.refreshPeriodNanos, this.cookie);
         this.this$0.frameCallback = this;
      }
   }
}
