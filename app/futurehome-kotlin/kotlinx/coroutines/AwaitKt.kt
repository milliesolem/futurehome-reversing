package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl

public suspend fun <T> Collection<Deferred<T>>.awaitAll(): List<T> {
   return if (var0.isEmpty()) CollectionsKt.emptyList() else new AwaitAll(var0.toArray(new Deferred[0])).await(var1);
}

public suspend fun <T> awaitAll(vararg deferreds: Deferred<T>): List<T> {
   return if (var0.length == 0) CollectionsKt.emptyList() else new AwaitAll(var0).await(var1);
}

public suspend fun Collection<Job>.joinAll() {
   label28: {
      if (var1 is <unrepresentable>) {
         val var3: <unrepresentable> = var1 as <unrepresentable>;
         if ((var1.label and Integer.MIN_VALUE) != 0) {
            var3.label += Integer.MIN_VALUE;
            var1 = var3;
            break label28;
         }
      }

      var1 = new ContinuationImpl(var1) {
         Object L$0;
         int label;
         Object result;

         {
            super(var1);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            this.result = var1;
            this.label |= Integer.MIN_VALUE;
            return AwaitKt.joinAll(null, this);
         }
      };
   }

   var var4: Any = var1.result;
   val var7: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
   val var5: java.util.Iterator;
   if (var1.label != 0) {
      if (var1.label != 1) {
         throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }

      var5 = var1.L$0 as java.util.Iterator;
      ResultKt.throwOnFailure(var4);
   } else {
      ResultKt.throwOnFailure(var4);
      var5 = var0.iterator();
   }

   while (var5.hasNext()) {
      var4 = var5.next() as Job;
      var1.L$0 = var5;
      var1.label = 1;
      if (((Job)var4).join(var1) === var7) {
         return var7;
      }
   }

   return Unit.INSTANCE;
}

public suspend fun joinAll(vararg jobs: Job) {
   label26: {
      if (var1 is <unrepresentable>) {
         val var4: <unrepresentable> = var1 as <unrepresentable>;
         if ((var1.label and Integer.MIN_VALUE) != 0) {
            var4.label += Integer.MIN_VALUE;
            var1 = var4;
            break label26;
         }
      }

      var1 = new ContinuationImpl(var1) {
         int I$0;
         int I$1;
         Object L$0;
         int label;
         Object result;

         {
            super(var1);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            this.result = var1;
            this.label |= Integer.MIN_VALUE;
            return AwaitKt.joinAll(null, this);
         }
      };
   }

   var var10: Any = var1.result;
   val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
   val var3: Int;
   var var8: Int;
   if (var1.label != 0) {
      if (var1.label != 1) {
         throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }

      var3 = var1.I$1;
      var8 = var1.I$0;
      var0 = var1.L$0 as Array<Job>;
      ResultKt.throwOnFailure(var10);
      var8++;
      var10 = var1;
   } else {
      ResultKt.throwOnFailure(var10);
      var3 = var0.length;
      var8 = 0;
      var10 = var1;
   }

   while (var8 < var3) {
      val var6: Job = var0[var8];
      ((<unrepresentable>)var10).L$0 = var0;
      ((<unrepresentable>)var10).I$0 = var8;
      ((<unrepresentable>)var10).I$1 = var3;
      ((<unrepresentable>)var10).label = 1;
      if (var6.join((Continuation<? super Unit>)var10) === var5) {
         return var5;
      }

      var8++;
      var10 = var10;
   }

   return Unit.INSTANCE;
}
