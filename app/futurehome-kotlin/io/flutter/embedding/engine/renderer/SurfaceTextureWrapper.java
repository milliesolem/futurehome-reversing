package io.flutter.embedding.engine.renderer;

import android.graphics.SurfaceTexture;

public class SurfaceTextureWrapper {
   private boolean attached;
   private boolean newFrameAvailable = false;
   private Runnable onFrameConsumed;
   private boolean released;
   private SurfaceTexture surfaceTexture;

   public SurfaceTextureWrapper(SurfaceTexture var1) {
      this(var1, null);
   }

   public SurfaceTextureWrapper(SurfaceTexture var1, Runnable var2) {
      this.surfaceTexture = var1;
      this.released = false;
      this.onFrameConsumed = var2;
   }

   public void attachToGLContext(int param1) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.released Z
      // 06: ifeq 0c
      // 09: aload 0
      // 0a: monitorexit
      // 0b: return
      // 0c: aload 0
      // 0d: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.attached Z
      // 10: ifeq 1a
      // 13: aload 0
      // 14: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.surfaceTexture Landroid/graphics/SurfaceTexture;
      // 17: invokevirtual android/graphics/SurfaceTexture.detachFromGLContext ()V
      // 1a: aload 0
      // 1b: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.surfaceTexture Landroid/graphics/SurfaceTexture;
      // 1e: iload 1
      // 1f: invokevirtual android/graphics/SurfaceTexture.attachToGLContext (I)V
      // 22: aload 0
      // 23: bipush 1
      // 24: putfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.attached Z
      // 27: aload 0
      // 28: monitorexit
      // 29: return
      // 2a: astore 2
      // 2b: aload 0
      // 2c: monitorexit
      // 2d: aload 2
      // 2e: athrow
   }

   public void detachFromGLContext() {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.attached Z
      // 06: ifeq 1c
      // 09: aload 0
      // 0a: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.released Z
      // 0d: ifne 1c
      // 10: aload 0
      // 11: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.surfaceTexture Landroid/graphics/SurfaceTexture;
      // 14: invokevirtual android/graphics/SurfaceTexture.detachFromGLContext ()V
      // 17: aload 0
      // 18: bipush 0
      // 19: putfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.attached Z
      // 1c: aload 0
      // 1d: monitorexit
      // 1e: return
      // 1f: astore 1
      // 20: aload 0
      // 21: monitorexit
      // 22: aload 1
      // 23: athrow
   }

   public void getTransformMatrix(float[] var1) {
      this.surfaceTexture.getTransformMatrix(var1);
   }

   public void markDirty() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 0: aload 0
      // 1: monitorenter
      // 2: aload 0
      // 3: bipush 1
      // 4: putfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.newFrameAvailable Z
      // 7: aload 0
      // 8: monitorexit
      // 9: return
      // a: astore 1
      // b: aload 0
      // c: monitorexit
      // d: aload 1
      // e: athrow
   }

   public void release() {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.released Z
      // 06: ifne 1a
      // 09: aload 0
      // 0a: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.surfaceTexture Landroid/graphics/SurfaceTexture;
      // 0d: invokevirtual android/graphics/SurfaceTexture.release ()V
      // 10: aload 0
      // 11: bipush 1
      // 12: putfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.released Z
      // 15: aload 0
      // 16: bipush 0
      // 17: putfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.attached Z
      // 1a: aload 0
      // 1b: monitorexit
      // 1c: return
      // 1d: astore 1
      // 1e: aload 0
      // 1f: monitorexit
      // 20: aload 1
      // 21: athrow
   }

   public boolean shouldUpdate() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 0: aload 0
      // 1: monitorenter
      // 2: aload 0
      // 3: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.newFrameAvailable Z
      // 6: istore 1
      // 7: aload 0
      // 8: monitorexit
      // 9: iload 1
      // a: ireturn
      // b: astore 2
      // c: aload 0
      // d: monitorexit
      // e: aload 2
      // f: athrow
   }

   public SurfaceTexture surfaceTexture() {
      return this.surfaceTexture;
   }

   public void updateTexImage() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.util.collections.fixed.FastFixedSet.contains(Object)" because "predset" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.lambda$removeErroneousNodes$1(FastExtendedPostdominanceHelper.java:231)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.iterateReachability(FastExtendedPostdominanceHelper.java:373)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.removeErroneousNodes(FastExtendedPostdominanceHelper.java:207)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.FastExtendedPostdominanceHelper.getExtendedPostdominators(FastExtendedPostdominanceHelper.java:63)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.findGeneralStatement(DomHelper.java:537)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.processStatement(DomHelper.java:472)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.processStatement(DomHelper.java:379)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:208)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: bipush 0
      // 04: putfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.newFrameAvailable Z
      // 07: aload 0
      // 08: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.released Z
      // 0b: ifne 24
      // 0e: aload 0
      // 0f: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.surfaceTexture Landroid/graphics/SurfaceTexture;
      // 12: invokevirtual android/graphics/SurfaceTexture.updateTexImage ()V
      // 15: aload 0
      // 16: getfield io/flutter/embedding/engine/renderer/SurfaceTextureWrapper.onFrameConsumed Ljava/lang/Runnable;
      // 19: astore 1
      // 1a: aload 1
      // 1b: ifnull 24
      // 1e: aload 1
      // 1f: invokeinterface java/lang/Runnable.run ()V 1
      // 24: aload 0
      // 25: monitorexit
      // 26: return
      // 27: astore 1
      // 28: aload 0
      // 29: monitorexit
      // 2a: aload 1
      // 2b: athrow
   }
}
