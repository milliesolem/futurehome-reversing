package okio

import java.util.concurrent.TimeUnit

public open class ForwardingTimeout(delegate: Timeout) : Timeout {
   public final var delegate: Timeout
      internal set

   init {
      this.delegate = var1;
   }

   public override fun clearDeadline(): Timeout {
      return this.delegate.clearDeadline();
   }

   public override fun clearTimeout(): Timeout {
      return this.delegate.clearTimeout();
   }

   public override fun deadlineNanoTime(): Long {
      return this.delegate.deadlineNanoTime();
   }

   public override fun deadlineNanoTime(deadlineNanoTime: Long): Timeout {
      return this.delegate.deadlineNanoTime(var1);
   }

   public override fun hasDeadline(): Boolean {
      return this.delegate.hasDeadline();
   }

   public fun setDelegate(delegate: Timeout): ForwardingTimeout {
      this.delegate = var1;
      return this;
   }

   @Throws(java/io/IOException::class)
   public override fun throwIfReached() {
      this.delegate.throwIfReached();
   }

   public override fun timeout(timeout: Long, unit: TimeUnit): Timeout {
      return this.delegate.timeout(var1, var3);
   }

   public override fun timeoutNanos(): Long {
      return this.delegate.timeoutNanos();
   }
}
