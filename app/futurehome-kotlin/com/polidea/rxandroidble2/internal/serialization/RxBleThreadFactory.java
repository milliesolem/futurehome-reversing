package com.polidea.rxandroidble2.internal.serialization;

import io.reactivex.internal.schedulers.NonBlockingThread;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class RxBleThreadFactory extends AtomicLong implements ThreadFactory {
   @Override
   public Thread newThread(Runnable var1) {
      StringBuilder var2 = new StringBuilder("RxBleThread-");
      var2.append(this.incrementAndGet());
      RxBleThreadFactory.RxBleNonBlockingThread var3 = new RxBleThreadFactory.RxBleNonBlockingThread(var1, var2.toString());
      var3.setPriority(5);
      var3.setDaemon(true);
      return var3;
   }

   static final class RxBleNonBlockingThread extends Thread implements NonBlockingThread {
      RxBleNonBlockingThread(Runnable var1, String var2) {
         super(var1, var2);
      }
   }
}
