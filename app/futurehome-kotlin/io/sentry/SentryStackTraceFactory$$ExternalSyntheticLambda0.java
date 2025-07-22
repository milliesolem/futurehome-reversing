package io.sentry;

import io.sentry.protocol.SentryStackFrame;
import io.sentry.util.CollectionUtils;

// $VF: synthetic class
public final class SentryStackTraceFactory$$ExternalSyntheticLambda0 implements CollectionUtils.Predicate {
   @Override
   public final boolean test(Object var1) {
      return SentryStackTraceFactory.lambda$getInAppCallStack$0((SentryStackFrame)var1);
   }
}
