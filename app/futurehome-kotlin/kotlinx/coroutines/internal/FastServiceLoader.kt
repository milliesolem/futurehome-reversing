package kotlinx.coroutines.internal

import java.io.BufferedReader
import java.net.URL
import java.util.ArrayList
import java.util.Collections
import java.util.LinkedHashSet
import java.util.ServiceLoader
import java.util.jar.JarFile

internal object FastServiceLoader {
   private const val PREFIX: String = "META-INF/services/"

   private inline fun createInstanceOf(baseClass: Class<MainDispatcherFactory>, serviceClass: String): MainDispatcherFactory? {
      try {
         var5 = var1.cast(Class.forName(var2, true, var1.getClassLoader()).getDeclaredConstructor(null).newInstance(null)) as MainDispatcherFactory;
      } catch (var4: ClassNotFoundException) {
         var5 = null;
      }

      return var5;
   }

   private fun <S> getProviderInstance(name: String, loader: ClassLoader, service: Class<S>): S {
      val var5: Class = Class.forName(var1, false, var2);
      if (var3.isAssignableFrom(var5)) {
         return (S)var3.cast(var5.getDeclaredConstructor(null).newInstance(null));
      } else {
         val var4: StringBuilder = new StringBuilder("Expected service of class ");
         var4.append(var3);
         var4.append(", but found ");
         var4.append(var5);
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   private fun <S> load(service: Class<S>, loader: ClassLoader): List<S> {
      try {
         ;
      } catch (var4: java.lang.Throwable) {
         return CollectionsKt.toList(ServiceLoader.load(var1, var2));
      }

      val var3: Any;
      return (java.util.List<S>)var3;
   }

   private fun parse(url: URL): List<String> {
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
      //
      // Bytecode:
      // 00: aload 1
      // 01: invokevirtual java/net/URL.toString ()Ljava/lang/String;
      // 04: astore 2
      // 05: aload 2
      // 06: ldc "jar"
      // 08: bipush 0
      // 09: bipush 2
      // 0a: aconst_null
      // 0b: invokestatic kotlin/text/StringsKt.startsWith$default (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
      // 0e: ifeq 9d
      // 11: aload 2
      // 12: ldc "jar:file:"
      // 14: aconst_null
      // 15: bipush 2
      // 16: aconst_null
      // 17: invokestatic kotlin/text/StringsKt.substringAfter$default (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 1a: bipush 33
      // 1c: aconst_null
      // 1d: bipush 2
      // 1e: aconst_null
      // 1f: invokestatic kotlin/text/StringsKt.substringBefore$default (Ljava/lang/String;CLjava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 22: astore 1
      // 23: aload 2
      // 24: ldc "!/"
      // 26: aconst_null
      // 27: bipush 2
      // 28: aconst_null
      // 29: invokestatic kotlin/text/StringsKt.substringAfter$default (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/Object;)Ljava/lang/String;
      // 2c: astore 2
      // 2d: new java/util/jar/JarFile
      // 30: dup
      // 31: aload 1
      // 32: bipush 0
      // 33: invokespecial java/util/jar/JarFile.<init> (Ljava/lang/String;Z)V
      // 36: astore 1
      // 37: new java/io/BufferedReader
      // 3a: astore 5
      // 3c: new java/io/InputStreamReader
      // 3f: astore 4
      // 41: new java/util/zip/ZipEntry
      // 44: astore 3
      // 45: aload 3
      // 46: aload 2
      // 47: invokespecial java/util/zip/ZipEntry.<init> (Ljava/lang/String;)V
      // 4a: aload 4
      // 4c: aload 1
      // 4d: aload 3
      // 4e: invokevirtual java/util/jar/JarFile.getInputStream (Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
      // 51: ldc "UTF-8"
      // 53: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/lang/String;)V
      // 56: aload 5
      // 58: aload 4
      // 5a: checkcast java/io/Reader
      // 5d: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 60: aload 5
      // 62: checkcast java/io/Closeable
      // 65: astore 2
      // 66: aload 2
      // 67: checkcast java/io/BufferedReader
      // 6a: astore 3
      // 6b: getstatic kotlinx/coroutines/internal/FastServiceLoader.INSTANCE Lkotlinx/coroutines/internal/FastServiceLoader;
      // 6e: aload 3
      // 6f: invokespecial kotlinx/coroutines/internal/FastServiceLoader.parseFile (Ljava/io/BufferedReader;)Ljava/util/List;
      // 72: astore 3
      // 73: aload 2
      // 74: aconst_null
      // 75: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 78: aload 1
      // 79: invokevirtual java/util/jar/JarFile.close ()V
      // 7c: aload 3
      // 7d: areturn
      // 7e: astore 3
      // 7f: aload 3
      // 80: athrow
      // 81: astore 4
      // 83: aload 2
      // 84: aload 3
      // 85: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 88: aload 4
      // 8a: athrow
      // 8b: astore 2
      // 8c: aload 2
      // 8d: athrow
      // 8e: astore 3
      // 8f: aload 1
      // 90: invokevirtual java/util/jar/JarFile.close ()V
      // 93: aload 3
      // 94: athrow
      // 95: astore 1
      // 96: aload 2
      // 97: aload 1
      // 98: invokestatic kotlin/ExceptionsKt.addSuppressed (Ljava/lang/Throwable;Ljava/lang/Throwable;)V
      // 9b: aload 2
      // 9c: athrow
      // 9d: new java/io/BufferedReader
      // a0: dup
      // a1: new java/io/InputStreamReader
      // a4: dup
      // a5: aload 1
      // a6: invokevirtual java/net/URL.openStream ()Ljava/io/InputStream;
      // a9: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;)V
      // ac: checkcast java/io/Reader
      // af: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // b2: checkcast java/io/Closeable
      // b5: astore 1
      // b6: aload 1
      // b7: checkcast java/io/BufferedReader
      // ba: astore 2
      // bb: getstatic kotlinx/coroutines/internal/FastServiceLoader.INSTANCE Lkotlinx/coroutines/internal/FastServiceLoader;
      // be: aload 2
      // bf: invokespecial kotlinx/coroutines/internal/FastServiceLoader.parseFile (Ljava/io/BufferedReader;)Ljava/util/List;
      // c2: astore 2
      // c3: aload 1
      // c4: aconst_null
      // c5: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // c8: aload 2
      // c9: areturn
      // ca: astore 2
      // cb: aload 2
      // cc: athrow
      // cd: astore 3
      // ce: aload 1
      // cf: aload 2
      // d0: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // d3: aload 3
      // d4: athrow
   }

   private fun parseFile(r: BufferedReader): List<String> {
      val var4: java.util.Set = new LinkedHashSet();

      while (true) {
         var var5: java.lang.String = var1.readLine();
         if (var5 == null) {
            return CollectionsKt.toList(var4);
         }

         var5 = StringsKt.trim(StringsKt.substringBefore$default(var5, "#", null, 2, null)).toString();
         val var6: java.lang.CharSequence = var5;

         for (int var3 = 0; var3 < var6.length(); var3++) {
            val var2: Char = var6.charAt(var3);
            if (var2 != '.' && !Character.isJavaIdentifierPart(var2)) {
               val var7: StringBuilder = new StringBuilder("Illegal service provider class name: ");
               var7.append(var5);
               throw new IllegalArgumentException(var7.toString().toString());
            }
         }

         if (var6.length() > 0) {
            var4.add(var5);
         }
      }
   }

   private inline fun <R> JarFile.use(block: (JarFile) -> R): R {
      label22: {
         try {
            var17 = var2.invoke(var1);
         } catch (var6: java.lang.Throwable) {
            val var16: java.lang.Throwable = var6;

            try {
               throw var16;
            } catch (var5: java.lang.Throwable) {
               try {
                  var1.close();
               } catch (var4: java.lang.Throwable) {
                  ExceptionsKt.addSuppressed(var6, var4);
                  throw var6;
               }
            }
         }

         var1.close();
         return (R)var17;
      }
   }

   internal fun loadMainDispatcherFactory(): List<MainDispatcherFactory> {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
      //
      // Bytecode:
      // 00: invokestatic kotlinx/coroutines/internal/FastServiceLoaderKt.getANDROID_DETECTED ()Z
      // 03: ifne 12
      // 06: aload 0
      // 07: ldc kotlinx/coroutines/internal/MainDispatcherFactory
      // 09: ldc kotlinx/coroutines/internal/MainDispatcherFactory
      // 0b: invokevirtual java/lang/Class.getClassLoader ()Ljava/lang/ClassLoader;
      // 0e: invokespecial kotlinx/coroutines/internal/FastServiceLoader.load (Ljava/lang/Class;Ljava/lang/ClassLoader;)Ljava/util/List;
      // 11: areturn
      // 12: new java/util/ArrayList
      // 15: astore 3
      // 16: aload 3
      // 17: bipush 2
      // 18: invokespecial java/util/ArrayList.<init> (I)V
      // 1b: aconst_null
      // 1c: astore 2
      // 1d: ldc kotlinx/coroutines/internal/MainDispatcherFactory
      // 1f: ldc_w "kotlinx.coroutines.android.AndroidDispatcherFactory"
      // 22: bipush 1
      // 23: ldc kotlinx/coroutines/internal/MainDispatcherFactory
      // 25: invokevirtual java/lang/Class.getClassLoader ()Ljava/lang/ClassLoader;
      // 28: invokestatic java/lang/Class.forName (Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;
      // 2b: aconst_null
      // 2c: invokevirtual java/lang/Class.getDeclaredConstructor ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
      // 2f: aconst_null
      // 30: invokevirtual java/lang/reflect/Constructor.newInstance ([Ljava/lang/Object;)Ljava/lang/Object;
      // 33: invokevirtual java/lang/Class.cast (Ljava/lang/Object;)Ljava/lang/Object;
      // 36: checkcast kotlinx/coroutines/internal/MainDispatcherFactory
      // 39: astore 1
      // 3a: goto 40
      // 3d: astore 1
      // 3e: aconst_null
      // 3f: astore 1
      // 40: aload 1
      // 41: ifnull 4a
      // 44: aload 3
      // 45: aload 1
      // 46: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 49: pop
      // 4a: ldc kotlinx/coroutines/internal/MainDispatcherFactory
      // 4c: ldc_w "kotlinx.coroutines.test.internal.TestMainDispatcherFactory"
      // 4f: bipush 1
      // 50: ldc kotlinx/coroutines/internal/MainDispatcherFactory
      // 52: invokevirtual java/lang/Class.getClassLoader ()Ljava/lang/ClassLoader;
      // 55: invokestatic java/lang/Class.forName (Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;
      // 58: aconst_null
      // 59: invokevirtual java/lang/Class.getDeclaredConstructor ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
      // 5c: aconst_null
      // 5d: invokevirtual java/lang/reflect/Constructor.newInstance ([Ljava/lang/Object;)Ljava/lang/Object;
      // 60: invokevirtual java/lang/Class.cast (Ljava/lang/Object;)Ljava/lang/Object;
      // 63: checkcast kotlinx/coroutines/internal/MainDispatcherFactory
      // 66: astore 1
      // 67: goto 6d
      // 6a: astore 1
      // 6b: aload 2
      // 6c: astore 1
      // 6d: aload 1
      // 6e: ifnull 77
      // 71: aload 3
      // 72: aload 1
      // 73: invokevirtual java/util/ArrayList.add (Ljava/lang/Object;)Z
      // 76: pop
      // 77: aload 3
      // 78: checkcast java/util/List
      // 7b: astore 1
      // 7c: goto 8c
      // 7f: astore 1
      // 80: aload 0
      // 81: ldc kotlinx/coroutines/internal/MainDispatcherFactory
      // 83: ldc kotlinx/coroutines/internal/MainDispatcherFactory
      // 85: invokevirtual java/lang/Class.getClassLoader ()Ljava/lang/ClassLoader;
      // 88: invokespecial kotlinx/coroutines/internal/FastServiceLoader.load (Ljava/lang/Class;Ljava/lang/ClassLoader;)Ljava/util/List;
      // 8b: astore 1
      // 8c: aload 1
      // 8d: areturn
   }

   internal fun <S> loadProviders(service: Class<S>, loader: ClassLoader): List<S> {
      val var3: StringBuilder = new StringBuilder("META-INF/services/");
      var3.append(var1.getName());
      val var6: ArrayList = Collections.list(var2.getResources(var3.toString()));
      var var4: java.lang.Iterable = var6;
      val var7: java.util.Collection = new ArrayList();

      for (URL var5 : var4) {
         CollectionsKt.addAll(var7, INSTANCE.parse(var5));
      }

      val var8: java.util.Set = CollectionsKt.toSet(var7 as java.util.List);
      if (var8.isEmpty()) {
         throw new IllegalArgumentException("No providers were loaded with FastServiceLoader".toString());
      } else {
         var4 = var8;
         val var9: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var8, 10));

         for (java.lang.String var12 : var4) {
            var9.add(INSTANCE.getProviderInstance(var12, var2, var1));
         }

         return var9 as MutableList<S>;
      }
   }
}
