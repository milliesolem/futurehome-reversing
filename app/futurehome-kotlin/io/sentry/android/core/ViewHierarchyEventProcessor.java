package io.sentry.android.core;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import io.sentry.Attachment;
import io.sentry.EventProcessor;
import io.sentry.Hint;
import io.sentry.ILogger;
import io.sentry.ISerializer;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import io.sentry.android.core.internal.gestures.ViewUtils;
import io.sentry.android.core.internal.util.AndroidCurrentDateProvider;
import io.sentry.android.core.internal.util.AndroidMainThreadChecker;
import io.sentry.android.core.internal.util.ClassUtil;
import io.sentry.android.core.internal.util.Debouncer;
import io.sentry.internal.viewhierarchy.ViewHierarchyExporter;
import io.sentry.protocol.SentryTransaction;
import io.sentry.protocol.ViewHierarchy;
import io.sentry.protocol.ViewHierarchyNode;
import io.sentry.util.HintUtils;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.JsonSerializationUtils;
import io.sentry.util.Objects;
import io.sentry.util.thread.IMainThreadChecker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ViewHierarchyEventProcessor implements EventProcessor {
   private static final long CAPTURE_TIMEOUT_MS = 1000L;
   private static final int DEBOUNCE_MAX_EXECUTIONS = 3;
   private static final long DEBOUNCE_WAIT_TIME_MS = 2000L;
   private final Debouncer debouncer;
   private final SentryAndroidOptions options;

   public ViewHierarchyEventProcessor(SentryAndroidOptions var1) {
      this.options = Objects.requireNonNull(var1, "SentryAndroidOptions is required");
      this.debouncer = new Debouncer(AndroidCurrentDateProvider.getInstance(), 2000L, 3);
      if (var1.isAttachViewHierarchy()) {
         IntegrationUtils.addIntegrationToSdkVersion("ViewHierarchy");
      }
   }

   private static void addChildren(View var0, ViewHierarchyNode var1, List<ViewHierarchyExporter> var2) {
      if (var0 instanceof ViewGroup) {
         Iterator var5 = var2.iterator();

         while (var5.hasNext()) {
            if (((ViewHierarchyExporter)var5.next()).export(var1, var0)) {
               return;
            }
         }

         ViewGroup var9 = (ViewGroup)var0;
         int var4 = var9.getChildCount();
         if (var4 != 0) {
            ArrayList var8 = new ArrayList(var4);

            for (int var3 = 0; var3 < var4; var3++) {
               View var7 = var9.getChildAt(var3);
               if (var7 != null) {
                  ViewHierarchyNode var6 = viewToNode(var7);
                  var8.add(var6);
                  addChildren(var7, var6, var2);
               }
            }

            var1.setChildren(var8);
         }
      }
   }

   public static ViewHierarchy snapshotViewHierarchy(Activity var0, ILogger var1) {
      return snapshotViewHierarchy(var0, new ArrayList<>(0), AndroidMainThreadChecker.getInstance(), var1);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static ViewHierarchy snapshotViewHierarchy(Activity var0, List<ViewHierarchyExporter> var1, IMainThreadChecker var2, ILogger var3) {
      if (var0 == null) {
         var3.log(SentryLevel.INFO, "Missing activity for view hierarchy snapshot.");
         return null;
      } else {
         Window var4 = var0.getWindow();
         if (var4 == null) {
            var3.log(SentryLevel.INFO, "Missing window for view hierarchy snapshot.");
            return null;
         } else {
            View var10 = var4.peekDecorView();
            if (var10 == null) {
               var3.log(SentryLevel.INFO, "Missing decor view for view hierarchy snapshot.");
               return null;
            } else {
               try {
                  if (var2.isMainThread()) {
                     return snapshotViewHierarchy(var10, var1);
                  }

                  CountDownLatch var9 = new CountDownLatch(1);
                  AtomicReference var5 = new AtomicReference(null);
                  ViewHierarchyEventProcessor$$ExternalSyntheticLambda0 var6 = new ViewHierarchyEventProcessor$$ExternalSyntheticLambda0(
                     var5, var10, var1, var9, var3
                  );
                  var0.runOnUiThread(var6);
                  if (var9.await(1000L, TimeUnit.MILLISECONDS)) {
                     return (ViewHierarchy)var5.get();
                  }
               } catch (Throwable var8) {
                  var3.log(SentryLevel.ERROR, "Failed to process view hierarchy.", var8);
                  return null;
               }

               return null;
            }
         }
      }
   }

   public static ViewHierarchy snapshotViewHierarchy(View var0) {
      return snapshotViewHierarchy(var0, new ArrayList<>(0));
   }

   public static ViewHierarchy snapshotViewHierarchy(View var0, List<ViewHierarchyExporter> var1) {
      ArrayList var3 = new ArrayList(1);
      ViewHierarchy var4 = new ViewHierarchy("android_view_system", var3);
      ViewHierarchyNode var2 = viewToNode(var0);
      var3.add(var2);
      addChildren(var0, var2, var1);
      return var4;
   }

   public static byte[] snapshotViewHierarchyAsData(Activity var0, IMainThreadChecker var1, ISerializer var2, ILogger var3) {
      ViewHierarchy var4 = snapshotViewHierarchy(var0, new ArrayList<>(0), var1, var3);
      if (var4 == null) {
         var3.log(SentryLevel.ERROR, "Could not get ViewHierarchy.");
         return null;
      } else {
         byte[] var5 = JsonSerializationUtils.bytesFrom(var2, var3, var4);
         if (var5 == null) {
            var3.log(SentryLevel.ERROR, "Could not serialize ViewHierarchy.");
            return null;
         } else if (var5.length < 1) {
            var3.log(SentryLevel.ERROR, "Got empty bytes array after serializing ViewHierarchy.");
            return null;
         } else {
            return var5;
         }
      }
   }

   private static ViewHierarchyNode viewToNode(View var0) {
      ViewHierarchyNode var2 = new ViewHierarchyNode();
      var2.setType(ClassUtil.getClassName(var0));

      label34:
      try {
         var2.setIdentifier(ViewUtils.getResourceId(var0));
      } finally {
         break label34;
      }

      var2.setX((double)var0.getX());
      var2.setY((double)var0.getY());
      var2.setWidth((double)var0.getWidth());
      var2.setHeight((double)var0.getHeight());
      var2.setAlpha((double)var0.getAlpha());
      int var1 = var0.getVisibility();
      if (var1 != 0) {
         if (var1 != 4) {
            if (var1 == 8) {
               var2.setVisibility("gone");
            }
         } else {
            var2.setVisibility("invisible");
         }
      } else {
         var2.setVisibility("visible");
      }

      return var2;
   }

   @Override
   public SentryEvent process(SentryEvent var1, Hint var2) {
      if (!var1.isErrored()) {
         return var1;
      } else if (!this.options.isAttachViewHierarchy()) {
         this.options.getLogger().log(SentryLevel.DEBUG, "attachViewHierarchy is disabled.");
         return var1;
      } else if (HintUtils.isFromHybridSdk(var2)) {
         return var1;
      } else {
         boolean var3 = this.debouncer.checkForDebounce();
         SentryAndroidOptions.BeforeCaptureCallback var4 = this.options.getBeforeViewHierarchyCaptureCallback();
         if (var4 != null) {
            if (!var4.execute(var1, var2, var3)) {
               return var1;
            }
         } else if (var3) {
            return var1;
         }

         ViewHierarchy var5 = snapshotViewHierarchy(
            CurrentActivityHolder.getInstance().getActivity(),
            this.options.getViewHierarchyExporters(),
            this.options.getMainThreadChecker(),
            this.options.getLogger()
         );
         if (var5 != null) {
            var2.setViewHierarchy(Attachment.fromViewHierarchy(var5));
         }

         return var1;
      }
   }

   @Override
   public SentryTransaction process(SentryTransaction var1, Hint var2) {
      return var1;
   }
}
