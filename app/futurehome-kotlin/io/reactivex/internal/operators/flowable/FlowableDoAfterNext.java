package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableDoAfterNext<T> extends AbstractFlowableWithUpstream<T, T> {
   final Consumer<? super T> onAfterNext;

   public FlowableDoAfterNext(Flowable<T> var1, Consumer<? super T> var2) {
      super(var1);
      this.onAfterNext = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      if (var1 instanceof ConditionalSubscriber) {
         this.source.subscribe(new FlowableDoAfterNext.DoAfterConditionalSubscriber<>((ConditionalSubscriber<? super T>)var1, this.onAfterNext));
      } else {
         this.source.subscribe(new FlowableDoAfterNext.DoAfterSubscriber<>(var1, this.onAfterNext));
      }
   }

   static final class DoAfterConditionalSubscriber<T> extends BasicFuseableConditionalSubscriber<T, T> {
      final Consumer<? super T> onAfterNext;

      DoAfterConditionalSubscriber(ConditionalSubscriber<? super T> var1, Consumer<? super T> var2) {
         super(var1);
         this.onAfterNext = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         this.downstream.onNext(var1);
         if (this.sourceMode == 0) {
            try {
               this.onAfterNext.accept((T)var1);
            } catch (Throwable var3) {
               this.fail(var3);
               return;
            }
         }
      }

      @Override
      public T poll() throws Exception {
         Object var1 = this.qs.poll();
         if (var1 != null) {
            this.onAfterNext.accept((T)var1);
         }

         return (T)var1;
      }

      @Override
      public int requestFusion(int var1) {
         return this.transitiveBoundaryFusion(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         boolean var2 = this.downstream.tryOnNext((T)var1);

         try {
            this.onAfterNext.accept((T)var1);
         } catch (Throwable var4) {
            this.fail(var4);
            return var2;
         }

         return var2;
      }
   }

   static final class DoAfterSubscriber<T> extends BasicFuseableSubscriber<T, T> {
      final Consumer<? super T> onAfterNext;

      DoAfterSubscriber(Subscriber<? super T> var1, Consumer<? super T> var2) {
         super(var1);
         this.onAfterNext = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            this.downstream.onNext(var1);
            if (this.sourceMode == 0) {
               try {
                  this.onAfterNext.accept((T)var1);
               } catch (Throwable var3) {
                  this.fail(var3);
                  return;
               }
            }
         }
      }

      @Override
      public T poll() throws Exception {
         Object var1 = this.qs.poll();
         if (var1 != null) {
            this.onAfterNext.accept((T)var1);
         }

         return (T)var1;
      }

      @Override
      public int requestFusion(int var1) {
         return this.transitiveBoundaryFusion(var1);
      }
   }
}
