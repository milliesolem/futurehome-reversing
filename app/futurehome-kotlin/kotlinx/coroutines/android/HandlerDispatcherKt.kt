package kotlinx.coroutines.android

import android.os.Handler
import android.os.Looper
import android.os.Build.VERSION
import android.os.Handler.Callback
import android.view.Choreographer
import java.lang.reflect.Constructor
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellableContinuationImpl
import kotlinx.coroutines.Dispatchers

private const val MAX_DELAY: Long = 4611686018427387903L

@Deprecated(
   level = DeprecationLevel.HIDDEN,
   message = "Use Dispatchers.Main instead"
)
internal final val Main: HandlerDispatcher?

private final var choreographer: Choreographer?

@JvmSynthetic
fun `$r8$lambda$_-s4SOKmmdhN7PexQng1D-Olurw`(var0: CancellableContinuation, var1: Long) {
   postFrameCallback$lambda$6(var0, var1);
}

@JvmSynthetic
fun `access$awaitFrameSlowPath`(var0: Continuation): Any {
   return awaitFrameSlowPath(var0);
}

@JvmSynthetic
fun `access$postFrameCallback`(var0: Choreographer, var1: CancellableContinuation) {
   postFrameCallback(var0, var1);
}

@JvmSynthetic
fun `access$updateChoreographerAndPostFrameCallback`(var0: CancellableContinuation) {
   updateChoreographerAndPostFrameCallback(var0);
}

internal fun Looper.asHandler(async: Boolean): Handler {
   if (var1) {
      if (VERSION.SDK_INT >= 28) {
         val var4: Any = Handler.class.getDeclaredMethod("createAsync", Looper.class).invoke(null, var0);
         return var4 as Handler;
      } else {
         var var2: Constructor;
         try {
            var2 = Handler.class.getDeclaredConstructor(Looper.class, Callback.class, boolean.class);
         } catch (var3: NoSuchMethodException) {
            return new Handler(var0);
         }

         return var2.newInstance(var0, null, true) as Handler;
      }
   } else {
      return new Handler(var0);
   }
}

public suspend fun awaitFrame(): Long {
   var var1: Choreographer = choreographer;
   if (choreographer != null) {
      val var2: CancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var0), 1);
      var2.initCancellability();
      access$postFrameCallback(var1, var2);
      var1 = (Choreographer)var2.getResult();
      if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var0);
      }

      return var1;
   } else {
      return awaitFrameSlowPath(var0);
   }
}

private suspend fun awaitFrameSlowPath(): Long {
   val var1: CancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var0), 1);
   var1.initCancellability();
   val var2: CancellableContinuation = var1;
   if (Looper.myLooper() === Looper.getMainLooper()) {
      access$updateChoreographerAndPostFrameCallback(var2);
   } else {
      Dispatchers.getMain().dispatch(var2.getContext(), new Runnable(var2) {
         final CancellableContinuation $cont$inlined;

         {
            this.$cont$inlined = var1;
         }

         @Override
         public final void run() {
            HandlerDispatcherKt.access$updateChoreographerAndPostFrameCallback(this.$cont$inlined);
         }
      });
   }

   val var3: Any = var1.getResult();
   if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(var0);
   }

   return var3;
}

fun from(var0: Handler): HandlerDispatcher {
   return from$default(var0, null, 1, null);
}

public fun Handler.asCoroutineDispatcher(name: String? = ...): HandlerDispatcher {
   return new HandlerContext(var0, var1);
}

@JvmSynthetic
fun `from$default`(var0: Handler, var1: java.lang.String, var2: Int, var3: Any): HandlerDispatcher {
   if ((var2 and 1) != 0) {
      var1 = null;
   }

   return from(var0, var1);
}

private fun postFrameCallback(choreographer: Choreographer, cont: CancellableContinuation<Long>) {
   var0.postFrameCallback(new HandlerDispatcherKt$$ExternalSyntheticLambda0(var1));
}

fun `postFrameCallback$lambda$6`(var0: CancellableContinuation, var1: Long) {
   var0.resumeUndispatched(Dispatchers.getMain(), var1);
}

private fun updateChoreographerAndPostFrameCallback(cont: CancellableContinuation<Long>) {
   var var1: Choreographer = choreographer;
   if (choreographer == null) {
      var1 = Choreographer.getInstance();
      choreographer = var1;
   }

   postFrameCallback(var1, var0);
}
