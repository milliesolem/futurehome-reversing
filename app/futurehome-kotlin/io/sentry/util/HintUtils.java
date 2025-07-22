package io.sentry.util;

import io.sentry.Hint;
import io.sentry.ILogger;
import io.sentry.hints.ApplyScopeData;
import io.sentry.hints.Backfillable;
import io.sentry.hints.Cached;
import io.sentry.hints.EventDropReason;

public final class HintUtils {
   private HintUtils() {
   }

   public static Hint createWithTypeCheckHint(Object var0) {
      Hint var1 = new Hint();
      setTypeCheckHint(var1, var0);
      return var1;
   }

   public static EventDropReason getEventDropReason(Hint var0) {
      return var0.getAs("sentry:eventDropReason", EventDropReason.class);
   }

   public static Object getSentrySdkHint(Hint var0) {
      return var0.get("sentry:typeCheckHint");
   }

   public static boolean hasType(Hint var0, Class<?> var1) {
      return var1.isInstance(getSentrySdkHint(var0));
   }

   public static boolean isFromHybridSdk(Hint var0) {
      return Boolean.TRUE.equals(var0.getAs("sentry:isFromHybridSdk", Boolean.class));
   }

   public static <T> void runIfDoesNotHaveType(Hint var0, Class<T> var1, HintUtils.SentryNullableConsumer<Object> var2) {
      runIfHasType(var0, var1, new HintUtils$$ExternalSyntheticLambda2(), new HintUtils$$ExternalSyntheticLambda3(var2));
   }

   public static <T> void runIfHasType(Hint var0, Class<T> var1, HintUtils.SentryConsumer<T> var2) {
      runIfHasType(var0, var1, var2, new HintUtils$$ExternalSyntheticLambda0());
   }

   public static <T> void runIfHasType(Hint var0, Class<T> var1, HintUtils.SentryConsumer<T> var2, HintUtils.SentryHintFallback var3) {
      Object var4 = getSentrySdkHint(var0);
      if (hasType(var0, var1) && var4 != null) {
         var2.accept(var4);
      } else {
         var3.accept(var4, var1);
      }
   }

   public static <T> void runIfHasTypeLogIfNot(Hint var0, Class<T> var1, ILogger var2, HintUtils.SentryConsumer<T> var3) {
      runIfHasType(var0, var1, var3, new HintUtils$$ExternalSyntheticLambda1(var2));
   }

   public static void setEventDropReason(Hint var0, EventDropReason var1) {
      var0.set("sentry:eventDropReason", var1);
   }

   public static void setIsFromHybridSdk(Hint var0, String var1) {
      if (var1.startsWith("sentry.javascript") || var1.startsWith("sentry.dart") || var1.startsWith("sentry.dotnet")) {
         var0.set("sentry:isFromHybridSdk", true);
      }
   }

   public static void setTypeCheckHint(Hint var0, Object var1) {
      var0.set("sentry:typeCheckHint", var1);
   }

   public static boolean shouldApplyScopeData(Hint var0) {
      boolean var1;
      if ((hasType(var0, Cached.class) || hasType(var0, Backfillable.class)) && !hasType(var0, ApplyScopeData.class)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   @FunctionalInterface
   public interface SentryConsumer<T> {
      void accept(T var1);
   }

   @FunctionalInterface
   public interface SentryHintFallback {
      void accept(Object var1, Class<?> var2);
   }

   @FunctionalInterface
   public interface SentryNullableConsumer<T> {
      void accept(T var1);
   }
}
