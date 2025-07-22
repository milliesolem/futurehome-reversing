package io.sentry;

import io.sentry.protocol.SentryId;
import io.sentry.protocol.TransactionNameSource;
import io.sentry.protocol.User;
import io.sentry.util.SampleRateUtils;
import io.sentry.util.StringUtils;
import j..util.concurrent.ConcurrentHashMap;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;

public final class Baggage {
   static final String CHARSET = "UTF-8";
   static final Integer MAX_BAGGAGE_LIST_MEMBER_COUNT = 64;
   static final Integer MAX_BAGGAGE_STRING_LENGTH = 8192;
   static final String SENTRY_BAGGAGE_PREFIX = "sentry-";
   final Map<String, String> keyValues;
   final ILogger logger;
   private boolean mutable;
   final String thirdPartyHeader;

   public Baggage(Baggage var1) {
      this(var1.keyValues, var1.thirdPartyHeader, var1.mutable, var1.logger);
   }

   public Baggage(ILogger var1) {
      this(new HashMap<>(), null, true, var1);
   }

   public Baggage(Map<String, String> var1, String var2, boolean var3, ILogger var4) {
      this.keyValues = var1;
      this.logger = var4;
      this.mutable = var3;
      this.thirdPartyHeader = var2;
   }

   private static String decode(String var0) throws UnsupportedEncodingException {
      return URLDecoder.decode(var0, "UTF-8");
   }

   private String encode(String var1) throws UnsupportedEncodingException {
      return URLEncoder.encode(var1, "UTF-8").replaceAll("\\+", "%20");
   }

   public static Baggage fromEvent(SentryEvent var0, SentryOptions var1) {
      Baggage var3 = new Baggage(var1.getLogger());
      SpanContext var2 = var0.getContexts().getTrace();
      String var7;
      if (var2 != null) {
         var7 = var2.getTraceId().toString();
      } else {
         var7 = null;
      }

      var3.setTraceId(var7);
      var3.setPublicKey(var1.retrieveParsedDsn().getPublicKey());
      var3.setRelease(var0.getRelease());
      var3.setEnvironment(var0.getEnvironment());
      User var4 = var0.getUser();
      String var5;
      if (var4 != null) {
         var5 = getSegment(var4);
      } else {
         var5 = null;
      }

      var3.setUserSegment(var5);
      var3.setTransaction(var0.getTransaction());
      var3.setSampleRate(null);
      var3.setSampled(null);
      Object var6 = var0.getContexts().get("replay_id");
      if (var6 != null && !var6.toString().equals(SentryId.EMPTY_ID.toString())) {
         var3.setReplayId(var6.toString());
         var0.getContexts().remove("replay_id");
      }

      var3.freeze();
      return var3;
   }

   public static Baggage fromHeader(String var0) {
      return fromHeader(var0, false, HubAdapter.getInstance().getOptions().getLogger());
   }

   public static Baggage fromHeader(String var0, ILogger var1) {
      return fromHeader(var0, false, var1);
   }

