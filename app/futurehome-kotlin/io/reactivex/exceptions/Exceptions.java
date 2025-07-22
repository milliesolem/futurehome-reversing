package io.reactivex.exceptions;

import io.reactivex.internal.util.ExceptionHelper;

public final class Exceptions {
   private Exceptions() {
      throw new IllegalStateException("No instances!");
   }

   public static RuntimeException propagate(Throwable var0) {
      throw ExceptionHelper.wrapOrThrow(var0);
   }

   public static void throwIfFatal(Throwable var0) {
      if (!(var0 instanceof VirtualMachineError)) {
         if (!(var0 instanceof ThreadDeath)) {
            if (var0 instanceof LinkageError) {
               throw (LinkageError)var0;
            }
         } else {
            throw (ThreadDeath)var0;
         }
      } else {
         throw (VirtualMachineError)var0;
      }
   }
}
