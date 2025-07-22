package com.polidea.rxandroidble2.internal.connection;

import com.polidea.rxandroidble2.RxBleConnection;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class NoRetryStrategy implements RxBleConnection.WriteOperationRetryStrategy {
   public Observable<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure> apply(
      Observable<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure> var1
   ) {
      return var1.flatMap(
         new Function<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure, Observable<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure>>(
            this
         ) {
            final NoRetryStrategy this$0;

            {
               this.this$0 = var1;
            }

            public Observable<RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure> apply(
               RxBleConnection.WriteOperationRetryStrategy.LongWriteFailure var1
            ) {
               return Observable.error(var1.getCause());
            }
         }
      );
   }
}
