package okhttp3.internal.publicsuffix

import java.net.IDN
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util

public class PublicSuffixDatabase {
   private final val listRead: AtomicBoolean = new AtomicBoolean(false)
   private final lateinit var publicSuffixExceptionListBytes: ByteArray
   private final lateinit var publicSuffixListBytes: ByteArray
   private final val readCompleteLatch: CountDownLatch = new CountDownLatch(1)

   private fun findMatchingRule(domainLabels: List<String>): List<String> {
      if (!this.listRead.get() && this.listRead.compareAndSet(false, true)) {
         this.readTheListUninterruptibly();
      } else {
         try {
            this.readCompleteLatch.await();
         } catch (var10: InterruptedException) {
            Thread.currentThread().interrupt();
         }
      }

      val var4: PublicSuffixDatabase = this;
      var var2: Boolean;
      if (this.publicSuffixListBytes != null) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      if (!var2) {
         throw (new IllegalStateException("Unable to load publicsuffixes.gz resource from the classpath.".toString())) as java.lang.Throwable;
      } else {
         var var3: Int = var1.size();
         val var23: Array<ByteArray> = new byte[var3][];

         for (int var16 = 0; var16 < var3; var16++) {
            val var5: java.lang.String = var1.get(var16) as java.lang.String;
            val var6: Charset = StandardCharsets.UTF_8;
            Intrinsics.checkExpressionValueIsNotNull(StandardCharsets.UTF_8, "UTF_8");
            if (var5 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            val var28: ByteArray = var5.getBytes(var6);
            Intrinsics.checkExpressionValueIsNotNull(var28, "(this as java.lang.String).getBytes(charset)");
            var23[var16] = var28;
         }

         val var7: Array<ByteArray> = var23;
         val var32: Any = null;
         val var11: java.lang.String = null as java.lang.String;
         var3 = var7.length;
         var2 = 0;

         while (true) {
            if (var2 >= var3) {
               var13 = null;
               break;
            }

            if (this.publicSuffixListBytes == null) {
               Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
            }

            var13 = PublicSuffixDatabase.Companion.access$binarySearch(Companion, this.publicSuffixListBytes, var7, var2);
            if (var13 != null) {
               break;
            }

            var2++;
         }

         var var8: Array<Any>;
         label111: {
            var8 = var7 as Array<Any>;
            if ((var7 as Array<Any>).length > 1) {
               val var29: Array<ByteArray> = var8.clone() as Array<ByteArray>;
               var3 = (var29 as Array<Any>).length;

               for (int var18 = 0; var18 < var3 - 1; var18++) {
                  var29[var18] = WILDCARD_LABEL;
                  if (this.publicSuffixListBytes == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
                  }

                  var26 = PublicSuffixDatabase.Companion.access$binarySearch(Companion, this.publicSuffixListBytes, var29, var18);
                  if (var26 != null) {
                     break label111;
                  }
               }
            }

            var26 = null;
         }

         var var30: java.lang.String = null;
         if (var26 != null) {
            var3 = var8.length;
            var2 = 0;

            while (true) {
               var30 = (java.lang.String)var32;
               if (var2 >= var3 - 1) {
                  break;
               }

               if (this.publicSuffixExceptionListBytes == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("publicSuffixExceptionListBytes");
               }

               var30 = PublicSuffixDatabase.Companion.access$binarySearch(Companion, this.publicSuffixExceptionListBytes, var7, var2);
               if (var30 != null) {
                  break;
               }

               var2++;
            }
         }

         if (var30 != null) {
            val var15: StringBuilder = new StringBuilder("!");
            var15.append(var30);
            return StringsKt.split$default(var15.toString(), new char[]{'.'}, false, 0, 6, null);
         } else if (var13 == null && var26 == null) {
            return PREVAILING_RULE;
         } else {
            label86: {
               if (var13 != null) {
                  var1 = StringsKt.split$default(var13, new char[]{'.'}, false, 0, 6, null);
                  if (var1 != null) {
                     break label86;
                  }
               }

               var1 = CollectionsKt.emptyList();
            }

            label81: {
               if (var26 != null) {
                  var27 = StringsKt.split$default(var26, new char[]{'.'}, false, 0, 6, null);
                  if (var27 != null) {
                     break label81;
                  }
               }

               var27 = CollectionsKt.emptyList();
            }

            if (var1.size() <= var27.size()) {
               var1 = var27;
            }

            return var1;
         }
      }
   }

   @Throws(java/io/IOException::class)
   private fun readTheList() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aconst_null
      // 01: checkcast [B
      // 04: astore 1
      // 05: ldc okhttp3/internal/publicsuffix/PublicSuffixDatabase
      // 07: ldc "publicsuffixes.gz"
      // 09: invokevirtual java/lang/Class.getResourceAsStream (Ljava/lang/String;)Ljava/io/InputStream;
      // 0c: astore 1
      // 0d: aload 1
      // 0e: ifnull 8e
      // 11: new okio/GzipSource
      // 14: dup
      // 15: aload 1
      // 16: invokestatic okio/Okio.source (Ljava/io/InputStream;)Lokio/Source;
      // 19: invokespecial okio/GzipSource.<init> (Lokio/Source;)V
      // 1c: checkcast okio/Source
      // 1f: invokestatic okio/Okio.buffer (Lokio/Source;)Lokio/BufferedSource;
      // 22: checkcast java/io/Closeable
      // 25: astore 1
      // 26: aconst_null
      // 27: checkcast java/lang/Throwable
      // 2a: astore 2
      // 2b: aload 1
      // 2c: checkcast okio/BufferedSource
      // 2f: astore 3
      // 30: aload 3
      // 31: aload 3
      // 32: invokeinterface okio/BufferedSource.readInt ()I 1
      // 37: i2l
      // 38: invokeinterface okio/BufferedSource.readByteArray (J)[B 3
      // 3d: astore 2
      // 3e: aload 3
      // 3f: aload 3
      // 40: invokeinterface okio/BufferedSource.readInt ()I 1
      // 45: i2l
      // 46: invokeinterface okio/BufferedSource.readByteArray (J)[B 3
      // 4b: astore 3
      // 4c: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 4f: astore 4
      // 51: aload 1
      // 52: aconst_null
      // 53: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 56: aload 0
      // 57: monitorenter
      // 58: aload 2
      // 59: ifnonnull 5f
      // 5c: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 5f: aload 0
      // 60: aload 2
      // 61: putfield okhttp3/internal/publicsuffix/PublicSuffixDatabase.publicSuffixListBytes [B
      // 64: aload 3
      // 65: ifnonnull 6b
      // 68: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 6b: aload 0
      // 6c: aload 3
      // 6d: putfield okhttp3/internal/publicsuffix/PublicSuffixDatabase.publicSuffixExceptionListBytes [B
      // 70: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 73: astore 1
      // 74: aload 0
      // 75: monitorexit
      // 76: aload 0
      // 77: getfield okhttp3/internal/publicsuffix/PublicSuffixDatabase.readCompleteLatch Ljava/util/concurrent/CountDownLatch;
      // 7a: invokevirtual java/util/concurrent/CountDownLatch.countDown ()V
      // 7d: return
      // 7e: astore 1
      // 7f: aload 0
      // 80: monitorexit
      // 81: aload 1
      // 82: athrow
      // 83: astore 3
      // 84: aload 3
      // 85: athrow
      // 86: astore 2
      // 87: aload 1
      // 88: aload 3
      // 89: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 8c: aload 2
      // 8d: athrow
      // 8e: return
   }

