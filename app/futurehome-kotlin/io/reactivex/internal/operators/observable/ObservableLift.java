package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableOperator;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableLift<R, T> extends AbstractObservableWithUpstream<T, R> {
   final ObservableOperator<? extends R, ? super T> operator;

   public ObservableLift(ObservableSource<T> var1, ObservableOperator<? extends R, ? super T> var2) {
      super(var1);
      this.operator = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Observer<? super R> var1) {
      label25: {
         try {
            try {
               var1 = this.operator.apply(var1);
               StringBuilder var10 = new StringBuilder("Operator ");
               var10.append(this.operator);
               var10.append(" returned a null Observer");
               var1 = ObjectHelper.requireNonNull(var1, var10.toString());
               break label25;
            } catch (NullPointerException var5) {
               var7 = var5;
            }
         } catch (Throwable var6) {
            Exceptions.throwIfFatal(var6);
            RxJavaPlugins.onError(var6);
            NullPointerException var2 = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
            var2.initCause(var6);
            throw var2;
         }

         throw var7;
      }

      this.source.subscribe(var1);
   }
}
