package io.sentry.android.core.internal.util;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.Choreographer;
import android.view.FrameMetrics;
import android.view.Window;
import android.view.Window.OnFrameMetricsAvailableListener;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.android.core.BuildInfoProvider;
import io.sentry.android.core.ContextUtils;
import io.sentry.util.Objects;
import j..util.concurrent.ConcurrentHashMap;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

public final class SentryFrameMetricsCollector implements ActivityLifecycleCallbacks {
   private static final long frozenFrameThresholdNanos = TimeUnit.MILLISECONDS.toNanos(700L);
   private static final long oneSecondInNanos = TimeUnit.SECONDS.toNanos(1L);
   private final BuildInfoProvider buildInfoProvider;
   private Choreographer choreographer;
   private Field choreographerLastFrameTimeField;
   private WeakReference<Window> currentWindow;
   private OnFrameMetricsAvailableListener frameMetricsAvailableListener;
   private Handler handler;
   private boolean isAvailable;
   private long lastFrameEndNanos;
   private long lastFrameStartNanos;
   private final Map<String, SentryFrameMetricsCollector.FrameMetricsCollectorListener> listenerMap;
   private final ILogger logger;
   private final Set<Window> trackedWindows = new CopyOnWriteArraySet<>();
   private final SentryFrameMetricsCollector.WindowFrameMetricsManager windowFrameMetricsManager;

   public SentryFrameMetricsCollector(Context var1, ILogger var2, BuildInfoProvider var3) {
      this(var1, var2, var3, new SentryFrameMetricsCollector.WindowFrameMetricsManager() {});
   }

   public SentryFrameMetricsCollector(Context var1, ILogger var2, BuildInfoProvider var3, SentryFrameMetricsCollector.WindowFrameMetricsManager var4) {
      this.listenerMap = new ConcurrentHashMap();
      this.isAvailable = false;
      this.lastFrameStartNanos = 0L;
      this.lastFrameEndNanos = 0L;
      var1 = Objects.requireNonNull(ContextUtils.getApplicationContext(var1), "The context is required");
      this.logger = Objects.requireNonNull(var2, "Logger is required");
      this.buildInfoProvider = Objects.requireNonNull(var3, "BuildInfoProvider is required");
      this.windowFrameMetricsManager = Objects.requireNonNull(var4, "WindowFrameMetricsManager is required");
      if (var1 instanceof Application) {
         if (var3.getSdkInfoVersion() >= 24) {
            this.isAvailable = true;
            HandlerThread var8 = new HandlerThread("io.sentry.android.core.internal.util.SentryFrameMetricsCollector");
            var8.setUncaughtExceptionHandler(new SentryFrameMetricsCollector$$ExternalSyntheticLambda2(var2));
            var8.start();
            this.handler = new Handler(var8.getLooper());
            ((Application)var1).registerActivityLifecycleCallbacks(this);
            new Handler(Looper.getMainLooper()).post(new SentryFrameMetricsCollector$$ExternalSyntheticLambda3(this, var2));

            try {
               Field var7 = Choreographer.class.getDeclaredField("mLastFrameTimeNanos");
               this.choreographerLastFrameTimeField = var7;
               var7.setAccessible(true);
            } catch (NoSuchFieldException var5) {
               var2.log(SentryLevel.ERROR, "Unable to get the frame timestamp from the choreographer: ", var5);
            }

            this.frameMetricsAvailableListener = new SentryFrameMetricsCollector$$ExternalSyntheticLambda4(this, var3);
         }
      }
   }

   public SentryFrameMetricsCollector(Context var1, SentryOptions var2, BuildInfoProvider var3) {
      this(var1, var2, var3, new SentryFrameMetricsCollector.WindowFrameMetricsManager() {});
   }

   public SentryFrameMetricsCollector(Context var1, SentryOptions var2, BuildInfoProvider var3, SentryFrameMetricsCollector.WindowFrameMetricsManager var4) {
      this(var1, var2.getLogger(), var3, var4);
   }

