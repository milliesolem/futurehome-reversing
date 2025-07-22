package com.polidea.rxandroidble2;

import java.util.concurrent.TimeUnit;

public class ConnectionSetup {
   public static final int DEFAULT_OPERATION_TIMEOUT = 30;
   public final boolean autoConnect;
   public final Timeout operationTimeout;
   public final boolean suppressOperationCheck;

   ConnectionSetup(boolean var1, boolean var2, Timeout var3) {
      this.autoConnect = var1;
      this.suppressOperationCheck = var2;
      this.operationTimeout = var3;
   }

   public static class Builder {
      private boolean autoConnect = false;
      private Timeout operationTimeout;
      private boolean suppressOperationCheck = false;

      public Builder() {
         this.operationTimeout = new Timeout(30L, TimeUnit.SECONDS);
      }

      public ConnectionSetup build() {
         return new ConnectionSetup(this.autoConnect, this.suppressOperationCheck, this.operationTimeout);
      }

      public ConnectionSetup.Builder setAutoConnect(boolean var1) {
         this.autoConnect = var1;
         return this;
      }

      public ConnectionSetup.Builder setOperationTimeout(Timeout var1) {
         this.operationTimeout = var1;
         return this;
      }

      public ConnectionSetup.Builder setSuppressIllegalOperationCheck(boolean var1) {
         this.suppressOperationCheck = var1;
         return this;
      }
   }
}
