package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReference;

public final class SequentialDisposable extends AtomicReference<Disposable> implements Disposable {
   private static final long serialVersionUID = -754898800686245608L;

   public SequentialDisposable() {
   }

   public SequentialDisposable(Disposable var1) {
      this.lazySet(var1);
   }

   @Override
   public void dispose() {
      DisposableHelper.dispose(this);
   }

   @Override
   public boolean isDisposed() {
      return DisposableHelper.isDisposed(this.get());
   }

   public boolean replace(Disposable var1) {
      return DisposableHelper.replace(this, var1);
   }

   public boolean update(Disposable var1) {
      return DisposableHelper.set(this, var1);
   }
}
