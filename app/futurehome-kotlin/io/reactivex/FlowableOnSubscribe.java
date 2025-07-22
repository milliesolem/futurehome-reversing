package io.reactivex;

public interface FlowableOnSubscribe<T> {
   void subscribe(FlowableEmitter<T> var1) throws Exception;
}
