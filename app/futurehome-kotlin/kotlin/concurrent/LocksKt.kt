package kotlin.concurrent

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock
import kotlin.contracts.InvocationKind

public inline fun <T> ReentrantReadWriteLock.read(action: () -> T): T {
   contract {
      callsInPlace(action, InvocationKind.EXACTLY_ONCE)
   }

   label13: {
      val var4: ReadLock = var0.readLock();
      var4.lock();

      try {
         val var5: Any = var1.invoke();
      } catch (var2: java.lang.Throwable) {
         var4.unlock();
      }

      var4.unlock();
   }
}

public inline fun <T> Lock.withLock(action: () -> T): T {
   contract {
      callsInPlace(action, InvocationKind.EXACTLY_ONCE)
   }

   label13: {
      var0.lock();

      try {
         val var4: Any = var1.invoke();
      } catch (var2: java.lang.Throwable) {
         var0.unlock();
      }

      var0.unlock();
   }
}

public inline fun <T> ReentrantReadWriteLock.write(action: () -> T): T {
   contract {
      callsInPlace(action, InvocationKind.EXACTLY_ONCE)
   }

   label45: {
      val var6: ReadLock = var0.readLock();
      val var11: Int;
      if (var0.getWriteHoldCount() == 0) {
         var11 = var0.getReadHoldCount();
      } else {
         var11 = 0;
      }

      for (int var3 = 0; var3 < var11; var3++) {
         var6.unlock();
      }

      val var9: WriteLock = var0.writeLock();
      var9.lock();

      try {
         val var10: Any = var1.invoke();
      } catch (var7: java.lang.Throwable) {
         for (int var12 = 0; var12 < var11; var12++) {
            var6.lock();
         }

         var9.unlock();
      }

      for (int var13 = 0; var13 < var11; var13++) {
         var6.lock();
      }

      var9.unlock();
   }
}
