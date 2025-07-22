package com.google.protobuf;

import java.util.Map;

@CheckReturnValue
interface MapFieldSchema {
   Map<?, ?> forMapData(Object var1);

   MapEntryLite.Metadata<?, ?> forMapMetadata(Object var1);

   Map<?, ?> forMutableMapData(Object var1);

   int getSerializedSize(int var1, Object var2, Object var3);

   boolean isImmutable(Object var1);

   Object mergeFrom(Object var1, Object var2);

   Object newMapField(Object var1);

   Object toImmutable(Object var1);
}
