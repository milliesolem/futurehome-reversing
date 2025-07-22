package io.reactivex.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ListCompositeDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ResourceObserver<T> implements Observer<T>, Disposable {
   private final ListCompositeDisposable resources;
   private final AtomicReference<Disposable> upstream = new AtomicReference<>();

   public ResourceObserver() {
      this.resources = new ListCompositeDisposable();
   }

   public final void add(Disposable var1) {
      ObjectHelper.requireNonNull(var1, "resource is null");
      this.resources.add(var1);
   }

   @Override
   public final void dispose() {
      if (DisposableHelper.dispose(this.upstream)) {
         this.resources.dispose();
      }
   }

   @Override
   public final boolean isDisposed() {
      return DisposableHelper.isDisposed(this.upstream.get());
   }

   protected void onStart() {
   }

   @Override
   public final void onSubscribe(Disposable var1) {
      if (EndConsumerHelper.setOnce(this.upstream, var1, this.getClass())) {
         this.onStart();
      }
   }
}
