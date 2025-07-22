package io.reactivex.internal.fuseable;

public interface QueueFuseable<T> extends SimpleQueue<T> {
   int ANY = 3;
   int ASYNC = 2;
   int BOUNDARY = 4;
   int NONE = 0;
   int SYNC = 1;

   int requestFusion(int var1);
}
