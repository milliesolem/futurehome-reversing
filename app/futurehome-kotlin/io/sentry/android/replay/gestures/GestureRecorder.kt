package io.sentry.android.replay.gestures

import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.Window.Callback
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.android.replay.OnRootViewsChangedListener
import io.sentry.android.replay.WindowsKt
import io.sentry.android.replay.util.FixedWindowCallback
import java.lang.ref.WeakReference
import java.util.ArrayList

public class GestureRecorder(options: SentryOptions, touchRecorderCallback: TouchRecorderCallback) : OnRootViewsChangedListener {
   private final val options: SentryOptions
   private final val rootViews: ArrayList<WeakReference<View>>
   private final val rootViewsLock: Any
   private final val touchRecorderCallback: TouchRecorderCallback

   init {
      this.options = var1;
      this.touchRecorderCallback = var2;
      this.rootViews = new ArrayList<>();
      this.rootViewsLock = new Object();
   }

   private fun View.startGestureTracking() {
      val var2: Window = WindowsKt.getPhoneWindow(var1);
      if (var2 == null) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Window is invalid, not tracking gestures");
      } else {
         val var3: Callback = var2.getCallback();
         if (var3 !is GestureRecorder.SentryReplayGestureRecorder) {
            var2.setCallback(new GestureRecorder.SentryReplayGestureRecorder(this.options, this.touchRecorderCallback, var3));
         }
      }
   }

   private fun View.stopGestureTracking() {
      val var2: Window = WindowsKt.getPhoneWindow(var1);
      if (var2 == null) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Window was null in stopGestureTracking");
      } else {
         val var3: Callback = var2.getCallback();
         if (var3 is GestureRecorder.SentryReplayGestureRecorder) {
            var2.setCallback((var3 as GestureRecorder.SentryReplayGestureRecorder).delegate);
         }
      }
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
      // 07: getfield io/sentry/android/replay/gestures/GestureRecorder.rootViewsLock Ljava/lang/Object;
      // 0a: astore 3
      // 0b: aload 3
      // 0c: monitorenter
      // 0d: iload 2
      // 0e: ifeq 36
      // 11: aload 0
      // 12: getfield io/sentry/android/replay/gestures/GestureRecorder.rootViews Ljava/util/ArrayList;
      // 15: astore 5
      // 17: new java/lang/ref/WeakReference
      // 1a: astore 4
      // 1c: aload 4
      // 1e: aload 1
      // 1f: invokespecial java/lang/ref/WeakReference.<init> (Ljava/lang/Object;)V
      // 22: aload 5
      // 24: aload 4
      // 26: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 29: pop
      // 2a: aload 0
      // 2b: aload 1
      // 2c: invokespecial io/sentry/android/replay/gestures/GestureRecorder.startGestureTracking (Landroid/view/View;)V
      // 2f: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 32: astore 1
      // 33: goto 5a
      // 36: aload 0
      // 37: aload 1
      // 38: invokespecial io/sentry/android/replay/gestures/GestureRecorder.stopGestureTracking (Landroid/view/View;)V
      // 3b: aload 0
      // 3c: getfield io/sentry/android/replay/gestures/GestureRecorder.rootViews Ljava/util/ArrayList;
      // 3f: checkcast java/util/List
      // 42: astore 5
      // 44: new io/sentry/android/replay/gestures/GestureRecorder$onRootViewsChanged$1$1
      // 47: astore 4
      // 49: aload 4
      // 4b: aload 1
      // 4c: invokespecial io/sentry/android/replay/gestures/GestureRecorder$onRootViewsChanged$1$1.<init> (Landroid/view/View;)V
      // 4f: aload 5
      // 51: aload 4
      // 53: checkcast kotlin/jvm/functions/Function1
      // 56: invokestatic kotlin/collections/CollectionsKt.removeAll (Ljava/util/List;Lkotlin/jvm/functions/Function1;)Z
      // 59: pop
      // 5a: aload 3
      // 5b: monitorexit
      // 5c: return
      // 5d: astore 1
      // 5e: aload 3
      // 5f: monitorexit
      // 60: aload 1
      // 61: athrow
   }

   public fun stop() {
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
      // 01: getfield io/sentry/android/replay/gestures/GestureRecorder.rootViewsLock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/android/replay/gestures/GestureRecorder.rootViews Ljava/util/ArrayList;
      // 0b: checkcast java/lang/Iterable
      // 0e: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 13: astore 3
      // 14: aload 3
      // 15: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 1a: ifeq 3f
      // 1d: aload 3
      // 1e: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 23: checkcast java/lang/ref/WeakReference
      // 26: invokevirtual java/lang/ref/WeakReference.get ()Ljava/lang/Object;
      // 29: checkcast android/view/View
      // 2c: astore 2
      // 2d: aload 2
      // 2e: ifnull 14
      // 31: aload 2
      // 32: ldc "get()"
      // 34: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 37: aload 0
      // 38: aload 2
      // 39: invokespecial io/sentry/android/replay/gestures/GestureRecorder.stopGestureTracking (Landroid/view/View;)V
      // 3c: goto 14
      // 3f: aload 0
      // 40: getfield io/sentry/android/replay/gestures/GestureRecorder.rootViews Ljava/util/ArrayList;
      // 43: invokevirtual java/util/ArrayList.clear ()V
      // 46: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 49: astore 2
      // 4a: aload 1
      // 4b: monitorexit
      // 4c: return
      // 4d: astore 2
      // 4e: aload 1
      // 4f: monitorexit
      // 50: aload 2
      // 51: athrow
   }

   internal class SentryReplayGestureRecorder(options: SentryOptions, touchRecorderCallback: TouchRecorderCallback?, delegate: Callback?) : FixedWindowCallback(
         var3
      ) {
      private final val options: SentryOptions
      private final val touchRecorderCallback: TouchRecorderCallback?

      init {
         this.options = var1;
         this.touchRecorderCallback = var2;
      }

      public override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
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
         // 00: aload 1
         // 01: ifnull 43
         // 04: aload 1
         // 05: invokestatic android/view/MotionEvent.obtainNoHistory (Landroid/view/MotionEvent;)Landroid/view/MotionEvent;
         // 08: astore 2
         // 09: aload 2
         // 0a: ldc "obtainNoHistory(event)"
         // 0c: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
         // 0f: aload 0
         // 10: getfield io/sentry/android/replay/gestures/GestureRecorder$SentryReplayGestureRecorder.touchRecorderCallback Lio/sentry/android/replay/gestures/TouchRecorderCallback;
         // 13: astore 3
         // 14: aload 3
         // 15: ifnull 1f
         // 18: aload 3
         // 19: aload 2
         // 1a: invokeinterface io/sentry/android/replay/gestures/TouchRecorderCallback.onTouchEvent (Landroid/view/MotionEvent;)V 2
         // 1f: aload 2
         // 20: invokevirtual android/view/MotionEvent.recycle ()V
         // 23: goto 43
         // 26: astore 3
         // 27: aload 0
         // 28: getfield io/sentry/android/replay/gestures/GestureRecorder$SentryReplayGestureRecorder.options Lio/sentry/SentryOptions;
         // 2b: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
         // 2e: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
         // 31: ldc "Error dispatching touch event"
         // 33: aload 3
         // 34: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
         // 39: goto 1f
         // 3c: astore 1
         // 3d: aload 2
         // 3e: invokevirtual android/view/MotionEvent.recycle ()V
         // 41: aload 1
         // 42: athrow
         // 43: aload 0
         // 44: aload 1
         // 45: invokespecial io/sentry/android/replay/util/FixedWindowCallback.dispatchTouchEvent (Landroid/view/MotionEvent;)Z
         // 48: ireturn
      }
   }
}
