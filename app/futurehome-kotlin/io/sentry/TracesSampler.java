package io.sentry;

import io.sentry.util.Objects;
import io.sentry.util.Random;
import io.sentry.util.SentryRandom;

final class TracesSampler {
   private static final Double DEFAULT_TRACES_SAMPLE_RATE = 1.0;
   private final SentryOptions options;
   private final Random random;

   public TracesSampler(SentryOptions var1) {
      this(Objects.requireNonNull(var1, "options are required"), null);
   }

   TracesSampler(SentryOptions var1, Random var2) {
      this.options = var1;
      this.random = var2;
   }

   private Random getRandom() {
      Random var2 = this.random;
      Random var1 = var2;
      if (var2 == null) {
         var1 = SentryRandom.current();
      }

      return var1;
   }

   private boolean sample(Double var1) {
      boolean var2;
      if (!(var1 < this.getRandom().nextDouble())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   TracesSamplingDecision sample(SamplingContext var1) {
      TracesSamplingDecision var7 = var1.getTransactionContext().getSamplingDecision();
      if (var7 != null) {
         return var7;
      } else {
         label137: {
            if (this.options.getProfilesSampler() != null) {
               label135:
               try {
                  var21 = this.options.getProfilesSampler().sample(var1);
                  break label137;
               } catch (Throwable var16) {
                  this.options.getLogger().log(SentryLevel.ERROR, "Error in the 'ProfilesSamplerCallback' callback.", var16);
                  break label135;
               }
            }

            var21 = null;
         }

         Double var8 = var21;
         if (var21 == null) {
            var8 = this.options.getProfilesSampleRate();
         }

         boolean var6;
         if (var8 != null && this.sample(var8)) {
            var6 = true;
         } else {
            var6 = false;
         }

         Boolean var10 = var6;
         if (this.options.getTracesSampler() != null) {
            label123:
            try {
               var22 = this.options.getTracesSampler().sample(var1);
            } catch (Throwable var15) {
               this.options.getLogger().log(SentryLevel.ERROR, "Error in the 'TracesSamplerCallback' callback.", var15);
               var22 = null;
               break label123;
            }

            if (var22 != null) {
               return new TracesSamplingDecision(this.sample(var22), var22, var10, var8);
            }
         }

         TracesSamplingDecision var17 = var1.getTransactionContext().getParentSamplingDecision();
         if (var17 != null) {
            return var17;
         } else {
            Double var9 = this.options.getTracesSampleRate();
            Boolean var18 = this.options.getEnableTracing();
            Double var19;
            if (Boolean.TRUE.equals(var18)) {
               var19 = DEFAULT_TRACES_SAMPLE_RATE;
            } else {
               var19 = null;
            }

            Double var23 = var9;
            if (var9 == null) {
               var23 = var19;
            }

            double var4 = Math.pow(2.0, this.options.getBackpressureMonitor().getDownsampleFactor());
            Double var20;
            if (var23 == null) {
               var20 = null;
            } else {
               double var2 = var23;
               Double.valueOf(var4).getClass();
               var20 = var2 / var4;
            }

            return var20 != null ? new TracesSamplingDecision(this.sample(var20), var20, var10, var8) : new TracesSamplingDecision(false, null, false, null);
         }
      }
   }
}
