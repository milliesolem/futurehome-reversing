package io.sentry.android.core;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import io.sentry.Breadcrumb;
import io.sentry.Hint;
import io.sentry.IHub;
import io.sentry.Integration;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;

public final class ActivityBreadcrumbsIntegration implements Integration, Closeable, ActivityLifecycleCallbacks {
   private final Application application;
   private boolean enabled;
   private IHub hub;

   public ActivityBreadcrumbsIntegration(Application var1) {
      this.application = Objects.requireNonNull(var1, "Application is required");
   }

   private void addBreadcrumb(Activity var1, String var2) {
      if (this.hub != null) {
         Breadcrumb var3 = new Breadcrumb();
         var3.setType("navigation");
         var3.setData("state", var2);
         var3.setData("screen", this.getActivityName(var1));
         var3.setCategory("ui.lifecycle");
         var3.setLevel(SentryLevel.INFO);
         Hint var4 = new Hint();
         var4.set("android:activity", var1);
         this.hub.addBreadcrumb(var3, var4);
      }
   }

   private String getActivityName(Activity var1) {
      return var1.getClass().getSimpleName();
   }

   @Override
   public void close() throws IOException {
      if (this.enabled) {
         this.application.unregisterActivityLifecycleCallbacks(this);
         IHub var1 = this.hub;
         if (var1 != null) {
            var1.getOptions().getLogger().log(SentryLevel.DEBUG, "ActivityBreadcrumbsIntegration removed.");
         }
      }
   }

   public void onActivityCreated(Activity param1, Bundle param2) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: aload 1
      // 04: ldc "created"
      // 06: invokespecial io/sentry/android/core/ActivityBreadcrumbsIntegration.addBreadcrumb (Landroid/app/Activity;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: monitorexit
      // 0b: return
      // 0c: astore 1
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aload 1
      // 10: athrow
   }

   public void onActivityDestroyed(Activity param1) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: aload 1
      // 04: ldc "destroyed"
      // 06: invokespecial io/sentry/android/core/ActivityBreadcrumbsIntegration.addBreadcrumb (Landroid/app/Activity;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: monitorexit
      // 0b: return
      // 0c: astore 1
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aload 1
      // 10: athrow
   }

   public void onActivityPaused(Activity param1) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: aload 1
      // 04: ldc "paused"
      // 06: invokespecial io/sentry/android/core/ActivityBreadcrumbsIntegration.addBreadcrumb (Landroid/app/Activity;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: monitorexit
      // 0b: return
      // 0c: astore 1
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aload 1
      // 10: athrow
   }

   public void onActivityResumed(Activity param1) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: aload 1
      // 04: ldc "resumed"
      // 06: invokespecial io/sentry/android/core/ActivityBreadcrumbsIntegration.addBreadcrumb (Landroid/app/Activity;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: monitorexit
      // 0b: return
      // 0c: astore 1
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aload 1
      // 10: athrow
   }

   public void onActivitySaveInstanceState(Activity param1, Bundle param2) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: aload 1
      // 04: ldc "saveInstanceState"
      // 06: invokespecial io/sentry/android/core/ActivityBreadcrumbsIntegration.addBreadcrumb (Landroid/app/Activity;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: monitorexit
      // 0b: return
      // 0c: astore 1
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aload 1
      // 10: athrow
   }

   public void onActivityStarted(Activity param1) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: aload 1
      // 04: ldc "started"
      // 06: invokespecial io/sentry/android/core/ActivityBreadcrumbsIntegration.addBreadcrumb (Landroid/app/Activity;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: monitorexit
      // 0b: return
      // 0c: astore 1
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aload 1
      // 10: athrow
   }

   public void onActivityStopped(Activity param1) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: aload 1
      // 04: ldc "stopped"
      // 06: invokespecial io/sentry/android/core/ActivityBreadcrumbsIntegration.addBreadcrumb (Landroid/app/Activity;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: monitorexit
      // 0b: return
      // 0c: astore 1
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aload 1
      // 10: athrow
   }

   @Override
   public void register(IHub var1, SentryOptions var2) {
      SentryAndroidOptions var3;
      if (var2 instanceof SentryAndroidOptions) {
         var3 = (SentryAndroidOptions)var2;
      } else {
         var3 = null;
      }

      var3 = Objects.requireNonNull(var3, "SentryAndroidOptions is required");
      this.hub = Objects.requireNonNull(var1, "Hub is required");
      this.enabled = var3.isEnableActivityLifecycleBreadcrumbs();
      var2.getLogger().log(SentryLevel.DEBUG, "ActivityBreadcrumbsIntegration enabled: %s", this.enabled);
      if (this.enabled) {
         this.application.registerActivityLifecycleCallbacks(this);
         var2.getLogger().log(SentryLevel.DEBUG, "ActivityBreadcrumbIntegration installed.");
         IntegrationUtils.addIntegrationToSdkVersion("ActivityBreadcrumbs");
      }
   }
}
