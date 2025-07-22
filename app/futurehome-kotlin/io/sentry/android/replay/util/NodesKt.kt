package io.sentry.android.replay.util

import android.graphics.Rect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.OffsetKt
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.LayoutCoordinatesKt
import androidx.compose.ui.node.LayoutNode
import androidx.compose.ui.unit.IntSize

internal fun LayoutCoordinates.boundsInWindow(rootCoordinates: LayoutCoordinates?): Rect {
   var var17: LayoutCoordinates = var1;
   if (var1 == null) {
      var17 = LayoutCoordinatesKt.findRootCoordinates(var0);
   }

   var var6: Float = IntSize.getWidth-impl(var17.getSize-YbymL2g());
   var var3: Float = IntSize.getHeight-impl(var17.getSize-YbymL2g());
   val var18: androidx.compose.ui.geometry.Rect = LayoutCoordinates.localBoundingBoxOf$default(var17, var0, false, 2, null);
   var var4: Float = var18.getLeft();
   var var2: Float = var4;
   if (var4 < 0.0F) {
      var2 = 0.0F;
   }

   var4 = var2;
   if (var2 > var6) {
      var4 = var6;
   }

   var var5: Float = var18.getTop();
   var2 = var5;
   if (var5 < 0.0F) {
      var2 = 0.0F;
   }

   var5 = var2;
   if (var2 > var3) {
      var5 = var3;
   }

   var var8: Float = var18.getRight();
   var2 = var8;
   if (var8 < 0.0F) {
      var2 = 0.0F;
   }

   if (!(var2 > var6)) {
      var6 = var2;
   }

   var2 = var18.getBottom();
   if (var2 < 0.0F) {
      var2 = 0.0F;
   }

   if (var2 > var3) {
      var2 = var3;
   }

   if (var4 != var6 && var5 != var2) {
      val var11: Long = var17.localToWindow-MK-Hz9U(OffsetKt.Offset(var4, var5));
      val var15: Long = var17.localToWindow-MK-Hz9U(OffsetKt.Offset(var6, var5));
      val var13: Long = var17.localToWindow-MK-Hz9U(OffsetKt.Offset(var6, var2));
      val var9: Long = var17.localToWindow-MK-Hz9U(OffsetKt.Offset(var4, var2));
      var3 = Offset.getX-impl(var11);
      var6 = Offset.getX-impl(var15);
      var4 = Offset.getX-impl(var9);
      var5 = Offset.getX-impl(var13);
      var2 = Math.min(var3, Math.min(var6, Math.min(var4, var5)));
      var4 = Math.max(var3, Math.max(var6, Math.max(var4, var5)));
      var6 = Offset.getY-impl(var11);
      var8 = Offset.getY-impl(var15);
      val var34: Float = Offset.getY-impl(var9);
      var5 = Offset.getY-impl(var13);
      return new Rect(
         (int)var2, (int)Math.min(var6, Math.min(var8, Math.min(var34, var5))), (int)var4, (int)Math.max(var6, Math.max(var8, Math.max(var34, var5)))
      );
   } else {
      return new Rect();
   }
}

private inline fun Float.fastCoerceAtLeast(minimumValue: Float): Float {
   var var2: Float = var0;
   if (var0 < var1) {
      var2 = var1;
   }

   return var2;
}

private inline fun Float.fastCoerceAtMost(maximumValue: Float): Float {
   var var2: Float = var0;
   if (var0 > var1) {
      var2 = var1;
   }

   return var2;
}

private inline fun Float.fastCoerceIn(minimumValue: Float, maximumValue: Float): Float {
   var var3: Float = var0;
   if (var0 < var1) {
      var3 = var1;
   }

   if (!(var3 > var2)) {
      var2 = var3;
   }

   return var2;
}

private inline fun fastMaxOf(a: Float, b: Float, c: Float, d: Float): Float {
   return Math.max(var0, Math.max(var1, Math.max(var2, var3)));
}

