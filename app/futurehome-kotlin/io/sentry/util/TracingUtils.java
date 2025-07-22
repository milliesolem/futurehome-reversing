package io.sentry.util;

import io.sentry.Baggage;
import io.sentry.BaggageHeader;
import io.sentry.IHub;
import io.sentry.IScope;
import io.sentry.ISpan;
import io.sentry.PropagationContext;
import io.sentry.SentryOptions;
import io.sentry.SentryTraceHeader;
import java.util.List;

public final class TracingUtils {
   public static PropagationContext maybeUpdateBaggage(IScope var0, SentryOptions var1) {
      return var0.withPropagationContext(new TracingUtils$$ExternalSyntheticLambda2(var1, var0));
   }

   private static boolean shouldAttachTracingHeaders(String var0, SentryOptions var1) {
      return PropagationTargetsUtils.contain(var1.getTracePropagationTargets(), var0);
   }

   public static void startNewTrace(IHub var0) {
      var0.configureScope(new TracingUtils$$ExternalSyntheticLambda0());
   }

   public static TracingUtils.TracingHeaders trace(IHub var0, List<String> var1, ISpan var2) {
      SentryOptions var3 = var0.getOptions();
      if (var2 != null && !var2.isNoOp()) {
         return new TracingUtils.TracingHeaders(var2.toSentryTrace(), var2.toBaggageHeader(var1));
      } else {
         TracingUtils.PropagationContextHolder var6 = new TracingUtils.PropagationContextHolder();
         var0.configureScope(new TracingUtils$$ExternalSyntheticLambda3(var6, var3));
         if (var6.propagationContext != null) {
            PropagationContext var7 = var6.propagationContext;
            Baggage var4 = var7.getBaggage();
            BaggageHeader var5;
            if (var4 != null) {
               var5 = BaggageHeader.fromBaggageAndOutgoingHeader(var4, var1);
            } else {
               var5 = null;
            }

            return new TracingUtils.TracingHeaders(new SentryTraceHeader(var7.getTraceId(), var7.getSpanId(), null), var5);
         } else {
            return null;
         }
      }
   }

   public static TracingUtils.TracingHeaders traceIfAllowed(IHub var0, String var1, List<String> var2, ISpan var3) {
      SentryOptions var4 = var0.getOptions();
      return var4.isTraceSampling() && shouldAttachTracingHeaders(var1, var4) ? trace(var0, var2, var3) : null;
   }

   private static final class PropagationContextHolder {
      private PropagationContext propagationContext = null;

      private PropagationContextHolder() {
      }
   }

   public static final class TracingHeaders {
      private final BaggageHeader baggageHeader;
      private final SentryTraceHeader sentryTraceHeader;

      public TracingHeaders(SentryTraceHeader var1, BaggageHeader var2) {
         this.sentryTraceHeader = var1;
         this.baggageHeader = var2;
      }

      public BaggageHeader getBaggageHeader() {
         return this.baggageHeader;
      }

      public SentryTraceHeader getSentryTraceHeader() {
         return this.sentryTraceHeader;
      }
   }
}
