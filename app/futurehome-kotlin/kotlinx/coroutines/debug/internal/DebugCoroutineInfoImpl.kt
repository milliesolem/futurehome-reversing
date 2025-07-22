package kotlinx.coroutines.debug.internal

import java.lang.ref.WeakReference
import java.util.ArrayList
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlin.jvm.functions.Function2

internal class DebugCoroutineInfoImpl internal constructor(context: CoroutineContext?, creationStackBottom: StackTraceFrame?, sequenceNumber: Long) {
   private final val _context: WeakReference<CoroutineContext?>

   public final var _lastObservedFrame: WeakReference<CoroutineStackFrame>?
      private set

   public final var _state: String
      private set

   public final val context: CoroutineContext?
      public final get() {
         return this._context.get();
      }


   internal final val creationStackBottom: StackTraceFrame?

   public final val creationStackTrace: List<StackTraceElement>
      public final get() {
         return this.creationStackTrace();
      }


   internal final var lastObservedFrame: CoroutineStackFrame?
      internal final get() {
         val var2: CoroutineStackFrame;
         if (this._lastObservedFrame != null) {
            var2 = this._lastObservedFrame.get();
         } else {
            var2 = null;
         }

         return var2;
      }

      internal final set(value) {
         val var2: WeakReference;
         if (var1 != null) {
            var2 = new WeakReference<>(var1);
         } else {
            var2 = null;
         }

         this._lastObservedFrame = var2;
      }


   public final var lastObservedThread: Thread?
      private set

   public final val sequenceNumber: Long

   internal final val state: String
      internal final get() {
         return this._state;
      }


   private final var unmatchedResume: Int

   init {
      this.creationStackBottom = var2;
      this.sequenceNumber = var3;
      this._context = new WeakReference<>(var1);
      this._state = "CREATED";
   }

