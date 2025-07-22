package okhttp3

import java.util.concurrent.TimeUnit
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.concurrent.TaskRunner
import okhttp3.internal.connection.RealConnectionPool

public class ConnectionPool internal constructor(delegate: RealConnectionPool) {
   internal final val delegate: RealConnectionPool

   public constructor() : this(5, 5L, TimeUnit.MINUTES)
   public constructor(maxIdleConnections: Int, keepAliveDuration: Long, timeUnit: TimeUnit) : Intrinsics.checkParameterIsNotNull(var4, "timeUnit") {
      this(new RealConnectionPool(TaskRunner.INSTANCE, var1, var2, var4));
   }

   init {
      Intrinsics.checkParameterIsNotNull(var1, "delegate");
      super();
      this.delegate = var1;
   }

   public fun connectionCount(): Int {
      return this.delegate.connectionCount();
   }

   public fun evictAll() {
      this.delegate.evictAll();
   }

   public fun idleConnectionCount(): Int {
      return this.delegate.idleConnectionCount();
   }
}
