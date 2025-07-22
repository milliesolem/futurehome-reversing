package com.google.protobuf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ExtensionRegistryLite {
   static final ExtensionRegistryLite EMPTY_REGISTRY_LITE = new ExtensionRegistryLite(true);
   static final String EXTENSION_CLASS_NAME = "com.google.protobuf.Extension";
   private static boolean doFullRuntimeInheritanceCheck;
   private static volatile boolean eagerlyParseMessageSets;
   private static volatile ExtensionRegistryLite emptyRegistry;
   private final Map<ExtensionRegistryLite.ObjectIntPair, GeneratedMessageLite.GeneratedExtension<?, ?>> extensionsByNumber;

   ExtensionRegistryLite() {
      this.extensionsByNumber = new HashMap<>();
   }

   ExtensionRegistryLite(ExtensionRegistryLite var1) {
      if (var1 == EMPTY_REGISTRY_LITE) {
         this.extensionsByNumber = Collections.emptyMap();
      } else {
         this.extensionsByNumber = Collections.unmodifiableMap(var1.extensionsByNumber);
      }
   }

   ExtensionRegistryLite(boolean var1) {
      this.extensionsByNumber = Collections.emptyMap();
   }

   public static ExtensionRegistryLite getEmptyRegistry() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: getstatic com/google/protobuf/ExtensionRegistryLite.doFullRuntimeInheritanceCheck Z
      // 03: ifne 0a
      // 06: getstatic com/google/protobuf/ExtensionRegistryLite.EMPTY_REGISTRY_LITE Lcom/google/protobuf/ExtensionRegistryLite;
      // 09: areturn
      // 0a: getstatic com/google/protobuf/ExtensionRegistryLite.emptyRegistry Lcom/google/protobuf/ExtensionRegistryLite;
      // 0d: astore 1
      // 0e: aload 1
      // 0f: astore 0
      // 10: aload 1
      // 11: ifnonnull 35
      // 14: ldc com/google/protobuf/ExtensionRegistryLite
      // 16: monitorenter
      // 17: getstatic com/google/protobuf/ExtensionRegistryLite.emptyRegistry Lcom/google/protobuf/ExtensionRegistryLite;
      // 1a: astore 1
      // 1b: aload 1
      // 1c: astore 0
      // 1d: aload 1
      // 1e: ifnonnull 29
      // 21: invokestatic com/google/protobuf/ExtensionRegistryFactory.createEmpty ()Lcom/google/protobuf/ExtensionRegistryLite;
      // 24: astore 0
      // 25: aload 0
      // 26: putstatic com/google/protobuf/ExtensionRegistryLite.emptyRegistry Lcom/google/protobuf/ExtensionRegistryLite;
      // 29: ldc com/google/protobuf/ExtensionRegistryLite
      // 2b: monitorexit
      // 2c: goto 35
      // 2f: astore 0
      // 30: ldc com/google/protobuf/ExtensionRegistryLite
      // 32: monitorexit
      // 33: aload 0
      // 34: athrow
      // 35: aload 0
      // 36: areturn
   }

   public static boolean isEagerlyParseMessageSets() {
      return eagerlyParseMessageSets;
   }

   public static ExtensionRegistryLite newInstance() {
      ExtensionRegistryLite var0;
      if (doFullRuntimeInheritanceCheck) {
         var0 = ExtensionRegistryFactory.create();
      } else {
         var0 = new ExtensionRegistryLite();
      }

      return var0;
   }

   public static void setEagerlyParseMessageSets(boolean var0) {
      eagerlyParseMessageSets = var0;
   }

   public final void add(ExtensionLite<?, ?> var1) {
      if (GeneratedMessageLite.GeneratedExtension.class.isAssignableFrom(var1.getClass())) {
         this.add((GeneratedMessageLite.GeneratedExtension<?, ?>)var1);
      }

      if (doFullRuntimeInheritanceCheck && ExtensionRegistryFactory.isFullRegistry(this)) {
         try {
            this.getClass().getMethod("add", ExtensionRegistryLite.ExtensionClassHolder.INSTANCE).invoke(this, var1);
         } catch (Exception var3) {
            throw new IllegalArgumentException(String.format("Could not invoke ExtensionRegistry#add for %s", var1), var3);
         }
      }
   }

   public final void add(GeneratedMessageLite.GeneratedExtension<?, ?> var1) {
      this.extensionsByNumber.put(new ExtensionRegistryLite.ObjectIntPair(var1.getContainingTypeDefaultInstance(), var1.getNumber()), var1);
   }

   public <ContainingType extends MessageLite> GeneratedMessageLite.GeneratedExtension<ContainingType, ?> findLiteExtensionByNumber(
      ContainingType var1, int var2
   ) {
      return (GeneratedMessageLite.GeneratedExtension<ContainingType, ?>)this.extensionsByNumber.get(new ExtensionRegistryLite.ObjectIntPair(var1, var2));
   }

   public ExtensionRegistryLite getUnmodifiable() {
      return new ExtensionRegistryLite(this);
   }

   private static class ExtensionClassHolder {
      static final Class<?> INSTANCE = resolveExtensionClass();

      static Class<?> resolveExtensionClass() {
         try {
            return Class.forName("com.google.protobuf.Extension");
         } catch (ClassNotFoundException var1) {
            return null;
         }
      }
   }

   private static final class ObjectIntPair {
      private final int number;
      private final Object object;

      ObjectIntPair(Object var1, int var2) {
         this.object = var1;
         this.number = var2;
      }

      @Override
      public boolean equals(Object var1) {
         boolean var2 = var1 instanceof ExtensionRegistryLite.ObjectIntPair;
         boolean var3 = false;
         if (!var2) {
            return false;
         } else {
            var1 = var1;
            var2 = var3;
            if (this.object == var1.object) {
               var2 = var3;
               if (this.number == var1.number) {
                  var2 = true;
               }
            }

            return var2;
         }
      }

      @Override
      public int hashCode() {
         return System.identityHashCode(this.object) * 65535 + this.number;
      }
   }
}
