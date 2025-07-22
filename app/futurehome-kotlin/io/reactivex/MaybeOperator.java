package io.reactivex;

public interface MaybeOperator<Downstream, Upstream> {
   MaybeObserver<? super Upstream> apply(MaybeObserver<? super Downstream> var1) throws Exception;
}
