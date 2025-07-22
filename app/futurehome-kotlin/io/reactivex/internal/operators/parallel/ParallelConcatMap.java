package io.reactivex.internal.operators.parallel;

import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.flowable.FlowableConcatMap;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.parallel.ParallelFlowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class ParallelConcatMap<T, R> extends ParallelFlowable<R> {
   final ErrorMode errorMode;
   final Function<? super T, ? extends Publisher<? extends R>> mapper;
   final int prefetch;
   final ParallelFlowable<T> source;

   public ParallelConcatMap(ParallelFlowable<T> var1, Function<? super T, ? extends Publisher<? extends R>> var2, int var3, ErrorMode var4) {
      this.source = var1;
      this.mapper = ObjectHelper.requireNonNull(var2, "mapper");
      this.prefetch = var3;
      this.errorMode = ObjectHelper.requireNonNull(var4, "errorMode");
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
            var4[var2] = FlowableConcatMap.subscribe(var1[var2], this.mapper, this.prefetch, this.errorMode);
         }

         this.source.subscribe(var4);
      }
   }
}
