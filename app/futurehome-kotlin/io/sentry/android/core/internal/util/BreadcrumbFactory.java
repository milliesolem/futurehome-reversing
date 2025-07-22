package io.sentry.android.core.internal.util;

import io.sentry.Breadcrumb;
import io.sentry.SentryLevel;

public class BreadcrumbFactory {
   public static Breadcrumb forSession(String var0) {
      Breadcrumb var1 = new Breadcrumb();
      var1.setType("session");
      var1.setData("state", var0);
      var1.setCategory("app.lifecycle");
      var1.setLevel(SentryLevel.INFO);
      return var1;
   }
}
