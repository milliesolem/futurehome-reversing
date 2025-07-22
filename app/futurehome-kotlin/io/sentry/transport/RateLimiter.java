package io.sentry.transport;

import io.sentry.DataCategory;
import io.sentry.Hint;
import io.sentry.SentryEnvelope;
import io.sentry.SentryEnvelopeItem;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.clientreport.DiscardReason;
import io.sentry.hints.Retryable;
import io.sentry.hints.SubmissionResult;
import io.sentry.util.CollectionUtils;
import io.sentry.util.HintUtils;
import io.sentry.util.StringUtils;
import j..util.concurrent.ConcurrentHashMap;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

public final class RateLimiter implements Closeable {
   private static final int HTTP_RETRY_AFTER_DEFAULT_DELAY_MILLIS = 60000;
   private final ICurrentDateProvider currentDateProvider;
   private final SentryOptions options;
   private final List<RateLimiter.IRateLimitObserver> rateLimitObservers;
   private final Map<DataCategory, Date> sentryRetryAfterLimit = new ConcurrentHashMap();
   private Timer timer;
   private final Object timerLock;

   public RateLimiter(SentryOptions var1) {
      this(CurrentDateProvider.getInstance(), var1);
   }

   public RateLimiter(ICurrentDateProvider var1, SentryOptions var2) {
      this.rateLimitObservers = new CopyOnWriteArrayList<>();
      this.timer = null;
      this.timerLock = new Object();
      this.currentDateProvider = var1;
      this.options = var2;
   }

   private void applyRetryAfterOnlyIfLonger(DataCategory param1, Date param2) {
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
      // 01: getfield io/sentry/transport/RateLimiter.sentryRetryAfterLimit Ljava/util/Map;
      // 04: aload 1
      // 05: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0a: checkcast java/util/Date
      // 0d: astore 3
      // 0e: aload 3
      // 0f: ifnull 1a
      // 12: aload 2
      // 13: aload 3
      // 14: invokevirtual java/util/Date.after (Ljava/util/Date;)Z
      // 17: ifeq 5e
      // 1a: aload 0
      // 1b: getfield io/sentry/transport/RateLimiter.sentryRetryAfterLimit Ljava/util/Map;
      // 1e: aload 1
      // 1f: aload 2
      // 20: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 25: pop
      // 26: aload 0
      // 27: invokespecial io/sentry/transport/RateLimiter.notifyRateLimitObservers ()V
      // 2a: aload 0
      // 2b: getfield io/sentry/transport/RateLimiter.timerLock Ljava/lang/Object;
      // 2e: astore 1
      // 2f: aload 1
      // 30: monitorenter
      // 31: aload 0
      // 32: getfield io/sentry/transport/RateLimiter.timer Ljava/util/Timer;
      // 35: ifnonnull 46
      // 38: new java/util/Timer
      // 3b: astore 3
      // 3c: aload 3
      // 3d: bipush 1
      // 3e: invokespecial java/util/Timer.<init> (Z)V
      // 41: aload 0
      // 42: aload 3
      // 43: putfield io/sentry/transport/RateLimiter.timer Ljava/util/Timer;
      // 46: aload 0
      // 47: getfield io/sentry/transport/RateLimiter.timer Ljava/util/Timer;
      // 4a: astore 4
      // 4c: new io/sentry/transport/RateLimiter$1
      // 4f: astore 3
      // 50: aload 3
      // 51: aload 0
      // 52: invokespecial io/sentry/transport/RateLimiter$1.<init> (Lio/sentry/transport/RateLimiter;)V
      // 55: aload 4
      // 57: aload 3
      // 58: aload 2
      // 59: invokevirtual java/util/Timer.schedule (Ljava/util/TimerTask;Ljava/util/Date;)V
      // 5c: aload 1
      // 5d: monitorexit
      // 5e: return
      // 5f: astore 2
      // 60: aload 1
      // 61: monitorexit
      // 62: aload 2
      // 63: athrow
   }

