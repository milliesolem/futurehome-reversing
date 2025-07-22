package com.google.protobuf;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.Map.Entry;

public final class Internal {
   private static final int DEFAULT_BUFFER_SIZE = 4096;
   public static final byte[] EMPTY_BYTE_ARRAY;
   public static final ByteBuffer EMPTY_BYTE_BUFFER;
   public static final CodedInputStream EMPTY_CODED_INPUT_STREAM;
   static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
   static final Charset US_ASCII = Charset.forName("US-ASCII");
   static final Charset UTF_8 = Charset.forName("UTF-8");

   static {
      byte[] var0 = new byte[0];
      EMPTY_BYTE_ARRAY = var0;
      EMPTY_BYTE_BUFFER = ByteBuffer.wrap(var0);
      EMPTY_CODED_INPUT_STREAM = CodedInputStream.newInstance(var0);
   }

   private Internal() {
   }

   public static byte[] byteArrayDefaultValue(String var0) {
      return var0.getBytes(ISO_8859_1);
   }

   public static ByteBuffer byteBufferDefaultValue(String var0) {
      return ByteBuffer.wrap(byteArrayDefaultValue(var0));
   }

   public static ByteString bytesDefaultValue(String var0) {
      return ByteString.copyFrom(var0.getBytes(ISO_8859_1));
   }

   static <T> T checkNotNull(T var0) {
      var0.getClass();
      return (T)var0;
   }

   static <T> T checkNotNull(T var0, String var1) {
      if (var0 != null) {
         return (T)var0;
      } else {
         throw new NullPointerException(var1);
      }
   }

   public static ByteBuffer copyByteBuffer(ByteBuffer var0) {
      var0 = var0.duplicate();
      ((Buffer)var0).clear();
      ByteBuffer var1 = ByteBuffer.allocate(var0.capacity());
      var1.put(var0);
      ((Buffer)var1).clear();
      return var1;
   }

