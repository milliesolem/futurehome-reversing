package com.google.protobuf;

import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

public class MapEntryLite<K, V> {
   private static final int KEY_FIELD_NUMBER = 1;
   private static final int VALUE_FIELD_NUMBER = 2;
   private final K key;
   private final MapEntryLite.Metadata<K, V> metadata;
   private final V value;

   private MapEntryLite(MapEntryLite.Metadata<K, V> var1, K var2, V var3) {
      this.metadata = var1;
      this.key = (K)var2;
      this.value = (V)var3;
   }

   private MapEntryLite(WireFormat.FieldType var1, K var2, WireFormat.FieldType var3, V var4) {
      this.metadata = new MapEntryLite.Metadata<>(var1, (K)var2, var3, (V)var4);
      this.key = (K)var2;
      this.value = (V)var4;
   }

   static <K, V> int computeSerializedSize(MapEntryLite.Metadata<K, V> var0, K var1, V var2) {
      return FieldSet.computeElementSize(var0.keyType, 1, var1) + FieldSet.computeElementSize(var0.valueType, 2, var2);
   }

   public static <K, V> MapEntryLite<K, V> newDefaultInstance(WireFormat.FieldType var0, K var1, WireFormat.FieldType var2, V var3) {
      return new MapEntryLite<>(var0, (K)var1, var2, (V)var3);
   }

   static <K, V> Entry<K, V> parseEntry(CodedInputStream var0, MapEntryLite.Metadata<K, V> var1, ExtensionRegistryLite var2) throws IOException {
      Object var5 = var1.defaultKey;
      Object var4 = var1.defaultValue;

      while (true) {
         int var3 = var0.readTag();
         if (var3 == 0) {
            break;
         }

         if (var3 == WireFormat.makeTag(1, var1.keyType.getWireType())) {
            var5 = parseField(var0, var2, var1.keyType, var5);
         } else if (var3 == WireFormat.makeTag(2, var1.valueType.getWireType())) {
            var4 = parseField(var0, var2, var1.valueType, var4);
         } else if (!var0.skipField(var3)) {
            break;
         }
      }

      return new SimpleImmutableEntry<>((K)var5, (V)var4);
   }

   static <T> T parseField(CodedInputStream var0, ExtensionRegistryLite var1, WireFormat.FieldType var2, T var3) throws IOException {
      int var4 = <unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var2.ordinal()];
      if (var4 != 1) {
         if (var4 != 2) {
            if (var4 != 3) {
               return (T)FieldSet.readPrimitiveField(var0, var2, true);
            } else {
               throw new RuntimeException("Groups are not allowed in maps.");
            }
         } else {
            return (T)var0.readEnum();
         }
      } else {
         MessageLite.Builder var5 = ((MessageLite)var3).toBuilder();
         var0.readMessage(var5, var1);
         return (T)var5.buildPartial();
      }
   }

   static <K, V> void writeTo(CodedOutputStream var0, MapEntryLite.Metadata<K, V> var1, K var2, V var3) throws IOException {
      FieldSet.writeElement(var0, var1.keyType, 1, var2);
      FieldSet.writeElement(var0, var1.valueType, 2, var3);
   }

   public int computeMessageSize(int var1, K var2, V var3) {
      return CodedOutputStream.computeTagSize(var1) + CodedOutputStream.computeLengthDelimitedFieldSize(computeSerializedSize(this.metadata, (K)var2, (V)var3));
   }

   public K getKey() {
      return this.key;
   }

   MapEntryLite.Metadata<K, V> getMetadata() {
      return this.metadata;
   }

   public V getValue() {
      return this.value;
   }

   public Entry<K, V> parseEntry(ByteString var1, ExtensionRegistryLite var2) throws IOException {
      return parseEntry(var1.newCodedInput(), this.metadata, var2);
   }

   public void parseInto(MapFieldLite<K, V> var1, CodedInputStream var2, ExtensionRegistryLite var3) throws IOException {
      int var4 = var2.pushLimit(var2.readRawVarint32());
      Object var7 = this.metadata.defaultKey;
      Object var6 = this.metadata.defaultValue;

      while (true) {
         int var5 = var2.readTag();
         if (var5 == 0) {
            break;
         }

         if (var5 == WireFormat.makeTag(1, this.metadata.keyType.getWireType())) {
            var7 = parseField(var2, var3, this.metadata.keyType, var7);
         } else if (var5 == WireFormat.makeTag(2, this.metadata.valueType.getWireType())) {
            var6 = parseField(var2, var3, this.metadata.valueType, var6);
         } else if (!var2.skipField(var5)) {
            break;
         }
      }

      var2.checkLastTagWas(0);
      var2.popLimit(var4);
      var1.put(var7, var6);
   }

   public void serializeTo(CodedOutputStream var1, int var2, K var3, V var4) throws IOException {
      var1.writeTag(var2, 2);
      var1.writeUInt32NoTag(computeSerializedSize(this.metadata, (K)var3, (V)var4));
      writeTo(var1, this.metadata, (K)var3, (V)var4);
   }

   static class Metadata<K, V> {
      public final K defaultKey;
      public final V defaultValue;
      public final WireFormat.FieldType keyType;
      public final WireFormat.FieldType valueType;

      public Metadata(WireFormat.FieldType var1, K var2, WireFormat.FieldType var3, V var4) {
         this.keyType = var1;
         this.defaultKey = (K)var2;
         this.valueType = var3;
         this.defaultValue = (V)var4;
      }
   }
}
