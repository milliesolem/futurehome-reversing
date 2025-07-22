package io.sentry.android.replay

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build.VERSION
import android.view.MotionEvent
import io.sentry.Breadcrumb
import io.sentry.DataCategory
import io.sentry.Hint
import io.sentry.IConnectionStatusProvider
import io.sentry.IHub
import io.sentry.IScope
import io.sentry.ISentryExecutorService
import io.sentry.Integration
import io.sentry.NoOpReplayBreadcrumbConverter
import io.sentry.ReplayBreadcrumbConverter
import io.sentry.ReplayController
import io.sentry.SentryIntegrationPackageStorage
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.SentryReplayOptions
import io.sentry.IConnectionStatusProvider.ConnectionStatus
import io.sentry.android.replay.capture.CaptureStrategy
import io.sentry.android.replay.capture.SessionCaptureStrategy
import io.sentry.android.replay.gestures.GestureRecorder
import io.sentry.android.replay.gestures.TouchRecorderCallback
import io.sentry.android.replay.util.ContextKt
import io.sentry.android.replay.util.ExecutorsKt
import io.sentry.android.replay.util.MainLooperHandler
import io.sentry.cache.PersistingScopeObserver
import io.sentry.hints.Backfillable
import io.sentry.protocol.SentryId
import io.sentry.transport.ICurrentDateProvider
import io.sentry.transport.RateLimiter
import io.sentry.util.FileUtils
import io.sentry.util.HintUtils
import io.sentry.util.IntegrationUtils
import io.sentry.util.Random
import java.io.Closeable
import java.io.File
import java.util.LinkedList
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Intrinsics
import kotlin.jvm.internal.Ref

