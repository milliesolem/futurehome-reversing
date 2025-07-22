package okio

import java.io.InterruptedIOException
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition

public open class Timeout {
   private final var deadlineNanoTime: Long
   private final var hasDeadline: Boolean
   private final var timeoutNanos: Long

   @Throws(java/io/InterruptedIOException::class)
   public fun awaitSignal(condition: Condition) {
      var var2: Long;
      var var8: Boolean;
      try {
         var8 = this.hasDeadline();
         var2 = this.timeoutNanos();
      } catch (var15: InterruptedException) {
         Thread.currentThread().interrupt();
         throw new InterruptedIOException("interrupted");
      }

      var var4: Long = 0L;
      if (!var8 && var2 == 0L) {
         try {
            var1.await();
         } catch (var9: InterruptedException) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("interrupted");
         }
      } else {
         var var6: Long;
         try {
            var6 = System.nanoTime();
         } catch (var14: InterruptedException) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("interrupted");
         }

         if (var8 && var2 != 0L) {
            try {
               var2 = Math.min(var2, this.deadlineNanoTime() - var6);
            } catch (var13: InterruptedException) {
               Thread.currentThread().interrupt();
               throw new InterruptedIOException("interrupted");
            }
         } else if (var8) {
            try {
               var2 = this.deadlineNanoTime() - var6;
            } catch (var12: InterruptedException) {
               Thread.currentThread().interrupt();
               throw new InterruptedIOException("interrupted");
            }
         }

         if (var2 > 0L) {
            try {
               var1.await(var2, TimeUnit.NANOSECONDS);
               var4 = System.nanoTime() - var6;
            } catch (var11: InterruptedException) {
               Thread.currentThread().interrupt();
               throw new InterruptedIOException("interrupted");
            }
         }

         if (var4 >= var2) {
            try {
               throw new InterruptedIOException("timeout");
            } catch (var10: InterruptedException) {
               Thread.currentThread().interrupt();
               throw new InterruptedIOException("interrupted");
            }
         }
      }
   }

   public open fun clearDeadline(): Timeout {
      this.hasDeadline = false;
      return this;
   }

   public open fun clearTimeout(): Timeout {
      this.timeoutNanos = 0L;
      return this;
   }

   public fun deadline(duration: Long, unit: TimeUnit): Timeout {
      if (var1 > 0L) {
         return this.deadlineNanoTime(System.nanoTime() + var3.toNanos(var1));
      } else {
         val var4: StringBuilder = new StringBuilder("duration <= 0: ");
         var4.append(var1);
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   public open fun deadlineNanoTime(): Long {
      if (this.hasDeadline) {
         return this.deadlineNanoTime;
      } else {
         throw new IllegalStateException("No deadline".toString());
      }
   }

   public open fun deadlineNanoTime(deadlineNanoTime: Long): Timeout {
      this.hasDeadline = true;
      this.deadlineNanoTime = var1;
      return this;
   }

   public open fun hasDeadline(): Boolean {
      return this.hasDeadline;
   }

   public inline fun <T> intersectWith(other: Timeout, block: () -> T): T {
      val var3: Long = this.timeoutNanos();
      this.timeout(Companion.minTimeout(var1.timeoutNanos(), this.timeoutNanos()), TimeUnit.NANOSECONDS);
      label38:
      if (!this.hasDeadline()) {
         if (var1.hasDeadline()) {
            this.deadlineNanoTime(var1.deadlineNanoTime());
         }

         try {
            val var14: Any = var2.invoke();
         } catch (var7: java.lang.Throwable) {
            this.timeout(var3, TimeUnit.NANOSECONDS);
            if (var1.hasDeadline()) {
               this.clearDeadline();
            }
         }

         this.timeout(var3, TimeUnit.NANOSECONDS);
         if (var1.hasDeadline()) {
            this.clearDeadline();
         }
      } else {
         label98: {
            val var5: Long = this.deadlineNanoTime();
            if (var1.hasDeadline()) {
               this.deadlineNanoTime(Math.min(this.deadlineNanoTime(), var1.deadlineNanoTime()));
            }

            try {
               val var13: Any = var2.invoke();
            } catch (var8: java.lang.Throwable) {
               this.timeout(var3, TimeUnit.NANOSECONDS);
               if (var1.hasDeadline()) {
                  this.deadlineNanoTime(var5);
               }
            }

            this.timeout(var3, TimeUnit.NANOSECONDS);
            if (var1.hasDeadline()) {
               this.deadlineNanoTime(var5);
            }
         }
      }
   }

   @Throws(java/io/IOException::class)
   public open fun throwIfReached() {
      if (!Thread.currentThread().isInterrupted()) {
         if (this.hasDeadline && this.deadlineNanoTime - System.nanoTime() <= 0L) {
            throw new InterruptedIOException("deadline reached");
         }
      } else {
         throw new InterruptedIOException("interrupted");
      }
   }

   public open fun timeout(timeout: Long, unit: TimeUnit): Timeout {
      if (var1 >= 0L) {
         this.timeoutNanos = var3.toNanos(var1);
         return this;
      } else {
         val var4: StringBuilder = new StringBuilder("timeout < 0: ");
         var4.append(var1);
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   public open fun timeoutNanos(): Long {
      return this.timeoutNanos;
   }

   @Throws(java/io/InterruptedIOException::class)
   public fun waitUntilNotified(monitor: Any) {
      var var3: Long;
      var var9: Boolean;
      try {
         var9 = this.hasDeadline();
         var3 = this.timeoutNanos();
      } catch (var17: InterruptedException) {
         Thread.currentThread().interrupt();
         throw new InterruptedIOException("interrupted");
      }

      var var5: Long = 0L;
      if (!var9 && var3 == 0L) {
         try {
            var1.wait();
         } catch (var10: InterruptedException) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("interrupted");
         }
      } else {
         var var7: Long;
         try {
            var7 = System.nanoTime();
         } catch (var16: InterruptedException) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("interrupted");
         }

         if (var9 && var3 != 0L) {
            try {
               var3 = Math.min(var3, this.deadlineNanoTime() - var7);
            } catch (var15: InterruptedException) {
               Thread.currentThread().interrupt();
               throw new InterruptedIOException("interrupted");
            }
         } else if (var9) {
            try {
               var3 = this.deadlineNanoTime() - var7;
            } catch (var14: InterruptedException) {
               Thread.currentThread().interrupt();
               throw new InterruptedIOException("interrupted");
            }
         }

         if (var3 > 0L) {
            try {
               var5 = var3 / 1000000L;
            } catch (var13: InterruptedException) {
               Thread.currentThread().interrupt();
               throw new InterruptedIOException("interrupted");
            }

            java.lang.Long.signum(var5);
            val var2: Int = (int)(var3 - 1000000L * var5);

            try {
               var1.wait(var5, var2);
               var5 = System.nanoTime() - var7;
            } catch (var12: InterruptedException) {
               Thread.currentThread().interrupt();
               throw new InterruptedIOException("interrupted");
            }
         }

         if (var5 >= var3) {
            try {
               throw new InterruptedIOException("timeout");
            } catch (var11: InterruptedException) {
               Thread.currentThread().interrupt();
               throw new InterruptedIOException("interrupted");
            }
         }
      }
   }

   public companion object {
      public final val NONE: Timeout

      public fun minTimeout(aNanos: Long, bNanos: Long): Long {
         if (var1 == 0L || var3 != 0L && var1 >= var3) {
            var1 = var3;
         }

         return var1;
      }
   }
}
