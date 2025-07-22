package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableMergeIterable extends Completable {
   final Iterable<? extends CompletableSource> sources;

   public CompletableMergeIterable(Iterable<? extends CompletableSource> var1) {
      this.sources = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(CompletableObserver var1) {
      CompositeDisposable var3 = new CompositeDisposable();
      var1.onSubscribe(var3);

      Iterator var4;
      try {
         var4 = ObjectHelper.requireNonNull(this.sources.iterator(), "The source iterator returned is null");
      } catch (Throwable var18) {
         Exceptions.throwIfFatal(var18);
         var1.onError(var18);
         return;
      }

      AtomicInteger var5 = new AtomicInteger(1);
      CompletableMergeIterable.MergeCompletableObserver var19 = new CompletableMergeIterable.MergeCompletableObserver(var1, var3, var5);

      while (!var3.isDisposed()) {
         boolean var2;
         try {
            var2 = var4.hasNext();
         } catch (Throwable var16) {
            Exceptions.throwIfFatal(var16);
            var3.dispose();
            var19.onError(var16);
            return;
         }

         if (!var2) {
            var19.onComplete();
            return;
         }

         if (var3.isDisposed()) {
            return;
         }

         CompletableSource var6;
         try {
            var6 = ObjectHelper.requireNonNull((CompletableSource)var4.next(), "The iterator returned a null CompletableSource");
         } catch (Throwable var17) {
            Exceptions.throwIfFatal(var17);
            var3.dispose();
            var19.onError(var17);
            return;
         }

         if (var3.isDisposed()) {
            return;
         }

         var5.getAndIncrement();
         var6.subscribe(var19);
      }
   }

   static final class MergeCompletableObserver extends AtomicBoolean implements CompletableObserver {
      private static final long serialVersionUID = -7730517613164279224L;
      final CompletableObserver downstream;
      final CompositeDisposable set;
      final AtomicInteger wip;

      MergeCompletableObserver(CompletableObserver var1, CompositeDisposable var2, AtomicInteger var3) {
         this.downstream = var1;
         this.set = var2;
         this.wip = var3;
      }

      @Override
      public void onComplete() {
         if (this.wip.decrementAndGet() == 0 && this.compareAndSet(false, true)) {
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.set.dispose();
         if (this.compareAndSet(false, true)) {
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.set.add(var1);
      }
   }
}
