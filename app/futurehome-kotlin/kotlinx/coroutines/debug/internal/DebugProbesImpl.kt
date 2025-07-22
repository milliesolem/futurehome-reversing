package kotlinx.coroutines.debug.internal

import _COROUTINE.ArtificialStackFrames
import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Comparator
import java.util.LinkedHashMap
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import java.util.concurrent.atomic.AtomicLongFieldUpdater
import kotlin.concurrent.ThreadsKt
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.TypeIntrinsics
import kotlinx.atomicfu.AtomicInt
import kotlinx.atomicfu.AtomicLong
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineId
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.JobKt
import kotlinx.coroutines.JobSupport
import kotlinx.coroutines.internal.ScopeCoroutine

internal object DebugProbesImpl {
   private final val ARTIFICIAL_FRAME: StackTraceElement = new ArtificialStackFrames().coroutineCreation()
   private final val callerInfoCache: ConcurrentWeakMap<CoroutineStackFrame, DebugCoroutineInfoImpl> = new ConcurrentWeakMap(true)

   private final val capturedCoroutines: Set<kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner<*>>
      private final get() {
         return capturedCoroutinesMap.keySet();
      }


   private final val capturedCoroutinesMap: ConcurrentWeakMap<kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner<*>, Boolean> =
      new ConcurrentWeakMap(false, 1, null)
      private final val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
   private final val dynamicAttach: ((Boolean) -> Unit)?
   internal final var enableCreationStackTraces: Boolean = true

   public final var ignoreCoroutinesWithEmptyContext: Boolean = true
      internal set

   private final val installations: AtomicInt

