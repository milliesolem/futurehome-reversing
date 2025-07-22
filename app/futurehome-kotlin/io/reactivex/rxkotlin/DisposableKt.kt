package io.reactivex.rxkotlin

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlin.jvm.internal.Intrinsics

public fun Disposable.addTo(compositeDisposable: CompositeDisposable): Disposable {
   Intrinsics.checkParameterIsNotNull(var0, "$this$addTo");
   Intrinsics.checkParameterIsNotNull(var1, "compositeDisposable");
   var1.add(var0);
   return var0;
}

public operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
   Intrinsics.checkParameterIsNotNull(var0, "$this$plusAssign");
   Intrinsics.checkParameterIsNotNull(var1, "disposable");
   var0.add(var1);
}