   public static Baggage fromHeader(String param0, boolean param1, ILogger param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: new java/util/HashMap
      // 003: dup
      // 004: invokespecial java/util/HashMap.<init> ()V
      // 007: astore 9
      // 009: new java/util/ArrayList
      // 00c: dup
      // 00d: invokespecial java/util/ArrayList.<init> ()V
      // 010: astore 10
      // 012: bipush 1
      // 013: istore 6
      // 015: aload 0
      // 016: ifnull 0eb
      // 019: aload 0
      // 01a: ldc ","
      // 01c: bipush -1
      // 01d: invokevirtual java/lang/String.split (Ljava/lang/String;I)[Ljava/lang/String;
      // 020: astore 8
      // 022: aload 8
      // 024: arraylength
      // 025: istore 4
      // 027: bipush 0
      // 028: istore 3
      // 029: bipush 1
      // 02a: istore 6
      // 02c: iload 6
      // 02e: istore 7
      // 030: iload 3
      // 031: iload 4
      // 033: if_icmpge 0e7
      // 036: aload 8
      // 038: iload 3
      // 039: aaload
      // 03a: astore 11
      // 03c: aload 11
      // 03e: invokevirtual java/lang/String.trim ()Ljava/lang/String;
      // 041: ldc "sentry-"
      // 043: invokevirtual java/lang/String.startsWith (Ljava/lang/String;)Z
      // 046: istore 7
      // 048: iload 7
      // 04a: ifeq 0a0
      // 04d: aload 11
      // 04f: ldc "="
      // 051: invokevirtual java/lang/String.indexOf (Ljava/lang/String;)I
      // 054: istore 5
      // 056: aload 9
      // 058: aload 11
      // 05a: bipush 0
      // 05b: iload 5
      // 05d: invokevirtual java/lang/String.substring (II)Ljava/lang/String;
      // 060: invokevirtual java/lang/String.trim ()Ljava/lang/String;
      // 063: invokestatic io/sentry/Baggage.decode (Ljava/lang/String;)Ljava/lang/String;
      // 066: aload 11
      // 068: iload 5
      // 06a: bipush 1
      // 06b: iadd
      // 06c: invokevirtual java/lang/String.substring (I)Ljava/lang/String;
      // 06f: invokevirtual java/lang/String.trim ()Ljava/lang/String;
      // 072: invokestatic io/sentry/Baggage.decode (Ljava/lang/String;)Ljava/lang/String;
      // 075: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 07a: pop
      // 07b: bipush 0
      // 07c: istore 7
      // 07e: goto 0b9
      // 081: astore 12
      // 083: aload 2
      // 084: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 087: aload 12
      // 089: ldc "Unable to decode baggage key value pair %s"
      // 08b: bipush 1
      // 08c: anewarray 4
      // 08f: dup
      // 090: bipush 0
      // 091: aload 11
      // 093: aastore
      // 094: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // 099: iload 6
      // 09b: istore 7
      // 09d: goto 0b9
      // 0a0: iload 6
      // 0a2: istore 7
      // 0a4: iload 1
      // 0a5: ifeq 0b9
      // 0a8: aload 10
      // 0aa: aload 11
      // 0ac: invokevirtual java/lang/String.trim ()Ljava/lang/String;
      // 0af: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 0b4: pop
      // 0b5: iload 6
      // 0b7: istore 7
      // 0b9: iinc 3 1
      // 0bc: iload 7
      // 0be: istore 6
      // 0c0: goto 02c
      // 0c3: astore 8
      // 0c5: goto 0cd
      // 0c8: astore 8
      // 0ca: bipush 1
      // 0cb: istore 6
      // 0cd: aload 2
      // 0ce: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 0d1: aload 8
      // 0d3: ldc_w "Unable to decode baggage header %s"
      // 0d6: bipush 1
      // 0d7: anewarray 4
      // 0da: dup
      // 0db: bipush 0
      // 0dc: aload 0
      // 0dd: aastore
      // 0de: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // 0e3: iload 6
      // 0e5: istore 7
      // 0e7: iload 7
      // 0e9: istore 6
      // 0eb: aload 10
      // 0ed: invokeinterface java/util/List.isEmpty ()Z 1
      // 0f2: ifeq 0fa
      // 0f5: aconst_null
      // 0f6: astore 0
      // 0f7: goto 102
      // 0fa: ldc ","
      // 0fc: aload 10
      // 0fe: invokestatic io/sentry/util/StringUtils.join (Ljava/lang/CharSequence;Ljava/lang/Iterable;)Ljava/lang/String;
      // 101: astore 0
      // 102: new io/sentry/Baggage
      // 105: dup
      // 106: aload 9
      // 108: aload 0
      // 109: iload 6
      // 10b: aload 2
      // 10c: invokespecial io/sentry/Baggage.<init> (Ljava/util/Map;Ljava/lang/String;ZLio/sentry/ILogger;)V
      // 10f: areturn
   }

