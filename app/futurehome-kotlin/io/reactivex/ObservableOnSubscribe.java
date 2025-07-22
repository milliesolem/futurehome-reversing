package io.reactivex;

public interface ObservableOnSubscribe<T> {
   void subscribe(ObservableEmitter<T> var1) throws Exception;
}
