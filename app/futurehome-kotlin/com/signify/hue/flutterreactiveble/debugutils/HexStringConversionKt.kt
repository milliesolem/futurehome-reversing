package com.signify.hue.flutterreactiveble.debugutils

import java.util.Arrays
import kotlin.jvm.internal.StringCompanionObject

@JvmSynthetic
fun `$r8$lambda$aFAIQUIi71VdyabSzH4Yb3Vzo_M`(var0: Byte): java.lang.CharSequence {
   return toHexString$lambda$0(var0);
}

public fun ByteArray.toHexString(): String {
   return ArraysKt.joinToString$default(var0, "", null, null, 0, null, new HexStringConversionKt$$ExternalSyntheticLambda0(), 30, null);
}

fun `toHexString$lambda$0`(var0: Byte): java.lang.CharSequence {
   val var1: StringCompanionObject = StringCompanionObject.INSTANCE;
   val var2: java.lang.String = java.lang.String.format("%02x", Arrays.copyOf(new Object[]{var0}, 1));
   return var2;
}
