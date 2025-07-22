package kotlin.time.jdk8

import kotlin.time.Duration
import kotlin.time.DurationKt
import kotlin.time.DurationUnit

public inline fun Duration.toJavaDuration(): j..time.Duration {
   val var2: j..time.Duration = j..time.Duration.ofSeconds(Duration.getInWholeSeconds-impl(var0), (long)Duration.getNanosecondsComponent-impl(var0));
   return var2;
}

public inline fun j..time.Duration.toKotlinDuration(): Duration {
   return Duration.plus-LRDsOJo(DurationKt.toDuration(var0.getSeconds(), DurationUnit.SECONDS), DurationKt.toDuration(var0.getNano(), DurationUnit.NANOSECONDS));
}
