package io.reactivex.rxkotlin

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.BackpressureKind
import io.reactivex.annotations.BackpressureSupport
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Intrinsics

private final val onCompleteStub: () -> Unit = <unrepresentable>.INSTANCE as Function0
private final val onErrorStub: (Throwable) -> Unit = <unrepresentable>.INSTANCE as Function1
private final val onNextStub: (Any) -> Unit = <unrepresentable>.INSTANCE as Function1

private fun <T : Any> ((T) -> Unit).asConsumer(): Consumer<T> {
   val var2: Consumer;
   if (var0 === onNextStub) {
      var2 = Functions.emptyConsumer();
      Intrinsics.checkExpressionValueIsNotNull(var2, "Functions.emptyConsumer()");
   } else {
      var var1: Any = var0;
      if (var0 != null) {
         var1 = new SubscribersKt$sam$io_reactivex_functions_Consumer$0(var0);
      }

      var2 = var1 as Consumer;
   }

   return var2;
}

private fun (() -> Unit).asOnCompleteAction(): Action {
   val var2: Action;
   if (var0 === onCompleteStub) {
      var2 = Functions.EMPTY_ACTION;
      Intrinsics.checkExpressionValueIsNotNull(Functions.EMPTY_ACTION, "Functions.EMPTY_ACTION");
   } else {
      var var1: Any = var0;
      if (var0 != null) {
         var1 = new SubscribersKt$sam$io_reactivex_functions_Action$0(var0);
      }

      var2 = var1 as Action;
   }

   return var2;
}

private fun ((Throwable) -> Unit).asOnErrorConsumer(): Consumer<Throwable> {
   val var2: Consumer;
   if (var0 === onErrorStub) {
      var2 = Functions.ON_ERROR_MISSING;
      Intrinsics.checkExpressionValueIsNotNull(Functions.ON_ERROR_MISSING, "Functions.ON_ERROR_MISSING");
   } else {
      var var1: Any = var0;
      if (var0 != null) {
         var1 = new SubscribersKt$sam$io_reactivex_functions_Consumer$0(var0);
      }

      var2 = var1 as Consumer;
   }

   return var2;
}

@BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
@SchedulerSupport("none")
public fun <T : Any> Flowable<T>.blockingSubscribeBy(
   onError: (Throwable) -> Unit = onErrorStub,
   onComplete: () -> Unit = onCompleteStub,
   onNext: (T) -> Unit = onNextStub
) {
   Intrinsics.checkParameterIsNotNull(var0, "$this$blockingSubscribeBy");
   Intrinsics.checkParameterIsNotNull(var1, "onError");
   Intrinsics.checkParameterIsNotNull(var2, "onComplete");
   Intrinsics.checkParameterIsNotNull(var3, "onNext");
   var0.blockingSubscribe(asConsumer(var3), asOnErrorConsumer(var1), asOnCompleteAction(var2));
}

@SchedulerSupport("none")
public fun <T : Any> Observable<T>.blockingSubscribeBy(
   onError: (Throwable) -> Unit = onErrorStub,
   onComplete: () -> Unit = onCompleteStub,
   onNext: (T) -> Unit = onNextStub
) {
   Intrinsics.checkParameterIsNotNull(var0, "$this$blockingSubscribeBy");
   Intrinsics.checkParameterIsNotNull(var1, "onError");
   Intrinsics.checkParameterIsNotNull(var2, "onComplete");
   Intrinsics.checkParameterIsNotNull(var3, "onNext");
   var0.blockingSubscribe(asConsumer(var3), asOnErrorConsumer(var1), asOnCompleteAction(var2));
}

@JvmSynthetic
fun `blockingSubscribeBy$default`(var0: Flowable, var1: Function1, var2: Function0, var3: Function1, var4: Int, var5: Any) {
   if ((var4 and 1) != 0) {
      var1 = onErrorStub;
   }

   if ((var4 and 2) != 0) {
      var2 = onCompleteStub;
   }

   if ((var4 and 4) != 0) {
      var3 = onNextStub;
   }

   blockingSubscribeBy(var0, var1, var2, var3);
}

@JvmSynthetic
fun `blockingSubscribeBy$default`(var0: Observable, var1: Function1, var2: Function0, var3: Function1, var4: Int, var5: Any) {
   if ((var4 and 1) != 0) {
      var1 = onErrorStub;
   }

   if ((var4 and 2) != 0) {
      var2 = onCompleteStub;
   }

   if ((var4 and 4) != 0) {
      var3 = onNextStub;
   }

   blockingSubscribeBy(var0, var1, var2, var3);
}

@CheckReturnValue
@SchedulerSupport("none")
public fun Completable.subscribeBy(onError: (Throwable) -> Unit = onErrorStub, onComplete: () -> Unit = onCompleteStub): Disposable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$subscribeBy");
   Intrinsics.checkParameterIsNotNull(var1, "onError");
   Intrinsics.checkParameterIsNotNull(var2, "onComplete");
   val var4: Disposable;
   if (var1 === onErrorStub && var2 === onCompleteStub) {
      var4 = var0.subscribe();
      Intrinsics.checkExpressionValueIsNotNull(var4, "subscribe()");
   } else if (var1 === onErrorStub) {
      var4 = var0.subscribe(new SubscribersKt$sam$io_reactivex_functions_Action$0(var2));
      Intrinsics.checkExpressionValueIsNotNull(var4, "subscribe(onComplete)");
   } else {
      var4 = var0.subscribe(asOnCompleteAction(var2), new SubscribersKt$sam$io_reactivex_functions_Consumer$0(var1));
      Intrinsics.checkExpressionValueIsNotNull(var4, "subscribe(onComplete.asO…ion(), Consumer(onError))");
   }

   return var4;
}

@BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Flowable<T>.subscribeBy(
   onError: (Throwable) -> Unit = onErrorStub,
   onComplete: () -> Unit = onCompleteStub,
   onNext: (T) -> Unit = onNextStub
): Disposable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$subscribeBy");
   Intrinsics.checkParameterIsNotNull(var1, "onError");
   Intrinsics.checkParameterIsNotNull(var2, "onComplete");
   Intrinsics.checkParameterIsNotNull(var3, "onNext");
   val var4: Disposable = var0.subscribe(asConsumer(var3), asOnErrorConsumer(var1), asOnCompleteAction(var2));
   Intrinsics.checkExpressionValueIsNotNull(var4, "subscribe(onNext.asConsu…ete.asOnCompleteAction())");
   return var4;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Maybe<T>.subscribeBy(
   onError: (Throwable) -> Unit = onErrorStub,
   onComplete: () -> Unit = onCompleteStub,
   onSuccess: (T) -> Unit = onNextStub
): Disposable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$subscribeBy");
   Intrinsics.checkParameterIsNotNull(var1, "onError");
   Intrinsics.checkParameterIsNotNull(var2, "onComplete");
   Intrinsics.checkParameterIsNotNull(var3, "onSuccess");
   val var4: Disposable = var0.subscribe(asConsumer(var3), asOnErrorConsumer(var1), asOnCompleteAction(var2));
   Intrinsics.checkExpressionValueIsNotNull(var4, "subscribe(onSuccess.asCo…ete.asOnCompleteAction())");
   return var4;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Observable<T>.subscribeBy(
   onError: (Throwable) -> Unit = onErrorStub,
   onComplete: () -> Unit = onCompleteStub,
   onNext: (T) -> Unit = onNextStub
): Disposable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$subscribeBy");
   Intrinsics.checkParameterIsNotNull(var1, "onError");
   Intrinsics.checkParameterIsNotNull(var2, "onComplete");
   Intrinsics.checkParameterIsNotNull(var3, "onNext");
   val var4: Disposable = var0.subscribe(asConsumer(var3), asOnErrorConsumer(var1), asOnCompleteAction(var2));
   Intrinsics.checkExpressionValueIsNotNull(var4, "subscribe(onNext.asConsu…ete.asOnCompleteAction())");
   return var4;
}

@CheckReturnValue
@SchedulerSupport("none")
public fun <T : Any> Single<T>.subscribeBy(onError: (Throwable) -> Unit = onErrorStub, onSuccess: (T) -> Unit = onNextStub): Disposable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$subscribeBy");
   Intrinsics.checkParameterIsNotNull(var1, "onError");
   Intrinsics.checkParameterIsNotNull(var2, "onSuccess");
   val var3: Disposable = var0.subscribe(asConsumer(var2), asOnErrorConsumer(var1));
   Intrinsics.checkExpressionValueIsNotNull(var3, "subscribe(onSuccess.asCo…rror.asOnErrorConsumer())");
   return var3;
}

@JvmSynthetic
fun `subscribeBy$default`(var0: Completable, var1: Function1, var2: Function0, var3: Int, var4: Any): Disposable {
   if ((var3 and 1) != 0) {
      var1 = onErrorStub;
   }

   if ((var3 and 2) != 0) {
      var2 = onCompleteStub;
   }

   return subscribeBy(var0, var1, var2);
}

@JvmSynthetic
fun `subscribeBy$default`(var0: Flowable, var1: Function1, var2: Function0, var3: Function1, var4: Int, var5: Any): Disposable {
   if ((var4 and 1) != 0) {
      var1 = onErrorStub;
   }

   if ((var4 and 2) != 0) {
      var2 = onCompleteStub;
   }

   if ((var4 and 4) != 0) {
      var3 = onNextStub;
   }

   return subscribeBy(var0, var1, var2, var3);
}

@JvmSynthetic
fun `subscribeBy$default`(var0: Maybe, var1: Function1, var2: Function0, var3: Function1, var4: Int, var5: Any): Disposable {
   if ((var4 and 1) != 0) {
      var1 = onErrorStub;
   }

   if ((var4 and 2) != 0) {
      var2 = onCompleteStub;
   }

   if ((var4 and 4) != 0) {
      var3 = onNextStub;
   }

   return subscribeBy(var0, var1, var2, var3);
}

@JvmSynthetic
fun `subscribeBy$default`(var0: Observable, var1: Function1, var2: Function0, var3: Function1, var4: Int, var5: Any): Disposable {
   if ((var4 and 1) != 0) {
      var1 = onErrorStub;
   }

   if ((var4 and 2) != 0) {
      var2 = onCompleteStub;
   }

   if ((var4 and 4) != 0) {
      var3 = onNextStub;
   }

   return subscribeBy(var0, var1, var2, var3);
}

@JvmSynthetic
fun `subscribeBy$default`(var0: Single, var1: Function1, var2: Function1, var3: Int, var4: Any): Disposable {
   if ((var3 and 1) != 0) {
      var1 = onErrorStub;
   }

   if ((var3 and 2) != 0) {
      var2 = onNextStub;
   }

   return subscribeBy(var0, var1, var2);
}
