package okhttp3.internal

import java.io.Closeable
import java.io.File
import java.io.IOException
import java.lang.reflect.Field
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketAddress
import java.net.SocketTimeoutException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.Comparator
import java.util.LinkedHashMap
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit
import kotlin.jvm.internal.Intrinsics
import kotlin.jvm.internal.StringCompanionObject
import kotlin.jvm.internal.TypeIntrinsics
import okhttp3.Call
import okhttp3.EventListener
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.EventListener.Factory
import okhttp3.internal.http2.Header
import okhttp3.internal.io.FileSystem
import okio.Buffer
import okio.BufferedSink
import okio.BufferedSource
import okio.ByteString
import okio.Options
import okio.Source

public final val EMPTY_BYTE_ARRAY: ByteArray
public final val EMPTY_HEADERS: Headers = Headers.Companion.of()
public final val EMPTY_REQUEST: RequestBody
public final val EMPTY_RESPONSE: ResponseBody
private final val UNICODE_BOMS: Options =
   Options.Companion
      .of(
         ByteString.Companion.decodeHex("efbbbf"),
         ByteString.Companion.decodeHex("feff"),
         ByteString.Companion.decodeHex("fffe"),
         ByteString.Companion.decodeHex("0000ffff"),
         ByteString.Companion.decodeHex("ffff0000")
      )
      public final val UTC: TimeZone
private final val VERIFY_AS_IP_ADDRESS: Regex
internal final val assertionsEnabled: Boolean
internal final val okHttpName: String
public const val userAgent: String = "okhttp/4.8.0"

internal fun <E> MutableList<E>.addIfAbsent(element: E) {
   Intrinsics.checkParameterIsNotNull(var0, "$this$addIfAbsent");
   if (!var0.contains(var1)) {
      var0.add(var1);
   }
}

public infix fun Byte.and(mask: Int): Int {
   return var0 and var1;
}

public infix fun Short.and(mask: Int): Int {
   return var0 and var1;
}

public infix fun Int.and(mask: Long): Long {
   return var0 and var1;
}

public fun EventListener.asFactory(): Factory {
   Intrinsics.checkParameterIsNotNull(var0, "$this$asFactory");
   return new EventListener.Factory(var0) {
      final EventListener $this_asFactory;

      {
         this.$this_asFactory = var1;
      }

      @Override
      public EventListener create(Call var1) {
         Intrinsics.checkParameterIsNotNull(var1, "call");
         return this.$this_asFactory;
      }
   };
}

internal inline fun Any.assertThreadDoesntHoldLock() {
   Intrinsics.checkParameterIsNotNull(var0, "$this$assertThreadDoesntHoldLock");
   if (assertionsEnabled && Thread.holdsLock(var0)) {
      val var1: StringBuilder = new StringBuilder("Thread ");
      val var2: Thread = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(var2, "Thread.currentThread()");
      var1.append(var2.getName());
      var1.append(" MUST NOT hold lock on ");
      var1.append(var0);
      throw (new AssertionError(var1.toString())) as java.lang.Throwable;
   }
}

internal inline fun Any.assertThreadHoldsLock() {
   Intrinsics.checkParameterIsNotNull(var0, "$this$assertThreadHoldsLock");
   if (assertionsEnabled && !Thread.holdsLock(var0)) {
      val var1: StringBuilder = new StringBuilder("Thread ");
      val var2: Thread = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(var2, "Thread.currentThread()");
      var1.append(var2.getName());
      var1.append(" MUST hold lock on ");
      var1.append(var0);
      throw (new AssertionError(var1.toString())) as java.lang.Throwable;
   }
}

public fun String.canParseAsIpAddress(): Boolean {
   Intrinsics.checkParameterIsNotNull(var0, "$this$canParseAsIpAddress");
   return VERIFY_AS_IP_ADDRESS.matches(var0);
}

public fun HttpUrl.canReuseConnectionFor(other: HttpUrl): Boolean {
   Intrinsics.checkParameterIsNotNull(var0, "$this$canReuseConnectionFor");
   Intrinsics.checkParameterIsNotNull(var1, "other");
   val var2: Boolean;
   if (var0.host() == var1.host() && var0.port() == var1.port() && var0.scheme() == var1.scheme()) {
      var2 = true;
   } else {
      var2 = false;
   }

   return var2;
}