private inline fun fastMinOf(a: Float, b: Float, c: Float, d: Float): Float {
   return Math.min(var0, Math.min(var1, Math.min(var2, var3)));
}

internal fun LayoutNode.findPainter(): Painter? {
   // $VF: Couldn't be decompiled
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
   //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
   //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
   //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
   //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
   //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
   //
   // Bytecode:
   // 00: aload 0
   // 01: ldc "<this>"
   // 03: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
   // 06: aload 0
   // 07: invokevirtual androidx/compose/ui/node/LayoutNode.getModifierInfo ()Ljava/util/List;
   // 0a: astore 4
   // 0c: aload 4
   // 0e: invokeinterface java/util/List.size ()I 1
   // 13: istore 2
   // 14: bipush 0
   // 15: istore 1
   // 16: aconst_null
   // 17: astore 3
   // 18: iload 1
   // 19: iload 2
   // 1a: if_icmpge 80
   // 1d: aload 4
   // 1f: iload 1
   // 20: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
   // 25: checkcast androidx/compose/ui/layout/ModifierInfo
   // 28: invokevirtual androidx/compose/ui/layout/ModifierInfo.getModifier ()Landroidx/compose/ui/Modifier;
   // 2b: astore 0
   // 2c: aload 0
   // 2d: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
   // 30: invokevirtual java/lang/Class.getName ()Ljava/lang/String;
   // 33: astore 5
   // 35: aload 5
   // 37: ldc "modifier::class.java.name"
   // 39: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
   // 3c: aload 5
   // 3e: checkcast java/lang/CharSequence
   // 41: ldc "Painter"
   // 43: checkcast java/lang/CharSequence
   // 46: bipush 0
   // 47: bipush 2
   // 48: aconst_null
   // 49: invokestatic kotlin/text/StringsKt.contains$default (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
   // 4c: ifeq 7a
   // 4f: aload 0
   // 50: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
   // 53: ldc "painter"
   // 55: invokevirtual java/lang/Class.getDeclaredField (Ljava/lang/String;)Ljava/lang/reflect/Field;
   // 58: astore 4
   // 5a: aload 4
   // 5c: bipush 1
   // 5d: invokevirtual java/lang/reflect/Field.setAccessible (Z)V
   // 60: aload 4
   // 62: aload 0
   // 63: invokevirtual java/lang/reflect/Field.get (Ljava/lang/Object;)Ljava/lang/Object;
   // 66: astore 4
   // 68: aload 3
   // 69: astore 0
   // 6a: aload 4
   // 6c: instanceof androidx/compose/ui/graphics/painter/Painter
   // 6f: ifeq 78
   // 72: aload 4
   // 74: checkcast androidx/compose/ui/graphics/painter/Painter
   // 77: astore 0
   // 78: aload 0
   // 79: areturn
   // 7a: iinc 1 1
   // 7d: goto 16
   // 80: aconst_null
   // 81: areturn
   // 82: astore 0
   // 83: aload 3
   // 84: astore 0
   // 85: goto 78
}

