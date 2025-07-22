package com.google.protobuf;

import java.util.Iterator;
import java.util.Map.Entry;

public class LazyField extends LazyFieldLite {
   private final MessageLite defaultInstance;

   public LazyField(MessageLite var1, ExtensionRegistryLite var2, ByteString var3) {
      super(var2, var3);
      this.defaultInstance = var1;
   }

   @Override
   public boolean containsDefaultInstance() {
      boolean var1;
      if (!super.containsDefaultInstance() && this.value != this.defaultInstance) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   @Override
   public boolean equals(Object var1) {
      return this.getValue().equals(var1);
   }

   public MessageLite getValue() {
      return this.getValue(this.defaultInstance);
   }

   @Override
   public int hashCode() {
      return this.getValue().hashCode();
   }

   @Override
   public String toString() {
      return this.getValue().toString();
   }

   static class LazyEntry<K> implements Entry<K, Object> {
      private Entry<K, LazyField> entry;

      private LazyEntry(Entry<K, LazyField> var1) {
         this.entry = var1;
      }

      public LazyField getField() {
         return this.entry.getValue();
      }

      @Override
      public K getKey() {
         return this.entry.getKey();
      }

      @Override
      public Object getValue() {
         LazyField var1 = this.entry.getValue();
         return var1 == null ? null : var1.getValue();
      }

      @Override
      public Object setValue(Object var1) {
         if (var1 instanceof MessageLite) {
            return this.entry.getValue().setValue((MessageLite)var1);
         } else {
            throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
         }
      }
   }

   static class LazyIterator<K> implements Iterator<Entry<K, Object>> {
      private Iterator<Entry<K, Object>> iterator;

      public LazyIterator(Iterator<Entry<K, Object>> var1) {
         this.iterator = var1;
      }

      @Override
      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      public Entry<K, Object> next() {
         Entry var1 = this.iterator.next();
         return (Entry<K, Object>)(var1.getValue() instanceof LazyField ? new LazyField.LazyEntry<>(var1) : var1);
      }

      @Override
      public void remove() {
         this.iterator.remove();
      }
   }
}