public fun checkDuration(name: String, duration: Long, unit: TimeUnit?): Int {
   Intrinsics.checkParameterIsNotNull(var0, "name");
   val var14: Long;
   val var6: Byte = (byte)(if ((var14 = var1 - 0L) == 0L) 0 else (if (var14 < 0L) -1 else 1));
   var var4: Boolean;
   if (var1 >= 0L) {
      var4 = true;
   } else {
      var4 = false;
   }

   if (var4) {
      if (var3 != null) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         var1 = var3.toMillis(var1);
         if (var1 <= Integer.MAX_VALUE) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            var4 = true;
            if (var1 == 0L) {
               if (var6 <= 0) {
                  var4 = true;
               } else {
                  var4 = false;
               }
            }

            if (var4) {
               return (int)var1;
            } else {
               val var10: StringBuilder = new StringBuilder();
               var10.append(var0);
               var10.append(" too small.");
               throw (new IllegalArgumentException(var10.toString().toString())) as java.lang.Throwable;
            }
         } else {
            val var9: StringBuilder = new StringBuilder();
            var9.append(var0);
            var9.append(" too large.");
            throw (new IllegalArgumentException(var9.toString().toString())) as java.lang.Throwable;
         }
      } else {
         throw (new IllegalStateException("unit == null".toString())) as java.lang.Throwable;
      }
   } else {
      val var8: StringBuilder = new StringBuilder();
      var8.append(var0);
      var8.append(" < 0");
      throw (new IllegalStateException(var8.toString().toString())) as java.lang.Throwable;
   }
}

public fun checkOffsetAndCount(arrayLength: Long, offset: Long, count: Long) {
   if ((var2 or var4) < 0L || var2 > var0 || var0 - var2 < var4) {
      throw (new ArrayIndexOutOfBoundsException()) as java.lang.Throwable;
   }
}

public fun Closeable.closeQuietly() {
   Intrinsics.checkParameterIsNotNull(var0, "$this$closeQuietly");

   try {
      var0.close();
   } catch (var1: RuntimeException) {
      throw var1 as java.lang.Throwable;
   } catch (var2: Exception) {
   }
}

public fun ServerSocket.closeQuietly() {
   Intrinsics.checkParameterIsNotNull(var0, "$this$closeQuietly");

   try {
      var0.close();
   } catch (var1: RuntimeException) {
      throw var1 as java.lang.Throwable;
   } catch (var2: Exception) {
   }
}

public fun Socket.closeQuietly() {
   Intrinsics.checkParameterIsNotNull(var0, "$this$closeQuietly");

   try {
      var0.close();
   } catch (var1: AssertionError) {
      throw var1 as java.lang.Throwable;
   } catch (var2: RuntimeException) {
      throw var2 as java.lang.Throwable;
   } catch (var3: Exception) {
   }
}

public fun Array<String>.concat(value: String): Array<String> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$concat");
   Intrinsics.checkParameterIsNotNull(var1, "value");
   val var2: Array<Any> = Arrays.copyOf(var0, var0.length + 1);
   Intrinsics.checkExpressionValueIsNotNull(var2, "java.util.Arrays.copyOf(this, newSize)");
   var0 = var2 as Array<java.lang.String>;
   (var2 as Array<java.lang.String>)[ArraysKt.getLastIndex(var2 as Array<java.lang.String>)] = var1;
   if (var0 != null) {
      return var0;
   } else {
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
   }
}

public fun String.delimiterOffset(delimiter: Char, startIndex: Int = 0, endIndex: Int = var0.length()): Int {
   Intrinsics.checkParameterIsNotNull(var0, "$this$delimiterOffset");

   while (var2 < var3) {
      if (var0.charAt(var2) == var1) {
         return var2;
      }

      var2++;
   }

   return var3;
}

public fun String.delimiterOffset(delimiters: String, startIndex: Int = 0, endIndex: Int = var0.length()): Int {
   Intrinsics.checkParameterIsNotNull(var0, "$this$delimiterOffset");
   Intrinsics.checkParameterIsNotNull(var1, "delimiters");

   while (var2 < var3) {
      if (StringsKt.contains$default(var1, var0.charAt(var2), false, 2, null)) {
         return var2;
      }

      var2++;
   }

   return var3;
}

@JvmSynthetic
fun `delimiterOffset$default`(var0: java.lang.String, var1: Char, var2: Int, var3: Int, var4: Int, var5: Any): Int {
   if ((var4 and 2) != 0) {
      var2 = 0;
   }

   if ((var4 and 4) != 0) {
      var3 = var0.length();
   }

   return delimiterOffset(var0, var1, var2, var3);
}

