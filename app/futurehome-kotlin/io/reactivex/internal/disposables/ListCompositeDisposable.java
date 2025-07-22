package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class ListCompositeDisposable implements Disposable, DisposableContainer {
   volatile boolean disposed;
   List<Disposable> resources;

   public ListCompositeDisposable() {
   }

   public ListCompositeDisposable(Iterable<? extends Disposable> var1) {
      ObjectHelper.requireNonNull(var1, "resources is null");
      this.resources = new LinkedList<>();

      for (Disposable var3 : var1) {
         ObjectHelper.requireNonNull(var3, "Disposable item is null");
         this.resources.add(var3);
      }
   }

   public ListCompositeDisposable(Disposable... var1) {
      ObjectHelper.requireNonNull(var1, "resources is null");
      this.resources = new LinkedList<>();

      for (Disposable var4 : var1) {
         ObjectHelper.requireNonNull(var4, "Disposable item is null");
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
      // 01: ldc "d is null"
      // 03: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 06: pop
      // 07: aload 0
      // 08: getfield io/reactivex/internal/disposables/ListCompositeDisposable.disposed Z
      // 0b: ifne 45
      // 0e: aload 0
      // 0f: monitorenter
      // 10: aload 0
      // 11: getfield io/reactivex/internal/disposables/ListCompositeDisposable.disposed Z
      // 14: ifne 3b
      // 17: aload 0
      // 18: getfield io/reactivex/internal/disposables/ListCompositeDisposable.resources Ljava/util/List;
      // 1b: astore 3
      // 1c: aload 3
      // 1d: astore 2
      // 1e: aload 3
      // 1f: ifnonnull 2f
      // 22: new java/util/LinkedList
      // 25: astore 2
      // 26: aload 2
      // 27: invokespecial java/util/LinkedList.<init> ()V
      // 2a: aload 0
      // 2b: aload 2
      // 2c: putfield io/reactivex/internal/disposables/ListCompositeDisposable.resources Ljava/util/List;
      // 2f: aload 2
      // 30: aload 1
      // 31: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 36: pop
      // 37: aload 0
      // 38: monitorexit
      // 39: bipush 1
      // 3a: ireturn
      // 3b: aload 0
      // 3c: monitorexit
      // 3d: goto 45
      // 40: astore 1
      // 41: aload 0
      // 42: monitorexit
      // 43: aload 1
      // 44: athrow
      // 45: aload 1
      // 46: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
      // 4b: bipush 0
      // 4c: ireturn
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
      // 01: ldc "ds is null"
      // 03: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 06: pop
      // 07: aload 0
      // 08: getfield io/reactivex/internal/disposables/ListCompositeDisposable.disposed Z
      // 0b: istore 4
      // 0d: bipush 0
      // 0e: istore 2
      // 0f: iload 4
      // 11: ifne 6f
      // 14: aload 0
      // 15: monitorenter
      // 16: aload 0
      // 17: getfield io/reactivex/internal/disposables/ListCompositeDisposable.disposed Z
      // 1a: ifne 65
      // 1d: aload 0
      // 1e: getfield io/reactivex/internal/disposables/ListCompositeDisposable.resources Ljava/util/List;
      // 21: astore 6
      // 23: aload 6
      // 25: astore 5
      // 27: aload 6
      // 29: ifnonnull 3c
      // 2c: new java/util/LinkedList
      // 2f: astore 5
      // 31: aload 5
      // 33: invokespecial java/util/LinkedList.<init> ()V
      // 36: aload 0
      // 37: aload 5
      // 39: putfield io/reactivex/internal/disposables/ListCompositeDisposable.resources Ljava/util/List;
      // 3c: aload 1
      // 3d: arraylength
      // 3e: istore 3
      // 3f: iload 2
      // 40: iload 3
      // 41: if_icmpge 61
      // 44: aload 1
      // 45: iload 2
      // 46: aaload
      // 47: astore 6
      // 49: aload 6
      // 4b: ldc "d is null"
      // 4d: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 50: pop
      // 51: aload 5
      // 53: aload 6
      // 55: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 5a: pop
      // 5b: iinc 2 1
      // 5e: goto 3f
      // 61: aload 0
      // 62: monitorexit
      // 63: bipush 1
      // 64: ireturn
      // 65: aload 0
      // 66: monitorexit
      // 67: goto 6f
      // 6a: astore 1
      // 6b: aload 0
      // 6c: monitorexit
      // 6d: aload 1
      // 6e: athrow
      // 6f: aload 1
      // 70: arraylength
      // 71: istore 3
      // 72: bipush 0
      // 73: istore 2
      // 74: iload 2
      // 75: iload 3
      // 76: if_icmpge 87
      // 79: aload 1
      // 7a: iload 2
      // 7b: aaload
      // 7c: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
      // 81: iinc 2 1
      // 84: goto 74
      // 87: bipush 0
      // 88: ireturn
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
      // 01: getfield io/reactivex/internal/disposables/ListCompositeDisposable.disposed Z
      // 04: ifeq 08
      // 07: return
      // 08: aload 0
      // 09: monitorenter
      // 0a: aload 0
      // 0b: getfield io/reactivex/internal/disposables/ListCompositeDisposable.disposed Z
      // 0e: ifeq 14
      // 11: aload 0
      // 12: monitorexit
      // 13: return
      // 14: aload 0
      // 15: getfield io/reactivex/internal/disposables/ListCompositeDisposable.resources Ljava/util/List;
      // 18: astore 1
      // 19: aload 0
      // 1a: aconst_null
      // 1b: putfield io/reactivex/internal/disposables/ListCompositeDisposable.resources Ljava/util/List;
      // 1e: aload 0
      // 1f: monitorexit
      // 20: aload 0
      // 21: aload 1
      // 22: invokevirtual io/reactivex/internal/disposables/ListCompositeDisposable.dispose (Ljava/util/List;)V
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
      // 01: ldc "Disposable item is null"
      // 03: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 06: pop
      // 07: aload 0
      // 08: getfield io/reactivex/internal/disposables/ListCompositeDisposable.disposed Z
      // 0b: ifeq 10
      // 0e: bipush 0
      // 0f: ireturn
      // 10: aload 0
      // 11: monitorenter
      // 12: aload 0
      // 13: getfield io/reactivex/internal/disposables/ListCompositeDisposable.disposed Z
      // 16: ifeq 1d
      // 19: aload 0
      // 1a: monitorexit
      // 1b: bipush 0
      // 1c: ireturn
      // 1d: aload 0
      // 1e: getfield io/reactivex/internal/disposables/ListCompositeDisposable.resources Ljava/util/List;
      // 21: astore 2
      // 22: aload 2
      // 23: ifnull 37
      // 26: aload 2
      // 27: aload 1
      // 28: invokeinterface java/util/List.remove (Ljava/lang/Object;)Z 2
      // 2d: ifne 33
      // 30: goto 37
      // 33: aload 0
      // 34: monitorexit
      // 35: bipush 1
      // 36: ireturn
      // 37: aload 0
      // 38: monitorexit
      // 39: bipush 0
      // 3a: ireturn
      // 3b: astore 1
      // 3c: aload 0
      // 3d: monitorexit
      // 3e: aload 1
      // 3f: athrow
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
      // 01: getfield io/reactivex/internal/disposables/ListCompositeDisposable.disposed Z
      // 04: ifeq 08
      // 07: return
      // 08: aload 0
      // 09: monitorenter
      // 0a: aload 0
      // 0b: getfield io/reactivex/internal/disposables/ListCompositeDisposable.disposed Z
      // 0e: ifeq 14
      // 11: aload 0
      // 12: monitorexit
      // 13: return
      // 14: aload 0
      // 15: bipush 1
      // 16: putfield io/reactivex/internal/disposables/ListCompositeDisposable.disposed Z
      // 19: aload 0
      // 1a: getfield io/reactivex/internal/disposables/ListCompositeDisposable.resources Ljava/util/List;
      // 1d: astore 1
      // 1e: aload 0
      // 1f: aconst_null
      // 20: putfield io/reactivex/internal/disposables/ListCompositeDisposable.resources Ljava/util/List;
      // 23: aload 0
      // 24: monitorexit
      // 25: aload 0
      // 26: aload 1
      // 27: invokevirtual io/reactivex/internal/disposables/ListCompositeDisposable.dispose (Ljava/util/List;)V
      // 2a: return
      // 2b: astore 1
      // 2c: aload 0
      // 2d: monitorexit
      // 2e: aload 1
      // 2f: athrow
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   void dispose(List<Disposable> var1) {
      if (var1 != null) {
         Iterator var3 = var1.iterator();
         ArrayList var7 = null;

         while (var3.hasNext()) {
            Disposable var2 = (Disposable)var3.next();

            try {
               var2.dispose();
            } catch (Throwable var6) {
               Exceptions.throwIfFatal(var6);
               ArrayList var8 = var7;
               if (var7 == null) {
                  var8 = new ArrayList();
               }

               var8.add(var6);
               var7 = var8;
               continue;
            }
         }

         if (var7 != null) {
            if (var7.size() == 1) {
               throw ExceptionHelper.wrapOrThrow((Throwable)var7.get(0));
            } else {
               throw new CompositeException(var7);
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
}
