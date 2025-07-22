package io.reactivex.internal.fuseable;

public interface SimpleQueue<T> {
   void clear();

   boolean isEmpty();

   boolean offer(T var1);

   boolean offer(T var1, T var2);

   T poll() throws Exception;
}
