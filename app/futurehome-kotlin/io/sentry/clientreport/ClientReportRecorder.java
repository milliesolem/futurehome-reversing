package io.sentry.clientreport;

import io.sentry.DataCategory;
import io.sentry.DateUtils;
import io.sentry.SentryEnvelope;
import io.sentry.SentryEnvelopeItem;
import io.sentry.SentryItemType;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public final class ClientReportRecorder implements IClientReportRecorder {
   private final SentryOptions options;
   private final IClientReportStorage storage;

   public ClientReportRecorder(SentryOptions var1) {
      this.options = var1;
      this.storage = new AtomicClientReportStorage();
   }

   private DataCategory categoryFromItemType(SentryItemType var1) {
      if (SentryItemType.Event.equals(var1)) {
         return DataCategory.Error;
      } else if (SentryItemType.Session.equals(var1)) {
         return DataCategory.Session;
      } else if (SentryItemType.Transaction.equals(var1)) {
         return DataCategory.Transaction;
      } else if (SentryItemType.UserFeedback.equals(var1)) {
         return DataCategory.UserReport;
      } else if (SentryItemType.Profile.equals(var1)) {
         return DataCategory.Profile;
      } else if (SentryItemType.Statsd.equals(var1)) {
         return DataCategory.MetricBucket;
      } else if (SentryItemType.Attachment.equals(var1)) {
         return DataCategory.Attachment;
      } else if (SentryItemType.CheckIn.equals(var1)) {
         return DataCategory.Monitor;
      } else {
         return SentryItemType.ReplayVideo.equals(var1) ? DataCategory.Replay : DataCategory.Default;
      }
   }

   private void recordLostEventInternal(String var1, String var2, Long var3) {
      ClientReportKey var4 = new ClientReportKey(var1, var2);
      this.storage.addCount(var4, var3);
   }

   private void restoreCountsFromClientReport(ClientReport var1) {
      if (var1 != null) {
         for (DiscardedEvent var2 : var1.getDiscardedEvents()) {
            this.recordLostEventInternal(var2.getReason(), var2.getCategory(), var2.getQuantity());
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public SentryEnvelope attachReportToEnvelope(SentryEnvelope var1) {
      ClientReport var4 = this.resetCountsAndGenerateClientReport();
      if (var4 == null) {
         return var1;
      } else {
         Iterator var2;
         ArrayList var3;
         try {
            this.options.getLogger().log(SentryLevel.DEBUG, "Attaching client report to envelope.");
            var3 = new ArrayList();
            var2 = var1.getItems().iterator();
         } catch (Throwable var15) {
            this.options.getLogger().log(SentryLevel.ERROR, var15, "Unable to attach client report to envelope.");
            return var1;
         }

         while (true) {
            try {
               if (!var2.hasNext()) {
                  break;
               }

               var3.add((SentryEnvelopeItem)var2.next());
            } catch (Throwable var16) {
               this.options.getLogger().log(SentryLevel.ERROR, var16, "Unable to attach client report to envelope.");
               return var1;
            }
         }

         try {
            var3.add(SentryEnvelopeItem.fromClientReport(this.options.getSerializer(), var4));
            return new SentryEnvelope(var1.getHeader(), var3);
         } catch (Throwable var14) {
            this.options.getLogger().log(SentryLevel.ERROR, var14, "Unable to attach client report to envelope.");
            return var1;
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void recordLostEnvelope(DiscardReason var1, SentryEnvelope var2) {
      if (var2 != null) {
         try {
            var9 = var2.getItems().iterator();
         } catch (Throwable var8) {
            this.options.getLogger().log(SentryLevel.ERROR, var8, "Unable to record lost envelope.");
            return;
         }

         while (true) {
            try {
               if (!var9.hasNext()) {
                  break;
               }

               this.recordLostEnvelopeItem(var1, (SentryEnvelopeItem)var9.next());
            } catch (Throwable var7) {
               this.options.getLogger().log(SentryLevel.ERROR, var7, "Unable to record lost envelope.");
               break;
            }
         }
      }
   }

   @Override
   public void recordLostEnvelopeItem(DiscardReason param1, SentryEnvelopeItem param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 2
      // 01: ifnonnull 05
      // 04: return
      // 05: aload 2
      // 06: invokevirtual io/sentry/SentryEnvelopeItem.getHeader ()Lio/sentry/SentryEnvelopeItemHeader;
      // 09: invokevirtual io/sentry/SentryEnvelopeItemHeader.getType ()Lio/sentry/SentryItemType;
      // 0c: astore 4
      // 0e: getstatic io/sentry/SentryItemType.ClientReport Lio/sentry/SentryItemType;
      // 11: aload 4
      // 13: invokevirtual io/sentry/SentryItemType.equals (Ljava/lang/Object;)Z
      // 16: istore 3
      // 17: iload 3
      // 18: ifeq 46
      // 1b: aload 0
      // 1c: aload 2
      // 1d: aload 0
      // 1e: getfield io/sentry/clientreport/ClientReportRecorder.options Lio/sentry/SentryOptions;
      // 21: invokevirtual io/sentry/SentryOptions.getSerializer ()Lio/sentry/ISerializer;
      // 24: invokevirtual io/sentry/SentryEnvelopeItem.getClientReport (Lio/sentry/ISerializer;)Lio/sentry/clientreport/ClientReport;
      // 27: invokespecial io/sentry/clientreport/ClientReportRecorder.restoreCountsFromClientReport (Lio/sentry/clientreport/ClientReport;)V
      // 2a: goto b4
      // 2d: astore 1
      // 2e: aload 0
      // 2f: getfield io/sentry/clientreport/ClientReportRecorder.options Lio/sentry/SentryOptions;
      // 32: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 35: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 38: ldc "Unable to restore counts from previous client report."
      // 3a: bipush 0
      // 3b: anewarray 4
      // 3e: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 43: goto b4
      // 46: aload 0
      // 47: aload 4
      // 49: invokespecial io/sentry/clientreport/ClientReportRecorder.categoryFromItemType (Lio/sentry/SentryItemType;)Lio/sentry/DataCategory;
      // 4c: astore 4
      // 4e: aload 4
      // 50: getstatic io/sentry/DataCategory.Transaction Lio/sentry/DataCategory;
      // 53: invokevirtual io/sentry/DataCategory.equals (Ljava/lang/Object;)Z
      // 56: ifeq 88
      // 59: aload 2
      // 5a: aload 0
      // 5b: getfield io/sentry/clientreport/ClientReportRecorder.options Lio/sentry/SentryOptions;
      // 5e: invokevirtual io/sentry/SentryOptions.getSerializer ()Lio/sentry/ISerializer;
      // 61: invokevirtual io/sentry/SentryEnvelopeItem.getTransaction (Lio/sentry/ISerializer;)Lio/sentry/protocol/SentryTransaction;
      // 64: astore 2
      // 65: aload 2
      // 66: ifnull 88
      // 69: aload 2
      // 6a: invokevirtual io/sentry/protocol/SentryTransaction.getSpans ()Ljava/util/List;
      // 6d: astore 2
      // 6e: aload 0
      // 6f: aload 1
      // 70: invokevirtual io/sentry/clientreport/DiscardReason.getReason ()Ljava/lang/String;
      // 73: getstatic io/sentry/DataCategory.Span Lio/sentry/DataCategory;
      // 76: invokevirtual io/sentry/DataCategory.getCategory ()Ljava/lang/String;
      // 79: aload 2
      // 7a: invokeinterface java/util/List.size ()I 1
      // 7f: i2l
      // 80: lconst_1
      // 81: ladd
      // 82: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 85: invokespecial io/sentry/clientreport/ClientReportRecorder.recordLostEventInternal (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;)V
      // 88: aload 0
      // 89: aload 1
      // 8a: invokevirtual io/sentry/clientreport/DiscardReason.getReason ()Ljava/lang/String;
      // 8d: aload 4
      // 8f: invokevirtual io/sentry/DataCategory.getCategory ()Ljava/lang/String;
      // 92: lconst_1
      // 93: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 96: invokespecial io/sentry/clientreport/ClientReportRecorder.recordLostEventInternal (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;)V
      // 99: goto b4
      // 9c: astore 1
      // 9d: aload 0
      // 9e: getfield io/sentry/clientreport/ClientReportRecorder.options Lio/sentry/SentryOptions;
      // a1: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // a4: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // a7: aload 1
      // a8: ldc_w "Unable to record lost envelope item."
      // ab: bipush 0
      // ac: anewarray 4
      // af: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // b4: return
   }

   @Override
   public void recordLostEvent(DiscardReason var1, DataCategory var2) {
      this.recordLostEvent(var1, var2, 1L);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void recordLostEvent(DiscardReason var1, DataCategory var2, long var3) {
      try {
         this.recordLostEventInternal(var1.getReason(), var2.getCategory(), var3);
      } catch (Throwable var6) {
         this.options.getLogger().log(SentryLevel.ERROR, var6, "Unable to record lost event.");
         return;
      }
   }

   ClientReport resetCountsAndGenerateClientReport() {
      Date var1 = DateUtils.getCurrentDateTime();
      List var2 = this.storage.resetCountsAndGet();
      return var2.isEmpty() ? null : new ClientReport(var1, var2);
   }
}
