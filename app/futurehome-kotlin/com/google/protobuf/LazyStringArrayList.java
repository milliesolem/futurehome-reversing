package com.google.protobuf;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public class LazyStringArrayList extends AbstractProtobufList<String> implements LazyStringList, RandomAccess {
   @Deprecated
   public static final LazyStringList EMPTY;
   private static final LazyStringArrayList EMPTY_LIST;
   private final List<Object> list;

   static {
      LazyStringArrayList var0 = new LazyStringArrayList(false);
      EMPTY_LIST = var0;
      EMPTY = var0;
   }

   public LazyStringArrayList() {
      this(10);
   }

   public LazyStringArrayList(int var1) {
      this(new ArrayList<>(var1));
   }

   public LazyStringArrayList(LazyStringList var1) {
      this.list = new ArrayList<>(var1.size());
      this.addAll(var1);
   }

   private LazyStringArrayList(ArrayList<Object> var1) {
      this.list = var1;
   }

   public LazyStringArrayList(List<String> var1) {
      this(new ArrayList<>(var1));
   }

   private LazyStringArrayList(boolean var1) {
      super(var1);
      this.list = Collections.emptyList();
   }

   private void add(int var1, ByteString var2) {
      this.ensureIsMutable();
      this.list.add(var1, var2);
      this.modCount++;
   }

   private void add(int var1, byte[] var2) {
      this.ensureIsMutable();
      this.list.add(var1, var2);
      this.modCount++;
   }

   private static byte[] asByteArray(Object var0) {
      if (var0 instanceof byte[]) {
         return (byte[])var0;
      } else {
         return var0 instanceof String ? Internal.toByteArray((String)var0) : ((ByteString)var0).toByteArray();
      }
   }

   private static ByteString asByteString(Object var0) {
      if (var0 instanceof ByteString) {
         return (ByteString)var0;
      } else {
         return var0 instanceof String ? ByteString.copyFromUtf8((String)var0) : ByteString.copyFrom((byte[])var0);
      }
   }

   private static String asString(Object var0) {
      if (var0 instanceof String) {
         return (String)var0;
      } else {
         return var0 instanceof ByteString ? ((ByteString)var0).toStringUtf8() : Internal.toStringUtf8((byte[])var0);
      }
   }

   public static LazyStringArrayList emptyList() {
      return EMPTY_LIST;
   }

   private Object setAndReturn(int var1, ByteString var2) {
      this.ensureIsMutable();
      return this.list.set(var1, var2);
   }

   private Object setAndReturn(int var1, byte[] var2) {
      this.ensureIsMutable();
      return this.list.set(var1, var2);
   }

   public void add(int var1, String var2) {
      this.ensureIsMutable();
      this.list.add(var1, var2);
      this.modCount++;
   }

   @Override
   public void add(ByteString var1) {
      this.ensureIsMutable();
      this.list.add(var1);
      this.modCount++;
   }

   @Override
   public void add(byte[] var1) {
      this.ensureIsMutable();
      this.list.add(var1);
      this.modCount++;
   }

   @Override
   public boolean addAll(int var1, Collection<? extends String> var2) {
      this.ensureIsMutable();
      Object var4 = var2;
      if (var2 instanceof LazyStringList) {
         var4 = ((LazyStringList)var2).getUnderlyingElements();
      }

      boolean var3 = this.list.addAll(var1, (Collection<? extends Object>)var4);
      this.modCount++;
      return var3;
   }

   @Override
   public boolean addAll(Collection<? extends String> var1) {
      return this.addAll(this.size(), var1);
   }

   @Override
   public boolean addAllByteArray(Collection<byte[]> var1) {
      this.ensureIsMutable();
      boolean var2 = this.list.addAll(var1);
      this.modCount++;
      return var2;
   }

   @Override
   public boolean addAllByteString(Collection<? extends ByteString> var1) {
      this.ensureIsMutable();
      boolean var2 = this.list.addAll(var1);
      this.modCount++;
      return var2;
   }

   @Override
   public List<byte[]> asByteArrayList() {
      return new LazyStringArrayList.ByteArrayListView(this);
   }

   @Override
   public List<ByteString> asByteStringList() {
      return new LazyStringArrayList.ByteStringListView(this);
   }

   @Override
   public void clear() {
      this.ensureIsMutable();
      this.list.clear();
      this.modCount++;
   }

   public String get(int var1) {
      String var2 = (String)this.list.get(var1);
      if (var2 instanceof String) {
         return var2;
      } else if (var2 instanceof ByteString) {
         ByteString var6 = (ByteString)var2;
         var2 = var6.toStringUtf8();
         if (var6.isValidUtf8()) {
            this.list.set(var1, var2);
         }

         return var2;
      } else {
         byte[] var3 = (byte[])var2;
         var2 = Internal.toStringUtf8(var3);
         if (Internal.isValidUtf8(var3)) {
            this.list.set(var1, var2);
         }

         return var2;
      }
   }

   @Override
   public byte[] getByteArray(int var1) {
      Object var2 = this.list.get(var1);
      byte[] var3 = asByteArray(var2);
      if (var3 != var2) {
         this.list.set(var1, var3);
      }

      return var3;
   }

   @Override
   public ByteString getByteString(int var1) {
      Object var2 = this.list.get(var1);
      ByteString var3 = asByteString(var2);
      if (var3 != var2) {
         this.list.set(var1, var3);
      }

      return var3;
   }

   @Override
   public Object getRaw(int var1) {
      return this.list.get(var1);
   }

   @Override
   public List<?> getUnderlyingElements() {
      return Collections.unmodifiableList(this.list);
   }

   @Override
   public LazyStringList getUnmodifiableView() {
      return (LazyStringList)(this.isModifiable() ? new UnmodifiableLazyStringList(this) : this);
   }

   @Override
   public void mergeFrom(LazyStringList var1) {
      this.ensureIsMutable();

      for (Object var2 : var1.getUnderlyingElements()) {
         if (var2 instanceof byte[]) {
            var2 = (byte[])var2;
            this.list.add(Arrays.copyOf((byte[])var2, ((Object[])var2).length));
         } else {
            this.list.add(var2);
         }
      }
   }

   public LazyStringArrayList mutableCopyWithCapacity(int var1) {
      if (var1 >= this.size()) {
         ArrayList var2 = new ArrayList(var1);
         var2.addAll(this.list);
         return new LazyStringArrayList(var2);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public String remove(int var1) {
      this.ensureIsMutable();
      Object var2 = this.list.remove(var1);
      this.modCount++;
      return asString(var2);
   }

   public String set(int var1, String var2) {
      this.ensureIsMutable();
      return asString(this.list.set(var1, var2));
   }

   @Override
   public void set(int var1, ByteString var2) {
      this.setAndReturn(var1, var2);
   }

   @Override
   public void set(int var1, byte[] var2) {
      this.setAndReturn(var1, var2);
   }

   @Override
   public int size() {
      return this.list.size();
   }

   private static class ByteArrayListView extends AbstractList<byte[]> implements RandomAccess {
      private final LazyStringArrayList list;

      ByteArrayListView(LazyStringArrayList var1) {
         this.list = var1;
      }

      public void add(int var1, byte[] var2) {
         this.list.add(var1, var2);
         this.modCount++;
      }

      public byte[] get(int var1) {
         return this.list.getByteArray(var1);
      }

      public byte[] remove(int var1) {
         String var2 = this.list.remove(var1);
         this.modCount++;
         return LazyStringArrayList.asByteArray(var2);
      }

      public byte[] set(int var1, byte[] var2) {
         Object var3 = this.list.setAndReturn(var1, var2);
         this.modCount++;
         return LazyStringArrayList.asByteArray(var3);
      }

      @Override
      public int size() {
         return this.list.size();
      }
   }

   private static class ByteStringListView extends AbstractList<ByteString> implements RandomAccess {
      private final LazyStringArrayList list;

      ByteStringListView(LazyStringArrayList var1) {
         this.list = var1;
      }

      public void add(int var1, ByteString var2) {
         this.list.add(var1, var2);
         this.modCount++;
      }

      public ByteString get(int var1) {
         return this.list.getByteString(var1);
      }

      public ByteString remove(int var1) {
         String var2 = this.list.remove(var1);
         this.modCount++;
         return LazyStringArrayList.asByteString(var2);
      }

      public ByteString set(int var1, ByteString var2) {
         Object var3 = this.list.setAndReturn(var1, var2);
         this.modCount++;
         return LazyStringArrayList.asByteString(var3);
      }

      @Override
      public int size() {
         return this.list.size();
      }
   }
}
