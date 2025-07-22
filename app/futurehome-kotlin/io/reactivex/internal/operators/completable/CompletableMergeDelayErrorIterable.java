package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableMergeDelayErrorIterable extends Completable {
   final Iterable<? extends CompletableSource> sources;

   public CompletableMergeDelayErrorIterable(Iterable<? extends CompletableSource> var1) {
      this.sources = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(CompletableObserver var1) {
      CompositeDisposable var7 = new CompositeDisposable();
      var1.onSubscribe(var7);

      Iterator var6;
      try {
         var6 = ObjectHelper.requireNonNull(this.sources.iterator(), "The source iterator returned is null");
      } catch (Throwable var17) {
         Exceptions.throwIfFatal(var17);
         var1.onError(var17);
         return;
      }

      AtomicInteger var3 = new AtomicInteger(1);
      AtomicThrowable var4 = new AtomicThrowable();

      while (!var7.isDisposed()) {
         label138: {
            boolean var2;
            try {
               var2 = var6.hasNext();
            } catch (Throwable var19) {
               Exceptions.throwIfFatal(var19);
               var4.addThrowable(var19);
               break label138;
            }

            label135:
            if (var2) {
               if (var7.isDisposed()) {
                  return;
               }

               CompletableSource var5;
               try {
                  var5 = ObjectHelper.requireNonNull((CompletableSource)var6.next(), "The iterator returned a null CompletableSource");
               } catch (Throwable var18) {
                  Exceptions.throwIfFatal(var18);
                  var4.addThrowable(var18);
                  break label135;
               }

               if (var7.isDisposed()) {
                  return;
               }

               var3.getAndIncrement();
               var5.subscribe(new CompletableMergeDelayErrorArray.MergeInnerCompletableObserver(var1, var7, var4, var3));
               continue;
            }
         }

         if (var3.decrementAndGet() == 0) {
            Throwable var20 = var4.terminate();
            if (var20 == null) {
               var1.onComplete();
            } else {
               var1.onError(var20);
            }
         }

         return;
      }
   }
}