   public final val isInstalled: Boolean
      public final get() {
         val var1: Boolean;
         if (DebugProbesImpl.Installations$kotlinx$VolatileWrapper.access$getInstallations$FU$p().get(installations$kotlinx$VolatileWrapper) > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   internal final var sanitizeStackTraces: Boolean = true
   private final val sequenceNumber: AtomicLong
   private final var weakRefCleanerThread: Thread?

   private final val debugString: String
      private final get() {
         val var2: java.lang.String;
         if (var1 is JobSupport) {
            var2 = (var1 as JobSupport).toDebugString();
         } else {
            var2 = var1.toString();
         }

         return var2;
      }


   private final val isInternalMethod: Boolean
      private final get() {
         return StringsKt.startsWith$default(var1.getClassName(), "kotlinx.coroutines", false, 2, null);
      }


   @JvmStatic
   fun {
      val var0: DebugProbesImpl = new DebugProbesImpl();
      INSTANCE = var0;
      dynamicAttach = var0.getDynamicAttach();
   }

   private fun Job.build(map: Map<Job, DebugCoroutineInfoImpl>, builder: StringBuilder, indent: String) {
      val var6: DebugCoroutineInfoImpl = var2.get(var1) as DebugCoroutineInfoImpl;
      var var5: java.lang.String;
      if (var6 == null) {
         var5 = var4;
         if (var1 !is ScopeCoroutine) {
            val var9: StringBuilder = new StringBuilder();
            var9.append(var4);
            var9.append(this.getDebugString(var1));
            var9.append('\n');
            var3.append(var9.toString());
            val var10: StringBuilder = new StringBuilder();
            var10.append(var4);
            var10.append('\t');
            var5 = var10.toString();
         }
      } else {
         val var11: StackTraceElement = CollectionsKt.firstOrNull(var6.lastObservedStackTrace$kotlinx_coroutines_core());
         val var7: java.lang.String = var6.getState$kotlinx_coroutines_core();
         val var13: StringBuilder = new StringBuilder();
         var13.append(var4);
         var13.append(this.getDebugString(var1));
         var13.append(", continuation is ");
         var13.append(var7);
         var13.append(" at line ");
         var13.append(var11);
         var13.append('\n');
         var3.append(var13.toString());
         val var12: StringBuilder = new StringBuilder();
         var12.append(var4);
         var12.append('\t');
         var5 = var12.toString();
      }

      val var8: java.util.Iterator = var1.getChildren().iterator();

      while (var8.hasNext()) {
         this.build(var8.next() as Job, var2, var3, var5);
      }
   }

   private fun <T> createOwner(completion: Continuation<T>, frame: StackTraceFrame?): Continuation<T> {
      if (!this.isInstalled$kotlinx_coroutines_debug()) {
         return var1;
      } else {
         val var4: DebugProbesImpl.CoroutineOwner = new DebugProbesImpl.CoroutineOwner(
            var1,
            new DebugCoroutineInfoImpl(
               var1.getContext(),
               var2,
               DebugProbesImpl.SequenceNumber$kotlinx$VolatileWrapper.access$getSequenceNumber$FU$p().incrementAndGet(sequenceNumber$kotlinx$VolatileWrapper)
            )
         );
         val var3: ConcurrentWeakMap = capturedCoroutinesMap;
         capturedCoroutinesMap.put(var4, true);
         if (!this.isInstalled$kotlinx_coroutines_debug()) {
            var3.clear();
         }

         return var4;
      }
   }

   private inline fun <R : Any> dumpCoroutinesInfoImpl(
      crossinline create: (kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner<*>, CoroutineContext) -> R
   ): List<R> {
      if (this.isInstalled$kotlinx_coroutines_debug()) {
         return SequencesKt.toList(
            SequencesKt.mapNotNull(
               SequencesKt.sortedWith(
                  CollectionsKt.asSequence(this.getCapturedCoroutines()),
                  new Comparator() {
                     @Override
                     public final int compare(T var1, T var2) {
                        return ComparisonsKt.compareValues(
                           (var1 as DebugProbesImpl.CoroutineOwner).info.sequenceNumber, (var2 as DebugProbesImpl.CoroutineOwner).info.sequenceNumber
                        );
                     }
                  }
               ),
               (new Function1<DebugProbesImpl.CoroutineOwner<?>, R>(var1) {
                  final Function2<DebugProbesImpl.CoroutineOwner<?>, CoroutineContext, R> $create;

                  {
                     super(1);
                     this.$create = var1;
                  }

                  public final R invoke(DebugProbesImpl.CoroutineOwner<?> var1) {
                     val var2: Boolean = DebugProbesImpl.access$isFinished(DebugProbesImpl.INSTANCE, var1);
                     var var3: Any = null;
                     if (!var2) {
                        val var4: CoroutineContext = var1.info.getContext();
                        if (var4 != null) {
                           var3 = this.$create.invoke(var1, var4);
                        }
                     }

                     return (R)var3;
                  }
               }) as Function1
            )
         );
      } else {
         throw new IllegalStateException("Debug probes are not installed".toString());
      }
   }

   private fun dumpCoroutinesSynchronized(out: PrintStream) {
      if (!this.isInstalled$kotlinx_coroutines_debug()) {
         throw new IllegalStateException("Debug probes are not installed".toString());
      } else {
         var var2: StringBuilder = new StringBuilder("Coroutines dump ");
         var2.append(dateFormat.format(System.currentTimeMillis()));
         var1.print(var2.toString());

         for (DebugProbesImpl.CoroutineOwner var7 : SequencesKt.sortedWith(
            SequencesKt.filter(CollectionsKt.asSequence(this.getCapturedCoroutines()), <unrepresentable>.INSTANCE),
            new Comparator() {
               @Override
               public final int compare(T var1, T var2) {
                  return ComparisonsKt.compareValues(
                     (var1 as DebugProbesImpl.CoroutineOwner).info.sequenceNumber, (var2 as DebugProbesImpl.CoroutineOwner).info.sequenceNumber
                  );
               }
            }
         )) {
            val var3: DebugCoroutineInfoImpl = var7.info;
            val var6: java.util.List = var7.info.lastObservedStackTrace$kotlinx_coroutines_core();
            val var5: DebugProbesImpl = INSTANCE;
            val var8: java.util.List = INSTANCE.enhanceStackTraceWithThreadDumpImpl(var3.getState$kotlinx_coroutines_core(), var3.lastObservedThread, var6);
            val var10: java.lang.String;
            if (var3.getState$kotlinx_coroutines_core() == "RUNNING" && var8 === var6) {
               var2 = new StringBuilder();
               var2.append(var3.getState$kotlinx_coroutines_core());
               var2.append(" (Last suspension stacktrace, not an actual stacktrace)");
               var10 = var2.toString();
            } else {
               var10 = var3.getState$kotlinx_coroutines_core();
            }

            val var9: StringBuilder = new StringBuilder("\n\nCoroutine ");
            var9.append(var7.delegate);
            var9.append(", state: ");
            var9.append(var10);
            var1.print(var9.toString());
            if (var6.isEmpty()) {
               var2 = new StringBuilder("\n\tat ");
               var2.append(ARTIFICIAL_FRAME);
               var1.print(var2.toString());
               var5.printStackTrace(var1, var3.getCreationStackTrace());
            } else {
               var5.printStackTrace(var1, var8);
            }
         }
      }
   }

   private fun enhanceStackTraceWithThreadDumpImpl(state: String, thread: Thread?, coroutineTrace: List<StackTraceElement>): List<StackTraceElement> {
      if (var1 == "RUNNING" && var2 != null) {
         label63:
         try {
            val var12: Result.Companion = Result.Companion;
            val var13: DebugProbesImpl = this;
            var11 = Result.constructor-impl(var2.getStackTrace());
         } catch (var8: java.lang.Throwable) {
            val var10: Result.Companion = Result.Companion;
            var11 = Result.constructor-impl(ResultKt.createFailure(var8));
            break label63;
         }

         var var15: Any = var11;
         if (Result.isFailure-impl(var11)) {
            var15 = null;
         }

         val var14: Array<StackTraceElement> = var15 as Array<StackTraceElement>;
         if (var15 as Array<StackTraceElement> == null) {
            return var3;
         } else {
            var var6: Int = var14.length;
            var var5: Int = 0;
            var var4: Int = 0;

            while (true) {
               if (var4 >= var6) {
                  var4 = -1;
                  break;
               }

               if (var14[var4].getClassName() == "kotlin.coroutines.jvm.internal.BaseContinuationImpl"
                  && var14[var4].getMethodName() == "resumeWith"
                  && var14[var4].getFileName() == "ContinuationImpl.kt") {
                  break;
               }

               var4++;
            }

            var15 = this.findContinuationStartIndex(var4, var14, var3);
            var6 = (((Pair)var15).component1() as java.lang.Number).intValue();
            val var7: Int = (((Pair)var15).component2() as java.lang.Number).intValue();
            if (var6 == -1) {
               return var3;
            } else {
               for (var15 = new ArrayList(var3.size() + var4 - var6 - 1 - var7); var5 < var4 - var7; var5++) {
                  (var15 as java.util.Collection).add(var14[var5]);
               }

               var4 = var6 + 1;

               for (int var20 = var3.size(); var4 < var20; var4++) {
                  (var15 as java.util.Collection).add(var3.get(var4));
               }

               return var15 as MutableList<StackTraceElement>;
            }
         }
      } else {
         return var3;
      }
   }

   private fun findContinuationStartIndex(indexOfResumeWith: Int, actualTrace: Array<StackTraceElement>, coroutineTrace: List<StackTraceElement>): Pair<
         Int,
         Int
      > {
      for (int var4 = 0; var4 < 3; var4++) {
         val var5: Int = INSTANCE.findIndexOfFrame(var1 - 1 - var4, var2, var3);
         if (var5 != -1) {
            return TuplesKt.to(var5, var4);
         }
      }

      return TuplesKt.to(-1, 0);
   }

   private fun findIndexOfFrame(frameIndex: Int, actualTrace: Array<StackTraceElement>, coroutineTrace: List<StackTraceElement>): Int {
      val var8: StackTraceElement = ArraysKt.getOrNull(var2, var1);
      val var5: Byte = -1;
      if (var8 == null) {
         return -1;
      } else {
         val var9: java.util.Iterator = var3.iterator();
         var1 = 0;

         var var4: Int;
         while (true) {
            var4 = var5;
            if (!var9.hasNext()) {
               break;
            }

            val var6: StackTraceElement = var9.next() as StackTraceElement;
            if (var6.getFileName() == var8.getFileName() && var6.getClassName() == var8.getClassName() && var6.getMethodName() == var8.getMethodName()) {
               var4 = var1;
               break;
            }

            var1++;
         }

         return var4;
      }
   }

   private fun getDynamicAttach(): ((Boolean) -> Unit)? {
      var var1: Any;
      label16:
      try {
         var1 = Result.Companion;
         var1 = this;
         var1 = Class.forName("kotlinx.coroutines.debug.internal.ByteBuddyDynamicAttach").getConstructors()[0].newInstance(null);
         var1 = Result.constructor-impl(TypeIntrinsics.beforeCheckcastToFunctionOfArity(var1, 1) as Function1);
      } catch (var4: java.lang.Throwable) {
         val var3: Result.Companion = Result.Companion;
         var1 = Result.constructor-impl(ResultKt.createFailure(var4));
         break label16;
      }

      if (Result.isFailure-impl(var1)) {
         var1 = null;
      }

      return var1 as (java.lang.Boolean?) -> Unit;
   }

   private fun kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner<*>.isFinished(): Boolean {
      val var2: CoroutineContext = var1.info.getContext();
      if (var2 != null) {
         val var3: Job = var2.get(Job.Key);
         if (var3 != null) {
            if (!var3.isCompleted()) {
               return false;
            }

            capturedCoroutinesMap.remove(var1);
            return true;
         }
      }

      return false;
   }

   private fun Continuation<*>.owner(): kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner<*>? {
      val var2: Boolean = var1 is CoroutineStackFrame;
      var var3: DebugProbesImpl.CoroutineOwner = null;
      val var4: CoroutineStackFrame;
      if (var2) {
         var4 = var1 as CoroutineStackFrame;
      } else {
         var4 = null;
      }

      if (var4 != null) {
         var3 = this.owner(var4);
      }

      return var3;
   }

   private tailrec fun CoroutineStackFrame.owner(): kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner<*>? {
      while (true) {
         val var2: DebugProbesImpl.CoroutineOwner;
         if (var1 is DebugProbesImpl.CoroutineOwner) {
            var2 = var1 as DebugProbesImpl.CoroutineOwner;
         } else {
            var1 = var1.getCallerFrame();
            if (var1 != null) {
               continue;
            }

            var2 = null;
         }

         return var2;
      }
   }

   private fun printStackTrace(out: PrintStream, frames: List<StackTraceElement>) {
      for (StackTraceElement var5 : var2) {
         val var3: StringBuilder = new StringBuilder("\n\tat ");
         var3.append(var5);
         var1.print(var3.toString());
      }
   }

   private fun probeCoroutineCompleted(owner: kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner<*>) {
      capturedCoroutinesMap.remove(var1);
      val var2: CoroutineStackFrame = var1.info.getLastObservedFrame$kotlinx_coroutines_core();
      if (var2 != null) {
         val var3: CoroutineStackFrame = this.realCaller(var2);
         if (var3 != null) {
            callerInfoCache.remove(var3);
         }
      }
   }

   private tailrec fun CoroutineStackFrame.realCaller(): CoroutineStackFrame? {
      val var2: CoroutineStackFrame;
      do {
         var2 = var1.getCallerFrame();
         if (var2 == null) {
            return null;
         }

         var1 = var2;
      } while (var2.getStackTraceElement() == null);

      return var2;
   }

   private fun <T : Throwable> sanitizeStackTrace(throwable: T): List<StackTraceElement> {
      val var9: Array<StackTraceElement> = var1.getStackTrace();
      val var6: Int = var9.length;
      var var4: Int = var9.length - 1;
      var var10: Int = -1;
      if (var4 >= 0) {
         var10 = var4;

         while (true) {
            var4 = var10 - 1;
            if (var9[var10].getClassName() == "kotlin.coroutines.jvm.internal.DebugProbesKt") {
               break;
            }

            if (var4 < 0) {
               var10 = -1;
               break;
            }

            var10 = var4;
         }
      }

      var var13: Int = var10 + 1;
      if (!sanitizeStackTraces) {
         var4 = var6 - var13;
         val var18: ArrayList = new ArrayList(var6 - var13);

         for (int var12 = 0; var12 < var4; var12++) {
            var18.add(var9[var12 + var13]);
         }

         return var18;
      } else {
         val var7: ArrayList = new ArrayList(var6 - var13 + 1);
         var10 = var13;

         while (var10 < var6) {
            if (this.isInternalMethod(var9[var10])) {
               val var8: java.util.Collection = var7;
               var7.add(var9[var10]);
               var13 = var10 + 1;

               while (var13 < var6 && this.isInternalMethod(var9[var13])) {
                  var13++;
               }

               val var5: Int = var13 - 1;
               var4 = var13 - 1;

               while (var4 > var10 && var9[var4].getFileName() == null) {
                  var4--;
               }

               if (var4 > var10 && var4 < var5) {
                  var8.add(var9[var4]);
               }

               var8.add(var9[var5]);
               var10 = var13;
            } else {
               var7.add(var9[var10]);
               var10++;
            }
         }

         return var7;
      }
   }

   private fun startWeakRefCleanerThread() {
      weakRefCleanerThread = ThreadsKt.thread$default(false, true, null, "Coroutines Debugger Cleaner", 0, <unrepresentable>.INSTANCE, 21, null);
   }

   private fun stopWeakRefCleanerThread() {
      val var1: Thread = weakRefCleanerThread;
      if (weakRefCleanerThread != null) {
         weakRefCleanerThread = null;
         var1.interrupt();
         var1.join();
      }
   }

   private fun List<StackTraceElement>.toStackTraceFrame(): StackTraceFrame {
      val var2: Boolean = var1.isEmpty();
      var var3: StackTraceFrame = null;
      if (!var2) {
         val var5: java.util.ListIterator = var1.listIterator(var1.size());
         var var6: StackTraceFrame = null;

         while (true) {
            var3 = var6;
            if (!var5.hasPrevious()) {
               break;
            }

            var6 = new StackTraceFrame(var6, var5.previous() as StackTraceElement);
         }
      }

      return new StackTraceFrame(var3, ARTIFICIAL_FRAME);
   }

   private fun Any.toStringRepr(): String {
      return DebugProbesImplKt.access$repr(var1.toString());
   }

   private fun updateRunningState(frame: CoroutineStackFrame, state: String) {
      if (this.isInstalled$kotlinx_coroutines_debug()) {
         val var6: ConcurrentWeakMap = callerInfoCache;
         var var4: DebugCoroutineInfoImpl = callerInfoCache.remove(var1);
         val var3: Boolean;
         if (var4 != null) {
            var3 = false;
         } else {
            val var8: DebugProbesImpl.CoroutineOwner = this.owner(var1);
            if (var8 == null) {
               return;
            }

            val var5: DebugCoroutineInfoImpl = var8.info;
            if (var8.info == null) {
               return;
            }

            val var9: CoroutineStackFrame = var8.info.getLastObservedFrame$kotlinx_coroutines_core();
            val var10: CoroutineStackFrame;
            if (var9 != null) {
               var10 = this.realCaller(var9);
            } else {
               var10 = null;
            }

            if (var10 != null) {
               var6.remove(var10);
            }

            var3 = true;
            var4 = var5;
         }

         var4.updateState$kotlinx_coroutines_core(var2, var1 as Continuation<?>, var3);
         var1 = this.realCaller(var1);
         if (var1 != null) {
            var6.put(var1, var4);
         }
      }
   }

   private fun updateState(frame: Continuation<*>, state: String) {
      if (this.isInstalled$kotlinx_coroutines_debug()) {
         if (!ignoreCoroutinesWithEmptyContext || var1.getContext() != EmptyCoroutineContext.INSTANCE) {
            if (var2 == "RUNNING") {
               val var4: CoroutineStackFrame;
               if (var1 is CoroutineStackFrame) {
                  var4 = var1 as CoroutineStackFrame;
               } else {
                  var4 = null;
               }

               if (var4 != null) {
                  this.updateRunningState(var4, var2);
               }
            } else {
               val var3: DebugProbesImpl.CoroutineOwner = this.owner(var1);
               if (var3 != null) {
                  this.updateState(var3, var1, var2);
               }
            }
         }
      }
   }

   private fun updateState(owner: kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner<*>, frame: Continuation<*>, state: String) {
      if (this.isInstalled$kotlinx_coroutines_debug()) {
         var1.info.updateState$kotlinx_coroutines_core(var3, var2, true);
      }
   }

   internal fun dumpCoroutines(out: PrintStream) {
      label13: {
         synchronized (var1){} // $VF: monitorenter 

         try {
            INSTANCE.dumpCoroutinesSynchronized(var1);
         } catch (var3: java.lang.Throwable) {
            // $VF: monitorexit
         }

         // $VF: monitorexit
      }
   }

   public fun dumpCoroutinesInfo(): List<DebugCoroutineInfo> {
      if (this.isInstalled$kotlinx_coroutines_debug()) {
         return SequencesKt.toList(
            SequencesKt.mapNotNull(
               SequencesKt.sortedWith(
                  CollectionsKt.asSequence(this.getCapturedCoroutines()),
                  new Comparator() {
                     @Override
                     public final int compare(T var1, T var2) {
                        return ComparisonsKt.compareValues(
                           (var1 as DebugProbesImpl.CoroutineOwner).info.sequenceNumber, (var2 as DebugProbesImpl.CoroutineOwner).info.sequenceNumber
                        );
                     }
                  }
               ),
               (new Function1<DebugProbesImpl.CoroutineOwner<?>, DebugCoroutineInfo>() {
                  {
                     super(1);
                  }

                  public final DebugCoroutineInfo invoke(DebugProbesImpl.CoroutineOwner<?> var1) {
                     val var2: Boolean = DebugProbesImpl.access$isFinished(DebugProbesImpl.INSTANCE, var1);
                     var var3: DebugCoroutineInfo = null;
                     if (!var2) {
                        val var4: CoroutineContext = var1.info.getContext();
                        if (var4 != null) {
                           var3 = new DebugCoroutineInfo(var1.info, var4);
                        }
                     }

                     return var3;
                  }
               }) as (DebugProbesImplCoroutineOwner<?>?) -> DebugCoroutineInfo
            )
         );
      } else {
         throw new IllegalStateException("Debug probes are not installed".toString());
      }
   }

   public fun dumpCoroutinesInfoAsJsonAndReferences(): Array<Any> {
      val var5: java.util.List = this.dumpCoroutinesInfo();
      val var1: Int = var5.size();
      val var6: ArrayList = new ArrayList(var1);
      val var8: ArrayList = new ArrayList(var1);
      val var7: ArrayList = new ArrayList(var1);

      for (DebugCoroutineInfo var9 : var5) {
         var var11: CoroutineContext;
         var var14: java.lang.String;
         label26: {
            var11 = var9.getContext();
            val var2: CoroutineName = var11.get(CoroutineName.Key);
            if (var2 != null) {
               var14 = var2.getName();
               if (var14 != null) {
                  var14 = this.toStringRepr(var14);
                  break label26;
               }
            }

            var14 = null;
         }

         val var3: CoroutineDispatcher = var11.get(CoroutineDispatcher.Key);
         val var17: java.lang.String;
         if (var3 != null) {
            var17 = this.toStringRepr(var3);
         } else {
            var17 = null;
         }

         val var12: StringBuilder = new StringBuilder("\n                {\n                    \"name\": ");
         var12.append(var14);
         var12.append(",\n                    \"id\": ");
         val var18: CoroutineId = var11.get(CoroutineId.Key);
         var var15: java.lang.Long = null;
         if (var18 != null) {
            var15 = var18.getId();
         }

         var12.append(var15);
         var12.append(",\n                    \"dispatcher\": ");
         var12.append(var17);
         var12.append(",\n                    \"sequenceNumber\": ");
         var12.append(var9.getSequenceNumber());
         var12.append(",\n                    \"state\": \"");
         var12.append(var9.getState());
         var12.append("\"\n                } \n                ");
         var7.add(StringsKt.trimIndent(var12.toString()));
         var8.add(var9.getLastObservedFrame());
         var6.add(var9.getLastObservedThread());
      }

      val var16: StringBuilder = new StringBuilder("[");
      var16.append(CollectionsKt.joinToString$default(var7, null, null, null, 0, null, null, 63, null));
      var16.append(']');
      return new Object[]{var16.toString(), var6.toArray(new Thread[0]), var8.toArray(new CoroutineStackFrame[0]), var5.toArray(new DebugCoroutineInfo[0])};
   }

   public fun dumpDebuggerInfo(): List<DebuggerInfo> {
      if (this.isInstalled$kotlinx_coroutines_debug()) {
         return SequencesKt.toList(
            SequencesKt.mapNotNull(
               SequencesKt.sortedWith(
                  CollectionsKt.asSequence(this.getCapturedCoroutines()),
                  new Comparator() {
                     @Override
                     public final int compare(T var1, T var2) {
                        return ComparisonsKt.compareValues(
                           (var1 as DebugProbesImpl.CoroutineOwner).info.sequenceNumber, (var2 as DebugProbesImpl.CoroutineOwner).info.sequenceNumber
                        );
                     }
                  }
               ),
               (new Function1<DebugProbesImpl.CoroutineOwner<?>, DebuggerInfo>() {
                  {
                     super(1);
                  }

                  public final DebuggerInfo invoke(DebugProbesImpl.CoroutineOwner<?> var1) {
                     val var2: Boolean = DebugProbesImpl.access$isFinished(DebugProbesImpl.INSTANCE, var1);
                     var var3: DebuggerInfo = null;
                     if (!var2) {
                        val var4: CoroutineContext = var1.info.getContext();
                        if (var4 != null) {
                           var3 = new DebuggerInfo(var1.info, var4);
                        }
                     }

                     return var3;
                  }
               }) as (DebugProbesImplCoroutineOwner<?>?) -> DebuggerInfo
            )
         );
      } else {
         throw new IllegalStateException("Debug probes are not installed".toString());
      }
   }

   public fun enhanceStackTraceWithThreadDump(info: DebugCoroutineInfo, coroutineTrace: List<StackTraceElement>): List<StackTraceElement> {
      return this.enhanceStackTraceWithThreadDumpImpl(var1.getState(), var1.getLastObservedThread(), var2);
   }

   public fun enhanceStackTraceWithThreadDumpAsJson(info: DebugCoroutineInfo): String {
      val var6: java.util.List = this.enhanceStackTraceWithThreadDump(var1, var1.lastObservedStackTrace());
      val var2: java.util.List = new ArrayList();

      for (StackTraceElement var3 : var6) {
         val var4: StringBuilder = new StringBuilder("\n                {\n                    \"declaringClass\": \"");
         var4.append(var3.getClassName());
         var4.append("\",\n                    \"methodName\": \"");
         var4.append(var3.getMethodName());
         var4.append("\",\n                    \"fileName\": ");
         val var7: java.lang.String = var3.getFileName();
         val var8: java.lang.String;
         if (var7 != null) {
            var8 = this.toStringRepr(var7);
         } else {
            var8 = null;
         }

         var4.append(var8);
         var4.append(",\n                    \"lineNumber\": ");
         var4.append(var3.getLineNumber());
         var4.append("\n                }\n                ");
         var2.add(StringsKt.trimIndent(var4.toString()));
      }

      val var9: StringBuilder = new StringBuilder("[");
      var9.append(CollectionsKt.joinToString$default(var2, null, null, null, 0, null, null, 63, null));
      var9.append(']');
      return var9.toString();
   }

   internal fun hierarchyToString(job: Job): String {
      if (!this.isInstalled$kotlinx_coroutines_debug()) {
         throw new IllegalStateException("Debug probes are not installed".toString());
      } else {
         var var3: java.lang.Iterable = this.getCapturedCoroutines();
         val var2: java.util.Collection = new ArrayList();

         for (var3 : var3) {
            if ((var3 as DebugProbesImpl.CoroutineOwner).delegate.getContext().get(Job.Key) != null) {
               var2.add(var3);
            }
         }

         var3 = var2 as java.util.List;
         val var6: java.util.Map = new LinkedHashMap(
            RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var2 as java.util.List, 10)), 16)
         );

         for (DebugProbesImpl.CoroutineOwner var9 : var3) {
            var6.put(JobKt.getJob(var9.delegate.getContext()), var9.info);
         }

         val var10: StringBuilder = new StringBuilder();
         INSTANCE.build(var1, var6, var10, "");
         val var5: java.lang.String = var10.toString();
         return var5;
      }
   }

   internal fun install() {
      if (DebugProbesImpl.Installations$kotlinx$VolatileWrapper.access$getInstallations$FU$p().incrementAndGet(installations$kotlinx$VolatileWrapper) <= 1) {
         this.startWeakRefCleanerThread();
         if (!AgentInstallationType.INSTANCE.isInstalledStatically$kotlinx_coroutines_core()) {
            if (dynamicAttach != null) {
               dynamicAttach.invoke(true);
            }
         }
      }
   }

   internal fun <T> probeCoroutineCreated(completion: Continuation<T>): Continuation<T> {
      if (!this.isInstalled$kotlinx_coroutines_debug()) {
         return var1;
      } else if (ignoreCoroutinesWithEmptyContext && var1.getContext() === EmptyCoroutineContext.INSTANCE) {
         return var1;
      } else if (this.owner(var1) != null) {
         return var1;
      } else {
         val var2: StackTraceFrame;
         if (enableCreationStackTraces) {
            var2 = this.toStackTraceFrame(this.sanitizeStackTrace(new Exception()));
         } else {
            var2 = null;
         }

         return this.createOwner(var1, var2);
      }
   }

   internal fun probeCoroutineResumed(frame: Continuation<*>) {
      this.updateState(var1, "RUNNING");
   }

   internal fun probeCoroutineSuspended(frame: Continuation<*>) {
      this.updateState(var1, "SUSPENDED");
   }

   internal fun uninstall() {
      if (this.isInstalled$kotlinx_coroutines_debug()) {
         if (DebugProbesImpl.Installations$kotlinx$VolatileWrapper.access$getInstallations$FU$p().decrementAndGet(installations$kotlinx$VolatileWrapper) == 0) {
            this.stopWeakRefCleanerThread();
            capturedCoroutinesMap.clear();
            callerInfoCache.clear();
            if (!AgentInstallationType.INSTANCE.isInstalledStatically$kotlinx_coroutines_core()) {
               if (dynamicAttach != null) {
                  dynamicAttach.invoke(false);
               }
            }
         }
      } else {
         throw new IllegalStateException("Agent was not installed".toString());
      }
   }

   public class CoroutineOwner<T> internal constructor(delegate: Continuation<Any>, info: DebugCoroutineInfoImpl) : Continuation<T>, CoroutineStackFrame {
      public open val callerFrame: CoroutineStackFrame?
         public open get() {
            val var1: StackTraceFrame = this.getFrame();
            val var2: CoroutineStackFrame;
            if (var1 != null) {
               var2 = var1.getCallerFrame();
            } else {
               var2 = null;
            }

            return var2;
         }


      public open val context: CoroutineContext
      internal final val delegate: Continuation<Any>

      private final val frame: StackTraceFrame?
         private final get() {
            return this.info.getCreationStackBottom$kotlinx_coroutines_core();
         }


      public final val info: DebugCoroutineInfoImpl

      init {
         this.delegate = var1;
         this.info = var2;
      }

      public override fun getStackTraceElement(): StackTraceElement? {
         val var1: StackTraceFrame = this.getFrame();
         val var2: StackTraceElement;
         if (var1 != null) {
            var2 = var1.getStackTraceElement();
         } else {
            var2 = null;
         }

         return var2;
      }

      public override fun resumeWith(result: Result<Any>) {
         DebugProbesImpl.access$probeCoroutineCompleted(DebugProbesImpl.INSTANCE, this);
         this.delegate.resumeWith(var1);
      }

      public override fun toString(): String {
         return this.delegate.toString();
      }
   }

   // $VF: Class flags could not be determined
   internal class `Installations$kotlinx$VolatileWrapper` {
      @JvmStatic
      private AtomicIntegerFieldUpdater installations$FU = AtomicIntegerFieldUpdater.newUpdater(
         DebugProbesImpl.Installations$kotlinx$VolatileWrapper.class, "installations"
      );
      @Volatile
      @Volatile
      private int installations;

      fun `Installations$kotlinx$VolatileWrapper`() {
      }
   }

   // $VF: Class flags could not be determined
   internal class `SequenceNumber$kotlinx$VolatileWrapper` {
      @JvmStatic
      private AtomicLongFieldUpdater sequenceNumber$FU = AtomicLongFieldUpdater.newUpdater(
         DebugProbesImpl.SequenceNumber$kotlinx$VolatileWrapper.class, "sequenceNumber"
      );
      @Volatile
      @Volatile
      private long sequenceNumber;

      fun `SequenceNumber$kotlinx$VolatileWrapper`() {
      }
   }
}