@JvmSynthetic
fun `delimiterOffset$default`(var0: java.lang.String, var1: java.lang.String, var2: Int, var3: Int, var4: Int, var5: Any): Int {
   if ((var4 and 2) != 0) {
      var2 = 0;
   }

   if ((var4 and 4) != 0) {
      var3 = var0.length();
   }

   return delimiterOffset(var0, var1, var2, var3);
}

public fun Source.discard(timeout: Int, timeUnit: TimeUnit): Boolean {
   Intrinsics.checkParameterIsNotNull(var0, "$this$discard");
   Intrinsics.checkParameterIsNotNull(var2, "timeUnit");

   var var3: Boolean;
   try {
      var3 = skipAll(var0, var1, var2);
   } catch (var4: IOException) {
      var3 = false;
   }

   return var3;
}

public inline fun <T> Iterable<T>.filterList(predicate: (T) -> Boolean): List<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$filterList");
   Intrinsics.checkParameterIsNotNull(var1, "predicate");
   var var2: java.util.List = CollectionsKt.emptyList();
   val var3: java.util.Iterator = var0.iterator();
   var var5: java.util.List = var2;

   while (var3.hasNext()) {
      val var4: Any = var3.next();
      if (var1.invoke(var4) as java.lang.Boolean) {
         var2 = var5;
         if (var5.isEmpty()) {
            var2 = new ArrayList();
         }

         if (var2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableList<T>");
         }

         TypeIntrinsics.asMutableList(var2).add(var4);
         var5 = var2;
      }
   }

   return var5;
}

public fun format(format: String, vararg args: Any): String {
   Intrinsics.checkParameterIsNotNull(var0, "format");
   Intrinsics.checkParameterIsNotNull(var1, "args");
   val var2: StringCompanionObject = StringCompanionObject.INSTANCE;
   val var5: Locale = Locale.US;
   Intrinsics.checkExpressionValueIsNotNull(Locale.US, "Locale.US");
   var1 = Arrays.copyOf(var1, var1.length);
   var0 = java.lang.String.format(var5, var0, Arrays.copyOf(var1, var1.length));
   Intrinsics.checkExpressionValueIsNotNull(var0, "java.lang.String.format(locale, format, *args)");
   return var0;
}

public fun Array<String>.hasIntersection(other: Array<String>?, comparator: Comparator<in String>): Boolean {
   Intrinsics.checkParameterIsNotNull(var0, "$this$hasIntersection");
   Intrinsics.checkParameterIsNotNull(var2, "comparator");
   if (var0.length != 0 && var1 != null && var1.length != 0) {
      val var5: Int = var0.length;

      for (int var3 = 0; var3 < var5; var3++) {
         val var7: java.lang.String = var0[var3];
         val var6: Int = var1.length;

         for (int var4 = 0; var4 < var6; var4++) {
            if (var2.compare(var7, var1[var4]) == 0) {
               return true;
            }
         }
      }
   }

   return false;
}

public fun Response.headersContentLength(): Long {
   Intrinsics.checkParameterIsNotNull(var0, "$this$headersContentLength");
   val var3: java.lang.String = var0.headers().get("Content-Length");
   var var1: Long = -1L;
   if (var3 != null) {
      var1 = toLongOrDefault(var3, -1L);
   }

   return var1;
}

public inline fun ignoreIoExceptions(block: () -> Unit) {
   Intrinsics.checkParameterIsNotNull(var0, "block");

   try {
      var0.invoke();
   } catch (var1: IOException) {
   }
}

@SafeVarargs
public fun <T> immutableListOf(vararg elements: T): List<T> {
   Intrinsics.checkParameterIsNotNull(var0, "elements");
   var0 = var0.clone() as Array<Any>;
   val var2: java.util.List = Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(var0, var0.length)));
   Intrinsics.checkExpressionValueIsNotNull(var2, "Collections.unmodifiable…sList(*elements.clone()))");
   return var2;
}

public fun Array<String>.indexOf(value: String, comparator: Comparator<String>): Int {
   Intrinsics.checkParameterIsNotNull(var0, "$this$indexOf");
   Intrinsics.checkParameterIsNotNull(var1, "value");
   Intrinsics.checkParameterIsNotNull(var2, "comparator");
   val var4: Int = var0.length;
   var var3: Int = 0;

   while (true) {
      if (var3 >= var4) {
         var3 = -1;
         break;
      }

      if (var2.compare(var0[var3], var1) == 0) {
         break;
      }

      var3++;
   }

   return var3;
}

