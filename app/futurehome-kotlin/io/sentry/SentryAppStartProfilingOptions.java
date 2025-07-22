package io.sentry;

import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;

public final class SentryAppStartProfilingOptions implements JsonUnknown, JsonSerializable {
   boolean isProfilingEnabled;
   Double profileSampleRate;
   boolean profileSampled;
   String profilingTracesDirPath;
   int profilingTracesHz;
   Double traceSampleRate;
   boolean traceSampled;
   private Map<String, Object> unknown;

   public SentryAppStartProfilingOptions() {
      this.traceSampled = false;
      this.traceSampleRate = null;
      this.profileSampled = false;
      this.profileSampleRate = null;
      this.profilingTracesDirPath = null;
      this.isProfilingEnabled = false;
      this.profilingTracesHz = 0;
   }

   SentryAppStartProfilingOptions(SentryOptions var1, TracesSamplingDecision var2) {
      this.traceSampled = var2.getSampled();
      this.traceSampleRate = var2.getSampleRate();
      this.profileSampled = var2.getProfileSampled();
      this.profileSampleRate = var2.getProfileSampleRate();
      this.profilingTracesDirPath = var1.getProfilingTracesDirPath();
      this.isProfilingEnabled = var1.isProfilingEnabled();
      this.profilingTracesHz = var1.getProfilingTracesHz();
   }

   public Double getProfileSampleRate() {
      return this.profileSampleRate;
   }

   public String getProfilingTracesDirPath() {
      return this.profilingTracesDirPath;
   }

   public int getProfilingTracesHz() {
      return this.profilingTracesHz;
   }

   public Double getTraceSampleRate() {
      return this.traceSampleRate;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public boolean isProfileSampled() {
      return this.profileSampled;
   }

   public boolean isProfilingEnabled() {
      return this.isProfilingEnabled;
   }

   public boolean isTraceSampled() {
      return this.traceSampled;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("profile_sampled").value(var2, this.profileSampled);
      var1.name("profile_sample_rate").value(var2, this.profileSampleRate);
      var1.name("trace_sampled").value(var2, this.traceSampled);
      var1.name("trace_sample_rate").value(var2, this.traceSampleRate);
      var1.name("profiling_traces_dir_path").value(var2, this.profilingTracesDirPath);
      var1.name("is_profiling_enabled").value(var2, this.isProfilingEnabled);
      var1.name("profiling_traces_hz").value(var2, this.profilingTracesHz);
      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            var3 = (Map)this.unknown.get(var4);
            var1.name(var4);
            var1.value(var2, var3);
         }
      }

      var1.endObject();
   }

   public void setProfileSampleRate(Double var1) {
      this.profileSampleRate = var1;
   }

   public void setProfileSampled(boolean var1) {
      this.profileSampled = var1;
   }

   public void setProfilingEnabled(boolean var1) {
      this.isProfilingEnabled = var1;
   }

   public void setProfilingTracesDirPath(String var1) {
      this.profilingTracesDirPath = var1;
   }

   public void setProfilingTracesHz(int var1) {
      this.profilingTracesHz = var1;
   }

   public void setTraceSampleRate(Double var1) {
      this.traceSampleRate = var1;
   }

   public void setTraceSampled(boolean var1) {
      this.traceSampled = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentryAppStartProfilingOptions> {
      public SentryAppStartProfilingOptions deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         SentryAppStartProfilingOptions var7 = new SentryAppStartProfilingOptions();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -566246656:
                  if (var8.equals("trace_sampled")) {
                     var3 = 0;
                  }
                  break;
               case -450071601:
                  if (var8.equals("profiling_traces_dir_path")) {
                     var3 = 1;
                  }
                  break;
               case -116896685:
                  if (var8.equals("is_profiling_enabled")) {
                     var3 = 2;
                  }
                  break;
               case -69617820:
                  if (var8.equals("profile_sampled")) {
                     var3 = 3;
                  }
                  break;
               case 1583866442:
                  if (var8.equals("profiling_traces_hz")) {
                     var3 = 4;
                  }
                  break;
               case 1653938779:
                  if (var8.equals("trace_sample_rate")) {
                     var3 = 5;
                  }
                  break;
               case 2140552383:
                  if (var8.equals("profile_sample_rate")) {
                     var3 = 6;
                  }
            }

            switch (var3) {
               case 0:
                  Boolean var15 = var1.nextBooleanOrNull();
                  if (var15 != null) {
                     var7.traceSampled = var15;
                  }
                  break;
               case 1:
                  String var14 = var1.nextStringOrNull();
                  if (var14 != null) {
                     var7.profilingTracesDirPath = var14;
                  }
                  break;
               case 2:
                  Boolean var13 = var1.nextBooleanOrNull();
                  if (var13 != null) {
                     var7.isProfilingEnabled = var13;
                  }
                  break;
               case 3:
                  Boolean var12 = var1.nextBooleanOrNull();
                  if (var12 != null) {
                     var7.profileSampled = var12;
                  }
                  break;
               case 4:
                  Integer var11 = var1.nextIntegerOrNull();
                  if (var11 != null) {
                     var7.profilingTracesHz = var11;
                  }
                  break;
               case 5:
                  Double var10 = var1.nextDoubleOrNull();
                  if (var10 != null) {
                     var7.traceSampleRate = var10;
                  }
                  break;
               case 6:
                  Double var9 = var1.nextDoubleOrNull();
                  if (var9 != null) {
                     var7.profileSampleRate = var9;
                  }
                  break;
               default:
                  ConcurrentHashMap var6 = var5;
                  if (var5 == null) {
                     var6 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var6, var8);
                  var5 = var6;
            }
         }

         var7.setUnknown(var5);
         var1.endObject();
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String IS_PROFILING_ENABLED = "is_profiling_enabled";
      public static final String PROFILE_SAMPLED = "profile_sampled";
      public static final String PROFILE_SAMPLE_RATE = "profile_sample_rate";
      public static final String PROFILING_TRACES_DIR_PATH = "profiling_traces_dir_path";
      public static final String PROFILING_TRACES_HZ = "profiling_traces_hz";
      public static final String TRACE_SAMPLED = "trace_sampled";
      public static final String TRACE_SAMPLE_RATE = "trace_sample_rate";
   }
}
