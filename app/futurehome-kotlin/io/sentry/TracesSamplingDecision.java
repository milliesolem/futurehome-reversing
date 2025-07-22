package io.sentry;

public final class TracesSamplingDecision {
   private final Double profileSampleRate;
   private final Boolean profileSampled;
   private final Double sampleRate;
   private final Boolean sampled;

   public TracesSamplingDecision(Boolean var1) {
      this(var1, null);
   }

   public TracesSamplingDecision(Boolean var1, Double var2) {
      this(var1, var2, false, null);
   }

   public TracesSamplingDecision(Boolean var1, Double var2, Boolean var3, Double var4) {
      this.sampled = var1;
      this.sampleRate = var2;
      boolean var5;
      if (var1 && var3) {
         var5 = true;
      } else {
         var5 = false;
      }

      this.profileSampled = var5;
      this.profileSampleRate = var4;
   }

   public Double getProfileSampleRate() {
      return this.profileSampleRate;
   }

   public Boolean getProfileSampled() {
      return this.profileSampled;
   }

   public Double getSampleRate() {
      return this.sampleRate;
   }

   public Boolean getSampled() {
      return this.sampled;
   }
}
