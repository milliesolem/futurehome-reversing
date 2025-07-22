package io.reactivex.disposables;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.OpenHashSet;
import java.util.ArrayList;

public final class CompositeDisposable implements Disposable, DisposableContainer {
   volatile boolean disposed;
   OpenHashSet<Disposable> resources;

   public CompositeDisposable() {
   }

   public CompositeDisposable(Iterable<? extends Disposable> var1) {
      ObjectHelper.requireNonNull(var1, "disposables is null");
      this.resources = new OpenHashSet<>();

      for (Disposable var3 : var1) {
         ObjectHelper.requireNonNull(var3, "A Disposable item in the disposables sequence is null");
         this.resources.add(var3);
      }
   }

   public CompositeDisposable(Disposable... var1) {
      ObjectHelper.requireNonNull(var1, "disposables is null");
      this.resources = new OpenHashSet<>(var1.length + 1);

      for (Disposable var4 : var1) {
         ObjectHelper.requireNonNull(var4, "A Disposable in the disposables array is null");
         this.resources.add(var4);
      }
   }

   @Override
   public boolean add(Disposable param1) {
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
      // 00: aload 1
      // 01: ldc "disposable is null"
      // 03: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 06: pop
      // 07: aload 0
      // 08: getfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 0b: ifne 43
      // 0e: aload 0
      // 0f: monitorenter
      // 10: aload 0
      // 11: getfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 14: ifne 39
      // 17: aload 0
      // 18: getfield io/reactivex/disposables/CompositeDisposable.resources Lio/reactivex/internal/util/OpenHashSet;
      // 1b: astore 3
      // 1c: aload 3
      // 1d: astore 2
      // 1e: aload 3
      // 1f: ifnonnull 2f
      // 22: new io/reactivex/internal/util/OpenHashSet
      // 25: astore 2
      // 26: aload 2
      // 27: invokespecial io/reactivex/internal/util/OpenHashSet.<init> ()V
      // 2a: aload 0
      // 2b: aload 2
      // 2c: putfield io/reactivex/disposables/CompositeDisposable.resources Lio/reactivex/internal/util/OpenHashSet;
      // 2f: aload 2
      // 30: aload 1
      // 31: invokevirtual io/reactivex/internal/util/OpenHashSet.add (Ljava/lang/Object;)Z
      // 34: pop
      // 35: aload 0
      // 36: monitorexit
      // 37: bipush 1
      // 38: ireturn
      // 39: aload 0
      // 3a: monitorexit
      // 3b: goto 43
      // 3e: astore 1
      // 3f: aload 0
      // 40: monitorexit
      // 41: aload 1
      // 42: athrow
      // 43: aload 1
      // 44: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
      // 49: bipush 0
      // 4a: ireturn
   }

   public boolean addAll(Disposable... param1) {
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
      // 00: aload 1
      // 01: ldc "disposables is null"
      // 03: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 06: pop
      // 07: aload 0
      // 08: getfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 0b: istore 4
      // 0d: bipush 0
      // 0e: istore 2
      // 0f: iload 4
      // 11: ifne 71
      // 14: aload 0
      // 15: monitorenter
      // 16: aload 0
      // 17: getfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 1a: ifne 67
      // 1d: aload 0
      // 1e: getfield io/reactivex/disposables/CompositeDisposable.resources Lio/reactivex/internal/util/OpenHashSet;
      // 21: astore 6
      // 23: aload 6
      // 25: astore 5
      // 27: aload 6
      // 29: ifnonnull 40
      // 2c: new io/reactivex/internal/util/OpenHashSet
      // 2f: astore 5
      // 31: aload 5
      // 33: aload 1
      // 34: arraylength
      // 35: bipush 1
      // 36: iadd
      // 37: invokespecial io/reactivex/internal/util/OpenHashSet.<init> (I)V
      // 3a: aload 0
      // 3b: aload 5
      // 3d: putfield io/reactivex/disposables/CompositeDisposable.resources Lio/reactivex/internal/util/OpenHashSet;
      // 40: aload 1
      // 41: arraylength
      // 42: istore 3
      // 43: iload 2
      // 44: iload 3
      // 45: if_icmpge 63
      // 48: aload 1
      // 49: iload 2
      // 4a: aaload
      // 4b: astore 6
      // 4d: aload 6
      // 4f: ldc "A Disposable in the disposables array is null"
      // 51: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 54: pop
      // 55: aload 5
      // 57: aload 6
      // 59: invokevirtual io/reactivex/internal/util/OpenHashSet.add (Ljava/lang/Object;)Z
      // 5c: pop
      // 5d: iinc 2 1
      // 60: goto 43
      // 63: aload 0
      // 64: monitorexit
      // 65: bipush 1
      // 66: ireturn
      // 67: aload 0
      // 68: monitorexit
      // 69: goto 71
      // 6c: astore 1
      // 6d: aload 0
      // 6e: monitorexit
      // 6f: aload 1
      // 70: athrow
      // 71: aload 1
      // 72: arraylength
      // 73: istore 3
      // 74: bipush 0
      // 75: istore 2
      // 76: iload 2
      // 77: iload 3
      // 78: if_icmpge 89
      // 7b: aload 1
      // 7c: iload 2
      // 7d: aaload
      // 7e: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
      // 83: iinc 2 1
      // 86: goto 76
      // 89: bipush 0
      // 8a: ireturn
   }

