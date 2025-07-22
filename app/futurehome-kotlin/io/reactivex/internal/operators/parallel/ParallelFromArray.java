package io.reactivex.internal.operators.parallel;

import io.reactivex.parallel.ParallelFlowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class ParallelFromArray<T> extends ParallelFlowable<T> {
   final Publisher<T>[] sources;

   public ParallelFromArray(Publisher<T>[] var1) {
      this.sources = var1;
   }

   @Override
   public int parallelism() {
      return this.sources.length;
   }

   @Override
   public void subscribe(Subscriber<? super T>[] var1) {
      if (this.validate(var1)) {
         int var3 = var1.length;

         for (int var2 = 0; var2 < var3; var2++) {
            this.sources[var2].subscribe(var1[var2]);
         }
      }
   }
}
