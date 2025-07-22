package com.google.protobuf;

import java.io.IOException;

public class LazyFieldLite {
   private static final ExtensionRegistryLite EMPTY_REGISTRY = ExtensionRegistryLite.getEmptyRegistry();
   private ByteString delayedBytes;
   private ExtensionRegistryLite extensionRegistry;
   private volatile ByteString memoizedBytes;
   protected volatile MessageLite value;

   public LazyFieldLite() {
   }

   public LazyFieldLite(ExtensionRegistryLite var1, ByteString var2) {
      checkArguments(var1, var2);
      this.extensionRegistry = var1;
      this.delayedBytes = var2;
   }

   private static void checkArguments(ExtensionRegistryLite var0, ByteString var1) {
      if (var0 != null) {
         if (var1 == null) {
            throw new NullPointerException("found null ByteString");
         }
      } else {
         throw new NullPointerException("found null ExtensionRegistry");
      }
   }

   public static LazyFieldLite fromValue(MessageLite var0) {
      LazyFieldLite var1 = new LazyFieldLite();
      var1.setValue(var0);
      return var1;
   }

   private static MessageLite mergeValueAndBytes(MessageLite var0, ByteString var1, ExtensionRegistryLite var2) {
      try {
         var4 = var0.toBuilder().mergeFrom(var1, var2).build();
      } catch (InvalidProtocolBufferException var3) {
         return var0;
      }

      return var4;
   }

   public void clear() {
      this.delayedBytes = null;
      this.value = null;
      this.memoizedBytes = null;
   }

   public boolean containsDefaultInstance() {
      if (this.memoizedBytes != ByteString.EMPTY) {
         if (this.value != null) {
            return false;
         }

         ByteString var2 = this.delayedBytes;
         if (var2 != null && var2 != ByteString.EMPTY) {
            return false;
         }
      }

      return true;
   }

