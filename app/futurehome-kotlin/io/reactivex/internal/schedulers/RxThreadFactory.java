package io.reactivex.internal.schedulers;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class RxThreadFactory extends AtomicLong implements ThreadFactory {
   private static final long serialVersionUID = -7789753024099756196L;
   final boolean nonBlocking;
   final String prefix;
   final int priority;

   public RxThreadFactory(String var1) {
      this(var1, 5, false);
   }

   public RxThreadFactory(String var1, int var2) {
      this(var1, var2, false);
   }

   public RxThreadFactory(String var1, int var2, boolean var3) {
      this.prefix = var1;
      this.priority = var2;
      this.nonBlocking = var3;
   }

   @Override
   public Thread newThread(Runnable var1) {
      StringBuilder var2 = new StringBuilder(this.prefix);
      var2.append('-');
      var2.append(this.incrementAndGet());
      String var4 = var2.toString();
      if (this.nonBlocking) {
         var1 = new RxThreadFactory.RxCustomThread(var1, var4);
      } else {
         var1 = new Thread(var1, var4);
      }

      var1.setPriority(this.priority);
      var1.setDaemon(true);
      return var1;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("RxThreadFactory[");
      var1.append(this.prefix);
      var1.append("]");
      return var1.toString();
   }

   static final class RxCustomThread extends Thread implements NonBlockingThread {
      RxCustomThread(Runnable var1, String var2) {
         super(var1, var2);
      }
   }
}
