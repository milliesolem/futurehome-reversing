package com.polidea.rxandroidble2.internal.scan;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.exceptions.BleScanException;
import io.reactivex.Scheduler;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ScanPreconditionsVerifierApi24 implements ScanPreconditionsVerifier {
   private static final long EXCESSIVE_SCANNING_PERIOD = TimeUnit.SECONDS.toMillis(30L);
   private static final int SCANS_LENGTH = 5;
   private final long[] previousChecks = new long[5];
   private final ScanPreconditionsVerifierApi18 scanPreconditionVerifierApi18;
   private final Scheduler timeScheduler;

   @Inject
   public ScanPreconditionsVerifierApi24(ScanPreconditionsVerifierApi18 var1, @Named("computation") Scheduler var2) {
      this.scanPreconditionVerifierApi18 = var1;
      this.timeScheduler = var2;
   }

   private int getOldestCheckTimestampIndex() {
      long var5 = Long.MAX_VALUE;
      int var2 = -1;
      int var1 = 0;

      while (var1 < 5) {
         long var7 = this.previousChecks[var1];
         long var3 = var5;
         if (var7 < var5) {
            var2 = var1;
            var3 = var7;
         }

         var1++;
         var5 = var3;
      }

      return var2;
   }

   @Override
   public void verify(boolean var1) {
      this.scanPreconditionVerifierApi18.verify(var1);
      int var2 = this.getOldestCheckTimestampIndex();
      long var3 = this.previousChecks[var2];
      long var7 = this.timeScheduler.now(TimeUnit.MILLISECONDS);
      long var5 = EXCESSIVE_SCANNING_PERIOD;
      if (var7 - var3 >= var5) {
         this.previousChecks[var2] = var7;
      } else {
         throw new BleScanException(2147483646, new Date(var3 + var5));
      }
   }
}
