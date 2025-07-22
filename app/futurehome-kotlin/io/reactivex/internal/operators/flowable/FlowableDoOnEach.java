package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

public final class FlowableDoOnEach<T> extends AbstractFlowableWithUpstream<T, T> {
   final Action onAfterTerminate;
   final Action onComplete;
   final Consumer<? super Throwable> onError;
   final Consumer<? super T> onNext;

   public FlowableDoOnEach(Flowable<T> var1, Consumer<? super T> var2, Consumer<? super Throwable> var3, Action var4, Action var5) {
      super(var1);
      this.onNext = var2;
      this.onError = var3;
      this.onComplete = var4;
      this.onAfterTerminate = var5;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      if (var1 instanceof ConditionalSubscriber) {
         this.source
            .subscribe(
               new FlowableDoOnEach.DoOnEachConditionalSubscriber<>(
                  (ConditionalSubscriber<? super T>)var1, this.onNext, this.onError, this.onComplete, this.onAfterTerminate
               )
            );
      } else {
         this.source.subscribe(new FlowableDoOnEach.DoOnEachSubscriber<>(var1, this.onNext, this.onError, this.onComplete, this.onAfterTerminate));
      }
   }

   static final class DoOnEachConditionalSubscriber<T> extends BasicFuseableConditionalSubscriber<T, T> {
      final Action onAfterTerminate;
      final Action onComplete;
      final Consumer<? super Throwable> onError;
      final Consumer<? super T> onNext;

