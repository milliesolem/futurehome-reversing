package io.reactivex;

public interface ObservableTransformer<Upstream, Downstream> {
   ObservableSource<Downstream> apply(Observable<Upstream> var1);
}
