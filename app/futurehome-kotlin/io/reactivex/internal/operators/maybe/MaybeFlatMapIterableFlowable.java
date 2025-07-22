package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;

public final class MaybeFlatMapIterableFlowable<T, R> extends Flowable<R> {
   final Function<? super T, ? extends Iterable<? extends R>> mapper;
   final MaybeSource<T> source;

   public MaybeFlatMapIterableFlowable(MaybeSource<T> var1, Function<? super T, ? extends Iterable<? extends R>> var2) {
      this.source = var1;
      this.mapper = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      this.source.subscribe(new MaybeFlatMapIterableFlowable.FlatMapIterableObserver<>(var1, this.mapper));
   }

   static final class FlatMapIterableObserver<T, R> extends BasicIntQueueSubscription<R> implements MaybeObserver<T> {
      private static final long serialVersionUID = -8938804753851907758L;
      volatile boolean cancelled;
      final Subscriber<? super R> downstream;
      volatile Iterator<? extends R> it;
      final Function<? super T, ? extends Iterable<? extends R>> mapper;
      boolean outputFused;
      final AtomicLong requested;
      Disposable upstream;

      FlatMapIterableObserver(Subscriber<? super R> var1, Function<? super T, ? extends Iterable<? extends R>> var2) {
         this.downstream = var1;
         this.mapper = var2;
         this.requested = new AtomicLong();
      }

      public void cancel() {
         this.cancelled = true;
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }

      @Override
      public void clear() {
         this.it = null;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var9 = this.downstream;
            Iterator var8 = this.it;
            if (this.outputFused && var8 != null) {
               var9.onNext(null);
               var9.onComplete();
            } else {
               int var1 = 1;

               while (true) {
                  if (var8 != null) {
                     long var5 = this.requested.get();
                     if (var5 == Long.MAX_VALUE) {
                        this.fastPath(var9, var8);
                        return;
                     }

                     long var3 = 0L;

                     while (var3 != var5) {
                        if (this.cancelled) {
                           return;
                        }

                        Object var10;
                        try {
                           var10 = ObjectHelper.requireNonNull(var8.next(), "The iterator returned a null value");
                        } catch (Throwable var15) {
                           Exceptions.throwIfFatal(var15);
                           var9.onError(var15);
                           return;
                        }

                        var9.onNext(var10);
                        if (this.cancelled) {
                           return;
                        }

                        var3++;

                        boolean var7;
                        try {
                           var7 = var8.hasNext();
                        } catch (Throwable var16) {
                           Exceptions.throwIfFatal(var16);
                           var9.onError(var16);
                           return;
                        }

                        if (!var7) {
                           var9.onComplete();
                           return;
                        }
                     }

                     if (var3 != 0L) {
                        BackpressureHelper.produced(this.requested, var3);
                     }
                  }

                  int var2 = this.addAndGet(-var1);
                  if (var2 == 0) {
                     return;
                  }

                  var1 = var2;
                  if (var8 == null) {
                     var8 = this.it;
                     var1 = var2;
                  }
               }
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void fastPath(Subscriber<? super R> var1, Iterator<? extends R> var2) {
         while (!this.cancelled) {
            Object var4;
            try {
               var4 = var2.next();
            } catch (Throwable var10) {
               Exceptions.throwIfFatal(var10);
               var1.onError(var10);
               return;
            }

            var1.onNext(var4);
            if (this.cancelled) {
               return;
            }

            boolean var3;
            try {
               var3 = var2.hasNext();
            } catch (Throwable var9) {
               Exceptions.throwIfFatal(var9);
               var1.onError(var9);
               return;
            }

            if (!var3) {
               var1.onComplete();
               return;
            }
         }
      }

      @Override
      public boolean isEmpty() {
         boolean var1;
         if (this.it == null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         boolean var2;
         try {
            var1 = this.mapper.apply((T)var1).iterator();
            var2 = var1.hasNext();
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.downstream.onError(var4);
            return;
         }

         if (!var2) {
            this.downstream.onComplete();
         } else {
            this.it = var1;
            this.drain();
         }
      }

      @Override
      public R poll() throws Exception {
         Iterator var1 = this.it;
         if (var1 != null) {
            Object var2 = ObjectHelper.requireNonNull(var1.next(), "The iterator returned a null value");
            if (!var1.hasNext()) {
               this.it = null;
            }

            return (R)var2;
         } else {
            return null;
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }

      @Override
      public int requestFusion(int var1) {
         if ((var1 & 2) != 0) {
            this.outputFused = true;
            return 2;
         } else {
            return 0;
         }
      }
   }
}
