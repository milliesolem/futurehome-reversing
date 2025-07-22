package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public final class MaybeAmb<T> extends Maybe<T> {
   private final MaybeSource<? extends T>[] sources;
   private final Iterable<? extends MaybeSource<? extends T>> sourcesIterable;

   public MaybeAmb(MaybeSource<? extends T>[] var1, Iterable<? extends MaybeSource<? extends T>> var2) {
      this.sources = var1;
      this.sourcesIterable = var2;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      MaybeSource[] var5 = this.sources;
      int var3;
      if (var5 == null) {
         MaybeSource[] var4 = new MaybeSource[8];

         Iterator var6;
         try {
            var6 = this.sourcesIterable.iterator();
         } catch (Throwable var25) {
            Exceptions.throwIfFatal(var25);
            EmptyDisposable.error(var25, var1);
            return;
         }

         int var2 = 0;

         while (true) {
            var5 = var4;
            var3 = var2;

            MaybeSource var7;
            try {
               if (!var6.hasNext()) {
                  break;
               }

               var7 = (MaybeSource)var6.next();
            } catch (Throwable var27) {
               Exceptions.throwIfFatal(var27);
               EmptyDisposable.error(var27, var1);
               return;
            }

            if (var7 == null) {
               try {
                  NullPointerException var29 = new NullPointerException("One of the sources is null");
                  EmptyDisposable.error(var29, var1);
                  return;
               } catch (Throwable var24) {
                  Exceptions.throwIfFatal(var24);
                  EmptyDisposable.error(var24, var1);
                  return;
               }
            }

            var5 = var4;

            try {
               if (var2 == var4.length) {
                  var5 = new MaybeSource[(var2 >> 2) + var2];
                  System.arraycopy(var4, 0, var5, 0, var2);
               }
            } catch (Throwable var26) {
               Exceptions.throwIfFatal(var26);
               EmptyDisposable.error(var26, var1);
               return;
            }

            var5[var2] = var7;
            var2++;
            var4 = var5;
         }
      } else {
         var3 = var5.length;
      }

      CompositeDisposable var33 = new CompositeDisposable();
      var1.onSubscribe(var33);
      AtomicBoolean var30 = new AtomicBoolean();

      for (int var28 = 0; var28 < var3; var28++) {
         MaybeSource var34 = var5[var28];
         if (var33.isDisposed()) {
            return;
         }

         if (var34 == null) {
            var33.dispose();
            NullPointerException var32 = new NullPointerException("One of the MaybeSources is null");
            if (var30.compareAndSet(false, true)) {
               var1.onError(var32);
            } else {
               RxJavaPlugins.onError(var32);
            }

            return;
         }

         var34.subscribe(new MaybeAmb.AmbMaybeObserver<>(var1, var33, var30));
      }

      if (var3 == 0) {
         var1.onComplete();
      }
   }

   static final class AmbMaybeObserver<T> implements MaybeObserver<T> {
      final MaybeObserver<? super T> downstream;
      final CompositeDisposable set;
      Disposable upstream;
      final AtomicBoolean winner;

      AmbMaybeObserver(MaybeObserver<? super T> var1, CompositeDisposable var2, AtomicBoolean var3) {
         this.downstream = var1;
         this.set = var2;
         this.winner = var3;
      }

      @Override
      public void onComplete() {
         if (this.winner.compareAndSet(false, true)) {
            this.set.delete(this.upstream);
            this.set.dispose();
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.winner.compareAndSet(false, true)) {
            this.set.delete(this.upstream);
            this.set.dispose();
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.upstream = var1;
         this.set.add(var1);
      }

      @Override
      public void onSuccess(T var1) {
         if (this.winner.compareAndSet(false, true)) {
            this.set.delete(this.upstream);
            this.set.dispose();
            this.downstream.onSuccess((T)var1);
         }
      }
   }
}