   private DataCategory getCategoryFromItemType(String var1) {
      var1.hashCode();
      int var3 = var1.hashCode();
      byte var2 = -1;
      switch (var3) {
         case -1963501277:
            if (var1.equals("attachment")) {
               var2 = 0;
            }
            break;
         case -1639516637:
            if (var1.equals("replay_video")) {
               var2 = 1;
            }
            break;
         case -892481627:
            if (var1.equals("statsd")) {
               var2 = 2;
            }
            break;
         case -309425751:
            if (var1.equals("profile")) {
               var2 = 3;
            }
            break;
         case 96891546:
            if (var1.equals("event")) {
               var2 = 4;
            }
            break;
         case 1536888764:
            if (var1.equals("check_in")) {
               var2 = 5;
            }
            break;
         case 1984987798:
            if (var1.equals("session")) {
               var2 = 6;
            }
            break;
         case 2141246174:
            if (var1.equals("transaction")) {
               var2 = 7;
            }
      }

      switch (var2) {
         case 0:
            return DataCategory.Attachment;
         case 1:
            return DataCategory.Replay;
         case 2:
            return DataCategory.MetricBucket;
         case 3:
            return DataCategory.Profile;
         case 4:
            return DataCategory.Error;
         case 5:
            return DataCategory.Monitor;
         case 6:
            return DataCategory.Session;
         case 7:
            return DataCategory.Transaction;
         default:
            return DataCategory.Unknown;
      }
   }

   private boolean isRetryAfter(String var1) {
      return this.isActiveForCategory(this.getCategoryFromItemType(var1));
   }

   private static void markHintWhenSendingFailed(Hint var0, boolean var1) {
      HintUtils.runIfHasType(var0, SubmissionResult.class, new RateLimiter$$ExternalSyntheticLambda0());
      HintUtils.runIfHasType(var0, Retryable.class, new RateLimiter$$ExternalSyntheticLambda1(var1));
   }

   private void notifyRateLimitObservers() {
      Iterator var1 = this.rateLimitObservers.iterator();

      while (var1.hasNext()) {
         ((RateLimiter.IRateLimitObserver)var1.next()).onRateLimitChanged(this);
      }
   }

   private long parseRetryAfterOrDefault(String var1) {
      if (var1 != null) {
         double var2;
         try {
            var2 = Double.parseDouble(var1);
         } catch (NumberFormatException var6) {
            return 60000L;
         }

         return (long)(var2 * 1000.0);
      } else {
         return 60000L;
      }
   }

   public void addRateLimitObserver(RateLimiter.IRateLimitObserver var1) {
      this.rateLimitObservers.add(var1);
   }

   @Override
   public void close() throws IOException {
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
      // 01: getfield io/sentry/transport/RateLimiter.timerLock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/transport/RateLimiter.timer Ljava/util/Timer;
      // 0b: astore 2
      // 0c: aload 2
      // 0d: ifnull 19
      // 10: aload 2
      // 11: invokevirtual java/util/Timer.cancel ()V
      // 14: aload 0
      // 15: aconst_null
      // 16: putfield io/sentry/transport/RateLimiter.timer Ljava/util/Timer;
      // 19: aload 1
      // 1a: monitorexit
      // 1b: aload 0
      // 1c: getfield io/sentry/transport/RateLimiter.rateLimitObservers Ljava/util/List;
      // 1f: invokeinterface java/util/List.clear ()V 1
      // 24: return
      // 25: astore 2
      // 26: aload 1
      // 27: monitorexit
      // 28: aload 2
      // 29: athrow
   }

   public SentryEnvelope filter(SentryEnvelope var1, Hint var2) {
      Iterator var5 = var1.getItems().iterator();
      ArrayList var3 = null;

      while (var5.hasNext()) {
         SentryEnvelopeItem var6 = (SentryEnvelopeItem)var5.next();
         if (this.isRetryAfter(var6.getHeader().getType().getItemType())) {
            ArrayList var4 = var3;
            if (var3 == null) {
               var4 = new ArrayList();
            }

            var4.add(var6);
            this.options.getClientReportRecorder().recordLostEnvelopeItem(DiscardReason.RATELIMIT_BACKOFF, var6);
            var3 = var4;
         }
      }

      if (var3 != null) {
         this.options.getLogger().log(SentryLevel.INFO, "%d items will be dropped due rate limiting.", var3.size());
         ArrayList var9 = new ArrayList();

         for (SentryEnvelopeItem var8 : var1.getItems()) {
            if (!var3.contains(var8)) {
               var9.add(var8);
            }
         }

         if (var9.isEmpty()) {
            this.options.getLogger().log(SentryLevel.INFO, "Envelope discarded due all items rate limited.");
            markHintWhenSendingFailed(var2, false);
            return null;
         } else {
            return new SentryEnvelope(var1.getHeader(), var9);
         }
      } else {
         return var1;
      }
   }

