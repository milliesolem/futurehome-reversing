package io.reactivex.internal.util;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.exceptions.CompositeException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ExceptionHelper {
   public static final Throwable TERMINATED = new ExceptionHelper.Termination();

   private ExceptionHelper() {
      throw new IllegalStateException("No instances!");
   }

   public static <T> boolean addThrowable(AtomicReference<Throwable> var0, Throwable var1) {
      Object var2;
      Throwable var3;
      do {
         var3 = (Throwable)var0.get();
         if (var3 == TERMINATED) {
            return false;
         }

         if (var3 == null) {
            var2 = var1;
         } else {
            var2 = new CompositeException(var3, var1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(var0, var3, var2));

      return true;
   }

   public static List<Throwable> flatten(Throwable var0) {
      ArrayList var2 = new ArrayList();
      ArrayDeque var3 = new ArrayDeque();
      var3.offer(var0);

      while (!var3.isEmpty()) {
         var0 = (Throwable)var3.removeFirst();
         if (var0 instanceof CompositeException) {
            List var5 = ((CompositeException)var0).getExceptions();

            for (int var1 = var5.size() - 1; var1 >= 0; var1--) {
               var3.offerFirst(var5.get(var1));
            }
         } else {
            var2.add(var0);
         }
      }

      return var2;
   }

   public static <T> Throwable terminate(AtomicReference<Throwable> var0) {
      Throwable var2 = (Throwable)var0.get();
      Throwable var3 = TERMINATED;
      Throwable var1 = var2;
      if (var2 != var3) {
         var1 = var0.getAndSet(var3);
      }

      return var1;
   }

   public static <E extends Throwable> Exception throwIfThrowable(Throwable var0) throws E {
      if (var0 instanceof Exception) {
         return (Exception)var0;
      } else {
         throw var0;
      }
   }

   public static String timeoutMessage(long var0, TimeUnit var2) {
      StringBuilder var3 = new StringBuilder("The source did not signal an event for ");
      var3.append(var0);
      var3.append(" ");
      var3.append(var2.toString().toLowerCase());
      var3.append(" and has been terminated.");
      return var3.toString();
   }

   public static RuntimeException wrapOrThrow(Throwable var0) {
      if (!(var0 instanceof Error)) {
         return var0 instanceof RuntimeException ? (RuntimeException)var0 : new RuntimeException(var0);
      } else {
         throw (Error)var0;
      }
   }

   static final class Termination extends Throwable {
      private static final long serialVersionUID = -4649703670690200604L;

      Termination() {
         super("No further exceptions");
      }

      @Override
      public Throwable fillInStackTrace() {
         return this;
      }
   }
}
