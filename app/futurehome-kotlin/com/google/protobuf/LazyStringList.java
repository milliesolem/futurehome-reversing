package com.google.protobuf;

import java.util.Collection;
import java.util.List;

public interface LazyStringList extends ProtocolStringList {
   void add(ByteString var1);

   void add(byte[] var1);

   boolean addAllByteArray(Collection<byte[]> var1);

   boolean addAllByteString(Collection<? extends ByteString> var1);

   List<byte[]> asByteArrayList();

   byte[] getByteArray(int var1);

   ByteString getByteString(int var1);

   Object getRaw(int var1);

   List<?> getUnderlyingElements();

   LazyStringList getUnmodifiableView();

   void mergeFrom(LazyStringList var1);

   void set(int var1, ByteString var2);

   void set(int var1, byte[] var2);
}
