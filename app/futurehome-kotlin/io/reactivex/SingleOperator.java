package io.reactivex;

public interface SingleOperator<Downstream, Upstream> {
   SingleObserver<? super Upstream> apply(SingleObserver<? super Downstream> var1) throws Exception;
}
