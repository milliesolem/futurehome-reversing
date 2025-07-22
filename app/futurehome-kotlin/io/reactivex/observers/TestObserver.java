package io.reactivex.observers;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TestObserver<T>
   extends BaseTestConsumer<T, TestObserver<T>>
   implements Observer<T>,
   Disposable,
   MaybeObserver<T>,
   SingleObserver<T>,
   CompletableObserver {
   private final Observer<? super T> downstream;
   private QueueDisposable<T> qd;
   private final AtomicReference<Disposable> upstream = new AtomicReference<>();

   public TestObserver() {
      this(TestObserver.EmptyObserver.INSTANCE);
   }

   public TestObserver(Observer<? super T> var1) {
      this.downstream = var1;
   }

   public static <T> TestObserver<T> create() {
      return new TestObserver<>();
   }

   public static <T> TestObserver<T> create(Observer<? super T> var0) {
      return new TestObserver<>(var0);
   }

   static String fusionModeToString(int var0) {
      if (var0 != 0) {
         if (var0 != 1) {
            if (var0 != 2) {
               StringBuilder var1 = new StringBuilder("Unknown(");
               var1.append(var0);
               var1.append(")");
               return var1.toString();
            } else {
               return "ASYNC";
            }
         } else {
            return "SYNC";
         }
      } else {
         return "NONE";
      }
   }

   final TestObserver<T> assertFuseable() {
      if (this.qd != null) {
         return this;
      } else {
         throw new AssertionError("Upstream is not fuseable.");
      }
   }

   final TestObserver<T> assertFusionMode(int var1) {
      int var2 = this.establishedFusionMode;
      if (var2 != var1) {
         if (this.qd != null) {
            StringBuilder var3 = new StringBuilder("Fusion mode different. Expected: ");
            var3.append(fusionModeToString(var1));
            var3.append(", actual: ");
            var3.append(fusionModeToString(var2));
            throw new AssertionError(var3.toString());
         } else {
            throw this.fail("Upstream is not fuseable");
         }
      } else {
         return this;
      }
   }

   final TestObserver<T> assertNotFuseable() {
      if (this.qd == null) {
         return this;
      } else {
         throw new AssertionError("Upstream is fuseable.");
      }
   }

   public final TestObserver<T> assertNotSubscribed() {
      if (this.upstream.get() == null) {
         if (this.errors.isEmpty()) {
            return this;
         } else {
            throw this.fail("Not subscribed but errors found");
         }
      } else {
         throw this.fail("Subscribed!");
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final TestObserver<T> assertOf(Consumer<? super TestObserver<T>> var1) {
      try {
         var1.accept(this);
         return this;
      } catch (Throwable var3) {
         throw ExceptionHelper.wrapOrThrow(var3);
      }
   }

   public final TestObserver<T> assertSubscribed() {
      if (this.upstream.get() != null) {
         return this;
      } else {
         throw this.fail("Not subscribed!");
      }
   }

   public final void cancel() {
      this.dispose();
   }

   @Override
   public final void dispose() {
      DisposableHelper.dispose(this.upstream);
   }

   public final boolean hasSubscription() {
      boolean var1;
      if (this.upstream.get() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean isCancelled() {
      return this.isDisposed();
   }

   @Override
   public final boolean isDisposed() {
      return DisposableHelper.isDisposed(this.upstream.get());
   }

   @Override
   public void onComplete() {
      if (!this.checkSubscriptionOnce) {
         this.checkSubscriptionOnce = true;
         if (this.upstream.get() == null) {
            this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
         }
      }

      try {
         this.lastThread = Thread.currentThread();
         this.completions++;
         this.downstream.onComplete();
      } finally {
         this.done.countDown();
      }
   }

   @Override
   public void onError(Throwable param1) {
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
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/reactivex/observers/TestObserver.checkSubscriptionOnce Z
      // 04: ifne 29
      // 07: aload 0
      // 08: bipush 1
      // 09: putfield io/reactivex/observers/TestObserver.checkSubscriptionOnce Z
      // 0c: aload 0
      // 0d: getfield io/reactivex/observers/TestObserver.upstream Ljava/util/concurrent/atomic/AtomicReference;
      // 10: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 13: ifnonnull 29
      // 16: aload 0
      // 17: getfield io/reactivex/observers/TestObserver.errors Ljava/util/List;
      // 1a: new java/lang/IllegalStateException
      // 1d: dup
      // 1e: ldc "onSubscribe not called in proper order"
      // 20: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 23: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 28: pop
      // 29: aload 0
      // 2a: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 2d: putfield io/reactivex/observers/TestObserver.lastThread Ljava/lang/Thread;
      // 30: aload 1
      // 31: ifnonnull 4e
      // 34: aload 0
      // 35: getfield io/reactivex/observers/TestObserver.errors Ljava/util/List;
      // 38: astore 3
      // 39: new java/lang/NullPointerException
      // 3c: astore 2
      // 3d: aload 2
      // 3e: ldc "onError received a null Throwable"
      // 40: invokespecial java/lang/NullPointerException.<init> (Ljava/lang/String;)V
      // 43: aload 3
      // 44: aload 2
      // 45: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 4a: pop
      // 4b: goto 59
      // 4e: aload 0
      // 4f: getfield io/reactivex/observers/TestObserver.errors Ljava/util/List;
      // 52: aload 1
      // 53: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 58: pop
      // 59: aload 0
      // 5a: getfield io/reactivex/observers/TestObserver.downstream Lio/reactivex/Observer;
      // 5d: aload 1
      // 5e: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
      // 63: aload 0
      // 64: getfield io/reactivex/observers/TestObserver.done Ljava/util/concurrent/CountDownLatch;
      // 67: invokevirtual java/util/concurrent/CountDownLatch.countDown ()V
      // 6a: return
      // 6b: astore 1
      // 6c: aload 0
      // 6d: getfield io/reactivex/observers/TestObserver.done Ljava/util/concurrent/CountDownLatch;
      // 70: invokevirtual java/util/concurrent/CountDownLatch.countDown ()V
      // 73: aload 1
      // 74: athrow
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onNext(T var1) {
      if (!this.checkSubscriptionOnce) {
         this.checkSubscriptionOnce = true;
         if (this.upstream.get() == null) {
            this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
         }
      }

      this.lastThread = Thread.currentThread();
      if (this.establishedFusionMode != 2) {
         this.values.add((T)var1);
         if (var1 == null) {
            this.errors.add(new NullPointerException("onNext received a null value"));
         }

         this.downstream.onNext((T)var1);
      } else {
         while (true) {
            try {
               var1 = this.qd.poll();
            } catch (Throwable var7) {
               this.errors.add(var7);
               this.qd.dispose();
               break;
            }

            if (var1 == null) {
               break;
            }

            try {
               this.values.add((T)var1);
            } catch (Throwable var6) {
               this.errors.add(var6);
               this.qd.dispose();
               break;
            }
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onSubscribe(Disposable var1) {
      this.lastThread = Thread.currentThread();
      if (var1 == null) {
         this.errors.add(new NullPointerException("onSubscribe received a null Subscription"));
      } else if (!ExternalSyntheticBackportWithForwarding0.m(this.upstream, null, var1)) {
         var1.dispose();
         if (this.upstream.get() != DisposableHelper.DISPOSED) {
            List var18 = this.errors;
            StringBuilder var4 = new StringBuilder("onSubscribe received multiple subscriptions: ");
            var4.append(var1);
            var18.add(new IllegalStateException(var4.toString()));
         }
      } else {
         if (this.initialFusionMode != 0 && var1 instanceof QueueDisposable) {
            QueueDisposable var3 = (QueueDisposable)var1;
            this.qd = var3;
            int var2 = var3.requestFusion(this.initialFusionMode);
            this.establishedFusionMode = var2;
            if (var2 == 1) {
               this.checkSubscriptionOnce = true;
               this.lastThread = Thread.currentThread();

               while (true) {
                  try {
                     var17 = this.qd.poll();
                  } catch (Throwable var16) {
                     this.errors.add(var16);
                     break;
                  }

                  if (var17 == null) {
                     try {
                        this.completions++;
                        this.upstream.lazySet(DisposableHelper.DISPOSED);
                        break;
                     } catch (Throwable var14) {
                        this.errors.add(var14);
                        break;
                     }
                  }

                  try {
                     this.values.add((T)var17);
                  } catch (Throwable var15) {
                     this.errors.add(var15);
                     break;
                  }
               }

               return;
            }
         }

         this.downstream.onSubscribe(var1);
      }
   }

   @Override
   public void onSuccess(T var1) {
      this.onNext((T)var1);
      this.onComplete();
   }

   final TestObserver<T> setInitialFusionMode(int var1) {
      this.initialFusionMode = var1;
      return this;
   }

   static enum EmptyObserver implements Observer<Object> {
      INSTANCE;
      private static final TestObserver.EmptyObserver[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         TestObserver.EmptyObserver var0 = new TestObserver.EmptyObserver();
         INSTANCE = var0;
         $VALUES = new TestObserver.EmptyObserver[]{var0};
      }

      @Override
      public void onComplete() {
      }

      @Override
      public void onError(Throwable var1) {
      }

      @Override
      public void onNext(Object var1) {
      }

      @Override
      public void onSubscribe(Disposable var1) {
      }
   }
}
