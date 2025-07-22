package io.reactivex.internal.util;

import org.reactivestreams.Subscriber;

public interface QueueDrain<T, U> {
   boolean accept(Subscriber<? super U> var1, T var2);

   boolean cancelled();

   boolean done();

   boolean enter();

   Throwable error();

   int leave(int var1);

   long produced(long var1);

   long requested();
}