internal fun LayoutNode.findTextAttributes(): TextAttributes {
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
   // 00: aload 0
   // 01: ldc "<this>"
   // 03: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
   // 06: aload 0
   // 07: invokevirtual androidx/compose/ui/node/LayoutNode.getModifierInfo ()Ljava/util/List;
   // 0a: astore 5
   // 0c: aload 5
   // 0e: invokeinterface java/util/List.size ()I 1
   // 13: istore 2
   // 14: aconst_null
   // 15: astore 4
   // 17: bipush 0
   // 18: istore 1
   // 19: bipush 0
   // 1a: istore 3
   // 1b: iload 1
   // 1c: iload 2
   // 1d: if_icmpge b6
   // 20: aload 5
   // 22: iload 1
   // 23: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
   // 28: checkcast androidx/compose/ui/layout/ModifierInfo
   // 2b: invokevirtual androidx/compose/ui/layout/ModifierInfo.getModifier ()Landroidx/compose/ui/Modifier;
   // 2e: astore 0
   // 2f: aload 0
   // 30: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
   // 33: invokevirtual java/lang/Class.getName ()Ljava/lang/String;
   // 36: astore 6
   // 38: aload 6
   // 3a: ldc "modifierClassName"
   // 3c: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
   // 3f: aload 6
   // 41: checkcast java/lang/CharSequence
   // 44: astore 6
   // 46: aload 6
   // 48: ldc "Text"
   // 4a: checkcast java/lang/CharSequence
   // 4d: bipush 0
   // 4e: bipush 2
   // 4f: aconst_null
   // 50: invokestatic kotlin/text/StringsKt.contains$default (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
   // 53: ifeq 95
   // 56: aload 0
   // 57: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
   // 5a: ldc "color"
   // 5c: invokevirtual java/lang/Class.getDeclaredField (Ljava/lang/String;)Ljava/lang/reflect/Field;
   // 5f: astore 4
   // 61: aload 4
   // 63: bipush 1
   // 64: invokevirtual java/lang/reflect/Field.setAccessible (Z)V
   // 67: aload 4
   // 69: aload 0
   // 6a: invokevirtual java/lang/reflect/Field.get (Ljava/lang/Object;)Ljava/lang/Object;
   // 6d: astore 0
   // 6e: aload 0
   // 6f: instanceof androidx/compose/ui/graphics/ColorProducer
   // 72: ifeq 7d
   // 75: aload 0
   // 76: checkcast androidx/compose/ui/graphics/ColorProducer
   // 79: astore 0
   // 7a: goto 7f
   // 7d: aconst_null
   // 7e: astore 0
   // 7f: aload 0
   // 80: ifnull 90
   // 83: aload 0
   // 84: invokeinterface androidx/compose/ui/graphics/ColorProducer.invoke-0d7_KjU ()J 1
   // 89: invokestatic androidx/compose/ui/graphics/Color.box-impl (J)Landroidx/compose/ui/graphics/Color;
   // 8c: astore 0
   // 8d: goto ad
   // 90: aconst_null
   // 91: astore 0
   // 92: goto ad
   // 95: aload 4
   // 97: astore 0
   // 98: aload 6
   // 9a: ldc "Fill"
   // 9c: checkcast java/lang/CharSequence
   // 9f: bipush 0
   // a0: bipush 2
   // a1: aconst_null
   // a2: invokestatic kotlin/text/StringsKt.contains$default (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
   // a5: ifeq ad
   // a8: bipush 1
   // a9: istore 3
   // aa: aload 4
   // ac: astore 0
   // ad: iinc 1 1
   // b0: aload 0
   // b1: astore 4
   // b3: goto 1b
   // b6: new io/sentry/android/replay/util/TextAttributes
   // b9: dup
   // ba: aload 4
   // bc: iload 3
   // bd: aconst_null
   // be: invokespecial io/sentry/android/replay/util/TextAttributes.<init> (Landroidx/compose/ui/graphics/Color;ZLkotlin/jvm/internal/DefaultConstructorMarker;)V
   // c1: areturn
   // c2: astore 0
   // c3: goto 90
}

internal fun Painter.isMaskable(): Boolean {
   val var4: java.lang.String = var0.getClass().getName();
   val var3: java.lang.CharSequence = var4;
   val var5: java.lang.CharSequence = "Vector";
   var var1: Boolean = false;
   if (!StringsKt.contains$default(var3, var5, false, 2, null)) {
      var1 = false;
      if (!StringsKt.contains$default(var3, "Color", false, 2, null)) {
         var1 = false;
         if (!StringsKt.contains$default(var3, "Brush", false, 2, null)) {
            var1 = true;
         }
      }
   }

   return var1;
}
