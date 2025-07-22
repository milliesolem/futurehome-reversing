package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

public final class FlowableRange extends Flowable<Integer> {
   final int end;
   final int start;

   public FlowableRange(int var1, int var2) {
      this.start = var1;
      this.end = var1 + var2;
   }

   @Override
   public void subscribeActual(Subscriber<? super Integer> var1) {
      if (var1 instanceof ConditionalSubscriber) {
         var1.onSubscribe(new FlowableRange.RangeConditionalSubscription((ConditionalSubscriber<? super Integer>)var1, this.start, this.end));
      } else {
         var1.onSubscribe(new FlowableRange.RangeSubscription(var1, this.start, this.end));
      }
   }

   abstract static class BaseRangeSubscription extends BasicQueueSubscription<Integer> {
      private static final long serialVersionUID = -2252972430506210021L;
      volatile boolean cancelled;
      final int end;
      int index;

      BaseRangeSubscription(int var1, int var2) {
         this.index = var1;
         this.end = var2;
      }

      public final void cancel() {
         this.cancelled = true;
      }

      @Override
      public final void clear() {
         this.index = this.end;
      }

      abstract void fastPath();

      @Override
      public final boolean isEmpty() {
         boolean var1;
         if (this.index == this.end) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public final Integer poll() {
         int var1 = this.index;
         if (var1 == this.end) {
            return null;
         } else {
            this.index = var1 + 1;
            return var1;
         }
      }

      public final void request(long var1) {
         if (SubscriptionHelper.validate(var1) && BackpressureHelper.add(this, var1) == 0L) {
            if (var1 == Long.MAX_VALUE) {
               this.fastPath();
            } else {
               this.slowPath(var1);
            }
         }
      }

      @Override
      public final int requestFusion(int var1) {
         return var1 & 1;
      }

      abstract void slowPath(long var1);
   }

   static final class RangeConditionalSubscription extends FlowableRange.BaseRangeSubscription {
      private static final long serialVersionUID = 2587302975077663557L;
      final ConditionalSubscriber<? super Integer> downstream;

      RangeConditionalSubscription(ConditionalSubscriber<? super Integer> var1, int var2, int var3) {
         super(var2, var3);
         this.downstream = var1;
      }

      @Override
      void fastPath() {
         int var2 = this.end;
         ConditionalSubscriber var3 = this.downstream;

         for (int var1 = this.index; var1 != var2; var1++) {
            if (this.cancelled) {
               return;
            }

            var3.tryOnNext(var1);
         }

         if (!this.cancelled) {
            var3.onComplete();
         }
      }

      @Override
      void slowPath(long var1) {
         int var4 = this.end;
         int var3 = this.index;
         ConditionalSubscriber var9 = this.downstream;

         label40:
         while (true) {
            long var5 = 0L;

            while (true) {
               while (var5 == var1 || var3 == var4) {
                  if (var3 == var4) {
                     if (!this.cancelled) {
                        var9.onComplete();
                     }

                     return;
                  }

                  long var7 = this.get();
                  var1 = var7;
                  if (var5 == var7) {
                     this.index = var3;
                     var5 = this.addAndGet(-var5);
                     var1 = var5;
                     if (var5 == 0L) {
                        break label40;
                     }
                     continue label40;
                  }
               }

               if (this.cancelled) {
                  return;
               }

               long var11 = var5;
               if (var9.tryOnNext(var3)) {
                  var11 = var5 + 1L;
               }

               var3++;
               var5 = var11;
            }
         }
      }
   }

   static final class RangeSubscription extends FlowableRange.BaseRangeSubscription {
      private static final long serialVersionUID = 2587302975077663557L;
      final Subscriber<? super Integer> downstream;

      RangeSubscription(Subscriber<? super Integer> var1, int var2, int var3) {
         super(var2, var3);
         this.downstream = var1;
      }

      @Override
      void fastPath() {
         int var2 = this.end;
         Subscriber var3 = this.downstream;

         for (int var1 = this.index; var1 != var2; var1++) {
            if (this.cancelled) {
               return;
            }

            var3.onNext(var1);
         }

         if (!this.cancelled) {
            var3.onComplete();
         }
      }

      @Override
      void slowPath(long var1) {
         int var4 = this.end;
         int var3 = this.index;
         Subscriber var9 = this.downstream;

         label36:
         while (true) {
            long var5 = 0L;

            while (true) {
               while (var5 == var1 || var3 == var4) {
                  if (var3 == var4) {
                     if (!this.cancelled) {
                        var9.onComplete();
                     }

                     return;
                  }

                  long var7 = this.get();
                  var1 = var7;
                  if (var5 == var7) {
                     this.index = var3;
                     var5 = this.addAndGet(-var5);
                     var1 = var5;
                     if (var5 == 0L) {
                        break label36;
                     }
                     continue label36;
                  }
               }

               if (this.cancelled) {
                  return;
               }

               var9.onNext(var3);
               var5++;
               var3++;
            }
         }
      }
   }
}