   protected void ensureInitialized(MessageLite param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield com/google/protobuf/LazyFieldLite.value Lcom/google/protobuf/MessageLite;
      // 04: ifnull 08
      // 07: return
      // 08: aload 0
      // 09: monitorenter
      // 0a: aload 0
      // 0b: getfield com/google/protobuf/LazyFieldLite.value Lcom/google/protobuf/MessageLite;
      // 0e: ifnull 14
      // 11: aload 0
      // 12: monitorexit
      // 13: return
      // 14: aload 0
      // 15: getfield com/google/protobuf/LazyFieldLite.delayedBytes Lcom/google/protobuf/ByteString;
      // 18: ifnull 40
      // 1b: aload 0
      // 1c: aload 1
      // 1d: invokeinterface com/google/protobuf/MessageLite.getParserForType ()Lcom/google/protobuf/Parser; 1
      // 22: aload 0
      // 23: getfield com/google/protobuf/LazyFieldLite.delayedBytes Lcom/google/protobuf/ByteString;
      // 26: aload 0
      // 27: getfield com/google/protobuf/LazyFieldLite.extensionRegistry Lcom/google/protobuf/ExtensionRegistryLite;
      // 2a: invokeinterface com/google/protobuf/Parser.parseFrom (Lcom/google/protobuf/ByteString;Lcom/google/protobuf/ExtensionRegistryLite;)Ljava/lang/Object; 3
      // 2f: checkcast com/google/protobuf/MessageLite
      // 32: putfield com/google/protobuf/LazyFieldLite.value Lcom/google/protobuf/MessageLite;
      // 35: aload 0
      // 36: aload 0
      // 37: getfield com/google/protobuf/LazyFieldLite.delayedBytes Lcom/google/protobuf/ByteString;
      // 3a: putfield com/google/protobuf/LazyFieldLite.memoizedBytes Lcom/google/protobuf/ByteString;
      // 3d: goto 5c
      // 40: aload 0
      // 41: aload 1
      // 42: putfield com/google/protobuf/LazyFieldLite.value Lcom/google/protobuf/MessageLite;
      // 45: aload 0
      // 46: getstatic com/google/protobuf/ByteString.EMPTY Lcom/google/protobuf/ByteString;
      // 49: putfield com/google/protobuf/LazyFieldLite.memoizedBytes Lcom/google/protobuf/ByteString;
      // 4c: goto 5c
      // 4f: astore 2
      // 50: aload 0
      // 51: aload 1
      // 52: putfield com/google/protobuf/LazyFieldLite.value Lcom/google/protobuf/MessageLite;
      // 55: aload 0
      // 56: getstatic com/google/protobuf/ByteString.EMPTY Lcom/google/protobuf/ByteString;
      // 59: putfield com/google/protobuf/LazyFieldLite.memoizedBytes Lcom/google/protobuf/ByteString;
      // 5c: aload 0
      // 5d: monitorexit
      // 5e: return
      // 5f: astore 1
      // 60: aload 0
      // 61: monitorexit
      // 62: aload 1
      // 63: athrow
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof LazyFieldLite)) {
         return false;
      } else {
         LazyFieldLite var2 = (LazyFieldLite)var1;
         MessageLite var3 = this.value;
         var1 = var2.value;
         if (var3 == null && var1 == null) {
            return this.toByteString().equals(var2.toByteString());
         } else if (var3 != null && var1 != null) {
            return var3.equals(var1);
         } else {
            return var3 != null ? var3.equals(var2.getValue(var3.getDefaultInstanceForType())) : this.getValue(var1.getDefaultInstanceForType()).equals(var1);
         }
      }
   }

   public int getSerializedSize() {
      if (this.memoizedBytes != null) {
         return this.memoizedBytes.size();
      } else {
         ByteString var1 = this.delayedBytes;
         if (var1 != null) {
            return var1.size();
         } else {
            return this.value != null ? this.value.getSerializedSize() : 0;
         }
      }
   }

   public MessageLite getValue(MessageLite var1) {
      this.ensureInitialized(var1);
      return this.value;
   }

   @Override
   public int hashCode() {
      return 1;
   }

   public void merge(LazyFieldLite var1) {
      if (!var1.containsDefaultInstance()) {
         if (this.containsDefaultInstance()) {
            this.set(var1);
         } else {
            if (this.extensionRegistry == null) {
               this.extensionRegistry = var1.extensionRegistry;
            }

            ByteString var2 = this.delayedBytes;
            if (var2 != null) {
               ByteString var3 = var1.delayedBytes;
               if (var3 != null) {
                  this.delayedBytes = var2.concat(var3);
                  return;
               }
            }

            if (this.value == null && var1.value != null) {
               this.setValue(mergeValueAndBytes(var1.value, this.delayedBytes, this.extensionRegistry));
            } else if (this.value != null && var1.value == null) {
               this.setValue(mergeValueAndBytes(this.value, var1.delayedBytes, var1.extensionRegistry));
            } else {
               this.setValue(this.value.toBuilder().mergeFrom(var1.value).build());
            }
         }
      }
   }

   public void mergeFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws IOException {
      if (this.containsDefaultInstance()) {
         this.setByteString(var1.readBytes(), var2);
      } else {
         if (this.extensionRegistry == null) {
            this.extensionRegistry = var2;
         }

         ByteString var3 = this.delayedBytes;
         if (var3 != null) {
            this.setByteString(var3.concat(var1.readBytes()), this.extensionRegistry);
         } else {
            try {
               this.setValue(this.value.toBuilder().mergeFrom(var1, var2).build());
            } catch (InvalidProtocolBufferException var4) {
            }
         }
      }
   }

   public void set(LazyFieldLite var1) {
      this.delayedBytes = var1.delayedBytes;
      this.value = var1.value;
      this.memoizedBytes = var1.memoizedBytes;
      ExtensionRegistryLite var2 = var1.extensionRegistry;
      if (var2 != null) {
         this.extensionRegistry = var2;
      }
   }

   public void setByteString(ByteString var1, ExtensionRegistryLite var2) {
      checkArguments(var2, var1);
      this.delayedBytes = var1;
      this.extensionRegistry = var2;
      this.value = null;
      this.memoizedBytes = null;
   }

   public MessageLite setValue(MessageLite var1) {
      MessageLite var2 = this.value;
      this.delayedBytes = null;
      this.memoizedBytes = null;
      this.value = var1;
      return var2;
   }

   public ByteString toByteString() {
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
      // 00: aload 0
      // 01: getfield com/google/protobuf/LazyFieldLite.memoizedBytes Lcom/google/protobuf/ByteString;
      // 04: ifnull 0c
      // 07: aload 0
      // 08: getfield com/google/protobuf/LazyFieldLite.memoizedBytes Lcom/google/protobuf/ByteString;
      // 0b: areturn
      // 0c: aload 0
      // 0d: getfield com/google/protobuf/LazyFieldLite.delayedBytes Lcom/google/protobuf/ByteString;
      // 10: astore 1
      // 11: aload 1
      // 12: ifnull 17
      // 15: aload 1
      // 16: areturn
      // 17: aload 0
      // 18: monitorenter
      // 19: aload 0
      // 1a: getfield com/google/protobuf/LazyFieldLite.memoizedBytes Lcom/google/protobuf/ByteString;
      // 1d: ifnull 29
      // 20: aload 0
      // 21: getfield com/google/protobuf/LazyFieldLite.memoizedBytes Lcom/google/protobuf/ByteString;
      // 24: astore 1
      // 25: aload 0
      // 26: monitorexit
      // 27: aload 1
      // 28: areturn
      // 29: aload 0
      // 2a: getfield com/google/protobuf/LazyFieldLite.value Lcom/google/protobuf/MessageLite;
      // 2d: ifnonnull 3a
      // 30: aload 0
      // 31: getstatic com/google/protobuf/ByteString.EMPTY Lcom/google/protobuf/ByteString;
      // 34: putfield com/google/protobuf/LazyFieldLite.memoizedBytes Lcom/google/protobuf/ByteString;
      // 37: goto 47
      // 3a: aload 0
      // 3b: aload 0
      // 3c: getfield com/google/protobuf/LazyFieldLite.value Lcom/google/protobuf/MessageLite;
      // 3f: invokeinterface com/google/protobuf/MessageLite.toByteString ()Lcom/google/protobuf/ByteString; 1
      // 44: putfield com/google/protobuf/LazyFieldLite.memoizedBytes Lcom/google/protobuf/ByteString;
      // 47: aload 0
      // 48: getfield com/google/protobuf/LazyFieldLite.memoizedBytes Lcom/google/protobuf/ByteString;
      // 4b: astore 1
      // 4c: aload 0
      // 4d: monitorexit
      // 4e: aload 1
      // 4f: areturn
      // 50: astore 1
      // 51: aload 0
      // 52: monitorexit
      // 53: aload 1
      // 54: athrow
   }

   void writeTo(Writer var1, int var2) throws IOException {
      if (this.memoizedBytes != null) {
         var1.writeBytes(var2, this.memoizedBytes);
      } else {
         ByteString var3 = this.delayedBytes;
         if (var3 != null) {
            var1.writeBytes(var2, var3);
         } else if (this.value != null) {
            var1.writeMessage(var2, this.value);
         } else {
            var1.writeBytes(var2, ByteString.EMPTY);
         }
      }
   }
}
