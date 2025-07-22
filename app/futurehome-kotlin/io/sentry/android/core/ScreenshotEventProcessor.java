package io.sentry.android.core;

import android.app.Activity;
import io.sentry.Attachment;
import io.sentry.EventProcessor;
import io.sentry.Hint;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import io.sentry.android.core.internal.util.AndroidCurrentDateProvider;
import io.sentry.android.core.internal.util.Debouncer;
import io.sentry.android.core.internal.util.ScreenshotUtils;
import io.sentry.protocol.SentryTransaction;
import io.sentry.util.HintUtils;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.Objects;

public final class ScreenshotEventProcessor implements EventProcessor {
   private static final int DEBOUNCE_MAX_EXECUTIONS = 3;
   private static final long DEBOUNCE_WAIT_TIME_MS = 2000L;
   private final BuildInfoProvider buildInfoProvider;
   private final Debouncer debouncer;
   private final SentryAndroidOptions options;

   public ScreenshotEventProcessor(SentryAndroidOptions var1, BuildInfoProvider var2) {
      this.options = Objects.requireNonNull(var1, "SentryAndroidOptions is required");
      this.buildInfoProvider = Objects.requireNonNull(var2, "BuildInfoProvider is required");
      this.debouncer = new Debouncer(AndroidCurrentDateProvider.getInstance(), 2000L, 3);
      if (var1.isAttachScreenshot()) {
         IntegrationUtils.addIntegrationToSdkVersion("Screenshot");
      }
   }

   @Override
   public SentryEvent process(SentryEvent var1, Hint var2) {
      if (!var1.isErrored()) {
         return var1;
      } else if (!this.options.isAttachScreenshot()) {
         this.options.getLogger().log(SentryLevel.DEBUG, "attachScreenshot is disabled.");
         return var1;
      } else {
         Activity var4 = CurrentActivityHolder.getInstance().getActivity();
         if (var4 != null && !HintUtils.isFromHybridSdk(var2)) {
            boolean var3 = this.debouncer.checkForDebounce();
            SentryAndroidOptions.BeforeCaptureCallback var5 = this.options.getBeforeScreenshotCaptureCallback();
            if (var5 != null) {
               if (!var5.execute(var1, var2, var3)) {
                  return var1;
               }
            } else if (var3) {
               return var1;
            }

            byte[] var6 = ScreenshotUtils.takeScreenshot(var4, this.options.getMainThreadChecker(), this.options.getLogger(), this.buildInfoProvider);
            if (var6 == null) {
               return var1;
            }

            var2.setScreenshot(Attachment.fromScreenshot(var6));
            var2.set("android:activity", var4);
         }

         return var1;
      }
   }

   @Override
   public SentryTransaction process(SentryTransaction var1, Hint var2) {
      return var1;
   }
}