public fun String.indexOfControlOrNonAscii(): Int {
   Intrinsics.checkParameterIsNotNull(var0, "$this$indexOfControlOrNonAscii");
   val var2: Int = var0.length();

   for (int var1 = 0; var1 < var2; var1++) {
      val var3: Char = var0.charAt(var1);
      if (var3 <= 31 || var3 >= 127) {
         return var1;
      }
   }

   return -1;
}

public fun String.indexOfFirstNonAsciiWhitespace(startIndex: Int = 0, endIndex: Int = var0.length()): Int {
   Intrinsics.checkParameterIsNotNull(var0, "$this$indexOfFirstNonAsciiWhitespace");

   while (var1 < var2) {
      val var3: Char = var0.charAt(var1);
      if (var3 != '\t' && var3 != '\n' && var3 != '\f' && var3 != '\r' && var3 != ' ') {
         return var1;
      }

      var1++;
   }

   return var2;
}

@JvmSynthetic
fun `indexOfFirstNonAsciiWhitespace$default`(var0: java.lang.String, var1: Int, var2: Int, var3: Int, var4: Any): Int {
   if ((var3 and 1) != 0) {
      var1 = 0;
   }

   if ((var3 and 2) != 0) {
      var2 = var0.length();
   }

   return indexOfFirstNonAsciiWhitespace(var0, var1, var2);
}

public fun String.indexOfLastNonAsciiWhitespace(startIndex: Int = 0, endIndex: Int = var0.length()): Int {
   Intrinsics.checkParameterIsNotNull(var0, "$this$indexOfLastNonAsciiWhitespace");
   if (--var2 >= var1) {
      while (true) {
         val var3: Char = var0.charAt(var2);
         if (var3 != '\t' && var3 != '\n' && var3 != '\f' && var3 != '\r' && var3 != ' ') {
            return var2 + 1;
         }

         if (var2 == var1) {
            break;
         }

         var2--;
      }
   }

   return var1;
}

@JvmSynthetic
fun `indexOfLastNonAsciiWhitespace$default`(var0: java.lang.String, var1: Int, var2: Int, var3: Int, var4: Any): Int {
   if ((var3 and 1) != 0) {
      var1 = 0;
   }

   if ((var3 and 2) != 0) {
      var2 = var0.length();
   }

   return indexOfLastNonAsciiWhitespace(var0, var1, var2);
}

public fun String.indexOfNonWhitespace(startIndex: Int = 0): Int {
   Intrinsics.checkParameterIsNotNull(var0, "$this$indexOfNonWhitespace");
   val var2: Int = var0.length();

   while (var1 < var2) {
      val var3: Char = var0.charAt(var1);
      if (var3 != ' ' && var3 != '\t') {
         return var1;
      }

      var1++;
   }

   return var0.length();
}

@JvmSynthetic
fun `indexOfNonWhitespace$default`(var0: java.lang.String, var1: Int, var2: Int, var3: Any): Int {
   if ((var2 and 1) != 0) {
      var1 = 0;
   }

   return indexOfNonWhitespace(var0, var1);
}

public fun Array<String>.intersect(other: Array<String>, comparator: Comparator<in String>): Array<String> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$intersect");
   Intrinsics.checkParameterIsNotNull(var1, "other");
   Intrinsics.checkParameterIsNotNull(var2, "comparator");
   val var8: java.util.List = new ArrayList();
   val var5: Int = var0.length;

   for (int var3 = 0; var3 < var5; var3++) {
      val var7: java.lang.String = var0[var3];
      val var6: Int = var1.length;

      for (int var4 = 0; var4 < var6; var4++) {
         if (var2.compare(var7, var1[var4]) == 0) {
            var8.add(var7);
            break;
         }
      }
   }

   val var9: Array<Any> = var8.toArray(new java.lang.String[0]);
   if (var9 != null) {
      return var9 as Array<java.lang.String>;
   } else {
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
   }
}