   public static boolean equals(List<byte[]> var0, List<byte[]> var1) {
      if (var0.size() != var1.size()) {
         return false;
      } else {
         for (int var2 = 0; var2 < var0.size(); var2++) {
            if (!Arrays.equals((byte[])var0.get(var2), (byte[])var1.get(var2))) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean equalsByteBuffer(ByteBuffer var0, ByteBuffer var1) {
      if (var0.capacity() != var1.capacity()) {
         return false;
      } else {
         var0 = var0.duplicate();
         Java8Compatibility.clear(var0);
         var1 = var1.duplicate();
         Java8Compatibility.clear(var1);
         return var0.equals(var1);
      }
   }

   public static boolean equalsByteBuffer(List<ByteBuffer> var0, List<ByteBuffer> var1) {
      if (var0.size() != var1.size()) {
         return false;
      } else {
         for (int var2 = 0; var2 < var0.size(); var2++) {
            if (!equalsByteBuffer((ByteBuffer)var0.get(var2), (ByteBuffer)var1.get(var2))) {
               return false;
            }
         }

         return true;
      }
   }

   public static <T extends MessageLite> T getDefaultInstance(Class<T> var0) {
      try {
         java.lang.reflect.Method var4 = var0.getMethod("getDefaultInstance", null);
         return (T)var4.invoke(var4, null);
      } catch (Exception var3) {
         StringBuilder var1 = new StringBuilder("Failed to get default instance for ");
         var1.append(var0);
         throw new RuntimeException(var1.toString(), var3);
      }
   }

   public static int hashBoolean(boolean var0) {
      short var1;
      if (var0) {
         var1 = 1231;
      } else {
         var1 = 1237;
      }

      return var1;
   }

   public static int hashCode(List<byte[]> var0) {
      Iterator var2 = var0.iterator();
      int var1 = 1;

      while (var2.hasNext()) {
         var1 = var1 * 31 + hashCode((byte[])var2.next());
      }

      return var1;
   }

   public static int hashCode(byte[] var0) {
      return hashCode(var0, 0, var0.length);
   }

   static int hashCode(byte[] var0, int var1, int var2) {
      var2 = partialHash(var2, var0, var1, var2);
      var1 = var2;
      if (var2 == 0) {
         var1 = 1;
      }

      return var1;
   }

   public static int hashCodeByteBuffer(ByteBuffer var0) {
      boolean var5 = var0.hasArray();
      byte var4 = 1;
      int var1 = 1;
      if (var5) {
         int var10 = partialHash(var0.capacity(), var0.array(), var0.arrayOffset(), var0.capacity());
         if (var10 != 0) {
            var1 = var10;
         }

         return var1;
      } else {
         int var2 = var0.capacity();
         var1 = 4096;
         if (var2 <= 4096) {
            var1 = var0.capacity();
         }

         byte[] var6 = new byte[var1];
         ByteBuffer var7 = var0.duplicate();
         Java8Compatibility.clear(var7);
         var2 = var0.capacity();

         while (var7.remaining() > 0) {
            int var3;
            if (var7.remaining() <= var1) {
               var3 = var7.remaining();
            } else {
               var3 = var1;
            }

            var7.get(var6, 0, var3);
            var2 = partialHash(var2, var6, 0, var3);
         }

         if (var2 == 0) {
            var2 = var4;
         }

         return var2;
      }
   }

   public static int hashCodeByteBuffer(List<ByteBuffer> var0) {
      Iterator var2 = var0.iterator();
      int var1 = 1;

      while (var2.hasNext()) {
         var1 = var1 * 31 + hashCodeByteBuffer((ByteBuffer)var2.next());
      }

      return var1;
   }

   public static int hashEnum(Internal.EnumLite var0) {
      return var0.getNumber();
   }

   public static int hashEnumList(List<? extends Internal.EnumLite> var0) {
      Iterator var2 = var0.iterator();
      int var1 = 1;

      while (var2.hasNext()) {
         var1 = var1 * 31 + hashEnum((Internal.EnumLite)var2.next());
      }

      return var1;
   }

   public static int hashLong(long var0) {
      return (int)(var0 ^ var0 >>> 32);
   }

   public static boolean isValidUtf8(ByteString var0) {
      return var0.isValidUtf8();
   }

   public static boolean isValidUtf8(byte[] var0) {
      return Utf8.isValidUtf8(var0);
   }

   static Object mergeMessage(Object var0, Object var1) {
      return ((MessageLite)var0).toBuilder().mergeFrom((MessageLite)var1).buildPartial();
   }

   static int partialHash(int var0, byte[] var1, int var2, int var3) {
      for (int var4 = var2; var4 < var2 + var3; var4++) {
         var0 = var0 * 31 + var1[var4];
      }

      return var0;
   }

   public static String stringDefaultValue(String var0) {
      return new String(var0.getBytes(ISO_8859_1), UTF_8);
   }

   public static byte[] toByteArray(String var0) {
      return var0.getBytes(UTF_8);
   }

   public static String toStringUtf8(byte[] var0) {
      return new String(var0, UTF_8);
   }

   public interface BooleanList extends Internal.ProtobufList<Boolean> {
      void addBoolean(boolean var1);

      boolean getBoolean(int var1);

      Internal.BooleanList mutableCopyWithCapacity(int var1);

      boolean setBoolean(int var1, boolean var2);
   }

   public interface DoubleList extends Internal.ProtobufList<Double> {
      void addDouble(double var1);

      double getDouble(int var1);

      Internal.DoubleList mutableCopyWithCapacity(int var1);

      double setDouble(int var1, double var2);
   }

   public interface EnumLite {
      int getNumber();
   }

   public interface EnumLiteMap<T extends Internal.EnumLite> {
      T findValueByNumber(int var1);
   }

   public interface EnumVerifier {
      boolean isInRange(int var1);
   }

   public interface FloatList extends Internal.ProtobufList<Float> {
      void addFloat(float var1);

      float getFloat(int var1);

      Internal.FloatList mutableCopyWithCapacity(int var1);

      float setFloat(int var1, float var2);
   }

   public interface IntList extends Internal.ProtobufList<Integer> {
      void addInt(int var1);

      int getInt(int var1);

      Internal.IntList mutableCopyWithCapacity(int var1);

      int setInt(int var1, int var2);
   }

   public static class ListAdapter<F, T> extends AbstractList<T> {
      private final Internal.ListAdapter.Converter<F, T> converter;
      private final List<F> fromList;

      public ListAdapter(List<F> var1, Internal.ListAdapter.Converter<F, T> var2) {
         this.fromList = var1;
         this.converter = var2;
      }

      @Override
      public T get(int var1) {
         return this.converter.convert(this.fromList.get(var1));
      }

      @Override
      public int size() {
         return this.fromList.size();
      }

      public interface Converter<F, T> {
         T convert(F var1);
      }
   }

   public interface LongList extends Internal.ProtobufList<Long> {
      void addLong(long var1);

      long getLong(int var1);

      Internal.LongList mutableCopyWithCapacity(int var1);

      long setLong(int var1, long var2);
   }

   public static class MapAdapter<K, V, RealValue> extends AbstractMap<K, V> {
      private final Map<K, RealValue> realMap;
      private final Internal.MapAdapter.Converter<RealValue, V> valueConverter;

      public MapAdapter(Map<K, RealValue> var1, Internal.MapAdapter.Converter<RealValue, V> var2) {
         this.realMap = var1;
         this.valueConverter = var2;
      }

      public static <T extends Internal.EnumLite> Internal.MapAdapter.Converter<Integer, T> newEnumConverter(Internal.EnumLiteMap<T> var0, T var1) {
         return new Internal.MapAdapter.Converter<Integer, T>(var0, var1) {
            final Internal.EnumLiteMap val$enumMap;
            final Internal.EnumLite val$unrecognizedValue;

            {
               this.val$enumMap = var1;
               this.val$unrecognizedValue = var2;
            }

            public Integer doBackward(T var1) {
               return var1.getNumber();
            }

            public T doForward(Integer var1) {
               Internal.EnumLite var2 = this.val$enumMap.findValueByNumber(var1);
               Internal.EnumLite var3 = var2;
               if (var2 == null) {
                  var3 = this.val$unrecognizedValue;
               }

               return (T)var3;
            }
         };
      }

      @Override
      public Set<Entry<K, V>> entrySet() {
         return new Internal.MapAdapter.SetAdapter(this, this.realMap.entrySet());
      }

      @Override
      public V get(Object var1) {
         var1 = this.realMap.get(var1);
         return var1 == null ? null : this.valueConverter.doForward((RealValue)var1);
      }

      @Override
      public V put(K var1, V var2) {
         var1 = this.realMap.put((K)var1, this.valueConverter.doBackward((V)var2));
         return var1 == null ? null : this.valueConverter.doForward((RealValue)var1);
      }

      public interface Converter<A, B> {
         A doBackward(B var1);

         B doForward(A var1);
      }

      private class EntryAdapter implements Entry<K, V> {
         private final Entry<K, RealValue> realEntry;
         final Internal.MapAdapter this$0;

         public EntryAdapter(Entry<K, RealValue> var1, Entry var2) {
            this.this$0 = var1;
            this.realEntry = var2;
         }

         @Override
         public boolean equals(Object var1) {
            boolean var2 = true;
            if (var1 == this) {
               return true;
            } else if (!(var1 instanceof Entry)) {
               return false;
            } else {
               var1 = var1;
               if (!this.getKey().equals(var1.getKey()) || !this.getValue().equals(this.getValue())) {
                  var2 = false;
               }

               return var2;
            }
         }

         @Override
         public K getKey() {
            return this.realEntry.getKey();
         }

         @Override
         public V getValue() {
            return this.this$0.valueConverter.doForward(this.realEntry.getValue());
         }

         @Override
         public int hashCode() {
            return this.realEntry.hashCode();
         }

         @Override
         public V setValue(V var1) {
            var1 = this.realEntry.setValue(this.this$0.valueConverter.doBackward((V)var1));
            return var1 == null ? null : this.this$0.valueConverter.doForward((RealValue)var1);
         }
      }

      private class IteratorAdapter implements Iterator<Entry<K, V>> {
         private final Iterator<Entry<K, RealValue>> realIterator;
         final Internal.MapAdapter this$0;

         public IteratorAdapter(Iterator<Entry<K, RealValue>> var1, Iterator var2) {
            this.this$0 = var1;
            this.realIterator = var2;
         }

         @Override
         public boolean hasNext() {
            return this.realIterator.hasNext();
         }

         public Entry<K, V> next() {
            return this.this$0.new EntryAdapter(this.this$0, this.realIterator.next());
         }

         @Override
         public void remove() {
            this.realIterator.remove();
         }
      }

      private class SetAdapter extends AbstractSet<Entry<K, V>> {
         private final Set<Entry<K, RealValue>> realSet;
         final Internal.MapAdapter this$0;

         public SetAdapter(Set<Entry<K, RealValue>> var1, Set var2) {
            this.this$0 = var1;
            this.realSet = var2;
         }

         @Override
         public Iterator<Entry<K, V>> iterator() {
            return this.this$0.new IteratorAdapter(this.this$0, this.realSet.iterator());
         }

         @Override
         public int size() {
            return this.realSet.size();
         }
      }
   }

   public interface ProtobufList<E> extends List<E>, RandomAccess {
      boolean isModifiable();

      void makeImmutable();

      Internal.ProtobufList<E> mutableCopyWithCapacity(int var1);
   }
}
