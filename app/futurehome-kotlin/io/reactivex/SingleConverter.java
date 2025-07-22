package io.reactivex;

public interface SingleConverter<T, R> {
   R apply(Single<T> var1);
}
