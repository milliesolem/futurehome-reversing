package io.reactivex.internal.util;

import io.reactivex.Observer;

public interface ObservableQueueDrain<T, U> {
   void accept(Observer<? super U> var1, T var2);

   boolean cancelled();

   boolean done();

   boolean enter();

   Throwable error();

   int leave(int var1);
}
