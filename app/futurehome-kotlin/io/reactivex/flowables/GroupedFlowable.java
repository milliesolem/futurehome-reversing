package io.reactivex.flowables;

import io.reactivex.Flowable;

public abstract class GroupedFlowable<K, T> extends Flowable<T> {
   final K key;

   protected GroupedFlowable(K var1) {
      this.key = (K)var1;
   }

   public K getKey() {
      return this.key;
   }
}