   private fun creationStackTrace(): List<StackTraceElement> {
      return if (this.creationStackBottom == null)
         CollectionsKt.emptyList()
         else
         SequencesKt.toList(
            SequencesKt.sequence(
               (new Function2<SequenceScope<? super StackTraceElement>, Continuation<? super Unit>, Object>(this, this.creationStackBottom, null) {
                  final StackTraceFrame $bottom;
                  private Object L$0;
                  int label;
                  final DebugCoroutineInfoImpl this$0;

                  {
                     super(2, var3x);
                     this.this$0 = var1;
                     this.$bottom = var2x;
                  }

                  @Override
                  public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                     val var3: Function2 = new <anonymous constructor>(this.this$0, this.$bottom, var2);
                     var3.L$0 = var1;
                     return var3 as Continuation<Unit>;
                  }

                  public final Object invoke(SequenceScope<? super StackTraceElement> var1, Continuation<? super Unit> var2x) {
                     return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     val var3x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (this.label != 0) {
                        if (this.label != 1) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var1);
                     } else {
                        ResultKt.throwOnFailure(var1);
                        val var5: SequenceScope = this.L$0 as SequenceScope;
                        val var6: DebugCoroutineInfoImpl = this.this$0;
                        val var4: CoroutineStackFrame = this.$bottom.getCallerFrame();
                        var1 = this;
                        this.label = 1;
                        if (DebugCoroutineInfoImpl.access$yieldFrames(var6, var5, var4, var1) === var3x) {
                           return var3x;
                        }
                     }

                     return Unit.INSTANCE;
                  }
               }) as (SequenceScope<? super StackTraceElement>?, Continuation<? super Unit>?) -> Any
            )
         );
   }

   private tailrec suspend fun SequenceScope<StackTraceElement>.yieldFrames(frame: CoroutineStackFrame?) {
      label33: {
         if (var3 is <unrepresentable>) {
            val var5: <unrepresentable> = var3 as <unrepresentable>;
            if ((var3.label and Integer.MIN_VALUE) != 0) {
               var5.label += Integer.MIN_VALUE;
               var3 = var5;
               break label33;
            }
         }

         var3 = new ContinuationImpl(this, var3) {
            Object L$0;
            Object L$1;
            Object L$2;
            int label;
            Object result;
            final DebugCoroutineInfoImpl this$0;

            {
               super(var2);
               this.this$0 = var1;
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return DebugCoroutineInfoImpl.access$yieldFrames(this.this$0, null, null, this);
            }
         };
      }

      var var6: CoroutineStackFrame = (CoroutineStackFrame)var3.result;
      val var10: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      var var13: DebugCoroutineInfoImpl;
      var var17: SequenceScope;
      if (var3.label != 0) {
         if (var3.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         val var16: CoroutineStackFrame = var3.L$2 as CoroutineStackFrame;
         val var14: SequenceScope = var3.L$1 as SequenceScope;
         val var12: DebugCoroutineInfoImpl = var3.L$0 as DebugCoroutineInfoImpl;
         ResultKt.throwOnFailure(var6);
         var2 = var16.getCallerFrame();
         if (var2 == null) {
            return Unit.INSTANCE;
         }

         var3 = var3;
         var13 = var12;
         var17 = var14;
         if (var2 == null) {
            return Unit.INSTANCE;
         }
      } else {
         ResultKt.throwOnFailure(var6);
         var17 = var1;
         var13 = this;
         if (var2 == null) {
            return Unit.INSTANCE;
         }
      }

      do {
         val var11: StackTraceElement = var2.getStackTraceElement();
         var var9: Any = var3;
         var var8: DebugCoroutineInfoImpl = var13;
         var var19: SequenceScope = var17;
         var6 = var2;
         if (var11 != null) {
            var3.L$0 = var13;
            var3.L$1 = var17;
            var3.L$2 = var2;
            var3.label = 1;
            if (var17.yield(var11, var3) === var10) {
               return var10;
            }

            var6 = var2;
            var19 = var17;
            var8 = var13;
            var9 = var3;
         }

         var2 = var6.getCallerFrame();
         if (var2 == null) {
            return Unit.INSTANCE;
         }

         var3 = var9;
         var13 = var8;
         var17 = var19;
      } while (var2 != null);

      return Unit.INSTANCE;
   }

   internal fun lastObservedStackTrace(): List<StackTraceElement> {
      var var1: CoroutineStackFrame = this.getLastObservedFrame$kotlinx_coroutines_core();
      if (var1 == null) {
         return CollectionsKt.emptyList();
      } else {
         val var2: ArrayList;
         for (var2 = new ArrayList(); var1 != null; var1 = var1.getCallerFrame()) {
            val var3: StackTraceElement = var1.getStackTraceElement();
            if (var3 != null) {
               var2.add(var3);
            }
         }

         return var2;
      }
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("DebugCoroutineInfo(state=");
      var1.append(this.getState$kotlinx_coroutines_core());
      var1.append(",context=");
      var1.append(this.getContext());
      var1.append(')');
      return var1.toString();
   }

   internal fun updateState(state: String, frame: Continuation<*>, shouldBeMatched: Boolean) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield kotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl._state Ljava/lang/String;
      // 06: ldc "RUNNING"
      // 08: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0b: ifeq 28
      // 0e: aload 1
      // 0f: ldc "RUNNING"
      // 11: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 14: ifeq 28
      // 17: iload 3
      // 18: ifeq 28
      // 1b: aload 0
      // 1c: aload 0
      // 1d: getfield kotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl.unmatchedResume I
      // 20: bipush 1
      // 21: iadd
      // 22: putfield kotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl.unmatchedResume I
      // 25: goto 45
      // 28: aload 0
      // 29: getfield kotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl.unmatchedResume I
      // 2c: ifle 45
      // 2f: aload 1
      // 30: ldc "SUSPENDED"
      // 32: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 35: ifeq 45
      // 38: aload 0
      // 39: aload 0
      // 3a: getfield kotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl.unmatchedResume I
      // 3d: bipush 1
      // 3e: isub
      // 3f: putfield kotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl.unmatchedResume I
      // 42: aload 0
      // 43: monitorexit
      // 44: return
      // 45: aload 0
      // 46: getfield kotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl._state Ljava/lang/String;
      // 49: aload 1
      // 4a: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 4d: ifeq 67
      // 50: aload 1
      // 51: ldc "SUSPENDED"
      // 53: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 56: ifeq 67
      // 59: aload 0
      // 5a: invokevirtual kotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl.getLastObservedFrame$kotlinx_coroutines_core ()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;
      // 5d: astore 4
      // 5f: aload 4
      // 61: ifnull 67
      // 64: aload 0
      // 65: monitorexit
      // 66: return
      // 67: aload 0
      // 68: aload 1
      // 69: putfield kotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl._state Ljava/lang/String;
      // 6c: aload 2
      // 6d: instanceof kotlin/coroutines/jvm/internal/CoroutineStackFrame
      // 70: istore 3
      // 71: aconst_null
      // 72: astore 4
      // 74: iload 3
      // 75: ifeq 80
      // 78: aload 2
      // 79: checkcast kotlin/coroutines/jvm/internal/CoroutineStackFrame
      // 7c: astore 2
      // 7d: goto 82
      // 80: aconst_null
      // 81: astore 2
      // 82: aload 0
      // 83: aload 2
      // 84: invokevirtual kotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl.setLastObservedFrame$kotlinx_coroutines_core (Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)V
      // 87: aload 4
      // 89: astore 2
      // 8a: aload 1
      // 8b: ldc "RUNNING"
      // 8d: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 90: ifeq 97
      // 93: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 96: astore 2
      // 97: aload 0
      // 98: aload 2
      // 99: putfield kotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl.lastObservedThread Ljava/lang/Thread;
      // 9c: aload 0
      // 9d: monitorexit
      // 9e: return
      // 9f: astore 1
      // a0: aload 0
      // a1: monitorexit
      // a2: aload 1
      // a3: athrow
   }
}
