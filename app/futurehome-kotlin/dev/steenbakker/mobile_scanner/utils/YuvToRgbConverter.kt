package dev.steenbakker.mobile_scanner.utils

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicYuvToRGB
import java.nio.ByteBuffer

public class YuvToRgbConverter(context: Context) {
   private final val rs: RenderScript
   private final val scriptYuvToRgb: ScriptIntrinsicYuvToRGB
   private final var yuvBits: ByteBuffer?
   private final var bytes: ByteArray
   private final var inputAllocation: Allocation?
   private final var outputAllocation: Allocation?

   init {
      val var2: RenderScript = RenderScript.create(var1);
      this.rs = var2;
      this.scriptYuvToRgb = ScriptIntrinsicYuvToRGB.create(var2, Element.U8_4(var2));
      this.bytes = new byte[0];
   }

   private fun needCreateAllocations(image: Image, yuvBuffer: YuvByteBuffer): Boolean {
      var var4: Allocation = this.inputAllocation;
      if (this.inputAllocation != null) {
         if (var4.getType().getX() == var1.getWidth()) {
            var4 = this.inputAllocation;
            if (var4.getType().getY() == var1.getHeight()) {
               val var5: Allocation = this.inputAllocation;
               if (var5.getType().getYuv() == var2.getType() && this.bytes.length != var2.getBuffer().capacity()) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   public fun yuvToRgb(image: Image, output: Bitmap) {
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
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 1
      // 003: ldc "image"
      // 005: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 008: aload 2
      // 009: ldc "output"
      // 00b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 00e: new dev/steenbakker/mobile_scanner/utils/YuvByteBuffer
      // 011: astore 3
      // 012: aload 3
      // 013: aload 1
      // 014: aload 0
      // 015: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.yuvBits Ljava/nio/ByteBuffer;
      // 018: invokespecial dev/steenbakker/mobile_scanner/utils/YuvByteBuffer.<init> (Landroid/media/Image;Ljava/nio/ByteBuffer;)V
      // 01b: aload 0
      // 01c: aload 3
      // 01d: invokevirtual dev/steenbakker/mobile_scanner/utils/YuvByteBuffer.getBuffer ()Ljava/nio/ByteBuffer;
      // 020: putfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.yuvBits Ljava/nio/ByteBuffer;
      // 023: aload 0
      // 024: aload 1
      // 025: aload 3
      // 026: invokespecial dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.needCreateAllocations (Landroid/media/Image;Ldev/steenbakker/mobile_scanner/utils/YuvByteBuffer;)Z
      // 029: ifeq 0b2
      // 02c: new android/renderscript/Type$Builder
      // 02f: astore 5
      // 031: aload 0
      // 032: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.rs Landroid/renderscript/RenderScript;
      // 035: astore 4
      // 037: aload 5
      // 039: aload 4
      // 03b: aload 4
      // 03d: invokestatic android/renderscript/Element.U8 (Landroid/renderscript/RenderScript;)Landroid/renderscript/Element;
      // 040: invokespecial android/renderscript/Type$Builder.<init> (Landroid/renderscript/RenderScript;Landroid/renderscript/Element;)V
      // 043: aload 5
      // 045: aload 1
      // 046: invokevirtual android/media/Image.getWidth ()I
      // 049: invokevirtual android/renderscript/Type$Builder.setX (I)Landroid/renderscript/Type$Builder;
      // 04c: aload 1
      // 04d: invokevirtual android/media/Image.getHeight ()I
      // 050: invokevirtual android/renderscript/Type$Builder.setY (I)Landroid/renderscript/Type$Builder;
      // 053: aload 3
      // 054: invokevirtual dev/steenbakker/mobile_scanner/utils/YuvByteBuffer.getType ()I
      // 057: invokevirtual android/renderscript/Type$Builder.setYuvFormat (I)Landroid/renderscript/Type$Builder;
      // 05a: astore 4
      // 05c: aload 0
      // 05d: aload 0
      // 05e: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.rs Landroid/renderscript/RenderScript;
      // 061: aload 4
      // 063: invokevirtual android/renderscript/Type$Builder.create ()Landroid/renderscript/Type;
      // 066: bipush 1
      // 067: invokestatic android/renderscript/Allocation.createTyped (Landroid/renderscript/RenderScript;Landroid/renderscript/Type;I)Landroid/renderscript/Allocation;
      // 06a: putfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.inputAllocation Landroid/renderscript/Allocation;
      // 06d: aload 0
      // 06e: aload 3
      // 06f: invokevirtual dev/steenbakker/mobile_scanner/utils/YuvByteBuffer.getBuffer ()Ljava/nio/ByteBuffer;
      // 072: invokevirtual java/nio/ByteBuffer.capacity ()I
      // 075: newarray 8
      // 077: putfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.bytes [B
      // 07a: new android/renderscript/Type$Builder
      // 07d: astore 4
      // 07f: aload 0
      // 080: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.rs Landroid/renderscript/RenderScript;
      // 083: astore 5
      // 085: aload 4
      // 087: aload 5
      // 089: aload 5
      // 08b: invokestatic android/renderscript/Element.RGBA_8888 (Landroid/renderscript/RenderScript;)Landroid/renderscript/Element;
      // 08e: invokespecial android/renderscript/Type$Builder.<init> (Landroid/renderscript/RenderScript;Landroid/renderscript/Element;)V
      // 091: aload 4
      // 093: aload 1
      // 094: invokevirtual android/media/Image.getWidth ()I
      // 097: invokevirtual android/renderscript/Type$Builder.setX (I)Landroid/renderscript/Type$Builder;
      // 09a: aload 1
      // 09b: invokevirtual android/media/Image.getHeight ()I
      // 09e: invokevirtual android/renderscript/Type$Builder.setY (I)Landroid/renderscript/Type$Builder;
      // 0a1: astore 1
      // 0a2: aload 0
      // 0a3: aload 0
      // 0a4: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.rs Landroid/renderscript/RenderScript;
      // 0a7: aload 1
      // 0a8: invokevirtual android/renderscript/Type$Builder.create ()Landroid/renderscript/Type;
      // 0ab: bipush 1
      // 0ac: invokestatic android/renderscript/Allocation.createTyped (Landroid/renderscript/RenderScript;Landroid/renderscript/Type;I)Landroid/renderscript/Allocation;
      // 0af: putfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.outputAllocation Landroid/renderscript/Allocation;
      // 0b2: aload 3
      // 0b3: invokevirtual dev/steenbakker/mobile_scanner/utils/YuvByteBuffer.getBuffer ()Ljava/nio/ByteBuffer;
      // 0b6: aload 0
      // 0b7: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.bytes [B
      // 0ba: invokevirtual java/nio/ByteBuffer.get ([B)Ljava/nio/ByteBuffer;
      // 0bd: pop
      // 0be: aload 0
      // 0bf: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.inputAllocation Landroid/renderscript/Allocation;
      // 0c2: astore 1
      // 0c3: aload 1
      // 0c4: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 0c7: aload 1
      // 0c8: aload 0
      // 0c9: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.bytes [B
      // 0cc: invokevirtual android/renderscript/Allocation.copyFrom ([B)V
      // 0cf: aload 0
      // 0d0: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.inputAllocation Landroid/renderscript/Allocation;
      // 0d3: astore 1
      // 0d4: aload 1
      // 0d5: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 0d8: aload 1
      // 0d9: aload 0
      // 0da: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.bytes [B
      // 0dd: invokevirtual android/renderscript/Allocation.copyFrom ([B)V
      // 0e0: aload 0
      // 0e1: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.scriptYuvToRgb Landroid/renderscript/ScriptIntrinsicYuvToRGB;
      // 0e4: aload 0
      // 0e5: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.inputAllocation Landroid/renderscript/Allocation;
      // 0e8: invokevirtual android/renderscript/ScriptIntrinsicYuvToRGB.setInput (Landroid/renderscript/Allocation;)V
      // 0eb: aload 0
      // 0ec: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.scriptYuvToRgb Landroid/renderscript/ScriptIntrinsicYuvToRGB;
      // 0ef: aload 0
      // 0f0: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.outputAllocation Landroid/renderscript/Allocation;
      // 0f3: invokevirtual android/renderscript/ScriptIntrinsicYuvToRGB.forEach (Landroid/renderscript/Allocation;)V
      // 0f6: aload 0
      // 0f7: getfield dev/steenbakker/mobile_scanner/utils/YuvToRgbConverter.outputAllocation Landroid/renderscript/Allocation;
      // 0fa: astore 1
      // 0fb: aload 1
      // 0fc: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 0ff: aload 1
      // 100: aload 2
      // 101: invokevirtual android/renderscript/Allocation.copyTo (Landroid/graphics/Bitmap;)V
      // 104: aload 0
      // 105: monitorexit
      // 106: return
      // 107: astore 1
      // 108: aload 0
      // 109: monitorexit
      // 10a: aload 1
      // 10b: athrow
   }
}
