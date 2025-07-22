package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservablePublishSelector<T, R> extends AbstractObservableWithUpstream<T, R> {
   final Function<? super Observable<T>, ? extends ObservableSource<R>> selector;

   public ObservablePublishSelector(ObservableSource<T> var1, Function<? super Observable<T>, ? extends ObservableSource<R>> var2) {
      super(var1);
      this.selector = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(Observer<? super R> var1) {
      PublishSubject var3 = PublishSubject.create();

      ObservableSource var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.selector.apply(var3), "The selector returned a null ObservableSource");
      } catch (Throwable var5) {
         Exceptions.throwIfFatal(var5);
         EmptyDisposable.error(var5, var1);
         return;
      }

      ObservablePublishSelector.TargetObserver var6 = new ObservablePublishSelector.TargetObserver(var1);
      var2.subscribe(var6);
      this.source.subscribe(new ObservablePublishSelector.SourceObserver<>(var3, var6));
   }

   static final class SourceObserver<T, R> implements Observer<T> {
      final PublishSubject<T> subject;
      final AtomicReference<Disposable> target;

      SourceObserver(PublishSubject<T> var1, AtomicReference<Disposable> var2) {
         this.subject = var1;
         this.target = var2;
      }

      @Override
      public void onComplete() {
         this.subject.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.subject.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         this.subject.onNext((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.target, var1);
      }
   }

   static final class TargetObserver<T, R> extends AtomicReference<Disposable> implements Observer<R>, Disposable {
      private static final long serialVersionUID = 854110278590336484L;
      final Observer<? super R> downstream;
      Disposable upstream;

      TargetObserver(Observer<? super R> var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         DisposableHelper.dispose(this);
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         DisposableHelper.dispose(this);
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(R var1) {
         this.downstream.onNext((R)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
