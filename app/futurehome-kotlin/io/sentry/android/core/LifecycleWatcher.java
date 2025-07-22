package io.sentry.android.core;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import io.sentry.Breadcrumb;
import io.sentry.IHub;
import io.sentry.SentryLevel;
import io.sentry.transport.CurrentDateProvider;
import io.sentry.transport.ICurrentDateProvider;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

final class LifecycleWatcher implements DefaultLifecycleObserver {
   private final ICurrentDateProvider currentDateProvider;
   private final boolean enableAppLifecycleBreadcrumbs;
   private final boolean enableSessionTracking;
   private final IHub hub;
   private final AtomicLong lastUpdatedSession = new AtomicLong(0L);
   private final long sessionIntervalMillis;
   private final Timer timer = new Timer(true);
   private final Object timerLock = new Object();
   private TimerTask timerTask;

   LifecycleWatcher(IHub var1, long var2, boolean var4, boolean var5) {
      this(var1, var2, var4, var5, CurrentDateProvider.getInstance());
   }

   LifecycleWatcher(IHub var1, long var2, boolean var4, boolean var5, ICurrentDateProvider var6) {
      this.sessionIntervalMillis = var2;
      this.enableSessionTracking = var4;
      this.enableAppLifecycleBreadcrumbs = var5;
      this.hub = var1;
      this.currentDateProvider = var6;
   }

   private void addAppBreadcrumb(String var1) {
      if (this.enableAppLifecycleBreadcrumbs) {
         Breadcrumb var2 = new Breadcrumb();
         var2.setType("navigation");
         var2.setData("state", var1);
         var2.setCategory("app.lifecycle");
         var2.setLevel(SentryLevel.INFO);
         this.hub.addBreadcrumb(var2);
      }
   }

   private void cancelTask() {
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
      // 01: getfield io/sentry/android/core/LifecycleWatcher.timerLock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/android/core/LifecycleWatcher.timerTask Ljava/util/TimerTask;
      // 0b: astore 2
      // 0c: aload 2
      // 0d: ifnull 1a
      // 10: aload 2
      // 11: invokevirtual java/util/TimerTask.cancel ()Z
      // 14: pop
      // 15: aload 0
      // 16: aconst_null
      // 17: putfield io/sentry/android/core/LifecycleWatcher.timerTask Ljava/util/TimerTask;
      // 1a: aload 1
      // 1b: monitorexit
      // 1c: return
      // 1d: astore 2
      // 1e: aload 1
      // 1f: monitorexit
      // 20: aload 2
      // 21: athrow
   }

   private void scheduleEndSession() {
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
      // 01: getfield io/sentry/android/core/LifecycleWatcher.timerLock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: invokespecial io/sentry/android/core/LifecycleWatcher.cancelTask ()V
      // 0b: aload 0
      // 0c: getfield io/sentry/android/core/LifecycleWatcher.timer Ljava/util/Timer;
      // 0f: ifnull 2c
      // 12: new io/sentry/android/core/LifecycleWatcher$1
      // 15: astore 2
      // 16: aload 2
      // 17: aload 0
      // 18: invokespecial io/sentry/android/core/LifecycleWatcher$1.<init> (Lio/sentry/android/core/LifecycleWatcher;)V
      // 1b: aload 0
      // 1c: aload 2
      // 1d: putfield io/sentry/android/core/LifecycleWatcher.timerTask Ljava/util/TimerTask;
      // 20: aload 0
      // 21: getfield io/sentry/android/core/LifecycleWatcher.timer Ljava/util/Timer;
      // 24: aload 2
      // 25: aload 0
      // 26: getfield io/sentry/android/core/LifecycleWatcher.sessionIntervalMillis J
      // 29: invokevirtual java/util/Timer.schedule (Ljava/util/TimerTask;J)V
      // 2c: aload 1
      // 2d: monitorexit
      // 2e: return
      // 2f: astore 2
      // 30: aload 1
      // 31: monitorexit
      // 32: aload 2
      // 33: athrow
   }

   private void startSession() {
      this.cancelTask();
      long var1 = this.currentDateProvider.getCurrentTimeMillis();
      this.hub.configureScope(new LifecycleWatcher$$ExternalSyntheticLambda0(this));
      long var3 = this.lastUpdatedSession.get();
      if (var3 == 0L || var3 + this.sessionIntervalMillis <= var1) {
         if (this.enableSessionTracking) {
            this.hub.startSession();
         }

         this.hub.getOptions().getReplayController().start();
      }

      this.hub.getOptions().getReplayController().resume();
      this.lastUpdatedSession.set(var1);
   }

   Timer getTimer() {
      return this.timer;
   }

   TimerTask getTimerTask() {
      return this.timerTask;
   }

   public void onStart(LifecycleOwner var1) {
      this.startSession();
      this.addAppBreadcrumb("foreground");
      AppState.getInstance().setInBackground(false);
   }

   public void onStop(LifecycleOwner var1) {
      long var2 = this.currentDateProvider.getCurrentTimeMillis();
      this.lastUpdatedSession.set(var2);
      this.hub.getOptions().getReplayController().pause();
      this.scheduleEndSession();
      AppState.getInstance().setInBackground(true);
      this.addAppBreadcrumb("background");
   }
}
