package io.reactivex;

public interface MaybeOnSubscribe<T> {
   void subscribe(MaybeEmitter<T> var1) throws Exception;
}
