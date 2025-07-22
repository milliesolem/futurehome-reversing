package io.reactivex;

public interface ObservableOperator<Downstream, Upstream> {
   Observer<? super Upstream> apply(Observer<? super Downstream> var1) throws Exception;
}
