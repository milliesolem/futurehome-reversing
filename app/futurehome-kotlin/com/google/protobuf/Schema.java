package com.google.protobuf;

import java.io.IOException;

@CheckReturnValue
interface Schema<T> {
   boolean equals(T var1, T var2);

   int getSerializedSize(T var1);

   int hashCode(T var1);

   boolean isInitialized(T var1);

   void makeImmutable(T var1);

   void mergeFrom(T var1, Reader var2, ExtensionRegistryLite var3) throws IOException;

   void mergeFrom(T var1, T var2);

   void mergeFrom(T var1, byte[] var2, int var3, int var4, ArrayDecoders.Registers var5) throws IOException;

   T newInstance();

   void writeTo(T var1, Writer var2) throws IOException;
}
