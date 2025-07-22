package kotlinx.coroutines

import java.util.ArrayList
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlinx.atomicfu.AtomicInt
import kotlinx.atomicfu.AtomicRef

private class AwaitAll<T>(vararg deferreds: Any) {
   private final val deferreds: Array<out Deferred<Any>>
   private final val notCompletedCount: AtomicInt

   init {
      this.deferreds = var1;
      this.notCompletedCount = var1.length;
   }

   public suspend fun await(): List<Any> {
      val var6: CancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var1), 1);
      var6.initCancellability();
      val var5: CancellableContinuation = var6;
      val var4: Int = access$getDeferreds$p(this).length;
      val var7: Array<AwaitAll.AwaitAllNode> = new AwaitAll.AwaitAllNode[var4];

      for (int var2 = 0; var2 < var4; var2++) {
         val var9: Deferred = access$getDeferreds$p(this)[var2];
         var9.start();
         val var8: AwaitAll.AwaitAllNode = new AwaitAll.AwaitAllNode(this, var5);
         var8.setHandle(var9.invokeOnCompletion(var8));
         var7[var2] = var8;
      }

      val var12: AwaitAll.DisposeHandlersOnCancel = new AwaitAll.DisposeHandlersOnCancel(this, var7);

      for (int var10 = 0; var10 < var4; var10++) {
         var7[var10].setDisposer(var12);
      }

      if (var5.isCompleted()) {
         var12.disposeAll();
      } else {
         var5.invokeOnCancellation(var12);
      }

      val var11: Any = var6.getResult();
      if (var11 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var1);
      }

      return var11;
   }

   private inner class AwaitAllNode(continuation: CancellableContinuation<List<Any>>) : JobNode {
      private final val _disposer: AtomicRef<kotlinx.coroutines.AwaitAll.DisposeHandlersOnCancel?>
      private final val continuation: CancellableContinuation<List<Any>>

      public final var disposer: kotlinx.coroutines.AwaitAll.DisposeHandlersOnCancel?
         public final get() {
            return _disposer$FU.get(this) as AwaitAll.DisposeHandlersOnCancel;
         }

         public final set(value) {
            _disposer$FU.set(this, var1);
         }


      public final lateinit var handle: DisposableHandle
         internal set

      init {
         this.this$0 = var1;
         this.continuation = var2;
      }

      public override operator fun invoke(cause: Throwable?) {
         if (var1 != null) {
            var var6: AwaitAll.DisposeHandlersOnCancel = (AwaitAll.DisposeHandlersOnCancel)this.continuation.tryResumeWithException(var1);
            if (var6 != null) {
               this.continuation.completeResume(var6);
               var6 = this.getDisposer();
               if (var6 != null) {
                  var6.disposeAll();
               }
            }
         } else if (AwaitAll.access$getNotCompletedCount$FU$p().decrementAndGet(this.this$0) == 0) {
            val var8: Continuation = this.continuation;
            val var4: Array<Deferred> = AwaitAll.access$getDeferreds$p(this.this$0);
            val var5: java.util.Collection = new ArrayList(var4.length);
            val var3: Int = var4.length;

            for (int var2 = 0; var2 < var3; var2++) {
               var5.add(var4[var2].getCompleted());
            }

            val var10: java.util.List = var5 as java.util.List;
            val var9: Result.Companion = Result.Companion;
            var8.resumeWith(Result.constructor-impl(var10));
         }
      }
   }

   private inner class DisposeHandlersOnCancel(vararg nodes: Any) : CancelHandler {
      private final val nodes: Array<kotlinx.coroutines.AwaitAll.AwaitAllNode>

      init {
         this.this$0 = var1;
         this.nodes = var2;
      }

      public fun disposeAll() {
         val var3: Array<AwaitAll.AwaitAllNode> = this.nodes;
         val var2: Int = this.nodes.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].getHandle().dispose();
         }
      }

      public override operator fun invoke(cause: Throwable?) {
         this.disposeAll();
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder("DisposeHandlersOnCancel[");
         var1.append(this.nodes);
         var1.append(']');
         return var1.toString();
      }
   }
}
