package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleCreate<T> extends Single<T> {
   final SingleOnSubscribe<T> source;

   public SingleCreate(SingleOnSubscribe<T> var1) {
      this.source = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      SingleCreate.Emitter var2 = new SingleCreate.Emitter(var1);
      var1.onSubscribe(var2);

      try {
         this.source.subscribe(var2);
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         var2.onError(var4);
         return;
      }
   }

   static final class Emitter<T> extends AtomicReference<Disposable> implements SingleEmitter<T>, Disposable {
      private static final long serialVersionUID = -2467358622224974244L;
      final SingleObserver<? super T> downstream;

      Emitter(SingleObserver<? super T> var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onError(Throwable var1) {
         if (!this.tryOnError(var1)) {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onSuccess(T param1) {
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
         // 01: invokevirtual io/reactivex/internal/operators/single/SingleCreate$Emitter.get ()Ljava/lang/Object;
         // 04: getstatic io/reactivex/internal/disposables/DisposableHelper.DISPOSED Lio/reactivex/internal/disposables/DisposableHelper;
         // 07: if_acmpeq 5d
         // 0a: aload 0
         // 0b: getstatic io/reactivex/internal/disposables/DisposableHelper.DISPOSED Lio/reactivex/internal/disposables/DisposableHelper;
         // 0e: invokevirtual io/reactivex/internal/operators/single/SingleCreate$Emitter.getAndSet (Ljava/lang/Object;)Ljava/lang/Object;
         // 11: checkcast io/reactivex/disposables/Disposable
         // 14: astore 2
         // 15: aload 2
         // 16: getstatic io/reactivex/internal/disposables/DisposableHelper.DISPOSED Lio/reactivex/internal/disposables/DisposableHelper;
         // 19: if_acmpeq 5d
         // 1c: aload 1
         // 1d: ifnonnull 39
         // 20: aload 0
         // 21: getfield io/reactivex/internal/operators/single/SingleCreate$Emitter.downstream Lio/reactivex/SingleObserver;
         // 24: astore 3
         // 25: new java/lang/NullPointerException
         // 28: astore 1
         // 29: aload 1
         // 2a: ldc "onSuccess called with null. Null values are generally not allowed in 2.x operators and sources."
         // 2c: invokespecial java/lang/NullPointerException.<init> (Ljava/lang/String;)V
         // 2f: aload 3
         // 30: aload 1
         // 31: invokeinterface io/reactivex/SingleObserver.onError (Ljava/lang/Throwable;)V 2
         // 36: goto 43
         // 39: aload 0
         // 3a: getfield io/reactivex/internal/operators/single/SingleCreate$Emitter.downstream Lio/reactivex/SingleObserver;
         // 3d: aload 1
         // 3e: invokeinterface io/reactivex/SingleObserver.onSuccess (Ljava/lang/Object;)V 2
         // 43: aload 2
         // 44: ifnull 5d
         // 47: aload 2
         // 48: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
         // 4d: goto 5d
         // 50: astore 1
         // 51: aload 2
         // 52: ifnull 5b
         // 55: aload 2
         // 56: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
         // 5b: aload 1
         // 5c: athrow
         // 5d: return
      }

      @Override
      public void setCancellable(Cancellable var1) {
         this.setDisposable(new CancellableDisposable(var1));
      }

      @Override
      public void setDisposable(Disposable var1) {
         DisposableHelper.set(this, var1);
      }

      @Override
      public String toString() {
         return String.format("%s{%s}", this.getClass().getSimpleName(), super.toString());
      }

      @Override
      public boolean tryOnError(Throwable var1) {
         Object var2 = var1;
         if (var1 == null) {
            var2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
         }

         if (this.get() != DisposableHelper.DISPOSED) {
            Disposable var5 = this.getAndSet(DisposableHelper.DISPOSED);
            if (var5 != DisposableHelper.DISPOSED) {
               try {
                  this.downstream.onError((Throwable)var2);
               } finally {
                  if (var5 != null) {
                     var5.dispose();
                  }
               }

               return true;
            }
         }

         return false;
      }
   }
}
