package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.ScalarSubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableScalarXMap {
   private FlowableScalarXMap() {
      throw new IllegalStateException("No instances!");
   }

   public static <T, U> Flowable<U> scalarXMap(T var0, Function<? super T, ? extends Publisher<? extends U>> var1) {
      return RxJavaPlugins.onAssembly(new FlowableScalarXMap.ScalarXMapFlowable<>(var0, var1));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static <T, R> boolean tryScalarXMapSubscribe(
      Publisher<T> var0, Subscriber<? super R> var1, Function<? super T, ? extends Publisher<? extends R>> var2
   ) {
      if (var0 instanceof Callable) {
         try {
            var15 = ((Callable)var0).call();
         } catch (Throwable var14) {
            Exceptions.throwIfFatal(var14);
            EmptySubscription.error(var14, var1);
            return true;
         }

         if (var15 == null) {
            EmptySubscription.complete(var1);
            return true;
         } else {
            try {
               var0 = ObjectHelper.requireNonNull((Publisher)var2.apply(var15), "The mapper returned a null Publisher");
            } catch (Throwable var13) {
               Exceptions.throwIfFatal(var13);
               EmptySubscription.error(var13, var1);
               return true;
            }

            if (var0 instanceof Callable) {
               try {
                  var17 = ((Callable)var0).call();
               } catch (Throwable var12) {
                  Exceptions.throwIfFatal(var12);
                  EmptySubscription.error(var12, var1);
                  return true;
               }

               if (var17 == null) {
                  EmptySubscription.complete(var1);
                  return true;
               }

               var1.onSubscribe(new ScalarSubscription<>(var1, var17));
            } else {
               var0.subscribe(var1);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   static final class ScalarXMapFlowable<T, R> extends Flowable<R> {
      final Function<? super T, ? extends Publisher<? extends R>> mapper;
      final T value;

      ScalarXMapFlowable(T var1, Function<? super T, ? extends Publisher<? extends R>> var2) {
         this.value = (T)var1;
         this.mapper = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void subscribeActual(Subscriber<? super R> var1) {
         Publisher var2;
         try {
            var2 = ObjectHelper.requireNonNull(this.mapper.apply(this.value), "The mapper returned a null Publisher");
         } catch (Throwable var8) {
            EmptySubscription.error(var8, var1);
            return;
         }

         if (var2 instanceof Callable) {
            try {
               var9 = ((Callable)var2).call();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               EmptySubscription.error(var7, var1);
               return;
            }

            if (var9 == null) {
               EmptySubscription.complete(var1);
               return;
            }

            var1.onSubscribe(new ScalarSubscription<>(var1, var9));
         } else {
            var2.subscribe(var1);
         }
      }
   }
}
