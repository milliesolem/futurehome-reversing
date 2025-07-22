package kotlin.sequences

import java.util.NoSuchElementException
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.internal.markers.KMappedMarker

private class SequenceBuilderIterator<T> : SequenceScope<T>, java.util.Iterator<T>, Continuation<Unit>, KMappedMarker {
   private final var state: Int
   private final var nextValue: Any?
   private final var nextIterator: Iterator<Any>?

   public final var nextStep: Continuation<Unit>?
      internal set

   public open val context: CoroutineContext
      public open get() {
         return EmptyCoroutineContext.INSTANCE;
      }


   private fun exceptionalState(): Throwable {
      val var3: java.lang.Throwable;
      if (this.state != 4) {
         if (this.state != 5) {
            val var2: StringBuilder = new StringBuilder("Unexpected state of the iterator: ");
            var2.append(this.state);
            var3 = new IllegalStateException(var2.toString());
         } else {
            var3 = new IllegalStateException("Iterator has failed.");
         }
      } else {
         var3 = new NoSuchElementException();
      }

      return var3;
   }

   private fun nextNotReady(): Any {
      if (this.hasNext()) {
         return this.next();
      } else {
         throw new NoSuchElementException();
      }
   }

   public override operator fun hasNext(): Boolean {
      while (true) {
         if (this.state != 0) {
            if (this.state != 1) {
               if (this.state != 2 && this.state != 3) {
                  if (this.state == 4) {
                     return false;
                  }

                  throw this.exceptionalState();
               }

               return true;
            }

            val var2: java.util.Iterator = this.nextIterator;
            if (var2.hasNext()) {
               this.state = 2;
               return true;
            }

            this.nextIterator = null;
         }

         this.state = 5;
         val var4: Continuation = this.nextStep;
         this.nextStep = null;
         val var3: Result.Companion = Result.Companion;
         var4.resumeWith(Result.constructor-impl(Unit.INSTANCE));
      }
   }

   public override operator fun next(): Any {
      if (this.state == 0 || this.state == 1) {
         return this.nextNotReady();
      } else if (this.state == 2) {
         this.state = 1;
         val var3: java.util.Iterator = this.nextIterator;
         return (T)var3.next();
      } else if (this.state == 3) {
         this.state = 0;
         val var2: Any = this.nextValue;
         this.nextValue = null;
         return (T)var2;
      } else {
         throw this.exceptionalState();
      }
   }

   override fun remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public override fun resumeWith(result: Result<Unit>) {
      ResultKt.throwOnFailure(var1);
      this.state = 4;
   }

   public override suspend fun yield(value: Any) {
      this.nextValue = (T)var1;
      this.state = 3;
      this.nextStep = var2;
      var1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var2);
      }

      return if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var1 else Unit.INSTANCE;
   }

   public override suspend fun yieldAll(iterator: Iterator<Any>) {
      if (!var1.hasNext()) {
         return Unit.INSTANCE;
      } else {
         this.nextIterator = var1;
         this.state = 2;
         this.nextStep = var2;
         val var3: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
         if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(var2);
         }

         return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
      }
   }
}
