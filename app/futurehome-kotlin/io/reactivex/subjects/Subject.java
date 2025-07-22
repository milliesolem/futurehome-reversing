package io.reactivex.subjects;

import io.reactivex.Observable;
import io.reactivex.Observer;

public abstract class Subject<T> extends Observable<T> implements Observer<T> {
   public abstract Throwable getThrowable();

   public abstract boolean hasComplete();

   public abstract boolean hasObservers();

   public abstract boolean hasThrowable();

   public final Subject<T> toSerialized() {
      return (Subject<T>)(this instanceof SerializedSubject ? this : new SerializedSubject<>(this));
   }
}
