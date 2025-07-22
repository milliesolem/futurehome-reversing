package com.polidea.rxandroidble2.internal.util;

import android.os.ParcelUuid;
import com.polidea.rxandroidble2.scan.ScanRecord;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Deprecated
public class UUIDUtil {
   public static final ParcelUuid BASE_UUID = new ParcelUuid(ScanRecordParser.BASE_UUID);
   public static final int UUID_BYTES_128_BIT = 16;
   public static final int UUID_BYTES_16_BIT = 2;
   public static final int UUID_BYTES_32_BIT = 4;
   private final ScanRecordParser parser = new ScanRecordParser();

   public List<UUID> extractUUIDs(byte[] var1) {
      return this.parser.extractUUIDs(var1);
   }

   public ScanRecord parseFromBytes(byte[] var1) {
      return this.parser.parseFromBytes(var1);
   }

   public Set<UUID> toDistinctSet(UUID[] var1) {
      UUID[] var2 = var1;
      if (var1 == null) {
         var2 = new UUID[0];
      }

      return new HashSet<>(Arrays.asList(var2));
   }
}
