package io.sentry.android.core;

import android.content.Context;
import io.sentry.Integration;

public final class AnrIntegrationFactory {
   public static Integration create(Context var0, BuildInfoProvider var1) {
      return (Integration)(var1.getSdkInfoVersion() >= 30 ? new AnrV2Integration(var0) : new AnrIntegration(var0));
   }
}