public fun FileSystem.isCivilized(file: File): Boolean {
   // $VF: Couldn't be decompiled
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   // java.lang.RuntimeException: parsing failure!
   //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
   //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
   //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
   //
   // Bytecode:
   // 00: aload 0
   // 01: ldc_w "$this$isCivilized"
   // 04: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
   // 07: aload 1
   // 08: ldc_w "file"
   // 0b: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
   // 0e: aload 0
   // 0f: aload 1
   // 10: invokeinterface okhttp3/internal/io/FileSystem.sink (Ljava/io/File;)Lokio/Sink; 2
   // 15: checkcast java/io/Closeable
   // 18: astore 2
   // 19: aconst_null
   // 1a: checkcast java/lang/Throwable
   // 1d: astore 3
   // 1e: aload 2
   // 1f: checkcast okio/Sink
   // 22: astore 3
   // 23: aload 0
   // 24: aload 1
   // 25: invokeinterface okhttp3/internal/io/FileSystem.delete (Ljava/io/File;)V 2
   // 2a: aload 2
   // 2b: aconst_null
   // 2c: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 2f: bipush 1
   // 30: ireturn
   // 31: astore 3
   // 32: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
   // 35: astore 3
   // 36: aload 2
   // 37: aconst_null
   // 38: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 3b: aload 0
   // 3c: aload 1
   // 3d: invokeinterface okhttp3/internal/io/FileSystem.delete (Ljava/io/File;)V 2
   // 42: bipush 0
   // 43: ireturn
   // 44: astore 1
   // 45: aload 1
   // 46: athrow
   // 47: astore 0
   // 48: aload 2
   // 49: aload 1
   // 4a: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 4d: aload 0
   // 4e: athrow
}

public fun Socket.isHealthy(source: BufferedSource): Boolean {
   label34: {
      Intrinsics.checkParameterIsNotNull(var0, "$this$isHealthy");
      Intrinsics.checkParameterIsNotNull(var1, "source");

      var var2: Int;
      try {
         var2 = var0.getSoTimeout();
      } catch (var9: SocketTimeoutException) {
         return true;
      } catch (var10: IOException) {
         return false;
      }

      var var3: Boolean;
      try {
         var0.setSoTimeout(1);
         var3 = var1.exhausted();
      } catch (var8: java.lang.Throwable) {
         try {
            var0.setSoTimeout(var2);
         } catch (var4: SocketTimeoutException) {
            return true;
         } catch (var5: IOException) {
            return false;
         }
      }

      try {
         var0.setSoTimeout(var2);
      } catch (var6: SocketTimeoutException) {
         return true;
      } catch (var7: IOException) {
         return false;
      }

      return var3 xor true;
   }
}

public inline fun Any.notify() {
   Intrinsics.checkParameterIsNotNull(var0, "$this$notify");
   var0.notify();
}

public inline fun Any.notifyAll() {
   Intrinsics.checkParameterIsNotNull(var0, "$this$notifyAll");
   var0.notifyAll();
}

public fun Char.parseHexDigit(): Int {
   if (48 <= var0 && 57 >= var0) {
      var0 = var0 - 48;
   } else if (97 <= var0 && 102 >= var0) {
      var0 = var0 - 87;
   } else if (65 <= var0 && 70 >= var0) {
      var0 = var0 - 55;
   } else {
      var0 = -1;
   }

   return var0;
}

public fun Socket.peerName(): String {
   Intrinsics.checkParameterIsNotNull(var0, "$this$peerName");
   val var1: SocketAddress = var0.getRemoteSocketAddress();
   val var2: java.lang.String;
   if (var1 is InetSocketAddress) {
      var2 = (var1 as InetSocketAddress).getHostName();
      Intrinsics.checkExpressionValueIsNotNull(var2, "address.hostName");
   } else {
      var2 = var1.toString();
   }

   return var2;
}

@Throws(java/io/IOException::class)
public fun BufferedSource.readBomAsCharset(default: Charset): Charset {
   Intrinsics.checkParameterIsNotNull(var0, "$this$readBomAsCharset");
   Intrinsics.checkParameterIsNotNull(var1, "default");
   val var2: Int = var0.select(UNICODE_BOMS);
   if (var2 != -1) {
      if (var2 != 0) {
         if (var2 != 1) {
            if (var2 != 2) {
               if (var2 != 3) {
                  if (var2 != 4) {
                     throw (new AssertionError()) as java.lang.Throwable;
                  }

                  var1 = Charsets.INSTANCE.UTF32_LE();
               } else {
                  var1 = Charsets.INSTANCE.UTF32_BE();
               }
            } else {
               var1 = StandardCharsets.UTF_16LE;
               Intrinsics.checkExpressionValueIsNotNull(StandardCharsets.UTF_16LE, "UTF_16LE");
            }
         } else {
            var1 = StandardCharsets.UTF_16BE;
            Intrinsics.checkExpressionValueIsNotNull(StandardCharsets.UTF_16BE, "UTF_16BE");
         }
      } else {
         var1 = StandardCharsets.UTF_8;
         Intrinsics.checkExpressionValueIsNotNull(StandardCharsets.UTF_8, "UTF_8");
      }
   }

   return var1;
}