   public void clear() {
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
      // 01: getfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 04: ifeq 08
      // 07: return
      // 08: aload 0
      // 09: monitorenter
      // 0a: aload 0
      // 0b: getfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 0e: ifeq 14
      // 11: aload 0
      // 12: monitorexit
      // 13: return
      // 14: aload 0
      // 15: getfield io/reactivex/disposables/CompositeDisposable.resources Lio/reactivex/internal/util/OpenHashSet;
      // 18: astore 1
      // 19: aload 0
      // 1a: aconst_null
      // 1b: putfield io/reactivex/disposables/CompositeDisposable.resources Lio/reactivex/internal/util/OpenHashSet;
      // 1e: aload 0
      // 1f: monitorexit
      // 20: aload 0
      // 21: aload 1
      // 22: invokevirtual io/reactivex/disposables/CompositeDisposable.dispose (Lio/reactivex/internal/util/OpenHashSet;)V
      // 25: return
      // 26: astore 1
      // 27: aload 0
      // 28: monitorexit
      // 29: aload 1
      // 2a: athrow
   }

   @Override
   public boolean delete(Disposable param1) {
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
      // 00: aload 1
      // 01: ldc "disposables is null"
      // 03: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 06: pop
      // 07: aload 0
      // 08: getfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 0b: ifeq 10
      // 0e: bipush 0
      // 0f: ireturn
      // 10: aload 0
      // 11: monitorenter
      // 12: aload 0
      // 13: getfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 16: ifeq 1d
      // 19: aload 0
      // 1a: monitorexit
      // 1b: bipush 0
      // 1c: ireturn
      // 1d: aload 0
      // 1e: getfield io/reactivex/disposables/CompositeDisposable.resources Lio/reactivex/internal/util/OpenHashSet;
      // 21: astore 2
      // 22: aload 2
      // 23: ifnull 35
      // 26: aload 2
      // 27: aload 1
      // 28: invokevirtual io/reactivex/internal/util/OpenHashSet.remove (Ljava/lang/Object;)Z
      // 2b: ifne 31
      // 2e: goto 35
      // 31: aload 0
      // 32: monitorexit
      // 33: bipush 1
      // 34: ireturn
      // 35: aload 0
      // 36: monitorexit
      // 37: bipush 0
      // 38: ireturn
      // 39: astore 1
      // 3a: aload 0
      // 3b: monitorexit
      // 3c: aload 1
      // 3d: athrow
   }

   @Override
   public void dispose() {
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
      // 01: getfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 04: ifeq 08
      // 07: return
      // 08: aload 0
      // 09: monitorenter
      // 0a: aload 0
      // 0b: getfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 0e: ifeq 14
      // 11: aload 0
      // 12: monitorexit
      // 13: return
      // 14: aload 0
      // 15: bipush 1
      // 16: putfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 19: aload 0
      // 1a: getfield io/reactivex/disposables/CompositeDisposable.resources Lio/reactivex/internal/util/OpenHashSet;
      // 1d: astore 1
      // 1e: aload 0
      // 1f: aconst_null
      // 20: putfield io/reactivex/disposables/CompositeDisposable.resources Lio/reactivex/internal/util/OpenHashSet;
      // 23: aload 0
      // 24: monitorexit
      // 25: aload 0
      // 26: aload 1
      // 27: invokevirtual io/reactivex/disposables/CompositeDisposable.dispose (Lio/reactivex/internal/util/OpenHashSet;)V
      // 2a: return
      // 2b: astore 1
      // 2c: aload 0
      // 2d: monitorexit
      // 2e: aload 1
      // 2f: athrow
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   void dispose(OpenHashSet<Disposable> var1) {
      if (var1 != null) {
         Object[] var5 = var1.keys();
         int var3 = var5.length;
         ArrayList var9 = null;
         int var2 = 0;

         while (var2 < var3) {
            ArrayList var4;
            Object var6 = var5[var2];
            var4 = var9;
            label55:
            if (var6 instanceof Disposable) {
               try {
                  ((Disposable)var6).dispose();
               } catch (Throwable var8) {
                  Exceptions.throwIfFatal(var8);
                  var4 = var9;
                  if (var9 == null) {
                     var4 = new ArrayList();
                  }

                  var4.add(var8);
                  break label55;
               }

               var4 = var9;
            }

            var2++;
            var9 = var4;
         }

         if (var9 != null) {
            if (var9.size() == 1) {
               throw ExceptionHelper.wrapOrThrow((Throwable)var9.get(0));
            } else {
               throw new CompositeException(var9);
            }
         }
      }
   }

   @Override
   public boolean isDisposed() {
      return this.disposed;
   }

   @Override
   public boolean remove(Disposable var1) {
      if (this.delete(var1)) {
         var1.dispose();
         return true;
      } else {
         return false;
      }
   }

   public int size() {
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
      // 01: getfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 04: istore 2
      // 05: bipush 0
      // 06: istore 1
      // 07: iload 2
      // 08: ifeq 0d
      // 0b: bipush 0
      // 0c: ireturn
      // 0d: aload 0
      // 0e: monitorenter
      // 0f: aload 0
      // 10: getfield io/reactivex/disposables/CompositeDisposable.disposed Z
      // 13: ifeq 1a
      // 16: aload 0
      // 17: monitorexit
      // 18: bipush 0
      // 19: ireturn
      // 1a: aload 0
      // 1b: getfield io/reactivex/disposables/CompositeDisposable.resources Lio/reactivex/internal/util/OpenHashSet;
      // 1e: astore 3
      // 1f: aload 3
      // 20: ifnull 28
      // 23: aload 3
      // 24: invokevirtual io/reactivex/internal/util/OpenHashSet.size ()I
      // 27: istore 1
      // 28: aload 0
      // 29: monitorexit
      // 2a: iload 1
      // 2b: ireturn
      // 2c: astore 3
      // 2d: aload 0
      // 2e: monitorexit
      // 2f: aload 3
      // 30: athrow
   }
}
