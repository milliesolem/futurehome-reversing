package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

public final class FlowableRangeLong extends Flowable<Long> {
   final long end;
   final long start;

   public FlowableRangeLong(long var1, long var3) {
      this.start = var1;
      this.end = var1 + var3;
   }

   @Override
   public void subscribeActual(Subscriber<? super Long> var1) {
      if (var1 instanceof ConditionalSubscriber) {
         var1.onSubscribe(new FlowableRangeLong.RangeConditionalSubscription((ConditionalSubscriber<? super Long>)var1, this.start, this.end));
      } else {
         var1.onSubscribe(new FlowableRangeLong.RangeSubscription(var1, this.start, this.end));
      }
   }

   abstract static class BaseRangeSubscription extends BasicQueueSubscription<Long> {
      private static final long serialVersionUID = -2252972430506210021L;
      volatile boolean cancelled;
      final long end;
      long index;

      BaseRangeSubscription(long var1, long var3) {
         this.index = var1;
         this.end = var3;
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

      public final Long poll() {
         long var1 = this.index;
         if (var1 == this.end) {
            return null;
         } else {
            this.index = 1L + var1;
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

   static final class RangeConditionalSubscription extends FlowableRangeLong.BaseRangeSubscription {
      private static final long serialVersionUID = 2587302975077663557L;
      final ConditionalSubscriber<? super Long> downstream;

      RangeConditionalSubscription(ConditionalSubscriber<? super Long> var1, long var2, long var4) {
         super(var2, var4);
         this.downstream = var1;
      }

      @Override
      void fastPath() {
         long var3 = this.end;
         ConditionalSubscriber var5 = this.downstream;

         for (long var1 = this.index; var1 != var3; var1++) {
            if (this.cancelled) {
               return;
            }

            var5.tryOnNext(var1);
         }

         if (!this.cancelled) {
            var5.onComplete();
         }
      }

      @Override
      void slowPath(long var1) {
         long var9 = this.end;
         long var3 = this.index;
         ConditionalSubscriber var11 = this.downstream;

         label40:
         while (true) {
            long var5 = 0L;

            while (true) {
               while (var5 == var1 || var3 == var9) {
                  if (var3 == var9) {
                     if (!this.cancelled) {
                        var11.onComplete();
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

               long var13 = var5;
               if (var11.tryOnNext(var3)) {
                  var13 = var5 + 1L;
               }

               var3++;
               var5 = var13;
            }
         }
      }
   }

   static final class RangeSubscription extends FlowableRangeLong.BaseRangeSubscription {
      private static final long serialVersionUID = 2587302975077663557L;
      final Subscriber<? super Long> downstream;

      RangeSubscription(Subscriber<? super Long> var1, long var2, long var4) {
         super(var2, var4);
         this.downstream = var1;
      }

      @Override
      void fastPath() {
         long var3 = this.end;
         Subscriber var5 = this.downstream;

         for (long var1 = this.index; var1 != var3; var1++) {
            if (this.cancelled) {
               return;
            }

            var5.onNext(var1);
         }

         if (!this.cancelled) {
            var5.onComplete();
         }
      }

      @Override
      void slowPath(long var1) {
         long var9 = this.end;
         long var3 = this.index;
         Subscriber var11 = this.downstream;

         label36:
         while (true) {
            long var5 = 0L;

            while (true) {
               while (var5 == var1 || var3 == var9) {
                  if (var3 == var9) {
                     if (!this.cancelled) {
                        var11.onComplete();
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

               var11.onNext(var3);
               var5++;
               var3++;
            }
         }
      }
   }
}
