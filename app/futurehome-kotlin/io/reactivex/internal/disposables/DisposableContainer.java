package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;

public interface DisposableContainer {
   boolean add(Disposable var1);

   boolean delete(Disposable var1);

   boolean remove(Disposable var1);
}