   private long getFrameCpuDuration(FrameMetrics var1) {
      return AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, 0)
         + AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, 1)
         + AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, 2)
         + AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, 3)
         + AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, 4)
         + AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, 5);
   }

   private long getFrameStartTimestamp(FrameMetrics var1) {
      return this.buildInfoProvider.getSdkInfoVersion() >= 26
         ? AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, 10)
         : this.getLastKnownFrameStartTimeNanos();
   }

   public static boolean isFrozen(long var0) {
      boolean var2;
      if (var0 > frozenFrameThresholdNanos) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean isSlow(long var0, long var2) {
      boolean var4;
      if (var0 > var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   private void setCurrentWindow(Window var1) {
      WeakReference var2 = this.currentWindow;
      if (var2 == null || var2.get() != var1) {
         this.currentWindow = new WeakReference<>(var1);
         this.trackCurrentWindow();
      }
   }

   private void stopTrackingWindow(Window var1) {
      if (this.trackedWindows.contains(var1)) {
         if (this.buildInfoProvider.getSdkInfoVersion() >= 24) {
            try {
               this.windowFrameMetricsManager.removeOnFrameMetricsAvailableListener(var1, this.frameMetricsAvailableListener);
            } catch (Exception var3) {
               this.logger.log(SentryLevel.ERROR, "Failed to remove frameMetricsAvailableListener", var3);
            }
         }

         this.trackedWindows.remove(var1);
      }
   }

   private void trackCurrentWindow() {
      WeakReference var1 = this.currentWindow;
      Window var2;
      if (var1 != null) {
         var2 = (Window)var1.get();
      } else {
         var2 = null;
      }

      if (var2 != null
         && this.isAvailable
         && !this.trackedWindows.contains(var2)
         && !this.listenerMap.isEmpty()
         && this.buildInfoProvider.getSdkInfoVersion() >= 24
         && this.handler != null) {
         this.trackedWindows.add(var2);
         this.windowFrameMetricsManager.addOnFrameMetricsAvailableListener(var2, this.frameMetricsAvailableListener, this.handler);
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public long getLastKnownFrameStartTimeNanos() {
      Choreographer var4 = this.choreographer;
      if (var4 != null) {
         Field var3 = this.choreographerLastFrameTimeField;
         if (var3 != null) {
            try {
               var7 = (Long)var3.get(var4);
            } catch (IllegalAccessException var6) {
               return -1L;
            }

            if (var7 != null) {
               try {
                  return var7;
               } catch (IllegalAccessException var5) {
               }
            }
         }
      }

      return -1L;
   }

   public void onActivityCreated(Activity var1, Bundle var2) {
   }

   public void onActivityDestroyed(Activity var1) {
   }

   public void onActivityPaused(Activity var1) {
   }

   public void onActivityResumed(Activity var1) {
   }

   public void onActivitySaveInstanceState(Activity var1, Bundle var2) {
   }

   public void onActivityStarted(Activity var1) {
      this.setCurrentWindow(var1.getWindow());
   }

   public void onActivityStopped(Activity var1) {
      this.stopTrackingWindow(var1.getWindow());
      WeakReference var2 = this.currentWindow;
      if (var2 != null && var2.get() == var1.getWindow()) {
         this.currentWindow = null;
      }
   }

   public String startCollection(SentryFrameMetricsCollector.FrameMetricsCollectorListener var1) {
      if (!this.isAvailable) {
         return null;
      } else {
         String var2 = UUID.randomUUID().toString();
         this.listenerMap.put(var2, var1);
         this.trackCurrentWindow();
         return var2;
      }
   }

   public void stopCollection(String var1) {
      if (this.isAvailable) {
         if (var1 != null) {
            this.listenerMap.remove(var1);
         }

         WeakReference var2 = this.currentWindow;
         Window var3;
         if (var2 != null) {
            var3 = (Window)var2.get();
         } else {
            var3 = null;
         }

         if (var3 != null && this.listenerMap.isEmpty()) {
            this.stopTrackingWindow(var3);
         }
      }
   }

   public interface FrameMetricsCollectorListener {
      void onFrameMetricCollected(long var1, long var3, long var5, long var7, boolean var9, boolean var10, float var11);
   }

   public interface WindowFrameMetricsManager {
      void addOnFrameMetricsAvailableListener(Window var1, OnFrameMetricsAvailableListener var2, Handler var3);

      void removeOnFrameMetricsAvailableListener(Window var1, OnFrameMetricsAvailableListener var2);
   }
}
