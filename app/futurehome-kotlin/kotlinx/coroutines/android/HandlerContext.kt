package kotlinx.coroutines.android

import android.os.Handler
import android.os.Looper
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.functions.Function1
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.JobKt
import kotlinx.coroutines.NonDisposableHandle

internal class HandlerContext private constructor(handler: Handler, name: String?, invokeImmediately: Boolean) : HandlerDispatcher, Delay {
   private final var _immediate: HandlerContext?
   private final val handler: Handler
   public open val immediate: HandlerContext
   private final val invokeImmediately: Boolean
   private final val name: String?

   public constructor(handler: Handler, name: String? = null) : this(var1, var2, false)
   init {
      var var4: HandlerContext = null;
      super(null);
      this.handler = var1;
      this.name = var2;
      this.invokeImmediately = var3;
      if (var3) {
         var4 = this;
      }

      this._immediate = var4;
      var4 = this._immediate;
      if (this._immediate == null) {
         var4 = new HandlerContext(var1, var2, true);
         this._immediate = var4;
      }

      this.immediate = var4;
   }

   private fun cancelOnRejection(context: CoroutineContext, block: Runnable) {
      val var3: StringBuilder = new StringBuilder("The task was rejected, the handler underlying the dispatcher '");
      var3.append(this);
      var3.append("' was closed");
      JobKt.cancel(var1, new CancellationException(var3.toString()));
      Dispatchers.getIO().dispatch(var1, var2);
   }

   @JvmStatic
   fun `invokeOnTimeout$lambda$3`(var0: HandlerContext, var1: Runnable) {
      var0.handler.removeCallbacks(var1);
   }

   public override fun dispatch(context: CoroutineContext, block: Runnable) {
      if (!this.handler.post(var2)) {
         this.cancelOnRejection(var1, var2);
      }
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 is HandlerContext && (var1 as HandlerContext).handler === this.handler) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun hashCode(): Int {
      return System.identityHashCode(this.handler);
   }

   public override fun invokeOnTimeout(timeMillis: Long, block: Runnable, context: CoroutineContext): DisposableHandle {
      if (this.handler.postDelayed(var3, RangesKt.coerceAtMost(var1, 4611686018427387903L))) {
         return new HandlerContext$$ExternalSyntheticLambda0(this, var3);
      } else {
         this.cancelOnRejection(var4, var3);
         return NonDisposableHandle.INSTANCE;
      }
   }

   public override fun isDispatchNeeded(context: CoroutineContext): Boolean {
      val var2: Boolean;
      if (this.invokeImmediately && Looper.myLooper() == this.handler.getLooper()) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
      val var4: Runnable = new Runnable(var3, this) {
         final CancellableContinuation $continuation$inlined;
         final HandlerContext this$0;

         {
            this.$continuation$inlined = var1;
            this.this$0 = var2;
         }

         @Override
         public final void run() {
            this.$continuation$inlined.resumeUndispatched(this.this$0, Unit.INSTANCE);
         }
      };
      if (this.handler.postDelayed(var4, RangesKt.coerceAtMost(var1, 4611686018427387903L))) {
         var3.invokeOnCancellation((new Function1<java.lang.Throwable, Unit>(this, var4) {
            final Runnable $block;
            final HandlerContext this$0;

            {
               super(1);
               this.this$0 = var1;
               this.$block = var2;
            }

            public final void invoke(java.lang.Throwable var1) {
               HandlerContext.access$getHandler$p(this.this$0).removeCallbacks(this.$block);
            }
         }) as (java.lang.Throwable?) -> Unit);
      } else {
         this.cancelOnRejection(var3.getContext(), var4);
      }
   }

   public override fun toString(): String {
      var var1: java.lang.String = this.toStringInternalImpl();
      var var2: java.lang.String = var1;
      if (var1 == null) {
         val var3: HandlerContext = this;
         var1 = this.name;
         if (this.name == null) {
            var1 = this.handler.toString();
         }

         var2 = var1;
         if (this.invokeImmediately) {
            val var6: StringBuilder = new StringBuilder();
            var6.append(var1);
            var6.append(".immediate");
            var2 = var6.toString();
         }
      }

      return var2;
   }
}
