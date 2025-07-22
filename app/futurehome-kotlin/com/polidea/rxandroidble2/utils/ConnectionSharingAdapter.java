package com.polidea.rxandroidble2.utils;

import com.polidea.rxandroidble2.RxBleConnection;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.util.concurrent.atomic.AtomicReference;

@Deprecated
public class ConnectionSharingAdapter implements ObservableTransformer<RxBleConnection, RxBleConnection> {
   final AtomicReference<Observable<RxBleConnection>> connectionObservable = new AtomicReference<>();

   @Override
   public ObservableSource<RxBleConnection> apply(Observable<RxBleConnection> param1) {
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
      // 01: getfield com/polidea/rxandroidble2/utils/ConnectionSharingAdapter.connectionObservable Ljava/util/concurrent/atomic/AtomicReference;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield com/polidea/rxandroidble2/utils/ConnectionSharingAdapter.connectionObservable Ljava/util/concurrent/atomic/AtomicReference;
      // 0b: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 0e: checkcast io/reactivex/Observable
      // 11: astore 3
      // 12: aload 3
      // 13: ifnull 1a
      // 16: aload 2
      // 17: monitorexit
      // 18: aload 3
      // 19: areturn
      // 1a: new com/polidea/rxandroidble2/utils/ConnectionSharingAdapter$1
      // 1d: astore 3
      // 1e: aload 3
      // 1f: aload 0
      // 20: invokespecial com/polidea/rxandroidble2/utils/ConnectionSharingAdapter$1.<init> (Lcom/polidea/rxandroidble2/utils/ConnectionSharingAdapter;)V
      // 23: aload 1
      // 24: aload 3
      // 25: invokevirtual io/reactivex/Observable.doFinally (Lio/reactivex/functions/Action;)Lio/reactivex/Observable;
      // 28: bipush 1
      // 29: invokevirtual io/reactivex/Observable.replay (I)Lio/reactivex/observables/ConnectableObservable;
      // 2c: invokevirtual io/reactivex/observables/ConnectableObservable.refCount ()Lio/reactivex/Observable;
      // 2f: astore 1
      // 30: aload 0
      // 31: getfield com/polidea/rxandroidble2/utils/ConnectionSharingAdapter.connectionObservable Ljava/util/concurrent/atomic/AtomicReference;
      // 34: aload 1
      // 35: invokevirtual java/util/concurrent/atomic/AtomicReference.set (Ljava/lang/Object;)V
      // 38: aload 2
      // 39: monitorexit
      // 3a: aload 1
      // 3b: areturn
      // 3c: astore 1
      // 3d: aload 2
      // 3e: monitorexit
      // 3f: aload 1
      // 40: athrow
   }
}