public fun <T> readFieldOrNull(instance: Any, fieldType: Class<T>, fieldName: String): T? {
   Intrinsics.checkParameterIsNotNull(var0, "instance");
   Intrinsics.checkParameterIsNotNull(var1, "fieldType");
   Intrinsics.checkParameterIsNotNull(var2, "fieldName");
   var var4: Class = var0.getClass();

   while (true) {
      if (var4 == Object::class.java) {
         if (!(var2 == "delegate")) {
            var0 = readFieldOrNull(var0, Object.class, "delegate");
            if (var0 != null) {
               return (T)readFieldOrNull(var0, var1, var2);
            }
         }

         return null;
      }

      label29: {
         var var10: Any;
         try {
            val var6: Field = var4.getDeclaredField(var2);
            Intrinsics.checkExpressionValueIsNotNull(var6, "field");
            var6.setAccessible(true);
            val var11: Any = var6.get(var0);
            if (!var1.isInstance(var11)) {
               break label29;
            }

            var10 = var1.cast(var11);
         } catch (var7: NoSuchFieldException) {
            var4 = var4.getSuperclass();
            Intrinsics.checkExpressionValueIsNotNull(var4, "c.superclass");
            continue;
         }

         var0 = var10;
         break;
      }

      var0 = null;
      break;
   }

   return (T)var0;
}

@Throws(java/io/IOException::class)
public fun BufferedSource.readMedium(): Int {
   Intrinsics.checkParameterIsNotNull(var0, "$this$readMedium");
   return and(var0.readByte(), 255) or and(var0.readByte(), 255) shl 16 or and(var0.readByte(), 255) shl 8;
}

public fun Buffer.skipAll(b: Byte): Int {
   Intrinsics.checkParameterIsNotNull(var0, "$this$skipAll");
   var var2: Int = 0;

   while (!var0.exhausted() && var0.getByte(0L) == var1) {
      var2++;
      var0.readByte();
   }

   return var2;
}

