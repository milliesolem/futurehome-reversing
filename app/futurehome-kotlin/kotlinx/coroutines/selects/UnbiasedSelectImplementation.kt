package kotlinx.coroutines.selects

import java.util.ArrayList
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.functions.Function3
import kotlinx.coroutines.selects.SelectImplementation.ClauseData

internal open class UnbiasedSelectImplementation<R>(context: CoroutineContext) : SelectImplementation(var1) {
   private final val clausesToRegister: MutableList<ClauseData> = (new ArrayList()) as java.util.List

   private fun shuffleAndRegisterClauses() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield kotlinx/coroutines/selects/UnbiasedSelectImplementation.clausesToRegister Ljava/util/List;
      // 04: invokestatic java/util/Collections.shuffle (Ljava/util/List;)V
      // 07: aload 0
      // 08: getfield kotlinx/coroutines/selects/UnbiasedSelectImplementation.clausesToRegister Ljava/util/List;
      // 0b: checkcast java/lang/Iterable
      // 0e: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 13: astore 1
      // 14: aload 1
      // 15: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 1a: ifeq 35
      // 1d: aload 1
      // 1e: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 23: checkcast kotlinx/coroutines/selects/SelectImplementation$ClauseData
      // 26: astore 2
      // 27: aload 0
      // 28: checkcast kotlinx/coroutines/selects/SelectImplementation
      // 2b: aload 2
      // 2c: bipush 0
      // 2d: bipush 1
      // 2e: aconst_null
      // 2f: invokestatic kotlinx/coroutines/selects/SelectImplementation.register$default (Lkotlinx/coroutines/selects/SelectImplementation;Lkotlinx/coroutines/selects/SelectImplementation$ClauseData;ZILjava/lang/Object;)V
      // 32: goto 14
      // 35: aload 0
      // 36: getfield kotlinx/coroutines/selects/UnbiasedSelectImplementation.clausesToRegister Ljava/util/List;
      // 39: invokeinterface java/util/List.clear ()V 1
      // 3e: return
      // 3f: astore 1
      // 40: aload 0
      // 41: getfield kotlinx/coroutines/selects/UnbiasedSelectImplementation.clausesToRegister Ljava/util/List;
      // 44: invokeinterface java/util/List.clear ()V 1
      // 49: aload 1
      // 4a: athrow
   }

   internal override suspend fun doSelect(): Any {
      return doSelect$suspendImpl(this, var1);
   }

   public override operator fun SelectClause0.invoke(block: (Continuation<Any>) -> Any?) {
      this.clausesToRegister
         .add(
            this as SelectImplementation.new ClauseData(
               this,
               (Function3<Object, ? super SelectInstance<?>, Object, Unit>)var1.getClauseObject(),
               var1.getRegFunc(),
               var1.getProcessResFunc(),
               SelectKt.getPARAM_CLAUSE_0(),
               var2,
               var1.getOnCancellationConstructor()
            )
         );
   }

   public override operator fun <Q> SelectClause1<Q>.invoke(block: (Q, Continuation<Any>) -> Any?) {
      this.clausesToRegister
         .add(
            this as SelectImplementation.new ClauseData(
               this,
               (Function3<Object, ? super SelectInstance<?>, Object, Unit>)var1.getClauseObject(),
               var1.getRegFunc(),
               var1.getProcessResFunc(),
               null,
               var2,
               var1.getOnCancellationConstructor()
            )
         );
   }

   public override operator fun <P, Q> SelectClause2<P, Q>.invoke(param: P, block: (Q, Continuation<Any>) -> Any?) {
      this.clausesToRegister
         .add(
            this as SelectImplementation.new ClauseData(
               this,
               (Function3<Object, ? super SelectInstance<?>, Object, Unit>)var1.getClauseObject(),
               var1.getRegFunc(),
               var1.getProcessResFunc(),
               var2,
               var3,
               var1.getOnCancellationConstructor()
            )
         );
   }
}
