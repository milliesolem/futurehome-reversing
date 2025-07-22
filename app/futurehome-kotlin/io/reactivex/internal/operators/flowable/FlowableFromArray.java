package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

public final class FlowableFromArray<T> extends Flowable<T> {
   final T[] array;

   public FlowableFromArray(T[] var1) {
      this.array = (T[])var1;
   }

   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      if (var1 instanceof ConditionalSubscriber) {
         var1.onSubscribe(new FlowableFromArray.ArrayConditionalSubscription((ConditionalSubscriber<? super T>)var1, this.array));
      } else {
         var1.onSubscribe(new FlowableFromArray.ArraySubscription(var1, this.array));
      }
   }

   static final class ArrayConditionalSubscription<T> extends FlowableFromArray.BaseArraySubscription<T> {
      private static final long serialVersionUID = 2587302975077663557L;
      final ConditionalSubscriber<? super T> downstream;

      ArrayConditionalSubscription(ConditionalSubscriber<? super T> var1, T[] var2) {
         super((T[])var2);
         this.downstream = var1;
      }

      @Override
      void fastPath() {
         Object[] var5 = this.array;
         int var2 = var5.length;
         ConditionalSubscriber var3 = this.downstream;

         for (int var1 = this.index; var1 != var2; var1++) {
            if (this.cancelled) {
               return;
            }

            StringBuilder var4 = (StringBuilder)var5[var1];
            if (var4 == null) {
               var4 = new StringBuilder("The element at index ");
               var4.append(var1);
               var4.append(" is null");
               var3.onError(new NullPointerException(var4.toString()));
               return;
            }

            var3.tryOnNext(var4);
         }

         if (!this.cancelled) {
            var3.onComplete();
         }
      }

      @Override
      void slowPath(long var1) {
         Object[] var10 = this.array;
         int var4 = var10.length;
         int var3 = this.index;
         ConditionalSubscriber var9 = this.downstream;

         label44:
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
                        break label44;
                     }
                     continue label44;
                  }
               }

               if (this.cancelled) {
                  return;
               }

               Object var11 = var10[var3];
               if (var11 == null) {
                  StringBuilder var14 = new StringBuilder("The element at index ");
                  var14.append(var3);
                  var14.append(" is null");
                  var9.onError(new NullPointerException(var14.toString()));
                  return;
               }

               long var13 = var5;
               if (var9.tryOnNext(var11)) {
                  var13 = var5 + 1L;
               }

               var3++;
               var5 = var13;
            }
         }
      }
   }

   static final class ArraySubscription<T> extends FlowableFromArray.BaseArraySubscription<T> {
      private static final long serialVersionUID = 2587302975077663557L;
      final Subscriber<? super T> downstream;

      ArraySubscription(Subscriber<? super T> var1, T[] var2) {
         super((T[])var2);
         this.downstream = var1;
      }

      @Override
      void fastPath() {
         Object[] var5 = this.array;
         int var2 = var5.length;
         Subscriber var3 = this.downstream;

         for (int var1 = this.index; var1 != var2; var1++) {
            if (this.cancelled) {
               return;
            }

            StringBuilder var4 = (StringBuilder)var5[var1];
            if (var4 == null) {
               var4 = new StringBuilder("The element at index ");
               var4.append(var1);
               var4.append(" is null");
               var3.onError(new NullPointerException(var4.toString()));
               return;
            }

            var3.onNext(var4);
         }

         if (!this.cancelled) {
            var3.onComplete();
         }
      }

      @Override
      void slowPath(long var1) {
         Object[] var11 = this.array;
         int var4 = var11.length;
         int var3 = this.index;
         Subscriber var9 = this.downstream;

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

               StringBuilder var10 = (StringBuilder)var11[var3];
               if (var10 == null) {
                  var10 = new StringBuilder("The element at index ");
                  var10.append(var3);
                  var10.append(" is null");
                  var9.onError(new NullPointerException(var10.toString()));
                  return;
               }

               var9.onNext(var10);
               var5++;
               var3++;
            }
         }
      }
   }

   abstract static class BaseArraySubscription<T> extends BasicQueueSubscription<T> {
      private static final long serialVersionUID = -2252972430506210021L;
      final T[] array;
      volatile boolean cancelled;
      int index;

      BaseArraySubscription(T[] var1) {
         this.array = (T[])var1;
      }

      public final void cancel() {
         this.cancelled = true;
      }

      @Override
      public final void clear() {
         this.index = this.array.length;
      }

      abstract void fastPath();

      @Override
      public final boolean isEmpty() {
         boolean var1;
         if (this.index == this.array.length) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public final T poll() {
         int var1 = this.index;
         Object[] var2 = this.array;
         if (var1 == var2.length) {
            return null;
         } else {
            this.index = var1 + 1;
            return ObjectHelper.requireNonNull((T)var2[var1], "array element is null");
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
}
