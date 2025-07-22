package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class SingleZipIterable<T, R> extends Single<R> {
   final Iterable<? extends SingleSource<? extends T>> sources;
   final Function<? super Object[], ? extends R> zipper;

   public SingleZipIterable(Iterable<? extends SingleSource<? extends T>> var1, Function<? super Object[], ? extends R> var2) {
      this.sources = var1;
      this.zipper = var2;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(SingleObserver<? super R> var1) {
      SingleSource[] var4 = new SingleSource[8];

      Iterator var6;
      try {
         var6 = this.sources.iterator();
      } catch (Throwable var25) {
         Exceptions.throwIfFatal(var25);
         EmptyDisposable.error(var25, var1);
         return;
      }

      int var3 = 0;
      int var2 = 0;

      while (true) {
         SingleSource var7;
         try {
            if (!var6.hasNext()) {
               break;
            }

            var7 = (SingleSource)var6.next();
         } catch (Throwable var27) {
            Exceptions.throwIfFatal(var27);
            EmptyDisposable.error(var27, var1);
            return;
         }

         if (var7 == null) {
            try {
               NullPointerException var28 = new NullPointerException("One of the sources is null");
               EmptyDisposable.error(var28, var1);
               return;
            } catch (Throwable var24) {
               Exceptions.throwIfFatal(var24);
               EmptyDisposable.error(var24, var1);
               return;
            }
         }

         SingleSource[] var5 = var4;

         try {
            if (var2 == var4.length) {
               var5 = Arrays.copyOf(var4, (var2 >> 2) + var2);
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

      if (var2 == 0) {
         EmptyDisposable.error(new NoSuchElementException(), var1);
      } else if (var2 == 1) {
         var4[0].subscribe(new SingleMap.MapSingleObserver<>(var1, new SingleZipIterable.SingletonArrayFunc(this)));
      } else {
         SingleZipArray.ZipCoordinator var29 = new SingleZipArray.ZipCoordinator<>(var1, var2, this.zipper);
         var1.onSubscribe(var29);

         while (var3 < var2) {
            if (var29.isDisposed()) {
               return;
            }

            var4[var3].subscribe(var29.observers[var3]);
            var3++;
         }
      }
   }

   final class SingletonArrayFunc implements Function<T, R> {
      final SingleZipIterable this$0;

      SingletonArrayFunc(SingleZipIterable var1) {
         this.this$0 = var1;
      }

      @Override
      public R apply(T var1) throws Exception {
         return ObjectHelper.requireNonNull((R)this.this$0.zipper.apply(new Object[]{var1}), "The zipper returned a null value");
      }
   }
}
