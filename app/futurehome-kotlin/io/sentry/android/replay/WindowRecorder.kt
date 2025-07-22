package io.sentry.android.replay

import android.view.View
import io.sentry.SentryOptions
import io.sentry.android.replay.util.ExecutorsKt
import io.sentry.android.replay.util.MainLooperHandler
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

internal class WindowRecorder(options: SentryOptions,
      screenshotRecorderCallback: ScreenshotRecorderCallback? = null,
      mainLooperHandler: MainLooperHandler,
      replayExecutor: ScheduledExecutorService
   ) :
   Recorder,
   OnRootViewsChangedListener {
   private final val capturer: ScheduledExecutorService
      private final get() {
         return this.capturer$delegate.getValue() as ScheduledExecutorService;
      }


   private final var capturingTask: ScheduledFuture<*>?
   private final val isRecording: AtomicBoolean
   private final val mainLooperHandler: MainLooperHandler
   private final val options: SentryOptions
   private final var recorder: ScreenshotRecorder?
   private final val replayExecutor: ScheduledExecutorService
   private final val rootViews: ArrayList<WeakReference<View>>
   private final val rootViewsLock: Any
   private final val screenshotRecorderCallback: ScreenshotRecorderCallback?

   init {
      this.options = var1;
      this.screenshotRecorderCallback = var2;
      this.mainLooperHandler = var3;
      this.replayExecutor = var4;
      this.isRecording = new AtomicBoolean(false);
      this.rootViews = new ArrayList<>();
      this.rootViewsLock = new Object();
      this.capturer$delegate = LazyKt.lazy(<unrepresentable>.INSTANCE);
   }

   @JvmStatic
   fun `start$lambda$1`(var0: WindowRecorder) {
      if (var0.recorder != null) {
         var0.recorder.capture();
      }
   }

   public override fun close() {
      this.stop();
      val var1: ScheduledExecutorService = this.getCapturer();
      ExecutorsKt.gracefullyShutdown(var1, this.options);
   }

   public override fun onRootViewsChanged(root: View, added: Boolean) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 1
      // 01: ldc "root"
      // 03: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 06: aload 0
      // 07: getfield io/sentry/android/replay/WindowRecorder.rootViewsLock Ljava/lang/Object;
      // 0a: astore 4
      // 0c: aload 4
      // 0e: monitorenter
      // 0f: iload 2
      // 10: ifeq 3f
      // 13: aload 0
      // 14: getfield io/sentry/android/replay/WindowRecorder.rootViews Ljava/util/ArrayList;
      // 17: astore 3
      // 18: new java/lang/ref/WeakReference
      // 1b: astore 5
      // 1d: aload 5
      // 1f: aload 1
      // 20: invokespecial java/lang/ref/WeakReference.<init> (Ljava/lang/Object;)V
      // 23: aload 3
      // 24: aload 5
      // 26: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 29: pop
      // 2a: aload 0
      // 2b: getfield io/sentry/android/replay/WindowRecorder.recorder Lio/sentry/android/replay/ScreenshotRecorder;
      // 2e: astore 3
      // 2f: aload 3
      // 30: ifnull ae
      // 33: aload 3
      // 34: aload 1
      // 35: invokevirtual io/sentry/android/replay/ScreenshotRecorder.bind (Landroid/view/View;)V
      // 38: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 3b: astore 1
      // 3c: goto ae
      // 3f: aload 0
      // 40: getfield io/sentry/android/replay/WindowRecorder.recorder Lio/sentry/android/replay/ScreenshotRecorder;
      // 43: astore 3
      // 44: aload 3
      // 45: ifnull 4d
      // 48: aload 3
      // 49: aload 1
      // 4a: invokevirtual io/sentry/android/replay/ScreenshotRecorder.unbind (Landroid/view/View;)V
      // 4d: aload 0
      // 4e: getfield io/sentry/android/replay/WindowRecorder.rootViews Ljava/util/ArrayList;
      // 51: checkcast java/util/List
      // 54: astore 3
      // 55: new io/sentry/android/replay/WindowRecorder$onRootViewsChanged$1$1
      // 58: astore 5
      // 5a: aload 5
      // 5c: aload 1
      // 5d: invokespecial io/sentry/android/replay/WindowRecorder$onRootViewsChanged$1$1.<init> (Landroid/view/View;)V
      // 60: aload 3
      // 61: aload 5
      // 63: checkcast kotlin/jvm/functions/Function1
      // 66: invokestatic kotlin/collections/CollectionsKt.removeAll (Ljava/util/List;Lkotlin/jvm/functions/Function1;)Z
      // 69: pop
      // 6a: aload 0
      // 6b: getfield io/sentry/android/replay/WindowRecorder.rootViews Ljava/util/ArrayList;
      // 6e: checkcast java/util/List
      // 71: invokestatic kotlin/collections/CollectionsKt.lastOrNull (Ljava/util/List;)Ljava/lang/Object;
      // 74: checkcast java/lang/ref/WeakReference
      // 77: astore 3
      // 78: aload 3
      // 79: ifnull 87
      // 7c: aload 3
      // 7d: invokevirtual java/lang/ref/WeakReference.get ()Ljava/lang/Object;
      // 80: checkcast android/view/View
      // 83: astore 3
      // 84: goto 89
      // 87: aconst_null
      // 88: astore 3
      // 89: aload 3
      // 8a: ifnull aa
      // 8d: aload 1
      // 8e: aload 3
      // 8f: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 92: ifne aa
      // 95: aload 0
      // 96: getfield io/sentry/android/replay/WindowRecorder.recorder Lio/sentry/android/replay/ScreenshotRecorder;
      // 99: astore 1
      // 9a: aload 1
      // 9b: ifnull ae
      // 9e: aload 1
      // 9f: aload 3
      // a0: invokevirtual io/sentry/android/replay/ScreenshotRecorder.bind (Landroid/view/View;)V
      // a3: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // a6: astore 1
      // a7: goto ae
      // aa: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // ad: astore 1
      // ae: aload 4
      // b0: monitorexit
      // b1: return
      // b2: astore 1
      // b3: aload 4
      // b5: monitorexit
      // b6: aload 1
      // b7: athrow
   }

   public override fun pause() {
      if (this.recorder != null) {
         this.recorder.pause();
      }
   }

   public override fun resume() {
      if (this.recorder != null) {
         this.recorder.resume();
      }
   }

   public override fun start(recorderConfig: ScreenshotRecorderConfig) {
      if (!this.isRecording.getAndSet(true)) {
         this.recorder = new ScreenshotRecorder(var1, this.options, this.mainLooperHandler, this.replayExecutor, this.screenshotRecorderCallback);
         val var2: ScheduledExecutorService = this.getCapturer();
         this.capturingTask = ExecutorsKt.scheduleAtFixedRateSafely(
            var2,
            this.options,
            "WindowRecorder.capture",
            100L,
            1000L / (long)var1.getFrameRate(),
            TimeUnit.MILLISECONDS,
            new WindowRecorder$$ExternalSyntheticLambda0(this)
         );
      }
   }

   public override fun stop() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/android/replay/WindowRecorder.rootViewsLock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/android/replay/WindowRecorder.rootViews Ljava/util/ArrayList;
      // 0b: checkcast java/lang/Iterable
      // 0e: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 13: astore 4
      // 15: aload 4
      // 17: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 1c: ifeq 41
      // 1f: aload 4
      // 21: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 26: checkcast java/lang/ref/WeakReference
      // 29: astore 2
      // 2a: aload 0
      // 2b: getfield io/sentry/android/replay/WindowRecorder.recorder Lio/sentry/android/replay/ScreenshotRecorder;
      // 2e: astore 3
      // 2f: aload 3
      // 30: ifnull 15
      // 33: aload 3
      // 34: aload 2
      // 35: invokevirtual java/lang/ref/WeakReference.get ()Ljava/lang/Object;
      // 38: checkcast android/view/View
      // 3b: invokevirtual io/sentry/android/replay/ScreenshotRecorder.unbind (Landroid/view/View;)V
      // 3e: goto 15
      // 41: aload 0
      // 42: getfield io/sentry/android/replay/WindowRecorder.rootViews Ljava/util/ArrayList;
      // 45: invokevirtual java/util/ArrayList.clear ()V
      // 48: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 4b: astore 2
      // 4c: aload 1
      // 4d: monitorexit
      // 4e: aload 0
      // 4f: getfield io/sentry/android/replay/WindowRecorder.recorder Lio/sentry/android/replay/ScreenshotRecorder;
      // 52: astore 1
      // 53: aload 1
      // 54: ifnull 5b
      // 57: aload 1
      // 58: invokevirtual io/sentry/android/replay/ScreenshotRecorder.close ()V
      // 5b: aload 0
      // 5c: aconst_null
      // 5d: putfield io/sentry/android/replay/WindowRecorder.recorder Lio/sentry/android/replay/ScreenshotRecorder;
      // 60: aload 0
      // 61: getfield io/sentry/android/replay/WindowRecorder.capturingTask Ljava/util/concurrent/ScheduledFuture;
      // 64: astore 1
      // 65: aload 1
      // 66: ifnull 71
      // 69: aload 1
      // 6a: bipush 0
      // 6b: invokeinterface java/util/concurrent/ScheduledFuture.cancel (Z)Z 2
      // 70: pop
      // 71: aload 0
      // 72: aconst_null
      // 73: putfield io/sentry/android/replay/WindowRecorder.capturingTask Ljava/util/concurrent/ScheduledFuture;
      // 76: aload 0
      // 77: getfield io/sentry/android/replay/WindowRecorder.isRecording Ljava/util/concurrent/atomic/AtomicBoolean;
      // 7a: bipush 0
      // 7b: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 7e: return
      // 7f: astore 2
      // 80: aload 1
      // 81: monitorexit
      // 82: aload 2
      // 83: athrow
   }

   internal companion object {
      private const val TAG: String
   }

   private class RecorderExecutorServiceThreadFactory : ThreadFactory {
      private final var cnt: Int

      public override fun newThread(r: Runnable): Thread {
         val var3: StringBuilder = new StringBuilder("SentryWindowRecorder-");
         var3.append(this.cnt++);
         val var4: Thread = new Thread(var1, var3.toString());
         var4.setDaemon(true);
         return var4;
      }
   }
}