      DoOnEachConditionalSubscriber(ConditionalSubscriber<? super T> var1, Consumer<? super T> var2, Consumer<? super Throwable> var3, Action var4, Action var5) {
         super(var1);
         this.onNext = var2;
         this.onError = var3;
         this.onComplete = var4;
         this.onAfterTerminate = var5;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onComplete() {
         if (!this.done) {
            try {
               this.onComplete.run();
            } catch (Throwable var6) {
               this.fail(var6);
               return;
            }

            this.done = true;
            this.downstream.onComplete();

            try {
               this.onAfterTerminate.run();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               RxJavaPlugins.onError(var7);
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;

            label50: {
               try {
                  this.onError.accept(var1);
               } catch (Throwable var8) {
                  Exceptions.throwIfFatal(var8);
                  this.downstream.onError(new CompositeException(var1, var8));
                  break label50;
               }

               this.downstream.onError(var1);
            }

            try {
               this.onAfterTerminate.run();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               RxJavaPlugins.onError(var7);
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            if (this.sourceMode != 0) {
               this.downstream.onNext(null);
            } else {
               try {
                  this.onNext.accept((T)var1);
               } catch (Throwable var3) {
                  this.fail(var3);
                  return;
               }

               this.downstream.onNext(var1);
            }
         }
      }

      @Override
      public T poll() throws Exception {
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber.qs Lio/reactivex/internal/fuseable/QueueSubscription;
         // 04: invokeinterface io/reactivex/internal/fuseable/QueueSubscription.poll ()Ljava/lang/Object; 1
         // 09: astore 1
         // 0a: aload 1
         // 0b: ifnull 5b
         // 0e: aload 0
         // 0f: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber.onNext Lio/reactivex/functions/Consumer;
         // 12: aload 1
         // 13: invokeinterface io/reactivex/functions/Consumer.accept (Ljava/lang/Object;)V 2
         // 18: aload 0
         // 19: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber.onAfterTerminate Lio/reactivex/functions/Action;
         // 1c: invokeinterface io/reactivex/functions/Action.run ()V 1
         // 21: goto 75
         // 24: astore 1
         // 25: aload 1
         // 26: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 29: aload 0
         // 2a: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber.onError Lio/reactivex/functions/Consumer;
         // 2d: aload 1
         // 2e: invokeinterface io/reactivex/functions/Consumer.accept (Ljava/lang/Object;)V 2
         // 33: aload 1
         // 34: invokestatic io/reactivex/internal/util/ExceptionHelper.throwIfThrowable (Ljava/lang/Throwable;)Ljava/lang/Exception;
         // 37: athrow
         // 38: astore 3
         // 39: new io/reactivex/exceptions/CompositeException
         // 3c: astore 2
         // 3d: aload 2
         // 3e: bipush 2
         // 3f: anewarray 74
         // 42: dup
         // 43: bipush 0
         // 44: aload 1
         // 45: aastore
         // 46: dup
         // 47: bipush 1
         // 48: aload 3
         // 49: aastore
         // 4a: invokespecial io/reactivex/exceptions/CompositeException.<init> ([Ljava/lang/Throwable;)V
         // 4d: aload 2
         // 4e: athrow
         // 4f: astore 1
         // 50: aload 0
         // 51: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber.onAfterTerminate Lio/reactivex/functions/Action;
         // 54: invokeinterface io/reactivex/functions/Action.run ()V 1
         // 59: aload 1
         // 5a: athrow
         // 5b: aload 0
         // 5c: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber.sourceMode I
         // 5f: bipush 1
         // 60: if_icmpne 75
         // 63: aload 0
         // 64: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber.onComplete Lio/reactivex/functions/Action;
         // 67: invokeinterface io/reactivex/functions/Action.run ()V 1
         // 6c: aload 0
         // 6d: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber.onAfterTerminate Lio/reactivex/functions/Action;
         // 70: invokeinterface io/reactivex/functions/Action.run ()V 1
         // 75: aload 1
         // 76: areturn
         // 77: astore 2
         // 78: aload 2
         // 79: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 7c: aload 0
         // 7d: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber.onError Lio/reactivex/functions/Consumer;
         // 80: aload 2
         // 81: invokeinterface io/reactivex/functions/Consumer.accept (Ljava/lang/Object;)V 2
         // 86: aload 2
         // 87: invokestatic io/reactivex/internal/util/ExceptionHelper.throwIfThrowable (Ljava/lang/Throwable;)Ljava/lang/Exception;
         // 8a: athrow
         // 8b: astore 1
         // 8c: new io/reactivex/exceptions/CompositeException
         // 8f: dup
         // 90: bipush 2
         // 91: anewarray 74
         // 94: dup
         // 95: bipush 0
         // 96: aload 2
         // 97: aastore
         // 98: dup
         // 99: bipush 1
         // 9a: aload 1
         // 9b: aastore
         // 9c: invokespecial io/reactivex/exceptions/CompositeException.<init> ([Ljava/lang/Throwable;)V
         // 9f: athrow
      }

      @Override
      public int requestFusion(int var1) {
         return this.transitiveBoundaryFusion(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         if (this.done) {
            return false;
         } else {
            try {
               this.onNext.accept((T)var1);
            } catch (Throwable var3) {
               this.fail(var3);
               return false;
            }

            return this.downstream.tryOnNext((T)var1);
         }
      }
   }

   static final class DoOnEachSubscriber<T> extends BasicFuseableSubscriber<T, T> {
      final Action onAfterTerminate;
      final Action onComplete;
      final Consumer<? super Throwable> onError;
      final Consumer<? super T> onNext;

      DoOnEachSubscriber(Subscriber<? super T> var1, Consumer<? super T> var2, Consumer<? super Throwable> var3, Action var4, Action var5) {
         super(var1);
         this.onNext = var2;
         this.onError = var3;
         this.onComplete = var4;
         this.onAfterTerminate = var5;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onComplete() {
         if (!this.done) {
            try {
               this.onComplete.run();
            } catch (Throwable var6) {
               this.fail(var6);
               return;
            }

            this.done = true;
            this.downstream.onComplete();

            try {
               this.onAfterTerminate.run();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               RxJavaPlugins.onError(var7);
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;

            label50: {
               try {
                  this.onError.accept(var1);
               } catch (Throwable var8) {
                  Exceptions.throwIfFatal(var8);
                  this.downstream.onError(new CompositeException(var1, var8));
                  break label50;
               }

               this.downstream.onError(var1);
            }

            try {
               this.onAfterTerminate.run();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               RxJavaPlugins.onError(var7);
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            if (this.sourceMode != 0) {
               this.downstream.onNext(null);
            } else {
               try {
                  this.onNext.accept((T)var1);
               } catch (Throwable var3) {
                  this.fail(var3);
                  return;
               }

               this.downstream.onNext(var1);
            }
         }
      }

      @Override
      public T poll() throws Exception {
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber.qs Lio/reactivex/internal/fuseable/QueueSubscription;
         // 04: invokeinterface io/reactivex/internal/fuseable/QueueSubscription.poll ()Ljava/lang/Object; 1
         // 09: astore 1
         // 0a: aload 1
         // 0b: ifnull 5b
         // 0e: aload 0
         // 0f: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber.onNext Lio/reactivex/functions/Consumer;
         // 12: aload 1
         // 13: invokeinterface io/reactivex/functions/Consumer.accept (Ljava/lang/Object;)V 2
         // 18: aload 0
         // 19: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber.onAfterTerminate Lio/reactivex/functions/Action;
         // 1c: invokeinterface io/reactivex/functions/Action.run ()V 1
         // 21: goto 75
         // 24: astore 2
         // 25: aload 2
         // 26: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 29: aload 0
         // 2a: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber.onError Lio/reactivex/functions/Consumer;
         // 2d: aload 2
         // 2e: invokeinterface io/reactivex/functions/Consumer.accept (Ljava/lang/Object;)V 2
         // 33: aload 2
         // 34: invokestatic io/reactivex/internal/util/ExceptionHelper.throwIfThrowable (Ljava/lang/Throwable;)Ljava/lang/Exception;
         // 37: athrow
         // 38: astore 3
         // 39: new io/reactivex/exceptions/CompositeException
         // 3c: astore 1
         // 3d: aload 1
         // 3e: bipush 2
         // 3f: anewarray 74
         // 42: dup
         // 43: bipush 0
         // 44: aload 2
         // 45: aastore
         // 46: dup
         // 47: bipush 1
         // 48: aload 3
         // 49: aastore
         // 4a: invokespecial io/reactivex/exceptions/CompositeException.<init> ([Ljava/lang/Throwable;)V
         // 4d: aload 1
         // 4e: athrow
         // 4f: astore 1
         // 50: aload 0
         // 51: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber.onAfterTerminate Lio/reactivex/functions/Action;
         // 54: invokeinterface io/reactivex/functions/Action.run ()V 1
         // 59: aload 1
         // 5a: athrow
         // 5b: aload 0
         // 5c: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber.sourceMode I
         // 5f: bipush 1
         // 60: if_icmpne 75
         // 63: aload 0
         // 64: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber.onComplete Lio/reactivex/functions/Action;
         // 67: invokeinterface io/reactivex/functions/Action.run ()V 1
         // 6c: aload 0
         // 6d: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber.onAfterTerminate Lio/reactivex/functions/Action;
         // 70: invokeinterface io/reactivex/functions/Action.run ()V 1
         // 75: aload 1
         // 76: areturn
         // 77: astore 1
         // 78: aload 1
         // 79: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 7c: aload 0
         // 7d: getfield io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber.onError Lio/reactivex/functions/Consumer;
         // 80: aload 1
         // 81: invokeinterface io/reactivex/functions/Consumer.accept (Ljava/lang/Object;)V 2
         // 86: aload 1
         // 87: invokestatic io/reactivex/internal/util/ExceptionHelper.throwIfThrowable (Ljava/lang/Throwable;)Ljava/lang/Exception;
         // 8a: athrow
         // 8b: astore 2
         // 8c: new io/reactivex/exceptions/CompositeException
         // 8f: dup
         // 90: bipush 2
         // 91: anewarray 74
         // 94: dup
         // 95: bipush 0
         // 96: aload 1
         // 97: aastore
         // 98: dup
         // 99: bipush 1
         // 9a: aload 2
         // 9b: aastore
         // 9c: invokespecial io/reactivex/exceptions/CompositeException.<init> ([Ljava/lang/Throwable;)V
         // 9f: athrow
      }

      @Override
      public int requestFusion(int var1) {
         return this.transitiveBoundaryFusion(var1);
      }
   }
}
