package com.polidea.rxandroidble2.internal.util;

import com.polidea.rxandroidble2.internal.serialization.QueueReleaseInterface;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import java.util.concurrent.atomic.AtomicBoolean;

public class QueueReleasingEmitterWrapper<T> implements Observer<T>, Cancellable {
   private final ObservableEmitter<T> emitter;
   private final AtomicBoolean isEmitterCanceled = new AtomicBoolean(false);
   private final QueueReleaseInterface queueReleaseInterface;

   public QueueReleasingEmitterWrapper(ObservableEmitter<T> var1, QueueReleaseInterface var2) {
      this.emitter = var1;
      this.queueReleaseInterface = var2;
      var1.setCancellable(this);
   }

   @Override
   public void cancel() {
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
      // 03: getfield com/polidea/rxandroidble2/internal/util/QueueReleasingEmitterWrapper.isEmitterCanceled Ljava/util/concurrent/atomic/AtomicBoolean;
      // 06: bipush 1
      // 07: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 0a: aload 0
      // 0b: monitorexit
      // 0c: return
      // 0d: astore 1
      // 0e: aload 0
      // 0f: monitorexit
      // 10: aload 1
      // 11: athrow
   }

   public boolean isWrappedEmitterUnsubscribed() {
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
      // 03: getfield com/polidea/rxandroidble2/internal/util/QueueReleasingEmitterWrapper.isEmitterCanceled Ljava/util/concurrent/atomic/AtomicBoolean;
      // 06: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 09: istore 1
      // 0a: aload 0
      // 0b: monitorexit
      // 0c: iload 1
      // 0d: ireturn
      // 0e: astore 2
      // 0f: aload 0
      // 10: monitorexit
      // 11: aload 2
      // 12: athrow
   }

   @Override
   public void onComplete() {
      this.queueReleaseInterface.release();
      this.emitter.onComplete();
   }

   @Override
   public void onError(Throwable var1) {
      this.queueReleaseInterface.release();
      this.emitter.tryOnError(var1);
   }

   @Override
   public void onNext(T var1) {
      this.emitter.onNext((T)var1);
   }

   @Override
   public void onSubscribe(Disposable var1) {
   }
}
