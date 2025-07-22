package com.google.protobuf;

import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

@CheckReturnValue
final class Protobuf {
   private static final Protobuf INSTANCE = new Protobuf();
   private final ConcurrentMap<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap();
   private final SchemaFactory schemaFactory = new ManifestSchemaFactory();

   private Protobuf() {
   }

   public static Protobuf getInstance() {
      return INSTANCE;
   }

   int getTotalSchemaSize() {
      Iterator var2 = this.schemaCache.values().iterator();
      int var1 = 0;

      while (var2.hasNext()) {
         Schema var3 = (Schema)var2.next();
         if (var3 instanceof MessageSchema) {
            var1 += ((MessageSchema)var3).getSchemaSize();
         }
      }

      return var1;
   }

   <T> boolean isInitialized(T var1) {
      return this.schemaFor(var1).isInitialized(var1);
   }

   public <T> void makeImmutable(T var1) {
      this.schemaFor(var1).makeImmutable(var1);
   }

   public <T> void mergeFrom(T var1, Reader var2) throws IOException {
      this.mergeFrom(var1, var2, ExtensionRegistryLite.getEmptyRegistry());
   }

   public <T> void mergeFrom(T var1, Reader var2, ExtensionRegistryLite var3) throws IOException {
      this.schemaFor(var1).mergeFrom(var1, var2, var3);
   }

   public Schema<?> registerSchema(Class<?> var1, Schema<?> var2) {
      Internal.checkNotNull(var1, "messageType");
      Internal.checkNotNull(var2, "schema");
      return this.schemaCache.putIfAbsent(var1, var2);
   }

   public Schema<?> registerSchemaOverride(Class<?> var1, Schema<?> var2) {
      Internal.checkNotNull(var1, "messageType");
      Internal.checkNotNull(var2, "schema");
      return this.schemaCache.put(var1, var2);
   }

   public <T> Schema<T> schemaFor(Class<T> var1) {
      Internal.checkNotNull(var1, "messageType");
      Schema var3 = this.schemaCache.get(var1);
      Schema var2 = var3;
      if (var3 == null) {
         var2 = this.schemaFactory.createSchema(var1);
         Schema var4 = this.registerSchema(var1, var2);
         if (var4 != null) {
            var2 = var4;
         }
      }

      return var2;
   }

   public <T> Schema<T> schemaFor(T var1) {
      return this.schemaFor((Class<T>)var1.getClass());
   }

   public <T> void writeTo(T var1, Writer var2) throws IOException {
      this.schemaFor(var1).writeTo(var1, var2);
   }
}