   private fun readTheListUninterruptibly() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
      //
      // Bytecode:
      // 00: bipush 0
      // 01: istore 1
      // 02: aload 0
      // 03: invokespecial okhttp3/internal/publicsuffix/PublicSuffixDatabase.readTheList ()V
      // 06: iload 1
      // 07: ifeq 10
      // 0a: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 0d: invokevirtual java/lang/Thread.interrupt ()V
      // 10: return
      // 11: astore 2
      // 12: goto 3c
      // 15: astore 2
      // 16: getstatic okhttp3/internal/platform/Platform.Companion Lokhttp3/internal/platform/Platform$Companion;
      // 19: invokevirtual okhttp3/internal/platform/Platform$Companion.get ()Lokhttp3/internal/platform/Platform;
      // 1c: ldc_w "Failed to read public suffix list"
      // 1f: bipush 5
      // 20: aload 2
      // 21: checkcast java/lang/Throwable
      // 24: invokevirtual okhttp3/internal/platform/Platform.log (Ljava/lang/String;ILjava/lang/Throwable;)V
      // 27: iload 1
      // 28: ifeq 31
      // 2b: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 2e: invokevirtual java/lang/Thread.interrupt ()V
      // 31: return
      // 32: astore 2
      // 33: invokestatic java/lang/Thread.interrupted ()Z
      // 36: pop
      // 37: bipush 1
      // 38: istore 1
      // 39: goto 02
      // 3c: iload 1
      // 3d: ifeq 46
      // 40: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 43: invokevirtual java/lang/Thread.interrupt ()V
      // 46: aload 2
      // 47: athrow
   }

   private fun splitDomain(domain: String): List<String> {
      val var2: java.util.List = StringsKt.split$default(var1, new char[]{'.'}, false, 0, 6, null);
      return if (CollectionsKt.last(var2) as java.lang.String == "") CollectionsKt.dropLast(var2, 1) else var2;
   }

   public fun getEffectiveTldPlusOne(domain: String): String? {
      Intrinsics.checkParameterIsNotNull(var1, "domain");
      val var4: java.lang.String = IDN.toUnicode(var1);
      Intrinsics.checkExpressionValueIsNotNull(var4, "unicodeDomain");
      val var6: java.util.List = this.splitDomain(var4);
      val var5: java.util.List = this.findMatchingRule(var6);
      if (var6.size() == var5.size() && (var5.get(0) as java.lang.String).charAt(0) != '!') {
         return null;
      } else {
         val var2: Int;
         val var3: Int;
         if ((var5.get(0) as java.lang.String).charAt(0) == '!') {
            var3 = var6.size();
            var2 = var5.size();
         } else {
            var3 = var6.size();
            var2 = var5.size() + 1;
         }

         return SequencesKt.joinToString$default(
            SequencesKt.drop(CollectionsKt.asSequence(this.splitDomain(var1)), var3 - var2), ".", null, null, 0, null, null, 62, null
         );
      }
   }

   public fun setListBytes(publicSuffixListBytes: ByteArray, publicSuffixExceptionListBytes: ByteArray) {
      Intrinsics.checkParameterIsNotNull(var1, "publicSuffixListBytes");
      Intrinsics.checkParameterIsNotNull(var2, "publicSuffixExceptionListBytes");
      this.publicSuffixListBytes = var1;
      this.publicSuffixExceptionListBytes = var2;
      this.listRead.set(true);
      this.readCompleteLatch.countDown();
   }

   public companion object {
      private const val EXCEPTION_MARKER: Char
      private final val PREVAILING_RULE: List<String>
      public const val PUBLIC_SUFFIX_RESOURCE: String
      private final val WILDCARD_LABEL: ByteArray
      private final val instance: PublicSuffixDatabase

      private fun ByteArray.binarySearch(labels: Array<ByteArray>, labelIndex: Int): String? {
         var var9: Int = var1.length;
         val var15: java.lang.String = null as java.lang.String;
         var var8: Int = 0;

         while (var8 < var9) {
            var var4: Int = (var8 + var9) / 2;

            while (var4 > -1 && var1[var4] != 10) {
               var4--;
            }

            val var12: Int = var4 + 1;
            var var5: Int = 1;

            while (true) {
               val var13: Int = var12 + var5;
               if (var1[var12 + var5] == 10) {
                  val var14: Int = var13 - var12;
                  var var10: Int = var3;
                  var var7: Int = 0;
                  var var6: Int = 0;
                  var5 = 0;

                  var var23: Int;
                  while (true) {
                     if (var7) {
                        var23 = 46;
                        var7 = 0;
                     } else {
                        var23 = Util.and(var2[var10][var6], 255);
                     }

                     var23 = var23 - Util.and(var1[var12 + var5], 255);
                     if (var23 != 0) {
                        break;
                     }

                     var5++;
                     var6++;
                     if (var5 == var14) {
                        break;
                     }

                     if (var2[var10].length == var6) {
                        if (var10 == (var2 as Array<Any>).length - 1) {
                           break;
                        }

                        var10++;
                        var7 = 1;
                        var6 = -1;
                     }
                  }

                  label49:
                  if (var23 >= 0) {
                     if (var23 <= 0) {
                        var7 = var14 - var5;
                        var6 = var2[var10].length - var6;
                        var5 = var10 + 1;

                        for (int var22 = ((Object[])var2).length; var5 < var22; var5++) {
                           var6 += var2[var5].length;
                        }

                        if (var6 < var7) {
                           break label49;
                        }

                        if (var6 <= var7) {
                           val var17: Charset = StandardCharsets.UTF_8;
                           Intrinsics.checkExpressionValueIsNotNull(StandardCharsets.UTF_8, "UTF_8");
                           return new java.lang.String(var1, var12, var14, var17);
                        }
                     }

                     var8 = var13 + 1;
                     break;
                  }

                  var9 = var4;
                  break;
               }

               var5++;
            }
         }

         return null;
      }

      public fun get(): PublicSuffixDatabase {
         return PublicSuffixDatabase.access$getInstance$cp();
      }
   }
}
