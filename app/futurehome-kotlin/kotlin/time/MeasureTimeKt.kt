package kotlin.time

import kotlin.contracts.InvocationKind
import kotlin.time.TimeSource.Monotonic

public inline fun measureTime(block: () -> Unit): Duration {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   val var1: Long = TimeSource.Monotonic.INSTANCE.markNow-z9LOYto();
   var0.invoke();
   return TimeSource.Monotonic.ValueTimeMark.elapsedNow-UwyO8pc(var1);
}

public inline fun Monotonic.measureTime(block: () -> Unit): Duration {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   val var2: Long = var0.markNow-z9LOYto();
   var1.invoke();
   return TimeSource.Monotonic.ValueTimeMark.elapsedNow-UwyO8pc(var2);
}

public inline fun TimeSource.measureTime(block: () -> Unit): Duration {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   val var2: TimeMark = var0.markNow();
   var1.invoke();
   return var2.elapsedNow-UwyO8pc();
}

public inline fun <T> measureTimedValue(block: () -> T): TimedValue<T> {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   return new TimedValue(var0.invoke(), TimeSource.Monotonic.ValueTimeMark.elapsedNow-UwyO8pc(TimeSource.Monotonic.INSTANCE.markNow-z9LOYto()), null);
}

public inline fun <T> Monotonic.measureTimedValue(block: () -> T): TimedValue<T> {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   return new TimedValue(var1.invoke(), TimeSource.Monotonic.ValueTimeMark.elapsedNow-UwyO8pc(var0.markNow-z9LOYto()), null);
}

public inline fun <T> TimeSource.measureTimedValue(block: () -> T): TimedValue<T> {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   return new TimedValue(var1.invoke(), var0.markNow().elapsedNow-UwyO8pc(), null);
}