public class ReplayIntegration(context: Context,
      dateProvider: ICurrentDateProvider,
      recorderProvider: (() -> Recorder)? = null,
      recorderConfigProvider: ((Boolean) -> ScreenshotRecorderConfig)? = null,
      replayCacheProvider: ((SentryId) -> ReplayCache)? = null
   ) :
   Integration,
   Closeable,
   ScreenshotRecorderCallback,
   TouchRecorderCallback,
   ReplayController,
   ComponentCallbacks,
   IConnectionStatusProvider.IConnectionStatusObserver,
   RateLimiter.IRateLimitObserver {
   private final var captureStrategy: CaptureStrategy?
   private final val context: Context
   private final val dateProvider: ICurrentDateProvider
   private final var gestureRecorder: GestureRecorder?
   private final var gestureRecorderProvider: (() -> GestureRecorder)?
   private final var hub: IHub?
   internal final val isEnabled: AtomicBoolean
   internal final val isManualPause: AtomicBoolean
   private final val lifecycle: ReplayLifecycle
   private final var mainLooperHandler: MainLooperHandler
   private final lateinit var options: SentryOptions

   private final val random: Random
      private final get() {
         return this.random$delegate.getValue() as Random;
      }


   private final var recorder: Recorder?
   private final val recorderConfigProvider: ((Boolean) -> ScreenshotRecorderConfig)?
   private final val recorderProvider: (() -> Recorder)?
   private final var replayBreadcrumbConverter: ReplayBreadcrumbConverter

   public final val replayCacheDir: File?
      public final get() {
         val var2: File;
         if (this.captureStrategy != null) {
            var2 = this.captureStrategy.getReplayCacheDir();
         } else {
            var2 = null;
         }

         return var2;
      }


   private final val replayCacheProvider: ((SentryId) -> ReplayCache)?
   private final var replayCaptureStrategyProvider: ((Boolean) -> CaptureStrategy)?

   private final val replayExecutor: ScheduledExecutorService
      private final get() {
         return this.replayExecutor$delegate.getValue() as ScheduledExecutorService;
      }


   internal final val rootViewsSpy: RootViewsSpy
      internal final get() {
         return this.rootViewsSpy$delegate.getValue() as RootViewsSpy;
      }


   public constructor(context: Context, dateProvider: ICurrentDateProvider) : this(ContextKt.appContext(var1), var2, null, null, null)
   init {
      this.context = var1;
      this.dateProvider = var2;
      this.recorderProvider = var3;
      this.recorderConfigProvider = var4;
      this.replayCacheProvider = var5;
      this.random$delegate = LazyKt.lazy(<unrepresentable>.INSTANCE);
      this.rootViewsSpy$delegate = LazyKt.lazy(<unrepresentable>.INSTANCE);
      this.replayExecutor$delegate = LazyKt.lazy(<unrepresentable>.INSTANCE);
      this.isEnabled = new AtomicBoolean(false);
      this.isManualPause = new AtomicBoolean(false);
      val var6: NoOpReplayBreadcrumbConverter = NoOpReplayBreadcrumbConverter.getInstance();
      this.replayBreadcrumbConverter = var6;
      this.mainLooperHandler = new MainLooperHandler(null, 1, null);
      this.lifecycle = new ReplayLifecycle();
   }

   internal constructor(context: Context,
      dateProvider: ICurrentDateProvider,
      recorderProvider: (() -> Recorder)?,
      recorderConfigProvider: ((Boolean) -> ScreenshotRecorderConfig)?,
      replayCacheProvider: ((SentryId) -> ReplayCache)?,
      replayCaptureStrategyProvider: ((Boolean) -> CaptureStrategy)? = null,
      mainLooperHandler: MainLooperHandler? = null,
      gestureRecorderProvider: (() -> GestureRecorder)? = null
   ) : this(ContextKt.appContext(var1), var2, var3, var4, var5) {
      this.replayCaptureStrategyProvider = var6;
      var var9: MainLooperHandler = var7;
      if (var7 == null) {
         var9 = new MainLooperHandler(null, 1, null);
      }

      this.mainLooperHandler = var9;
      this.gestureRecorderProvider = var8;
   }

   private fun checkCanRecord() {
      if (this.captureStrategy is SessionCaptureStrategy) {
         var var1: SentryOptions = this.options;
         if (this.options == null) {
            Intrinsics.throwUninitializedPropertyAccessException("options");
            var1 = null;
         }

         label29:
         if (var1.getConnectionStatusProvider().getConnectionStatus() != IConnectionStatusProvider.ConnectionStatus.DISCONNECTED) {
            if (this.hub != null) {
               val var4: RateLimiter = this.hub.getRateLimiter();
               if (var4 != null && var4.isActiveForCategory(DataCategory.All)) {
                  break label29;
               }
            }

            if (this.hub == null) {
               return;
            }

            val var6: RateLimiter = this.hub.getRateLimiter();
            if (var6 == null || !var6.isActiveForCategory(DataCategory.Replay)) {
               return;
            }
         }

         this.pauseInternal();
      }
   }

   private fun cleanupReplays(unfinishedReplayId: String = "") {
      var var4: SentryOptions = this.options;
      if (this.options == null) {
         Intrinsics.throwUninitializedPropertyAccessException("options");
         var4 = null;
      }

      val var8: java.lang.String = var4.getCacheDirPath();
      if (var8 != null) {
         val var9: Array<File> = new File(var8).listFiles();
         if (var9 != null) {
            val var3: Int = var9.length;

            for (int var2 = 0; var2 < var3; var2++) {
               val var10: File = var9[var2];
               val var6: java.lang.String = var9[var2].getName();
               if (StringsKt.startsWith$default(var6, "replay_", false, 2, null)) {
                  val var11: java.lang.CharSequence = var6;
                  val var7: java.lang.String = this.getReplayId().toString();
                  if (!StringsKt.contains$default(var11, var7, false, 2, null)
                     && (StringsKt.isBlank(var1) || !StringsKt.contains$default(var11, var1, false, 2, null))) {
                     FileUtils.deleteRecursively(var10);
                  }
               }
            }
         }
      }
   }

   private fun finalizePreviousReplay() {
      var var1: SentryOptions = this.options;
      if (this.options == null) {
         Intrinsics.throwUninitializedPropertyAccessException("options");
         var1 = null;
      }

      val var5: ISentryExecutorService = var1.getExecutorService();
      var1 = this.options;
      if (this.options == null) {
         Intrinsics.throwUninitializedPropertyAccessException("options");
         var1 = null;
      }

      ExecutorsKt.submitSafely(var5, var1, "ReplayIntegration.finalize_previous_replay", new ReplayIntegration$$ExternalSyntheticLambda0(this));
   }

   @JvmStatic
   fun `finalizePreviousReplay$lambda$5`(var0: ReplayIntegration) {
      var var8: SentryOptions = var0.options;
      if (var0.options == null) {
         Intrinsics.throwUninitializedPropertyAccessException("options");
         var8 = null;
      }

      val var11: java.lang.String = PersistingScopeObserver.read(var8, "replay.json", java.lang.String.class);
      if (var11 == null) {
         cleanupReplays$default(var0, null, 1, null);
      } else {
         val var12: SentryId = new SentryId(var11);
         if (var12 == SentryId.EMPTY_ID) {
            cleanupReplays$default(var0, null, 1, null);
         } else {
            var8 = var0.options;
            if (var0.options == null) {
               Intrinsics.throwUninitializedPropertyAccessException("options");
               var8 = null;
            }

            val var13: LastSegmentData = ReplayCache.Companion.fromDisk$sentry_android_replay_release(var8, var12, var0.replayCacheProvider);
            if (var13 == null) {
               cleanupReplays$default(var0, null, 1, null);
            } else {
               var8 = var0.options;
               if (var0.options == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("options");
                  var8 = null;
               }

               var8 = PersistingScopeObserver.read(var8, "breadcrumbs.json", java.util.List.class, new Breadcrumb.Deserializer());
               val var20: java.util.List;
               if (var8 is java.util.List) {
                  var20 = var8 as java.util.List;
               } else {
                  var20 = null;
               }

               var var24: SentryOptions = var0.options;
               if (var0.options == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("options");
                  var24 = null;
               }

               val var25: CaptureStrategy.ReplaySegment = CaptureStrategy.Companion
                  .createSegment(
                     var0.hub,
                     var24,
                     var13.getDuration(),
                     var13.getTimestamp(),
                     var12,
                     var13.getId(),
                     var13.getRecorderConfig().getRecordingHeight(),
                     var13.getRecorderConfig().getRecordingWidth(),
                     var13.getReplayType(),
                     var13.getCache(),
                     var13.getRecorderConfig().getFrameRate(),
                     var13.getRecorderConfig().getBitRate(),
                     var13.getScreenAtStart(),
                     var20,
                     new LinkedList<>(var13.getEvents())
                  );
               if (var25 is CaptureStrategy.ReplaySegment.Created) {
                  val var21: Hint = HintUtils.createWithTypeCheckHint(new ReplayIntegration.PreviousReplayHint());
                  val var26: CaptureStrategy.ReplaySegment.Created = var25 as CaptureStrategy.ReplaySegment.Created;
                  val var29: IHub = var0.hub;
                  var26.capture(var29, var21);
               }

               var0.cleanupReplays(var11);
            }
         }
      }
   }

   @JvmStatic
   fun `onScreenshotRecorded$lambda$0`(var0: Ref.ObjectRef, var1: IScope) {
      val var2: java.lang.String = var1.getScreen();
      var var3: java.lang.String = null;
      if (var2 != null) {
         var3 = StringsKt.substringAfterLast$default(var2, '.', null, 2, null);
      }

      var0.element = (T)var3;
   }

   private fun pauseInternal() {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/sentry/android/replay/ReplayIntegration.isEnabled Ljava/util/concurrent/atomic/AtomicBoolean;
      // 06: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 09: ifeq 47
      // 0c: aload 0
      // 0d: getfield io/sentry/android/replay/ReplayIntegration.lifecycle Lio/sentry/android/replay/ReplayLifecycle;
      // 10: getstatic io/sentry/android/replay/ReplayState.PAUSED Lio/sentry/android/replay/ReplayState;
      // 13: invokevirtual io/sentry/android/replay/ReplayLifecycle.isAllowed (Lio/sentry/android/replay/ReplayState;)Z
      // 16: ifne 1c
      // 19: goto 47
      // 1c: aload 0
      // 1d: getfield io/sentry/android/replay/ReplayIntegration.recorder Lio/sentry/android/replay/Recorder;
      // 20: astore 1
      // 21: aload 1
      // 22: ifnull 2b
      // 25: aload 1
      // 26: invokeinterface io/sentry/android/replay/Recorder.pause ()V 1
      // 2b: aload 0
      // 2c: getfield io/sentry/android/replay/ReplayIntegration.captureStrategy Lio/sentry/android/replay/capture/CaptureStrategy;
      // 2f: astore 1
      // 30: aload 1
      // 31: ifnull 3a
      // 34: aload 1
      // 35: invokeinterface io/sentry/android/replay/capture/CaptureStrategy.pause ()V 1
      // 3a: aload 0
      // 3b: getfield io/sentry/android/replay/ReplayIntegration.lifecycle Lio/sentry/android/replay/ReplayLifecycle;
      // 3e: getstatic io/sentry/android/replay/ReplayState.PAUSED Lio/sentry/android/replay/ReplayState;
      // 41: invokevirtual io/sentry/android/replay/ReplayLifecycle.setCurrentState$sentry_android_replay_release (Lio/sentry/android/replay/ReplayState;)V
      // 44: aload 0
      // 45: monitorexit
      // 46: return
      // 47: aload 0
      // 48: monitorexit
      // 49: return
      // 4a: astore 1
      // 4b: aload 0
      // 4c: monitorexit
      // 4d: aload 1
      // 4e: athrow
   }

   private fun registerRootViewListeners() {
      if (this.recorder is OnRootViewsChangedListener) {
         val var2: java.util.Collection = this.getRootViewsSpy$sentry_android_replay_release().getListeners();
         val var1: Recorder = this.recorder;
         var2.add(var1 as OnRootViewsChangedListener);
      }

      this.getRootViewsSpy$sentry_android_replay_release().getListeners().add(this.gestureRecorder);
   }

   private fun resumeInternal() {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/sentry/android/replay/ReplayIntegration.isEnabled Ljava/util/concurrent/atomic/AtomicBoolean;
      // 06: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 09: ifeq ba
      // 0c: aload 0
      // 0d: getfield io/sentry/android/replay/ReplayIntegration.lifecycle Lio/sentry/android/replay/ReplayLifecycle;
      // 10: getstatic io/sentry/android/replay/ReplayState.RESUMED Lio/sentry/android/replay/ReplayState;
      // 13: invokevirtual io/sentry/android/replay/ReplayLifecycle.isAllowed (Lio/sentry/android/replay/ReplayState;)Z
      // 16: ifne 1c
      // 19: goto ba
      // 1c: aload 0
      // 1d: getfield io/sentry/android/replay/ReplayIntegration.isManualPause Ljava/util/concurrent/atomic/AtomicBoolean;
      // 20: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 23: ifne b7
      // 26: aload 0
      // 27: getfield io/sentry/android/replay/ReplayIntegration.options Lio/sentry/SentryOptions;
      // 2a: astore 2
      // 2b: aload 2
      // 2c: astore 1
      // 2d: aload 2
      // 2e: ifnonnull 39
      // 31: ldc_w "options"
      // 34: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // 37: aconst_null
      // 38: astore 1
      // 39: aload 1
      // 3a: invokevirtual io/sentry/SentryOptions.getConnectionStatusProvider ()Lio/sentry/IConnectionStatusProvider;
      // 3d: invokeinterface io/sentry/IConnectionStatusProvider.getConnectionStatus ()Lio/sentry/IConnectionStatusProvider$ConnectionStatus; 1
      // 42: getstatic io/sentry/IConnectionStatusProvider$ConnectionStatus.DISCONNECTED Lio/sentry/IConnectionStatusProvider$ConnectionStatus;
      // 45: if_acmpeq b7
      // 48: aload 0
      // 49: getfield io/sentry/android/replay/ReplayIntegration.hub Lio/sentry/IHub;
      // 4c: astore 1
      // 4d: aload 1
      // 4e: ifnull 6a
      // 51: aload 1
      // 52: invokeinterface io/sentry/IHub.getRateLimiter ()Lio/sentry/transport/RateLimiter; 1
      // 57: astore 1
      // 58: aload 1
      // 59: ifnull 6a
      // 5c: aload 1
      // 5d: getstatic io/sentry/DataCategory.All Lio/sentry/DataCategory;
      // 60: invokevirtual io/sentry/transport/RateLimiter.isActiveForCategory (Lio/sentry/DataCategory;)Z
      // 63: bipush 1
      // 64: if_icmpne 6a
      // 67: goto b7
      // 6a: aload 0
      // 6b: getfield io/sentry/android/replay/ReplayIntegration.hub Lio/sentry/IHub;
      // 6e: astore 1
      // 6f: aload 1
      // 70: ifnull 8c
      // 73: aload 1
      // 74: invokeinterface io/sentry/IHub.getRateLimiter ()Lio/sentry/transport/RateLimiter; 1
      // 79: astore 1
      // 7a: aload 1
      // 7b: ifnull 8c
      // 7e: aload 1
      // 7f: getstatic io/sentry/DataCategory.Replay Lio/sentry/DataCategory;
      // 82: invokevirtual io/sentry/transport/RateLimiter.isActiveForCategory (Lio/sentry/DataCategory;)Z
      // 85: bipush 1
      // 86: if_icmpne 8c
      // 89: goto b7
      // 8c: aload 0
      // 8d: getfield io/sentry/android/replay/ReplayIntegration.captureStrategy Lio/sentry/android/replay/capture/CaptureStrategy;
      // 90: astore 1
      // 91: aload 1
      // 92: ifnull 9b
      // 95: aload 1
      // 96: invokeinterface io/sentry/android/replay/capture/CaptureStrategy.resume ()V 1
      // 9b: aload 0
      // 9c: getfield io/sentry/android/replay/ReplayIntegration.recorder Lio/sentry/android/replay/Recorder;
      // 9f: astore 1
      // a0: aload 1
      // a1: ifnull aa
      // a4: aload 1
      // a5: invokeinterface io/sentry/android/replay/Recorder.resume ()V 1
      // aa: aload 0
      // ab: getfield io/sentry/android/replay/ReplayIntegration.lifecycle Lio/sentry/android/replay/ReplayLifecycle;
      // ae: getstatic io/sentry/android/replay/ReplayState.RESUMED Lio/sentry/android/replay/ReplayState;
      // b1: invokevirtual io/sentry/android/replay/ReplayLifecycle.setCurrentState$sentry_android_replay_release (Lio/sentry/android/replay/ReplayState;)V
      // b4: aload 0
      // b5: monitorexit
      // b6: return
      // b7: aload 0
      // b8: monitorexit
      // b9: return
      // ba: aload 0
      // bb: monitorexit
      // bc: return
      // bd: astore 1
      // be: aload 0
      // bf: monitorexit
      // c0: aload 1
      // c1: athrow
   }

   private fun unregisterRootViewListeners() {
      if (this.recorder is OnRootViewsChangedListener) {
         val var1: java.util.Collection = this.getRootViewsSpy$sentry_android_replay_release().getListeners();
         val var2: Recorder = this.recorder;
         var1.remove(var2 as OnRootViewsChangedListener);
      }

      this.getRootViewsSpy$sentry_android_replay_release().getListeners().remove(this.gestureRecorder);
   }

   public override fun captureReplay(isTerminating: Boolean?) {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/sentry/android/replay/ReplayIntegration.isEnabled Ljava/util/concurrent/atomic/AtomicBoolean;
      // 06: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 09: ifeq ab
      // 0c: aload 0
      // 0d: invokevirtual io/sentry/android/replay/ReplayIntegration.isRecording ()Z
      // 10: ifne 16
      // 13: goto ab
      // 16: getstatic io/sentry/protocol/SentryId.EMPTY_ID Lio/sentry/protocol/SentryId;
      // 19: astore 6
      // 1b: aload 0
      // 1c: getfield io/sentry/android/replay/ReplayIntegration.captureStrategy Lio/sentry/android/replay/capture/CaptureStrategy;
      // 1f: astore 3
      // 20: aconst_null
      // 21: astore 4
      // 23: aconst_null
      // 24: astore 5
      // 26: aload 3
      // 27: ifnull 34
      // 2a: aload 3
      // 2b: invokeinterface io/sentry/android/replay/capture/CaptureStrategy.getCurrentReplayId ()Lio/sentry/protocol/SentryId; 1
      // 30: astore 3
      // 31: goto 36
      // 34: aconst_null
      // 35: astore 3
      // 36: aload 6
      // 38: aload 3
      // 39: invokevirtual io/sentry/protocol/SentryId.equals (Ljava/lang/Object;)Z
      // 3c: ifeq 6a
      // 3f: aload 0
      // 40: getfield io/sentry/android/replay/ReplayIntegration.options Lio/sentry/SentryOptions;
      // 43: astore 1
      // 44: aload 1
      // 45: ifnonnull 54
      // 48: ldc_w "options"
      // 4b: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // 4e: aload 5
      // 50: astore 1
      // 51: goto 54
      // 54: aload 1
      // 55: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 58: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 5b: ldc_w "Replay id is not set, not capturing for event"
      // 5e: bipush 0
      // 5f: anewarray 4
      // 62: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 67: aload 0
      // 68: monitorexit
      // 69: return
      // 6a: aload 0
      // 6b: getfield io/sentry/android/replay/ReplayIntegration.captureStrategy Lio/sentry/android/replay/capture/CaptureStrategy;
      // 6e: astore 3
      // 6f: aload 3
      // 70: ifnull 90
      // 73: aload 1
      // 74: bipush 1
      // 75: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 78: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 7b: istore 2
      // 7c: new io/sentry/android/replay/ReplayIntegration$captureReplay$1
      // 7f: astore 1
      // 80: aload 1
      // 81: aload 0
      // 82: invokespecial io/sentry/android/replay/ReplayIntegration$captureReplay$1.<init> (Lio/sentry/android/replay/ReplayIntegration;)V
      // 85: aload 3
      // 86: iload 2
      // 87: aload 1
      // 88: checkcast kotlin/jvm/functions/Function1
      // 8b: invokeinterface io/sentry/android/replay/capture/CaptureStrategy.captureReplay (ZLkotlin/jvm/functions/Function1;)V 3
      // 90: aload 0
      // 91: getfield io/sentry/android/replay/ReplayIntegration.captureStrategy Lio/sentry/android/replay/capture/CaptureStrategy;
      // 94: astore 3
      // 95: aload 4
      // 97: astore 1
      // 98: aload 3
      // 99: ifnull a3
      // 9c: aload 3
      // 9d: invokeinterface io/sentry/android/replay/capture/CaptureStrategy.convert ()Lio/sentry/android/replay/capture/CaptureStrategy; 1
      // a2: astore 1
      // a3: aload 0
      // a4: aload 1
      // a5: putfield io/sentry/android/replay/ReplayIntegration.captureStrategy Lio/sentry/android/replay/capture/CaptureStrategy;
      // a8: aload 0
      // a9: monitorexit
      // aa: return
      // ab: aload 0
      // ac: monitorexit
      // ad: return
      // ae: astore 1
      // af: aload 0
      // b0: monitorexit
      // b1: aload 1
      // b2: athrow
   }

   public override fun close() {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/sentry/android/replay/ReplayIntegration.isEnabled Ljava/util/concurrent/atomic/AtomicBoolean;
      // 06: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 09: ifeq e2
      // 0c: aload 0
      // 0d: getfield io/sentry/android/replay/ReplayIntegration.lifecycle Lio/sentry/android/replay/ReplayLifecycle;
      // 10: getstatic io/sentry/android/replay/ReplayState.CLOSED Lio/sentry/android/replay/ReplayState;
      // 13: invokevirtual io/sentry/android/replay/ReplayLifecycle.isAllowed (Lio/sentry/android/replay/ReplayState;)Z
      // 16: ifne 1c
      // 19: goto e2
      // 1c: aload 0
      // 1d: getfield io/sentry/android/replay/ReplayIntegration.options Lio/sentry/SentryOptions;
      // 20: astore 4
      // 22: aconst_null
      // 23: astore 3
      // 24: aload 4
      // 26: astore 2
      // 27: aload 4
      // 29: ifnonnull 34
      // 2c: ldc_w "options"
      // 2f: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // 32: aconst_null
      // 33: astore 2
      // 34: aload 2
      // 35: invokevirtual io/sentry/SentryOptions.getConnectionStatusProvider ()Lio/sentry/IConnectionStatusProvider;
      // 38: aload 0
      // 39: checkcast io/sentry/IConnectionStatusProvider$IConnectionStatusObserver
      // 3c: invokeinterface io/sentry/IConnectionStatusProvider.removeConnectionStatusObserver (Lio/sentry/IConnectionStatusProvider$IConnectionStatusObserver;)V 2
      // 41: aload 0
      // 42: getfield io/sentry/android/replay/ReplayIntegration.hub Lio/sentry/IHub;
      // 45: astore 2
      // 46: aload 2
      // 47: ifnull 5d
      // 4a: aload 2
      // 4b: invokeinterface io/sentry/IHub.getRateLimiter ()Lio/sentry/transport/RateLimiter; 1
      // 50: astore 2
      // 51: aload 2
      // 52: ifnull 5d
      // 55: aload 2
      // 56: aload 0
      // 57: checkcast io/sentry/transport/RateLimiter$IRateLimitObserver
      // 5a: invokevirtual io/sentry/transport/RateLimiter.removeRateLimitObserver (Lio/sentry/transport/RateLimiter$IRateLimitObserver;)V
      // 5d: aload 0
      // 5e: getfield io/sentry/android/replay/ReplayIntegration.options Lio/sentry/SentryOptions;
      // 61: astore 4
      // 63: aload 4
      // 65: astore 2
      // 66: aload 4
      // 68: ifnonnull 73
      // 6b: ldc_w "options"
      // 6e: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // 71: aconst_null
      // 72: astore 2
      // 73: aload 2
      // 74: invokevirtual io/sentry/SentryOptions.getSessionReplay ()Lio/sentry/SentryReplayOptions;
      // 77: invokevirtual io/sentry/SentryReplayOptions.isTrackOrientationChange ()Z
      // 7a: istore 1
      // 7b: iload 1
      // 7c: ifeq 8a
      // 7f: aload 0
      // 80: getfield io/sentry/android/replay/ReplayIntegration.context Landroid/content/Context;
      // 83: aload 0
      // 84: checkcast android/content/ComponentCallbacks
      // 87: invokevirtual android/content/Context.unregisterComponentCallbacks (Landroid/content/ComponentCallbacks;)V
      // 8a: aload 0
      // 8b: invokevirtual io/sentry/android/replay/ReplayIntegration.stop ()V
      // 8e: aload 0
      // 8f: getfield io/sentry/android/replay/ReplayIntegration.recorder Lio/sentry/android/replay/Recorder;
      // 92: astore 2
      // 93: aload 2
      // 94: ifnull 9d
      // 97: aload 2
      // 98: invokeinterface io/sentry/android/replay/Recorder.close ()V 1
      // 9d: aload 0
      // 9e: aconst_null
      // 9f: putfield io/sentry/android/replay/ReplayIntegration.recorder Lio/sentry/android/replay/Recorder;
      // a2: aload 0
      // a3: invokevirtual io/sentry/android/replay/ReplayIntegration.getRootViewsSpy$sentry_android_replay_release ()Lio/sentry/android/replay/RootViewsSpy;
      // a6: invokevirtual io/sentry/android/replay/RootViewsSpy.close ()V
      // a9: aload 0
      // aa: invokespecial io/sentry/android/replay/ReplayIntegration.getReplayExecutor ()Ljava/util/concurrent/ScheduledExecutorService;
      // ad: astore 2
      // ae: aload 2
      // af: ldc_w "replayExecutor"
      // b2: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // b5: aload 2
      // b6: checkcast java/util/concurrent/ExecutorService
      // b9: astore 4
      // bb: aload 0
      // bc: getfield io/sentry/android/replay/ReplayIntegration.options Lio/sentry/SentryOptions;
      // bf: astore 2
      // c0: aload 2
      // c1: ifnonnull cf
      // c4: ldc_w "options"
      // c7: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // ca: aload 3
      // cb: astore 2
      // cc: goto cf
      // cf: aload 4
      // d1: aload 2
      // d2: invokestatic io/sentry/android/replay/util/ExecutorsKt.gracefullyShutdown (Ljava/util/concurrent/ExecutorService;Lio/sentry/SentryOptions;)V
      // d5: aload 0
      // d6: getfield io/sentry/android/replay/ReplayIntegration.lifecycle Lio/sentry/android/replay/ReplayLifecycle;
      // d9: getstatic io/sentry/android/replay/ReplayState.CLOSED Lio/sentry/android/replay/ReplayState;
      // dc: invokevirtual io/sentry/android/replay/ReplayLifecycle.setCurrentState$sentry_android_replay_release (Lio/sentry/android/replay/ReplayState;)V
      // df: aload 0
      // e0: monitorexit
      // e1: return
      // e2: aload 0
      // e3: monitorexit
      // e4: return
      // e5: astore 2
      // e6: aload 0
      // e7: monitorexit
      // e8: aload 2
      // e9: athrow
      // ea: astore 2
      // eb: goto 8a
   }

   public override fun getBreadcrumbConverter(): ReplayBreadcrumbConverter {
      return this.replayBreadcrumbConverter;
   }

   public override fun getReplayId(): SentryId {
      if (this.captureStrategy != null) {
         val var2: SentryId = this.captureStrategy.getCurrentReplayId();
         if (var2 != null) {
            return var2;
         }
      }

      val var3: SentryId = SentryId.EMPTY_ID;
      return var3;
   }

   public override fun isRecording(): Boolean {
      val var1: Boolean;
      if (this.lifecycle.getCurrentState$sentry_android_replay_release().compareTo(ReplayState.STARTED) >= 0
         && this.lifecycle.getCurrentState$sentry_android_replay_release().compareTo(ReplayState.STOPPED) < 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public open fun onConfigurationChanged(newConfig: Configuration) {
      if (this.isEnabled.get() && this.isRecording()) {
         if (this.recorder != null) {
            this.recorder.stop();
         }

         label41: {
            if (this.recorderConfigProvider != null) {
               val var2: ScreenshotRecorderConfig = this.recorderConfigProvider.invoke(true);
               var7 = var2;
               if (var2 != null) {
                  break label41;
               }
            }

            val var4: ScreenshotRecorderConfig.Companion = ScreenshotRecorderConfig.Companion;
            val var3: Context = this.context;
            var var8: SentryOptions = this.options;
            if (this.options == null) {
               Intrinsics.throwUninitializedPropertyAccessException("options");
               var8 = null;
            }

            val var9: SentryReplayOptions = var8.getSessionReplay();
            var7 = var4.from(var3, var9);
         }

         if (this.captureStrategy != null) {
            this.captureStrategy.onConfigurationChanged(var7);
         }

         if (this.recorder != null) {
            this.recorder.start(var7);
         }

         if (this.lifecycle.getCurrentState$sentry_android_replay_release() === ReplayState.PAUSED && this.recorder != null) {
            this.recorder.pause();
         }
      }
   }

   public override fun onConnectionStatusChanged(status: ConnectionStatus) {
      if (this.captureStrategy is SessionCaptureStrategy) {
         if (var1 === IConnectionStatusProvider.ConnectionStatus.DISCONNECTED) {
            this.pauseInternal();
         } else {
            this.resumeInternal();
         }
      }
   }

   public open fun onLowMemory() {
   }

   public override fun onRateLimitChanged(rateLimiter: RateLimiter) {
      if (this.captureStrategy is SessionCaptureStrategy) {
         if (!var1.isActiveForCategory(DataCategory.All) && !var1.isActiveForCategory(DataCategory.Replay)) {
            this.resumeInternal();
         } else {
            this.pauseInternal();
         }
      }
   }

   public override fun onScreenshotRecorded(bitmap: Bitmap) {
      val var2: Ref.ObjectRef = new Ref.ObjectRef();
      if (this.hub != null) {
         this.hub.configureScope(new ReplayIntegration$$ExternalSyntheticLambda1(var2));
      }

      if (this.captureStrategy != null) {
         this.captureStrategy.onScreenshotRecorded(var1, (new Function2<ReplayCache, java.lang.Long, Unit>(var1, var2, this) {
            final Bitmap $bitmap;
            final Ref.ObjectRef<java.lang.String> $screen;
            final ReplayIntegration this$0;

            {
               super(2);
               this.$bitmap = var1;
               this.$screen = var2;
               this.this$0 = var3;
            }

            public final void invoke(ReplayCache var1, long var2) {
               var1.addFrame$sentry_android_replay_release(this.$bitmap, var2, this.$screen.element);
               ReplayIntegration.access$checkCanRecord(this.this$0);
            }
         }) as (ReplayCache?, java.lang.Long?) -> Unit);
      }
   }

   public override fun onScreenshotRecorded(screenshot: File, frameTimestamp: Long) {
      if (this.captureStrategy != null) {
         CaptureStrategy.DefaultImpls.onScreenshotRecorded$default(
            this.captureStrategy, null, (new Function2<ReplayCache, java.lang.Long, Unit>(var1, var2, this) {
               final long $frameTimestamp;
               final File $screenshot;
               final ReplayIntegration this$0;

               {
                  super(2);
                  this.$screenshot = var1;
                  this.$frameTimestamp = var2;
                  this.this$0 = var4;
               }

               public final void invoke(ReplayCache var1, long var2) {
                  ReplayCache.addFrame$default(var1, this.$screenshot, this.$frameTimestamp, null, 4, null);
                  ReplayIntegration.access$checkCanRecord(this.this$0);
               }
            }) as Function2, 1, null
         );
      }
   }

   public override fun onTouchEvent(event: MotionEvent) {
      if (this.isEnabled.get() && this.lifecycle.isTouchRecordingAllowed() && this.captureStrategy != null) {
         this.captureStrategy.onTouchEvent(var1);
      }
   }

   public override fun pause() {
      this.isManualPause.set(true);
      this.pauseInternal();
   }

   public override fun register(hub: IHub, options: SentryOptions) {
      this.options = var2;
      if (VERSION.SDK_INT < 26) {
         var2.getLogger().log(SentryLevel.INFO, "Session replay is only supported on API 26 and above");
      } else if (!var2.getSessionReplay().isSessionReplayEnabled() && !var2.getSessionReplay().isSessionReplayForErrorsEnabled()) {
         var2.getLogger().log(SentryLevel.INFO, "Session replay is disabled, no sample rate specified");
      } else {
         var var9: Recorder;
         label40: {
            this.hub = var1;
            if (this.recorderProvider != null) {
               val var4: Recorder = this.recorderProvider.invoke();
               var9 = var4;
               if (var4 != null) {
                  break label40;
               }
            }

            val var5: ScreenshotRecorderCallback = this;
            val var13: MainLooperHandler = this.mainLooperHandler;
            val var10: ScheduledExecutorService = this.getReplayExecutor();
            var9 = new WindowRecorder(var2, var5, var13, var10);
         }

         label35: {
            this.recorder = var9;
            if (this.gestureRecorderProvider != null) {
               val var14: GestureRecorder = this.gestureRecorderProvider.invoke();
               var12 = var14;
               if (var14 != null) {
                  break label35;
               }
            }

            var12 = new GestureRecorder(var2, this);
         }

         this.gestureRecorder = var12;
         this.isEnabled.set(true);
         var2.getConnectionStatusProvider().addConnectionStatusObserver(this);
         val var8: RateLimiter = var1.getRateLimiter();
         if (var8 != null) {
            var8.addRateLimitObserver(this);
         }

         if (var2.getSessionReplay().isTrackOrientationChange()) {
            label28:
            try {
               this.context.registerComponentCallbacks(this);
            } catch (var6: java.lang.Throwable) {
               var2.getLogger().log(SentryLevel.INFO, "ComponentCallbacks is not available, orientation changes won't be handled by Session replay", var6);
               break label28;
            }
         }

         IntegrationUtils.addIntegrationToSdkVersion("Replay");
         SentryIntegrationPackageStorage.getInstance().addPackage("maven:io.sentry:sentry-android-replay", "7.22.1");
         this.finalizePreviousReplay();
      }
   }

   public override fun resume() {
      this.isManualPause.set(false);
      this.resumeInternal();
   }

   public override fun setBreadcrumbConverter(converter: ReplayBreadcrumbConverter) {
      this.replayBreadcrumbConverter = var1;
   }

   public override fun start() {
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
      //
      // Bytecode:
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 0
      // 003: getfield io/sentry/android/replay/ReplayIntegration.isEnabled Ljava/util/concurrent/atomic/AtomicBoolean;
      // 006: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 009: istore 1
      // 00a: iload 1
      // 00b: ifne 011
      // 00e: aload 0
      // 00f: monitorexit
      // 010: return
      // 011: aload 0
      // 012: getfield io/sentry/android/replay/ReplayIntegration.lifecycle Lio/sentry/android/replay/ReplayLifecycle;
      // 015: getstatic io/sentry/android/replay/ReplayState.STARTED Lio/sentry/android/replay/ReplayState;
      // 018: invokevirtual io/sentry/android/replay/ReplayLifecycle.isAllowed (Lio/sentry/android/replay/ReplayState;)Z
      // 01b: istore 1
      // 01c: aconst_null
      // 01d: astore 3
      // 01e: aconst_null
      // 01f: astore 2
      // 020: iload 1
      // 021: ifne 04e
      // 024: aload 0
      // 025: getfield io/sentry/android/replay/ReplayIntegration.options Lio/sentry/SentryOptions;
      // 028: astore 3
      // 029: aload 3
      // 02a: ifnonnull 036
      // 02d: ldc_w "options"
      // 030: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // 033: goto 038
      // 036: aload 3
      // 037: astore 2
      // 038: aload 2
      // 039: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 03c: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 03f: ldc_w "Session replay is already being recorded, not starting a new one"
      // 042: bipush 0
      // 043: anewarray 4
      // 046: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 04b: aload 0
      // 04c: monitorexit
      // 04d: return
      // 04e: aload 0
      // 04f: invokespecial io/sentry/android/replay/ReplayIntegration.getRandom ()Lio/sentry/util/Random;
      // 052: astore 5
      // 054: aload 0
      // 055: getfield io/sentry/android/replay/ReplayIntegration.options Lio/sentry/SentryOptions;
      // 058: astore 4
      // 05a: aload 4
      // 05c: astore 2
      // 05d: aload 4
      // 05f: ifnonnull 06a
      // 062: ldc_w "options"
      // 065: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // 068: aconst_null
      // 069: astore 2
      // 06a: aload 5
      // 06c: aload 2
      // 06d: invokevirtual io/sentry/SentryOptions.getSessionReplay ()Lio/sentry/SentryReplayOptions;
      // 070: invokevirtual io/sentry/SentryReplayOptions.getSessionSampleRate ()Ljava/lang/Double;
      // 073: invokestatic io/sentry/android/replay/util/SamplingKt.sample (Lio/sentry/util/Random;Ljava/lang/Double;)Z
      // 076: istore 1
      // 077: iload 1
      // 078: ifne 0c5
      // 07b: aload 0
      // 07c: getfield io/sentry/android/replay/ReplayIntegration.options Lio/sentry/SentryOptions;
      // 07f: astore 4
      // 081: aload 4
      // 083: astore 2
      // 084: aload 4
      // 086: ifnonnull 091
      // 089: ldc_w "options"
      // 08c: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // 08f: aconst_null
      // 090: astore 2
      // 091: aload 2
      // 092: invokevirtual io/sentry/SentryOptions.getSessionReplay ()Lio/sentry/SentryReplayOptions;
      // 095: invokevirtual io/sentry/SentryReplayOptions.isSessionReplayForErrorsEnabled ()Z
      // 098: ifne 0c5
      // 09b: aload 0
      // 09c: getfield io/sentry/android/replay/ReplayIntegration.options Lio/sentry/SentryOptions;
      // 09f: astore 2
      // 0a0: aload 2
      // 0a1: ifnonnull 0af
      // 0a4: ldc_w "options"
      // 0a7: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // 0aa: aload 3
      // 0ab: astore 2
      // 0ac: goto 0af
      // 0af: aload 2
      // 0b0: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 0b3: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 0b6: ldc_w "Session replay is not started, full session was not sampled and onErrorSampleRate is not specified"
      // 0b9: bipush 0
      // 0ba: anewarray 4
      // 0bd: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 0c2: aload 0
      // 0c3: monitorexit
      // 0c4: return
      // 0c5: aload 0
      // 0c6: getfield io/sentry/android/replay/ReplayIntegration.recorderConfigProvider Lkotlin/jvm/functions/Function1;
      // 0c9: astore 2
      // 0ca: aload 2
      // 0cb: ifnull 0e2
      // 0ce: aload 2
      // 0cf: bipush 0
      // 0d0: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0d3: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0d8: checkcast io/sentry/android/replay/ScreenshotRecorderConfig
      // 0db: astore 3
      // 0dc: aload 3
      // 0dd: astore 2
      // 0de: aload 3
      // 0df: ifnonnull 115
      // 0e2: getstatic io/sentry/android/replay/ScreenshotRecorderConfig.Companion Lio/sentry/android/replay/ScreenshotRecorderConfig$Companion;
      // 0e5: astore 4
      // 0e7: aload 0
      // 0e8: getfield io/sentry/android/replay/ReplayIntegration.context Landroid/content/Context;
      // 0eb: astore 5
      // 0ed: aload 0
      // 0ee: getfield io/sentry/android/replay/ReplayIntegration.options Lio/sentry/SentryOptions;
      // 0f1: astore 3
      // 0f2: aload 3
      // 0f3: astore 2
      // 0f4: aload 3
      // 0f5: ifnonnull 100
      // 0f8: ldc_w "options"
      // 0fb: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // 0fe: aconst_null
      // 0ff: astore 2
      // 100: aload 2
      // 101: invokevirtual io/sentry/SentryOptions.getSessionReplay ()Lio/sentry/SentryReplayOptions;
      // 104: astore 2
      // 105: aload 2
      // 106: ldc_w "options.sessionReplay"
      // 109: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 10c: aload 4
      // 10e: aload 5
      // 110: aload 2
      // 111: invokevirtual io/sentry/android/replay/ScreenshotRecorderConfig$Companion.from (Landroid/content/Context;Lio/sentry/SentryReplayOptions;)Lio/sentry/android/replay/ScreenshotRecorderConfig;
      // 114: astore 2
      // 115: aload 0
      // 116: getfield io/sentry/android/replay/ReplayIntegration.replayCaptureStrategyProvider Lkotlin/jvm/functions/Function1;
      // 119: astore 3
      // 11a: aload 3
      // 11b: ifnull 135
      // 11e: aload 3
      // 11f: iload 1
      // 120: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 123: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 128: checkcast io/sentry/android/replay/capture/CaptureStrategy
      // 12b: astore 4
      // 12d: aload 4
      // 12f: astore 3
      // 130: aload 4
      // 132: ifnonnull 1df
      // 135: iload 1
      // 136: ifeq 187
      // 139: new io/sentry/android/replay/capture/SessionCaptureStrategy
      // 13c: astore 5
      // 13e: aload 0
      // 13f: getfield io/sentry/android/replay/ReplayIntegration.options Lio/sentry/SentryOptions;
      // 142: astore 4
      // 144: aload 4
      // 146: astore 3
      // 147: aload 4
      // 149: ifnonnull 154
      // 14c: ldc_w "options"
      // 14f: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // 152: aconst_null
      // 153: astore 3
      // 154: aload 0
      // 155: getfield io/sentry/android/replay/ReplayIntegration.hub Lio/sentry/IHub;
      // 158: astore 6
      // 15a: aload 0
      // 15b: getfield io/sentry/android/replay/ReplayIntegration.dateProvider Lio/sentry/transport/ICurrentDateProvider;
      // 15e: astore 7
      // 160: aload 0
      // 161: invokespecial io/sentry/android/replay/ReplayIntegration.getReplayExecutor ()Ljava/util/concurrent/ScheduledExecutorService;
      // 164: astore 4
      // 166: aload 4
      // 168: ldc_w "replayExecutor"
      // 16b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 16e: aload 5
      // 170: aload 3
      // 171: aload 6
      // 173: aload 7
      // 175: aload 4
      // 177: aload 0
      // 178: getfield io/sentry/android/replay/ReplayIntegration.replayCacheProvider Lkotlin/jvm/functions/Function1;
      // 17b: invokespecial io/sentry/android/replay/capture/SessionCaptureStrategy.<init> (Lio/sentry/SentryOptions;Lio/sentry/IHub;Lio/sentry/transport/ICurrentDateProvider;Ljava/util/concurrent/ScheduledExecutorService;Lkotlin/jvm/functions/Function1;)V
      // 17e: aload 5
      // 180: checkcast io/sentry/android/replay/capture/BaseCaptureStrategy
      // 183: astore 3
      // 184: goto 1da
      // 187: new io/sentry/android/replay/capture/BufferCaptureStrategy
      // 18a: astore 5
      // 18c: aload 0
      // 18d: getfield io/sentry/android/replay/ReplayIntegration.options Lio/sentry/SentryOptions;
      // 190: astore 4
      // 192: aload 4
      // 194: astore 3
      // 195: aload 4
      // 197: ifnonnull 1a2
      // 19a: ldc_w "options"
      // 19d: invokestatic kotlin/jvm/internal/Intrinsics.throwUninitializedPropertyAccessException (Ljava/lang/String;)V
      // 1a0: aconst_null
      // 1a1: astore 3
      // 1a2: aload 0
      // 1a3: getfield io/sentry/android/replay/ReplayIntegration.hub Lio/sentry/IHub;
      // 1a6: astore 6
      // 1a8: aload 0
      // 1a9: getfield io/sentry/android/replay/ReplayIntegration.dateProvider Lio/sentry/transport/ICurrentDateProvider;
      // 1ac: astore 7
      // 1ae: aload 0
      // 1af: invokespecial io/sentry/android/replay/ReplayIntegration.getRandom ()Lio/sentry/util/Random;
      // 1b2: astore 4
      // 1b4: aload 0
      // 1b5: invokespecial io/sentry/android/replay/ReplayIntegration.getReplayExecutor ()Ljava/util/concurrent/ScheduledExecutorService;
      // 1b8: astore 8
      // 1ba: aload 8
      // 1bc: ldc_w "replayExecutor"
      // 1bf: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 1c2: aload 5
      // 1c4: aload 3
      // 1c5: aload 6
      // 1c7: aload 7
      // 1c9: aload 4
      // 1cb: aload 8
      // 1cd: aload 0
      // 1ce: getfield io/sentry/android/replay/ReplayIntegration.replayCacheProvider Lkotlin/jvm/functions/Function1;
      // 1d1: invokespecial io/sentry/android/replay/capture/BufferCaptureStrategy.<init> (Lio/sentry/SentryOptions;Lio/sentry/IHub;Lio/sentry/transport/ICurrentDateProvider;Lio/sentry/util/Random;Ljava/util/concurrent/ScheduledExecutorService;Lkotlin/jvm/functions/Function1;)V
      // 1d4: aload 5
      // 1d6: checkcast io/sentry/android/replay/capture/BaseCaptureStrategy
      // 1d9: astore 3
      // 1da: aload 3
      // 1db: checkcast io/sentry/android/replay/capture/CaptureStrategy
      // 1de: astore 3
      // 1df: aload 0
      // 1e0: aload 3
      // 1e1: putfield io/sentry/android/replay/ReplayIntegration.captureStrategy Lio/sentry/android/replay/capture/CaptureStrategy;
      // 1e4: aload 3
      // 1e5: ifnull 1f3
      // 1e8: aload 3
      // 1e9: aload 2
      // 1ea: bipush 0
      // 1eb: aconst_null
      // 1ec: aconst_null
      // 1ed: bipush 14
      // 1ef: aconst_null
      // 1f0: invokestatic io/sentry/android/replay/capture/CaptureStrategy$DefaultImpls.start$default (Lio/sentry/android/replay/capture/CaptureStrategy;Lio/sentry/android/replay/ScreenshotRecorderConfig;ILio/sentry/protocol/SentryId;Lio/sentry/SentryReplayEvent$ReplayType;ILjava/lang/Object;)V
      // 1f3: aload 0
      // 1f4: getfield io/sentry/android/replay/ReplayIntegration.recorder Lio/sentry/android/replay/Recorder;
      // 1f7: astore 3
      // 1f8: aload 3
      // 1f9: ifnull 203
      // 1fc: aload 3
      // 1fd: aload 2
      // 1fe: invokeinterface io/sentry/android/replay/Recorder.start (Lio/sentry/android/replay/ScreenshotRecorderConfig;)V 2
      // 203: aload 0
      // 204: invokespecial io/sentry/android/replay/ReplayIntegration.registerRootViewListeners ()V
      // 207: aload 0
      // 208: getfield io/sentry/android/replay/ReplayIntegration.lifecycle Lio/sentry/android/replay/ReplayLifecycle;
      // 20b: getstatic io/sentry/android/replay/ReplayState.STARTED Lio/sentry/android/replay/ReplayState;
      // 20e: invokevirtual io/sentry/android/replay/ReplayLifecycle.setCurrentState$sentry_android_replay_release (Lio/sentry/android/replay/ReplayState;)V
      // 211: aload 0
      // 212: monitorexit
      // 213: return
      // 214: astore 2
      // 215: aload 0
      // 216: monitorexit
      // 217: aload 2
      // 218: athrow
   }

   public override fun stop() {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/sentry/android/replay/ReplayIntegration.isEnabled Ljava/util/concurrent/atomic/AtomicBoolean;
      // 06: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 09: ifeq 5d
      // 0c: aload 0
      // 0d: getfield io/sentry/android/replay/ReplayIntegration.lifecycle Lio/sentry/android/replay/ReplayLifecycle;
      // 10: getstatic io/sentry/android/replay/ReplayState.STOPPED Lio/sentry/android/replay/ReplayState;
      // 13: invokevirtual io/sentry/android/replay/ReplayLifecycle.isAllowed (Lio/sentry/android/replay/ReplayState;)Z
      // 16: ifne 1c
      // 19: goto 5d
      // 1c: aload 0
      // 1d: invokespecial io/sentry/android/replay/ReplayIntegration.unregisterRootViewListeners ()V
      // 20: aload 0
      // 21: getfield io/sentry/android/replay/ReplayIntegration.recorder Lio/sentry/android/replay/Recorder;
      // 24: astore 1
      // 25: aload 1
      // 26: ifnull 2f
      // 29: aload 1
      // 2a: invokeinterface io/sentry/android/replay/Recorder.stop ()V 1
      // 2f: aload 0
      // 30: getfield io/sentry/android/replay/ReplayIntegration.gestureRecorder Lio/sentry/android/replay/gestures/GestureRecorder;
      // 33: astore 1
      // 34: aload 1
      // 35: ifnull 3c
      // 38: aload 1
      // 39: invokevirtual io/sentry/android/replay/gestures/GestureRecorder.stop ()V
      // 3c: aload 0
      // 3d: getfield io/sentry/android/replay/ReplayIntegration.captureStrategy Lio/sentry/android/replay/capture/CaptureStrategy;
      // 40: astore 1
      // 41: aload 1
      // 42: ifnull 4b
      // 45: aload 1
      // 46: invokeinterface io/sentry/android/replay/capture/CaptureStrategy.stop ()V 1
      // 4b: aload 0
      // 4c: aconst_null
      // 4d: putfield io/sentry/android/replay/ReplayIntegration.captureStrategy Lio/sentry/android/replay/capture/CaptureStrategy;
      // 50: aload 0
      // 51: getfield io/sentry/android/replay/ReplayIntegration.lifecycle Lio/sentry/android/replay/ReplayLifecycle;
      // 54: getstatic io/sentry/android/replay/ReplayState.STOPPED Lio/sentry/android/replay/ReplayState;
      // 57: invokevirtual io/sentry/android/replay/ReplayLifecycle.setCurrentState$sentry_android_replay_release (Lio/sentry/android/replay/ReplayState;)V
      // 5a: aload 0
      // 5b: monitorexit
      // 5c: return
      // 5d: aload 0
      // 5e: monitorexit
      // 5f: return
      // 60: astore 1
      // 61: aload 0
      // 62: monitorexit
      // 63: aload 1
      // 64: athrow
   }

   private class PreviousReplayHint : Backfillable {
      public override fun shouldEnrich(): Boolean {
         return false;
      }
   }

   private class ReplayExecutorServiceThreadFactory : ThreadFactory {
      private final var cnt: Int

      public override fun newThread(r: Runnable): Thread {
         val var3: StringBuilder = new StringBuilder("SentryReplayIntegration-");
         var3.append(this.cnt++);
         val var4: Thread = new Thread(var1, var3.toString());
         var4.setDaemon(true);
         return var4;
      }
   }
}
