package com.google.protobuf;

import java.util.Map;
import java.util.Map.Entry;

@CheckReturnValue
class MapFieldSchemaLite implements MapFieldSchema {
   private static <K, V> int getSerializedSizeLite(int var0, Object var1, Object var2) {
      MapFieldLite var5 = (MapFieldLite)var1;
      var1 = (MapEntryLite)var2;
      boolean var4 = var5.isEmpty();
      int var3 = 0;
      if (var4) {
         return 0;
      } else {
         for (Entry var8 : var5.entrySet()) {
            var3 += var1.computeMessageSize(var0, var8.getKey(), var8.getValue());
         }

         return var3;
      }
   }

   private static <K, V> MapFieldLite<K, V> mergeFromLite(Object var0, Object var1) {
      MapFieldLite var2 = var0;
      var1 = var1;
      var0 = var2;
      if (!var1.isEmpty()) {
         var0 = var2;
         if (!var2.isMutable()) {
            var0 = var2.mutableCopy();
         }

         var0.mergeFrom(var1);
      }

      return var0;
   }

   @Override
   public Map<?, ?> forMapData(Object var1) {
      return (MapFieldLite)var1;
   }

   @Override
   public MapEntryLite.Metadata<?, ?> forMapMetadata(Object var1) {
      return ((MapEntryLite)var1).getMetadata();
   }

   @Override
   public Map<?, ?> forMutableMapData(Object var1) {
      return (MapFieldLite)var1;
   }

   @Override
   public int getSerializedSize(int var1, Object var2, Object var3) {
      return getSerializedSizeLite(var1, var2, var3);
   }

   @Override
   public boolean isImmutable(Object var1) {
      return ((MapFieldLite)var1).isMutable() ^ true;
   }

   @Override
   public Object mergeFrom(Object var1, Object var2) {
      return mergeFromLite(var1, var2);
   }

   @Override
   public Object newMapField(Object var1) {
      return MapFieldLite.emptyMapField().mutableCopy();
   }

   @Override
   public Object toImmutable(Object var1) {
      ((MapFieldLite)var1).makeImmutable();
      return var1;
   }
}
