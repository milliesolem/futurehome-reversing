package io.reactivex;

public interface ObservableConverter<T, R> {
   R apply(Observable<T> var1);
}