   public static Baggage fromHeader(List<String> var0) {
      return fromHeader(var0, false, HubAdapter.getInstance().getOptions().getLogger());
   }

   public static Baggage fromHeader(List<String> var0, ILogger var1) {
      return fromHeader(var0, false, var1);
   }

   public static Baggage fromHeader(List<String> var0, boolean var1, ILogger var2) {
      if (var0 != null) {
         return fromHeader(StringUtils.join(",", var0), var1, var2);
      } else {
         String var3 = (String)null;
         return fromHeader(null, var1, var2);
      }
   }

   @Deprecated
   private static String getSegment(User var0) {
      if (var0.getSegment() != null) {
         return var0.getSegment();
      } else {
         Map var1 = var0.getData();
         return var1 != null ? (String)var1.get("segment") : null;
      }
   }

   private static boolean isHighQualityTransactionName(TransactionNameSource var0) {
      boolean var1;
      if (var0 != null && !TransactionNameSource.URL.equals(var0)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static Double sampleRate(TracesSamplingDecision var0) {
      return var0 == null ? null : var0.getSampleRate();
   }

   private static String sampleRateToString(Double var0) {
      return !SampleRateUtils.isValidTracesSampleRate(var0, false)
         ? null
         : new DecimalFormat("#.################", DecimalFormatSymbols.getInstance(Locale.ROOT)).format(var0);
   }

   private static Boolean sampled(TracesSamplingDecision var0) {
      return var0 == null ? null : var0.getSampled();
   }

   public void freeze() {
      this.mutable = false;
   }

   public String get(String var1) {
      return var1 == null ? null : this.keyValues.get(var1);
   }

   public String getEnvironment() {
      return this.get("sentry-environment");
   }

   public String getPublicKey() {
      return this.get("sentry-public_key");
   }

   public String getRelease() {
      return this.get("sentry-release");
   }

   public String getReplayId() {
      return this.get("sentry-replay_id");
   }

   public String getSampleRate() {
      return this.get("sentry-sample_rate");
   }

   public Double getSampleRateDouble() {
      String var3 = this.getSampleRate();
      if (var3 != null) {
         try {
            double var1 = Double.parseDouble(var3);
            if (SampleRateUtils.isValidTracesSampleRate(var1, false)) {
               return var1;
            }
         } catch (NumberFormatException var4) {
         }
      }

      return null;
   }

   public String getSampled() {
      return this.get("sentry-sampled");
   }

   public String getThirdPartyHeader() {
      return this.thirdPartyHeader;
   }

   public String getTraceId() {
      return this.get("sentry-trace_id");
   }

   public String getTransaction() {
      return this.get("sentry-transaction");
   }

   public Map<String, Object> getUnknown() {
      ConcurrentHashMap var3 = new ConcurrentHashMap();

      for (Entry var4 : this.keyValues.entrySet()) {
         String var2 = (String)var4.getKey();
         String var5 = (String)var4.getValue();
         if (!Baggage.DSCKeys.ALL.contains(var2) && var5 != null) {
            var3.put(var2.replaceFirst("sentry-", ""), var5);
         }
      }

      return var3;
   }

   public String getUserId() {
      return this.get("sentry-user_id");
   }

   @Deprecated
   public String getUserSegment() {
      return this.get("sentry-user_segment");
   }

   public boolean isMutable() {
      return this.mutable;
   }

   public void set(String var1, String var2) {
      if (this.mutable) {
         this.keyValues.put(var1, var2);
      }
   }

   public void setEnvironment(String var1) {
      this.set("sentry-environment", var1);
   }

   public void setPublicKey(String var1) {
      this.set("sentry-public_key", var1);
   }

   public void setRelease(String var1) {
      this.set("sentry-release", var1);
   }

   public void setReplayId(String var1) {
      this.set("sentry-replay_id", var1);
   }

   public void setSampleRate(String var1) {
      this.set("sentry-sample_rate", var1);
   }

   public void setSampled(String var1) {
      this.set("sentry-sampled", var1);
   }

   public void setTraceId(String var1) {
      this.set("sentry-trace_id", var1);
   }

   public void setTransaction(String var1) {
      this.set("sentry-transaction", var1);
   }

   public void setUserId(String var1) {
      this.set("sentry-user_id", var1);
   }

   @Deprecated
   public void setUserSegment(String var1) {
      this.set("sentry-user_segment", var1);
   }

   public void setValuesFromScope(IScope var1, SentryOptions var2) {
      PropagationContext var3 = var1.getPropagationContext();
      User var4 = var1.getUser();
      SentryId var5 = var1.getReplayId();
      this.setTraceId(var3.getTraceId().toString());
      this.setPublicKey(var2.retrieveParsedDsn().getPublicKey());
      this.setRelease(var2.getRelease());
      this.setEnvironment(var2.getEnvironment());
      if (!SentryId.EMPTY_ID.equals(var5)) {
         this.setReplayId(var5.toString());
      }

      String var6;
      if (var4 != null) {
         var6 = getSegment(var4);
      } else {
         var6 = null;
      }

      this.setUserSegment(var6);
      this.setTransaction(null);
      this.setSampleRate(null);
      this.setSampled(null);
   }

   public void setValuesFromTransaction(ITransaction var1, User var2, SentryId var3, SentryOptions var4, TracesSamplingDecision var5) {
      this.setTraceId(var1.getSpanContext().getTraceId().toString());
      this.setPublicKey(var4.retrieveParsedDsn().getPublicKey());
      this.setRelease(var4.getRelease());
      this.setEnvironment(var4.getEnvironment());
      Object var8 = null;
      String var6;
      if (var2 != null) {
         var6 = getSegment(var2);
      } else {
         var6 = null;
      }

      this.setUserSegment(var6);
      String var7 = (String)var8;
      if (isHighQualityTransactionName(var1.getTransactionNameSource())) {
         var7 = var1.getName();
      }

      this.setTransaction(var7);
      if (var3 != null && !SentryId.EMPTY_ID.equals(var3)) {
         this.setReplayId(var3.toString());
      }

      this.setSampleRate(sampleRateToString(sampleRate(var5)));
      this.setSampled(StringUtils.toString(sampled(var5)));
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public String toHeaderString(String var1) {
      StringBuilder var6 = new StringBuilder();
      int var2;
      if (var1 != null && !var1.isEmpty()) {
         var6.append(var1);
         var2 = StringUtils.countOf(var1, ',') + 1;
         var1 = ",";
      } else {
         var1 = "";
         var2 = 0;
      }

      for (String var9 : new TreeSet<>(this.keyValues.keySet())) {
         String var8 = this.keyValues.get(var9);
         if (var8 != null) {
            Integer var10 = MAX_BAGGAGE_LIST_MEMBER_COUNT;
            if (var2 >= var10) {
               this.logger
                  .log(SentryLevel.ERROR, "Not adding baggage value %s as the total number of list members would exceed the maximum of %s.", var9, var10);
            } else {
               String var12;
               try {
                  var12 = this.encode(var9);
               } catch (Throwable var251) {
                  this.logger.log(SentryLevel.ERROR, var251, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }

               try {
                  var254 = this.encode(var8);
               } catch (Throwable var250) {
                  this.logger.log(SentryLevel.ERROR, var250, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }

               StringBuilder var11;
               try {
                  var11 = new StringBuilder;
               } catch (Throwable var249) {
                  this.logger.log(SentryLevel.ERROR, var249, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }

               try {
                  var11./* $VF: Unable to resugar constructor */<init>();
               } catch (Throwable var248) {
                  this.logger.log(SentryLevel.ERROR, var248, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }

               try {
                  var11.append(var1);
               } catch (Throwable var247) {
                  this.logger.log(SentryLevel.ERROR, var247, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }

               try {
                  var11.append(var12);
               } catch (Throwable var246) {
                  this.logger.log(SentryLevel.ERROR, var246, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }

               try {
                  var11.append("=");
               } catch (Throwable var245) {
                  this.logger.log(SentryLevel.ERROR, var245, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }

               try {
                  var11.append(var254);
               } catch (Throwable var244) {
                  this.logger.log(SentryLevel.ERROR, var244, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }

               try {
                  var256 = var11.toString();
               } catch (Throwable var243) {
                  this.logger.log(SentryLevel.ERROR, var243, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }

               int var5;
               try {
                  var5 = var256.length();
               } catch (Throwable var242) {
                  this.logger.log(SentryLevel.ERROR, var242, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }

               int var4;
               try {
                  var4 = var6.length();
               } catch (Throwable var241) {
                  this.logger.log(SentryLevel.ERROR, var241, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }

               try {
                  var10 = MAX_BAGGAGE_STRING_LENGTH;
               } catch (Throwable var240) {
                  this.logger.log(SentryLevel.ERROR, var240, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }

               label1225: {
                  try {
                     if (var4 + var5 > var10) {
                        break label1225;
                     }
                  } catch (Throwable var252) {
                     this.logger.log(SentryLevel.ERROR, var252, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                     var2 = var2;
                     continue;
                  }

                  var2++;

                  try {
                     var6.append(var256);
                  } catch (Throwable var239) {
                     this.logger.log(SentryLevel.ERROR, var239, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                     var2 = var2;
                     continue;
                  }

                  var1 = ",";
                  continue;
               }

               try {
                  this.logger
                     .log(SentryLevel.ERROR, "Not adding baggage value %s as the total header value length would exceed the maximum of %s.", var9, var10);
               } catch (Throwable var238) {
                  this.logger.log(SentryLevel.ERROR, var238, "Unable to encode baggage key value pair (key=%s,value=%s).", var9, var8);
                  var2 = var2;
                  continue;
               }
            }
         }
      }

      return var6.toString();
   }

   public TraceContext toTraceContext() {
      String var3 = this.getTraceId();
      String var1 = this.getReplayId();
      String var2 = this.getPublicKey();
      if (var3 != null && var2 != null) {
         SentryId var7 = new SentryId(var3);
         String var10 = this.getRelease();
         String var9 = this.getEnvironment();
         var3 = this.getUserId();
         String var4 = this.getUserSegment();
         String var6 = this.getTransaction();
         String var8 = this.getSampleRate();
         String var5 = this.getSampled();
         SentryId var11;
         if (var1 == null) {
            var11 = null;
         } else {
            var11 = new SentryId(var1);
         }

         TraceContext var12 = new TraceContext(var7, var2, var10, var9, var3, var4, var6, var8, var5, var11);
         var12.setUnknown(this.getUnknown());
         return var12;
      } else {
         return null;
      }
   }

   public static final class DSCKeys {
      public static final List<String> ALL = Arrays.asList(
         "sentry-trace_id",
         "sentry-public_key",
         "sentry-release",
         "sentry-user_id",
         "sentry-environment",
         "sentry-user_segment",
         "sentry-transaction",
         "sentry-sample_rate",
         "sentry-sampled",
         "sentry-replay_id"
      );
      public static final String ENVIRONMENT = "sentry-environment";
      public static final String PUBLIC_KEY = "sentry-public_key";
      public static final String RELEASE = "sentry-release";
      public static final String REPLAY_ID = "sentry-replay_id";
      public static final String SAMPLED = "sentry-sampled";
      public static final String SAMPLE_RATE = "sentry-sample_rate";
      public static final String TRACE_ID = "sentry-trace_id";
      public static final String TRANSACTION = "sentry-transaction";
      public static final String USER_ID = "sentry-user_id";
      public static final String USER_SEGMENT = "sentry-user_segment";
   }
}
