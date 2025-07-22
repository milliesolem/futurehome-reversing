package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableMap<T, U> extends AbstractFlowableWithUpstream<T, U> {
   final Function<? super T, ? extends U> mapper;

   public FlowableMap(Flowable<T> var1, Function<? super T, ? extends U> var2) {
      super(var1);
      this.mapper = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super U> var1) {
      if (var1 instanceof ConditionalSubscriber) {
         this.source.subscribe(new FlowableMap.MapConditionalSubscriber<>((ConditionalSubscriber<? super U>)var1, this.mapper));
      } else {
         this.source.subscribe(new FlowableMap.MapSubscriber<>(var1, this.mapper));
      }
   }

   static final class MapConditionalSubscriber<T, U> extends BasicFuseableConditionalSubscriber<T, U> {
      final Function<? super T, ? extends U> mapper;

      MapConditionalSubscriber(ConditionalSubscriber<? super U> var1, Function<? super T, ? extends U> var2) {
         super(var1);
         this.mapper = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            if (this.sourceMode != 0) {
               this.downstream.onNext(null);
            } else {
               try {
                  var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper function returned a null value.");
               } catch (Throwable var3) {
                  this.fail(var3);
                  return;
               }

               this.downstream.onNext(var1);
            }
         }
      }

      @Override
      public U poll() throws Exception {
         Object var1 = this.qs.poll();
         if (var1 != null) {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper function returned a null value.");
         } else {
            var1 = null;
         }

         return (U)var1;
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
               var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper function returned a null value.");
            } catch (Throwable var3) {
               this.fail(var3);
               return true;
            }

            return this.downstream.tryOnNext((U)var1);
         }
      }
   }

   static final class MapSubscriber<T, U> extends BasicFuseableSubscriber<T, U> {
      final Function<? super T, ? extends U> mapper;

      MapSubscriber(Subscriber<? super U> var1, Function<? super T, ? extends U> var2) {
         super(var1);
         this.mapper = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            if (this.sourceMode != 0) {
               this.downstream.onNext(null);
            } else {
               try {
                  var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper function returned a null value.");
               } catch (Throwable var3) {
                  this.fail(var3);
                  return;
               }

               this.downstream.onNext(var1);
            }
         }
      }

      @Override
      public U poll() throws Exception {
         Object var1 = this.qs.poll();
         if (var1 != null) {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper function returned a null value.");
         } else {
            var1 = null;
         }

         return (U)var1;
      }

      @Override
      public int requestFusion(int var1) {
         return this.transitiveBoundaryFusion(var1);
      }
   }
}