@Throws(java/io/IOException::class)
public fun Source.skipAll(duration: Int, timeUnit: TimeUnit): Boolean {
   // $VF: Couldn't be decompiled
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
   //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
   //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
   //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
   //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
   //
   // Bytecode:
   // 00: aload 0
   // 01: ldc_w "$this$skipAll"
   // 04: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
   // 07: aload 2
   // 08: ldc_w "timeUnit"
   // 0b: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
   // 0e: invokestatic java/lang/System.nanoTime ()J
   // 11: lstore 5
   // 13: aload 0
   // 14: invokeinterface okio/Source.timeout ()Lokio/Timeout; 1
   // 19: invokevirtual okio/Timeout.hasDeadline ()Z
   // 1c: ifeq 2f
   // 1f: aload 0
   // 20: invokeinterface okio/Source.timeout ()Lokio/Timeout; 1
   // 25: invokevirtual okio/Timeout.deadlineNanoTime ()J
   // 28: lload 5
   // 2a: lsub
   // 2b: lstore 3
   // 2c: goto 33
   // 2f: ldc2_w 9223372036854775807
   // 32: lstore 3
   // 33: aload 0
   // 34: invokeinterface okio/Source.timeout ()Lokio/Timeout; 1
   // 39: lload 3
   // 3a: aload 2
   // 3b: iload 1
   // 3c: i2l
   // 3d: invokevirtual java/util/concurrent/TimeUnit.toNanos (J)J
   // 40: invokestatic java/lang/Math.min (JJ)J
   // 43: lload 5
   // 45: ladd
   // 46: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
   // 49: pop
   // 4a: new okio/Buffer
   // 4d: astore 2
   // 4e: aload 2
   // 4f: invokespecial okio/Buffer.<init> ()V
   // 52: aload 0
   // 53: aload 2
   // 54: ldc2_w 8192
   // 57: invokeinterface okio/Source.read (Lokio/Buffer;J)J 4
   // 5c: ldc2_w -1
   // 5f: lcmp
   // 60: ifeq 6a
   // 63: aload 2
   // 64: invokevirtual okio/Buffer.clear ()V
   // 67: goto 52
   // 6a: lload 3
   // 6b: ldc2_w 9223372036854775807
   // 6e: lcmp
   // 6f: ifne 7f
   // 72: aload 0
   // 73: invokeinterface okio/Source.timeout ()Lokio/Timeout; 1
   // 78: invokevirtual okio/Timeout.clearDeadline ()Lokio/Timeout;
   // 7b: pop
   // 7c: goto 8d
   // 7f: aload 0
   // 80: invokeinterface okio/Source.timeout ()Lokio/Timeout; 1
   // 85: lload 5
   // 87: lload 3
   // 88: ladd
   // 89: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
   // 8c: pop
   // 8d: bipush 1
   // 8e: istore 7
   // 90: goto e0
   // 93: astore 2
   // 94: lload 3
   // 95: ldc2_w 9223372036854775807
   // 98: lcmp
   // 99: ifne a9
   // 9c: aload 0
   // 9d: invokeinterface okio/Source.timeout ()Lokio/Timeout; 1
   // a2: invokevirtual okio/Timeout.clearDeadline ()Lokio/Timeout;
   // a5: pop
   // a6: goto b7
   // a9: aload 0
   // aa: invokeinterface okio/Source.timeout ()Lokio/Timeout; 1
   // af: lload 5
   // b1: lload 3
   // b2: ladd
   // b3: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
   // b6: pop
   // b7: aload 2
   // b8: athrow
   // b9: astore 2
   // ba: lload 3
   // bb: ldc2_w 9223372036854775807
   // be: lcmp
   // bf: ifne cf
   // c2: aload 0
   // c3: invokeinterface okio/Source.timeout ()Lokio/Timeout; 1
   // c8: invokevirtual okio/Timeout.clearDeadline ()Lokio/Timeout;
   // cb: pop
   // cc: goto dd
   // cf: aload 0
   // d0: invokeinterface okio/Source.timeout ()Lokio/Timeout; 1
   // d5: lload 5
   // d7: lload 3
   // d8: ladd
   // d9: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
   // dc: pop
   // dd: bipush 0
   // de: istore 7
   // e0: iload 7
   // e2: ireturn
}

public fun threadFactory(name: String, daemon: Boolean): ThreadFactory {
   Intrinsics.checkParameterIsNotNull(var0, "name");
   return new ThreadFactory(var0, var1) {
      final boolean $daemon;
      final java.lang.String $name;

      {
         this.$name = var1;
         this.$daemon = var2;
      }

      @Override
      public final Thread newThread(Runnable var1) {
         val var2: Thread = new Thread(var1, this.$name);
         var2.setDaemon(this.$daemon);
         return var2;
      }
   };
}

public inline fun threadName(name: String, block: () -> Unit) {
   label13: {
      Intrinsics.checkParameterIsNotNull(var0, "name");
      Intrinsics.checkParameterIsNotNull(var1, "block");
      val var3: Thread = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(var3, "currentThread");
      val var2: java.lang.String = var3.getName();
      var3.setName(var0);

      try {
         var1.invoke();
      } catch (var4: java.lang.Throwable) {
         var3.setName(var2);
      }

      var3.setName(var2);
   }
}

public fun Headers.toHeaderList(): List<Header> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toHeaderList");
   val var3: java.lang.Iterable = RangesKt.until(0, var0.size());
   val var2: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));
   val var4: java.util.Iterator = var3.iterator();

   while (var4.hasNext()) {
      val var1: Int = (var4 as IntIterator).nextInt();
      var2.add(new Header(var0.name(var1), var0.value(var1)));
   }

   return var2 as MutableList<Header>;
}

public fun List<Header>.toHeaders(): Headers {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toHeaders");
   val var1: Headers.Builder = new Headers.Builder();

   for (Header var3 : var0) {
      var1.addLenient$okhttp(var3.component1().utf8(), var3.component2().utf8());
   }

   return var1.build();
}

public fun Int.toHexString(): String {
   val var1: java.lang.String = Integer.toHexString(var0);
   Intrinsics.checkExpressionValueIsNotNull(var1, "Integer.toHexString(this)");
   return var1;
}

