package io.sentry.transport;

public final class CurrentDateProvider implements ICurrentDateProvider {
   private static final ICurrentDateProvider instance = new CurrentDateProvider();

   private CurrentDateProvider() {
   }

   public static ICurrentDateProvider getInstance() {
      return instance;
   }

   @Override
   public final long getCurrentTimeMillis() {
      return System.currentTimeMillis();
   }
}
