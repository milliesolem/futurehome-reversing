package com.polidea.rxandroidble2.helpers;

import com.polidea.rxandroidble2.internal.util.ScanRecordParser;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AdvertisedServiceUUIDExtractor {
   private final ScanRecordParser parser = new ScanRecordParser();

   public List<UUID> extractUUIDs(byte[] var1) {
      return this.parser.extractUUIDs(var1);
   }

   public Set<UUID> toDistinctSet(UUID[] var1) {
      UUID[] var2 = var1;
      if (var1 == null) {
         var2 = new UUID[0];
      }

      return new HashSet<>(Arrays.asList(var2));
   }
}
