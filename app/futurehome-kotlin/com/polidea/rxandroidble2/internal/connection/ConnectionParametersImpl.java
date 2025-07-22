package com.polidea.rxandroidble2.internal.connection;

import com.polidea.rxandroidble2.ConnectionParameters;

public class ConnectionParametersImpl implements ConnectionParameters {
   private final int interval;
   private final int latency;
   private final int timeout;

   ConnectionParametersImpl(int var1, int var2, int var3) {
      this.interval = var1;
      this.latency = var2;
      this.timeout = var3;
   }

   @Override
   public int getConnectionInterval() {
      return this.interval;
   }

   @Override
   public int getSlaveLatency() {
      return this.latency;
   }

   @Override
   public int getSupervisionTimeout() {
      return this.timeout;
   }
}