   public boolean isActiveForCategory(DataCategory var1) {
      Date var2 = new Date(this.currentDateProvider.getCurrentTimeMillis());
      Date var3 = this.sentryRetryAfterLimit.get(DataCategory.All);
      if (var3 != null && !var2.after(var3)) {
         return true;
      } else if (DataCategory.Unknown.equals(var1)) {
         return false;
      } else {
         Date var4 = this.sentryRetryAfterLimit.get(var1);
         return var4 != null ? var2.after(var4) ^ true : false;
      }
   }

   public boolean isAnyRateLimitActive() {
      Date var2 = new Date(this.currentDateProvider.getCurrentTimeMillis());

      for (DataCategory var3 : this.sentryRetryAfterLimit.keySet()) {
         Date var4 = this.sentryRetryAfterLimit.get(var3);
         if (var4 != null && !var2.after(var4)) {
            return true;
         }
      }

      return false;
   }

   public void removeRateLimitObserver(RateLimiter.IRateLimitObserver var1) {
      this.rateLimitObservers.remove(var1);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public void updateRetryAfterLimits(String var1, String var2, int var3) {
      if (var1 != null) {
         String[] var19 = var1.split(",", -1);
         int var5 = var19.length;

         for (int var24 = 0; var24 < var5; var24++) {
            String[] var10 = var19[var24].replace(" ", "").split(":", -1);
            String var9;
            if (var10.length > 4) {
               var9 = var10[4];
            } else {
               var9 = null;
            }

            String[] var21 = var19;
            if (var10.length > 0) {
               long var7 = this.parseRetryAfterOrDefault(var10[0]);
               var21 = var19;
               if (var10.length > 1) {
                  var2 = var10[1];
                  Date var11 = new Date(this.currentDateProvider.getCurrentTimeMillis() + var7);
                  if (var2 == null || var2.isEmpty()) {
                     this.applyRetryAfterOnlyIfLonger(DataCategory.All, var11);
                     continue;
                  }

                  String[] var12 = var2.split(";", -1);
                  int var6 = var12.length;
                  int var4 = 0;

                  while (true) {
                     var21 = var19;
                     if (var4 >= var6) {
                        break;
                     }

                     String var13 = var12[var4];
                     DataCategory var23 = DataCategory.Unknown;

                     label73: {
                        label72: {
                           try {
                              var10 = StringUtils.camelCase(var13);
                           } catch (IllegalArgumentException var18) {
                              var26 = var18;
                              break label72;
                           }

                           label69:
                           if (var10 != null) {
                              try {
                                 var28 = DataCategory.valueOf(var10);
                              } catch (IllegalArgumentException var15) {
                                 var26 = var15;
                                 break label69;
                              }

                              var23 = var28;
                              break label73;
                           } else {
                              label68: {
                                 SentryLevel var14;
                                 try {
                                    var29 = this.options.getLogger();
                                    var14 = SentryLevel.ERROR;
                                 } catch (IllegalArgumentException var17) {
                                    var26 = var17;
                                    break label68;
                                 }

                                 try {
                                    var29.log(var14, "Couldn't capitalize: %s", var13);
                                    break label73;
                                 } catch (IllegalArgumentException var16) {
                                    var26 = var16;
                                 }
                              }
                           }
                        }

                        this.options.getLogger().log(SentryLevel.INFO, var26, "Unknown category: %s", var13);
                     }

                     label83:
                     if (!DataCategory.Unknown.equals(var23)) {
                        if (DataCategory.MetricBucket.equals(var23) && var9 != null && !var9.equals("")) {
                           var10 = var9.split(";", -1);
                           if (var10.length > 0 && !CollectionUtils.contains(var10, "custom")) {
                              break label83;
                           }
                        }

                        this.applyRetryAfterOnlyIfLonger(var23, var11);
                     }

                     var4++;
                  }
               }
            }

            var19 = var21;
         }
      } else if (var3 == 429) {
         long var25 = this.parseRetryAfterOrDefault(var2);
         Date var20 = new Date(this.currentDateProvider.getCurrentTimeMillis() + var25);
         this.applyRetryAfterOnlyIfLonger(DataCategory.All, var20);
      }
   }

   public interface IRateLimitObserver {
      void onRateLimitChanged(RateLimiter var1);
   }
}
