package com.polidea.rxandroidble2.internal.connection;

class ConstantPayloadSizeLimit implements PayloadSizeLimitProvider {
   private final int limit;

   ConstantPayloadSizeLimit(int var1) {
      this.limit = var1;
   }

   @Override
   public int getPayloadSizeLimit() {
      return this.limit;
   }
}
