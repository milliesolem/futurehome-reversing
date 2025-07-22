package io.reactivex.internal.util;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public final class ConnectConsumer implements Consumer<Disposable> {
   public Disposable disposable;

   public void accept(Disposable var1) throws Exception {
      this.disposable = var1;
   }
}
