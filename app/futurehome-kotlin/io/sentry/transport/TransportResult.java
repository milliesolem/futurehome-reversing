package io.sentry.transport;

public abstract class TransportResult {
   private TransportResult() {
   }

   public static TransportResult error() {
      return error(-1);
   }

   public static TransportResult error(int var0) {
      return new TransportResult.ErrorTransportResult(var0);
   }

   public static TransportResult success() {
      return TransportResult.SuccessTransportResult.INSTANCE;
   }

   public abstract int getResponseCode();

   public abstract boolean isSuccess();

   private static final class ErrorTransportResult extends TransportResult {
      private final int responseCode;

      ErrorTransportResult(int var1) {
         this.responseCode = var1;
      }

      @Override
      public int getResponseCode() {
         return this.responseCode;
      }

      @Override
      public boolean isSuccess() {
         return false;
      }
   }

   private static final class SuccessTransportResult extends TransportResult {
      static final TransportResult.SuccessTransportResult INSTANCE = new TransportResult.SuccessTransportResult();

      @Override
      public int getResponseCode() {
         return -1;
      }

      @Override
      public boolean isSuccess() {
         return true;
      }
   }
}
