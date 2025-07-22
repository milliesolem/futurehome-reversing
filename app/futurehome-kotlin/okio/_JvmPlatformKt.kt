package okio

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

internal fun String.asUtf8ToByteArray(): ByteArray {
   val var1: ByteArray = var0.getBytes(Charsets.UTF_8);
   return var1;
}

internal fun newLock(): ReentrantLock {
   return new ReentrantLock();
}

internal fun ByteArray.toUtf8String(): String {
   return new java.lang.String(var0, Charsets.UTF_8);
}

public inline fun <T> ReentrantLock.withLock(action: () -> T): T {
   label13: {
      val var4: Lock = var0;
      var0.lock();

      try {
         val var5: Any = var1.invoke();
      } catch (var2: java.lang.Throwable) {
         var4.unlock();
      }

      var4.unlock();
   }
}
