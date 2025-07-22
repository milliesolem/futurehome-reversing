package io.reactivex;

public interface SingleSource<T> {
   void subscribe(SingleObserver<? super T> var1);
}
