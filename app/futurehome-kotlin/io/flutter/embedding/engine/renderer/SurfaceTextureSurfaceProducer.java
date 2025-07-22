package io.flutter.embedding.engine.renderer;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.view.Surface;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.view.TextureRegistry;

final class SurfaceTextureSurfaceProducer implements TextureRegistry.SurfaceProducer, TextureRegistry.GLTextureConsumer {
   private final FlutterJNI flutterJNI;
   private final Handler handler;
   private final long id;
   private boolean released;
   private int requestBufferWidth;
   private int requestedBufferHeight;
   private Surface surface;
   private final TextureRegistry.SurfaceTextureEntry texture;

   SurfaceTextureSurfaceProducer(long var1, Handler var3, FlutterJNI var4, TextureRegistry.SurfaceTextureEntry var5) {
      this.id = var1;
      this.handler = var3;
      this.flutterJNI = var4;
      this.texture = var5;
   }

   @Override
   protected void finalize() throws Throwable {
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
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/flutter/embedding/engine/renderer/SurfaceTextureSurfaceProducer.released Z
      // 04: istore 1
      // 05: iload 1
      // 06: ifeq 0e
      // 09: aload 0
      // 0a: invokespecial java/lang/Object.finalize ()V
      // 0d: return
      // 0e: aload 0
      // 0f: invokevirtual io/flutter/embedding/engine/renderer/SurfaceTextureSurfaceProducer.release ()V
      // 12: aload 0
      // 13: getfield io/flutter/embedding/engine/renderer/SurfaceTextureSurfaceProducer.handler Landroid/os/Handler;
      // 16: astore 2
      // 17: new io/flutter/embedding/engine/renderer/FlutterRenderer$TextureFinalizerRunnable
      // 1a: astore 3
      // 1b: aload 3
      // 1c: aload 0
      // 1d: getfield io/flutter/embedding/engine/renderer/SurfaceTextureSurfaceProducer.id J
      // 20: aload 0
      // 21: getfield io/flutter/embedding/engine/renderer/SurfaceTextureSurfaceProducer.flutterJNI Lio/flutter/embedding/engine/FlutterJNI;
      // 24: invokespecial io/flutter/embedding/engine/renderer/FlutterRenderer$TextureFinalizerRunnable.<init> (JLio/flutter/embedding/engine/FlutterJNI;)V
      // 27: aload 2
      // 28: aload 3
      // 29: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 2c: pop
      // 2d: aload 0
      // 2e: invokespecial java/lang/Object.finalize ()V
      // 31: return
      // 32: astore 2
      // 33: aload 0
      // 34: invokespecial java/lang/Object.finalize ()V
      // 37: aload 2
      // 38: athrow
   }

   @Override
   public int getHeight() {
      return this.requestedBufferHeight;
   }

   @Override
   public Surface getSurface() {
      if (this.surface == null) {
         this.surface = new Surface(this.texture.surfaceTexture());
      }

      return this.surface;
   }

   @Override
   public SurfaceTexture getSurfaceTexture() {
      return this.texture.surfaceTexture();
   }

   @Override
   public int getWidth() {
      return this.requestBufferWidth;
   }

   @Override
   public boolean handlesCropAndRotation() {
      return true;
   }

   @Override
   public long id() {
      return this.id;
   }

   @Override
   public void release() {
      this.texture.release();
      this.released = true;
   }

   @Override
   public void scheduleFrame() {
      this.flutterJNI.markTextureFrameAvailable(this.id);
   }

   @Override
   public void setCallback(TextureRegistry.SurfaceProducer.Callback var1) {
   }

   @Override
   public void setSize(int var1, int var2) {
      this.requestBufferWidth = var1;
      this.requestedBufferHeight = var2;
      this.getSurfaceTexture().setDefaultBufferSize(var1, var2);
   }
}
