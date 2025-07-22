package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.Iterator;
import org.reactivestreams.Subscriber;

public final class FlowableFromIterable<T> extends Flowable<T> {
   final Iterable<? extends T> source;

   public FlowableFromIterable(Iterable<? extends T> var1) {
      this.source = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static <T> void subscribe(Subscriber<? super T> var0, Iterator<? extends T> var1) {
      boolean var2;
      try {
         var2 = var1.hasNext();
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptySubscription.error(var4, var0);
         return;
      }

      if (!var2) {
         EmptySubscription.complete(var0);
      } else {
         if (var0 instanceof ConditionalSubscriber) {
            var0.onSubscribe(new FlowableFromIterable.IteratorConditionalSubscription((ConditionalSubscriber<? super T>)var0, var1));
         } else {
            var0.onSubscribe(new FlowableFromIterable.IteratorSubscription(var0, var1));
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      Iterator var2;
      try {
         var2 = this.source.iterator();
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptySubscription.error(var4, var1);
         return;
      }

      subscribe(var1, var2);
   }

   abstract static class BaseRangeSubscription<T> extends BasicQueueSubscription<T> {
      private static final long serialVersionUID = -2252972430506210021L;
      volatile boolean cancelled;
      Iterator<? extends T> it;
      boolean once;

      BaseRangeSubscription(Iterator<? extends T> var1) {
         this.it = var1;
      }

      public final void cancel() {
         this.cancelled = true;
      }

      @Override
      public final void clear() {
         this.it = null;
      }

      abstract void fastPath();

      @Override
      public final boolean isEmpty() {
         Iterator var2 = this.it;
         boolean var1;
         if (var2 != null && var2.hasNext()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      @Override
      public final T poll() {
         Iterator var1 = this.it;
         if (var1 == null) {
            return null;
         } else {
            if (!this.once) {
               this.once = true;
            } else if (!var1.hasNext()) {
               return null;
            }

            return ObjectHelper.requireNonNull((T)this.it.next(), "Iterator.next() returned a null value");
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

   static final class IteratorConditionalSubscription<T> extends FlowableFromIterable.BaseRangeSubscription<T> {
      private static final long serialVersionUID = -6022804456014692607L;
      final ConditionalSubscriber<? super T> downstream;

      IteratorConditionalSubscription(ConditionalSubscriber<? super T> var1, Iterator<? extends T> var2) {
         super(var2);
         this.downstream = var1;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      void fastPath() {
         Iterator var3 = this.it;
         ConditionalSubscriber var2 = this.downstream;

         while (!this.cancelled) {
            Object var4;
            try {
               var4 = var3.next();
            } catch (Throwable var9) {
               Exceptions.throwIfFatal(var9);
               var2.onError(var9);
               return;
            }

            if (this.cancelled) {
               return;
            }

            if (var4 == null) {
               var2.onError(new NullPointerException("Iterator.next() returned a null value"));
               return;
            }

            var2.tryOnNext(var4);
            if (this.cancelled) {
               return;
            }

            boolean var1;
            try {
               var1 = var3.hasNext();
            } catch (Throwable var10) {
               Exceptions.throwIfFatal(var10);
               var2.onError(var10);
               return;
            }

            if (!var1) {
               if (!this.cancelled) {
                  var2.onComplete();
               }

               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      void slowPath(long var1) {
         Iterator var10 = this.it;
         ConditionalSubscriber var9 = this.downstream;

         label129:
         while (true) {
            long var3 = 0L;

            while (true) {
               while (var3 == var1) {
                  long var5 = this.get();
                  var1 = var5;
                  if (var3 == var5) {
                     var3 = this.addAndGet(-var3);
                     var1 = var3;
                     if (var3 == 0L) {
                        break label129;
                     }
                     continue label129;
                  }
               }

               if (this.cancelled) {
                  return;
               }

               Object var11;
               try {
                  var11 = var10.next();
               } catch (Throwable var16) {
                  Exceptions.throwIfFatal(var16);
                  var9.onError(var16);
                  return;
               }

               if (this.cancelled) {
                  return;
               }

               if (var11 == null) {
                  var9.onError(new NullPointerException("Iterator.next() returned a null value"));
                  return;
               }

               boolean var7 = var9.tryOnNext(var11);
               if (this.cancelled) {
                  return;
               }

               boolean var8;
               try {
                  var8 = var10.hasNext();
               } catch (Throwable var17) {
                  Exceptions.throwIfFatal(var17);
                  var9.onError(var17);
                  return;
               }

               if (!var8) {
                  if (!this.cancelled) {
                     var9.onComplete();
                  }

                  return;
               }

               if (var7) {
                  var3++;
               }
            }
         }
      }
   }

   static final class IteratorSubscription<T> extends FlowableFromIterable.BaseRangeSubscription<T> {
      private static final long serialVersionUID = -6022804456014692607L;
      final Subscriber<? super T> downstream;

      IteratorSubscription(Subscriber<? super T> var1, Iterator<? extends T> var2) {
         super(var2);
         this.downstream = var1;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      void fastPath() {
         Iterator var3 = this.it;
         Subscriber var2 = this.downstream;

         while (!this.cancelled) {
            Object var4;
            try {
               var4 = var3.next();
            } catch (Throwable var9) {
               Exceptions.throwIfFatal(var9);
               var2.onError(var9);
               return;
            }

            if (this.cancelled) {
               return;
            }

            if (var4 == null) {
               var2.onError(new NullPointerException("Iterator.next() returned a null value"));
               return;
            }

            var2.onNext(var4);
            if (this.cancelled) {
               return;
            }

            boolean var1;
            try {
               var1 = var3.hasNext();
            } catch (Throwable var10) {
               Exceptions.throwIfFatal(var10);
               var2.onError(var10);
               return;
            }

            if (!var1) {
               if (!this.cancelled) {
                  var2.onComplete();
               }

               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      void slowPath(long var1) {
         Iterator var10 = this.it;
         Subscriber var8 = this.downstream;

         label125:
         while (true) {
            long var3 = 0L;

            while (true) {
               while (var3 == var1) {
                  long var5 = this.get();
                  var1 = var5;
                  if (var3 == var5) {
                     var3 = this.addAndGet(-var3);
                     var1 = var3;
                     if (var3 == 0L) {
                        break label125;
                     }
                     continue label125;
                  }
               }

               if (this.cancelled) {
                  return;
               }

               Object var9;
               try {
                  var9 = var10.next();
               } catch (Throwable var15) {
                  Exceptions.throwIfFatal(var15);
                  var8.onError(var15);
                  return;
               }

               if (this.cancelled) {
                  return;
               }

               if (var9 == null) {
                  var8.onError(new NullPointerException("Iterator.next() returned a null value"));
                  return;
               }

               var8.onNext(var9);
               if (this.cancelled) {
                  return;
               }

               boolean var7;
               try {
                  var7 = var10.hasNext();
               } catch (Throwable var16) {
                  Exceptions.throwIfFatal(var16);
                  var8.onError(var16);
                  return;
               }

               if (!var7) {
                  if (!this.cancelled) {
                     var8.onComplete();
                  }

                  return;
               }

               var3++;
            }
         }
      }
   }
}
