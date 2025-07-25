package io.reactivex.observables;

import io.reactivex.Observable;

public abstract class GroupedObservable<K, T> extends Observable<T> {
   final K key;

   protected GroupedObservable(K var1) {
      this.key = (K)var1;
   }

   public K getKey() {
      return this.key;
   }
}
