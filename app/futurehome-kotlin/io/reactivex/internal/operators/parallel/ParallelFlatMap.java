package io.reactivex.internal.operators.parallel;

import io.reactivex.functions.Function;
import io.reactivex.internal.operators.flowable.FlowableFlatMap;
import io.reactivex.parallel.ParallelFlowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class ParallelFlatMap<T, R> extends ParallelFlowable<R> {
   final boolean delayError;
   final Function<? super T, ? extends Publisher<? extends R>> mapper;
   final int maxConcurrency;
   final int prefetch;
   final ParallelFlowable<T> source;

   public ParallelFlatMap(ParallelFlowable<T> var1, Function<? super T, ? extends Publisher<? extends R>> var2, boolean var3, int var4, int var5) {
      this.source = var1;
      this.mapper = var2;
      this.delayError = var3;
      this.maxConcurrency = var4;
      this.prefetch = var5;
   }

   @Override
   public int parallelism() {
      return this.source.parallelism();
   }

   @Override
   public void subscribe(Subscriber<? super R>[] var1) {
      if (this.validate(var1)) {
         int var3 = var1.length;
         Subscriber[] var4 = new Subscriber[var3];

         for (int var2 = 0; var2 < var3; var2++) {
            var4[var2] = FlowableFlatMap.subscribe(var1[var2], this.mapper, this.delayError, this.maxConcurrency, this.prefetch);
         }

         this.source.subscribe(var4);
      }
   }
}
