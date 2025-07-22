package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CompletableAmb extends Completable {
   private final CompletableSource[] sources;
   private final Iterable<? extends CompletableSource> sourcesIterable;

   public CompletableAmb(CompletableSource[] var1, Iterable<? extends CompletableSource> var2) {
      this.sources = var1;
      this.sourcesIterable = var2;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(CompletableObserver var1) {
      CompletableSource[] var5 = this.sources;
      int var3;
      if (var5 == null) {
         CompletableSource[] var4 = new CompletableSource[8];

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

            CompletableSource var7;
            try {
               if (!var6.hasNext()) {
                  break;
               }

               var7 = (CompletableSource)var6.next();
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
                  var5 = new CompletableSource[(var2 >> 2) + var2];
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

      CompositeDisposable var30 = new CompositeDisposable();
      var1.onSubscribe(var30);
      AtomicBoolean var33 = new AtomicBoolean();

      for (int var28 = 0; var28 < var3; var28++) {
         CompletableSource var34 = var5[var28];
         if (var30.isDisposed()) {
            return;
         }

         if (var34 == null) {
            NullPointerException var32 = new NullPointerException("One of the sources is null");
            if (var33.compareAndSet(false, true)) {
               var30.dispose();
               var1.onError(var32);
            } else {
               RxJavaPlugins.onError(var32);
            }

            return;
         }

         var34.subscribe(new CompletableAmb.Amb(var33, var30, var1));
      }

      if (var3 == 0) {
         var1.onComplete();
      }
   }

   static final class Amb implements CompletableObserver {
      final CompletableObserver downstream;
      final AtomicBoolean once;
      final CompositeDisposable set;
      Disposable upstream;

      Amb(AtomicBoolean var1, CompositeDisposable var2, CompletableObserver var3) {
         this.once = var1;
         this.set = var2;
         this.downstream = var3;
      }

      @Override
      public void onComplete() {
         if (this.once.compareAndSet(false, true)) {
            this.set.delete(this.upstream);
            this.set.dispose();
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.once.compareAndSet(false, true)) {
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
   }
}
