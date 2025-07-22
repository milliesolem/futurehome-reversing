package io.reactivex;

public interface ObservableSource<T> {
   void subscribe(Observer<? super T> var1);
}
