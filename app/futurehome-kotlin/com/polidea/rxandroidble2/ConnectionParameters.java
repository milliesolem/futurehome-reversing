package com.polidea.rxandroidble2;

public interface ConnectionParameters {
   int getConnectionInterval();

   int getSlaveLatency();

   int getSupervisionTimeout();
}