public fun Long.toHexString(): String {
   val var2: java.lang.String = java.lang.Long.toHexString(var0);
   Intrinsics.checkExpressionValueIsNotNull(var2, "java.lang.Long.toHexString(this)");
   return var2;
}

public fun HttpUrl.toHostHeader(includeDefaultPort: Boolean = false): String {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toHostHeader");
   val var4: java.lang.String;
   if (StringsKt.contains$default(var0.host(), ":", false, 2, null)) {
      val var2: StringBuilder = new StringBuilder("[");
      var2.append(var0.host());
      var2.append(']');
      var4 = var2.toString();
   } else {
      var4 = var0.host();
   }

   if (!var1 && var0.port() == HttpUrl.Companion.defaultPort(var0.scheme())) {
      return var4;
   } else {
      val var5: StringBuilder = new StringBuilder();
      var5.append(var4);
      var5.append(':');
      var5.append(var0.port());
      return var5.toString();
   }
}

@JvmSynthetic
fun `toHostHeader$default`(var0: HttpUrl, var1: Boolean, var2: Int, var3: Any): java.lang.String {
   if ((var2 and 1) != 0) {
      var1 = false;
   }

   return toHostHeader(var0, var1);
}

public fun <T> List<T>.toImmutableList(): List<T> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toImmutableList");
   var0 = Collections.unmodifiableList(CollectionsKt.toMutableList(var0));
   Intrinsics.checkExpressionValueIsNotNull(var0, "Collections.unmodifiableList(toMutableList())");
   return var0;
}

public fun <K, V> Map<K, V>.toImmutableMap(): Map<K, V> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toImmutableMap");
   if (var0.isEmpty()) {
      var0 = MapsKt.emptyMap();
   } else {
      var0 = Collections.unmodifiableMap(new LinkedHashMap(var0));
      Intrinsics.checkExpressionValueIsNotNull(var0, "Collections.unmodifiableMap(LinkedHashMap(this))");
   }

   return var0;
}

public fun String.toLongOrDefault(defaultValue: Long): Long {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toLongOrDefault");

   var var3: Long;
   try {
      var3 = java.lang.Long.parseLong(var0);
   } catch (var5: NumberFormatException) {
      return var1;
   }

   return var3;
}

public fun String?.toNonNegativeInt(defaultValue: Int): Int {
   if (var0 != null) {
      var var2: Long;
      try {
         var2 = java.lang.Long.parseLong(var0);
      } catch (var4: NumberFormatException) {
         return var1;
      }

      var1 = Integer.MAX_VALUE;
      if (var2 <= Integer.MAX_VALUE) {
         if (var2 < 0L) {
            var1 = 0;
         } else {
            var1 = (int)var2;
         }
      }

      return var1;
   } else {
      return var1;
   }
}

public fun String.trimSubstring(startIndex: Int = 0, endIndex: Int = var0.length()): String {
   Intrinsics.checkParameterIsNotNull(var0, "$this$trimSubstring");
   var1 = indexOfFirstNonAsciiWhitespace(var0, var1, var2);
   var0 = var0.substring(var1, indexOfLastNonAsciiWhitespace(var0, var1, var2));
   Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.Strin…ing(startIndex, endIndex)");
   return var0;
}

@JvmSynthetic
fun `trimSubstring$default`(var0: java.lang.String, var1: Int, var2: Int, var3: Int, var4: Any): java.lang.String {
   if ((var3 and 1) != 0) {
      var1 = 0;
   }

   if ((var3 and 2) != 0) {
      var2 = var0.length();
   }

   return trimSubstring(var0, var1, var2);
}

public inline fun Any.wait() {
   Intrinsics.checkParameterIsNotNull(var0, "$this$wait");
   var0.wait();
}

public fun Exception.withSuppressed(suppressed: List<Exception>): Throwable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$withSuppressed");
   Intrinsics.checkParameterIsNotNull(var1, "suppressed");
   if (var1.size() > 1) {
      System.out.println(var1);
   }

   val var2: java.util.Iterator = var1.iterator();

   while (var2.hasNext()) {
      var0.addSuppressed(var2.next() as Exception);
   }

   return var0;
}

@Throws(java/io/IOException::class)
public fun BufferedSink.writeMedium(medium: Int) {
   Intrinsics.checkParameterIsNotNull(var0, "$this$writeMedium");
   var0.writeByte(var1 ushr 16 and 255);
   var0.writeByte(var1 ushr 8 and 255);
   var0.writeByte(var1 and 255);
}
