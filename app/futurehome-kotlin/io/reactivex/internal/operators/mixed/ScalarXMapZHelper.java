package io.reactivex.internal.operators.mixed;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.MaybeSource;
import io.reactivex.Observer;
import io.reactivex.SingleSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.maybe.MaybeToObservable;
import io.reactivex.internal.operators.single.SingleToObservable;
import java.util.concurrent.Callable;

final class ScalarXMapZHelper {
   private ScalarXMapZHelper() {
      throw new IllegalStateException("No instances!");
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static <T> boolean tryAsCompletable(Object var0, Function<? super T, ? extends CompletableSource> var1, CompletableObserver var2) {
      if (var0 instanceof Callable) {
         var0 = (Callable)var0;

         try {
            var0 = var0.call();
         } catch (Throwable var8) {
            Exceptions.throwIfFatal(var8);
            EmptyDisposable.error(var8, var2);
            return true;
         }

         if (var0 != null) {
            try {
               var0 = ObjectHelper.requireNonNull((CompletableSource)var1.apply(var0), "The mapper returned a null CompletableSource");
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               EmptyDisposable.error(var7, var2);
               return true;
            }
         } else {
            var0 = null;
         }

         if (var0 == null) {
            EmptyDisposable.complete(var2);
         } else {
            var0.subscribe(var2);
         }

         return true;
      } else {
         return false;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static <T, R> boolean tryAsMaybe(Object var0, Function<? super T, ? extends MaybeSource<? extends R>> var1, Observer<? super R> var2) {
      if (var0 instanceof Callable) {
         var0 = (Callable)var0;

         try {
            var0 = var0.call();
         } catch (Throwable var8) {
            Exceptions.throwIfFatal(var8);
            EmptyDisposable.error(var8, var2);
            return true;
         }

         if (var0 != null) {
            try {
               var0 = ObjectHelper.requireNonNull((MaybeSource)var1.apply(var0), "The mapper returned a null MaybeSource");
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               EmptyDisposable.error(var7, var2);
               return true;
            }
         } else {
            var0 = null;
         }

         if (var0 == null) {
            EmptyDisposable.complete(var2);
         } else {
            var0.subscribe(MaybeToObservable.create(var2));
         }

         return true;
      } else {
         return false;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static <T, R> boolean tryAsSingle(Object var0, Function<? super T, ? extends SingleSource<? extends R>> var1, Observer<? super R> var2) {
      if (var0 instanceof Callable) {
         var0 = (Callable)var0;

         try {
            var0 = var0.call();
         } catch (Throwable var8) {
            Exceptions.throwIfFatal(var8);
            EmptyDisposable.error(var8, var2);
            return true;
         }

         if (var0 != null) {
            try {
               var0 = ObjectHelper.requireNonNull((SingleSource)var1.apply(var0), "The mapper returned a null SingleSource");
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               EmptyDisposable.error(var7, var2);
               return true;
            }
         } else {
            var0 = null;
         }

         if (var0 == null) {
            EmptyDisposable.complete(var2);
         } else {
            var0.subscribe(SingleToObservable.create(var2));
         }

         return true;
      } else {
         return false;
      }
   }
}
