package io.reactivex;

public interface SingleOnSubscribe<T> {
   void subscribe(SingleEmitter<T> var1) throws Exception;
}
