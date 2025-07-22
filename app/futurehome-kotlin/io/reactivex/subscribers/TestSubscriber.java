package io.reactivex.subscribers;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.BaseTestConsumer;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class TestSubscriber<T> extends BaseTestConsumer<T, TestSubscriber<T>> implements FlowableSubscriber<T>, Subscription, Disposable {
   private volatile boolean cancelled;
   private final Subscriber<? super T> downstream;
   private final AtomicLong missedRequested;
   private QueueSubscription<T> qs;
   private final AtomicReference<Subscription> upstream;

   public TestSubscriber() {
      this(TestSubscriber.EmptySubscriber.INSTANCE, Long.MAX_VALUE);
   }

   public TestSubscriber(long var1) {
      this(TestSubscriber.EmptySubscriber.INSTANCE, var1);
   }

   public TestSubscriber(Subscriber<? super T> var1) {
      this(var1, Long.MAX_VALUE);
   }

   public TestSubscriber(Subscriber<? super T> var1, long var2) {
      if (var2 >= 0L) {
         this.downstream = var1;
         this.upstream = new AtomicReference<>();
         this.missedRequested = new AtomicLong(var2);
      } else {
         throw new IllegalArgumentException("Negative initial request not allowed");
      }
   }

   public static <T> TestSubscriber<T> create() {
      return new TestSubscriber<>();
   }

   public static <T> TestSubscriber<T> create(long var0) {
      return new TestSubscriber<>(var0);
   }

   public static <T> TestSubscriber<T> create(Subscriber<? super T> var0) {
      return new TestSubscriber<>(var0);
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

   final TestSubscriber<T> assertFuseable() {
      if (this.qs != null) {
         return this;
      } else {
         throw new AssertionError("Upstream is not fuseable.");
      }
   }

   final TestSubscriber<T> assertFusionMode(int var1) {
      int var2 = this.establishedFusionMode;
      if (var2 != var1) {
         if (this.qs != null) {
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

   final TestSubscriber<T> assertNotFuseable() {
      if (this.qs == null) {
         return this;
      } else {
         throw new AssertionError("Upstream is fuseable.");
      }
   }

   public final TestSubscriber<T> assertNotSubscribed() {
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
   public final TestSubscriber<T> assertOf(Consumer<? super TestSubscriber<T>> var1) {
      try {
         var1.accept(this);
         return this;
      } catch (Throwable var3) {
         throw ExceptionHelper.wrapOrThrow(var3);
      }
   }

   public final TestSubscriber<T> assertSubscribed() {
      if (this.upstream.get() != null) {
         return this;
      } else {
         throw this.fail("Not subscribed!");
      }
   }

   public final void cancel() {
      if (!this.cancelled) {
         this.cancelled = true;
         SubscriptionHelper.cancel(this.upstream);
      }
   }

   @Override
   public final void dispose() {
      this.cancel();
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
      return this.cancelled;
   }

   @Override
   public final boolean isDisposed() {
      return this.cancelled;
   }

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
      // 01: getfield io/reactivex/subscribers/TestSubscriber.checkSubscriptionOnce Z
      // 04: ifne 29
      // 07: aload 0
      // 08: bipush 1
      // 09: putfield io/reactivex/subscribers/TestSubscriber.checkSubscriptionOnce Z
      // 0c: aload 0
      // 0d: getfield io/reactivex/subscribers/TestSubscriber.upstream Ljava/util/concurrent/atomic/AtomicReference;
      // 10: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 13: ifnonnull 29
      // 16: aload 0
      // 17: getfield io/reactivex/subscribers/TestSubscriber.errors Ljava/util/List;
      // 1a: new java/lang/NullPointerException
      // 1d: dup
      // 1e: ldc "onSubscribe not called in proper order"
      // 20: invokespecial java/lang/NullPointerException.<init> (Ljava/lang/String;)V
      // 23: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 28: pop
      // 29: aload 0
      // 2a: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 2d: putfield io/reactivex/subscribers/TestSubscriber.lastThread Ljava/lang/Thread;
      // 30: aload 0
      // 31: getfield io/reactivex/subscribers/TestSubscriber.errors Ljava/util/List;
      // 34: aload 1
      // 35: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 3a: pop
      // 3b: aload 1
      // 3c: ifnonnull 56
      // 3f: aload 0
      // 40: getfield io/reactivex/subscribers/TestSubscriber.errors Ljava/util/List;
      // 43: astore 3
      // 44: new java/lang/IllegalStateException
      // 47: astore 2
      // 48: aload 2
      // 49: ldc "onError received a null Throwable"
      // 4b: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 4e: aload 3
      // 4f: aload 2
      // 50: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 55: pop
      // 56: aload 0
      // 57: getfield io/reactivex/subscribers/TestSubscriber.downstream Lorg/reactivestreams/Subscriber;
      // 5a: aload 1
      // 5b: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
      // 60: aload 0
      // 61: getfield io/reactivex/subscribers/TestSubscriber.done Ljava/util/concurrent/CountDownLatch;
      // 64: invokevirtual java/util/concurrent/CountDownLatch.countDown ()V
      // 67: return
      // 68: astore 1
      // 69: aload 0
      // 6a: getfield io/reactivex/subscribers/TestSubscriber.done Ljava/util/concurrent/CountDownLatch;
      // 6d: invokevirtual java/util/concurrent/CountDownLatch.countDown ()V
      // 70: aload 1
      // 71: athrow
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
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

         this.downstream.onNext(var1);
      } else {
         while (true) {
            try {
               var1 = this.qs.poll();
            } catch (Throwable var7) {
               this.errors.add(var7);
               this.qs.cancel();
               break;
            }

            if (var1 == null) {
               break;
            }

            try {
               this.values.add((T)var1);
            } catch (Throwable var6) {
               this.errors.add(var6);
               this.qs.cancel();
               break;
            }
         }
      }
   }

   protected void onStart() {
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onSubscribe(Subscription var1) {
      this.lastThread = Thread.currentThread();
      if (var1 == null) {
         this.errors.add(new NullPointerException("onSubscribe received a null Subscription"));
      } else if (!ExternalSyntheticBackportWithForwarding0.m(this.upstream, null, var1)) {
         var1.cancel();
         if (this.upstream.get() != SubscriptionHelper.CANCELLED) {
            List var6 = this.errors;
            StringBuilder var20 = new StringBuilder("onSubscribe received multiple subscriptions: ");
            var20.append(var1);
            var6.add(new IllegalStateException(var20.toString()));
         }
      } else {
         if (this.initialFusionMode != 0 && var1 instanceof QueueSubscription) {
            QueueSubscription var5 = (QueueSubscription)var1;
            this.qs = var5;
            int var2 = var5.requestFusion(this.initialFusionMode);
            this.establishedFusionMode = var2;
            if (var2 == 1) {
               this.checkSubscriptionOnce = true;
               this.lastThread = Thread.currentThread();

               while (true) {
                  try {
                     var19 = this.qs.poll();
                  } catch (Throwable var18) {
                     this.errors.add(var18);
                     break;
                  }

                  if (var19 == null) {
                     try {
                        this.completions++;
                        break;
                     } catch (Throwable var16) {
                        this.errors.add(var16);
                        break;
                     }
                  }

                  try {
                     this.values.add((T)var19);
                  } catch (Throwable var17) {
                     this.errors.add(var17);
                     break;
                  }
               }

               return;
            }
         }

         this.downstream.onSubscribe(var1);
         long var3 = this.missedRequested.getAndSet(0L);
         if (var3 != 0L) {
            var1.request(var3);
         }

         this.onStart();
      }
   }

   public final void request(long var1) {
      SubscriptionHelper.deferredRequest(this.upstream, this.missedRequested, var1);
   }

   public final TestSubscriber<T> requestMore(long var1) {
      this.request(var1);
      return this;
   }

   final TestSubscriber<T> setInitialFusionMode(int var1) {
      this.initialFusionMode = var1;
      return this;
   }

   static enum EmptySubscriber implements FlowableSubscriber<Object> {
      INSTANCE;
      private static final TestSubscriber.EmptySubscriber[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         TestSubscriber.EmptySubscriber var0 = new TestSubscriber.EmptySubscriber();
         INSTANCE = var0;
         $VALUES = new TestSubscriber.EmptySubscriber[]{var0};
      }

      public void onComplete() {
      }

      public void onError(Throwable var1) {
      }

      public void onNext(Object var1) {
      }

      @Override
      public void onSubscribe(Subscription var1) {
      }
   }
}
