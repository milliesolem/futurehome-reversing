package io.sentry;

interface UncaughtExceptionHandler {
   java.lang.Thread.UncaughtExceptionHandler getDefaultUncaughtExceptionHandler();

   void setDefaultUncaughtExceptionHandler(java.lang.Thread.UncaughtExceptionHandler var1);

   public static final class Adapter implements UncaughtExceptionHandler {
      private static final UncaughtExceptionHandler.Adapter INSTANCE = new UncaughtExceptionHandler.Adapter();

      private Adapter() {
      }

      static UncaughtExceptionHandler getInstance() {
         return INSTANCE;
      }

      @Override
      public java.lang.Thread.UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
         return Thread.getDefaultUncaughtExceptionHandler();
      }

      @Override
      public void setDefaultUncaughtExceptionHandler(java.lang.Thread.UncaughtExceptionHandler var1) {
         Thread.setDefaultUncaughtExceptionHandler(var1);
      }
   }
}
