package io.reactivex.internal.util;

import java.util.concurrent.atomic.AtomicReference;

public final class AtomicThrowable extends AtomicReference<Throwable> {
   private static final long serialVersionUID = 3949248817947090603L;

   public boolean addThrowable(Throwable var1) {
      return ExceptionHelper.addThrowable(this, var1);
   }

   public boolean isTerminated() {
      boolean var1;
      if (this.get() == ExceptionHelper.TERMINATED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Throwable terminate() {
      return ExceptionHelper.terminate(this);
   }
}
