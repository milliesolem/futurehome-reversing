package io.reactivex.internal.util;

import io.reactivex.functions.Function;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class SorterFunction<T> implements Function<List<T>, List<T>> {
   final Comparator<? super T> comparator;

   public SorterFunction(Comparator<? super T> var1) {
      this.comparator = var1;
   }

   public List<T> apply(List<T> var1) throws Exception {
      Collections.sort(var1, this.comparator);
      return var1;
   }
}
