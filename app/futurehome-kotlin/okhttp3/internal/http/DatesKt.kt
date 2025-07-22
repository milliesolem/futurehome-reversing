package okhttp3.internal.http

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util

private final val BROWSER_COMPATIBLE_DATE_FORMATS: Array<DateFormat?>
private final val BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS: Array<String>
internal const val MAX_DATE: Long = 253402300799999L

private final val STANDARD_DATE_FORMAT: <unrepresentable> = new ThreadLocal<DateFormat>() {
   protected DateFormat initialValue() {
      val var1: SimpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
      var1.setLenient(false);
      var1.setTimeZone(Util.UTC);
      return var1;
   }
}
private DateFormat[] BROWSER_COMPATIBLE_DATE_FORMATS;
private java.lang.String[] BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;

internal fun String.toHttpDateOrNull(): Date? {
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
   // 00: aload 0
   // 01: ldc "$this$toHttpDateOrNull"
   // 03: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
   // 06: aload 0
   // 07: checkcast java/lang/CharSequence
   // 0a: invokeinterface java/lang/CharSequence.length ()I 1
   // 0f: ifne 14
   // 12: aconst_null
   // 13: areturn
   // 14: new java/text/ParsePosition
   // 17: dup
   // 18: bipush 0
   // 19: invokespecial java/text/ParsePosition.<init> (I)V
   // 1c: astore 7
   // 1e: getstatic okhttp3/internal/http/DatesKt.STANDARD_DATE_FORMAT Lokhttp3/internal/http/DatesKt$STANDARD_DATE_FORMAT$1;
   // 21: invokevirtual okhttp3/internal/http/DatesKt$STANDARD_DATE_FORMAT$1.get ()Ljava/lang/Object;
   // 24: checkcast java/text/DateFormat
   // 27: aload 0
   // 28: aload 7
   // 2a: invokevirtual java/text/DateFormat.parse (Ljava/lang/String;Ljava/text/ParsePosition;)Ljava/util/Date;
   // 2d: astore 4
   // 2f: aload 7
   // 31: invokevirtual java/text/ParsePosition.getIndex ()I
   // 34: aload 0
   // 35: invokevirtual java/lang/String.length ()I
   // 38: if_icmpne 3e
   // 3b: aload 4
   // 3d: areturn
   // 3e: getstatic okhttp3/internal/http/DatesKt.BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS [Ljava/lang/String;
   // 41: astore 6
   // 43: aload 6
   // 45: monitorenter
   // 46: aload 6
   // 48: arraylength
   // 49: istore 2
   // 4a: bipush 0
   // 4b: istore 1
   // 4c: iload 1
   // 4d: iload 2
   // 4e: if_icmpge b2
   // 51: getstatic okhttp3/internal/http/DatesKt.BROWSER_COMPATIBLE_DATE_FORMATS [Ljava/text/DateFormat;
   // 54: astore 8
   // 56: aload 8
   // 58: iload 1
   // 59: aaload
   // 5a: astore 5
   // 5c: aload 5
   // 5e: astore 4
   // 60: aload 5
   // 62: ifnonnull 8c
   // 65: new java/text/SimpleDateFormat
   // 68: astore 4
   // 6a: aload 4
   // 6c: getstatic okhttp3/internal/http/DatesKt.BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS [Ljava/lang/String;
   // 6f: iload 1
   // 70: aaload
   // 71: getstatic java/util/Locale.US Ljava/util/Locale;
   // 74: invokespecial java/text/SimpleDateFormat.<init> (Ljava/lang/String;Ljava/util/Locale;)V
   // 77: aload 4
   // 79: getstatic okhttp3/internal/Util.UTC Ljava/util/TimeZone;
   // 7c: invokevirtual java/text/SimpleDateFormat.setTimeZone (Ljava/util/TimeZone;)V
   // 7f: aload 4
   // 81: checkcast java/text/DateFormat
   // 84: astore 4
   // 86: aload 8
   // 88: iload 1
   // 89: aload 4
   // 8b: aastore
   // 8c: aload 7
   // 8e: bipush 0
   // 8f: invokevirtual java/text/ParsePosition.setIndex (I)V
   // 92: aload 4
   // 94: aload 0
   // 95: aload 7
   // 97: invokevirtual java/text/DateFormat.parse (Ljava/lang/String;Ljava/text/ParsePosition;)Ljava/util/Date;
   // 9a: astore 4
   // 9c: aload 7
   // 9e: invokevirtual java/text/ParsePosition.getIndex ()I
   // a1: istore 3
   // a2: iload 3
   // a3: ifeq ac
   // a6: aload 6
   // a8: monitorexit
   // a9: aload 4
   // ab: areturn
   // ac: iinc 1 1
   // af: goto 4c
   // b2: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
   // b5: astore 0
   // b6: aload 6
   // b8: monitorexit
   // b9: aconst_null
   // ba: areturn
   // bb: astore 0
   // bc: aload 6
   // be: monitorexit
   // bf: aload 0
   // c0: athrow
}

internal fun Date.toHttpDateString(): String {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toHttpDateString");
   val var1: java.lang.String = STANDARD_DATE_FORMAT.get().format(var0);
   Intrinsics.checkExpressionValueIsNotNull(var1, "STANDARD_DATE_FORMAT.get().format(this)");
   return var1;
}
