package io.sentry;

import io.sentry.protocol.User;
import io.sentry.util.StringUtils;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public final class Session implements JsonUnknown, JsonSerializable {
   private String abnormalMechanism;
   private final String distinctId;
   private Double duration;
   private final String environment;
   private final AtomicInteger errorCount;
   private Boolean init;
   private final String ipAddress;
   private final String release;
   private Long sequence;
   private final UUID sessionId;
   private final Object sessionLock = new Object();
   private final Date started;
   private Session.State status;
   private Date timestamp;
   private Map<String, Object> unknown;
   private String userAgent;

   public Session(
      Session.State var1,
      Date var2,
      Date var3,
      int var4,
      String var5,
      UUID var6,
      Boolean var7,
      Long var8,
      Double var9,
      String var10,
      String var11,
      String var12,
      String var13,
      String var14
   ) {
      this.status = var1;
      this.started = var2;
      this.timestamp = var3;
      this.errorCount = new AtomicInteger(var4);
      this.distinctId = var5;
      this.sessionId = var6;
      this.init = var7;
      this.sequence = var8;
      this.duration = var9;
      this.ipAddress = var10;
      this.userAgent = var11;
      this.environment = var12;
      this.release = var13;
      this.abnormalMechanism = var14;
   }

   public Session(String var1, User var2, String var3, String var4) {
      Session.State var7 = Session.State.Ok;
      Date var8 = DateUtils.getCurrentDateTime();
      Date var6 = DateUtils.getCurrentDateTime();
      UUID var5 = UUID.randomUUID();
      String var9;
      if (var2 != null) {
         var9 = var2.getIpAddress();
      } else {
         var9 = null;
      }

      this(var7, var8, var6, 0, var1, var5, true, null, null, var9, null, var3, var4, null);
   }

   private double calculateDurationTime(Date var1) {
      return Math.abs(var1.getTime() - this.started.getTime()) / 1000.0;
   }

   private long getSequenceTimestamp(Date var1) {
      long var4 = var1.getTime();
      long var2 = var4;
      if (var4 < 0L) {
         var2 = Math.abs(var4);
      }

      return var2;
   }

   public Session clone() {
      return new Session(
         this.status,
         this.started,
         this.timestamp,
         this.errorCount.get(),
         this.distinctId,
         this.sessionId,
         this.init,
         this.sequence,
         this.duration,
         this.ipAddress,
         this.userAgent,
         this.environment,
         this.release,
         this.abnormalMechanism
      );
   }

   public void end() {
      this.end(DateUtils.getCurrentDateTime());
   }

   public void end(Date param1) {
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
      // 00: aload 0
      // 01: getfield io/sentry/Session.sessionLock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: aconst_null
      // 09: putfield io/sentry/Session.init Ljava/lang/Boolean;
      // 0c: aload 0
      // 0d: getfield io/sentry/Session.status Lio/sentry/Session$State;
      // 10: getstatic io/sentry/Session$State.Ok Lio/sentry/Session$State;
      // 13: if_acmpne 1d
      // 16: aload 0
      // 17: getstatic io/sentry/Session$State.Exited Lio/sentry/Session$State;
      // 1a: putfield io/sentry/Session.status Lio/sentry/Session$State;
      // 1d: aload 1
      // 1e: ifnull 29
      // 21: aload 0
      // 22: aload 1
      // 23: putfield io/sentry/Session.timestamp Ljava/util/Date;
      // 26: goto 30
      // 29: aload 0
      // 2a: invokestatic io/sentry/DateUtils.getCurrentDateTime ()Ljava/util/Date;
      // 2d: putfield io/sentry/Session.timestamp Ljava/util/Date;
      // 30: aload 0
      // 31: getfield io/sentry/Session.timestamp Ljava/util/Date;
      // 34: astore 1
      // 35: aload 1
      // 36: ifnull 54
      // 39: aload 0
      // 3a: aload 0
      // 3b: aload 1
      // 3c: invokespecial io/sentry/Session.calculateDurationTime (Ljava/util/Date;)D
      // 3f: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 42: putfield io/sentry/Session.duration Ljava/lang/Double;
      // 45: aload 0
      // 46: aload 0
      // 47: aload 0
      // 48: getfield io/sentry/Session.timestamp Ljava/util/Date;
      // 4b: invokespecial io/sentry/Session.getSequenceTimestamp (Ljava/util/Date;)J
      // 4e: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 51: putfield io/sentry/Session.sequence Ljava/lang/Long;
      // 54: aload 2
      // 55: monitorexit
      // 56: return
      // 57: astore 1
      // 58: aload 2
      // 59: monitorexit
      // 5a: aload 1
      // 5b: athrow
   }

   public int errorCount() {
      return this.errorCount.get();
   }

   public String getAbnormalMechanism() {
      return this.abnormalMechanism;
   }

   public String getDistinctId() {
      return this.distinctId;
   }

   public Double getDuration() {
      return this.duration;
   }

   public String getEnvironment() {
      return this.environment;
   }

   public Boolean getInit() {
      return this.init;
   }

   public String getIpAddress() {
      return this.ipAddress;
   }

   public String getRelease() {
      return this.release;
   }

   public Long getSequence() {
      return this.sequence;
   }

   public UUID getSessionId() {
      return this.sessionId;
   }

   public Date getStarted() {
      Date var1 = this.started;
      return var1 == null ? null : (Date)var1.clone();
   }

   public Session.State getStatus() {
      return this.status;
   }

   public Date getTimestamp() {
      Date var1 = this.timestamp;
      if (var1 != null) {
         var1 = (Date)var1.clone();
      } else {
         var1 = null;
      }

      return var1;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public String getUserAgent() {
      return this.userAgent;
   }

   public boolean isTerminated() {
      boolean var1;
      if (this.status != Session.State.Ok) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.sessionId != null) {
         var1.name("sid").value(this.sessionId.toString());
      }

      if (this.distinctId != null) {
         var1.name("did").value(this.distinctId);
      }

      if (this.init != null) {
         var1.name("init").value(this.init);
      }

      var1.name("started").value(var2, this.started);
      var1.name("status").value(var2, this.status.name().toLowerCase(Locale.ROOT));
      if (this.sequence != null) {
         var1.name("seq").value(this.sequence);
      }

      var1.name("errors").value((long)this.errorCount.intValue());
      if (this.duration != null) {
         var1.name("duration").value(this.duration);
      }

      if (this.timestamp != null) {
         var1.name("timestamp").value(var2, this.timestamp);
      }

      if (this.abnormalMechanism != null) {
         var1.name("abnormal_mechanism").value(var2, this.abnormalMechanism);
      }

      var1.name("attrs");
      var1.beginObject();
      var1.name("release").value(var2, this.release);
      if (this.environment != null) {
         var1.name("environment").value(var2, this.environment);
      }

      if (this.ipAddress != null) {
         var1.name("ip_address").value(var2, this.ipAddress);
      }

      if (this.userAgent != null) {
         var1.name("user_agent").value(var2, this.userAgent);
      }

      var1.endObject();
      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            Object var5 = this.unknown.get(var4);
            var1.name(var4);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setInitAsTrue() {
      this.init = true;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public boolean update(Session.State var1, String var2, boolean var3) {
      return this.update(var1, var2, var3, null);
   }

   public boolean update(Session.State param1, String param2, boolean param3, String param4) {
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
      // 00: aload 0
      // 01: getfield io/sentry/Session.sessionLock Ljava/lang/Object;
      // 04: astore 7
      // 06: aload 7
      // 08: monitorenter
      // 09: bipush 1
      // 0a: istore 6
      // 0c: aload 1
      // 0d: ifnull 1f
      // 10: aload 0
      // 11: aload 1
      // 12: putfield io/sentry/Session.status Lio/sentry/Session$State;
      // 15: bipush 1
      // 16: istore 5
      // 18: goto 22
      // 1b: astore 1
      // 1c: goto 79
      // 1f: bipush 0
      // 20: istore 5
      // 22: aload 2
      // 23: ifnull 2e
      // 26: aload 0
      // 27: aload 2
      // 28: putfield io/sentry/Session.userAgent Ljava/lang/String;
      // 2b: bipush 1
      // 2c: istore 5
      // 2e: iload 3
      // 2f: ifeq 3e
      // 32: aload 0
      // 33: getfield io/sentry/Session.errorCount Ljava/util/concurrent/atomic/AtomicInteger;
      // 36: bipush 1
      // 37: invokevirtual java/util/concurrent/atomic/AtomicInteger.addAndGet (I)I
      // 3a: pop
      // 3b: bipush 1
      // 3c: istore 5
      // 3e: aload 4
      // 40: ifnull 50
      // 43: aload 0
      // 44: aload 4
      // 46: putfield io/sentry/Session.abnormalMechanism Ljava/lang/String;
      // 49: iload 6
      // 4b: istore 5
      // 4d: goto 50
      // 50: iload 5
      // 52: ifeq 73
      // 55: aload 0
      // 56: aconst_null
      // 57: putfield io/sentry/Session.init Ljava/lang/Boolean;
      // 5a: invokestatic io/sentry/DateUtils.getCurrentDateTime ()Ljava/util/Date;
      // 5d: astore 1
      // 5e: aload 0
      // 5f: aload 1
      // 60: putfield io/sentry/Session.timestamp Ljava/util/Date;
      // 63: aload 1
      // 64: ifnull 73
      // 67: aload 0
      // 68: aload 0
      // 69: aload 1
      // 6a: invokespecial io/sentry/Session.getSequenceTimestamp (Ljava/util/Date;)J
      // 6d: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 70: putfield io/sentry/Session.sequence Ljava/lang/Long;
      // 73: aload 7
      // 75: monitorexit
      // 76: iload 5
      // 78: ireturn
      // 79: aload 7
      // 7b: monitorexit
      // 7c: aload 1
      // 7d: athrow
   }

   public static final class Deserializer implements JsonDeserializer<Session> {
      private Exception missingRequiredFieldException(String var1, ILogger var2) {
         StringBuilder var3 = new StringBuilder("Missing required field \"");
         var3.append(var1);
         var3.append("\"");
         String var5 = var3.toString();
         IllegalStateException var4 = new IllegalStateException(var5);
         var2.log(SentryLevel.ERROR, var5, var4);
         return var4;
      }

      public Session deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         Integer var15 = null;
         Session.State var14 = null;
         Date var13 = null;
         Date var12 = null;
         ConcurrentHashMap var4 = null;
         String var11 = null;
         UUID var10 = null;
         Boolean var9 = null;
         Long var18 = null;
         Double var17 = null;
         String var5 = null;
         String var6 = null;
         String var16 = null;
         String var8 = null;
         String var7 = null;

         while (true) {
            JsonToken var20 = var1.peek();
            JsonToken var19 = JsonToken.NAME;
            if (var20 != var19) {
               if (var14 != null) {
                  if (var13 != null) {
                     if (var15 != null) {
                        if (var8 != null) {
                           Session var30 = new Session(var14, var13, var12, var15, var11, var10, var9, var18, var17, var5, var6, var16, var8, var7);
                           var30.setUnknown(var4);
                           var1.endObject();
                           return var30;
                        }

                        throw this.missingRequiredFieldException("release", var2);
                     }

                     throw this.missingRequiredFieldException("errors", var2);
                  }

                  throw this.missingRequiredFieldException("started", var2);
               }

               throw this.missingRequiredFieldException("status", var2);
            }

            byte var3;
            label127: {
               var41 = var1.nextName();
               var41.hashCode();
               switch (var41.hashCode()) {
                  case -1992012396:
                     if (var41.equals("duration")) {
                        var3 = 0;
                        break label127;
                     }
                     break;
                  case -1897185151:
                     if (var41.equals("started")) {
                        var3 = 1;
                        break label127;
                     }
                     break;
                  case -1294635157:
                     if (var41.equals("errors")) {
                        var3 = 2;
                        break label127;
                     }
                     break;
                  case -892481550:
                     if (var41.equals("status")) {
                        var3 = 3;
                        break label127;
                     }
                     break;
                  case 99455:
                     if (var41.equals("did")) {
                        var3 = 4;
                        break label127;
                     }
                     break;
                  case 113759:
                     if (var41.equals("seq")) {
                        var3 = 5;
                        break label127;
                     }
                     break;
                  case 113870:
                     if (var41.equals("sid")) {
                        var3 = 6;
                        break label127;
                     }
                     break;
                  case 3237136:
                     if (var41.equals("init")) {
                        var3 = 7;
                        break label127;
                     }
                     break;
                  case 55126294:
                     if (var41.equals("timestamp")) {
                        var3 = 8;
                        break label127;
                     }
                     break;
                  case 93152418:
                     if (var41.equals("attrs")) {
                        var3 = 9;
                        break label127;
                     }
                     break;
                  case 213717026:
                     if (var41.equals("abnormal_mechanism")) {
                        var3 = 10;
                        break label127;
                     }
               }

               var3 = -1;
            }

            ConcurrentHashMap var22;
            Boolean var52;
            label166: {
               label178: {
                  label163: {
                     Date var21;
                     String var23;
                     UUID var24;
                     Boolean var25;
                     String var26;
                     Integer var42;
                     Date var49;
                     switch (var3) {
                        case 0:
                           Double var51 = var1.nextDoubleOrNull();
                           var22 = var4;
                           var38 = var10;
                           var37 = var9;
                           var36 = var51;
                           var6 = var5;
                           var5 = var6;
                           var32 = var16;
                           break label178;
                        case 1:
                           var49 = var1.nextDateOrNull(var2);
                           var42 = var15;
                           var21 = var12;
                           var22 = var4;
                           var23 = var11;
                           var24 = var10;
                           var25 = var9;
                           var26 = var7;
                           break;
                        case 2:
                           var42 = var1.nextIntegerOrNull();
                           var49 = var13;
                           var21 = var12;
                           var22 = var4;
                           var23 = var11;
                           var24 = var10;
                           var25 = var9;
                           var26 = var7;
                           break;
                        case 3:
                           String var27 = StringUtils.capitalize(var1.nextStringOrNull());
                           var42 = var15;
                           var49 = var13;
                           var21 = var12;
                           var22 = var4;
                           var23 = var11;
                           var24 = var10;
                           var25 = var9;
                           var26 = var7;
                           if (var27 != null) {
                              var14 = Session.State.valueOf(var27);
                              var42 = var15;
                              var49 = var13;
                              var21 = var12;
                              var22 = var4;
                              var23 = var11;
                              var24 = var10;
                              var25 = var9;
                              var26 = var7;
                           }
                           break;
                        case 4:
                           var23 = var1.nextStringOrNull();
                           var26 = var7;
                           var25 = var9;
                           var24 = var10;
                           var22 = var4;
                           var21 = var12;
                           var49 = var13;
                           var42 = var15;
                           break;
                        case 5:
                           var18 = var1.nextLongOrNull();
                           var47 = var16;
                           var16 = var6;
                           var22 = var4;
                           var50 = var10;
                           var52 = var9;
                           var6 = var5;
                           break label166;
                        case 6:
                           label158: {
                              label157: {
                                 try {
                                    var46 = var1.nextStringOrNull();
                                 } catch (IllegalArgumentException var29) {
                                    var46 = null;
                                    break label157;
                                 }

                                 try {
                                    var24 = UUID.fromString(var46);
                                    break label158;
                                 } catch (IllegalArgumentException var28) {
                                 }
                              }

                              var2.log(SentryLevel.ERROR, "%s sid is not valid.", var46);
                              var42 = var15;
                              var49 = var13;
                              var21 = var12;
                              var22 = var4;
                              var23 = var11;
                              var24 = var10;
                              var25 = var9;
                              var26 = var7;
                              break;
                           }

                           var42 = var15;
                           var49 = var13;
                           var21 = var12;
                           var22 = var4;
                           var23 = var11;
                           var25 = var9;
                           var26 = var7;
                           break;
                        case 7:
                           var25 = var1.nextBooleanOrNull();
                           var42 = var15;
                           var49 = var13;
                           var21 = var12;
                           var22 = var4;
                           var23 = var11;
                           var24 = var10;
                           var26 = var7;
                           break;
                        case 8:
                           var21 = var1.nextDateOrNull(var2);
                           var42 = var15;
                           var49 = var13;
                           var22 = var4;
                           var23 = var11;
                           var24 = var10;
                           var25 = var9;
                           var26 = var7;
                           break;
                        case 9:
                           var1.beginObject();
                           var5 = var6;
                           var6 = var5;

                           while (var1.peek() == JsonToken.NAME) {
                              label138: {
                                 String var44 = var1.nextName();
                                 var44.hashCode();
                                 switch (var44.hashCode()) {
                                    case -85904877:
                                       if (var44.equals("environment")) {
                                          var3 = 0;
                                          break label138;
                                       }
                                       break;
                                    case 1090594823:
                                       if (var44.equals("release")) {
                                          var3 = 1;
                                          break label138;
                                       }
                                       break;
                                    case 1480014044:
                                       if (var44.equals("ip_address")) {
                                          var3 = 2;
                                          break label138;
                                       }
                                       break;
                                    case 1917799825:
                                       if (var44.equals("user_agent")) {
                                          var3 = 3;
                                          break label138;
                                       }
                                 }

                                 var3 = -1;
                              }

                              switch (var3) {
                                 case 0:
                                    var16 = var1.nextStringOrNull();
                                    break;
                                 case 1:
                                    var8 = var1.nextStringOrNull();
                                    break;
                                 case 2:
                                    var6 = var1.nextStringOrNull();
                                    break;
                                 case 3:
                                    var5 = var1.nextStringOrNull();
                                    break;
                                 default:
                                    var1.skipValue();
                              }
                           }

                           var1.endObject();
                           var22 = var4;
                           var38 = var10;
                           var37 = var9;
                           var32 = var16;
                           break label163;
                        case 10:
                           var26 = var1.nextStringOrNull();
                           var42 = var15;
                           var49 = var13;
                           var21 = var12;
                           var22 = var4;
                           var23 = var11;
                           var24 = var10;
                           var25 = var9;
                           break;
                        default:
                           var22 = var4;
                           if (var4 == null) {
                              var22 = new ConcurrentHashMap();
                           }

                           var1.nextUnknown(var2, var22, var41);
                           var42 = var15;
                           var49 = var13;
                           var21 = var12;
                           var23 = var11;
                           var24 = var10;
                           var25 = var9;
                           var26 = var7;
                     }

                     var32 = var16;
                     var6 = var5;
                     var7 = var26;
                     var5 = var6;
                     var37 = var25;
                     var38 = var24;
                     var11 = var23;
                     var12 = var21;
                     var13 = var49;
                     var15 = var42;
                  }

                  var36 = var17;
               }

               var50 = var38;
               var52 = var37;
               var17 = var36;
               var16 = var5;
               var47 = var32;
            }

            var4 = var22;
            var10 = var50;
            var9 = var52;
            var5 = var6;
            var6 = var16;
            var16 = var47;
         }
      }
   }

   public static final class JsonKeys {
      public static final String ABNORMAL_MECHANISM = "abnormal_mechanism";
      public static final String ATTRS = "attrs";
      public static final String DID = "did";
      public static final String DURATION = "duration";
      public static final String ENVIRONMENT = "environment";
      public static final String ERRORS = "errors";
      public static final String INIT = "init";
      public static final String IP_ADDRESS = "ip_address";
      public static final String RELEASE = "release";
      public static final String SEQ = "seq";
      public static final String SID = "sid";
      public static final String STARTED = "started";
      public static final String STATUS = "status";
      public static final String TIMESTAMP = "timestamp";
      public static final String USER_AGENT = "user_agent";
   }

   public static enum State {
      Abnormal,
      Crashed,
      Exited,
      Ok;
      private static final Session.State[] $VALUES = $values();
   }
}
